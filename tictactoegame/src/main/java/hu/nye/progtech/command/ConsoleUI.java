package hu.nye.progtech.command;

import hu.nye.progtech.model.Position;
import hu.nye.progtech.util.PositionFormatter;

import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner;

    public ConsoleUI() {

        this.scanner = new Scanner(System.in);
    }

    public String getPlayerName() {

        System.out.print("Enter your name: ");
        return scanner.nextLine().trim();
    }

    public int getBoardRows() {
        while (true) {
            System.out.print("Enter number of rows (4-25): ");
            try {
                int rows = Integer.parseInt(scanner.nextLine().trim());
                if (rows >= 4 && rows <= 25) {
                    return rows;
                }
                System.out.println("Rows must be between 4 and 25.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public int getBoardCols() {
        while (true) {
            System.out.print("Enter number of rows (4-25): ");
            try {
                int cols = Integer.parseInt(scanner.nextLine().trim());
                if (cols >= 4 && cols <= 25) {
                    return cols;
                }
                System.out.println("Rows must be between 4 and 25.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public Position getPlayerMove() {
        while (true) {
            System.out.print("Enter your move (e.g., e5): ");
            String input = scanner.nextLine().trim().toLowerCase();

            Position position = PositionFormatter.parse(input);
            if (position != null) {
                return position;
            }

            System.out.println("Invalid input! Please use format like 'e5' (column letter and row number).");
        }
    }

    public void showMenu() {
        System.out.println();
        System.out.println("=== TICTACTOE GAME ===");
        System.out.println("1. New Game");
        System.out.println("2. Load Game");
        System.out.println("3. Exit");
        System.out.println();
    }

    public int getMenuChoice() {

        while(true) {
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= 3) {
                    return choice;
                }
                System.out.println("Please enter 1, 2, or 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}