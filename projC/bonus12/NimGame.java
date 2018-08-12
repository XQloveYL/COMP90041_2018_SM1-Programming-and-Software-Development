/**
 * This class is for the game.
 */
abstract class NimGame {
    NimPlayer player1;
    NimPlayer player2;

    /**
     * Set the first player of the game.
     * @param player a NimPlayer instance
     */
    void setPlayer1(NimPlayer player) {
        this.player1 = player;
    }

    /**
     * Set the second player of the game.
     * @param player a NimPlayer instance
     */
    void setPlayer2(NimPlayer player) {
        this.player2 = player;
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

    abstract NimPlayer processGame();

}
