package org.mitz.chess.model;

import java.awt.Color;

public class Knight extends Piece {

	public Knight(Color color, Tile tile) {
		super(color, tile);
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2658" : "\u265e");
	}
}
