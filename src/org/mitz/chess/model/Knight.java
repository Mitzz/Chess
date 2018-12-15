package org.mitz.chess.model;

import java.awt.Color;

import org.apache.log4j.Logger;

public class Knight extends Piece {
	
	private final static Logger logger = Logger.getLogger(Knight.class);
	private final String PIECE_NAME = "KNIGHT";
	
	public Knight(Color color, Tile tile) {
		super(color, tile);
		description = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2658" : "\u265e");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + description + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		if((to.getRank() == selfTile.getRank() + 2 || to.getRank() == selfTile.getRank() - 2) && 
				(to.getFile() == selfTile.getFile() - 1 || to.getFile() == selfTile.getFile() + 1) && 
				(to.isEmpty() || (!to.isEmpty() && selfTile.getPiece().getColor() != to.getPiece().getColor()))) {
			message = "1 - Piece '" + description + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if((to.getRank() == selfTile.getRank() + 1 || to.getRank() == selfTile.getRank() - 1) && 
				(to.getFile() == selfTile.getFile() - 2 || to.getFile() == selfTile.getFile() + 2) && 
				(to.isEmpty() || (!to.isEmpty() && selfTile.getPiece().getColor() != to.getPiece().getColor()))) {
			message = "2 - Piece '" + description + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			logger.debug("Knight.validateMove(message): " + message);
		else
			logger.debug("Knight.validateMove(message): " + message);
		return valid;
	}
}
