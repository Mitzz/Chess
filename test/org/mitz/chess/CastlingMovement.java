package org.mitz.chess;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Tile;

public class CastlingMovement {
	
	private Game game;
	private Board board;
	
	@Before
	public void setUp() {
		game = new Game();
		board = game.getBoard();
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
	
	protected void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile sourceTile = null;
		Tile targetTile = null;
		Piece sourcePiece = null;
		Piece targetPiece = null;
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);
		targetPiece = targetTile.getPiece();
		boolean isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement Invalid", !isMoveValid);	
		assertSame("Source Piece must be same after invalid movement", sourcePiece, sourceTile.getPiece());
		assertSame("Target Piece must be same after invalid movement", targetPiece, targetTile.getPiece());
		
		targetPiece = targetTile.getPiece();
		assertNotSame("Source and Target Piece must not be same after invalid valid movement", sourcePiece, targetPiece);
	}
	
	public void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		valid(sourceFile, sourceRank, targetFile, targetRank);
		
		Tile targetTile = board.getTileAt(targetRank, targetFile);
		Piece targetPiece = targetTile.getPiece();
		Tile sourceTile = board.getTileAt(targetRank, targetFile);
		Piece sourcePiece = sourceTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);
		
	}
	
	private void valid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile sourceTile = null;
		Tile targetTile = null;
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		targetTile = board.getTileAt(targetRank, targetFile);

		boolean isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
	}
}
