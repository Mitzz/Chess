package org.mitz.chess.model;

import java.awt.Color;

public class Queen extends Piece {

	public Queen(Color color, Tile tile) {
		super(color, tile);
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2655" : "\u265b");
	}
	
	
}
