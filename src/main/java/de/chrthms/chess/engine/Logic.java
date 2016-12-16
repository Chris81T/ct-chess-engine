package de.chrthms.chess.engine;

import java.util.List;

import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public interface Logic {

	/**
	 * Will prepare given handle for a new game.
	 * 
	 * @param handle
	 * @return the handle
	 * @throws ChessEngineException
	 */
	Handle newGame(Handle handle) throws ChessEngineException;
	
	/**
	 * Will check the game state.
	 * 
	 * @param handle
	 * @param colorType
	 * @return the actual state in form of the magic constant GameState
	 * @throws ChessEngineException
	 */
	int checkGameState(Handle handle, int colorType) throws ChessEngineException;
	
	/**
	 * Check, if given kingsField can be checked by any enemy figure.
	 * 
	 * @param handle
	 * @param kingsField
	 * @param ignoreFinalMovesCheckup OPTIONAL! Normally it can be false! Check the
     *        common mod -> finalPossibleMovesCheckup documentation / code. There it is the only position, where
     *        it is relevant and the flag is set to true.
	 * @return
	 * @throws ChessEngineException
	 */
	boolean isChecked(Handle handle, Field kingsField, boolean ignoreFinalMovesCheckup) throws ChessEngineException;
	boolean isChecked(Handle handle, Field kingsField) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param sourceField
	 * @return an empty list are a list with fields, where the figure can move to
	 * @throws ChessEngineException if the sourceField does not contain a figure
	 */
	List<Field> possibleMoves(Handle handle, Field sourceField) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param sourceField
	 * @param destField
	 * @return
	 * @throws ChessEngineException
	 */
	MoveResult performMoveTo(Handle handle, Field sourceField, Field destField) throws ChessEngineException;

	/**
	 * 
	 * @param handle
	 * @return
	 */
	MoveResult getLastMoveResult(Handle handle);
	
}
