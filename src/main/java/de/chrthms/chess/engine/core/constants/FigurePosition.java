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
 * Only for statistic analysis relevant
 * <p>
 * Normally a figure has the value REGARDLESS like the king, queen and the pawns. A knight, a bishop or a root exists
 * twice times for each color. One at the left and one at the right.
 * <p>
 * So this flag makes it possible to determine the desired figures of the player.
 *
 * @author Christian Thomas
 */
public abstract class FigurePosition {

    public static final int REGARDLESS = 0;
    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;

}
