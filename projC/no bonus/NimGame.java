/**
 * This class is for the game.
 */
public class NimGame {
    // Only a single NimGame instance is required at any time by Nimsys.
    private static NimGame game = new NimGame();

    private NimPlayer player1;
    private NimPlayer player2;
    private int currentStoneCount;
    private int upperBound;

    /**
     * Make the constructor private.
     */
    private NimGame() {};

    /**
     * Get the instance of NimGame.
     * @return a NimGame instance
     */
    public static NimGame getInstance() {
        return game;
    }

    /**
     * Set the current stone count.
     * @param currentStoneCount an int indicates the count of current stone
     */
    public void setCurrentStoneCount(int currentStoneCount) {
        this.currentStoneCount = currentStoneCount;
    }

    /**
     * Set the upper bound of the number to remove.
     * @param upperBound an int indicates the remove upper bound
     */
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    /**
     * Set the first player of the game.
     * @param player a NimPlayer instance
     */
    public void setPlayer1(NimPlayer player) {
        this.player1 = player;
    }

    /**
     * Set the second player of the game.
     * @param player a NimPlayer instance
     */
    public void setPlayer2(NimPlayer player) {
        this.player2 = player;
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
     * Determine the player for this turn.
     * @param round an int indicates the round number
     * @return an NimPlayer for this turn
     */
    private NimPlayer determineTurnPlayer(int round) {
        return round % 2 == 1 ? player1 : player2;
    }

    /**
     * Determine the winner for the game.
     * @param round an int indicates the round number
     * @return an NimPlayer who is the winner
     */
    private NimPlayer determineWinPlayer(int round) {
        return round % 2 == 1 ? player2 : player1;
    }

    /**
     * Process the removing of stones.
     * @param turnPlayer a NimPlayer indicates player for this turn
     */
    private void stonesRemoveProcess(NimPlayer turnPlayer) {
        int numRemove = askForRemoveNum(turnPlayer);
        stonesRemove(numRemove);
    }

    /**
     * Determine whether the remove number is valid or not.
     * @param numRemove the number of stones to remove
     * @param upperBound
     * @param currentStoneCount
     * @return a boolean, true for the remove is valid, false for invalid.
     */
    public static boolean removeValidation(int numRemove,
                                           int upperBound, int currentStoneCount) {
        return (numRemove >= 1 && numRemove <= Math.min(upperBound, currentStoneCount));
    }

    /**
     * Ask player for the remove number.
     * @param turnPlayer a Nimplayer who move the stone
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
     * Print game over and announce the winner.
     * @param winPlayer the winner
     */
    private void announceWinner(NimPlayer winPlayer) {
        System.out.println("Game Over");
        System.out.println(winPlayer.getGivenName() + " "
                + winPlayer.getFamilyName() + " wins!");
    }

    /**
     * Process a game.
     */
    public NimPlayer processGame() {
        int round = 0;
        NimPlayer turnPlayer = determineTurnPlayer(round);

        while (currentStoneCount > 0) {
            round++;
            turnPlayer = determineTurnPlayer(round);
            stonesRemoveProcess(turnPlayer);
        }

        NimPlayer winPlayer = determineWinPlayer(round);
        announceWinner(winPlayer);

        return winPlayer;
    }
}
