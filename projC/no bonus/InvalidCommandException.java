public class InvalidCommandException extends Exception {
    private String invalidCommand;

    public InvalidCommandException(String command) {
        super();
        invalidCommand = command;
    }

    public String getInvalidCommand() {
        return invalidCommand;
    }
}
