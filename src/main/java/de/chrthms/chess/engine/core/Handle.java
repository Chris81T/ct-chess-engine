/*
 *    ct-chess-engine, a chess engine playing and evaluating chess moves.
 *    Copyright (C) 2016-2017 Christian Thomas
 *
 *    This program ct-chess-engine is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.chrthms.chess.engine.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.chrthms.chess.engine.core.constants.ColorType;
import de.chrthms.chess.engine.core.constants.GameState;
import de.chrthms.chess.engine.exceptions.ChessEngineException;

/**
 * This is the only stateful (including the member in this class) instance of the engine.
 *
 * @author Christian Thomas
 */
public class Handle implements Serializable {

    private int gameState = GameState.UNKNOWN;
    private int activePlayer = ColorType.UNKNOWN;

    /**
     * A List instead of a Set will preserve the order
     */
    private List<MoveResult> moveResults = new ArrayList<>();

    private Map<String, Field> fields = new HashMap<>();

    public boolean isGameOver() {
        if (gameState == GameState.UNKNOWN) throw new ChessEngineException("Unknown GameState!");
        return GameState.isGameOver(gameState);
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public List<MoveResult> getMoveResults() {
        return moveResults;
    }

    public void setMoveResults(List<MoveResult> moveResults) {
        this.moveResults = moveResults;
    }

    public void addMoveResult(MoveResult moveResult) {
        moveResults.add(moveResult);
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public void setFields(Map<String, Field> fields) {
        this.fields = fields;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + activePlayer;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        result = prime * result + gameState;
        result = prime * result + ((moveResults == null) ? 0 : moveResults.hashCode());
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
        Handle other = (Handle) obj;
        if (activePlayer != other.activePlayer)
            return false;
        if (fields == null) {
            if (other.fields != null)
                return false;
        } else if (!fields.equals(other.fields))
            return false;
        if (gameState != other.gameState)
            return false;
        if (moveResults == null) {
            if (other.moveResults != null)
                return false;
        } else if (!moveResults.equals(other.moveResults))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Handle [gameState=" + gameState + ", activePlayer=" + activePlayer + ", moveResults=" + moveResults
                + ", fields=" + fields + "]";
    }

}
