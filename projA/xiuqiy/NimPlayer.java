/**
 * This class is for player.
 * @author Yang, Xiuqi
 */
public class NimPlayer {
    private String name;

    /**
     * Set the name when create a new object.
     */
    NimPlayer() {
        this.name = Nimsys.keyboard.nextLine();
    }

    /**
     * Get the name of the object.
     * @return a string indicates the name of the object
     */
    public String getName() {
        return name;
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
