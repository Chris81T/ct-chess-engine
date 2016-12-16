package de.chrthms.chess.engine;

import java.util.List;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.GameData;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.MoveResult;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public interface ChessEngine {

	/**
	 * Every component is stateless. So everytime the handle (the concrete game) is expected
	 * 
	 * @return a stateful handle. It is possible to create multiple handles (parallel usage) 
	 * @throws ChessEngineException
	 */
	Handle newGame() throws ChessEngineException;
	
	/**
	 * 
	 * @param gameData with serialized game information
	 * @return
	 * @throws ChessEngineException
	 */
	Handle loadGame(GameData gameData) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @return serialized game data object
	 * @throws ChessEngineException
	 */
	GameData saveGame(Handle handle) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param from
	 * @param to
	 * @return
	 * @throws ChessEngineException (runtime exception) if the move coordinates are invalid. Also an appropriate state
	 * is set in the MoveResult 
	 */
	MoveResult moveTo(Handle handle, Coord from, Coord to) throws ChessEngineException;
	
	/**
	 * Only relevant for the pawn transformation procedure
	 * 
	 * @param currentMoveResult
	 * @param newFigureType
	 * @return
	 * @throws ChessEngineException
	 */
	MoveResult newFigureDecision(MoveResult currentMoveResult, final int newFigureType) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param moveResult
	 * @return the actual state in form of the magic constant GameState after that movement.
	 * @throws ChessEngineException
	 */
	int completeMoveTo(Handle handle, MoveResult moveResult) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param from
	 * @return
	 * @throws ChessEngineException
	 */
	List<Coord> possibleMoves(Handle handle, Coord from) throws ChessEngineException;
	
}
