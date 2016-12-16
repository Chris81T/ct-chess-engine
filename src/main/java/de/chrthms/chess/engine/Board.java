package de.chrthms.chess.engine;

import java.util.List;

import de.chrthms.chess.engine.core.Coord;
import de.chrthms.chess.engine.core.Field;
import de.chrthms.chess.engine.core.Handle;
import de.chrthms.chess.engine.core.figures.AbstractFigure;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

public interface Board {

	/**
	 * Will prepare given handle
	 * 
	 * @param handle
	 * @return the handle
	 * @throws ChessEngineException
	 */
	Handle newGame(Handle handle) throws ChessEngineException;

	/**
	 * Get field for given coord.
	 * 
	 * @param handle
	 * @param coord
	 * @return
	 * @throws ChessEngineException
	 */
	Field getField(Handle handle, Coord coord) throws ChessEngineException;
	
	/**
	 * Get field for given numeric coordinates. Is relevant for inner recursive operation for instance.
	 * 
	 * @param handle
	 * @param x allowed values are 1 to 8
	 * @param y allowed values are 1 to 8
	 * @return
	 * @throws ChessEngineException
	 */
	Field getField(Handle handle, int x, int y) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param colorType
	 * @return
	 * @throws ChessEngineException
	 */
	List<Field> getOccupiedFields(Handle handle, int colorType) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param colorType
	 * @return
	 * @throws ChessEngineException
	 */
	List<Field> getEnemyOccupiedFields(Handle handle, int colorType) throws ChessEngineException;
	
	/**
	 * 
	 * @param handle
	 * @param colorType
	 * @return
	 * @throws ChessEngineException
	 */
	Field getKingsField(Handle handle, int colorType) throws ChessEngineException;
	
	/**
	 * 
	 * @param fromField
	 * @param toField
	 * @param simulatedMove the overloaded method wont be simulated
	 * @return may hit figure (null (no hit) or a figure instance is given)
	 * @throws ChessEngineException
	 */
	AbstractFigure moveFigure(Field fromField, Field toField, boolean simulatedMove) throws ChessEngineException;
	AbstractFigure moveFigure(Field fromField, Field toField) throws ChessEngineException;
	
	/**
	 * 
	 * @param field
	 * @param figureType
	 * @param colorType
	 * @return
	 * @throws ChessEngineException
	 */
	AbstractFigure createNewFigureTo(Field field, int figureType, int colorType) throws ChessEngineException;
	
}
