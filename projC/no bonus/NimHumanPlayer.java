import java.util.InputMismatchException;

public class NimHumanPlayer extends NimPlayer {

    public NimHumanPlayer(String username, String familyName, String givenName) {
        super(username, familyName, givenName);
    }

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
}
