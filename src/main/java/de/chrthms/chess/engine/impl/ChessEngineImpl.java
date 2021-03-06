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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.core.*;
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import java8.util.stream.Collectors;

public class ChessEngineImpl implements ChessEngine {

    private final Board board;
    private final Logic logic;

    ChessEngineImpl(Board board, Logic logic) {
        this.board = board;
        this.logic = logic;
    }

    private void setWhiteAsActivePlayer(Handle handle) {
        handle.setActivePlayer(ColorType.WHITE);
    }

    private void setBlackAsActivePlayer(Handle handle) {
        handle.setActivePlayer(ColorType.BLACK);
    }

    private void switchActivePlayer(Handle handle) {
        if (handle.getActivePlayer() == ColorType.WHITE) {
            setBlackAsActivePlayer(handle);
        } else {
            setWhiteAsActivePlayer(handle);
        }
    }

    private ChessEngineException handleException(final String message, Exception e) {
        if (e instanceof ChessEngineException) {
            return (ChessEngineException) e;
        }
        return new ChessEngineException(message + " Check cause:", e);
    }

    @Override
    public Handle newGame() throws ChessEngineException {
        try {

        } catch (Exception e) {
            throw handleException("Could not create new game!", e);
        }
        Handle handle = new Handle();

        handle = board.newGame(handle);
        handle = logic.newGame(handle);

        setWhiteAsActivePlayer(handle);

        return handle;
    }

    @Override
    public Handle loadGame(GameData gameData) throws ChessEngineException {

        try {
            Object handleAsObject = null;

            ByteArrayInputStream inStream = null;
            ObjectInput input = null;

            try {
                inStream = new ByteArrayInputStream(gameData.getHandle());
                input = new ObjectInputStream(inStream);
                handleAsObject = input.readObject();

            } catch (IOException e) {
                throw new ChessEngineException("Impossible to deserialize handle from given byteArray!", e);
            } finally {
                if (inStream != null) inStream.close();
                if (input != null) input.close();
            }

            Handle handle = (Handle) handleAsObject;
            return handle;

        } catch (Exception e) {
            throw handleException("Could not load given game!", e);
        }
    }

    @Override
    public GameData saveGame(Handle handle) throws ChessEngineException {
        try {

            GameData gameData = new GameData();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ObjectOutput output = null;

            try {
                output = new ObjectOutputStream(outStream);
                output.writeObject(handle);
                output.flush();

                byte[] serializedHandle = outStream.toByteArray();
                gameData.setHandle(serializedHandle);

            } catch (IOException e) {
                throw new ChessEngineException("Impossible to serialize handle to a byteArray!", e);
            } finally {
                if (output != null) output.close();
                outStream.close();
            }

            return gameData;
        } catch (Exception e) {
            throw handleException("Could not save current game!", e);
        }
    }

    @Override
    public MoveResult moveTo(Handle handle, Coord from, Coord to) throws ChessEngineException {
        try {

            Field field = board.getField(handle, from);

            if (field == null) {
                throw new ChessEngineException("Invalid coordinates (from="
                        + from
                        + ", to="
                        + to
                        + ") or handle (the engine should create the handle via new or load a game)!");
            }

            AbstractFigure sourceFigure = field.getFigure();
            if (sourceFigure == null || sourceFigure.getColorType() != handle.getActivePlayer()) {
                throw new ChessEngineException("No source figure given or wrong active player!");
            }

            List<Coord> possibleMoves = possibleMoves(handle, from);
            for (Coord possibleMove : possibleMoves) {
                // check if movement is valid, so the move can be performed
                if (possibleMove.equals(to)) {
                    return logic.performMoveTo(handle, field, board.getField(handle, to));
                }
            }

            throw new ChessEngineException("Move from " + from.getStrCoord() + " to " + to.getStrCoord() + " is invalid!");

        } catch (Exception e) {
            throw handleException("Could not perform moveTo!", e);
        }

    }

    @Override
    public MoveResult newFigureDecision(MoveResult currentMoveResult, final int newFigureType) throws ChessEngineException {
        try {
            if (!currentMoveResult.isPawnTransformation()) {
                throw new ChessEngineException("A new figure decision is only allowed, if a pawn transformation is pending!");
            }

            PawnTransformation pawnTransformation = currentMoveResult.getPawnTransformation();
            pawnTransformation.setNewFigureType(newFigureType);

            return currentMoveResult;
        } catch (Exception e) {
            throw handleException("Could not perform new figure decision!", e);
        }

    }

    private int checkAndCompleteResult(Handle handle, MoveResult moveResult) {
        int movedColorType = moveResult.getMovedColorType();
        int enemyColorType = movedColorType == ColorType.WHITE ? ColorType.BLACK : ColorType.WHITE;
        int actualGameState = logic.checkGameState(handle, enemyColorType);

        if (actualGameState == GameState.NORMAL || actualGameState == GameState.CHECK) {
            switchActivePlayer(handle);
        }

        /**
         * Is relevant to have a bunch of information for analysis. E.g. the fen/pgn creation
         */
        moveResult.setGameStateTrigger(actualGameState);

        return actualGameState;
    }

    @Override
    public int completeMoveTo(Handle handle, MoveResult moveResult) throws ChessEngineException {
        try {
            switch (moveResult.getMoveResultType()) {
                case MoveResultType.OK:
                    return checkAndCompleteResult(handle, moveResult);
                case MoveResultType.DECISION_NEEDED:

                    if (moveResult.isPawnTransformation()) {
                        // it is expected, that the client has chosen a new figure
                        PawnTransformation pawnTransformation = moveResult.getPawnTransformation();
                        int newFigureType = pawnTransformation.getNewFigureType();
                        AbstractFigure newFigure = board.createNewFigureTo(board.getField(handle, moveResult.getToField()), newFigureType, moveResult.getMovedColorType());
                        pawnTransformation.setNewFigure(newFigure);
                    }

                    return checkAndCompleteResult(handle, moveResult);
                case MoveResultType.UNKNOWN:
                default:
                    throw new ChessEngineException("Invalid given moveResult. Must be 'OK' or 'DECISION_NEEDED'");
            }
        } catch (Exception e) {
            throw handleException("Could not complete move to procedure!", e);
        }
    }

    @Override
    public List<Coord> possibleMoves(Handle handle, Coord from) throws ChessEngineException {
        try {

            return StreamBuilder.stream(logic.possibleMoves(handle, board.getField(handle, from)))
                    .map(move -> move.getCoord())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw handleException("Could not determine possible moves!", e);
        }
    }

    @Override
    public List<FigurePosition> getFigurePositions(Handle handle) throws ChessEngineException {
        try {

            return StreamBuilder.stream(handle.getFields()
                    .entrySet())
                    .filter(entry -> entry.getValue().isOccupied())
                    .map(entry -> new FigurePosition(entry.getValue().getFigure().getFigureType(),
                            entry.getValue().getFigure().getColorType(),
                            entry.getValue().getCoord().getStrCoord()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw handleException("Could not determine figure positions!", e);
        }
    }

    @Override
    public Coord getKingsField(Handle handle, int figureColor) throws ChessEngineException {
        return board.getKingsField(handle, figureColor).getCoord();
    }

}
