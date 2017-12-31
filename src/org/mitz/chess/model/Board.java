package org.mitz.chess.model;

import java.awt.Color;

public class Board {

	private Tile[][] tiles;
	private int successfulStep = 1;
	
	public Board() {
		tiles = new Tile[8][8];
		for(int rank = 0; rank < 8; rank++)
			for(int file = 0; file < 8; file++)
				tiles[rank][file] = new Tile(rank, file);
	}

	public boolean move(boolean isWhiteTurn, int fromRank, int fromFile, int toRank, int toFile) {
		Tile from = tiles[fromRank][fromFile];
		Tile to = tiles[toRank][toFile];
		System.out.println("------------Movement Step " + successfulStep + " Initiated from " + from.getPosition() + " to " + to.getPosition() + "for " + (isWhiteTurn ? "white" : "black") + "----------");
		if(!validateSourceTargetTile(to, from)) {
			return false;
		}
		if(to.equals(from)) {
			System.err.println("Both Source and Target Tile are same");
			return false;
		}
		if(isMovementDiagonal(from, to)) {
			System.out.println("Movement Diagonal");
			if(!isPathDiagonallyEmpty(from, to)) {
				System.err.println("Diagonal Path not empty");
				return false;
			}
		}
		if(isMovementSideways(from, to)) {
			System.out.println("Movement Sideways");
			if(!isPathSidewayEmpty(from, to)) {
				System.err.println("Sideway Path not empty");
				return false;
			}
		}
		if(!from.isEmpty() && !isMoveByTurn(isWhiteTurn, from)) {
			System.err.println("Move Invalid due wrong player played");
			return false;
		}
		boolean valid = from.moveTo(to);
		if(valid) successfulStep++;
		Tile kingTile = null;
		if(isWhiteTurn) {
			kingTile = getBlackKingTile();
		} else {
			kingTile = getWhiteKingTile();
		}
		System.out.println("King Tile Present at: " + kingTile.getPosition());
		return valid;
	}
	
	private Tile getBlackKingTile() {
		for(int rank = 0; rank < 8; rank++) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				if(tile.isEmpty()) continue;
				if(tile.getPiece().getColor() == Color.WHITE) continue;
				if(tile.isKingPiece()) return tile;
			}
		}
		return null;
	}
	
	private Tile getWhiteKingTile() {
		for(int rank = 0; rank < 8; rank++) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				if(tile.isEmpty()) continue;
				if(tile.getPiece().getColor() == Color.BLACK) continue;
				if(tile.isKingPiece()) return tile;
			}
		}
		return null;
	}

	private boolean isPathSidewayEmpty(Tile from, Tile to) {
		int xOffset = new Integer(to.getRank()).compareTo(new Integer(from.getRank()));
		int yOffset = new Integer(to.getFileIndex()).compareTo(new Integer(from.getFileIndex()));
		return isPathEmpty(to, from, xOffset, yOffset);
	}

	private boolean isMovementSideways(Tile from, Tile to) {
		return (from.getRank() - to.getRank() == 0 || from.getFileIndex() - to.getFileIndex() == 0);
	}

	private boolean isPathDiagonallyEmpty(Tile from, Tile to) {
		int xOffset = new Integer(to.getRank()).compareTo(new Integer(from.getRank()));
		int yOffset = new Integer(to.getFileIndex()).compareTo(new Integer(from.getFileIndex()));
		return isPathEmpty(to, from, xOffset, yOffset);
	}

	private boolean isMovementDiagonal(Tile from, Tile to) {
		
		return (Math.abs(from.getRank() - to.getRank()) == Math.abs(from.getFileIndex() - to.getFileIndex()));
	}

	private boolean isValidTile(Tile to) {
		return (to.getRank() <= 8 && to.getRank() >= 1) && 
				(to.getFile() == 'a' || to.getFile() == 'b' || to.getFile() == 'c' || to.getFile() == 'd' || to.getFile() == 'e' || to.getFile() == 'f' || to.getFile() == 'g' || to.getFile() == 'h' );
	}

	private boolean isMoveByTurn(boolean isWhiteTurn, Tile tile) {
		return (isWhiteTurn && tile.getPiece().getColor() == Color.WHITE) || (!isWhiteTurn && tile.getPiece().getColor() == Color.BLACK);
	}
	
	public boolean isPathEmpty(Tile to, Tile from, int rankOffset, int fileOffset) {
//		System.out.println("Rank Offset: " + rankOffset + ", File Offset: " + fileOffset);
//		int toRank = to.getRank() - 1;
//		int toFile = to.getFileIndex();
		int fromRank = from.getRankIndex();
		int fromFile = from.getFileIndex();
		if(!validateSourceTargetTile(to, from)) {
			return false;
		}
		
		fromRank += rankOffset;
		fromFile += fileOffset;
		while(!(to.getRankIndex() == fromRank && to.getFileIndex() == fromFile)) {
			System.out.println("Rank: " + fromRank + ", File: " + fromFile);
			if(!tiles[fromRank][fromFile].isEmpty()) {
				System.err.println(tiles[fromRank][fromFile].getPosition() + " is not empty");
				return false;
			}
			fromRank += rankOffset;
			fromFile += fileOffset;
			
		}
		
		return true;
	}

	private boolean validateSourceTargetTile(Tile to, Tile from) {
		if(!isValidTile(from)) {
			System.err.println("Move Invalid due to invalid source tile");
			return false;
		}
		if(!isValidTile(to)) {
			System.err.println("Move Invalid due to invalid destination tile");
			return false;
		}
		
		return true;
	}

	public void render() {
		
		for(int rank = 7; rank >= 0; rank--) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				System.out.print(tile.getPosition() + tile.getContent() + " ");
//				System.out.print(tile.getContent());
			}
			System.out.println();
		}
	}
}
