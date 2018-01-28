package org.mitz.chess;

import static org.junit.Assert.assertTrue;

import org.mitz.chess.model.Game;
import org.mitz.chess.model.GameState;

public class GameStateTest {
	
	private Game game;

	public GameStateTest(Game game) {
		this.game = game;
	}

	public void validateGameOver() {
		assertTrue("Game must be over", GameState.OVER == game.getStatus());
	}
}
