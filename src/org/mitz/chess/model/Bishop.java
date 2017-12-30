package org.mitz.chess.model;

import java.awt.Color;

public class Bishop extends Piece{

	private final String PIECE_NAME = "BISHOP";
	private final String PIECE_DESC;

	public Bishop(Color color, Tile tile) {
		super(color, tile);
		PIECE_DESC = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2657" : "\u265d");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + PIECE_DESC + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		if((Math.abs(selfTile.getRank() - to.getRank()) == Math.abs(selfTile.getFileIndex() - to.getFileIndex())) && 
				(to.isEmpty() || (!to.isEmpty() && getColor() != to.getPiece().getColor()))) {
			message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			System.out.println("Pawn.validateMove(message): " + message);
		else
			System.err.println("Pawn.validateMove(message): " + message);
		return valid;
	}
	
	

}
