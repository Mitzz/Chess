package org.mitz.chess;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Tile;

public class GameMovement {

	@Test
	public void game1() {
		Game game = new Game();
		Board board = game.getBoard();
		Tile sourceTile = null;
		Tile targetTile = null;
		Piece sourcePiece = null;
		Piece targetPiece = null;
		
		int sourceRank = -1;
		char sourceFile = 'a';
		
		int targetRank = -1;
		char targetFile = 'b';
		
		sourceRank = 2;
		sourceFile = 'd';
		
		targetRank = 3;
		targetFile = 'd';
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		boolean isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);

		sourceRank = 7;
		sourceFile = 'd';
		
		targetRank = 6;
		targetFile = 'd';
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);

		sourceRank = 2;
		sourceFile = 'e';
		
		targetRank = 4;
		targetFile = 'e';
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);

		sourceRank = 6;
		sourceFile = 'd';
		
		targetRank = 5;
		targetFile = 'd';
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement valid", isMoveValid);
		
		assertTrue("Source Tile must be Empty", sourceTile.isEmpty());
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);
		
		sourceRank = 4;
		sourceFile = 'e';
		
		targetRank = 6;
		targetFile = 'e';
		
		sourceTile = board.getTileAt(sourceRank, sourceFile);
		sourcePiece = sourceTile.getPiece();
		targetTile = board.getTileAt(targetRank, targetFile);

		isMoveValid = game.move(sourceRank, sourceFile, targetRank, targetFile);
		assertTrue("Movement Invalid", !isMoveValid);
		
		assertTrue("Source Tile must not be Empty", !sourceTile.isEmpty());
		assertTrue("Target Tile must be Empty", targetTile.isEmpty());
		
		targetPiece = targetTile.getPiece();
		assertNotSame("Source and Target Piece must not be same after invalid valid movement", sourcePiece, targetPiece);
	}
}
