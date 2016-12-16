package de.chrthms.chess.engine.core;

import java.io.Serializable;

import de.chrthms.chess.engine.core.constants.CastlingType;
import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.MoveResultType;
import de.chrthms.chess.engine.core.figures.AbstractFigure;

public class MoveResult implements Serializable {

	// magic constants (android performance reasons)
	private int moveResultType = MoveResultType.UNKNOWN;
	private int movedColorType = ColorType.UNKNOWN;
	private int castlingType = CastlingType.NO_CASTLING; 

	// simple member
	private Coord fromField = null;
	private Coord toField = null;
	
	// object references
	private AbstractFigure movedFigure = null;
	private AbstractFigure hitFigure = null;
	
	/**
	 * if a pawn transformation is possible, this field will be instantiated by the engine
	 */
	private PawnTransformation pawnTransformation = null;

	public boolean isPawnTransformation() {
		return pawnTransformation != null;
	}

	public int getMoveResultType() {
		return moveResultType;
	}

	public void setMoveResultType(int moveResultType) {
		this.moveResultType = moveResultType;
	}

	public int getMovedColorType() {
		return movedColorType;
	}

	/**
	 * see setMoveFigure...
	 * @param movedColorType
	 */
	private void setMovedColorType(int movedColorType) {
		this.movedColorType = movedColorType;
	}

	public int getCastlingType() {
		return castlingType;
	}

	public void setCastlingType(int castlingType) {
		this.castlingType = castlingType;
	}

	public Coord getFromField() {
		return fromField;
	}

	public void setFromField(Coord fromField) {
		this.fromField = fromField;
	}

	public Coord getToField() {
		return toField;
	}

	public void setToField(Coord toField) {
		this.toField = toField;
	}

	public AbstractFigure getMovedFigure() {
		return movedFigure;
	}

	public void setMovedFigure(AbstractFigure movedFigure) {
		this.movedFigure = movedFigure;
		setMovedColorType(movedFigure.getColorType());
	}

	public AbstractFigure getHitFigure() {
		return hitFigure;
	}

	public void setHitFigure(AbstractFigure hitFigure) {
		this.hitFigure = hitFigure;
	}

	public PawnTransformation getPawnTransformation() {
		return pawnTransformation;
	}

	public void setPawnTransformation(PawnTransformation pawnTransformation) {
		this.pawnTransformation = pawnTransformation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + castlingType;
		result = prime * result + ((fromField == null) ? 0 : fromField.hashCode());
		result = prime * result + ((hitFigure == null) ? 0 : hitFigure.hashCode());
		result = prime * result + moveResultType;
		result = prime * result + movedColorType;
		result = prime * result + ((movedFigure == null) ? 0 : movedFigure.hashCode());
		result = prime * result + ((pawnTransformation == null) ? 0 : pawnTransformation.hashCode());
		result = prime * result + ((toField == null) ? 0 : toField.hashCode());
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
		MoveResult other = (MoveResult) obj;
		if (castlingType != other.castlingType)
			return false;
		if (fromField == null) {
			if (other.fromField != null)
				return false;
		} else if (!fromField.equals(other.fromField))
			return false;
		if (hitFigure == null) {
			if (other.hitFigure != null)
				return false;
		} else if (!hitFigure.equals(other.hitFigure))
			return false;
		if (moveResultType != other.moveResultType)
			return false;
		if (movedColorType != other.movedColorType)
			return false;
		if (movedFigure == null) {
			if (other.movedFigure != null)
				return false;
		} else if (!movedFigure.equals(other.movedFigure))
			return false;
		if (pawnTransformation == null) {
			if (other.pawnTransformation != null)
				return false;
		} else if (!pawnTransformation.equals(other.pawnTransformation))
			return false;
		if (toField == null) {
			if (other.toField != null)
				return false;
		} else if (!toField.equals(other.toField))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MoveResult [moveResultType=" + moveResultType + ", movedColorType=" + movedColorType + ", castlingType="
				+ castlingType + ", fromField=" + fromField + ", toField=" + toField + ", movedFigure=" + movedFigure
				+ ", hitFigure=" + hitFigure + ", pawnTransformation=" + pawnTransformation + "]";
	}
	
}
