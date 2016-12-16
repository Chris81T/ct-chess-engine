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

public class KnightTest extends AbstractBeforeTest {
	
	@Test
	public void testMoveWhite() {
		MoveResult result = moveTo("g1", "f3");
		assertMovedFigure(result, "f3");
	}

	@Test
	public void testMoveBlack() {
		moveToAndComplete("b1", "c3"); // first move white
		MoveResult result = moveTo("b8", "a6");
		assertMovedFigure(result, "a6");
	}

	@Test
	public void testMoveAndHit() {
		moveToAndComplete("b1", "c3");
		moveToAndComplete("b7", "b5");
		MoveResult result = moveTo("c3", "b5");
		assertMovedFigure(result, "b5");
		assertHitFigure(result);
	}

	@Test
	public void testInvalidMove() {
		exception.expect(ChessEngineException.class);
		moveTo("b1", "d2");
	}

}
