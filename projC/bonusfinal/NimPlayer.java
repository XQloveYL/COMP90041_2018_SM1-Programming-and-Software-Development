import java.io.Serializable;

/**
 * This class is for player.
 * @author Yang, Xiuqi
 */
public abstract class NimPlayer implements Serializable {
    private String username;
    private String givenName;
    private String familyName;
    private int numGamePlayed;
    private int numGameWon;
    private double winRatio;

    /**
     * Constructor with parameter
     */
    NimPlayer() {}

    /**
     * Constructor
     * @param username a string indicates the username
     * @param familyName a string indicates the family name
     * @param givenName a string indicates the given name
     */
    NimPlayer(String username, String familyName, String givenName) {
        this.username = username;
        this.familyName = familyName;
        this.givenName = givenName;
    }

    /**
     * Get the username of the object.
     * @return a string indicates the username
     */
    String getUsername() {
        return username;
    }

    /**
     * Get the family name of the object.
     * @return a string indicates the familyname
     */
    String getFamilyName() {
        return familyName;
    }

    /**
     * Get the given name of the object.
     * @return a string indicates the given name
     */
    String getGivenName() {
        return givenName;
    }

    /**
     * Get the number of games played of the object.
     * @return an int indicates the number of games played
     */
    int getNumGamePlayed() {
        return numGamePlayed;
    }

    /**
     * Get the number of games won of the object.
     * @return an int indicates the number of games won
     */
    int getNumGameWon() {
        return numGameWon;
    }

    /**
     * Get the win ratio of the object.
     * @return a double indicates the win ratio
     */
    double getWinRatio() {
        return winRatio;
    }

    /**
     * Set the family name.
     * @param familyName a string indicates the family name
     */
    void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Set the given name.
     * @param givenName a string indicates the given name
     */
    void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Set the number of games played.
     * @param numGamePlayed an int indicates the number of games played
     */
    void setNumGamePlayed(int numGamePlayed) {
        this.numGamePlayed = numGamePlayed;
    }

    /**
     * Set the number of games won.
     * @param numGameWon an int indicates the number of games won
     */
    void setNumGameWon(int numGameWon) {
        this.numGameWon = numGameWon;
    }

    /**
     * Update the win ratio of the object.
     */
    void updateWinRatio() {
        if (numGamePlayed == 0) {
            winRatio = 0;
        } else {
           winRatio = numGameWon / (double) numGamePlayed;
        }
    }

    /**
     * For original type Nim game.
     * Player choose the number of stones to remove.
     * @param currentStoneCount the current stones number
     * @param upperBound the upper bound
     * @return an int indicates the number to move
     * @throws InvalidMoveException for invalid move
     */
    public abstract int removeStone(int currentStoneCount, int upperBound)
            throws InvalidMoveException;

    /**
     * For advanced type Nim game.
     * Player choose which stone(s) to remove.
     * @param available an array represents the stones remained to be removed
     * @param lastMove a String represents the last move made by the rival player
     * @return a String indicates the move
     */
    public abstract String advancedMove(boolean[] available, String lastMove);

}
