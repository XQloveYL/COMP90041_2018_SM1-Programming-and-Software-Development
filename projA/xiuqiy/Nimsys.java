/** @file
 * Authorship Statement:
 * @author Yang, Xiuqi
 * Student ID: 814072
 * @date: 29/03/2018
 */

import java.util.Scanner;

/**
 * This class is for the system of the Nim game.
 * @author Yang, Xiuqi
 */
public class Nimsys {
    // Make the keyboard static for other class to use it.
    static Scanner keyboard = new Scanner(System.in);

    NimPlayer player1;
    NimPlayer player2;

    /**
     * Print welcome sentence.
     */
    private void printWelcome() {
        System.out.println("Welcome to Nim");
        System.out.println();
    }

    /**
     * Create two instance objects of NimPlayer class.
     */
    private void createPlayers() {
        System.out.println("Please enter Player 1's name:");
        player1 = new NimPlayer();
        System.out.println();
        System.out.println("Please enter Player 2's name:");
        player2 = new NimPlayer();
        System.out.println();
    }

    /**
     * Set the upper bound number of remove stones.
     * @return an int indicates upper bound
     */
    private int setUpperBound() {
        System.out.println("Please enter upper bound of stone removal:");
        int upperBound = keyboard.nextInt();
        System.out.println();
        return upperBound;
    }

    /**
     * Set the initial number of stones.
     * @return an int indicates the number of stones
     */
    private int setStonesNum() {
        System.out.println("Please enter initial number of stones:");
        int stones = keyboard.nextInt();
        System.out.println();
        return stones;
    }

    /**
     * Print out the remaining stones
     * @param stones an int indicates the number of stones
     */
    private void printStones(int stones) {
        System.out.printf("%d stones left:", stones);
        // For loop to print stones as the star symbol
        for (int i = stones; i > 0; i--) {
            System.out.print(" *");
        }
        System.out.println();
    }

    /**
     * Get the player for this turn
     * @param round an int indicates the round number
     * @return an NimPlayer for this turn
     */
    private NimPlayer getTurnPlayer(int round) {
        NimPlayer turnPlayer = round % 2 == 1 ? player1 : player2;
        return turnPlayer;
    }

    /**
     * Get the winner for the game.
     * @param round an int indicates the round number
     * @return an NimPlayer who is the winner
     */
    private NimPlayer getWinPlayer(int round) {
        NimPlayer winPlayer = round % 2 == 1 ? player2 : player1;
        return winPlayer;
    }

    /**
     * Process the removing of stones.
     * @param stones an int indicates stones number
     * @param upperBound an int indicates upper bound
     * @param turnPlayer an NimPlayer indicates player for this turn
     * @return an int indicates the stones left
     */
    private int stonesRemoving(int stones, int upperBound, NimPlayer turnPlayer) {
        System.out.println(turnPlayer.getName() + "'s turn - remove how many?");
        int numRemove = turnPlayer.removeStone();
        System.out.println();

        if (numRemove >= 1 && numRemove <= upperBound && numRemove <= stones) {
            stones = stones - numRemove;
        }
        return stones;
    }

    /**
     * Update whether want to play again
     * @param again a string indicates yes or no
     * @return a string indicates yes or no
     */
    private String againOrNot(String again) {
        System.out.print("Do you want to play again (Y/N):");
        // To get rid of rest of line.
        String junk = keyboard.nextLine();
        again = keyboard.nextLine();
        return again;
    }

    /**
     * Print game over and announce the winner.
     * @param round an int indicates the round number
     */
    private void printWinner(int round) {
        NimPlayer winPlayer = getWinPlayer(round);
        System.out.println("Game Over");
        System.out.println(winPlayer.getName() + " wins!");
        System.out.println();
    }

    public static void main(String[] args) {
        Nimsys game = new Nimsys();

        game.printWelcome();
        game.createPlayers();

        // Initiate a string variable indicates yes to play again.
        String again = "Y";

        // Execute at least one time.
        do {
            int upperBound = game.setUpperBound();
            int stones = game.setStonesNum();
            int round = 0;

            while (stones > 0) {
                round++;
                game.printStones(stones);
                NimPlayer turnPlayer = game.getTurnPlayer(round);
                stones = game.stonesRemoving(stones, upperBound, turnPlayer);
            }

            game.printWinner(round);

            again = game.againOrNot(again);
            if (again.equals("Y")) {
                System.out.println();
            }
        } while (again.equals("Y"));
        keyboard.close(); // Close the scanner.
    }
}
