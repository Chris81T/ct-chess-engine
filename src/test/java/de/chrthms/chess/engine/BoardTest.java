/*
 *    ct-chess-engine, a chess engine playing and evaluating chess moves.
 *    Copyright (C) 2016-2017 Christian Thomas
 *
 *    This program ct-chess-engine is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.chrthms.chess.engine;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;

public class BoardTest extends AbstractBeforeTest {

    @Test
    public void testFieldCreation() {
        dumpBoard("testFieldCreation");
        assertTrue(handle.getFields().size() == 64);

    }

    @Test
    public void testGetField() {
        final Coord coord = new Coord("c5");
        Field field = board.getField(handle, coord);
        assertTrue(field.getCoord().getStrCoord().equals(coord.getStrCoord()));
    }

    @Test
    public void testGetFieldNumeric() {
        final String coord = "c5";
        Field field = board.getField(handle, 3, 5);
        assertTrue(field.getCoord().getStrCoord().equals(coord));
    }

    @Test
    public void testGetFieldNumericInvalid() {
        assertNull(board.getField(handle, 0, 0));
        assertNull(board.getField(handle, 0, 1));
        assertNull(board.getField(handle, 0, 8));
        assertNull(board.getField(handle, 0, 9));
        assertNull(board.getField(handle, 1, 0));
        assertNull(board.getField(handle, 8, 0));
        assertNull(board.getField(handle, 9, 0));
        assertNull(board.getField(handle, 9, 1));
        assertNull(board.getField(handle, 9, 8));
        assertNull(board.getField(handle, 9, 9));
        assertNull(board.getField(handle, 1, 9));
        assertNull(board.getField(handle, 8, 9));
    }

    @Test
    public void getOccupiedFields() {
        List<Field> occupiedFields = board.getOccupiedFields(handle, ColorType.WHITE);
        assertTrue(occupiedFields.size() == 16);
        for (Field field : occupiedFields) {
            AbstractFigure figure = field.getFigure();
            assertNotNull(figure);
            assertTrue(figure.getColorType() == ColorType.WHITE);
        }
    }

    @Test
    public void getEnemyOccupiedFields() {
        List<Field> occupiedFields = board.getEnemyOccupiedFields(handle, ColorType.WHITE);
        assertTrue(occupiedFields.size() == 16);
        for (Field field : occupiedFields) {
            AbstractFigure figure = field.getFigure();
            assertNotNull(figure);
            assertTrue(figure.getColorType() == ColorType.BLACK);
        }
    }

    @Test
    public void testGetWhiteKingsField() {
        Field kingsField = board.getKingsField(handle, ColorType.WHITE);
        AbstractFigure king = kingsField.getFigure();
        assertNotNull(king);
        assertTrue(king.getColorType() == ColorType.WHITE);
        assertTrue(king.getFigureType() == FigureType.KING);
    }

    @Test
    public void testGetBlackKingsField() {
        Field kingsField = board.getKingsField(handle, ColorType.BLACK);
        AbstractFigure king = kingsField.getFigure();
        assertNotNull(king);
        assertTrue(king.getColorType() == ColorType.BLACK);
        assertTrue(king.getFigureType() == FigureType.KING);
    }

    @Test
    public void testMoveFigure() {
        board.moveFigure(board.getField(handle, 5, 2), board.getField(handle, 5, 4));
        Field sourceField = board.getField(handle, 5, 2);
        Field destField = board.getField(handle, 5, 4);
        assertNull(sourceField.getFigure());
        AbstractFigure pawn = destField.getFigure();
        assertNotNull(pawn);
        assertTrue(pawn.getColorType() == ColorType.WHITE);
        assertTrue(pawn.getFigureType() == FigureType.PAWN);
        dumpBoard("testMoveFigure");
    }

    @Test
    public void testCreateNewFigure() {
        AbstractFigure blackQueen = board.createNewFigureTo(board.getField(handle, 6, 4), FigureType.QUEEN, ColorType.BLACK);
        assertEquals(blackQueen, board.getField(handle, 6, 4).getFigure());
    }

}
