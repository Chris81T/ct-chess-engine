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

public class RookTest extends AbstractBeforeTest {
	
	@Test
	public void testMove() {
		moveToAndComplete("h2", "h4");
		moveToAndComplete("b7", "b5");
		MoveResult result = moveTo("h1", "h3");
		assertMovedFigure(result, "h3");
	}

	@Test
	public void testMoveAndHit() {
		moveToAndComplete("a2", "a4");
		moveToAndComplete("a7", "a5");
		moveToAndComplete("b2", "b4");
		moveToAndComplete("a5", "b4");
		moveToAndComplete("a4", "a5");
		MoveResult result = moveTo("a8", "a5");
		assertMovedFigure(result, "a5");
		assertHitFigure(result);
	}

	@Test
	public void testInvalidMove() {
		exception.expect(ChessEngineException.class);
		moveTo("h1", "h6");
	}
	
}
