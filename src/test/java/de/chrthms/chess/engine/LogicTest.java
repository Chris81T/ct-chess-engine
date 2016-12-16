package de.chrthms.chess.engine;

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

/**
 * possible moves and performMoveTo are tested in the ChessEngineTest. Check the code. 
 * 
 * @author p0514551
 *
 */
public class LogicTest extends AbstractBeforeTest {
		
	@Test
	public void testIsCheckmate() {
		moveToAndComplete("g2", "g4");
		moveToAndComplete("e7", "e5");
		moveToAndComplete("f2", "f4");
		assertTrue(moveToAndComplete("d8", "h4") == GameState.CHECKMATE);		
		dumpBoard("testIsCheckmate");
	}
	
	@Test
	public void testIsChecked() {
		moveToAndComplete("e2", "e3");
		moveToAndComplete("d7", "d5");
		assertTrue(moveToAndComplete("f1", "b5") == GameState.CHECK);
		dumpBoard("testIsChecked");
	}
		
	@Test
	public void testGetLastMoveResult() {
		MoveResult moveResult = moveTo("g1", "f3");
		completeMove(moveResult);
		MoveResult lastMoveResult = logic.getLastMoveResult(handle);
		assertEquals(moveResult, lastMoveResult);
	}
	
}
