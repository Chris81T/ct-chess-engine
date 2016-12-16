package de.chrthms.chess.engine.core.figures;

import java.util.ArrayList;
import java.util.List;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.constants.FigureType;

public class Bishop extends AbstractFigure {

	public Bishop(int colorType, int initialFigurePosition) {
		super(colorType, FigureType.BISHOP, initialFigurePosition);
	}

	@Override
	public List<Field> possibleMoves(Handle handle, Field field, AbstractFigure figure, boolean ignoreFinalMovesCheckup) {

		final Coord coord = field.getCoord();
		final int x = coord.getNumX();
		final int y = coord.getNumY();
		
		List<Field> prePossibleMoves = new ArrayList<>();
		prePossibleMoves.addAll(getUpperLeftFields(handle, figure, x, y));
		prePossibleMoves.addAll(getUpperRightFields(handle, figure, x, y));
		prePossibleMoves.addAll(getLowerRightFields(handle, figure, x, y));
		prePossibleMoves.addAll(getLowerLeftFields(handle, figure, x, y));
					
		return finalPossibleMovesCheckUp(handle, prePossibleMoves, field, figure, ignoreFinalMovesCheckup);
	}

}
