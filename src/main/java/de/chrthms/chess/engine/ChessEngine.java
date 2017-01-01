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
import java.util.Map;

import de.chrthms.chess.engine.core.*;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

/**
 * This is the main interface. Use the ChessEngineBuilder to get a running instance of this interface.
 */
public interface ChessEngine {

    /**
     * Every component is stateless. So everytime the handle (the concrete game) is expected, when using the engine api.
     *
     * @return a stateful handle. It is possible to create multiple handles (parallel usage)
     * @throws ChessEngineException
     */
    Handle newGame() throws ChessEngineException;

    /**
     * If only the byte-array is available, create new GameData instance and set the byte-array as a member. With that
     * GameData instance the according handle can be created.
     *
     * @param gameData with serialized game information
     * @return
     * @throws ChessEngineException
     */
    Handle loadGame(GameData gameData) throws ChessEngineException;

    /**
     * If it is relevant to save the current state of the game, a GameState instance including a byte-array is given as
     * the result. The relevant content is the internal byte-array. That has to be stored. Of course the entire GameData
     * instance can be stored as well.
     *
     * @param handle
     * @return serialized game data object
     * @throws ChessEngineException
     */
    GameData saveGame(Handle handle) throws ChessEngineException;

    /**
     * Typically the possibleMoves method can provide all valid moves for e.g. validation purposes. After that this
     * method can be called to perform the move. Finally the completeMoveTo method must be performed to finish the
     * movement. The reason for this is the possible pawn transformation, if the pawn is reaching the other end of the
     * board.
     *
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
     * With the given MoveResult, when calling the method moveTo, the movement can be completed with this method. Check
     * the MoveResult. Possibly a decision is requested.
     *
     * @param handle
     * @param moveResult
     * @return the actual state in form of the magic constant GameState after that movement.
     * @throws ChessEngineException
     */
    int completeMoveTo(Handle handle, MoveResult moveResult) throws ChessEngineException;

    /**
     * This method gives a list of possible movements for the field coord, that contains a figure.
     *
     * @param handle
     * @param from
     * @return
     * @throws ChessEngineException
     */
    List<Coord> possibleMoves(Handle handle, Coord from) throws ChessEngineException;

    List<FigurePosition> getFigurePositions(Handle handle) throws  ChessEngineException;

}
