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
import de.chrthms.chess.engine.core.PawnTransformation;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class Pawn extends AbstractFigure {

    public Pawn(int colorType) {
        super(colorType, FigureType.PAWN);
    }

    private void checkForwardLeftOrRightField(AbstractFigure figure, Field fieldToCheck, List<Field> possibleMoves) {
        if (fieldToCheck != null &&
                fieldToCheck.isOccupied() &&
                fieldToCheck.getFigure().isEnemyFigureOf(figure)) {
            possibleMoves.add(fieldToCheck);
        }
    }

    @Override
    public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {

        final Coord coord = field.getCoord();
        final int x = coord.getNumX();
        final int y = coord.getNumY();

        List<Field> prePossibleMoves = new ArrayList<>();

        /**
         * influences the direction of this pawn according to the colorType
         */
        int yMul = 0;

        /**
         * is needed for a checkup. Only if this pawn is set to following
         * defined value (check the switch), an en passant checkup is
         * relevant.
         */

        switch (figure.getColorType()) {
            case ColorType.BLACK:
                yMul = -1;
                break;
            case ColorType.WHITE:
                yMul = 1;
        }

        Field fieldForward = getBoard().getField(handle, x, y + 1*yMul);
        Field twoFieldsForward = getBoard().getField(handle, x, y + 2*yMul);

        /**
         * first check, if one field forward is free
         */
        if (fieldForward.isEmpty()) {
            prePossibleMoves.add(fieldForward);

            if (figure.isNotMoved() && twoFieldsForward.isEmpty()) {
                prePossibleMoves.add(twoFieldsForward);
            }
        }

        /**
         * can the pawn attack some enemy figures?
         */
        Field fieldForwardLeft = getBoard().getField(handle, x-1, y + 1*yMul);
        Field fieldForwardRight = getBoard().getField(handle, x+1, y + 1*yMul);
        checkForwardLeftOrRightField(figure, fieldForwardLeft, prePossibleMoves);
        checkForwardLeftOrRightField(figure, fieldForwardRight, prePossibleMoves);

        /**
         * check en passant. If y is the same, check the last moved enemy figure!
         */
        MoveResult lastMoveResult = getLogic().getLastMoveResult(handle);

        /**
         * fromField.y === yEnPassant + yMul * 2 -->
         * this will check, if the last moved enemy figure has moved two fields forward. The from coordinate is known.
         * So simply equal it with y = 2 or 7 according to the color of the figure
         */
        if (isEnPassantMovePossible(lastMoveResult, coord, figure)) {
            Field enPassantResultField = getBoard().getField(handle, lastMoveResult.getToField().getNumX(), y + 1*yMul);
            prePossibleMoves.add(enPassantResultField);
        }

        return finalPossibleMovesCheckUp(handle, prePossibleMoves, field, figure, ignoreFinalMovesCheckup);
    }

    public boolean isEnPassantMovePossible(MoveResult lastMoveResult, Coord coordPawnToCheck, AbstractFigure pawnToCheck) {

        final int x = coordPawnToCheck.getNumX();
        final int y = coordPawnToCheck.getNumY();

        /**
         * influences the direction of this pawn according to the colorType
         */
        int yMul = 0;

        /**
         * is needed for a checkup. Only if this pawn is set to following
         * defined value (check the switch), an en passant checkup is
         * relevant.
         */
        int yEnPassant = 0;

        switch (pawnToCheck.getColorType()) {
            case ColorType.BLACK:
                yMul = -1;
                yEnPassant = 4;
                break;
            case ColorType.WHITE:
                yMul = 1;
                yEnPassant = 5;
        }

        return y == yEnPassant &&
                lastMoveResult != null &&
                lastMoveResult.getMovedFigure().isEnemyFigureOf(pawnToCheck) &&
                lastMoveResult.getMovedFigure().getFigureType() == FigureType.PAWN &&
                lastMoveResult.getFromField().getNumY() == yEnPassant + yMul * 2 && (
                lastMoveResult.getToField().getNumX() + 1 == x ||
                        lastMoveResult.getToField().getNumX() - 1 == x
        );
    }


    @Override
    public void performMoveTo(Handle handle, Field sourceField, Field destField, MoveResult moveResult) {

        /**
         *  before invoking the super performMoveTo, check, if some figure exists at the destField
         */
        AbstractFigure originDestFieldFigure = destField.getFigure();

        super.performMoveTo(handle, sourceField, destField, moveResult);

        /**
         * now the pawn is moved to the destination field
         */
        AbstractFigure movedPawn = destField.getFigure();

        int yPawnTransform = 0;
        int yOffset = 0;

        switch (movedPawn.getColorType()) {
            case ColorType.BLACK:
                yPawnTransform = 1;
                yOffset = -1;
                break;
            case ColorType.WHITE:
                yPawnTransform = 8;
                yOffset = 1;
        }

        /**
         * check pawn transformation
         */
        if (destField.getCoord().getNumY() == yPawnTransform) {
            moveResult.setMoveResultType(MoveResultType.DECISION_NEEDED);
            moveResult.setPawnTransformation(new PawnTransformation(movedPawn));
        }

        /**
         * check may performed en passant. The x coordinate from a pawn is only different, if the pawn hits an
         * enemy figure. If to that situation also the destination field is empty, it must be en-passant. So finally
         * set the hitFigure and remove the hit enemy pawn from the field beside
         */
        final int sourceFieldX = sourceField.getCoord().getNumX();
        final int destFieldX = destField.getCoord().getNumX();
        final int destFieldY = destField.getCoord().getNumY();

        if (originDestFieldFigure == null &&
                sourceFieldX != destFieldX) {
            Field enemyPawnField = getBoard().getField(handle, destFieldX, destFieldY - yOffset);
            AbstractFigure enemyPawn = enemyPawnField.getFigure();

            if (enemyPawn != null &&
                    enemyPawn.getFigureType() == FigureType.PAWN &&
                    enemyPawn.isEnemyFigureOf(movedPawn)) {
                enemyPawnField.clearFigure();
                moveResult.setHitFigure(enemyPawn);
            } else {
                throw new ChessEngineException("En-passant procedure failed. Found enemyPawn = " + enemyPawn);
            }

        }
    }


}
