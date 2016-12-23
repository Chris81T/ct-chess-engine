package de.chrthms.chess.engine.notations;

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.notations.impl.NotationBuilder;

public class FenTest extends AbstractBeforeTest {
		
	private void printResult(final String expectedFen, final String generatedFen, final String label) {
		System.out.println("FEN Test Result: (" + label + ")");
		System.out.println("expected  : " + expectedFen);
		System.out.println("generated : " + generatedFen);
		System.out.println();
	}
	
	@Test
	public void testGetFenAfterNewGame() {
		
		final String expectedFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		String fen = NotationBuilder
			.getForsythEdwardsNotation()
			.getFen(handle);
		
		printResult(expectedFen, fen, "testGetFenAfterNewGame");
		assertTrue(expectedFen.equals(fen));
		
	}

	@Test
	public void testGetFenAfterTwoSimpleMoves() {

		moveToAndComplete("e2", "e4");
		moveToAndComplete("b8", "c6");
		
		final String expectedFen = "r1bqkbnr/pppppppp/2n5/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 1 2";
		String fen = NotationBuilder
			.getForsythEdwardsNotation()
			.getFen(handle);
		
		printResult(expectedFen, fen, "testGetFenAfterTwoSimpleMoves");
		assertTrue(expectedFen.equals(fen));
		
	}
	
	@Test
	public void testHalfmoves() {

		moveToAndComplete("b1", "a3");
		moveToAndComplete("b8", "c6");
		moveToAndComplete("g1", "f3");
		moveToAndComplete("g8", "h6");
		moveToAndComplete("a3", "c4");
		
		final String expectedFen = "r1bqkb1r/pppppppp/2n4n/8/2N5/5N2/PPPPPPPP/R1BQKB1R b KQkq - 5 3";
		String fen = NotationBuilder
			.getForsythEdwardsNotation()
			.getFen(handle);
		
		printResult(expectedFen, fen, "testHalfmoves");
		assertTrue(expectedFen.equals(fen));
		
	}
	
	@Test
	public void testGetFenCastlingEnPassant() {

		moveToAndComplete("e2", "e4");
		moveToAndComplete("b8", "c6");
		moveToAndComplete("e4", "e5");
		moveToAndComplete("a8", "b8"); // black player has moved left rook - no queenside castling option
		moveToAndComplete("e1", "e2"); // white player has moved the king - no castling option
		moveToAndComplete("d7", "d5"); // next player is white and should be able to play en passant
		
		final String expectedFen = "1rbqkbnr/ppp1pppp/2n5/3pP3/8/8/PPPPKPPP/RNBQ1BNR w k d6 0 4";
		String fen = NotationBuilder
			.getForsythEdwardsNotation()
			.getFen(handle);
		
		printResult(expectedFen, fen, "testGetFenCastlingEnPassant");
		assertTrue(expectedFen.equals(fen));
		
	}
	
}
