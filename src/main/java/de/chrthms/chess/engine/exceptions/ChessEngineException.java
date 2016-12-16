package de.chrthms.chess.engine.exceptions;

public class ChessEngineException extends RuntimeException {

	public ChessEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChessEngineException(String message) {
		super(message);
	}

}
