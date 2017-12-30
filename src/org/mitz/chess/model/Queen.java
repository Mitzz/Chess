package org.mitz.chess.model;

import java.awt.Color;

public class Queen extends Piece {

	private final String PIECE_NAME = "QUEEN";
	private final String PIECE_DESC;

	public Queen(Color color, Tile tile) {
		super(color, tile);
		PIECE_DESC = getPieceDescription(PIECE_NAME);  
	}


	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2655" : "\u265b");
	}
	

}
