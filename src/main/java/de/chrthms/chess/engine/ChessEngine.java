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

import java.util.List;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.GameData;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

/**
 * This is the main interface. Use the ChessEngineBuilder to get a running instance of this interface.
 */
public interface ChessEngine {

    /**
     * Every component is stateless. So everytime the handle (the concrete game) is expected
     *
     * @return a stateful handle. It is possible to create multiple handles (parallel usage)
     * @throws ChessEngineException
     */
    Handle newGame() throws ChessEngineException;

    /**
     * @param gameData with serialized game information
     * @return
     * @throws ChessEngineException
     */
    Handle loadGame(GameData gameData) throws ChessEngineException;

    /**
     * @param handle
     * @return serialized game data object
     * @throws ChessEngineException
     */
    GameData saveGame(Handle handle) throws ChessEngineException;

    /**
     * @param handle
     * @param from
     * @param to
     * @return
     * @throws ChessEngineException (runtime exception) if the move coordinates are invalid. Also an appropriate state
     *                              is set in the MoveResult
     */
    MoveResult moveTo(Handle handle, Coord from, Coord to) throws ChessEngineException;

    /**
     * Only relevant for the pawn transformation procedure
     *
     * @param currentMoveResult
     * @param newFigureType
     * @return
     * @throws ChessEngineException
     */
    MoveResult newFigureDecision(MoveResult currentMoveResult, final int newFigureType) throws ChessEngineException;

    /**
     * @param handle
     * @param moveResult
     * @return the actual state in form of the magic constant GameState after that movement.
     * @throws ChessEngineException
     */
    int completeMoveTo(Handle handle, MoveResult moveResult) throws ChessEngineException;

    /**
     * @param handle
     * @param from
     * @return
     * @throws ChessEngineException
     */
    List<Coord> possibleMoves(Handle handle, Coord from) throws ChessEngineException;

}
