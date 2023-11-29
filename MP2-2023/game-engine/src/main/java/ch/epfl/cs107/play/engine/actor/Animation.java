package ch.epfl.cs107.play.engine.actor;

import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.engine.Updatable;
import ch.epfl.cs107.play.window.Canvas;


/**
 * Animation is a Frames sequence of Sprite
 */
public class Animation implements Updatable, Graphics{

	/// Duration of each frame (all frames have same duration)
	private final int frameDuration;
	/// Speed factor to reduce the frame duration
	private int speedFactor;
	/// Frames sequence of Sprite
	private final Sprite[] frames;

	private boolean repeat;
	private boolean isCompleted;

	/// counts for the animation
	private int count;
	private int currentFrame;
	private boolean isPaused;


	/**
	 * Default Animation Constructor
	 * @param frameDuration (int): Duration of each frame (all frames have same duration)
	 * @param sprites (Sprite...): Array of sprite in the correct sequence order. Not null
	 * @param repeat (boolean): if the animation should be repeated after it is completed
	 */
	public Animation(int frameDuration, Sprite[] sprites, boolean repeat) {
		this.frameDuration = frameDuration;
		this.frames = sprites;
		this.speedFactor = 1;
		this.count = 1;
		this.currentFrame = 0;
		this.isPaused = false;
		this.repeat = repeat;
	}

	/*
	 * Repeated animation constructor
	 * @param frameDuration (int): Duration of each frame (all frames have same duration)
	 * @param sprites (Sprite...): Array of sprite in the correct sequence order. Not null
	 */
	public Animation(int frameDuration, Sprite[] sprites) {
		this(frameDuration, sprites, true);
	}

	/**
	 * Update the speed factor of this Animation. Can be done on the fly.
	 * Note the speed factor is given between 1 (original speed) and frameDuration (maximal speed)
	 * Hence we cannot slow down the animation !
	 * TODO make the animation compatible with slow down
	 * @param speedFactor (int): new speed factor. Will be cropped between 1 and frameDuration
	 */
	public void setSpeedFactor(int speedFactor) {
		this.speedFactor = Math.min(Math.max(1, speedFactor), frameDuration);
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setAnchor(Vector anchor) {
		for(Sprite sprite : frames) {
			sprite.setAnchor(anchor);
		}
	}

	public void setWidth(float width) {
		for(Sprite sprite : frames) {
			sprite.setWidth(width);
		}
	}

	public void setHeight(float height) {
		for(Sprite sprite : frames) {
			sprite.setHeight(height);
		}
	}

	/**
	 * Reset this animation by setting the current frame to the first of the sequence
	 */
	public void reset(){
		this.currentFrame = 0;
		this.count = 1;
		this.isPaused = false;
		this.isCompleted = false;
	}

	public void switchPause(){
		this.isPaused = !this.isPaused;
	}

	/// Animation implements Updatable

	@Override
	public void update(float deltaTime) {
		if (!isPaused && !isCompleted) {
			// Count the rendering frames. And decide when changing the frame
			count = (count + 1) % (frameDuration / (speedFactor));

			if (count == 0) {
				currentFrame = (currentFrame + 1) % frames.length;
				if(currentFrame == 0 && !repeat) {
					isCompleted = true;
					currentFrame = frames.length - 1; //Stay in the last frame state
				}
			}
		}
	}

	/// Animation implements Graphics

	@Override
	public void draw(Canvas canvas) {
		frames[currentFrame].draw(canvas);
	}


	// Utilities for creating animations from sprites

	/**
	 * Creates an array of 4 animations (one animation per orientation)
	 * the entry indexed by Orientation.dir.ordinal() is the animation corresponding 
	 * to the orientation Orientation.dir
	 * @param animationDuration (int): the animation duration
	 * @param sprites (Sprite[][]): sprites to be played by each animation
	 * sprites[Orientation.dir.ordinal()] is the set of sprites to be played 
	 * by the animation indexed by Orientation.dir.ordinal()
	 * @param repeat (boolean) : true if the animations must be repeated
	 * @return an array of 4 animations (one animation per orientation)
	 */
	public static Animation[] createAnimations(int animationDuration, Sprite[][] sprites, boolean repeat)
	{
		Animation[] animations = new Animation[4];
		for (Orientation direction : Orientation.values()) {
			int index = direction.ordinal();
			animations[index] = new Animation(animationDuration, sprites[index], repeat);
		}
		return animations;
	}

	/**
	 * Creates an array of 4 animations (one animation per orientation)
	 * the entry indexed by Orientation.dir.ordinal() is the animation corresponding 
	 * to the orientation Orientation.dir. The animations are repeated by default.
	 * @param animationDuration (int): the animation duration
	 * @param sprites (Sprite[][]): sprites to be played by each animation
	 * sprites[Orientation.dir.ordinal()] is the set of sprites to be played by 
	 * the animation indexed by Orientation.dir.ordinal()
	 * @return an array of 4 animations (one animation per orientation)
	 */
	public static Animation[] createAnimations(int animationDuration, Sprite[][] sprites)
	{
		return createAnimations(animationDuration, sprites, true);
	}
}
