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
		
		castlingValidMovement('e', 1, 'g', 1);
	}
	
	@Test
	public void whiteKingsideCastlingNonvalidMovementDueToRookMovement() {
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
	
	@Test
	public void whiteKingsideCastlingNonvalidMovementDueToKingMovement() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 5);
		simpleValidMovement('e', 1, 'f', 1);
		simpleValidMovement('b', 8, 'c', 6);
		simpleValidMovement('f', 1, 'e', 1);
		simpleValidMovement('d', 7, 'd', 5);
		invalid('e', 1, 'g', 1);
	}
	
	@Test
	public void whiteKingsideCastlingNonvalidMovementDueToKingCheck() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 6);
		simpleValidMovement('d', 2, 'd', 4);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('e', 3, 'e', 4);
		simpleValidMovement('f', 8, 'b', 4);
		invalid('e', 1, 'g', 1);
	}
	
	@Test
	public void whiteQueensideCastlingValidMovement() {
		simpleValidMovement('d', 2, 'd', 4);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('c', 1, 'e', 3);
		simpleValidMovement('f', 8, 'e', 7);
		simpleValidMovement('d', 1, 'd', 3);
		simpleValidMovement('b', 8, 'a', 6);
		simpleValidMovement('b', 1, 'c', 3);
		simpleValidMovement('g', 8, 'f', 6);
		castlingValidMovement('e', 1, 'c', 1);
		
	}
	
	@Test
	public void whiteQueensideCastlingNonvalidMovementDueToKingCheck() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 6);
		simpleValidMovement('d', 2, 'd', 4);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('e', 3, 'e', 4);
		simpleValidMovement('f', 8, 'd', 6);
		simpleValidMovement('b', 1, 'a', 3);
		simpleValidMovement('d', 6, 'f', 8);
		simpleValidMovement('d', 1, 'd', 3);
		simpleValidMovement('f', 8, 'd', 6);
		simpleValidMovement('c', 1, 'e', 3);
		simpleValidMovement('d', 6, 'b', 4);
		invalid('e', 1, 'c', 1);
		game.render();
	}
	
	private void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.invalid(sourceFile, sourceRank, targetFile, targetRank);
	}
	
	private void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
	
	private void castlingValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.castlingValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
}
