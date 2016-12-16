package de.chrthms.chess.engine.core.figures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public class QueenTest extends AbstractBeforeTest {
	
	@Test
	public void testMove() {
		moveToAndComplete("e2", "e3");
		moveToAndComplete("b7", "b5");
		MoveResult result = moveTo("d1", "h5");
		assertMovedFigure(result, "h5");
	}

	@Test
	public void testMoveAndHit() {
		moveToAndComplete("e2", "e3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("d1", "g4");
		moveToAndComplete("d7", "d6");
		MoveResult result = moveTo("g4", "c8");
		assertMovedFigure(result, "c8");
		assertHitFigure(result);
	}

	@Test
	public void testInvalidMove() {
		exception.expect(ChessEngineException.class);
		moveTo("d1", "d6");
	}
	
}
