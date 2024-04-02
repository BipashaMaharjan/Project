package BIM2;

import java.io.*;
import java.util.*;

public class MemoryGame1 {

    private static final String fis = "nccs.txt";

    private static String[] board;
    private static boolean[] flipped;
    private static ArrayList<String> cards;

    public static void main(String[] args) {
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        // Check if there is a saved state
        boolean loadSuccessful = loadGameState();

        if (!loadSuccessful) {
            initializeGame();
        }

        int pairsFound = 0;
        int chances = 4;

        while (chances > 0 && pairsFound < 4) {
            System.out.println(" Chances: " + chances);
            System.out.println("Current Board: ");
            printBoard(board);
            int firstIndex = getCardIndex(
                    scanner, board, flipped,
                    "Enter index of first card to flip:");
            board[firstIndex] = cards.get(firstIndex);
            flipped[firstIndex] = true;
            printBoard(board);
            int secondIndex = getCardIndex(
                    scanner, board, flipped,
                    "Enter index of second card to flip:");
            board[secondIndex] = cards.get(secondIndex);
            flipped[secondIndex] = true;

            if (cards.get(firstIndex)
                    .equals(cards.get(secondIndex))) {
                System.out.println("You found a pair!");
                pairsFound++;
                score += 2;

            } else {
                System.out.println("Sorry, those cards don't match.");
                board[firstIndex] = " ";
                board[secondIndex] = " ";
                chances--;
                flipped[firstIndex] = false;
                flipped[secondIndex] = false;

            }
        }
        if (pairsFound == 4) {
            System.out.println("Congratulations! You have won the game!");
        } else {
            System.out.println("Game over. You have run out of chances.");
        }
        System.out.println("Score: " + score);

        // Save the final state of the game
        saveGameState(score);
    }

    public static int getCardIndex(Scanner scanner, String[] board, boolean[] flipped, String prompt) {
        int index;
        while (true) {
            System.out.println(prompt);
            index = scanner.nextInt();
            if (index < 0 || index >= board.length) {
                System.out.println(
                        "Invalid index, try again.");
            } else if (flipped[index]) {
                System.out.println(
                        "Card already flipped, try again.");
            } else {
                break;
            }
        }
        return index;
    }

    public static void printBoard(String[] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("| " + board[i] + " ");
        }
        System.out.println("|");
    }

    private static void initializeGame() {
        cards = new ArrayList<>();
        cards.add("A");
        cards.add("A");
        cards.add("B");
        cards.add("B");
        cards.add("C");
        cards.add("C");
        cards.add("D");
        cards.add("D");
        Collections.shuffle(cards);

        board = new String[cards.size()];
        flipped = new boolean[cards.size()];

        saveGameState(0); // Save initial state with score 0
    }

    private static boolean loadGameState() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fis))) {
            String line;
            int lineNumber = 0;

            cards = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                switch (lineNumber) {
                    case 0:
                        String[] shuffledCards = line.split(",");
                        cards.addAll(Arrays.asList(shuffledCards));
                        break;
                    case 1:
                        String[] savedBoard = line.split(",");
                        System.arraycopy(savedBoard, 0, board, 0, savedBoard.length);
                        break;
                    case 2:
                        String[] savedFlipped = line.split(",");
                        for (int i = 0; i < savedFlipped.length; i++) {
                            flipped[i] = Boolean.parseBoolean(savedFlipped[i]);
                        }
                        break;
                    case 3:
                        int savedScore = Integer.parseInt(line);
                        break;
                }
                lineNumber++;
            }
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting a new game.");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void saveGameState(int score) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fis))) {
            writer.println(String.join(",", cards));
            writer.println(String.join(",", board));
            writer.println(Arrays.toString(flipped).replace("[", "").replace("]", "").replace(" ", ""));
            writer.println(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
