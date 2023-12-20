package ch.epfl.cs107.icmon.gamelogic.actions;

public class LogAction implements Action {

    private final String message;

    public LogAction(String messageToLog) {
        assert messageToLog != null;
        message = messageToLog;
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
}
