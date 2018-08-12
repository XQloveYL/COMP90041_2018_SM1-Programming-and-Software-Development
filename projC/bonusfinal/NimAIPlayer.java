import java.util.Random;

/**
 * Nim AI Player, derived from NimPlayer.
 * @author Yang, Xiuqi
 */
public class NimAIPlayer extends NimPlayer implements Testable {
    // the number of all possible moves in a 11-stones game
    private static final int MAX_NUM_POSSIBLE = 22;

    /**
     * a constructor with no parameters
     */
    public NimAIPlayer() {}

    /**
     * Constructor
     * @param username the username
     * @param familyName the family name
     * @param givenName the given name
     */
	NimAIPlayer(String username, String familyName, String givenName) {
		super(username, familyName, givenName);
	}

    /**
     * For original type Nim game.
     * Return the number of stones to be removed
     * @param currentStoneCount the current stones number
     * @param upperBound the upper bound
     * @return an int indicates the number to move
     */
    public int removeStone(int currentStoneCount, int upperBound) {
		int numRemove = (currentStoneCount - 1) % (upperBound + 1); // victory guaranteed strategy
		if (OriginalNimGame.removeValidation(numRemove, upperBound, currentStoneCount)) {
			return numRemove;
		} else {
            return generateRandomNum(Math.min(upperBound, currentStoneCount)) + 1;
		}
    }

    /**
     * For advanced type Nim game.
     * the implementation of the victory guaranteed strategy if move first
     * @param available an array represents the stones remained to be removed
     * @param lastMove a String represents the last move made by the rival player
     * @return a String indicates the move
     */
    public String advancedMove(boolean[] available, String lastMove) {
        String move;

        // Check the conditions and then move first strategy, victory guaranteed.
        move = movefirst(available, lastMove);

        // Check the conditions and then move second strategy.
        if (move == null) {
            move = moveSecond(available);
        }

        // If not satified with the above conditions, move random
        if (move == null) {
            move = moveRandom(available);
        }
        return move;
	}

    /**
     * Move strategy given the condition that move first.
     * @param available an array represents the stones remained to be removed
     * @param lastMove a String represents the last move made by the rival player
     * @return a String indicates the move, return null if the conditions not satisfied
     */
	private String movefirst(boolean[] available, String lastMove) {
        if (lastMove == null) { // the first move for the player move first
            return moveCurrentMiddle(available); // move the middle stone(s)
        } else { // there is a lastMove
            int lastMovePostion = Integer.parseInt(lastMove.split(" ")[0]);
            int lastMoveNumber = Integer.parseInt(lastMove.split(" ")[1]);

            // get the stones array before the last move
            boolean[] previousAvailable =
                    generatePreviousAvailable(available, lastMovePostion, lastMoveNumber);

            // if the previousAvailable is symmetric and its middle was removed
            if (checkSymmetric(previousAvailable)) {
                return moveSymmetrically(available, lastMovePostion, lastMoveNumber);
            }
        }
        return null;
    }

