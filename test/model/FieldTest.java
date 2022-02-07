package model;

import minesweeper.exception.ExplosionException;
import minesweeper.model.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    void startField() {
        field = new Field(3, 3);
    }

    @Test
    void leftNeighborDistance1Test() {
        Field neighbor = new Field(3, 2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void rightNeighborDistance1Test() {
        Field neighbor = new Field(3, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void upNeighborDistance1UpTest() {
        Field neighbor = new Field(2, 3);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void downNeighborDistance1Test() {
        Field neighbor = new Field(4, 3);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void diagonalUpAndLeftNeighborDistance2Test() {
        Field neighbor = new Field(2, 2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void diagonalUpAndRightNeighborDistance2Test() {
        Field neighbor = new Field(2, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void diagonalDownAndLeftNeighborDistance2Test() {
        Field neighbor = new Field(4, 2);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void diagonalDownAndRightNeighborDistance2Test() {
        Field neighbor = new Field(4, 4);
        boolean result = field.addNeighbor(neighbor);
        assertTrue(result);
    }

    @Test
    void notNeighborTest() {
        Field neighbor = new Field(1, 1);
        boolean result = field.addNeighbor(neighbor);
        assertFalse(result);
    }

    @Test
    void defaultValueMarkedAttributeTest() {
        assertFalse(field.isMarked());
    }

    @Test
    void alternateMarkedUnmarkedTest() {
        field.alternateMarkedUnmarked();
        assertTrue(field.isMarked());
    }

    @Test
    void alternateMarkedUnmarkedTwoCallsTest() {
        field.alternateMarkedUnmarked();
        field.alternateMarkedUnmarked();
        assertFalse(field.isMarked());
    }

    @Test
    void openNoMinedUnmarkedTest() {
        assertTrue(field.open());
    }

    @Test
    void openNoMinedMarkedTest() {
        field.alternateMarkedUnmarked();
        assertFalse(field.open());
    }

    @Test
    void openMinedMarkedTest() {
        field.alternateMarkedUnmarked();
        field.putAMine();
        assertFalse(field.open());
    }

    @Test
    void openMinedUnmarkedTest() {
        field.putAMine();
        assertThrows(ExplosionException.class, () -> {
            field.open();
        });
    }

    @Test
    void openWithNeighbors1() {
        Field neighborFromNeighbor1 = new Field(1, 1);
        Field neighbor1 = new Field(2, 2);

        neighbor1.addNeighbor(neighborFromNeighbor1);

        field.addNeighbor(neighbor1);
        field.open();

        assertTrue(neighbor1.isOpened() && neighborFromNeighbor1.isOpened());
    }

    @Test
    void openWithNeighbors2() {
        Field neighbor11 = new Field(1, 1);
        Field neighbor22 = new Field(2, 2);
        Field neighbor12 = new Field(1, 2);
        neighbor12.putAMine();

        neighbor22.addNeighbor(neighbor11);
        neighbor22.addNeighbor(neighbor12);

        field.addNeighbor(neighbor22);
        field.open();

        assertTrue(neighbor22.isOpened() && neighbor11.isClosed());
    }

    @Test
    void goalSecuredTest() {
        field.putAMine();
        field.alternateMarkedUnmarked();
        assertTrue(field.goalAchieved());
    }

    @Test
    void goalUncoveredTest() {
        field.open();
        assertTrue(field.goalAchieved());
    }

    @Test
    void numberOfMinesInTheNeighborhood1() {
        Field field22 = new Field(2, 2);
        Field field23 = new Field(2, 3);
        Field field24 = new Field(2, 4);

        Field field32 = new Field(3, 2);
        Field field34 = new Field(3, 4);

        Field field42 = new Field(4, 2);
        Field field43 = new Field(4, 3);
        Field field44 = new Field(4, 4);

        field22.putAMine();
        field23.putAMine();
        field24.putAMine();
        field32.putAMine();
        field34.putAMine();
        field42.putAMine();
        field43.putAMine();
        field44.putAMine();

        field.addNeighbor(field22);
        field.addNeighbor(field23);
        field.addNeighbor(field24);
        field.addNeighbor(field32);
        field.addNeighbor(field34);
        field.addNeighbor(field42);
        field.addNeighbor(field43);
        field.addNeighbor(field44);

        field.open();

        assertEquals(8, field.minesInTheNeighborhood());
    }

    @Test
    void numberOfMinesInTheNeighborhood2() {
        Field field22 = new Field(2, 2);
        Field field23 = new Field(2, 3);
        Field field24 = new Field(2, 4);

        Field field32 = new Field(3, 2);
        Field field34 = new Field(3, 4);

        field22.putAMine();
        field23.putAMine();
        field24.putAMine();

        field.addNeighbor(field22);
        field.addNeighbor(field23);
        field.addNeighbor(field24);
        field.addNeighbor(field32);
        field.addNeighbor(field34);

        field32.addNeighbor(field);
        field32.addNeighbor(field23);
        field32.addNeighbor(field22);

        field34.addNeighbor(field);
        field34.addNeighbor(field23);
        field34.addNeighbor(field24);

        field.open();
        field32.open();
        field34.open();

        assertEquals(3, field.minesInTheNeighborhood());
        assertEquals(2, field32.minesInTheNeighborhood());
        assertEquals(2, field34.minesInTheNeighborhood());
    }

    @Test
    void restartTest1() {
        field.putAMine();
        field.alternateMarkedUnmarked();
        field.restart();

        assertFalse(field.isMined());
        assertFalse(field.isMarked());
    }

    @Test
    void restartTest2() {
        field.open();
        field.restart();

        assertFalse(field.isOpened());
    }

    @Test
    void toStringTest() {
        field.alternateMarkedUnmarked();
        assertEquals("x", field.toString());

        field.alternateMarkedUnmarked();
        field.putAMine();
        try {
            field.open();
        } catch (Exception e) {
            assertEquals("*", field.toString());
        }

        field.restart();
        assertEquals("?", field.toString());

        field.open();
        assertEquals(" ", field.toString());
    }
}
