package ch.epfl.cs107.play.window.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.jar.JarFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.shape.Shape;
import ch.epfl.cs107.play.math.TextAlign;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Image;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Mouse;
import ch.epfl.cs107.play.window.Sound;
import ch.epfl.cs107.play.window.Window;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import static java.util.Objects.isNull;

/**
 * Swing implementation of window context.
 */
public final class SwingWindow extends Node implements Window {

	// File system
	private final FileSystem fileSystem;

	// Image stuff
	private final Map<String, SwingImage> images;
	private final List<Item> gItems;
	
	//Sound Stuff
	private final Map<String, SwingSound> sounds;
	private final List<SoundItem> aItems;

	// Swing components
	private final JFrame frame;
	private final java.awt.Canvas canvas;
	private BufferStrategy strategy;

	// State information
	private volatile boolean closeRequested;
	private Button focus;
	private final MouseProxy mouseProxy;
	private final KeyboardProxy keyboardProxy;
	
	// Define mouse manager
	private final class MouseProxy extends MouseAdapter implements Mouse {

		private int previous = 0;
		private int current = 0;
		private int buffer = 0;
		private Vector position = Vector.ZERO;

		@Override
		public void mousePressed(MouseEvent e) {
			synchronized (SwingWindow.this) {
				buffer |= 1 << e.getButton() - 1;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			synchronized (SwingWindow.this) {
				buffer &= ~(1 << e.getButton() - 1);
			}
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO mouse scroll?
		}

		@Override
		public Vector getPosition() {
			return position;
		}

		@Override
		public Button getButton(int index) {
			int mask = 1 << index;
			return new Button((previous & mask) != 0, (current & mask) != 0);
		}
		
		@Override
		public Vector getVelocity() {
			// TODO interpolate mouse velocity
			return Vector.ZERO;
		}
	}

	// Define keyboard manager
	private final class KeyboardProxy extends KeyAdapter implements Keyboard {

		private Set<Integer> previous;
		private Set<Integer> current;
		private Set<Integer> buffer;
		private int lastBufferAdd;
		private int lastPressed;

		private KeyboardProxy() {
			previous = new HashSet<>();
			current = new HashSet<>();
			buffer = new HashSet<>();
			lastBufferAdd = -1;
			lastPressed = -1;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			synchronized (SwingWindow.this) {
				buffer.add(e.getKeyCode());
				lastBufferAdd = e.getKeyCode();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			synchronized (SwingWindow.this) {
				buffer.remove(e.getKeyCode());
				if(e.getKeyCode() == lastBufferAdd)
					lastBufferAdd = -1;
			}
		}

		@Override
		public Button get(int code) {
			return new Button(previous.contains(code), current.contains(code), code == lastPressed);
		}
	}

	/**
	 * Creates a new window.
	 * @param title (String): window caption
	 * @param fileSystem (FileSystem): source used to load images
	 * @param width (int): width in pixel of the window
	 * @param height (int): height in pixel of the window
	 */
	public SwingWindow(String title, FileSystem fileSystem, int width, int height) {

		// Prepare image and sound loader
		this.fileSystem = fileSystem;
		images = new HashMap<>();
		gItems = new ArrayList<>();
		sounds = new HashMap<>();
		aItems = new ArrayList<>();

		// Create Swing canvas
		canvas = new java.awt.Canvas();
		canvas.setFocusable(true);
		canvas.setFocusTraversalKeysEnabled(false);
		canvas.setIgnoreRepaint(true);
		canvas.setBackground(Color.BLACK);

		// Create Swing frame
		frame = new JFrame(title);
		frame.add(canvas);
		focus = new Button(false);

		// Handle close request
		final WindowAdapter windowAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {
				closeRequested = true;
			}
		};
		frame.addWindowListener(windowAdapter);

		// Create mouse manager
		mouseProxy = new MouseProxy();
		canvas.addMouseListener(mouseProxy);
		canvas.addMouseWheelListener(mouseProxy);

		// Create keyboard manager
		keyboardProxy = new KeyboardProxy();
		canvas.addKeyListener(keyboardProxy);

		// Show frame
		frame.pack();
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public Button getFocus() {
		return focus;
	}

	@Override
	public Mouse getMouse() {
		return mouseProxy;
	}

	@Override
	public Keyboard getKeyboard() {
		return keyboardProxy;
	}

	@Override
	public boolean isCloseRequested() {
		return closeRequested;
	}

