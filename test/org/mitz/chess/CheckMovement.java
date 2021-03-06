package org.mitz.chess;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Bishop;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.GameState;
import org.mitz.chess.model.King;
import org.mitz.chess.model.Knight;
import org.mitz.chess.model.Pawn;
import org.mitz.chess.model.Queen;
import org.mitz.chess.model.Rook;
import org.mitz.chess.model.Tile;

public class CheckMovement {

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
	public void checkPathBlock() {
		game.clear();
		Tile t = board.getTileAt('e', 1);
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt('d', 1);
		t.setPiece(new Rook(Color.WHITE, t));
		
		t = board.getTileAt('a', 6);
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt('e', 8);
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt('e', 7);
		t.setPiece(new Knight(Color.BLACK, t));
		
		t = board.getTileAt('f', 7);
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt('f', 8);
		t.setPiece(new Rook(Color.BLACK, t));
		
		t = board.getTileAt('c', 7);
		t.setPiece(new Rook(Color.BLACK, t));
		
		simpleValidMovement('a', 6, 'a', 8);
		validateGameInProgress();
		
		game.render();
	}
	
	@Test
	public void checkPathBlockWithCheckAfterMovement() {
		game.clear();
		Tile t = board.getTileAt('e', 1);
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt('d', 1);
		t.setPiece(new Rook(Color.WHITE, t));
		
		t = board.getTileAt('a', 6);
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt('a', 4);
		t.setPiece(new Bishop(Color.WHITE, t));
		
		t = board.getTileAt('e', 8);
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt('e', 7);
		t.setPiece(new Knight(Color.BLACK, t));
		
		t = board.getTileAt('f', 7);
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt('f', 8);
		t.setPiece(new Rook(Color.BLACK, t));
		
		t = board.getTileAt('c', 6);
		t.setPiece(new Rook(Color.BLACK, t));
		
		simpleValidMovement('a', 6, 'a', 8);
		validateGameInProgress();
		
		game.render();
	}
	
	private void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		movement.simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}

	public void validateGameInProgress() {
		state.validateGameInProgress();
	}
	
	public void validateGameOverByCheckmate() {
		assertTrue("Game must be over due to checkmate", GameState.OVER_DUE_TO_CHECKMATE == game.getStatus());
	}

}
