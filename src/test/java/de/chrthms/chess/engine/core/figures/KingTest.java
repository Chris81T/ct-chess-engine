package de.chrthms.chess.engine.core.figures;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.CastlingType;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public class KingTest extends AbstractBeforeTest {

	@Test
	public void testMove() {
		moveToAndComplete("d2", "d3");
		moveToAndComplete("b7", "b5");
		MoveResult result = moveTo("e1", "d2");
		assertMovedFigure(result, "d2");
	}

	@Test
	public void testMoveAndHit() {
		moveToAndComplete("e2", "e3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("f1", "b5");
		moveToAndComplete("g5", "g4");
		moveToAndComplete("b5", "d7");
		MoveResult result = moveTo("e8", "d7");
		assertMovedFigure(result, "d7");
		assertHitFigure(result);
	}

	@Test
	public void testQueenSideCastling() {
		moveToAndComplete("b1", "a3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("b2", "b3");
		moveToAndComplete("g5", "g4");
		moveToAndComplete("c2", "c3");
		moveToAndComplete("h7", "h5");
		moveToAndComplete("c1", "b2");
		moveToAndComplete("h5", "h4");
		moveToAndComplete("d1", "c2");
		moveToAndComplete("h4", "h3");
		MoveResult result = moveTo("e1", "c1");
		assertMovedFigure(result, "c1");
		assertTrue(result.getCastlingType() == CastlingType.QUEENSIDE);
		assertTrue(board.getField(handle, new Coord("d1")).getFigure().getFigureType() == FigureType.ROOK);
	}

	@Test
	public void testKingSideCastling() {
		moveToAndComplete("g1", "h3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("g2", "g3");
		moveToAndComplete("g5", "g4");
		moveToAndComplete("f1", "g2");
		moveToAndComplete("h7", "h5");
		MoveResult result = moveTo("e1", "g1");
		assertMovedFigure(result, "g1");
		assertTrue(result.getCastlingType() == CastlingType.KINGSIDE);
		assertTrue(board.getField(handle, new Coord("f1")).getFigure().getFigureType() == FigureType.ROOK);
	}

	@Test
	public void testInvalidMove() {
		exception.expect(ChessEngineException.class);
		moveTo("e1", "e2");
	}
	
}
