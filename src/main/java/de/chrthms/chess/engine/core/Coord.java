package de.chrthms.chess.engine.core;

import java.io.Serializable;

import de.chrthms.chess.engine.exceptions.ChessEngineException;

public class Coord implements Serializable {

	private final String x;
	private final String y;

	private void validate() {
		// TODO make it more robust: is x a..h? is y a number 1..8?  
	}
	
	public Coord(String coord) {
		if (coord.length() != 2) {
			throw new ChessEngineException("Given coord = " + coord + " is wrong! Coord like a5 or b7 are expected");
		}
		
		char x = coord.charAt(0);
		char y = coord.charAt(1);
	
		this.x = String.valueOf(x);
		this.y = String.valueOf(y);
		validate();
	}
	
	public Coord(String x, String y) {
		this.x = x;
		this.y = y;
		validate();
	}
	
	public Coord(int x, int y) {
		switch(x) {
		case 1:
			this.x = "a";
			break;
		case 2:
			this.x = "b";
			break;
		case 3:
			this.x = "c";
			break;
		case 4:
			this.x = "d";
			break;
		case 5:
			this.x = "e";
			break;
		case 6:
			this.x = "f";
			break;
		case 7:
			this.x = "g";
			break;
		case 8:
			this.x = "h";
			break;
		default:
			this.x = "";
		}
		
		this.y = String.valueOf(y);
		validate();
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public int getNumX() {
		switch(getX()) {
		case "a":
			return 1;
		case "b":
			return 2;
		case "c":
			return 3;
		case "d":
			return 4;
		case "e":
			return 5;
		case "f":
			return 6;
		case "g":
			return 7;
		case "h":
			return 8;
		}
		
		return -1;
	}

	public int getNumY() {
		return Integer.valueOf(getY());
	}
	
	public String getStrCoord() {
		return new StringBuilder()
				.append(getX())
				.append(getY())
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		Coord other = (Coord) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coord [x=" + x + ", y=" + y + "]";
	}
	
}
