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
}

