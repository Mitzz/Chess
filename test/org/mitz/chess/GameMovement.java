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
	
	public void invalidPieceMovementDueToOpponentPiece(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
		assertTrue("Source and Target Piece are same", board.getTileAt(sourceRank, sourceFile).getPiece().getColor() != board.getTileAt(targetRank, targetFile).getPiece().getColor());
	}

	private void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
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

	
	@Test
	public void game1() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		invalidPieceMovement('e', 4, 'e', 6);
	}
	
	@Test
	public void game2() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		invalidPieceMovement('e', 4, 'e', 6);
		simpleValidMovement('d', 3, 'd', 4);
		invalidPieceMovementDueToOpponentPiece('d', 5, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
	}
	
	@Test
	public void game3KnightMovement() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		invalidPieceMovement('e', 4, 'e', 6);
		simpleValidMovement('d', 3, 'd', 4);
		invalidPieceMovementDueToOpponentPiece('d', 5, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'h', 6);
		simpleValidMovement('f', 3, 'd', 4);
		simpleValidMovement('h', 6, 'f', 5);
		simpleValidMovement('d', 4, 'e', 6);
		simpleValidMovement('f', 5, 'h', 4);
		simpleValidMovement('e', 6, 'f', 4);
		simpleValidMovement('h', 4, 'f', 3);
