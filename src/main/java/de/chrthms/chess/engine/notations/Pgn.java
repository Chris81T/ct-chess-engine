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

package de.chrthms.chess.engine.notations;

import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;

public interface Pgn {

    /**
     *
     * @param handle
     * @param notationType
     * @return
     */
    String getPgn(Handle handle, int notationType);

    /**
     *
     * @param handle
     * @return
     */
    String getPgnHeaderOnly(Handle handle);

    /**
     *
     * @param handle
     * @param notationType
     * @return
     */
    String getPgnMovesOnly(Handle handle, int notationType);

    /**
     *
     * @param handle
     * @param notationType
     * @return
     */
    String getPgnForLastMove(Handle handle, int notationType);

    /**
     *
     * @param handle
     * @param whiteMove
     * @param blackMove (optional) if blackmove is null, only the halfmove for white will be generated.
     * @param notationType
     * @return
     */
    String getPgnForMove(Handle handle, MoveResult whiteMove, MoveResult blackMove, int notationType);

    /**
     *
     * @param handle
     * @param moveResult
     * @param notationType
     * @return
     */
    String getPgnForHalfmove(Handle handle, MoveResult moveResult, int notationType);

}
