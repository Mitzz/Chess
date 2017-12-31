package org.mitz.chess.model;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
			System.err.println("Either Source and Target Tile not valid");
			return false;
		}
		if(to.equals(from)) {
			System.err.println("Both Source and Target Tile are same");
			return false;
		}
		if(!from.isEmpty() && !isMoveByTurn(isWhiteTurn, from)) {
			System.err.println("Move Invalid due wrong player played");
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
		Piece killedPiece = null;
		boolean beforeMoveAlreadyCheck = isCheck(!isWhiteTurn);
		boolean valid = from.validateMove(to);
		if(!valid) return false;
		if(valid) {
			killedPiece = from.moveTo(to);
			successfulStep++;
		}
		
		//Check of same color
//		System.out.println("isWhiteTurn: " + isWhiteTurn); 
		boolean afterMoveStillCheck = isCheck(!isWhiteTurn);
		System.out.println("isCheck: " + afterMoveStillCheck);
		if(afterMoveStillCheck) {
			to.moveTo(from, killedPiece);
			System.err.println("Piece Movement Blocked due to check");
			successfulStep--;
			return false;
		}
		
//		//Check of opponent
//		afterMoveStillCheck = isCheck(!isWhiteTurn);
//		if(afterMoveStillCheck) {
//			Tile kingTile = null;
//			if(isWhiteTurn) {
//				kingTile = getWhiteKingTile();
//			} else {
//				kingTile = getBlackKingTile();
//			}
//			if(!from.equals(kingTile)) {
//				System.err.println("King Movement necessary due to check");
//				successfulStep--;
//				return false;
//			}
//		}
		
		return valid;
	}
	
	private boolean isCheck(boolean isWhiteTurn) {
		Tile kingTile = null;
		boolean check = false;
		if(isWhiteTurn) {
			kingTile = getBlackKingTile();
		} else {
			kingTile = getWhiteKingTile();
		}
		System.out.println("King Tile Present at: " + kingTile.getPosition());
		Tile nextTile = null;
		nextTile = getNextNonEmptyTile(kingTile, 1, 0);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 1, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 0, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, 0);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 0, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 1, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		
		List<Point> p = new ArrayList<>();
		p.add(new Point(kingTile.getRankIndex() + 2, kingTile.getFileIndex() + 1));
		p.add(new Point(kingTile.getRankIndex() + 1, kingTile.getFileIndex() + 2));
		p.add(new Point(kingTile.getRankIndex() - 1, kingTile.getFileIndex() + 2));
		p.add(new Point(kingTile.getRankIndex() - 2, kingTile.getFileIndex() + 1));
		
		p.add(new Point(kingTile.getRankIndex() + 2, kingTile.getFileIndex() - 1));
		p.add(new Point(kingTile.getRankIndex() + 1, kingTile.getFileIndex() - 2));
		p.add(new Point(kingTile.getRankIndex() - 1, kingTile.getFileIndex() - 2));
		p.add(new Point(kingTile.getRankIndex() - 2, kingTile.getFileIndex() - 1));
		
		for(Point pt: p) {
			
			if(isValidTileAt(pt.x, pt.y)) {
				nextTile = tiles[pt.x][pt.y];
				if(!isSameOpponentPiece(kingTile, nextTile) && !nextTile.isEmpty() && nextTile.getPiece().validateMove(kingTile)) {
					System.out.println("Check Yes due to tile at " + nextTile.getPosition());
					return true;
				}
			}
		}
		
		System.out.println("No Check");
		return false;
	}

	private boolean isSameOpponentPiece(Tile v, Tile w) {
		return (!v.isEmpty() && !w.isEmpty() && v.getPiece().getColor() == w.getPiece().getColor());
	}

	private Tile getNextNonEmptyTile(Tile from, int rankOffset, int fileOffset) {

		int fromRank = from.getRankIndex();
		int fromFile = from.getFileIndex();
		fromRank += rankOffset;
		fromFile += fileOffset;
		while(isValidTileAt(fromRank, fromFile)) {
			if(!tiles[fromRank][fromFile].isEmpty()) 
				return tiles[fromRank][fromFile];
			fromRank += rankOffset;
			fromFile += fileOffset;
		}
		return null;
	}
	
	private boolean isValidTileAt(int fromRank, int fromFile) {
		return (fromRank <= 7 && fromRank >= 0) && (fromFile <= 7 && fromFile >= 0);
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
