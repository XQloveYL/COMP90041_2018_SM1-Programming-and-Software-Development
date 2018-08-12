import java.util.Random;

public class NimAIPlayer extends NimPlayer implements Testable {
	// you may further extend a class or implement an interface
	// to accomplish the tasks.

	// a constructor with no parameters
	public NimAIPlayer() {}

	public NimAIPlayer(String username, String familyName, String givenName) {
		super(username, familyName, givenName);
	}

	// return the number of stones to be removed
    public int removeStone(int currentStoneCount, int upperBound) {
		int numRemove = (currentStoneCount - 1) % (upperBound + 1); // victory guaranteed strategy
		if (NimGame.removeValidation(numRemove, upperBound, currentStoneCount)) {
			return numRemove;
		} else {
		    Random rand = new Random();
		    int randNum = rand.nextInt(Math.min(upperBound, currentStoneCount))+1;
			return randNum;
		}
    }

	public String advancedMove(boolean[] available, String lastMove) {
		// the implementation of the victory
		// guaranteed strategy designed by you
		String move = "";
		
		return move;
	}
}
