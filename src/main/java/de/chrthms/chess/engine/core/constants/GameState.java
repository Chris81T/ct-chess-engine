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

package de.chrthms.chess.engine.core.constants;

/**
 * Value description:
 * <p>
 * NORMAL    = game is running normally
 * CHECK     = king is checked (game is running)
 * CHECKMATE = king is checkmate (game over)
 * DEADLOCK  = King is not checked, but no figure can be moved (game over)
 * DRAWN     = player have drawn the match (manual decision) (game over)
 *
 * @author Christian Thomas
 */
public abstract class GameState extends AbstractType {

    public static final int NORMAL = 0;
    public static final int CHECK = 1;
    public static final int CHECKMATE = 2;
    public static final int DEADLOCK = 3;
    public static final int DRAWN = 4;

    public static boolean isGameOver(final int gameState) {
        return gameState == CHECKMATE ||
                gameState == DEADLOCK ||
                gameState == DRAWN;
    }

}
