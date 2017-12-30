package org.mitz.chess.model;

import java.awt.Color;

public class King extends Piece{

	public King(Color color, Tile tile) {
		super(color, tile);
	}
	
	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2654" : "\u265a");
	}
}
