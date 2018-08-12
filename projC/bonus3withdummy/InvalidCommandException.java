class InvalidCommandException extends Exception {
    private String invalidCommand;

    InvalidCommandException(String command) {
        super();
        invalidCommand = command;
    }

    String getInvalidCommand() {
        return invalidCommand;
    }
}
