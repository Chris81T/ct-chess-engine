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
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.CastlingType;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;

public class King extends AbstractFigure {

    public King(int colorType) {
        super(colorType, FigureType.KING);
    }

    private void checkField(Handle handle, AbstractFigure figure, final int x, final int y, List<Field> possibleMoves) {
        Field field = getBoard().getField(handle, x, y);
        if (field != null &&
                (field.isEmpty() || figure.isEnemyFigureOf(field.getFigure()))) {
            possibleMoves.add(field);
        }
    }

    private boolean isCastlingChecked(Handle handle, Field field, final int x, final int y) {
        Field possibleField = getBoard().getField(handle, x, y);
        AbstractFigure mayHitFigure = setFigureToSimulatedField(field, possibleField);

        boolean isChecked = getLogic().isChecked(handle, possibleField);

        setFigureToOriginField(field, possibleField, mayHitFigure);
        return isChecked;
    }

    private List<Field> checkCastling(Handle handle, Field field, AbstractFigure figure, List<Field> prePossibleMoves) {

        List<Field> possibleMoves = new ArrayList<>(prePossibleMoves);

        if (figure.isNotMoved()) {

            final Coord coord = field.getCoord();
            int y = coord.getNumY();

            /**
             * differ between black and white side
             */
            switch (figure.getColorType()) {
                case ColorType.BLACK:
                    y = 8;
                    break;
                case ColorType.WHITE:
                    y = 1;
            }

            int xShortRook = 8;
            int xShortDestField = 7;
            int xShortFieldToCheck = 6;

            int xLongRook = 1;
            int xLongDestField = 3;
            int xLongFieldToCheckOne = 2;
            int xLongFieldToCheckTwo = 4;

            /**
             * check the short castling
             */
            AbstractFigure shortCastlingRook = getBoard().getField(handle, xShortRook, y).getFigure();
            Field shortCastlingField = getBoard().getField(handle, xShortDestField, y);

            if (shortCastlingRook != null &&
                    shortCastlingRook.isNotMoved() &&
                    getBoard().getField(handle, xShortFieldToCheck, y).isEmpty() &&
                    shortCastlingField.isEmpty() &&
                    !getLogic().isChecked(handle, field) &&
                    !isCastlingChecked(handle, field, xShortFieldToCheck, y)) {
                possibleMoves.add(shortCastlingField);
            }

            /**
             * check the long castling
             */
            AbstractFigure longCastlingRook = getBoard().getField(handle, xLongRook, y).getFigure();
            Field longCastlingField = getBoard().getField(handle, xLongDestField, y);

            if (longCastlingRook != null &&
                    longCastlingRook.isNotMoved() &&
                    getBoard().getField(handle, xLongFieldToCheckOne, y).isEmpty() &&
                    getBoard().getField(handle, xLongFieldToCheckTwo, y).isEmpty() &&
                    longCastlingField.isEmpty() &&
                    !getLogic().isChecked(handle, field) &&
                    !isCastlingChecked(handle, field, xLongFieldToCheckOne, y) &&
                    !isCastlingChecked(handle, field, xLongFieldToCheckTwo, y) &&
                    !isCastlingChecked(handle, field, xLongDestField, y)) {
                possibleMoves.add(longCastlingField);
            }

        }

        return possibleMoves;
    }

    private List<Field> checkBasicPossibleMoves(Handle handle, AbstractFigure figure, Field field) {
        List<Field> possibleMoves = new ArrayList<>();

        final Coord coord = field.getCoord();
        final int x = coord.getNumX();
        final int y = coord.getNumY();

        checkField(handle, figure, x - 1, y, possibleMoves);
        checkField(handle, figure, x - 1, y + 1, possibleMoves);
        checkField(handle, figure, x, y + 1, possibleMoves);
        checkField(handle, figure, x + 1, y + 1, possibleMoves);
        checkField(handle, figure, x + 1, y, possibleMoves);
        checkField(handle, figure, x + 1, y - 1, possibleMoves);
        checkField(handle, figure, x, y - 1, possibleMoves);
        checkField(handle, figure, x - 1, y - 1, possibleMoves);

        return possibleMoves;
    }

    @Override
    public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {
        List<Field> prePrePossibleMoves = checkBasicPossibleMoves(handle, figure, field);
        List<Field> prePossibleMoves = finalPossibleMovesCheckUp(handle, prePrePossibleMoves, field, figure, ignoreFinalMovesCheckup);
        return checkCastling(handle, field, figure, prePossibleMoves);
    }

    /**
     * Only the basic possible moves are relevant here!
     */
    @Override
    public boolean canCheck(Handle handle, AbstractFigure figure, Field field, Field kingsField,
                            boolean ignorFinalMovesCheckup) {
        return StreamBuilder.stream(checkBasicPossibleMoves(handle, figure, field))
                .anyMatch(move -> move.equals(kingsField));
    }

    /**
     * Relevant for performing castling
     *
     * @param fromX
     * @param toX
     * @param y
     */
    private void moveRook(Handle handle, final int fromX, final int toX, final int y) {
        Field fromField = getBoard().getField(handle, fromX, y);
        Field toField = getBoard().getField(handle, toX, y);
        getBoard().moveFigure(fromField, toField);
    }

    @Override
    public void performMoveTo(Handle handle, Field sourceField, Field destField, MoveResult moveResult) {

        super.performMoveTo(handle, sourceField, destField, moveResult);

        /**
         * finally handle may castling situation
         */
        final Coord sourceCoord = sourceField.getCoord();
        int sourceX = sourceCoord.getNumX();
        int destX = destField.getCoord().getNumX();
        int y = sourceCoord.getNumY();

        boolean distanceCheckUp = Math.abs(sourceX - destX) > 1;

        if (distanceCheckUp && sourceX < destX) {
            moveResult.setCastlingType(CastlingType.KINGSIDE);
            moveRook(handle, 8, 6, y);
        } else if (distanceCheckUp && sourceX > destX) {
            moveResult.setCastlingType(CastlingType.QUEENSIDE);
            moveRook(handle, 1, 4, y);
        }

    }


}
