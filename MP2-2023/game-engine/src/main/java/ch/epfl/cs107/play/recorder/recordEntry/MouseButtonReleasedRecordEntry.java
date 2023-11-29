package ch.epfl.cs107.play.recorder.recordEntry;

import java.awt.Robot;
import java.awt.event.InputEvent;

import ch.epfl.cs107.play.window.Window;

public class MouseButtonReleasedRecordEntry extends RecordEntry{
	private static final long serialVersionUID = 1;
	private int keycode;
	
	public MouseButtonReleasedRecordEntry(long time, int keycode) {
		super(time);
		this.keycode = keycode;
	}

	@Override
	public void replay(Robot robot, Window window) {
		if(keycode == 0)
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		else if(keycode == 1)
			robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
		else if(keycode == 2)
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
	}
}
