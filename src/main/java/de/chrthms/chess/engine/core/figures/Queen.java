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

package de.chrthms.chess.engine.core.figures;

import java.util.ArrayList;
import java.util.List;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.constants.FigureType;

public class Queen extends AbstractFigure {

    public Queen(int colorType) {
        super(colorType, FigureType.QUEEN);
    }

    @Override
    public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {

        final Coord coord = field.getCoord();
        final int x = coord.getNumX();
        final int y = coord.getNumY();

        List<Field> prePossibleMoves = new ArrayList<>();
        prePossibleMoves.addAll(getLeftFields(handle, figure, x, y));
        prePossibleMoves.addAll(getUpperLeftFields(handle, figure, x, y));
        prePossibleMoves.addAll(getUpperFields(handle, figure, x, y));
        prePossibleMoves.addAll(getUpperRightFields(handle, figure, x, y));
        prePossibleMoves.addAll(getRightFields(handle, figure, x, y));
        prePossibleMoves.addAll(getLowerRightFields(handle, figure, x, y));
        prePossibleMoves.addAll(getLowerFields(handle, figure, x, y));
        prePossibleMoves.addAll(getLowerLeftFields(handle, figure, x, y));

        return finalPossibleMovesCheckUp(handle, prePossibleMoves, field, figure, ignoreFinalMovesCheckup);
    }

}
