package de.chrthms.chess.engine.core.figures;

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.PawnTransformation;
import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class PawnTest extends AbstractBeforeTest {
	
	@Test
	public void testMoveA2A3() {
		MoveResult result = moveTo("a2", "a3");
		assertMovedFigure(result, "a3");
	}
	
	@Test
	public void testMoveA2A4() {
		MoveResult result = moveTo("a2", "a4");
		assertMovedFigure(result, "a4");		
	}
	
	@Test
	public void testMoveInvalidA2B5() {
		exception.expect(ChessEngineException.class);
		MoveResult result = moveTo("a2", "b5");					
	}
	
	@Test
	public void testBoundPawnElseChecked() {
		moveToAndComplete("e2", "e3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("f1", "b5");
		exception.expect(ChessEngineException.class);
		MoveResult result = moveTo("d7", "d6");
	}
	
	
	@Test
	public void testMoveEnPassant() {
		moveToAndComplete("e2", "e4");
		moveToAndComplete("h7", "h6");
		moveToAndComplete("e4", "e5");
		
		// black pawn movement - should be hit
		MoveResult blackPawnMoveResult = moveTo("d7", "d5");
		assertNotNull(blackPawnMoveResult);
		AbstractFigure blackPawn = blackPawnMoveResult.getMovedFigure();
		completeMove(blackPawnMoveResult);
		
		dumpBoard("moveEnPassant before");
		MoveResult result = moveTo("e5", "d6");
		dumpBoard("moveEnPassant after");
		assertMovedFigure(result, "d6");
		assertTrue(blackPawn.getId().equals(assertHitFigure(result).getId()));
	}
	
	@Test
	public void testMovePawnTransform() {
		moveToAndComplete("a2", "a4");
		moveToAndComplete("b7", "b5");
		moveToAndComplete("a4", "b5");
		moveToAndComplete("b8", "a6");
		moveToAndComplete("b5", "b6");
		moveToAndComplete("h7", "h6");
		moveToAndComplete("b6", "b7");
		moveToAndComplete("h6", "h5");		
		MoveResult result = moveTo("b7", "b8");
		assertTrue(result.getMoveResultType() == MoveResultType.DECISION_NEEDED);
		assertTrue(result.isPawnTransformation());

		chessEngine.newFigureDecision(result, FigureType.QUEEN);
		
		int gameState = completeMove(result);
		assertTrue(gameState == GameState.NORMAL);
		
		MoveResult lastMoveResult = logic.getLastMoveResult(handle);
		PawnTransformation pawnTransformation = lastMoveResult.getPawnTransformation();
		assertTrue(pawnTransformation.getNewFigure() instanceof Queen);
		
		Field field = board.getField(handle, new Coord("b8"));
		AbstractFigure figure = field.getFigure();
		assertNotNull(figure);
		assertFalse(figure instanceof Pawn);
		assertTrue(figure instanceof Queen);
		assertTrue(figure.getColorType() == result.getMovedColorType());
		dumpBoard("movePawnTransform - new figure is a queen at b8");
	}
	
}
