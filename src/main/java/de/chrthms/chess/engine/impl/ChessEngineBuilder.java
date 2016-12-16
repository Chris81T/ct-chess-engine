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

package de.chrthms.chess.engine.impl;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class ChessEngineBuilder {

    private static ChessEngine chessEngine = null;
    private static Board board = null;
    private static Logic logic = null;

    private static void buildComponents() throws ChessEngineException {
        board = new BoardImpl();
        logic = new LogicImpl(board);
        chessEngine = new ChessEngineImpl(board, logic);
    }

    private static void checkAvailableEngine() throws ChessEngineException {
        if (chessEngine == null)
            throw new ChessEngineException("Could not perform operation. Actually no engine built!");
    }

    public static ChessEngine build() throws ChessEngineException {
        if (chessEngine == null) buildComponents();
        return chessEngine;
    }

    public static Board getBoard() throws ChessEngineException {
        checkAvailableEngine();
        return board;
    }

    public static Logic getLogic() throws ChessEngineException {
        checkAvailableEngine();
        return logic;
    }

}
