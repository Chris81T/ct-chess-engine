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

/**
 * Here's the FEN for the starting position:
 * rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
 *
 * @author Christian Thomas
 */
public interface Fen {

    /**
     * Produces the full FEN string
     *
     * @param handle
     * @return the string concatenation of following methods
     */
    String getFen(Handle handle);

    /**
     * The first field represents the placement of the pieces on the board. The board contents are specified starting
     * with the eighth rank and ending with the first rank. For each rank, the squares are specified from file a to file h.
     * White pieces are identified by uppercase SAN piece letters ("PNBRQK") and black pieces are identified by lowercase SAN
     * piece letters ("pnbrqk"). Empty squares are represented by the digits one through eight; the digit used represents the
     * count of contiguous empty squares along a rank. A solidus character "/" is used to separate data of adjacent ranks.
     *
     * @param handle
     * @return
     */
    String getPiecePlacementData(Handle handle);

    /**
     * The second field represents the active color. A lower case "w" is used if White is to move; a lower case "b" is
     * used if Black is the active player.
     *
     * @param handle
     * @return
     */
    String getActiveColor(Handle handle);

    /**
     * The third field represents castling availability. This indicates potential future castling that may of may not be possible
     * at the moment due to blocking pieces or enemy attacks. If there is no castling availability for either side, the single
     * character symbol "-" is used. Otherwise, a combination of from one to four characters are present. If White has kingside
     * castling availability, the uppercase letter "K" appears. If White has queenside castling availability, the uppercase
     * letter "Q" appears. If Black has kingside castling availability, the lowercase letter "k" appears. If Black has
     * queenside castling availability, then the lowercase letter "q" appears. Those letters which appear will be ordered
     * first uppercase before lowercase and second kingside before queenside. There is no white space between the letters.
     *
     * @param handle
     * @return
     */
    String getCastlingAvailability(Handle handle);

    /**
     * The fourth field is the en passant target square. If there is no en passant target square then the single character
     * symbol "-" appears. If there is an en passant target square then is represented by a lowercase file character immediately
     * followed by a rank digit. Obviously, the rank digit will be "3" following a white pawn double advance
     * (Black is the active color) or else be the digit "6" after a black pawn double advance (White being the active color).
     * <p>
     * An en passant target square is given if and only if the last move was a pawn advance of two squares. Therefore, an en
     * passant target square field may have a square name even if there is no pawn of the opposing side that may immediately
     * execute the en passant capture.
     *
     * @param handle
     * @return
     */
    String getEnPassantTargetField(Handle handle);

    /**
     * The fifth field is a nonnegative integer representing the halfmove clock. This number is the count of halfmoves (or ply)
     * since the last pawn advance or capturing move. This value is used for the fifty move draw rule.
     *
     * @param handle
     * @return
     */
    int getHalfmoveClock(Handle handle);

    /**
     * The sixth and last field is a positive integer that gives the fullmove number. This will have the value "1" for the
     * first move of a game for both White and Black. It is incremented by one immediately after each move by Black.
     *
     * @param handle
     * @return
     */
    int getFullmoveNumber(Handle handle);

}
