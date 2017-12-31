package org.mitz.chess.model;

import java.awt.Color;

public class King extends Piece{

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
//		Tile selfTile = this.getTile();
//		int rankDiff = Math.abs(selfTile.getRank() - to.getRank());
//		int fileIndexDiff = Math.abs(selfTile.getFileIndex() - to.getFileIndex());
//		if((rankDiff == fileIndexDiff) || 
//				((rankDiff > 0 && selfTile.getFileIndex() == to.getFileIndex()) ||
//						(0 < fileIndexDiff && selfTile.getRank() == to.getRank())) && 
//				(to.isEmpty() || (!to.isEmpty() && getColor() != to.getPiece().getColor()))) {
//			message = "1 - Piece '" + PIECE_DESC + "' movement valid from " + this.getTile().getPosition() + " to " + to.getPosition();
//			valid = true;
//		}
//		if(valid)
//			System.out.println("King.validateMove(message): " + message);
//		else
//			System.err.println("King.validateMove(message): " + message);
		return true;
	}

}
