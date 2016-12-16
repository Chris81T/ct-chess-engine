package de.chrthms.chess.engine.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoordTest {

	@Test
	public void testNewCoord() {
		
		Coord coord = new Coord("c6");
		
		assertEquals("c", coord.getX());
		assertEquals("6", coord.getY());
		
	}
	
}
