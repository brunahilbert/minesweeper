package minesweeper;

import minesweeper.model.Board;
import minesweeper.view.Terminal;

public class Application {

    public static void main(String[] args) {

        Board board = new Board(6, 6, 3);
        new Terminal(board);
    }
}
