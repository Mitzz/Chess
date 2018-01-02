package org.mitz.chess;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mitz.chess.model.Bishop;
import org.mitz.chess.model.King;
import org.mitz.chess.model.Knight;
import org.mitz.chess.model.Pawn;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Queen;
import org.mitz.chess.model.Rook;
import org.mitz.chess.model.Tile;

public class InitialPiecePlacement {

	@Test
	public void whiteRook() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(0, 0);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.WHITE && piece instanceof Rook);
		assertTrue("Must be Equal To White Rook", condition);
		
		tile = new Tile(0, 7);
		piece = tile.getPiece();
		assertTrue("Must be Equal To White Rook", condition);
	}
	
	@Test
	public void whiteKnight() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(0, 1);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.WHITE && piece instanceof Knight);
		assertTrue("Must be Equal To White Knight", condition);
		
		tile = new Tile(0, 6);
		piece = tile.getPiece();
		assertTrue("Must be Equal To White Knight", condition);
	}
	
	@Test
	public void whiteBishop() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(0, 2);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.WHITE && piece instanceof Bishop);
		assertTrue("Must be Equal To White Bishop", condition);
		
		tile = new Tile(0, 5);
		piece = tile.getPiece();
		assertTrue("Must be Equal To White Bishop", condition);
	}
	
	@Test
	public void whiteQueen() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(0, 3);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.WHITE && piece instanceof Queen);
		assertTrue("Must be Equal To White Queen", condition);
	}
	
	@Test
	public void whiteKing() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(0, 4);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.WHITE && piece instanceof King);
		assertTrue("Must be Equal To White King", condition);
	}
	
	@Test
	public void whitePawn() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		List<Integer> i = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
		for(int file : i) {
			tile = new Tile(1, file);
			piece = tile.getPiece();
			condition = (piece.getColor() == Color.WHITE && piece instanceof Pawn);
			assertTrue("Must be Equal To White Pawn", condition);
		}
	}
	
	@Test
	public void blackRook() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(7, 0);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.BLACK && piece instanceof Rook);
		assertTrue("Must be Equal To Black Rook", condition);
		
		tile = new Tile(7, 7);
		piece = tile.getPiece();
		assertTrue("Must be Equal To Black Rook", condition);
	}
	
	@Test
	public void blackKnight() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(7, 1);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.BLACK && piece instanceof Knight);
		assertTrue("Must be Equal To Black Knight", condition);
		
		tile = new Tile(7, 6);
		piece = tile.getPiece();
		assertTrue("Must be Equal To Black Knight", condition);
	}
	
	@Test
	public void blackBishop() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(7, 2);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.BLACK && piece instanceof Bishop);
		assertTrue("Must be Equal To Black Bishop", condition);
		
		tile = new Tile(7, 5);
		piece = tile.getPiece();
		assertTrue("Must be Equal To Black Bishop", condition);
	}
	
	@Test
	public void blackQueen() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(7, 3);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.BLACK && piece instanceof Queen);
		assertTrue("Must be Equal To Black Queen", condition);
	}
	
	@Test
	public void blackKing() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		tile = new Tile(7, 4);
		piece = tile.getPiece();
		condition = (piece.getColor() == Color.BLACK && piece instanceof King);
		assertTrue("Must be Equal To Black King", condition);
	}
	
	@Test
	public void blackPawn() {
		Tile tile = null;
		Piece piece = null;
		boolean condition = false;
		
		List<Integer> i = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
		for(int file : i) {
			tile = new Tile(6, file);
			piece = tile.getPiece();
			condition = (piece.getColor() == Color.BLACK && piece instanceof Pawn);
			assertTrue("Must be Equal To Black Pawn", condition);
		}
	}
}
