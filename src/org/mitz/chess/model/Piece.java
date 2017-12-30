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

		return false;
	}

	public void remove() {
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
		return "  ";
	}
}

