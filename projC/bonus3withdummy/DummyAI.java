public class DummyAI extends NimAIPlayer {

    DummyAI(String username, String familyName, String givenName) {
        super(username, familyName, givenName);
    }

    public String advancedMove(boolean[] available, String lastMove) {

        return moveRandom(available);
    }
}
