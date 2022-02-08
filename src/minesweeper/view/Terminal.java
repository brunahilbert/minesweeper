package minesweeper.view;

import minesweeper.exception.ExplosionException;
import minesweeper.exception.LeaveException;
import minesweeper.model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Terminal {

    private Board board;
    private Scanner entry = new Scanner(System.in);

    public Terminal(Board board) {
        this.board = board;

        runGame();
    }

    private void runGame() {
        try {
            boolean toContinue = true;

            while (toContinue) {
                gameCycle();

                System.out.println("Do you want to start a new game? (Y/N) ");
                String answer = entry.nextLine();

                if ("n".equalsIgnoreCase(answer)) {
                    toContinue = false;
                } else {
                    board.restart();
                }
            }
        } catch (LeaveException e) {
            System.out.println("Bye!");
        } finally {
            entry.close();
        }
    }

    private void gameCycle() {
        try {
            while (!board.goalAchieved()) {
                System.out.println(board);

                String typed = getValueTyped("Type (x, y): ");

                Iterator<Integer> xy = Arrays.stream(typed.split(","))
                        .map(e -> Integer.parseInt(e.trim())).iterator();

                typed = getValueTyped("1 - To open or 2 - To (Un)Marker: ");

                if ("1".equals(typed)) {
                    board.openField(xy.next(), xy.next());
                } else if ("2".equals(typed)) {
                    board.alternateMarkedUnmarkedField(xy.next(), xy.next());
                }
            }

            System.out.println(board);
            System.out.println("You won!");
        } catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Game over!");
        }
    }

    private String getValueTyped(String text) {
        System.out.print(text);
        String typed = entry.nextLine();

        if ("leave".equalsIgnoreCase(typed)) {
            throw new LeaveException();
        }
        return typed;
    }
}