	@Override
	public void update() {
		// Compute viewport metrics
		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		float halfX;
		float halfY;
		if (width > height) {
			halfX = 1.0f;
			halfY = (float) height / (float) width;
		} else {
			halfX = (float) width / (float) height;
			halfY = 1.0f;
		}
		final Transform viewToWorld = getTransform();
		final Transform worldToView = viewToWorld.inverted();
		final Transform projection = new Transform(width / halfX, 0.0f, 0.5f * width, 0.0f, -height / halfY, 0.5f * height);
		final Transform transform = worldToView.transformed(projection);

		// Setup double buffering if needed
		if (strategy == null) {
			canvas.createBufferStrategy(2);
			strategy = canvas.getBufferStrategy();
		}

		// Create graphic context
		final Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();

		// Clear background
		graphics.setColor(canvas.getBackground());
		graphics.fillRect(0, 0, width, height);

		// Enable anti-aliasing
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Set view transform
		final AffineTransform affine = new AffineTransform(transform.m00, transform.m10, transform.m01,
				transform.m11, transform.m02, transform.m12);
		graphics.transform(affine);

		// Render ordered drawable
		Collections.sort(gItems);
		for (Item item : gItems)
			item.render(graphics);

		// Clean the audio item by removing terminated ones
		if(isSoundSupported())
			aItems.removeIf(SoundItem::isFinish);

		// Finalize rendering
		graphics.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
		gItems.clear();

		// Update window state
		focus = focus.updated(canvas.hasFocus());

		// Get mouse pointer location
		float x = 0.0f;
		float y = 0.0f;
		PointerInfo pointer = MouseInfo.getPointerInfo();
		if (pointer != null) {
			Point point = pointer.getLocation();
			SwingUtilities.convertPointFromScreen(point, canvas);
			x = ((float) point.getX() - 0.5f * width) * halfX / width;
			y = ((float) point.getY() - 0.5f * height) * -halfY / height;
		}
		mouseProxy.position = viewToWorld.onPoint(x, y);

		synchronized (this) {

			// Update mouse buttons
			mouseProxy.previous = mouseProxy.current;
			mouseProxy.current = mouseProxy.buffer;

			// Update keyboard buttons
			final Set<Integer> tmp = keyboardProxy.previous;
			keyboardProxy.previous = keyboardProxy.current;
			keyboardProxy.current = keyboardProxy.buffer;
			keyboardProxy.lastPressed = keyboardProxy.lastBufferAdd;
			keyboardProxy.buffer = tmp;
			keyboardProxy.buffer.clear();
			keyboardProxy.buffer.addAll(keyboardProxy.current);
		}
	}

	@Override
	public void dispose() {
		playSound(null, false,0.0f, false, false, true);
		frame.dispose();
	}

	@Override
	public SwingImage getImage(String name, RegionOfInterest roi, boolean removeBackground) {
		SwingImage image = images.get(name+roi);
		if (image == null) {
			InputStream input = null;
			try {
				input = fileSystem.read(name);
				image = new SwingImage(input, roi, removeBackground);
			} catch (IOException e) {
				// Empty on purpose, will return null as an error
				System.out.println("File :"+ name +" not found");
			} finally {
				try {
					if (input != null)
						input.close();
				} catch (IOException e) {
					// Empty on purpose
				}
			}
			images.put(name+roi, image);
			// TODO maybe need to free memory at some point?
		}
		return image;
	}

	/**
	 * Add specified item to current draw list.
	 * @param item (Item) any item, not null
	 */
	public void draw(Item item) {
		if (item == null)
			throw new NullPointerException();
		gItems.add(item);
	}

	@Override
	public void drawImage(Image image, Transform transform, float alpha, float depth) {
		if (transform == null)
			throw new NullPointerException();
		if (image == null || alpha <= 0.0f)
			return;
		gItems.add(new ImageItem(depth, alpha, transform, (SwingImage) image));
	}

	@Override
	public void drawShape(Shape shape, Transform transform, Color fillColor, Color outlineColor, float thickness, float alpha, float depth) {
		if (transform == null)
			throw new NullPointerException();
		if (shape == null || alpha <= 0.0f || (fillColor == null && (outlineColor == null || thickness <= 0.0f)))
			return;
		Path2D path = shape.toPath();
		path.transform(transform.getAffineTransform());
		gItems.add(new ShapeItem(path, fillColor, outlineColor, thickness, alpha, depth));
	}

	@Override
	public void registerFonts(String directoryName) {
		try {
			// HR : Fetch the folder with the fonts
			var directory = getResourceDirectory(directoryName);
			if(isNull(directory)){
				System.out.printf("Directory %s is missing - cannot register fonts%n", directoryName);
				return;
			}
			for(var file : directory.listFiles()) {
				// HR : Recursively add fonts
				if(file.isDirectory())
					registerFonts(file.getName());
				else {
					// HR : Register the font in the GraphicsEnvironment
					var font = Font.createFont(Font.TRUETYPE_FONT, file);
					GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
				}
			}
		}catch(Exception e) {
			//Empty on purpose
			e.printStackTrace();
		}
	}

