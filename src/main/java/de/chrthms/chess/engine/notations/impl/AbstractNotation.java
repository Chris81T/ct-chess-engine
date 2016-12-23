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

package de.chrthms.chess.engine.notations.impl;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public abstract class AbstractNotation {

    protected final Board board;
    protected final Logic logic;

    public AbstractNotation() {
        board = ChessEngineBuilder.getBoard();
        logic = ChessEngineBuilder.getLogic();
    }

    /**
     * the advantage of an integer division is here in use:
     * 1 / 2 = 0
     * 2 / 2 = 1
     * 3 / 2 = 1
     * ...
     */
    protected int generateFullmoveCount(int halfmoveCount) {
        return halfmoveCount / 2 + 1;
    }

}
