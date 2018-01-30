package org.mitz.chess;

import static org.junit.Assert.assertTrue;

import org.mitz.chess.model.Game;
import org.mitz.chess.model.GameState;

public class GameStateTestUtility {
	
	private Game game;

	public GameStateTestUtility(Game game) {
		this.game = game;
	}

	public void validateGameOverByCheckmate() {
		assertTrue("Game must be over due to checkmate", GameState.OVER_DUE_TO_CHECKMATE == game.getStatus());
	}
	
	public void validateGameOverByStalemate() {
		assertTrue("Game must be over due to stalemate", GameState.OVER_DUE_TO_STALEMATE == game.getStatus());
	}
	
	public void validateGameInProgress() {
		assertTrue("Game must be in progress", GameState.IN_PROGRESS == game.getStatus());
	}
}
