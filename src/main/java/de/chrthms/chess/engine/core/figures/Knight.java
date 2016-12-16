package de.chrthms.chess.engine.core.figures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.constants.FigureType;

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
		fieldsToCheck.add(getBoard().getField(handle, x-2, y+1));
		fieldsToCheck.add(getBoard().getField(handle, x-1, y+2));
		fieldsToCheck.add(getBoard().getField(handle, x+1, y+2));
		fieldsToCheck.add(getBoard().getField(handle, x+2, y+1));
		fieldsToCheck.add(getBoard().getField(handle, x+2, y-1));
		fieldsToCheck.add(getBoard().getField(handle, x+1, y-2));
		fieldsToCheck.add(getBoard().getField(handle, x-1, y-2));
		fieldsToCheck.add(getBoard().getField(handle, x-2, y-1));
		
		List<Field> prePossibleMoves = fieldsToCheck.stream()
			.filter(fieldToCheck -> fieldToCheck != null)
			.filter(fieldToCheck -> fieldToCheck.isEmpty() || figure.isEnemyFigureOf(fieldToCheck.getFigure()))
			.collect(Collectors.toList());
					
		return finalPossibleMovesCheckUp(handle, prePossibleMoves, field, figure, ignoreFinalMovesCheckup);
	}

	
	
}
