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
//		if(this.getColor() == Color.WHITE) {
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
//			if(selfTile.getRank() == 2 && (to.getRank() == selfTile.getRank() + 2) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
//				message = "2 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() + 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
//				message = "3 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() - 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
//				message = "4 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//		}
//		if(this.getColor() == Color.BLACK) {
//			if((to.getRank() == selfTile.getRank() - 1) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
//				message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//			if(selfTile.getRank() == 7 && (to.getRank() == selfTile.getRank() - 2) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
//				message = "2 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//			if(to.getRank() == selfTile.getRank() - 1 && to.getFile() == selfTile.getFile() + 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
//				message = "3 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//			if(to.getRank() == selfTile.getRank() - 1 && to.getFile() == selfTile.getFile() - 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
//				message = "4 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//				valid = true;
//			}
//		}
		if(valid)
			logger.info("Knight.validateMove(message): " + message);
		else
			logger.info("Knight.validateMove(message): " + message);
		return valid;
	}
}
