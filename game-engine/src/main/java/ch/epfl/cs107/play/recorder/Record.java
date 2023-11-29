package ch.epfl.cs107.play.recorder;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.recorder.recordEntry.RecordEntry;

public class Record implements java.io.Serializable{
	private static final long serialVersionUID = 1;
	private List<RecordEntry> entries = new ArrayList<>();
	private long randomSeed;

	public void addEntry(RecordEntry entry) {
		entries.add(entry);
	}
	
	public RecordEntry getEntry(int index) {
		if(index < 0 || index >= entries.size()) return null;
		return entries.get(index);
	}

	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	public long getRandomSeed() {
		return randomSeed;
	}
}
