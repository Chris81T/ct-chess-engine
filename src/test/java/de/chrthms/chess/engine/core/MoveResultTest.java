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

package de.chrthms.chess.engine.core;

import de.chrthms.chess.engine.AbstractBeforeTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveResultTest extends AbstractBeforeTest {

    @Test
    public void testGetLastMoveResultFromMoveResult() {
        MoveResult moveResultNo1 = moveTo("g1", "f3");
        completeMove(moveResultNo1);
        MoveResult moveResultNo2 = moveTo("b7", "b5");
        completeMove(moveResultNo2);

        assertNull(moveResultNo1.getLastMoveResult());
        assertEquals(moveResultNo2.getLastMoveResult(), moveResultNo1);
    }

}
