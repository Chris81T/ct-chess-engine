package de.chrthms.chess.engine.notations;

import static org.junit.Assert.*;

import org.junit.Test;

import de.chrthms.chess.engine.AbstractBeforeTest;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.notations.constants.NotationType;
import de.chrthms.chess.engine.notations.impl.NotationBuilder;

public class PgnTest extends AbstractBeforeTest {

//	private void printResult(final String expectedPgn, final String generatedPgn, final String label) {
//		System.out.println("PGN Test Result: (" + label + ")");
//		System.out.println("expected  : " + expectedPgn);
//		System.out.println("generated : " + generatedPgn);
//		System.out.println();
//	}
//
//	@Test
//	public void testGetPgnForHalfmoveE4_SAN() {
//
//		MoveResult one = moveTo("e2", "e4");
//		completeMove(one);
//
//		final String expectedPgn = "1. e4";
//
//		String generatedPgn = NotationBuilder.getPortableGameNotation().getPgnForHalfmove(handle, one, NotationType.SAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForHalfmoveE4_SAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
//
//	@Test
//	public void testGetPgnForMoveA3_D5_SAN() {
//
//		MoveResult white = moveTo("a2", "a3");
//		completeMove(white);
//		MoveResult black = moveTo("d7", "d5");
//		completeMove(black);
//
//		final String expectedPgn = "1. a3 d5";
//
//		String generatedPgn = NotationBuilder
//				.getPortableGameNotation()
//				.getPgnForMove(handle, white, black, NotationType.SAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForMoveA3_D5_SAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
//
//	@Test
//	public void testGetPgnForHalfmoveBlack_D5_SAN() {
//
//		MoveResult white = moveTo("a2", "a3");
//		completeMove(white);
//		MoveResult black = moveTo("d7", "d5");
//		completeMove(black);
//
//		final String expectedPgn = "d5";
//
//		String generatedPgn = NotationBuilder
//				.getPortableGameNotation()
//				.getPgnForHalfmove(handle, black, NotationType.SAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForHalfmoveBlack_D5_SAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
//
//	@Test
//	public void testGetPgnForHalfmoveE4_LAN() {
//
//		MoveResult one = moveTo("e2", "e4");
//		completeMove(one);
//
//		final String expectedPgn = "1. e2e4";
//
//		String generatedPgn = NotationBuilder.getPortableGameNotation().getPgnForHalfmove(handle, one, NotationType.LAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForHalfmoveE4_LAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
//
//	@Test
//	public void testGetPgnForMoveA3_D5_LAN() {
//
//		MoveResult white = moveTo("a2", "a3");
//		completeMove(white);
//		MoveResult black = moveTo("d7", "d5");
//		completeMove(black);
//
//		final String expectedPgn = "1. a2a3 d7d5";
//
//		String generatedPgn = NotationBuilder
//				.getPortableGameNotation()
//				.getPgnForMove(handle, white, black, NotationType.LAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForMoveA3_D5_LAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
//
//	@Test
//	public void testGetPgnForHalfmoveBlack_D5_LAN() {
//
//		MoveResult white = moveTo("a2", "a3");
//		completeMove(white);
//		MoveResult black = moveTo("d7", "d5");
//		completeMove(black);
//
//		final String expectedPgn = "d7d5";
//
//		String generatedPgn = NotationBuilder
//				.getPortableGameNotation()
//				.getPgnForHalfmove(handle, black, NotationType.LAN);
//
//		printResult(expectedPgn, generatedPgn, "testGetPgnForHalfmoveBlack_D5_LAN");
//		assertTrue(expectedPgn.equals(generatedPgn));
//
//	}
}
