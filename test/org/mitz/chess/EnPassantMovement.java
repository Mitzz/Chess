package org.mitz.chess;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;

public class EnPassantMovement {

	private Game game;
	private Board board;
	private MovementTestUtility movement;
	
	@Before
	public void setUp() {
		game = new Game();
		board = game.getBoard();
		movement = new MovementTestUtility(game, board);
	}
	
	@Test
	public void valid() {
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 4, 'e', 5);
		simpleValidMovement('f', 7, 'f', 5);
		enPassantValidMovement('e', 5, 'f', 6);

	}
	
	@Test
	public void invalid() {
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 4, 'e', 5);
		simpleValidMovement('f', 7, 'f', 5);
		simpleValidMovement('b', 1, 'a', 3);
		simpleValidMovement('b', 8, 'a', 6);
		invalid('e', 5, 'f', 6);
		render();
	}
	
	private void enPassantValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.enPassantValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}

	private void render() {
		game.render();
	}

	private void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
	
	private void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.invalid(sourceFile, sourceRank, targetFile, targetRank);
	}
}
