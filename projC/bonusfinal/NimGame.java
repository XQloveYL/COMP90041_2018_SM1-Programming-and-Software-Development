/**
 * This class is for the game.
 * @author Yang, Xiuqi
 */
public abstract class NimGame {
    private NimPlayer player1;
    private NimPlayer player2;

    /**
     * Set the first player of the game.
     * @param player an NimPlayer instance
     */
    void setPlayer1(NimPlayer player) {
        this.player1 = player;
    }

    /**
     * Set the second player of the game.
     * @param player an NimPlayer instance
     */
    void setPlayer2(NimPlayer player) {
        this.player2 = player;
    }

    /**
     * Get the player1
     * @return an NimPlayer
     */
    NimPlayer getPlayer1() {
        return player1;
    }

    /**
     * Get the player2
     * @return an NimPlayer
     */
    NimPlayer getPlayer2() {
        return player2;
    }

    /**
     * Determine the player for this turn.
     * @param round an int indicates the round number
     * @return an NimPlayer for this turn
     */
    NimPlayer determineTurnPlayer(int round) {
        return round % 2 == 1 ? player1 : player2;
    }

    /**
     * Determine the winner for the game.
     * @param round an int indicates the round number
     * @return an NimPlayer who is the winner
     */
    abstract NimPlayer determineWinPlayer(int round);

    /**
     * Print game over and announce the winner.
     * @param winPlayer the winner
     */
    void announceWinner(NimPlayer winPlayer) {
        System.out.println("Game Over");
        System.out.println(winPlayer.getGivenName() + " "
                + winPlayer.getFamilyName() + " wins!");
    }

    /**
     * Processing a game and return the winner
     * @return an NimPlayer who is the winner
     */
    abstract NimPlayer processGame();

}
