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

package de.chrthms.chess.engine.impl;

import java.util.List;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class LogicImpl implements Logic {

    private final Board board;

    public LogicImpl(Board board) {
        this.board = board;
    }

    @Override
    public Handle newGame(Handle handle) throws ChessEngineException {
        handle.setGameState(GameState.NORMAL);
        return handle;
    }

    /**
     * Will check, if any other friendly figure is able to move to another field.
     *
     * @param handle
     * @param colorType
     * @return
     */
    private boolean friendlyFigureMovePossible(Handle handle, int colorType) {
        return StreamBuilder.stream(board.getOccupiedFields(handle, colorType))
                .anyMatch(field -> !possibleMoves(handle, field).isEmpty());
    }

    @Override
    public int checkGameState(Handle handle, int colorType) throws ChessEngineException {

        int gameState = GameState.NORMAL;

        Field kingsField = board.getKingsField(handle, colorType);

        boolean isChecked = isChecked(handle, kingsField, true);

        if (isChecked) {
            // is also checkmate?
            List<Field> possibleKingMoves = possibleMoves(handle, kingsField);
            boolean kingIsCaptured = possibleKingMoves.isEmpty();
            boolean checkmate = false;

            /**
             * may another friendly figure can prevent a checkmate?
             * -> Use the condition to prevent obsolete - a bit expensive - checkups
             */
            if (kingIsCaptured) {
                boolean anotherFigureCanHelp = friendlyFigureMovePossible(handle, colorType);
                checkmate = !anotherFigureCanHelp;
            }

            if (checkmate) {
                gameState = GameState.CHECKMATE;
            } else {
                gameState = GameState.CHECK;
            }

        } else {
            // does a deadlock exists?

            boolean noDeadlock = friendlyFigureMovePossible(handle, colorType);
            if (!noDeadlock) {
                gameState = GameState.DEADLOCK;
            }
        }

        handle.setGameState(gameState);
        return gameState;
    }

    @Override
    public boolean isChecked(Handle handle, Field kingsField, boolean ignoreFinalMovesCheckup)
            throws ChessEngineException {
        AbstractFigure king = kingsField.getFigure();

        return StreamBuilder.stream(board.getEnemyOccupiedFields(handle, king.getColorType()))
                .anyMatch(occupiedField -> {
                    AbstractFigure enemyFigure = occupiedField.getFigure();
                    return enemyFigure.canCheck(handle, enemyFigure, occupiedField, kingsField, ignoreFinalMovesCheckup);
                });
    }

    @Override
    public boolean isChecked(Handle handle, Field kingsField) throws ChessEngineException {
        return isChecked(handle, kingsField, false);
    }

    @Override
    public List<Field> possibleMoves(Handle handle, Field sourceField) throws ChessEngineException {
        if (sourceField.isEmpty()) {
            throw new ChessEngineException("No figure found at source field!");
        }

        AbstractFigure figure = sourceField.getFigure();
        return figure.possibleMoves(handle, sourceField, figure);
    }

    @Override
    public MoveResult performMoveTo(Handle handle, Field sourceField, Field destField) throws ChessEngineException {
        if (handle.isGameOver()) {
            throw new ChessEngineException("Game is actually over. Check the last given game state!");
        }

        AbstractFigure figureToMove = sourceField.getFigure();

        /**
         * prepare the moveResult
         */
        MoveResult moveResult = new MoveResult();
        moveResult.setMoveResultType(MoveResultType.OK);
        moveResult.setMovedFigure(figureToMove);
        moveResult.setFromField(sourceField.getCoord());
        moveResult.setToField(destField.getCoord());
        moveResult.setLastMoveResult(getLastMoveResult(handle));

        figureToMove.performMoveTo(handle, sourceField, destField, moveResult);
        handle.addMoveResult(moveResult);
        return moveResult;
    }

    @Override
    public MoveResult getLastMoveResult(Handle handle) {
        List<MoveResult> moveResults = handle.getMoveResults();
        return moveResults.isEmpty() ? null : moveResults.get(moveResults.size() - 1);
    }

}
