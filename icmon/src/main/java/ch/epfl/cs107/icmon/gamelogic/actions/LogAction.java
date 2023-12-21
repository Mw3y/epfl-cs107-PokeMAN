package ch.epfl.cs107.icmon.gamelogic.actions;

public class LogAction implements Action {

    private final String message;

    /**
     * Constructor for LogAction
     * @param messageToLog (String)
     */
    public LogAction(String messageToLog) {
        assert messageToLog != null;
        message = messageToLog;
    }

    /**
     * Prints the given message.
     */
    @Override
    public void perform() {
        System.out.println(message);
    }
}
