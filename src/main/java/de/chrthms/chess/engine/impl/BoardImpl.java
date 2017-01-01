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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FieldType;
import de.chrthms.chess.engine.core.constants.FigurePositionType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.core.figures.Bishop;
import de.chrthms.chess.engine.core.figures.King;
import de.chrthms.chess.engine.core.figures.Knight;
import de.chrthms.chess.engine.core.figures.Pawn;
import de.chrthms.chess.engine.core.figures.Queen;
import de.chrthms.chess.engine.core.figures.Rook;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class BoardImpl implements Board {

    private final int ROW_SIZE = 8;

    /**
     * F == Field
     * <p>
     * Y
     * 8  8[F][F][F][F][F][F][F][F]
     * 7  7[F][F][F][F][F][F][F][F]
     * 6  6[F][F][F][F][F][F][F][F]
     * 5  5[F][F][F][F][F][F][F][F]
     * 4  4[F][F][F][F][F][F][F][F]
     * 3  3[F][F][F][F][F][F][F][F]
     * 2  2[F][F][F][F][F][F][F][F]
     * 1  1[F][F][F][F][F][F][F][F]
     * 1  2  3  4  5  6  7  8  X
     * <p>
     * a  b  c  d  e  f  g  h
     */
    private Map<String, Field> buildFields(Handle handle) {

        Map<String, Field> fields = handle.getFields();
        fields.clear();

        int fieldType = FieldType.BLACK_FIELD;

        for (int y = 1; y <= ROW_SIZE; y++) {
            for (int x = 1; x <= ROW_SIZE; x++) {
                Coord coord = new Coord(x, y);
                Field field = new Field(fieldType, coord);
                fields.put(coord.getStrCoord(), field);
                fieldType = fieldType == FieldType.BLACK_FIELD ? FieldType.WHITE_FIELD : FieldType.BLACK_FIELD;
            }
        }

        return fields;
    }

    private void buildFiguresForNewGame(Map<String, Field> fields) {
        createNewFigureTo(fields.get(new Coord("a1").getStrCoord()), FigureType.ROOK, ColorType.WHITE, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("b1").getStrCoord()), FigureType.KNIGHT, ColorType.WHITE, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("c1").getStrCoord()), FigureType.BISHOP, ColorType.WHITE, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("d1").getStrCoord()), FigureType.QUEEN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("e1").getStrCoord()), FigureType.KING, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("f1").getStrCoord()), FigureType.BISHOP, ColorType.WHITE, FigurePositionType.RIGHT_SIDE);
        createNewFigureTo(fields.get(new Coord("g1").getStrCoord()), FigureType.KNIGHT, ColorType.WHITE, FigurePositionType.RIGHT_SIDE);
        createNewFigureTo(fields.get(new Coord("h1").getStrCoord()), FigureType.ROOK, ColorType.WHITE, FigurePositionType.RIGHT_SIDE);

        createNewFigureTo(fields.get(new Coord("a2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("b2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("c2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("d2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("e2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("f2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("g2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);
        createNewFigureTo(fields.get(new Coord("h2").getStrCoord()), FigureType.PAWN, ColorType.WHITE);

        createNewFigureTo(fields.get(new Coord("a7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("b7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("c7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("d7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("e7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("f7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("g7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("h7").getStrCoord()), FigureType.PAWN, ColorType.BLACK);

        createNewFigureTo(fields.get(new Coord("a8").getStrCoord()), FigureType.ROOK, ColorType.BLACK, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("b8").getStrCoord()), FigureType.KNIGHT, ColorType.BLACK, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("c8").getStrCoord()), FigureType.BISHOP, ColorType.BLACK, FigurePositionType.LEFT_SIDE);
        createNewFigureTo(fields.get(new Coord("d8").getStrCoord()), FigureType.QUEEN, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("e8").getStrCoord()), FigureType.KING, ColorType.BLACK);
        createNewFigureTo(fields.get(new Coord("f8").getStrCoord()), FigureType.BISHOP, ColorType.BLACK, FigurePositionType.RIGHT_SIDE);
        createNewFigureTo(fields.get(new Coord("g8").getStrCoord()), FigureType.KNIGHT, ColorType.BLACK, FigurePositionType.RIGHT_SIDE);
        createNewFigureTo(fields.get(new Coord("h8").getStrCoord()), FigureType.ROOK, ColorType.BLACK, FigurePositionType.RIGHT_SIDE);
    }

    @Override
    public Handle newGame(Handle handle) throws ChessEngineException {
        Map<String, Field> fields = buildFields(handle);
        buildFiguresForNewGame(fields);
        handle.setFields(fields);
        return handle;
    }

    @Override
    public Field getField(Handle handle, Coord coord) throws ChessEngineException {
        return handle.getFields().get(coord.getStrCoord());
    }

    @Override
    public Field getField(Handle handle, int x, int y) throws ChessEngineException {
        if (x < 1 || x > 8 || y < 1 || y > 8) return null;
        return getField(handle, new Coord(x, y));
    }

    private List<Field> determineOccupiedFields(Handle handle, int colorType, boolean enemy) {
        List<Field> occupiedFields = new ArrayList<>();

        for (int y = 1; y <= ROW_SIZE; y++) {
            for (int x = 1; x <= ROW_SIZE; x++) {
                Field field = getField(handle, x, y);
                if (!enemy && field.isOccupied() && field.getFigure().getColorType() == colorType ||
                        enemy && field.isOccupied() && field.getFigure().getColorType() != colorType) {
                    occupiedFields.add(field);
                }
            }
        }

        return occupiedFields;
    }

    @Override
    public List<Field> getOccupiedFields(Handle handle, int colorType) throws ChessEngineException {
        return determineOccupiedFields(handle, colorType, false);
    }

    @Override
    public List<Field> getOccupiedFields(Handle handle, int colorType, int figureType) throws ChessEngineException {
        return getOccupiedFields(handle, colorType)
                .stream()
                .filter(field -> field.getFigure().getFigureType() == figureType)
                .collect(Collectors.toList());
    }

    @Override
    public List<Field> getEnemyOccupiedFields(Handle handle, int colorType) throws ChessEngineException {
        return determineOccupiedFields(handle, colorType, true);
    }

    @Override
    public List<Field> getEnemyOccupiedFields(Handle handle, int colorType, int figureType)
            throws ChessEngineException {
        return getEnemyOccupiedFields(handle, colorType)
                .stream()
                .filter(field -> field.getFigure().getFigureType() == figureType)
                .collect(Collectors.toList());
    }

    @Override
    public Field getKingsField(Handle handle, int colorType) throws ChessEngineException {

        List<Field> occupiedKingFields = getOccupiedFields(handle, colorType, FigureType.KING);

        if (occupiedKingFields.size() != 1) {
            throw new ChessEngineException("At least one and only one king is expected!");
        }

        return occupiedKingFields.get(0);
    }

    @Override
    public AbstractFigure moveFigure(Field fromField, Field toField, boolean simulatedMove)
            throws ChessEngineException {

        AbstractFigure mayHitFigure = toField.getFigure();
        AbstractFigure figureToMove = fromField.getFigure();
        toField.setFigure(figureToMove);
        fromField.clearFigure();

        /**
         * sometimes only a simulation will be performed (check the king Figure operation/checkup's).
         * Therefor it is relevant to ignore the counter increasement. 
         */
        if (!simulatedMove) {
            figureToMove.incMovedCounter();
        }

        return mayHitFigure;
    }

    @Override
    public AbstractFigure moveFigure(Field fromField, Field toField) throws ChessEngineException {
        return moveFigure(fromField, toField, false);
    }

    @Override
    public AbstractFigure createNewFigureTo(Field field, int figureType, int colorType) throws ChessEngineException {
        return createNewFigureTo(field, figureType, colorType, FigurePositionType.REGARDLESS);
    }

    /**
     * If a new game is created and the figure will be created, for a bishop, knight and the rook it is relevant to
     * set the figure-position (check doc's for more information). But, if a pawn transformation will be performed,
     * it is possible, that the player will choose one of the listed figure. In that case every time the initial
     * position is regardless!  NOTE: This feature is only for statistic purposes!
     *
     * @param field
     * @param figureType
     * @param colorType
     * @param initialFigurePosition
     * @return
     * @throws ChessEngineException
     */
    public AbstractFigure createNewFigureTo(Field field, int figureType, int colorType, int initialFigurePosition) throws ChessEngineException {
        AbstractFigure figure = null;

        switch (figureType) {
            case FigureType.BISHOP:
                figure = new Bishop(colorType, initialFigurePosition);
                break;
            case FigureType.KING:
                figure = new King(colorType);
                break;
            case FigureType.KNIGHT:
                figure = new Knight(colorType, initialFigurePosition);
                break;
            case FigureType.PAWN:
                figure = new Pawn(colorType);
                break;
            case FigureType.QUEEN:
                figure = new Queen(colorType);
                break;
            case FigureType.ROOK:
                figure = new Rook(colorType, initialFigurePosition);
                break;
            default:
                return null;
        }

        field.setFigure(figure);
        return figure;
    }

}
