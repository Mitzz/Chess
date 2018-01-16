package org.mitz.chess.model;

import java.awt.Color;

public class Piece {
	
	private Tile tile;
	private Color color;

	public Piece(Color color, Tile tile) {
		this.color = color;
		this.tile = tile;
	}

	public void move(Tile from, Tile to) {

	}

	public boolean validateMove(Tile to) {

		return true;
	}

	public void remove() {
		this.tile = null;
	}
	
	public Tile getTile() {
		return tile;
	}

	public Color getColor() {
		return this.color;
	}

	public String getPieceDescription(String pieceName) {
		return ((color == Color.WHITE) ? "White" : "Black") + " " + pieceName;
	}

	public String getUnicodeCharacter() {
		return "";
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	public boolean isKingPiece() {
		return false;
	}

	public boolean isPawn() {
		return false;
	}

	public static Piece getInstance(int i, Color color, Tile tile) {
		//1 -> Queen, 2 -> Rook, 3 -> Knight, 4 -> Bishop
		Piece piece = null;
		switch (i) {
		case 1:
			piece = new Queen(color, tile);
			break;
		case 2:
			piece = new Rook(color, tile);
			break;
		case 3:
			piece = new Knight(color, tile);
			break;
		case 4:
			piece = new Bishop(color, tile);
			break;
		default:
			break;
		}
		return piece;
	}
}

