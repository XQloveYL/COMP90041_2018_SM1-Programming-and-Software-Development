/**
 * Authorship Statement:
 * @author Yang, Xiuqi
 * Student ID: 814072
 * @date: 30/04/2018
 */

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;

/**
 * This class is for the system of the Nim.
 * @author Yang, Xiuqi
 */
public class Nimsys {
    // Make the keyboard static for other class to use it.
    public static Scanner keyboard = new Scanner(System.in);

    // "y" indicates yes.
    private static final String YES = "y";

    // The maximum number of players can be set as 100.
    private static final int MAX_NUM_OF_PLAYERS = 100;
    private NimPlayer[] playerArray = new NimPlayer[MAX_NUM_OF_PLAYERS];

    // "desc" means descending.
    private static final String DESC = "desc";
    // "asc" means ascending.
    private static final String ASC = "asc";

    /**
     * Get the input command.
     * @return the input string
     */
    private String getCommand() {
        System.out.println();
        System.out.print("$");
        return keyboard.nextLine();
    }

    /**
     * Identify the command, call corresponding method.
     * @param command the input string
     */
    private void readCommand(String command) {
        String[] stringSplit = command.split(" ");
        String keyword = stringSplit[0];
        if (keyword.equals("addplayer")) {
            addPlayer(stringSplit[1]);
        } else if (keyword.equals("exit")) {
            exit();
        } else if (keyword.equals("removeplayer")) {
            if (stringSplit.length == 1) {
                removePlayer();
            } else {
                removePlayer(stringSplit[1]);
            }
        } else if (keyword.equals("editplayer")) {
            editPlayer(stringSplit[1]);
        } else if (keyword.equals("resetstats")) {
            if (stringSplit.length == 1) {
                resetStats();
            } else {
                resetStats(stringSplit[1]);
            }
        } else if (keyword.equals("displayplayer")) {
            if (stringSplit.length == 1) {
                displayPlayer();
            } else {
                displayPlayer(stringSplit[1]);
            }
        } else if (keyword.equals("startgame")) {
            startGame(stringSplit[1]);
        } else if (keyword.equals("rankings")) {
            if (stringSplit.length == 1) {
                rankings(DESC);
            } else {
                rankings(stringSplit[1]);
            }
        }
    }

