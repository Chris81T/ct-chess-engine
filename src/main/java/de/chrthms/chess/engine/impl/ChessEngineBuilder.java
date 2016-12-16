package de.chrthms.chess.engine.impl;

import de.chrthms.chess.engine.Board;
import de.chrthms.chess.engine.ChessEngine;
import de.chrthms.chess.engine.Logic;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class ChessEngineBuilder {

	private static ChessEngine chessEngine = null;
	private static Board board = null;
	private static Logic logic = null;
	
	private static void buildComponents() throws ChessEngineException {
		board = new BoardImpl();
		logic = new LogicImpl(board);
		chessEngine = new ChessEngineImpl(board, logic);		
	}

	private static void checkAvailableEngine() throws ChessEngineException {
		if (chessEngine == null) throw new ChessEngineException("Could not perform operation. Actually no engine built!"); 
	}
	
	public static ChessEngine build() throws ChessEngineException {
		if (chessEngine == null) buildComponents();
		return chessEngine;
	}

	public static Board getBoard() throws ChessEngineException {
		checkAvailableEngine();
		return board;
	}

	public static Logic getLogic() throws ChessEngineException {
		checkAvailableEngine();
		return logic;
	}
	
}
