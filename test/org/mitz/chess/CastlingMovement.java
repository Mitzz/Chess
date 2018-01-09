package org.mitz.chess;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;

public class CastlingMovement {
	
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
	public void whiteKingsideCastlingValidMovement() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 5);
		simpleValidMovement('e', 1, 'g', 1);
	}
	
	@Test
	public void whiteKingsideCastlingNonvalidMovement() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 5);
		simpleValidMovement('h', 1, 'g', 1);
		simpleValidMovement('h', 7, 'h', 5);
		simpleValidMovement('g', 1, 'h', 1);
		simpleValidMovement('b', 8, 'c', 6);
		invalid('e', 1, 'g', 1);
	}
	
	private void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.invalid(sourceFile, sourceRank, targetFile, targetRank);
	}
	
	private void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
}