    /**
     * Move strategy given the condition that move second
     * @param available an array represents the stones remained to be removed
     * @return a String indicates the move, return null if the conditions not satisfied
     */
    private String moveSecond(boolean[] available) {
        if (maxAvailableContinue(available) <= 2) {
            if (groupCount(available) % 2 == 1) { // odd number groups of stones
                if (groupCount(available) == 1) { // only one group
                    return moveCurrentMiddle(available);
                } else if ((stoneCount(available) % groupCount(available) == 1)
                        || (stoneCount(available) % groupCount(available) == 0)) {
                    return moveTwoStonesGroup(available, 2); // move a two-stone group
                } else if (stoneCount(available) % groupCount(available) == 2) {
                    return moveOneStoneGroup(available); // move a one-stone group
                }
            } else { // even number groups of stones
                return moveTwoStonesGroup(available, 1); // move one in a two-stone group
            }
        } else {
            int i = 0;
            String thisMove;
            while (i < MAX_NUM_POSSIBLE) {
                thisMove = moveRandom(available);
                if (groupCount(generatePostAvailable(available, thisMove)) % 2 == 0) {
                    return thisMove;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * Find the stone number consisting the longest continue row
     * @param available an array represents the stones remained to be removed
     * @return an int for the max number
     */
    private int maxAvailableContinue(boolean[] available) {
        int max = 0;
        int count;
        for (int i = 0; i < available.length; i++) {
            if (available[i]) {
                count = 0;
                for (int j = i; j < available.length; j++) {
                    count++;
                    if (! available[j]) {
                        if (max < count) {
                            max = count;
                        }
                        break;
                    }
                }
            }
        }
        return max;
    }

    /**
     * Move the middle of the current remaining stones array.
     * @param available an array represents the stones remained to be removed
     * @return the move to move middle
     */
    private String moveCurrentMiddle(boolean[] available) {
        int number;
        int position;
        position = (findFirstStone(available) + findLastStone(available)) / 2;
        if (stoneCount(available) % 2 == 1) { // odd number stones left
            number = 1;
        } else { // even number stones left
            number = 2;
        }
        return formatMove(position, number);
    }

    /**
     * Find the first stone available to move
     * @param available an array represents the stones remained to be removed
     * @return an int indicates the position of the stone
     */
    private int findFirstStone(boolean[] available) {
        for (int i = 0; i < available.length; i++) {
            if (available[i]) {
                return indexToNum(i);
            }
        }
        return 0;
    }

    /**
     * Find the last stone available to move
     * @param available an array represents the stones remained to be removed
     * @return an int indicates the position of the stone
     */
    private int findLastStone(boolean[] available) {
        for (int i = numToIndex(available.length); i >= 0; i--) {
            if (available[i]) {
                return indexToNum(i);
            }
        }
        return 0;
    }

    /**
     * count how many stones remaining to move
     * @param available an array represents the stones remained to be removed
     * @return the remaining stone count
     */
    private int stoneCount(boolean[] available) {
        int count = 0;
        for (boolean stone: available) {
            if (stone) {
                count++;
            }
        }
        return count;
    }

    /**
     * Count how many groups of stones are there
     * @param available an array represents the stones remained to be removed
     * @return the groups number
     */
    private int groupCount(boolean[] available) {
        int count = 1;
        boolean moveable = false;
        for (boolean stone: available) {
            if (stone != moveable) {
                count++;
                moveable = stone;
            }
        }
        return (count / 2);
    }

    /**
     * Move a group only contains one stone
     * @param available an array represents the stones remained to be removed
     * @return the move
     */
    private String moveOneStoneGroup(boolean[] available) {
        int position;
        for (int i = 0; i < available.length; i++) {
            if ((i + 1) < available.length) {
                if (available[i] && ! available[i + 1]) {
                    position = indexToNum(i);
                    return formatMove(position, 1);
                }
            } else {
                if (available[i]) {
                    position = indexToNum(i);
                    return formatMove(position, 1);
                }
            }
        }
        return null;
    }

    // move 1 or 2 of a group exactly contains 2 stones
    /**
     * Move one or two stones of a group which exactly contains 2 stones
     * @param available an array represents the stones remained to be removed
     * @return the move
     */
    private String moveTwoStonesGroup(boolean[] available, int number) {
        int position;
        for (int i = 0; i < available.length; i++) {
            if ((i + 2) < available.length) {
                if (available[i] && available[i + 1] && ! available[i + 2]) {
                    position = indexToNum(i);
                    return formatMove(position, number);
                }
            } else if ((i + 1) < available.length) {
                if (available[i] && available[i + 1]) {
                    position = indexToNum(i);
                    return formatMove(position, number);
                }
            }
        }
        return null;
    }

    /**
     * Move randomly.
     * @param available an array represents the stones remained to be removed
     * @return the move
     */
    private String moveRandom(boolean[] available) {
        String move;
        int position;
        int number;
        position = generateRandomNum(available.length) + 1;
        number = generateRandomNum(2) + 1;
        move = formatMove(position, number);
        if (! removeValidation(move, available)) {
            return moveRandom(available);
        }
        return move;
    }

    /**
     * Generate a random number.
     * @param range the range of the number to generate.
     * @return a random number
     */
    private int generateRandomNum(int range) {
        Random rand = new Random();
        return rand.nextInt(range);
    }

    /**
     * Check the stone array is symmetric or not, and whether the middle was removed
     * @param available an array represents the stones remained to be removed
     * @return a boolean, True for it is symmetric and the middle was removed
     */
    private boolean checkSymmetric(boolean[] available) {
        if (available.length % 2 == 1) { // odd numbered total stones
            int middle = (available.length / 2) + 1;
            if (! available[numToIndex(middle)]) { // the middle was removed
                int count = 0;
                for (int i = 1; i < middle; i++) {
                    if (available[numToIndex(middle - i)]
                            == available[numToIndex(middle + i)]) {
                        count++;
                    }
                }
                return count == middle - 1;
            }
        } else { // even numbered available stones
            int middle1 = (available.length / 2);
            int middle2 = (available.length / 2) + 1;
            if (! available[numToIndex(middle1)]
                    && ! available[numToIndex(middle2)]) { // the middle was removed
                int count = 0;
                for (int i = 1; i < middle1; i++) {
                    if (available[numToIndex(middle1 - i)]
                            == available[numToIndex(middle2 + i)]) {
                        count++;
                    }
                }
                return count == middle1 - 1;
            }
        }
        return false;
    }

    /**
     * Format the move.
     * @param position an int for the stone position
     * @param number an int for the move number
     * @return a String represents the move action
     */
    private String formatMove(int position, int number) {
        return position + " " + number;
    }

    /**
     * Generate the previous available stones.
     * @param available an array represents the stones remained to be removed
     * @param lastMovePostion the stone postion for last move
     * @param lastMoveNumber the stone number for last move
     * @return an array represents the stones before the last move
     */
    private boolean[] generatePreviousAvailable(boolean[] available,
                                           int lastMovePostion, int lastMoveNumber) {
        boolean[] previousAvailable = new boolean[available.length];
        System.arraycopy(available, 0, previousAvailable, 0,available.length);
        previousAvailable[numToIndex(lastMovePostion)] = true;
        if (lastMoveNumber == 2) {
            previousAvailable[numToIndex(lastMovePostion + 1)] = true;
        }
        return previousAvailable;
    }

    /**
     * Generate the post available stones.
     * @param available an array represents the stones remained to be removed
     * @param move a String indicates this move
     * @return an array represents the stones after this move
     */
    private boolean[] generatePostAvailable(boolean[] available, String move) {
        boolean[] postAvailable = new boolean[available.length];
        int position = Integer.parseInt(move.split(" ")[0]);
        int number = Integer.parseInt(move.split(" ")[1]);
        System.arraycopy(available, 0, postAvailable, 0,available.length);
        postAvailable[numToIndex(position)] = false;
        if (number == 2) {
            postAvailable[numToIndex(position + 1)] = false;
        }
        return postAvailable;
    }

    /**
     * Move symmetrically to the rival player.
     * @param available an array represents the stones remained to be removed
     * @param lastMovePostion the stone postion for last move
     * @param lastMoveNumber the stone number for last move
     * @return the move
     */
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

    /**
     * Convert a number to index
     * @param number an int
     * @return an int indicates the index of an array
     */
    private int numToIndex(int number) {
        return number - 1;
    }

    /**
     * Convert an index to number
     * @param index an int indicates the index of an array
     * @return an int
     */
    private int indexToNum(int index) {
        return index + 1;
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
            if (! available[numToIndex(position)]) {
                return false;
            }
            if (number == 2) {
                if (! available[numToIndex(position) + 1]) {
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
}
