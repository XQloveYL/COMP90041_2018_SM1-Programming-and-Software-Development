/**
 * This class is for player.
 * @author Yang, Xiuqi
 */
public class NimPlayer {
    // static variable, to record the count of instance objects of NimPlayer
    public static int count = 0;

    private String username;
    private String givenName;
    private String familyName;
    private int numGamePlayed;
    private int numGameWon;
    private double winRatio;

    /**
     * Constructor
     * @param username a string indicates the username
     * @param familyName a string indicates the family name
     * @param givenName a string indicates the given name
     */
    public NimPlayer(String username, String familyName, String givenName) {
        this.username = username;
        this.familyName = familyName;
        this.givenName = givenName;
    }

    /**
     * Get the username of the object.
     * @return a string indicates the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the family name of the object.
     * @return a string indicates the familyname
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Get the given name of the object.
     * @return a string indicates the given name
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Get the number of games played of the object.
     * @return an int indicates the number of games played
     */
    public int getNumGamePlayed() {
        return numGamePlayed;
    }

    /**
     * Get the number of games won of the object.
     * @return an int indicates the number of games won
     */
    public int getNumGameWon() {
        return numGameWon;
    }

    /**
     * Get the win ratio of the object.
     * @return a double indicates the win ratio
     */
    public double getWinRatio() {
        return winRatio;
    }

    /**
     * Set the family name.
     * @param familyName a string indicates the family name
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * Set the given name.
     * @param givenName a string indicates the given name
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * Set the number of games played.
     * @param numGamePlayed an int indicates the number of games played
     */
    public void setNumGamePlayed(int numGamePlayed) {
        this.numGamePlayed = numGamePlayed;
    }

    /**
     * Set the number of games won.
     * @param numGameWon an int indicates the number of games won
     */
    public void setNumGameWon(int numGameWon) {
        this.numGameWon = numGameWon;
    }

    /**
     * Update the win ratio of the object.
     */
    public void updateWinRatio() {
        if (numGamePlayed == 0) {
            winRatio = 0;
        } else {
           winRatio = numGameWon / numGamePlayed;
        }
    }

    /**
     * Player choose the number of stones to remove.
     * @return an int indicates the number of stones to remove
     */
    public int removeStone() {
        int numRemove = Nimsys.keyboard.nextInt();
        return numRemove;
    }
}
