class AdvancedNimGame extends NimGame {
    private int initialStones;
    private String lastMove;
    private boolean[] available;

    // Only a single AdvancedNimGame instance is required at any time by Nimsys.
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

    void setInitialStones(int initialStones) {
        this.initialStones = initialStones;
    }

    void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    void setAvailable(boolean[] available) {
        this.available = available;
    }

    NimPlayer determineWinPlayer(int round) {
        return round % 2 == 1 ? super.getPlayer1() : super.getPlayer2();
    }

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

    private void stonesRemoveProcess(NimPlayer turnPlayer) {
        String move = askForMove(turnPlayer);
        stonesRemove(move);
    }

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

    private int currentStoneCount() {
        int count = 0;
        for (boolean stone: available) {
            if (stone) {
                count++;
            }
        }
        return count;
    }

    static boolean removeValidation(String move, boolean[] available) {
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
