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
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.FigureType;
import java8.util.stream.Collectors;

public class Knight extends AbstractFigure {

    public Knight(int colorType, int initialFigurePosition) {
        super(colorType, FigureType.KNIGHT, initialFigurePosition);
    }

    @Override
    public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {

        final Coord coord = field.getCoord();
        final int x = coord.getNumX();
        final int y = coord.getNumY();

        List<Field> fieldsToCheck = new ArrayList<>();
        fieldsToCheck.add(getBoard().getField(handle, x - 2, y + 1));
        fieldsToCheck.add(getBoard().getField(handle, x - 1, y + 2));
        fieldsToCheck.add(getBoard().getField(handle, x + 1, y + 2));
        fieldsToCheck.add(getBoard().getField(handle, x + 2, y + 1));
        fieldsToCheck.add(getBoard().getField(handle, x + 2, y - 1));
        fieldsToCheck.add(getBoard().getField(handle, x + 1, y - 2));
        fieldsToCheck.add(getBoard().getField(handle, x - 1, y - 2));
        fieldsToCheck.add(getBoard().getField(handle, x - 2, y - 1));

        List<Field> prePossibleMoves = StreamBuilder.stream(fieldsToCheck)
                .filter(fieldToCheck -> fieldToCheck != null)
                .filter(fieldToCheck -> fieldToCheck.isEmpty() || figure.isEnemyFigureOf(fieldToCheck.getFigure()))
                .collect(Collectors.toList());

        return finalPossibleMovesCheckUp(handle, prePossibleMoves, field, figure, ignoreFinalMovesCheckup);
    }


}
