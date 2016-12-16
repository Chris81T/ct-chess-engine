package de.chrthms.chess.engine.core.constants;

/**
 * Only for statistic analysis relevant
 * 
 * Normally a figure has the value REGARDLESS like the king, queen and the pawns. A knight, a bishop or a root exists
 * twice times for each color. One at the left and one at the right.
 * 
 * So this flag makes it possible to determine the desired figures of the player.
 * 
 * @author Christian Thomas
 *
 */
public abstract class FigurePosition {

	public static final int REGARDLESS = 0;
	public static final int LEFT_SIDE  = 1;
	public static final int RIGHT_SIDE = 2;
	
}
