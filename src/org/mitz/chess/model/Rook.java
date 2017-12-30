package org.mitz.chess.model;

import java.awt.Color;

public class Rook extends Piece{

	public Rook(Color white, Tile tile) {
		super(white, tile);
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2656" : "\u265c");
	}
}
