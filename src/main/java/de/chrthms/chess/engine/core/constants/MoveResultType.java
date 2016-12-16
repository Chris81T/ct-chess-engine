package de.chrthms.chess.engine.core.constants;

/**
 * Why not using enum instead of this one?
 * 
 * (source) https://infinum.co/the-capsized-eight/magic-constants-in-android-development
 * 
 * @author Christian Thomas
 *
 */
public abstract class MoveResultType extends AbstractType {

	public static final int OK = 0;
	public static final int DECISION_NEEDED = 1;
	
}