//		invalidPieceMovementDueToCheck(game, 'f', 4, 'h', 3);
//		invalidPieceMovementDueToSamePlayer(game, 'f', 3, 'd', 2);
//		invalidPieceMovementDueToCheck(game, 'h', 3, 'f', 2);

	}
	
	@Test
	public void game4BishopMovement() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		simpleValidMovement('d', 3, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'h', 6);
		simpleValidMovement('f', 3, 'd', 4);
		simpleValidMovement('h', 6, 'f', 5);

		simpleValidMovement('d', 4, 'e', 6);
		simpleValidMovement('f', 5, 'h', 4);
		simpleValidMovement('e', 6, 'f', 4);
		invalidPieceMovementDueToUserPiece('f', 8, 'e', 7);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('f', 1, 'c', 4);
		killValidMovement('f', 8, 'c', 5);
		killValidMovement('c', 4, 'e', 6);
		killValidMovement('c', 8, 'e', 6);
		killValidMovement('f', 4, 'e', 6);
	}
	
	@Test
	public void game5QueenMovement() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		simpleValidMovement('d', 3, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'h', 6);
		simpleValidMovement('f', 3, 'd', 4);
		simpleValidMovement('h', 6, 'f', 5);
		simpleValidMovement('d', 4, 'e', 6);
		simpleValidMovement('f', 5, 'h', 4);
		simpleValidMovement('e', 6, 'f', 4);
		invalidPieceMovementDueToUserPiece('f', 8, 'e', 7);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('f', 1, 'c', 4);
		killValidMovement('f', 8, 'c', 5);
		killValidMovement('c', 4, 'e', 6);
		killValidMovement('c', 8, 'e', 6);
		killValidMovement('f', 4, 'e', 6);
		
		invalidPieceMovementDueToUserPiece('d', 8, 'b', 8);
		simpleValidMovement('d', 8, 'c', 8);
		simpleValidMovement('d', 1, 'g', 4);
		killValidMovement('c', 8, 'e', 6);
		invalidPieceMovementDueToUserPiece('g', 4, 'g', 2);
		killValidMovement('g', 4, 'g', 7);
	}
	
	@Test
	public void game6KingMovement() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		simpleValidMovement('d', 3, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'h', 6);
		simpleValidMovement('f', 3, 'd', 4);
		simpleValidMovement('h', 6, 'f', 5);
		simpleValidMovement('d', 4, 'e', 6);
		simpleValidMovement('f', 5, 'h', 4);
		simpleValidMovement('e', 6, 'f', 4);
		invalidPieceMovementDueToUserPiece('f', 8, 'e', 7);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('f', 1, 'c', 4);
		killValidMovement('f', 8, 'c', 5);
		killValidMovement('c', 4, 'e', 6);
		killValidMovement('c', 8, 'e', 6);
		killValidMovement('f', 4, 'e', 6);
		invalidPieceMovementDueToUserPiece('d', 8, 'b', 8);
		simpleValidMovement('d', 8, 'c', 8);
		simpleValidMovement('d', 1, 'g', 4);
		killValidMovement('c', 8, 'e', 6);
		invalidPieceMovementDueToUserPiece('g', 4, 'g', 2);
		killValidMovement('g', 4, 'g', 7);
		
		invalidPieceMovementDueToUserPiece('e', 8, 'f', 7);
		simpleValidMovement('e', 8, 'e', 7);
		simpleValidMovement('e', 1, 'd', 2);
		simpleValidMovement('e', 7, 'd', 7);
		simpleValidMovement('d', 2, 'd', 1);
		invalid('d', 7, 'd', 5);
		invalid('d', 7, 'e', 6);
		simpleValidMovement('d', 7, 'd', 6);
		invalid('d', 1, 'h', 5);
		invalid('d', 1, 'h', 4);
		invalid('d', 1, 'e', 3);
		simpleValidMovement('d', 1, 'e', 1);
	}
	
	@Test
	public void game7RootMovement() {
		simpleValidMovement('d', 2, 'd', 3);
		simpleValidMovement('d', 7, 'd', 6);
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 6, 'd', 5);
		simpleValidMovement('d', 3, 'd', 4);
		simpleValidMovement('c', 7, 'c', 5);
		killValidMovement('d', 4, 'c', 5);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'h', 6);
		simpleValidMovement('f', 3, 'd', 4);
		simpleValidMovement('h', 6, 'f', 5);
		simpleValidMovement('d', 4, 'e', 6);
		simpleValidMovement('f', 5, 'h', 4);
		simpleValidMovement('e', 6, 'f', 4);
		invalidPieceMovementDueToUserPiece('f', 8, 'e', 7);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('f', 1, 'c', 4);
		killValidMovement('f', 8, 'c', 5);
		killValidMovement('c', 4, 'e', 6);
		killValidMovement('c', 8, 'e', 6);
		killValidMovement('f', 4, 'e', 6);
		invalidPieceMovementDueToUserPiece('d', 8, 'b', 8);
		simpleValidMovement('d', 8, 'c', 8);
		simpleValidMovement('d', 1, 'g', 4);
		killValidMovement('c', 8, 'e', 6);
		invalidPieceMovementDueToUserPiece('g', 4, 'g', 2);
		killValidMovement('g', 4, 'g', 7);
		invalidPieceMovementDueToUserPiece('e', 8, 'f', 7);
		simpleValidMovement('e', 8, 'e', 7);
		simpleValidMovement('e', 1, 'd', 2);
		simpleValidMovement('e', 7, 'd', 7);
		simpleValidMovement('d', 2, 'd', 1);
		invalid('d', 7, 'd', 5);
		invalid('d', 7, 'e', 6);
		simpleValidMovement('d', 7, 'd', 6);
		invalid('d', 1, 'h', 5);
		invalid('d', 1, 'h', 4);
		invalid('d', 1, 'e', 3);
		simpleValidMovement('d', 1, 'e', 1);
		
		invalidPieceMovementDueToUserPiece('h', 8, 'b', 8);
		invalidPieceMovementDueToUserPiece('h', 8, 'h', 7);
		invalid('h', 8, 'g', 7);
		invalid('h', 8, 'f', 7);
		simpleValidMovement('h', 8, 'c', 8);
		simpleValidMovement('h', 1, 'f', 1);
		invalidPieceMovementDueToUserPiece('c', 8, 'c', 5);
		invalid('c', 8, 'd', 7);
		invalid('c', 8, 'e', 7);
		invalid('c', 8, 'g', 7);
		simpleValidMovement('c', 8, 'c', 7);
		simpleValidMovement('f', 2, 'f', 4);
		//Below Move Can be used for Testing en passant
		simpleValidMovement('e', 4, 'e', 3);
		simpleValidMovement('f', 1, 'f', 3);
		simpleValidMovement('f', 7, 'f', 5);
		killValidMovement('f', 3, 'e', 3);
		killValidMovement('c', 7, 'g', 7);
		simpleValidMovement('g', 2, 'g', 4);
		killValidMovement('c', 5, 'e', 3);
	}

	private void invalidPieceMovementDueToUserPiece(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
		assertTrue("Source and Target Piece are not same", board.getTileAt(sourceRank, sourceFile).getPiece().getColor() == board.getTileAt(targetRank, targetFile).getPiece().getColor());
	}

	private void killValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile targetTile = board.getTileAt(targetRank, targetFile);
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
	}
}
