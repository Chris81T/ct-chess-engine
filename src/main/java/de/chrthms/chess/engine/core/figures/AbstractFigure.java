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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigurePositionType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;
import java8.util.stream.Collectors;

public abstract class AbstractFigure implements Serializable {

    /**
     * logic and board are relevant for some checkup's.
     */
    private transient Board board = null;
    private transient Logic logic = null;

    private final int colorType;
    private final int figureType;

    private boolean notMoved = true;

    /**
     * nice feature for statistics
     */
    private int movedCounter = 0;
    private int initalFigurePosition = FigurePositionType.REGARDLESS;

    /**
     * is relevant for a special use case
     */
    private final String id = UUID.randomUUID().toString();

    public AbstractFigure(int colorType, int figureType) {
        this.colorType = colorType;
        this.figureType = figureType;
    }

    public AbstractFigure(int colorType, int figureType, int initialFigurePosition) {
        this.colorType = colorType;
        this.figureType = figureType;
        this.initalFigurePosition = initialFigurePosition;
    }

    protected Board getBoard() {
        if (board == null) board = ChessEngineBuilder.getBoard();
        return board;
    }

    protected Logic getLogic() {
        if (logic == null) logic = ChessEngineBuilder.getLogic();
        return logic;
    }

    public void incMovedCounter() {
        notMoved = false;
        movedCounter++;
    }

    public boolean isMoved() {
        return isNotMoved();
    }

    public boolean isNotMoved() {
        return notMoved;
    }

    public int getInitalFigurePosition() {
        return initalFigurePosition;
    }

    public void setInitalFigurePosition(int initalFigurePosition) {
        this.initalFigurePosition = initalFigurePosition;
    }

    /**
     * Helper method better coding
     *
     * @param figure
     * @return
     */
    public boolean isFriendlyFigureOf(AbstractFigure figure) {
        return figure.getColorType() == getColorType();
    }

    /**
     * Helper method better coding
     *
     * @param figure
     * @return
     */
    public boolean isEnemyFigureOf(AbstractFigure figure) {
        return !isFriendlyFigureOf(figure);
    }

    public int getColorType() {
        return colorType;
    }

    public int getFigureType() {
        return figureType;
    }

    public String getId() {
        return id;
    }

    public boolean canCheck(Handle handle, AbstractFigure figure, Field field, Field kingsField, boolean ignoreFinalMovesCheckup) {
        return StreamBuilder.stream(possibleMoves(handle, field, figure, ignoreFinalMovesCheckup))
                .anyMatch(move -> move.equals(kingsField));
    }

    public void performMoveTo(Handle handle, Field sourceField, Field destField, MoveResult moveResult) {
        AbstractFigure figure = sourceField.getFigure();

        moveResult.setHitFigure(destField.getFigure());
        moveResult.setMovedFigure(figure);

        getBoard().moveFigure(sourceField, destField);
    }

