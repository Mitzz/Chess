package org.mitz.chess.model;

import java.awt.Color;

import org.apache.log4j.Logger;

public class Bishop extends Piece{

	private final static Logger logger = Logger.getLogger(Bishop.class);
	private final String PIECE_NAME = "BISHOP";
	
	public Bishop(Color color, Tile tile) {
		super(color, tile);
		description = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2657" : "\u265d");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + description + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		if((Math.abs(selfTile.getRank() - to.getRank()) == Math.abs(selfTile.getFile() - to.getFile())) && 
				(to.isEmpty() || (!to.isEmpty() && getColor() != to.getPiece().getColor()))) {
			message = "1 - Piece '" + description + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			logger.debug("Pawn.validateMove(message): " + message);
		else
			logger.debug("Pawn.validateMove(message): " + message);
		return valid;
	}
	
	

}
