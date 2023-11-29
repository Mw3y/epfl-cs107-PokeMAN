package ch.epfl.cs107.play.io;


public class ResourcePath {

	private static final String SPRITE = "images/sprites/";
	private static final String BEHAVIORS = "images/behaviors/";
	private static final String BACKGROUNDS = "images/backgrounds/";
	private static final String FOREGROUNDS = "images/foregrounds/";
	private static final String SOUNDS = "sounds/";
	private static final String STRINGS = "strings/";
    public static final String DIALOGS = "dialogs/";
	public static final String FONTS = "fonts/";

	private static final String IMAGE_EXTENSION = ".png";
	private static final String SOUNDS_EXTENSION = ".wav";
	private static final String XML_EXTENSION = ".xml";

    private static String format(String path, String name, String ext){
        return String.format("%s%s%s", path, name, ext);
    }

	public static String getSprite(String name){
        return format(SPRITE, name, IMAGE_EXTENSION);
	}

	public static String getBehavior(String name){
        return format(BEHAVIORS, name, IMAGE_EXTENSION);
	}

	public static String getBackground(String name){
        return format(BACKGROUNDS, name, IMAGE_EXTENSION);
	}

	public static String getForeground(String name){
        return format(FOREGROUNDS, name, IMAGE_EXTENSION);
	}

	public static String getSound(String name){
        return format(SOUNDS, name, SOUNDS_EXTENSION);
	}

	public static String getString(String name){
        return format(STRINGS, name, XML_EXTENSION);
	}

    public static String getDialog(String name){
        return format(DIALOGS, name, XML_EXTENSION);
    }

}
