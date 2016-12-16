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

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class HandleTest extends AbstractBeforeTest {

    @Test
    public void testNoGameOver() {
        assertFalse(handle.isGameOver());
    }

    @Test
    public void testInvalidHandleCreation() {
        Handle handle = new Handle();
        exception.expect(ChessEngineException.class);
        exception.expectMessage("the engine should create the handle via new or load a game");
        chessEngine.moveTo(handle, new Coord("e2"), new Coord("e3"));
    }

}
