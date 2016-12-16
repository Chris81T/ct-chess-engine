package de.chrthms.chess.engine.core;

import java.io.Serializable;

import de.chrthms.chess.engine.core.constants.FigureType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;

/**
 * if a pawn transformation is possible, the engine will instantiate this class to provide the origin pawn
 * 
 * @author Christian Thomas
 *
 */
public class PawnTransformation implements Serializable {

	/**
	 * this is the decision, the client has to perform
	 */
	private int newFigureType = FigureType.UNKNOWN; 
	
	/**
	 * will be set by the engine, if the movement is completed
	 */
	private AbstractFigure newFigure = null;

	private final AbstractFigure originPawn;

	public PawnTransformation(AbstractFigure originPawn) {
		this.originPawn = originPawn;
	}
	
	public void setNewFigureType(int newFigureType) {
		this.newFigureType = newFigureType;
	}

	public int getNewFigureType() {
		return newFigureType;
	}

	public AbstractFigure getNewFigure() {
		return newFigure;
	}
	
	public void setNewFigure(AbstractFigure newFigure) {
		this.newFigure = newFigure;
	}

	public AbstractFigure getOriginPawn() {
		return originPawn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((newFigure == null) ? 0 : newFigure.hashCode());
		result = prime * result + newFigureType;
		result = prime * result + ((originPawn == null) ? 0 : originPawn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PawnTransformation other = (PawnTransformation) obj;
		if (newFigure == null) {
			if (other.newFigure != null)
				return false;
		} else if (!newFigure.equals(other.newFigure))
			return false;
		if (newFigureType != other.newFigureType)
			return false;
		if (originPawn == null) {
			if (other.originPawn != null)
				return false;
		} else if (!originPawn.equals(other.originPawn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PawnTransformation [newFigureType=" + newFigureType + ", newFigure=" + newFigure + ", originPawn="
				+ originPawn + "]";
	}
	
}
