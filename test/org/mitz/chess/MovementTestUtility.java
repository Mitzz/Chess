package org.mitz.chess;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Tile;

public class MovementTestUtility {
	
	private Game game;
	private Board board;
	
	public MovementTestUtility(Game game, Board board) {
		super();
		this.game = game;
		this.board = board;
	}

	public void pawnPromotion(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		valid(sourceFile, sourceRank, targetFile, targetRank);
		assertTrue("Pawn not promoted", !board.getTileAt(targetRank, targetFile).getPiece().isPawn());
	}

	public void movementNotPossibleDueToSourceNotPresent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
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

	void invalidPieceMoveDueToCheckByOpponent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
	}

	public void invalidKingMoveDueToCheckByOpponent(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
	}

	public void invalidPieceMovementDueToUserPiece(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		invalid(sourceFile, sourceRank, targetFile, targetRank);
		assertTrue("Source and Target Piece are not same", board.getTileAt(sourceRank, sourceFile).getPiece().getColor() == board.getTileAt(targetRank, targetFile).getPiece().getColor());
	}

	public void killValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		Tile targetTile = board.getTileAt(targetRank, targetFile);
		assertTrue("Target Tile must not be Empty", !targetTile.isEmpty());
		simpleValidMovement(sourceFile, sourceRank, targetFile, targetRank);
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

	public void invalid(char sourceFile, int sourceRank, char targetFile, int targetRank) {
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

	public void castlingValidMovement(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		valid(sourceFile, sourceRank, targetFile, targetRank);
		Tile targetTile = board.getTileAt(targetRank, targetFile);
		Piece targetPiece = targetTile.getPiece();
		assertTrue("Target Piece must be King", targetPiece.isKingPiece());
		if(isKingSideCastling(sourceFile, sourceRank, targetFile, targetRank)) {
			assertTrue("Rook Piece must be moved during castling", !board.getTileAt(targetRank, (char)(targetFile - 1)).isEmpty());
		} else {
			assertTrue("Rook Piece must be moved during castling", !board.getTileAt(targetRank, (char)(targetFile + 1)).isEmpty());
		}
	}
	
	private boolean isKingSideCastling(char sourceFile, int sourceRank, char targetFile, int targetRank) {
		return targetFile - sourceFile > 0;
	}
}
