package ch.epfl.cs107.play.math.random;

public class RandomGenerator {
	private static java.util.Random instance;
	
	public static java.util.Random getInstance() {
		if(instance == null)
			instance = new java.util.Random();
		return instance;
	}
}
