package org.mitz.chess.model;

import java.awt.Color;

import org.apache.log4j.Logger;

public class Rook extends Piece{

	private final static Logger logger = Logger.getLogger(Rook.class);
	private final String PIECE_NAME = "ROOK";
	
	public Rook(Color color, Tile tile) {
		super(color, tile);
		description = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2656" : "\u265c");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + description + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		
		if(getTile().isMovementDiagonal(to)) {
			
		} else {
			
		}
		
		if(((Math.abs(selfTile.getRank() - to.getRank()) > 0 && selfTile.getFile() == to.getFile()) ||
				(0 < Math.abs(selfTile.getFile() - to.getFile()) && selfTile.getRank() == to.getRank())) && 
				(to.isEmpty() || (!to.isEmpty() && getColor() != to.getPiece().getColor()))) {
			message = "1 - Piece '" + description + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			logger.debug("Rook.validateMove(message): " + message);
		else
			logger.debug("Rook.validateMove(message): " + message);
		return valid;
	}

}
