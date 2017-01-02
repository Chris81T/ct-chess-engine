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

package de.chrthms.chess.engine.notations.impl;

import java.util.List;

import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.backports.StreamBuilder;
import de.chrthms.chess.engine.core.constants.CastlingType;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.core.figures.Pawn;
import de.chrthms.chess.engine.notations.Fen;
import java8.util.Optional;

/**
 * @author Christian Thomas
 */
public class FenImpl extends AbstractNotation implements Fen {

    @Override
    public String getFen(Handle handle) {
        final String whiteSpace = " ";
        return new StringBuilder()
                .append(getPiecePlacementData(handle))
                .append(whiteSpace)
                .append(getActiveColor(handle))
                .append(whiteSpace)
                .append(getCastlingAvailability(handle))
                .append(whiteSpace)
                .append(getEnPassantTargetField(handle))
                .append(whiteSpace)
                .append(getHalfmoveClock(handle))
                .append(whiteSpace)
                .append(getFullmoveNumber(handle))
                .toString();
    }

    @Override
    public String getPiecePlacementData(Handle handle) {

        StringBuilder dataBuilder = new StringBuilder();

        for (int y = 8; y >= 1; y--) {

            int freeFieldsCount = 0;
            int restCount = 8;

            for (int x = 1; x <= 8; x++) {

                Field field = board.getField(handle, x, y);
                if (field.isEmpty()) {
                    freeFieldsCount++;
                } else {
                    // field is occupied
                    if (freeFieldsCount > 0) {
                        dataBuilder.append(freeFieldsCount);
                        restCount -= freeFieldsCount;
                        freeFieldsCount = 0;
                    }
                    dataBuilder.append(field.getFigure().toBoardString());
                    restCount--;
                }

            }

            if (restCount > 0) {
                dataBuilder.append(restCount);
            }

            if (y > 1) {
                dataBuilder.append("/");
            }

        }

        return dataBuilder.toString();
    }

    @Override
    public String getActiveColor(Handle handle) {
        return handle.getActivePlayer() == ColorType.WHITE ? "w" : "b";
    }

    private boolean checkRookMovement(Handle handle, int colorType, final int x, final int y) {
        Field rookField = board.getField(handle, x, y);
        AbstractFigure rook = rookField.getFigure();

        return rookField.isOccupied() &&
                rook.isNotMoved() &&
                rook.getFigureType() == FigureType.ROOK &&
                rook.getColorType() == colorType;
    }

    private boolean checkCastlingAvailablity(Handle handle, int colorType, int castlingType) {

        AbstractFigure king = board.getKingsField(handle, colorType).getFigure();
        if (king.isNotMoved()) {

            int y = 0;

            switch (colorType) {
                case ColorType.WHITE:
                    y = 1;
                    break;
                case ColorType.BLACK:
                    y = 8;
                    break;
            }

            switch (castlingType) {
                case CastlingType.KINGSIDE:
                    return checkRookMovement(handle, colorType, 8, y);
                case CastlingType.QUEENSIDE:
                    return checkRookMovement(handle, colorType, 1, y);
            }
        }

        return false;
    }

    /**
     * --> The third field represents castling availability. This indicates potential future castling that may of may not
     * be possible at the moment due to blocking pieces or enemy attacks.
     * <p>
     * If king is actually checked, it wont influence the castling availability (IMHO)
     *
     * @return KQkq | Kkq | Qkq | Qk | ...
     */
    @Override
    public String getCastlingAvailability(Handle handle) {
        StringBuilder codeBuilder = new StringBuilder();

        if (checkCastlingAvailablity(handle, ColorType.WHITE, CastlingType.KINGSIDE)) codeBuilder.append("K");
        if (checkCastlingAvailablity(handle, ColorType.WHITE, CastlingType.QUEENSIDE)) codeBuilder.append("Q");
        if (checkCastlingAvailablity(handle, ColorType.BLACK, CastlingType.KINGSIDE)) codeBuilder.append("k");
        if (checkCastlingAvailablity(handle, ColorType.BLACK, CastlingType.QUEENSIDE)) codeBuilder.append("q");

        return codeBuilder.length() == 0 ? "-" : codeBuilder.toString();
    }

    /**
     * It is relevant to determine all pawns of active player and then to check, if en passant is possible. It is possible
     * that two pawns are able to play en passant. But it is the same target field.
     */
    @Override
    public String getEnPassantTargetField(Handle handle) {

        List<Field> friendlyPawnFields = board.getOccupiedFields(handle, handle.getActivePlayer(), FigureType.PAWN);
        final MoveResult lastMoveResult = logic.getLastMoveResult(handle);

        Optional<Field> pawnField = StreamBuilder.stream(friendlyPawnFields)
                .filter(field -> {
                    Pawn pawn = (Pawn) field.getFigure();
                    return pawn.isEnPassantMovePossible(lastMoveResult, field.getCoord(), pawn);
                })
                .findAny();

        if (pawnField.isPresent()) {
            final Field field = pawnField.get();
            final AbstractFigure pawn = field.getFigure();
            final int y = field.getCoord().getNumY() + (pawn.getColorType() == ColorType.WHITE ? 1 : -1);
            Field targetField = board.getField(handle, lastMoveResult.getFromField().getNumX(), y);
            return targetField.getCoord().getStrCoord();
        } else {
            return "-";
        }
    }

    @Override
    public int getHalfmoveClock(Handle handle) {
        List<MoveResult> moveResults = handle.getMoveResults();

        int clock = 0;

        /**
         * Each moveResult is an halfmove.
         *
         * reverse for-each. Actually the stream api doesn't provide a simple way for this:
         */
        for (int i = moveResults.size() - 1; i >= 0; i--) {
            MoveResult moveResult = moveResults.get(i);
            if (moveResult.isHitFigure() || moveResult.getMovedFigure().getFigureType() == FigureType.PAWN) {
                break;
            }

            clock++;
        }

        return clock;
    }

    /**
     * the advantage of an integer division is here in use:
     * 1 / 2 = 0
     * 2 / 2 = 1
     * 3 / 2 = 1
     * ...
     */
    @Override
    public int getFullmoveNumber(Handle handle) {
        List<MoveResult> moveResults = handle.getMoveResults();
        return moveResults.isEmpty() ? 1 : generateFullmoveCount(moveResults.size());
    }

}
