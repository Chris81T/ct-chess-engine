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

package de.chrthms.chess.engine.core.figures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public class QueenTest extends AbstractBeforeTest {

    @Test
    public void testMove() {
        moveToAndComplete("e2", "e3");
        moveToAndComplete("b7", "b5");
        MoveResult result = moveTo("d1", "h5");
        assertMovedFigure(result, "h5");
    }

    @Test
    public void testMoveAndHit() {
        moveToAndComplete("e2", "e3");
        moveToAndComplete("g7", "g5");
        moveToAndComplete("d1", "g4");
        moveToAndComplete("d7", "d6");
        MoveResult result = moveTo("g4", "c8");
        assertMovedFigure(result, "c8");
        assertHitFigure(result);
    }

    @Test
    public void testInvalidMove() {
        exception.expect(ChessEngineException.class);
        moveTo("d1", "d6");
    }

}
