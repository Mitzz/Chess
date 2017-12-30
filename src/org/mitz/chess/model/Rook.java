package org.mitz.chess.model;

import java.awt.Color;

public class Rook extends Piece{

	private final String PIECE_NAME = "ROOK";
	private final String PIECE_DESC;

	public Rook(Color color, Tile tile) {
		super(color, tile);
		PIECE_DESC = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2656" : "\u265c");
	}

}
