package de.chrthms.chess.engine.core;

import java.io.Serializable;

import de.chrthms.chess.engine.core.figures.AbstractFigure;

public class Field implements Serializable {

	private final int fieldType;
	
	private final Coord coord;

	private AbstractFigure figure = null;
	
	public Field(int fieldType, Coord coord) {
		this.fieldType = fieldType;
		this.coord = coord;
	}

	public Field(int fieldType, Coord coord, AbstractFigure figure) {
		this.fieldType = fieldType;
		this.coord = coord;
		this.figure = figure;
	}
	
	public AbstractFigure getFigure() {
		return figure;
	}

	public void setFigure(AbstractFigure figure) {
		this.figure = figure;
	}

	public void clearFigure() {
		setFigure(null);
	}
	
	public Coord getCoord() {
		return coord;
	}
	
	public boolean isEmpty() {
		return figure == null;
	}
	
	public boolean isOccupied() {
		return !isEmpty();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		result = prime * result + fieldType;
		result = prime * result + ((figure == null) ? 0 : figure.hashCode());
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
		Field other = (Field) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		if (fieldType != other.fieldType)
			return false;
		if (figure == null) {
			if (other.figure != null)
				return false;
		} else if (!figure.equals(other.figure))
			return false;
		return true;
	}

	public String toBoardString() {
		return figure == null ? "[ ]" : "[" + figure.toBoardString() + "]";
	}
	
	@Override
	public String toString() {
		return "Field [fieldType=" + fieldType + ", coord=" + coord + ", figure=" + figure + "]";
	}
		
}
