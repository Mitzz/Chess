package org.mitz.chess.model;

import java.awt.Color;

public class Bishop extends Piece{

	public Bishop(Color color, Tile tile) {
		super(color, tile);
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2657" : "\u265d");
	}
}
