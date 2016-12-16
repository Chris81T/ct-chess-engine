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

import java.io.Serializable;
import java.util.Arrays;

/**
 * is relevant for saving or loading a game using the chess engine api.
 *
 * @author Christian Thomas
 */
public class GameData implements Serializable {

    private byte[] handle;

    public byte[] getHandle() {
        return handle;
    }

    public void setHandle(byte[] handle) {
        this.handle = handle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(handle);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameData other = (GameData) obj;
        if (!Arrays.equals(handle, other.handle))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GameData [handle (length)=" + handle.length + "]";
    }

}
