package org.mitz.chess;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.King;
import org.mitz.chess.model.Knight;
import org.mitz.chess.model.Pawn;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Queen;
import org.mitz.chess.model.Rook;
import org.mitz.chess.model.Tile;

public class StalemateTest {
	private Game game;
	private Board board;
	private MovementTestUtility movement;
	private GameStateTestUtility state;
	
	@Before
	public void setUp() {
		game = new Game();
		board = game.getBoard();
		movement = new MovementTestUtility(game, board);
		state = new GameStateTestUtility(game);
	}
	
	@Test
	public void game1() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		simpleValidMovement('g', 5, 'g', 6);
		
		validateGameOverByStalemate();
	}
	
	@Test
	public void game2() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(5, 'b');
		t.setPiece(new Pawn(Color.BLACK, t));
		
		simpleValidMovement('g', 5, 'g', 6);
		validateGameInProgress();
		
	}
	
	@Test
	public void game3() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(5, 'b');
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt(4, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		simpleValidMovement('g', 5, 'g', 6);
		game.render();
		validateGameOverByStalemate();
		
	}
	
	@Test
	public void game4() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(5, 'b');
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt(4, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt(6, 'b');
		t.setPiece(new Rook(Color.BLACK, t));
		game.render();
		simpleValidMovement('g', 5, 'g', 6);
		
		validateGameInProgress();
		
	}
	
	@Test
	public void game5() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(4, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt(6, 'b');
		t.setPiece(new Rook(Color.BLACK, t));
		game.render();
		simpleValidMovement('g', 5, 'g', 6);
		
		validateGameInProgress();
		game.render();
	}
	
	@Test
	public void game6() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(4, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		simpleValidMovement('g', 5, 'g', 4);
		game.render();
		validateGameInProgress();
		
	}
	
	@Test
	public void game7() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(4, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt(5, 'b');
		t.setPiece(new Knight(Color.BLACK, t));
		
		simpleValidMovement('g', 5, 'g', 6);
		game.render();
		validateGameInProgress();
		
	}
	
	@Test
	public void game8() {
		game.clear();
		Tile t = board.getTileAt(7, 'f');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(5, 'g');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt(8, 'h');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(5, 'b');
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt(3, 'b');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt(2, 'c');
		t.setPiece(new Pawn(Color.WHITE, t));
		
		t = board.getTileAt(1, 'c');
		t.setPiece(new Rook(Color.WHITE, t));
		
		simpleValidMovement('g', 5, 'g', 6);
		validateGameInProgress();
		simpleValidMovement('b', 5, 'b', 4);
		validateGameInProgress();
		simpleValidMovement('c', 2, 'c', 4);
		validateGameInProgress();
		enPassantValidMovement('b', 4, 'c', 3);
		validateGameInProgress();
		simpleValidMovement('b', 3, 'b', 4);
		validateGameInProgress();
		simpleValidMovement('c', 3, 'c', 2);
		validateGameInProgress();
		simpleValidMovement('b', 4, 'b', 5);
		validateGameOverByStalemate();
		game.render();
	}
	
	private void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
	
	public void validateGameOverByStalemate() {
		state.validateGameOverByStalemate();
	}
	
	public void validateGameInProgress() {
		state.validateGameInProgress();
	}

	public void enPassantValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.enPassantValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
}
