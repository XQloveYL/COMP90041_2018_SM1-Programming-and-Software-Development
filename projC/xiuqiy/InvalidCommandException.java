/**
 * Invalid Command Exception, derived from Exception.
 * @author Yang, Xiuqi
 */
class InvalidCommandException extends Exception {
    private String invalidCommand;

    /**
     * Constructor
     * @param command a String for command
     */
    InvalidCommandException(String command) {
        super();
        invalidCommand = command;
    }

    /**
     * Get the invalid command
     * @return a String for the invalid command
     */
    String getInvalidCommand() {
        return invalidCommand;
    }
}
