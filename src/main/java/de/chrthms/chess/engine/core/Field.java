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

import de.chrthms.chess.engine.core.figures.AbstractFigure;

public class Field implements Serializable {

    private final int fieldType;

    private final Coord coord;

    private AbstractFigure figure = null;

    public Field(int fieldType, Coord coord) {
        this.fieldType = fieldType;
        this.coord = coord;
    }

    public Field(int fieldType, Coord coord, AbstractFigure figure) {
        this.fieldType = fieldType;
        this.coord = coord;
        this.figure = figure;
    }

    public AbstractFigure getFigure() {
        return figure;
    }

    public void setFigure(AbstractFigure figure) {
        this.figure = figure;
    }

    public void clearFigure() {
        setFigure(null);
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean isEmpty() {
        return figure == null;
    }

    public boolean isOccupied() {
        return !isEmpty();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coord == null) ? 0 : coord.hashCode());
        result = prime * result + fieldType;
        result = prime * result + ((figure == null) ? 0 : figure.hashCode());
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
        Field other = (Field) obj;
        if (coord == null) {
            if (other.coord != null)
                return false;
        } else if (!coord.equals(other.coord))
            return false;
        if (fieldType != other.fieldType)
            return false;
        if (figure == null) {
            if (other.figure != null)
                return false;
        } else if (!figure.equals(other.figure))
            return false;
        return true;
    }

    public String toBoardString() {
        return figure == null ? "[ ]" : "[" + figure.toBoardString() + "]";
    }

    @Override
    public String toString() {
        return "Field [fieldType=" + fieldType + ", coord=" + coord + ", figure=" + figure + "]";
    }

}
