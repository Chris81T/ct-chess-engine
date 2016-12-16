package de.chrthms.chess.engine;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.GameData;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.exceptions.ChessEngineException;
import de.chrthms.chess.engine.impl.ChessEngineBuilder;

public class ChessEngineTest extends AbstractBeforeTest {

	@Test
	public void testNewGame() {
		
		Handle newGame = ChessEngineBuilder.build()
			.newGame();
		
		assertNotNull(newGame);
				
	}
		
	@Test
	public void testSaveAndLoadGame() {
		moveToAndComplete("b1", "a3");
		moveToAndComplete("g7", "g5");
		moveToAndComplete("b2", "b4");
		moveToAndComplete("g5", "g4");
		moveToAndComplete("c2", "c3");
		moveToAndComplete("h7", "h5");
		moveToAndComplete("c1", "b2");
		moveToAndComplete("h5", "h4");
		moveToAndComplete("d1", "c2");
		moveToAndComplete("h4", "h3");
		moveToAndComplete("e1", "c1"); // white queenside castling
		moveToAndComplete("d7", "d6");
		moveToAndComplete("c2", "a4"); // white queen --> check!
		MoveResult queenCheckResult = logic.getLastMoveResult(handle);
		assertTrue(queenCheckResult.getMoveResultType() == MoveResultType.OK);		
		assertTrue(handle.getGameState() == GameState.CHECK);		
		GameData savedGame = chessEngine.saveGame(handle);
		assertNotNull(savedGame);
		
		// after loading black king move from e8 to d7 must be invalid (checked by queen) - Test it...
		Handle reloadedHandle = chessEngine.loadGame(savedGame);
		System.out.println("Reloaded handle --> " + reloadedHandle);		
		assertEquals(handle, reloadedHandle);
		
		List<Coord> possibleMoves = chessEngine.possibleMoves(reloadedHandle, new Coord("e8"));
		System.out.println("after loading black king move from e8 to d7 must be invalid --> possibleMoves = " + possibleMoves);
		assertTrue(possibleMoves.isEmpty());
		dumpBoard("testSaveAndLoadGame - original handle");
		dumpBoard(reloadedHandle, "testSaveAndLoadGame - reloaded handle");
	}
	
	@Test
	public void testMoveToResult() {
		MoveResult moveResult = moveTo("e2", "e4");
		assertNotNull(moveResult);
	}
	
	@Test
	public void testCompleteMoveTo() {
		MoveResult moveResult = moveTo("e2", "e4");
		assertNotNull(moveResult);
		int beforeComplete = handle.getActivePlayer();
		chessEngine.completeMoveTo(handle, moveResult);
		int afterComplete = handle.getActivePlayer();		
		assertFalse(beforeComplete == afterComplete);
	}
	
	@Test
	public void testPossibleMovesE2() {
		List<Coord> possibleMoves = chessEngine.possibleMoves(handle, new Coord("e2"));
		assertNotNull(possibleMoves);
		assertTrue(possibleMoves.size() == 2);
	}

	@Test
	public void testPossibleMovesE1() {
		List<Coord> possibleMoves = chessEngine.possibleMoves(handle, new Coord("e1"));
		assertNotNull(possibleMoves);
		assertTrue(possibleMoves.isEmpty());
	}

	@Test
	public void testPossibleMovesB1() {
		List<Coord> possibleMoves = chessEngine.possibleMoves(handle, new Coord("b1"));
		assertNotNull(possibleMoves);
		assertTrue(possibleMoves.size() == 2);
	}

	@Test
	public void testPossibleMovesInvalid() {
		exception.expect(ChessEngineException.class);
		exception.expectMessage("No figure found at source field!");
		chessEngine.possibleMoves(handle, new Coord("e4"));
	}
	
	@Test
	public void testImpossibleMoves() {
		List<Coord> emptyMoves = chessEngine.possibleMoves(handle, new Coord("e1"));
		assertTrue(emptyMoves.isEmpty());
	}
	
}
