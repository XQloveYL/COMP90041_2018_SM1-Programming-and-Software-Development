/**
 * Advanced Nim Game, derived from NimGame.
 * @author Yang, Xiuqi
 */
public class AdvancedNimGame extends NimGame {
    private int initialStones;
    private String lastMove;
    private boolean[] available;

    /**
     * Only a single AdvancedNimGame instance is required at any time by Nimsys.
     */
    private static AdvancedNimGame game = new AdvancedNimGame();

    /**
     * Make the constructor private.
     */
    private AdvancedNimGame() {}

    /**
     * Get the instance of AdvancedNimGame.
     * @return a AdvancedNimGame instance
     */
    static AdvancedNimGame getInstance() {
        return game;
    }

    /**
     * Set the initial stones number.
     * @param initialStones an int indicates the number
     */
    void setInitialStones(int initialStones) {
        this.initialStones = initialStones;
    }

    /**
     * Set the last move.
     * @param lastMove the String for the last move
     */
    void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Set the array for the stones.
     * @param available an array represents the stones remained to be removed
     */
    void setAvailable(boolean[] available) {
        this.available = available;
    }

    /**
     * Determine the winner for the game.
     * @param round an int indicates the round number
     * @return an NimPlayer who is the winner
     */
    NimPlayer determineWinPlayer(int round) {
        return round % 2 == 1 ? super.getPlayer1() : super.getPlayer2();
    }

    /**
     * Processing an advanced-type game and return the winner
     * @return an NimPlayer who is the winner
     */
    NimPlayer processGame() {
        int round = 0;

        for (int i = 0; i < available.length; i++) {
            available[i] = true;
        }

        while (currentStoneCount() > 0) {
            round++;
            NimPlayer turnPlayer = determineTurnPlayer(round);
            stonesRemoveProcess(turnPlayer);
        }

        NimPlayer winPlayer = determineWinPlayer(round);
        announceWinner(winPlayer);

        return winPlayer;
    }

    /**
     * Process the removing of stones.
     * @param turnPlayer an NimPlayer indicates player for this turn
     */
    private void stonesRemoveProcess(NimPlayer turnPlayer) {
        String move = askForMove(turnPlayer);
        stonesRemove(move);
    }

    /**
     * Ask player for the move action.
     * @param turnPlayer an NimPlayer who move the stone
     * @return an int indicates the move action
     */
    private String askForMove(NimPlayer turnPlayer) {
        printStones();
        System.out.println(turnPlayer.getGivenName() + "'s turn - which to remove?");
        String move = turnPlayer.advancedMove(available, lastMove);
        System.out.println();
        if (! removeValidation(move, available)) {
            System.out.println("Invalid move.");
            System.out.println();
            move = askForMove(turnPlayer);
        }
        return move;
    }

    /**
     * Print out the array of the stones.
     */
    private void printStones() {
        System.out.printf("%d stones left:", currentStoneCount());
        for (int i = 1; i <= initialStones; i++) {
            if (available[Nimsys.numToIndex(i)]) {
                System.out.printf(" <%d,*>", i);
            } else {
                System.out.printf(" <%d,x>", i);
            }
        }
        System.out.println();
    }

    /**
     * Count the current stones.
     * @return the number of the stone
     */
    private int currentStoneCount() {
        int count = 0;
        for (boolean stone: available) {
            if (stone) {
                count++;
            }
        }
        return count;
    }

    /**
     * Determine whether the move is valid or not.
     * @param move the move
     * @param available an array represents the stones remained to be removed
     * @return a boolean, True for the move is valid, False for not
     */
    private boolean removeValidation(String move, boolean[] available) {
        String[] stringSplit = move.split(" ");
        int position = Integer.parseInt(stringSplit[0]);
        int number = Integer.parseInt(stringSplit[1]);
        try {
            if (! available[Nimsys.numToIndex(position)]) {
                return false;
            }
            if (number == 2) {
                if (! available[Nimsys.numToIndex(position) + 1]) {
                    return false;
                }
            } else if (number > 2) {
                return false;
            }
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

    /**
     * Remove the stones declared by the move String.
     * @param move a String represents the move action.
     */
    private void stonesRemove(String move) {
        String[] stringSplit = move.split(" ");
        int position = Integer.parseInt(stringSplit[0]);
        int number = Integer.parseInt(stringSplit[1]);
        available[Nimsys.numToIndex(position)] = false;
        if (number == 2) {
            available[Nimsys.numToIndex(position) + 1] = false;
        }
        lastMove = move;
    }

}
