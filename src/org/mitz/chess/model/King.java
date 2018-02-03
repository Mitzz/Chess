package org.mitz.chess.model;

import java.awt.Color;

import org.apache.log4j.Logger;

public class King extends Piece{

	private final static Logger logger = Logger.getLogger(King.class);
	private final String PIECE_NAME = "KING";
	private final String PIECE_DESC;

	public King(Color color, Tile tile) {
		super(color, tile);
		PIECE_DESC = getPieceDescription(PIECE_NAME);  
	}
	
	@Override
	public String getUnicodeCharacter() {
		return (Color.white == this.getColor() ? "\u2654" : "\u265a");
	}
	
	@Override
	public boolean validateMove(Tile to) {
		String message = "Piece '" + PIECE_DESC + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		int rankDiff = Math.abs(selfTile.getRank() - to.getRank());
		int fileIndexDiff = Math.abs(selfTile.getFile() - to.getFile());
		if((rankDiff == 0 || rankDiff == 1) && (fileIndexDiff == 0 || fileIndexDiff == 1) && (to.isEmpty() || (to.getPiece().getColor() != getColor()))) {
			message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
			valid = true;
		}
		if(valid)
			logger.debug("King.validateMove(message): " + message);
		else
			logger.debug("King.validateMove(message): " + message);
		return valid;
	}

	@Override
	public boolean isKingPiece() {
		return true;
	}
}
