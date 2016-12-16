package de.chrthms.chess.engine.core;

import java.io.Serializable;
import java.util.Arrays;

/**
 * is relevant for saving or loading a game using the chess engine api. 
 * 
 * @author Christian Thomas
 *
 */
/**
 * @author p0514551
 *
 */
public class GameData implements Serializable {

	private byte[] handle;

	public byte[] getHandle() {
		return handle;
	}

	public void setHandle(byte[] handle) {
		this.handle = handle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(handle);
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
		GameData other = (GameData) obj;
		if (!Arrays.equals(handle, other.handle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameData [handle (length)=" + handle.length + "]";
	}
	
}