    /**
     * Find a player in the playerArray, and return the index.
     * @param username a string indicates the username
     * @return the index of the player if found it, otherwise return "-1" indicates not found.
     */
    private int findPlayer(String username) {
        for (int index = 0; index < NimPlayer.count; index++) {
            NimPlayer player = playerArray[index];

            if (player.getUsername().equals((username))) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Add a player to the player array.
     * @param nameString a string for the name
     */
    private void addPlayer(String nameString) {
        String[] stringSplit = nameString.split(",");

        String username = stringSplit[0];
        String familyName = stringSplit[1];
        String givenName = stringSplit[2];

        if (findPlayer(username) != -1) {
            System.out.println("The player already exists.");
        } else {
            if (NimPlayer.count < MAX_NUM_OF_PLAYERS) {
                playerArray[NimPlayer.count] = new NimPlayer(username, familyName, givenName);
                NimPlayer.count++;
            }
        }
    }

    /**
     * Remove all the player in the player array.
     */
    private void removePlayer() {
        System.out.println("Are you sure you want to remove all players? (y/n)");
        String removeOrNot = keyboard.nextLine();

        if (removeOrNot.equals(YES)) {
            playerArray = new NimPlayer[100];
            NimPlayer.count = 0;
        }
    }

    /**
     * Remove a player.
     * @param username the username of a player
     */
    private void removePlayer(String username) {
        int index = findPlayer(username);

        if (index != -1) {
            for (int i = index; i < NimPlayer.count - 1; i++)
            playerArray[i] = playerArray[i + 1];
            playerArray[NimPlayer.count - 1] = null;
            NimPlayer.count--;
        } else {
            System.out.println("The player does not exist.");
        }
    }

    /**
     * Edit a player.
     * @param nameString a string for the name
     */
    private void editPlayer(String nameString) {
        String[] stringSplit = nameString.split(",");

        String username = stringSplit[0];
        String newFamilyName = stringSplit[1];
        String newGivenName = stringSplit[2];

        int index = findPlayer(username);
        if (index != -1) {
            NimPlayer player = playerArray[index];
            player.setFamilyName(newFamilyName);
            player.setGivenName(newGivenName);
        } else {
            System.out.println("The player does not exist.");
        }
    }

    /**
     * Reset statics for all the players.
     */
    private void resetStats() {
        System.out.println("Are you sure you want to reset all player statistics? (y/n)");
        String resetOrNot = keyboard.nextLine();

        if (resetOrNot.equals(YES)) {
            for (NimPlayer player: playerArray) {
                if (player != null) {
                    player.setNumGamePlayed(0);
                    player.setNumGameWon(0);
                    player.updateWinRatio();
                }
            }
        }
    }

    /**
     * Reset the statics of a player.
     * @param username the username of the player
     */
    private void resetStats(String username) {
        int index = findPlayer(username);

        if (index != -1) {
            NimPlayer player = playerArray[index];
            player.setNumGamePlayed(0);
            player.setNumGameWon(0);
            player.updateWinRatio();
        } else {
            System.out.println("The player does not exist.");
        }
    }

    /**
     * Sort the player array by the username in ascending order.
     */
    private void sortByUsernameAsc() {
        Arrays.sort(playerArray, 0, NimPlayer.count,
                Comparator.comparing(NimPlayer::getUsername));
    }

    /**
     * Display all the player information.
     */
    private void displayPlayer() {
        sortByUsernameAsc();

        for (NimPlayer player: playerArray) {
            if (player != null) {
                printPlayerInfo(player);
            }
        }
    }

    /**
     * Display a player's information.
     * @param username the username of the player
     */
    private void displayPlayer(String username) {
        int index = findPlayer(username);

        if (index != -1) {
            printPlayerInfo(playerArray[index]);
        } else {
            System.out.println("The player does not exist.");
        }
    }

    /**
     * Print out the information of a player.
     * @param player the player
     */
    private void printPlayerInfo(NimPlayer player) {
        System.out.printf("%s,%s,%s,%d games,%d wins",
                player.getUsername(),
                player.getGivenName(),
                player.getFamilyName(),
                player.getNumGamePlayed(),
                player.getNumGameWon());
        System.out.println();
    }

    /**
     * Format a double to the percentage.
     * @param d the double to be formatted
     * @param integerDigits the maximum of the integer digits
     * @param fractionDigits the maximum of the fraction digits
     * @return a string in percentage format
     */
    private String doubleToPercentformat(double d, int integerDigits, int fractionDigits) {
        NumberFormat numberFormat = java.text.NumberFormat.getPercentInstance();
        numberFormat.setMaximumIntegerDigits(integerDigits);
        numberFormat.setMaximumFractionDigits(fractionDigits);
        String str = numberFormat.format(d);
        return str;
    }

    /**
     * Format the win ratio to the percentage.
     * @param winRatio a double indicates the win ratio
     * @return the win ratio in percentage format
     */
    private String winRatioPercentformat(double winRatio) {
        return doubleToPercentformat(winRatio, 3, 0);
    }

    /**
     * Display the rank.
     */
    private void displayRank() {
        for (int i = 0; i < Math.min(10, NimPlayer.count); i++) {
            NimPlayer player = playerArray[i];
            System.out.printf("%-4s | %02d games | %s %s",
                    winRatioPercentformat(player.getWinRatio()),
                    player.getNumGamePlayed(),
                    player.getGivenName(),
                    player.getFamilyName());
            System.out.println();
        }
    }

    /**
     * Sort the player array by win ratio in descending order.
     * Ties be resolved by sorting on usernames alphabetically.
     */
    private void sortByWinRatioDesc() {
        Arrays.sort(playerArray, 0, NimPlayer.count,
                Collections.reverseOrder(Comparator.comparing(NimPlayer::getWinRatio))
                        .thenComparing(NimPlayer::getUsername));
    }

    /**
     * Sort the player array by win ratio in ascending order.
     * Ties be resolved by sorting on usernames alphabetically.
     */
    private void sortByWinRatioAsc() {
        Arrays.sort(playerArray, 0, NimPlayer.count,
                Comparator.comparing(NimPlayer::getWinRatio)
                        .thenComparing(NimPlayer::getUsername));
    }

    /**
     * Display the rank by the declared order.
     * @param sortOder a string indicates the sort order
     */
    private void rankings(String sortOder) {
        if (sortOder.equals(DESC)) {
            sortByWinRatioDesc();
            displayRank();
        } else if (sortOder.equals(ASC)) {
            sortByWinRatioAsc();
            displayRank();
        }
    }

    /**
     * Print out the game information.
     * @param initialStone the initial stones number
     * @param upperBound the upper bound stones number to remove
     * @param player1 the first player
     * @param player2 the second player
     */
    private void printGameInfo(int initialStone, int upperBound,
                               NimPlayer player1, NimPlayer player2) {
        System.out.println();
        System.out.println("Initial stone count: " + initialStone);
        System.out.println("Maximum stone removal: " + upperBound);
        System.out.println("Player 1: " + player1.getGivenName()
                + " " + player1.getFamilyName());
        System.out.println("Player 2: " + player2.getGivenName() +
                " " + player2.getFamilyName());
        System.out.println();
    }

    /**
     * Start the game.
     * @param gameInfo the information of a game
     */
    private void startGame(String gameInfo) {
        String[] stringSplit = gameInfo.split(",");

        String username1 = stringSplit[2];
        String username2 = stringSplit[3];

        int indexPlayer1 = findPlayer(username1);
        int indexPlayer2 = findPlayer(username2);

        if (indexPlayer1 != -1 && indexPlayer2 != -1) {
            NimGame game = NimGame.getInstance();

            int currentStoneCount = Integer.parseInt(stringSplit[0]);
            int upperBound = Integer.parseInt(stringSplit[1]);
            NimPlayer player1 = playerArray[indexPlayer1];
            NimPlayer player2 = playerArray[indexPlayer2];

            game.setCurrentStoneCount(currentStoneCount);
            game.setUpperBound(upperBound);
            game.setPlayer1(player1);
            game.setPlayer2(player2);

            printGameInfo(currentStoneCount, upperBound, player1, player2);

            game.processGame();
        } else {
            System.out.println("One of the players does not exist.");
        }
    }

    /**
     * Exit the Nimsys program
     */
    private void exit() {
        keyboard.close(); // Close the scanner.
        System.out.println();
        System.exit(0);
    }

    /**
     * Process a Nimsys.
     */
    public void processSys() {
        System.out.println("Welcome to Nim");

        for (;;) {
            this.readCommand(this.getCommand());
        }
    }

    public static void main(String[] args) {
        Nimsys sys = new Nimsys();

        sys.processSys();
    }
}
