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

/**
 * Created by christian on 01.01.17.
 */
public class FigurePosition implements Serializable {

    private final int figureType;
    private final int colorType;
    private final String fieldCoord;

    public FigurePosition(int figureType, int colorType, String fieldCoord) {
        this.figureType = figureType;
        this.colorType = colorType;
        this.fieldCoord = fieldCoord;
    }

    public int getFigureType() {
        return figureType;
    }

    public int getColorType() {
        return colorType;
    }

    public String getFieldCoord() {
        return fieldCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FigurePosition that = (FigurePosition) o;

        if (figureType != that.figureType) return false;
        if (colorType != that.colorType) return false;
        return fieldCoord != null ? fieldCoord.equals(that.fieldCoord) : that.fieldCoord == null;
    }

    @Override
    public int hashCode() {
        int result = figureType;
        result = 31 * result + colorType;
        result = 31 * result + (fieldCoord != null ? fieldCoord.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FigurePosition{" +
                "figureType=" + figureType +
                ", colorType=" + colorType +
                ", fieldCoord='" + fieldCoord + '\'' +
                '}';
    }
}
