package minesweeper.model;

import minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {

    private int rows;
    private int columns;
    private int mines;

    private final List<Field> fields = new ArrayList<>();

    public Board(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        associateNeighbors();
        distributeMines();
    }

    public void openField(int row, int column) {
        try {
            fields.parallelStream()
                    .filter(field -> field.getRow() == row && field.getColumn() == column)
                    .findFirst()
                    .ifPresent(field -> field.open());
        } catch (ExplosionException e) {
            fields.forEach(field -> field.setOpened(true));
            throw e;
        }
    }

    public void alternateMarkedUnmarkedField(int row, int column) {
        fields.parallelStream()
                .filter(field -> field.getRow() == row && field.getColumn() == column)
                .findFirst()
                .ifPresent(field -> field.alternateMarkedUnmarked());
    }

    private void generateFields() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                fields.add(new Field(r, c));
            }
        }
    }

    private void associateNeighbors() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addNeighbor(f2);
            }
        }
    }

    private void distributeMines() {
        long armedMines = 0;
        Predicate<Field> mined = f -> f.isMined();

        do {
            int aleatory = (int) (Math.random() * fields.size());
            fields.get(aleatory).putAMine();
            armedMines = fields.stream().filter(mined).count();
        } while (armedMines < mines);
    }

    public boolean goalAchieved() {
        return fields.stream().allMatch(field -> field.goalAchieved());
    }

    public void restart() {
        fields.stream().forEach(field -> field.restart());
        distributeMines();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        for (int c = 0; c < columns; c++) {
            sb.append(" ");
            sb.append(c);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int row = 0; row < rows; row++) {
            sb.append(row);
            sb.append(" ");
            for (int column = 0; column < columns; column++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