    public abstract List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup);

    public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure) {
        return possibleMoves(handle, field, figure, false);
    }

    /**
     * Is relevant for possible moves to simulate some figure moves.
     *
     * @param simulatedField
     * @return may hit figure (must be restored after simulation!)
     */
    protected AbstractFigure setFigureToSimulatedField(Field field, Field simulatedField) {
        return getBoard().moveFigure(field, simulatedField, true);
    }

    /**
     * Is relevant for possible moves. Will revert the simulation.
     *
     * @param simulatedField
     * @param mayHitFigure
     */
    protected void setFigureToOriginField(Field field, Field simulatedField, AbstractFigure mayHitFigure) {
        getBoard().moveFigure(simulatedField, field, true);
        simulatedField.setFigure(mayHitFigure);
    }

    /**
     * Typically each special mod will determine all possible moves. Finally it must be checked, if the own king
     * will be checked for some of the moves. The other way is, that some of the possible moves can prevent the
     * checked state.
     * <p>
     * NOTE: To avoid recursive infinite possibleMoves calls, this method must only be called by the origin figure
     * mod. Remember the according figure is to that time the only figure, that will be moved. This means, that
     * an enemy figure must not check, if may be the own king will be checked, because that enemy figure WONT move
     * to that time. It is a simulation triggered by this function.
     * <p>
     * The aim is to identify, if some other enemy figures will actual check the own king, if the given figure
     * (param) will move to one of the given moves/fields (param).
     *
     * @param handle
     * @param prePossibleMoves
     * @param field
     * @param figure
     * @param ignoreFinalMovesCheckup
     * @return
     */
    protected List<Field> finalPossibleMovesCheckUp(Handle handle, List<Field> prePossibleMoves, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {

        /**
         * will prevent recursive endless loop call
         */
        if (ignoreFinalMovesCheckup) return prePossibleMoves;

        Field kingsField = getBoard().getKingsField(handle, figure.getColorType());
        boolean figureIsKing = figure.getFigureType() == FigureType.KING;

        /**
         * Filter out all possible moves, where the king will be checked by the enemy
         */
        return StreamBuilder.stream(prePossibleMoves)
                .filter(possibleMove -> {

                    /**
                     * perform virtually the move and check the king's situation. If the king is not checked, the move is
                     * definitely possible
                     *
                     * remember: the move is the field
                     */
                    AbstractFigure mayHitFigure = setFigureToSimulatedField(field, possibleMove);

                    /**
                     * Possibly the figure is the king itself. in that case instead of the kingsField, that wont have the
                     * king in simulation mode, the move (field) itself has to be used.
                     */
                    boolean isNotChecked = !(getLogic().isChecked(handle, figureIsKing ? possibleMove : kingsField, true));

                    setFigureToOriginField(field, possibleMove, mayHitFigure);

                    return isNotChecked;
                })
                .collect(Collectors.toList());
    }

    private void parseFields(Handle handle, AbstractFigure sourceFigure, int x, int y, final int xOffset, final int yOffset, List<Field> foundFields) {

        Field field = getBoard().getField(handle, x, y);
        if (field != null && field.isEmpty()) {
            foundFields.add(field);
            parseFields(handle, sourceFigure, x + xOffset, y + yOffset, xOffset, yOffset, foundFields);
        } else if (field != null && sourceFigure.isEnemyFigureOf(field.getFigure())) {
            foundFields.add(field);
        }
    }

    private List<Field> getFields(Handle handle, AbstractFigure sourceFigure, int x, int y, final int xOffset, final int yOffset) {
        List<Field> foundFields = new ArrayList<>();
        parseFields(handle, sourceFigure, x + xOffset, y + yOffset, xOffset, yOffset, foundFields);
        return foundFields;
    }

    protected List<Field> getUpperFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, 0, 1);
    }

    protected List<Field> getLowerFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, 0, -1);
    }

    protected List<Field> getLeftFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, -1, 0);
    }

    protected List<Field> getRightFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, 1, 0);
    }

    protected List<Field> getUpperLeftFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, -1, 1);
    }

    protected List<Field> getUpperRightFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, 1, 1);
    }

    protected List<Field> getLowerLeftFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, -1, -1);
    }

    protected List<Field> getLowerRightFields(Handle handle, AbstractFigure sourceFigure, int x, int y) {
        return getFields(handle, sourceFigure, x, y, 1, -1);
    }

    public String toBoardString() {
        String figureStr = null;

        switch (figureType) {
            case FigureType.BISHOP:
                figureStr = "b";
                break;
            case FigureType.KING:
                figureStr = "k";
                break;
            case FigureType.KNIGHT:
                figureStr = "n";
                break;
            case FigureType.PAWN:
                figureStr = "p";
                break;
            case FigureType.QUEEN:
                figureStr = "q";
                break;
            case FigureType.ROOK:
                figureStr = "r";
                break;
            default:
                return "?";
        }

        if (colorType == ColorType.WHITE) {
            figureStr = figureStr.toUpperCase();
        }

        return figureStr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + colorType;
        result = prime * result + figureType;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + initalFigurePosition;
        result = prime * result + movedCounter;
        result = prime * result + (notMoved ? 1231 : 1237);
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
        AbstractFigure other = (AbstractFigure) obj;
        if (colorType != other.colorType)
            return false;
        if (figureType != other.figureType)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (initalFigurePosition != other.initalFigurePosition)
            return false;
        if (movedCounter != other.movedCounter)
            return false;
        if (notMoved != other.notMoved)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AbstractFigure [colorType=" + colorType + ", figureType=" + figureType + ", notMoved=" + notMoved
                + ", movedCounter=" + movedCounter + ", id=" + id + "]";
    }

}
