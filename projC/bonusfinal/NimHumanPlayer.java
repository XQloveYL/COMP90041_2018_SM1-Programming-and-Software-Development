/**
 * Nim Human Player, derived from NimPlayer.
 * @author Yang, Xiuqi
 */
public class NimHumanPlayer extends NimPlayer {

    /**
     * Constructor
     * @param username the username
     * @param familyName the family name
     * @param givenName the given name
     */
    NimHumanPlayer(String username, String familyName, String givenName) {
        super(username, familyName, givenName);
    }

    /**
     * For original type Nim game.
     * Player choose the number of stones to remove.
     * @param currentStoneCount the current stones number
     * @param upperBound the upper bound
     * @return an int indicates the number to move
     * @throws InvalidMoveException for invalid move
     */
    public int removeStone(int currentStoneCount, int upperBound)
            throws InvalidMoveException {
        int numRemove;
        try {
            numRemove = Integer.parseInt(Nimsys.keyboard.nextLine());
        }
        catch (NumberFormatException e) {
            throw new InvalidMoveException();
        }
        return numRemove;
    }

    /**
     * For advanced type Nim game.
     * Player choose which stone(s) to remove.
     * @param available an array represents the stones remained to be removed
     * @param lastMove a String represents the last move made by the rival player
     * @return a String indicates the move
     */
    public String advancedMove(boolean[] available, String lastMove) {
        return Nimsys.keyboard.nextLine();
    }
}
