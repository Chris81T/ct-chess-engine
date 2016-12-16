package de.chrthms.chess.engine.core;

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class HandleTest extends AbstractBeforeTest {

	@Test
	public void testNoGameOver() {
		assertFalse(handle.isGameOver());
	}
	
	@Test
	public void testInvalidHandleCreation() {
		Handle handle = new Handle();
		exception.expect(ChessEngineException.class);
		exception.expectMessage("the engine should create the handle via new or load a game");
		chessEngine.moveTo(handle, new Coord("e2"), new Coord("e3"));		
	}
	
}
