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

import org.junit.Test;

import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

/**
 * possible moves and performMoveTo are tested in the ChessEngineTest. Check the code.
 *
 * @author Christian Thomas
 */
public class LogicTest extends AbstractBeforeTest {

    @Test
    public void testIsCheckmate() {
        moveToAndComplete("g2", "g4");
        moveToAndComplete("e7", "e5");
        moveToAndComplete("f2", "f4");
        assertTrue(moveToAndComplete("d8", "h4") == GameState.CHECKMATE);
        dumpBoard("testIsCheckmate");
    }

    @Test
    public void testIsChecked() {
        moveToAndComplete("e2", "e3");
        moveToAndComplete("d7", "d5");
        assertTrue(moveToAndComplete("f1", "b5") == GameState.CHECK);
        dumpBoard("testIsChecked");
    }

    @Test
    public void testGetLastMoveResult() {
        MoveResult moveResult = moveTo("g1", "f3");
        completeMove(moveResult);
        MoveResult lastMoveResult = logic.getLastMoveResult(handle);
        assertEquals(moveResult, lastMoveResult);
    }

}
