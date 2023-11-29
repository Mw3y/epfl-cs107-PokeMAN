package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.io.ResourcePath;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;


public class Sprite extends ImageGraphics {

	/**
	 * Creates a new Sprite.
	 * @param name (String): image name, may be null
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param roi (RegionOfInterest): region of interest into the image as a rectangle in the image. May be null
	 * @param anchor (Vector): image anchor, not null
	 * @param alpha (float): transparency, between 0 (invisible) and 1 (opaque)
	 * @param depth (float): render priority, lower-values drawn first
	 */
	public Sprite(String name, float width, float height, Positionable parent, RegionOfInterest roi, Vector anchor, float alpha, float depth) {
		super(ResourcePath.getSprite(name), width, height, roi, anchor, alpha, depth);
		setParent(parent);
	}

	/**
	 * Creates a new image graphics.
	 * @param name (String): image name, not null
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param roi (RegionOfInterest): region of interest into the image as a rectangle in the image. May be null
	 * @param anchor (Vector): image anchor, not null
	 */
	public Sprite(String name, float width, float height, Positionable parent, RegionOfInterest roi, Vector anchor) {
		super(ResourcePath.getSprite(name), width, height, roi, anchor, 1.0f, 0);
		setParent(parent);
	}

	/**
	 * Creates a new image graphics.
	 * @param name (String): image name, not null
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param roi (RegionOfInterest): region of interest into the image as a rectangle in the image. May be null
	 */
	public Sprite(String name, float width, float height, Positionable parent, RegionOfInterest roi) {
		super(ResourcePath.getSprite(name), width, height, roi, Vector.ZERO, 1.0f, 0);
		setParent(parent);
	}

	/**
	 * Creates a new image graphics.
	 * @param name (String): image name, not null
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 */
	public Sprite(String name, float width, float height, Positionable parent) {
		super(ResourcePath.getSprite(name), width, height, null, Vector.ZERO, 1.0f, 0);
		setParent(parent);
	}

	//Utilities to extract sprites

	/**
	 * Extracts from an image the sprites corresponding to a given orientation
	 * the returned array has 4 entry (one per orientation)
	 * the content of each entry is an array of sprites corresponding to the given orientation
	 * (the entry indexed by Orientation.dir.ordinal() is the array of sprites corresponding 
	 * to the orientation Orientation.dir). 
	 * @param name (String): the name of the image 
	 * @param nbFrames (int): number of frames in each row
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param anchor (Vector) : image anchor, not null
	 * @param regionWidth (int): width of frame (number of pixels in the image)
	 * @param regionHeight (int): height of frame (number of pixels in the image)
	 * @param order (Orientation[]): order of the frames in the image
	 *
	 *
	 * @return an array of 4 Sprite[] (one Sprite[] per orientation)
	 */
	public static Sprite[][] extractSprites(String name, int nbFrames, float width, float height, Positionable parent, int regionWidth, int regionHeight, Vector anchor, Orientation[] order){

		Sprite[][] sprites = new Sprite[4][nbFrames];

		for(int i = 0; i < nbFrames; i++){
			int j = 0;
			sprites[order[0].ordinal()][i]  = new Sprite(name, width, height, parent, new RegionOfInterest(i*regionWidth, regionHeight*j++, regionWidth, regionHeight), anchor);
			sprites[order[1].ordinal()][i]  = new Sprite(name, width, height, parent, new RegionOfInterest(i*regionWidth, regionHeight*j++, regionWidth, regionHeight), anchor);
			sprites[order[2].ordinal()][i]  = new Sprite(name, width, height, parent, new RegionOfInterest(i*regionWidth, regionHeight*j++, regionWidth, regionHeight), anchor);
			sprites[order[3].ordinal()][i]  = new Sprite(name, width, height, parent, new RegionOfInterest(i*regionWidth, regionHeight*j, regionWidth, regionHeight), anchor);
		}
		return sprites;
	}
	
	/**
	 * Extracts from an image the sprites corresponding to a given orientation
	 * the returned array has 4 entry (one per orientation)
	 * the content of each entry is an array of sprites corresponding to the given orientation
	 * (the entry indexed by Orientation.dir.ordinal() is the array of sprites corresponding 
	 * to the orientation Orientation.dir). 
	 * @param name (String): the name of the image 
	 * @param nbFrames (int): number of frames in each row
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param regionWidth (int): width of frame (number of pixels in the image)
	 * @param regionHeight (int): height of frame (number of pixels in the image)
	 * @param order (Orientation[]): order of the frames in the image
	 *
	 *
	 * @return an array of 4 Sprite[] (one Sprite[] per orientation)
	 */
	public static  Sprite[][] extractSprites(String name, int nbFrames, float width, float height, Positionable parent, int regionWidth, int regionHeight, Orientation[] order){  	
		return extractSprites(name, nbFrames, width, height, parent, regionWidth, regionHeight, Vector.ZERO, order);
	}
	

	/**
	 * Extracts from an image the sprites
	 * @param name (String): the name of the image 
	 * @param nbFrames (int): number of frames in each row
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param anchor (Vector) : image anchor, not null
	 * @param regionWidth (int): width of frame (number of pixels in the image)
	 * @param regionHeight (int): height of frame (number of pixels in the image)
	 *
	 *
	 * @return an array of Sprite
	 */
	public static  Sprite[] extractSprites(String name, int nbFrames, float width, float height, Positionable parent, Vector anchor, int regionWidth, int regionHeight){  	
		Sprite[] sprites = new Sprite[nbFrames];

		for(int i = 0; i < nbFrames; i++){
			sprites[i]  = new Sprite(name, width, height, parent, new RegionOfInterest(i*regionWidth, 0, regionWidth, regionHeight), anchor);
		}

		return sprites;
	}

	/**
	 * Extracts from an image the sprites
	 * @param name (String): the name of the image 
	 * @param nbFrames (int): number of frames in each row
	 * @param width (int): actual image width, before transformation
	 * @param height (int): actual image height, before transformation
	 * @param parent (Positionable): parent of this, not null
	 * @param regionWidth (int): width of frame (number of pixels in the image)
	 * @param regionHeight (int): height of frame (number of pixels in the image)
	 *
	 *
	 * @return an array of Sprite
	 */
	public static Sprite[] extractSprites(String name, int nbFrames, float width, float height, Positionable parent, int regionWidth, int regionHeight){  	
		return extractSprites(name, nbFrames, width, height, parent, Vector.ZERO, regionWidth, regionHeight);
	}
}
