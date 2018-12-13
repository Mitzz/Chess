package org.mitz.chess.model;

import java.awt.Color;

import org.apache.log4j.Logger;

public class Queen extends Piece {

	private final static Logger logger = Logger.getLogger(Queen.class);
	private final String PIECE_NAME = "QUEEN";

	public Queen(Color color, Tile tile) {
		super(color, tile);
		description = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2655" : "\u265b");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + description + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		int rankDiff = Math.abs(selfTile.getRank() - to.getRank());
		int fileIndexDiff = Math.abs(selfTile.getFile() - to.getFile());
		if(((rankDiff == fileIndexDiff) || 
				((rankDiff > 0 && selfTile.getFile() == to.getFile()) ||
						(0 < fileIndexDiff && selfTile.getRank() == to.getRank()))) && 
				(to.isEmpty() || (!to.isEmpty() && getColor() != to.getPiece().getColor()))) {
			message = "1 - Piece '" + description + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			logger.debug("Queen.validateMove(message): " + message);
		else
			logger.debug("Queen.validateMove(message): " + message);
		return valid;
	}

}
