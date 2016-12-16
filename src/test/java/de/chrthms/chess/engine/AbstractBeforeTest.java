package de.chrthms.chess.engine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public abstract class AbstractBeforeTest {

	protected ChessEngine chessEngine;
	protected Board board;
	protected Logic logic;
	protected Handle handle;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void buildEngine() {
		chessEngine = ChessEngineBuilder.build();
		board = ChessEngineBuilder.getBoard();
		logic = ChessEngineBuilder.getLogic();
		handle = chessEngine.newGame();
	}

	protected void dumpBoard(String label) {
		dumpBoard(handle, label);
	}
	
	protected void dumpBoard(Handle handle, String label) {
		System.out.println(label + ":");
		System.out.println();
		for (int y = 8; y >= 1; y--) {
			for (int x = 1; x <= 8; x++) {
				Field field = board.getField(handle, x, y);
				assertNotNull(field);
				System.out.print("[" + new Coord(x, y).getStrCoord() + field.toBoardString() + "]");
			}
			System.out.println();
		}
		System.out.println();
	}

	protected MoveResult moveTo(String from, String to) {
		return chessEngine.moveTo(handle, new Coord(from), new Coord(to));
	}

	protected int completeMove(MoveResult moveResult) {
		return chessEngine.completeMoveTo(handle, moveResult);			
	}
	
	protected int moveToAndComplete(String from, String to) {
		MoveResult result = moveTo(from, to);
		return completeMove(result);
	}
	
	protected void assertMovedFigure(MoveResult moveResult, String to) {
		assertNotNull(moveResult);
		AbstractFigure movedFigure = moveResult.getMovedFigure();
		assertNotNull(movedFigure);
		Field field = board.getField(handle, new Coord(to));
		AbstractFigure figure = field.getFigure();
		assertNotNull(figure);
		assertTrue(figure.getId().equals(movedFigure.getId()));
	}

	protected AbstractFigure assertHitFigure(MoveResult moveResult) {
		assertNotNull(moveResult);
		AbstractFigure hitFigure = moveResult.getHitFigure();
		assertNotNull(hitFigure);
		return hitFigure;
	}

}
