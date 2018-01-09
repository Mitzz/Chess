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
	
//	@Test
	public void whiteKingsideCastlingValidMovement() {
		simpleValidMovement('g', 1, 'f', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('e', 2, 'e', 3);
		simpleValidMovement('b', 7, 'b', 6);
		simpleValidMovement('f', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 5);
		simpleValidMovement('e', 1, 'g', 1);
	}
	
//	@Test
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
		game.render();
	}
	
	public void simpleValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		valid(sourceFile, sourceRank, targetFile, targetRank);
		
		Tile targetTile = board.getTileAt(targetRank, targetFile);
		Piece targetPiece = targetTile.getPiece();
		Tile sourceTile = board.getTileAt(targetRank, targetFile);
		Piece sourcePiece = sourceTile.getPiece();
		assertSame("Source and Target Piece not same after valid movement", sourcePiece, targetPiece);
		
	}

	//Refactoring during Pawn Promotion Testing
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
	
	@Test
	public void game() {
		simpleValidMovement('e', 2, 'e', 4);
		simpleValidMovement('d', 7, 'd', 5);
		simpleValidMovement('d', 2, 'd', 4);
		simpleValidMovement('e', 7, 'e', 6);
		simpleValidMovement('c', 2, 'c', 3);
		killValidMovement('d', 5, 'e', 4);
		simpleValidMovement('g', 1, 'e', 2);
		simpleValidMovement('c', 7, 'c', 5);
		simpleValidMovement('c', 1, 'e', 3);
		simpleValidMovement('g', 8, 'f', 6);
		simpleValidMovement('b', 1, 'd', 2);
		simpleValidMovement('b', 8, 'd', 7);
		simpleValidMovement('g', 2, 'g', 3);
		killValidMovement('c', 5, 'd', 4);
		killValidMovement('e', 3, 'd', 4);
		simpleValidMovement('b', 7, 'b', 5);
		simpleValidMovement('f', 1, 'g', 2);
		simpleValidMovement('a', 7, 'a', 5);
		killValidMovement('d', 2, 'e', 4);
		simpleValidMovement('c', 8, 'a', 6);
		killValidMovement('e', 4, 'f', 6);
		killValidMovement('d', 7, 'f', 6);
		killValidMovement('g', 2, 'a', 8);
		killValidMovement('d', 8, 'a', 8);
		killValidMovement('d', 4, 'f', 6);
		killValidMovement('a', 8, 'h', 1);//King Check
		simpleValidMovement('e', 1, 'd', 2);
		simpleValidMovement('h', 1, 'e', 4);
		simpleValidMovement('f', 2, 'f', 3);
		killValidMovement('e', 4, 'f', 3);
		simpleValidMovement('d', 1, 'f', 1);
		killValidMovement('f', 3, 'f', 6);
		killValidMovement('f', 1, 'f', 6);
		killValidMovement('g', 7, 'f', 6);
		simpleValidMovement('e', 2, 'd', 4);
		simpleValidMovement('f', 8, 'd', 6);
		simpleValidMovement('a', 1, 'e', 1);
		simpleValidMovement('e', 6, 'e', 5);
		simpleValidMovement('d', 4, 'c', 6);
		simpleValidMovement('a', 5, 'a', 4);
		simpleValidMovement('c', 6, 'b', 8);
		simpleValidMovement('a', 6, 'b', 7);
		simpleValidMovement('a', 2, 'a', 3);
		simpleValidMovement('d', 6, 'b', 8);
		simpleValidMovement('e', 1, 'e', 3);
		simpleValidMovement('b', 8, 'a', 7);
		simpleValidMovement('e', 3, 'e', 4);		
		simpleValidMovement('b', 7, 'e', 4);
		simpleValidMovement('d', 2, 'd', 1);
		simpleValidMovement('a', 7, 'e', 3);
		simpleValidMovement('d', 1, 'e', 2);
		simpleValidMovement('e', 3, 'g', 5);
		simpleValidMovement('e', 2, 'f', 2);
		simpleValidMovement('g', 5, 'h', 6);
		simpleValidMovement('g', 3, 'g', 4);
		simpleValidMovement('h', 6, 'f', 4);
		simpleValidMovement('h', 2, 'h', 3);
		simpleValidMovement('h', 7, 'h', 5);
		simpleValidMovement('g', 4, 'h', 5);
		simpleValidMovement('f', 6, 'f', 5);
		simpleValidMovement('h', 5, 'h', 6);
		simpleValidMovement('e', 8, 'd', 7);
		simpleValidMovement('h', 6, 'h', 7);
		simpleValidMovement('d', 7, 'd', 6);
//33
		simpleValidMovement('h', 3, 'h', 4);
		simpleValidMovement('h', 8, 'h', 7);
		simpleValidMovement('h', 4, 'h', 5);
		simpleValidMovement('e', 4, 'd', 3);
		simpleValidMovement('h', 5, 'h', 6);
		simpleValidMovement('d', 3, 'c', 4);
//36
		simpleValidMovement('f', 2, 'f', 3);
		simpleValidMovement('h', 7, 'h', 6);
		simpleValidMovement('f', 3, 'g', 2);
		simpleValidMovement('h', 6, 'h', 7);
		simpleValidMovement('g', 2, 'f', 2);
		simpleValidMovement('h', 7, 'h', 6);
//39
		simpleValidMovement('f', 2, 'e', 1);
		simpleValidMovement('h', 6, 'h', 4);
		simpleValidMovement('e', 1, 'd', 1);
		simpleValidMovement('h', 4, 'h', 2);
		simpleValidMovement('b', 2, 'b', 4);
		simpleValidMovement('f', 7, 'f', 6);
//42
		simpleValidMovement('d', 1, 'e', 1);
		simpleValidMovement('h', 2, 'a', 2);
		simpleValidMovement('e', 1, 'd', 1);
		simpleValidMovement('a', 2, 'h', 2);
		simpleValidMovement('d', 1, 'e', 1);
		simpleValidMovement('h', 2, 'a', 2);
//45
		simpleValidMovement('e', 1, 'd', 1);
		simpleValidMovement('a', 2, 'f', 2);
		simpleValidMovement('d', 1, 'e', 1);
		simpleValidMovement('f', 2, 'f', 3);
		simpleValidMovement('e', 1, 'd', 1);
		simpleValidMovement('f', 3, 'f', 1);
//48
		simpleValidMovement('d', 1, 'c', 2);
		invalid('c', 4, 'c', 3);
		simpleValidMovement('c', 4, 'b', 3);
		simpleValidMovement('c', 2, 'b', 2);
		simpleValidMovement('f', 4, 'g', 5);
		invalidKingMoveDueToCheckByOpponent('b', 2, 'a', 2);
		invalidKingMoveDueToCheckByOpponent('b', 2, 'c', 2);
		invalidKingMoveDueToCheckByOpponent('b', 2, 'a', 1);
		invalidKingMoveDueToCheckByOpponent('b', 2, 'b', 1);
		invalidKingMoveDueToCheckByOpponent('b', 2, 'c', 1);
		simpleValidMovement('c', 3, 'c', 4);
		simpleValidMovement('f', 1, 'f', 2);
		invalidPieceMoveDueToCheckByOpponent('c', 4, 'c', 5);
		simpleValidMovement('b', 2, 'c', 3);
		simpleValidMovement('g', 5, 'c', 1);
		simpleValidMovement('c', 3, 'd', 3);
		simpleValidMovement('e', 5, 'e', 4);
		invalidPieceMoveDueToCheckByOpponent('c', 4, 'c', 5);
		simpleValidMovement('d', 3, 'd', 4);
		simpleValidMovement('f', 2, 'c', 2);
		killValidMovement('c', 4, 'b', 5);
		simpleValidMovement('b', 3, 'd', 5);
		simpleValidMovement('b', 5, 'b', 6);
		simpleValidMovement('f', 5, 'f', 4);
		simpleValidMovement('b', 6, 'b', 7);
		killValidMovement('d', 5, 'b', 7);
		simpleValidMovement('b', 4, 'b', 5);
		invalid('c', 1, 'b', 5);
		killValidMovement('c', 1, 'a', 3);
		simpleValidMovement('b', 5, 'b', 6);
		simpleValidMovement('f', 4, 'f', 3);
		simpleValidMovement('d', 4, 'e', 3);
		simpleValidMovement('f', 3, 'f', 2);
		invalidKingMoveDueToCheckByOpponent('e', 3, 'f', 2);
		movementNotPossibleDueToSourceNotPresent('f', 3, 'f', 4);
		simpleValidMovement('e', 3, 'f', 4);
		pawnPromotion('f', 2, 'f', 1);//Promoted to Rook
		simpleValidMovement('f', 4, 'g', 3);
		simpleValidMovement('f', 1, 'h', 1);
		simpleValidMovement('g', 3, 'f', 4);
		simpleValidMovement('h', 1, 'g', 1);
		simpleValidMovement('f', 4, 'f', 5);
		simpleValidMovement('a', 3, 'b', 2);
		simpleValidMovement('f', 5, 'f', 4);
		simpleValidMovement('b', 2, 'c', 1);
		simpleValidMovement('f', 4, 'f', 5);
		simpleValidMovement('c', 2, 'e', 2);
		killValidMovement('f', 5, 'f', 6);
		simpleValidMovement('c', 1, 'b', 2);
		simpleValidMovement('f', 6, 'f', 7);
		simpleValidMovement('b', 2, 'd', 4);
		simpleValidMovement('f', 7, 'f', 8);
		simpleValidMovement('e', 2, 'f', 2);
		simpleValidMovement('f', 8, 'e', 8);
		simpleValidMovement('b', 7, 'c', 6);
		simpleValidMovement('e', 8, 'd', 8);
		simpleValidMovement('e', 4, 'e', 3);
		simpleValidMovement('b', 6, 'b', 7);
		simpleValidMovement('g', 1, 'b', 1);
		simpleValidMovement('d', 8, 'c', 8);
		simpleValidMovement('c', 6, 'b', 7);
		simpleValidMovement('c', 8, 'd', 8);
		simpleValidMovement('b', 1, 'g', 1);
		simpleValidMovement('d', 8, 'e', 8);
		simpleValidMovement('e', 3, 'e', 2);
		simpleValidMovement('e', 8, 'd', 8);
		simpleValidMovement('a', 4, 'a', 3);
		simpleValidMovement('d', 8, 'e', 8);
		pawnPromotion('e', 2, 'e', 1);//Promoted to Queen
		simpleValidMovement('e', 8, 'd', 8);
		simpleValidMovement('g', 1, 'g', 8);
		game.render();
	}

	private void pawnPromotion(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		valid(sourceFile, sourceRank, targetFile, targetRank);
		assertTrue("Pawn not promoted", !board.getTileAt(targetRank, targetFile).getPiece().isPawn());
	}

	private void movementNotPossibleDueToSourceNotPresent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
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
	}

	private void invalidPieceMoveDueToCheckByOpponent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
	}

	private void invalidKingMoveDueToCheckByOpponent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
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
