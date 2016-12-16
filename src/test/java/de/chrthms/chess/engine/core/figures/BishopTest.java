package de.chrthms.chess.engine.core.figures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public class BishopTest extends AbstractBeforeTest {
		
	@Test
	public void testMove() {
		moveToAndComplete("d2", "d3");
		moveToAndComplete("b7", "b5");
		MoveResult result = moveTo("c1", "f4");
		assertMovedFigure(result, "f4");
	}
	
	@Test
	public void testMoveAndHit() {
		moveToAndComplete("d2", "d3");
		moveToAndComplete("g7", "g5");
		MoveResult result = moveTo("c1", "g5");
		assertMovedFigure(result, "g5");
		assertHitFigure(result);
	}
	
	@Test
	public void testInvalidMove() {
		moveToAndComplete("d2", "d3");
		moveToAndComplete("b7", "b5");
		exception.expect(ChessEngineException.class);
		moveTo("c1", "e5");
	}
		
}
