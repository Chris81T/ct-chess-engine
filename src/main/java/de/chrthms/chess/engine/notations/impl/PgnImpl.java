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

import java.awt.*;
import java.util.List;

import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.CastlingType;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.notations.Pgn;
import de.chrthms.chess.engine.notations.constants.NotationType;

public class PgnImpl extends AbstractNotation implements Pgn {

    @Override
    public String getPgn(Handle handle, int notationType) {
        return new StringBuilder()
                .append(getPgnHeaderOnly(handle))
                .append(getPgnMovesOnly(handle, notationType))
                .toString();
    }

    @Override
    public String getPgnHeaderOnly(Handle handle) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPgnMovesOnly(Handle handle, int notationType) {

        List<MoveResult> moveResults = handle.getMoveResults();

        int index = 0;
        do {
            // TODO HIER WAS GUTES ÜBERLEGEN....
        } while (moveResults.size() > index);

        return null;
    }

    @Override
    public String getPgnForLastMove(Handle handle, int notationType) {
        MoveResult lastMoveResult = logic.getLastMoveResult(handle);

        MoveResult whiteMove = null;
        MoveResult blackMove = null;

        if (lastMoveResult.getMovedColorType() == ColorType.WHITE) {
            whiteMove = lastMoveResult;
        } else {
            blackMove = lastMoveResult;
            List<MoveResult> moveResults = handle.getMoveResults();
            // get the penultimate move result
            whiteMove = moveResults.get(moveResults.size() - 2);
        }

        return getPgnForMove(handle, whiteMove, blackMove, notationType);
    }

    @Override
    public String getPgnForMove(Handle handle, MoveResult whiteMove, MoveResult blackMove, int notationType) {
        return new StringBuilder()
                .append(getPgnForHalfmove(handle, whiteMove, notationType))
                .append(getPgnForHalfmove(handle, blackMove, notationType))
                .toString();
    }

    @Override
    public String getPgnForHalfmove(Handle handle, MoveResult moveResult, int notationType) {

        if (moveResult == null) return "";

        StringBuilder pgnBuilder = new StringBuilder();
        List<MoveResult> moveResults = handle.getMoveResults();

        if (moveResult.getMovedColorType() == ColorType.WHITE) {
            final int halfmovesCount = moveResults.indexOf(moveResult) + 1;
            pgnBuilder.append(generateFullmoveCount(halfmovesCount));
            pgnBuilder.append(". ");
        }

        switch (moveResult.getCastlingType()) {
            case CastlingType.KINGSIDE:
                pgnBuilder.append("O-O");
                return pgnBuilder.toString();
            case CastlingType.QUEENSIDE:
                pgnBuilder.append("O-O-O");
                return pgnBuilder.toString();
        }

        if (NotationType.LAN == notationType) return getPgnForHalfmoveLAN(handle, moveResult, pgnBuilder);
        if (NotationType.SAN == notationType) return getPgnForHalfmoveSAN(handle, moveResult, pgnBuilder);
        // should never happen
        return "";
    }

    private String getPgnForHalfmoveSAN(Handle handle, MoveResult moveResult, StringBuilder pgnBuilder) {


        return pgnBuilder.toString();
    }

    /**
     * will be generated without hyphens -> usable for uci engines
     *
     * @param handle
     * @param moveResult
     * @param pgnBuilder
     * @return
     */
    private String getPgnForHalfmoveLAN(Handle handle, MoveResult moveResult, StringBuilder pgnBuilder) {

        pgnBuilder.append(getFigureTypeAsStr(moveResult.getMovedFigure()));
        pgnBuilder.append(moveResult.getFromField().getStrCoord());

        if (moveResult.isHitFigure()) {
            pgnBuilder.append("x");
        }

        pgnBuilder.append(moveResult.getToField().getStrCoord());

        if (moveResult.isPawnTransformation()) {
            pgnBuilder.append(getFigureTypeAsStr(moveResult.getPawnTransformation().getNewFigure()));
        }

        if (moveResult.isEnPassant()) {
            pgnBuilder.append(" e.p.");
        }

        if (moveResult.isCheckedTrigger()) {
            pgnBuilder.append("+");
        } else if (moveResult.isCheckmateTrigger()) {
            pgnBuilder.append("#");
        }

        return pgnBuilder.toString();
    }

    private String getFigureTypeAsStr(AbstractFigure figure) {

        if (figure.getFigureType() == FigureType.PAWN) {
            return "";
        }

        return figure.toBoardString().toUpperCase();
    }

}
