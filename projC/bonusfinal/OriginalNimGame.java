/**
 * Original Nim Game, derived from NimGame.
 * @author Yang, Xiuqi
 */
public class OriginalNimGame extends NimGame {
    private int currentStoneCount;
    private int upperBound;

    /**
     * Only a single OriginalNimGame instance is required at any time by Nimsys.
     */
    private static OriginalNimGame game = new OriginalNimGame();

    /**
     * Make the constructor private.
     */
    private OriginalNimGame() {}

    /**
     * Get the instance of OriginalNimGame.
     * @return an OriginalNimGame instance
     */
    static OriginalNimGame getInstance() {
        return game;
    }

    /**
     * Set the current stone count.
     * @param currentStoneCount an int indicates the count of current stone
     */
    void setCurrentStoneCount(int currentStoneCount) {
        this.currentStoneCount = currentStoneCount;
    }

    /**
     * Set the upper bound of the number to remove.
     * @param upperBound an int indicates the remove upper bound
     */
    void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Print out the current stone count.
     */
    private void printStones() {
        System.out.printf("%d stones left:", currentStoneCount);
        // For loop to print stones as the star symbol
        for (int i = currentStoneCount; i > 0; i--) {
            System.out.print(" *");
        }
        System.out.println();
    }

    /**
     * Determine the winner for the game.
     * @param round an int indicates the round number
     * @return an NimPlayer who is the winner
     */
    NimPlayer determineWinPlayer(int round) {
        return round % 2 == 1 ? super.getPlayer2() : super.getPlayer1();
    }

    /**
     * Process the removing of stones.
     * @param turnPlayer an NimPlayer indicates player for this turn
     */
    private void stonesRemoveProcess(NimPlayer turnPlayer) {
        int numRemove = askForRemoveNum(turnPlayer);
        stonesRemove(numRemove);
    }

    /**
     * Determine whether the remove number is valid or not.
     * @param numRemove the number of stones to remove
     * @param upperBound the upper bound for remove
     * @param currentStoneCount the current stone count
     * @return a boolean, True for the remove is valid, False for not.
     */
    static boolean removeValidation(int numRemove,
                                    int upperBound, int currentStoneCount) {
        return (numRemove >= 1 && numRemove <= Math.min(upperBound, currentStoneCount));
    }

    /**
     * Ask player for the remove number.
     * @param turnPlayer an NimPlayer who move the stone
     * @return an int indicates the number to remove
     */
    private int askForRemoveNum(NimPlayer turnPlayer) {
        printStones();
        System.out.println(turnPlayer.getGivenName() + "'s turn - remove how many?");
        int numRemove;
        try {
            numRemove = turnPlayer.removeStone(currentStoneCount, upperBound);
            if (! removeValidation(numRemove, upperBound, currentStoneCount)) {
                throw new InvalidMoveException();
            }
            System.out.println();
        }
        catch (InvalidMoveException e) {
            System.out.println();
            System.out.printf("Invalid move. You must remove between 1 and %d stones.",
                    Math.min(upperBound, currentStoneCount));
            System.out.println();
            System.out.println();
            numRemove = askForRemoveNum(turnPlayer);
        }
        return numRemove;
    }

    /**
     * Deduct the number of removed stone from stone count.
     * @param numRemove an int indicates the number of stone removed
     */
    private void stonesRemove(int numRemove) {
        currentStoneCount = currentStoneCount - numRemove;
    }

    /**
     * Processing an original-type game and return the winner
     * @return an NimPlayer who is the winner
     */
    NimPlayer processGame() {
        int round = 0;

        while (currentStoneCount > 0) {
            round++;
            NimPlayer turnPlayer = determineTurnPlayer(round);
            stonesRemoveProcess(turnPlayer);
        }

        NimPlayer winPlayer = determineWinPlayer(round);
        announceWinner(winPlayer);

        return winPlayer;
    }

}
