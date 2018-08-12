import java.util.Random;

public class NimAIPlayer extends NimPlayer implements Testable {
    // a constructor with no parameters
	public NimAIPlayer() {}

	NimAIPlayer(String username, String familyName, String givenName) {
		super(username, familyName, givenName);
	}

	// return the number of stones to be removed
    public int removeStone(int currentStoneCount, int upperBound) {
		int numRemove = (currentStoneCount - 1) % (upperBound + 1); // victory guaranteed strategy
		if (OriginalNimGame.removeValidation(numRemove, upperBound, currentStoneCount)) {
			return numRemove;
		} else {
		    Random rand = new Random();
			return rand.nextInt(Math.min(upperBound, currentStoneCount)) + 1;
		}
    }

    /**
     * the implementation of the victory
     * guaranteed strategy
     * @param available
     * @param lastMove
     * @return
     */
    public String advancedMove(boolean[] available, String lastMove) {
        String move = "";

		if (lastMove.equals("")) { // the first move for the player move first
            move = moveMiddle(available); // move the middle stone(s)

        } else { // there is a lastMove
		    int lastMovePostion = Integer.parseInt(lastMove.split(" ")[0]);
		    int lastMoveNumber = Integer.parseInt(lastMove.split(" ")[1]);

		    // get the stones array before the last move
		    boolean[] previousAvailable =
                    getPreviousAvailable(available, lastMovePostion,lastMoveNumber);

            // if the previousAvailable is symmetric and its middle was removed
		    if (checkArraySymmetric(previousAvailable)) {
                move = moveSymmetrically(available, lastMovePostion, lastMoveNumber);
            }
        }
        return move;
	}

    /**
     *
     * @param array
     * @return a boolean, true for odd num, false for even num.
     */
    private boolean checkArrayOddNum(boolean[] array) {
        return array.length % 2 == 1;
    }

    /**
     * Check the array is symmetric or not, given the middle was removed
     * @param array
     * @return
     */
    private boolean checkArraySymmetric(boolean[] array) {
        if (checkArrayOddNum(array)) { // there are odd numbered elements in this array
            int middle = (array.length / 2) + 1;
            if (! array[Nimsys.numToIndex(middle)]) { // the middle was removed
                int count = 0;
                for (int i = 0; i < Nimsys.numToIndex(middle); i++) {
                    if (array[Nimsys.numToIndex(middle) - i]
                            == array[Nimsys.numToIndex(middle) + i]) {
                        count++;
                    }
                }
                return count == middle -1;
            }
        } else { // there are even numbered elements in this array
            int middle1 = (array.length / 2);
            int middle2 = (array.length / 2) + 1;
            if (! array[Nimsys.numToIndex(middle1)]
                    && ! array[Nimsys.numToIndex(middle2)]) { // the middle was removed
                int count = 0;
                for (int i = 0; i < Nimsys.numToIndex(middle1); i++) {
                    if (array[Nimsys.numToIndex(middle1) - i]
                            == array[Nimsys.numToIndex(middle2) + i]) {
                        count++;
                    }
                }
                return count == middle1 -1;
            }
        }
        return false;
    }

    private String formatMove(int position, int number) {
        return position + " " + number;
    }

    private boolean[] getPreviousAvailable(boolean[] available,
                                           int lastMovePostion, int lastMoveNumber) {
        boolean[] previousAvailable = new boolean[available.length];
        System.arraycopy(available, 0, previousAvailable, 0,available.length);
        previousAvailable[Nimsys.numToIndex(lastMovePostion)] = true;
        if (lastMoveNumber == 2) {
            previousAvailable[Nimsys.numToIndex(lastMovePostion) + 1] = true;
        }
        return previousAvailable;
    }

    private String moveSymmetrically(boolean[] available,
                                     int lastMovePostion, int lastMoveNumber) {
        int number;
        int position;
        if (lastMoveNumber == 1) { // the last move moved 1 stone
            number = 1;
            position = available.length - lastMovePostion + 1;
        } else { // the last move moved 2 stones
            number = 2;
            position = available.length - lastMovePostion;
        }
        return formatMove(position, number);
    }

    private String moveMiddle(boolean[] available) {
        int number;
        int position;
        if (checkArrayOddNum(available)) { // odd numbered stones
            position = (available.length / 2) + 1; // get the middle one
            number = 1;
        } else { // even numbered stones
            position = available.length / 2; // get the middle two
            number = 2;
        }
        return formatMove(position, number);
    }
}
