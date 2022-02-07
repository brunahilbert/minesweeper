package minesweeper.model;

import minesweeper.exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean opened = false;
    private boolean mined = false;
    private boolean marked = false;

    private List<Field> neighbors = new ArrayList<>();

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean addNeighbor(Field neighbor) {
        boolean differentRow = row != neighbor.row;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentRow && differentColumn;

        int deltaRow = Math.abs(row - neighbor.row);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaGeneral = deltaRow + deltaColumn;

        if (deltaGeneral == 1 && !diagonal) {
            neighbors.add(neighbor);
            return true;
        } else if (deltaGeneral == 2 && diagonal) {
            neighbors.add(neighbor);
            return true;
        } else {
            return false;
        }
    }

    public void alternateMarkedUnmarked() {
        if (!opened) {
            marked = !marked;
        }
    }

    public boolean open() {
        if (!opened && !marked) {
            opened = true;

            if (mined) {
                throw new ExplosionException();
            }
            if (safeNeighborhood()) {
                neighbors.forEach(n -> n.open());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean safeNeighborhood() {
        return neighbors.stream().noneMatch(n -> n.mined);
    }

    public void putAMine() {
        mined = true;
    }

    public boolean isMarked() {
        return marked;
    }

    void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isClosed() {
        return !isOpened();
    }

    public boolean isMined() {
        return mined;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean goalAchieved() {
        boolean uncovered = !mined && opened;
        boolean secured = mined && marked;
        return uncovered || secured;
    }

    public long minesInTheNeighborhood() {
        return neighbors.stream().filter(n -> n.mined).count();
    }

    public void restart() {
        opened = false;
        mined = false;
        marked = false;
    }

    public String toString() {
        if (marked) {
            return "x";
        } else if (opened && mined) {
            return "*";
        } else if (opened && minesInTheNeighborhood() > 0) {
            return Long.toString(minesInTheNeighborhood());
        } else if (opened) {
            return " ";
        } else {
            return "?";
        }
    }
}

