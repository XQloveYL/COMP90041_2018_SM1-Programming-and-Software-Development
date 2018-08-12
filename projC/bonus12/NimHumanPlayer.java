public class NimHumanPlayer extends NimPlayer {

    NimHumanPlayer(String username, String familyName, String givenName) {
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

    public String advancedMove(boolean[] available, String lastMove) {
        String move = Nimsys.keyboard.nextLine();
        return move;
    }
}
