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

public class GameMovement {
	
	private Game game;
	private Board board;
	
	@Before
	public void setUp() {
		game = new Game();
		board = game.getBoard();
	}
	
	public void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile sourceTile = null;
		Tile targetTile = null;
		Piece sourcePiece = null;
		Piece targetPiece = null;
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		boolean isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);
	}
	
	public void invalidPieceMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile sourceTile = null;
		Tile targetTile = null;
		Piece sourcePiece = null;
		Piece targetPiece = null;
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		boolean isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement Invalid", !isMoveValid);	
		
		assertTrue("Source Tile must not be Empty", !sourceTile.isEmpty());
		assertTrue("Target Tile must be Empty", targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertNotSame("Source and Target Piece must not be same after invalid valid movement", sourcePiece, targetPiece);	
	}
	
	@Test
	public void game1() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		invalidPieceMovement('e', 4, 'e', 6);
	}
}