	private static File getResourceDirectory(String resource) throws IOException, URISyntaxException {
		// HR : ClassLoader to use
		var loader = SwingWindow.class.getClassLoader();
		var url = loader.getResource(resource);
		if (url.getProtocol().equals("jar")) {
			// HR : Create the files
			var jarFile = new File(url.getPath().substring(5, url.getPath().indexOf('!')));
			var tempDirectory = Files.createTempDirectory("game-engine").toFile();
			// HR : Enumerate the content of the JarFile
			try(var jar = new JarFile(jarFile)){
				var entries = jar.entries();
				while (entries.hasMoreElements()) {
					var entry = entries.nextElement();
					// HR : Convert from the JarEntry to the file
					var file = new File(tempDirectory, entry.getName());
					if (entry.isDirectory()) {
						file.mkdirs();
					} else {
						FileUtils.copyInputStreamToFile(jar.getInputStream(entry), file);
					}
				}
			}
			// HR : Find the subdirectory in the tempDirectory
			var resfile = tempDirectory.listFiles(file -> file.getName().endsWith(resource.replace("/", "")));
			tempDirectory.deleteOnExit();
			return resfile != null && resfile.length != 0 ? resfile[0] : null;
		} else {
			return new File(new URI(url.toString()).getPath());
		}
	}
	
	@Override
	public void drawText(String text, float fontSize, Transform transform, Color fillColor, Color outlineColor, float thickness, String fontName,
			boolean bold, boolean italics, Vector anchor, TextAlign.Horizontal hAlign, TextAlign.Vertical vAlign, float alpha, float depth) {
		if (transform == null)
			throw new NullPointerException();
		if (text == null || fontSize <= 0.0f || alpha <= 0.0f || (fillColor == null && (outlineColor == null || thickness <= 0.0f)))
			return;
				
		gItems.add(new TextItem(text, fontSize, transform, fillColor, outlineColor, thickness, fontName, bold, italics, anchor, hAlign, vAlign, depth, alpha));
	}

	@Override
	public SwingSound getSound(String name) {

		SwingSound sound = sounds.get(name);
		if (sound == null) {
			InputStream input = null;
			try {
				input = fileSystem.read(name);
				sound = new SwingSound(input);
			} catch (IOException | UnsupportedAudioFileException e) {
				// Empty on purpose, will return null as an error
				System.out.println("File :"+ name +" not found or not readable");
			} finally {
				try {
					if (input != null)
						input.close();
				} catch (IOException e) {
					// Empty on purpose
				}
			}
			sounds.put(name, sound);
		}
		return sound;
	}

	@Override
	public void playSound(Sound sound, boolean randomFirstStart, float volume, boolean fadeIn, boolean loop, boolean stopOthersOnStart) {

		if(isSoundSupported()) {
			if (stopOthersOnStart) {
				for (SoundItem item : aItems) {
					item.finish();        // Can be commented and replaced by fade out on next line
					//item.fadeOut();	// if uncomment fade out, please comment the clear line below
				}
				aItems.clear();            // Comment this line if fade out option !
			}

			if (sound == null)
				return;

			final SoundItem item = new SoundItem(loop, volume, fadeIn, randomFirstStart, (SwingSound) sound);
			item.start();
			aItems.add(item);
		}
	}

	@Override
	public boolean isSoundSupported() {
		return true;
	}

	@Override
	public Vector convertPositionOnScreen(Vector coord) {
		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		float halfX;
		float halfY;
		if (width > height) {
			halfX = 1.0f;
			halfY = (float) height / (float) width;
		} else {
			halfX = (float) width / (float) height;
			halfY = 1.0f;
		}
		final Transform viewToWorld = getTransform();
		final Transform worldToView = viewToWorld.inverted();

		final Vector pointInView = worldToView.onPoint(coord.x, coord.y);
		final float x = pointInView.getX() / (halfX / width) + 0.5f * width;
		final float y = pointInView.getY() / (-halfY / height) + 0.5f * height;
		final Vector pointInViewPixelCoord = new Vector(x, y);
		if(canvas.isShowing()) {
			final Point canvasLocationInScreen = canvas.getLocationOnScreen();
			final Vector pointInScreen = pointInViewPixelCoord.add(canvasLocationInScreen.x, canvasLocationInScreen.y);
			return pointInScreen;
		}
		return null;
	}
	
	@Override
	public int getWidth() {
		return canvas.getWidth();
	}

	@Override
	public int getHeight() {
		return canvas.getHeight();
	}

	@Override
	public float getXScale() {
		return getTransform().getX().getX();
	}

	@Override
	public float getYScale() {
		return getTransform().getY().getY();
	}
	
	@Override
    public float getScaledWidth() {
		 float width = getXScale();
		 float height = getYScale();
	
		 float ratio = getWidth()/(float)getHeight();
		 if(ratio <= 1) {
			 width = height * ratio;
		 }
		 return width;
	 }
    
    @Override
    public float getScaledHeight() {
    	 float width = getXScale();
		 float height = getYScale();
	
		 float ratio = getWidth()/(float)getHeight();
		 if(ratio > 1) {
			 height = width / ratio;
		 }
		 return height;
    }
	
}