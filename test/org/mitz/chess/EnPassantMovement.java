package org.mitz.chess;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.King;
import org.mitz.chess.model.Pawn;
import org.mitz.chess.model.Queen;
import org.mitz.chess.model.Tile;

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
	
	@Test
	public void invalidDueToCheck() {
		game.clear();
		Tile t = board.getTileAt('e', 4);
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt('h', 5);
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt('e', 1);
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt('f', 7);
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt('d', 7);
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt('a', 4);
		t.setPiece(new Queen(Color.BLACK, t));
		
		t = board.getTileAt('a', 8);
		t.setPiece(new King(Color.BLACK, t));
		
		simpleValidMovement('e', 4, 'e', 5);
		simpleValidMovement('a', 4, 'a', 5);
		simpleValidMovement('e', 1, 'e', 3);
		simpleValidMovement('f', 7, 'f', 5);
		invalidPieceMoveDueToCheckByOpponent('e', 5, 'f', 6);
		simpleValidMovement('h', 5, 'h', 4);
		simpleValidMovement('d', 7, 'd', 5);
		invalid('e', 5, 'f', 6);
		simpleValidMovement('e', 5, 'd', 6);
		
		game.render();

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
	
	private void invalidPieceMoveDueToCheckByOpponent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.invalidPieceMoveDueToCheckByOpponent(sourceFile, sourceRank, targetFile, targetRank);
	}
}
