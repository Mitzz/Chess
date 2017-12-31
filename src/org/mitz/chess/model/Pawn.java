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
		String message = "Piece '" + PIECE_DESC + "' movement invalid from " + this.getTile().getPosition() + " to " + to.getPosition();
		boolean valid = false;
		Tile selfTile = this.getTile();
		if(this.getColor() == Color.WHITE) {
			if((to.getRank() == selfTile.getRank() + 1) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
				message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(selfTile.getRank() == 2 && (to.getRank() == selfTile.getRank() + 2) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
				message = "2 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() + 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
				message = "3 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() + 1 && to.getFile() == selfTile.getFile() - 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
				message = "4 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
		}
		if(this.getColor() == Color.BLACK) {
			if((to.getRank() == selfTile.getRank() - 1) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
				message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(selfTile.getRank() == 7 && (to.getRank() == selfTile.getRank() - 2) && to.getFile() == selfTile.getFile() && to.isEmpty()) {
				message = "2 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() - 1 && to.getFile() == selfTile.getFile() + 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
				message = "3 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
			if(to.getRank() == selfTile.getRank() - 1 && to.getFile() == selfTile.getFile() - 1 && !to.isEmpty() && to.getPiece().getColor() != this.getColor()) {
				message = "4 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
				valid = true;
			}
		}
		if(valid)
			System.out.println("Pawn.validateMove(message): " + message);
		else
			System.err.println("Pawn.validateMove(message): " + message);
		return valid;
	}
	
	@Override
	public String getUnicodeCharacter() {
		
		return (Color.white == this.getColor() ? "\u2659" : "\u265F");
	}
	
	public boolean isPawn() {
		return true;
	}

}
