package de.chrthms.chess.engine.core.constants;

/**
 * Value description:
 * 
 * NORMAL    = game is running normally
 * CHECK     = king is checked (game is running)
 * CHECKMATE = king is checkmate (game over)
 * DEADLOCK  = King is not checked, but no figure can be moved (game over) 
 * DRAWN     = player have drawn the match (manual decision) (game over)
 * 
 * @author Christian Thomas
 *
 */
public abstract class GameState extends AbstractType {

	public static final int NORMAL = 0;
	public static final int CHECK = 1;
	public static final int CHECKMATE = 2;
	public static final int DEADLOCK = 3;
	public static final int DRAWN = 4;
	
	public static boolean isGameOver(final int gameState) {
		return gameState == CHECKMATE || 
				gameState == DEADLOCK || 
				gameState == DRAWN;
	}
	
}
