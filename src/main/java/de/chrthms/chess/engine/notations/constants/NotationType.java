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

package de.chrthms.chess.engine.notations.constants;

/**
 * SAN:
 * required by FIDE
 * <p>
 * LAN:
 * Some computer programs (and people) use a variant of algebraic chess notation termed long algebraic notation or fully expanded
 * algebraic notation. In long algebraic notation, moves specify both the starting and ending squares separated by a hyphen,
 * for example: e2-e4 or Nb1-c3. Captures are still indicated using "x": Rd3xd7.
 * <p>
 * The long notation takes more space and thus is not as commonly used. However, it has the advantage of clarity, particularly
 * for less-skilled players or players learning the game. Some books using primarily short algebraic notation use the long notation
 * instead of the disambiguation forms described earlier.
 * <p>
 * A form of long algebraic notation (without hyphens) is also notably used by the Universal Chess Interface (UCI) standard,
 * which is a common way for graphical chess programs to communicate with chess engines (e.g. for AI).
 *
 * @author Christian Thomas
 */
public abstract class NotationType {

    public final static int SAN = 0;
    public final static int LAN = 1;

}
