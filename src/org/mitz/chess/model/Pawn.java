package org.mitz.chess.model;

import java.awt.Color;

public class Pawn extends Piece {
	
	private final String PIECE_NAME = "PAWN";
	private final String PIECE_DESC;

	public Pawn(Color color, Tile tile) {
		super(color, tile);
		PIECE_DESC = getPieceDescription(PIECE_NAME);  
	}

	@Override
	public boolean validateMove(Tile to) {
		super.validateMove(to);
		String message = "Piece '" + PIECE_DESC + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		if(this.getColor() == Color.WHITE) {
			if((to.getRank() == selfTile.getRank() + 1) && to.getFile() == selfTile.getFile()) {
				message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(selfTile.getRank() == 2 && (to.getRank() == selfTile.getRank() + 2) && to.getFile() == selfTile.getFile()) {
				message = "2 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() + 1 && to.getPiece().getColor() != this.getColor()) {
				message = "3 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() - 1 && to.getPiece().getColor() != this.getColor()) {
				message = "4 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
		}
		System.out.println("Pawn.validateMove(message): " + message);
		return valid;
	}
}
