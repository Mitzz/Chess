package org.mitz.chess.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	private Tile[][] tiles;
	private int successfulStep = 1;
	private boolean isWhiteCastlingPossibleOnKingSide = true;
	private boolean isWhiteCastlingPossibleOnQueenSide = true;
	private boolean isBlackCastlingPossibleOnKingSide = true;
	private boolean isBlackCastlingPossibleOnQueenSide = true;
	
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
			System.out.println("Either Source and Target Tile not valid");
			return false;
		}
		if(!from.isEmpty() && !isMoveByTurn(isWhiteTurn, from)) {
			System.out.println("Move Invalid due wrong player played");
			return false;
		}
		Piece killedPiece = null;
		boolean valid = isPieceMoveValid(from, to);
		boolean isKingCheckBeforeMove = false;
		if(!valid) return false;
		if(valid) {
			isKingCheckBeforeMove = from.hasKingPiece() && isKingCheckAt(from);
			killedPiece = from.movePieceTo(to);
			successfulStep++;
		}
		boolean isKingCheckAfterMove = false;
		
		if(isWhiteTurn) isKingCheckAfterMove = isWhiteKingCheck();
		else 			isKingCheckAfterMove = isBlackKingCheck();
		
		if(isKingCheckAfterMove) {
			to.movePieceTo(from, killedPiece);
			System.out.println("Piece Movement Blocked due to check");
			successfulStep--;
			return false;
		}
		
		if(isCastlingMove(from, to, isWhiteTurn) && (!isCastlingMovePossible(from, to, isWhiteTurn) || isKingCheckBeforeMove)) {//Confused
			to.movePieceTo(from, killedPiece);
			System.out.println("Piece Movement Blocked due to no castling possible");
			successfulStep--;
			return false;
		}
		
		determineCastlingPossibility(from, to, isWhiteTurn);
		
		if(isPawnPromotion(isWhiteTurn, to)) {
			System.out.println("Finally a promotion");
			setPawnForPromotion(to);
		}
		System.out.println("Checking for GAME OVER!!!!");
		if(isGameOver(isWhiteTurn)) {
			System.out.println("GAME OVER!!!!");
		}
		
		return valid;
	}
	
	private void determineCastlingPossibility(Tile from, Tile to, boolean isWhiteTurn) {
		if(isWhiteTurn) {
			if(to.hasKingPiece()) {
				isWhiteCastlingPossibleOnQueenSide = false;
				isWhiteCastlingPossibleOnKingSide = false;
			}
			else if(from.isFirstRank() && from.getFile() == 'a') {
				isWhiteCastlingPossibleOnQueenSide = false;
			} else {
				isWhiteCastlingPossibleOnQueenSide = false;
			}
		} else {
			if(to.hasKingPiece()) {
				isBlackCastlingPossibleOnQueenSide = false;
				isBlackCastlingPossibleOnKingSide = false;
			}
			else if(from.isLastRank() && from.getFile() == 'a') {
				isBlackCastlingPossibleOnQueenSide = false;
			} else {
				isBlackCastlingPossibleOnQueenSide = false;
			}
		}
		
		System.out.println(String.format("Castling: WK - %s, WQ - %s, BK - %s, BQ - %s", isWhiteCastlingPossibleOnKingSide, isWhiteCastlingPossibleOnQueenSide, isBlackCastlingPossibleOnKingSide, isBlackCastlingPossibleOnQueenSide));
	}

	private boolean isCastlingMovePossible(Tile from, Tile to, boolean isWhiteTurn) {
		boolean isCastlingMovePossible = isWhiteTurn ? (isWhiteCastlingPossibleOnKingSide || isWhiteCastlingPossibleOnQueenSide) : (isBlackCastlingPossibleOnKingSide || isBlackCastlingPossibleOnQueenSide);
		if(!isCastlingMovePossible) return isCastlingMovePossible;
		
		boolean isCastlingKingSide = (to.getFile() - from.getFile()) == 2;
		Tile rook = null;
		if(isCastlingKingSide) {
			if(isWhiteTurn) rook = getTileAt(1, 'h');
			else 			rook = getTileAt(8, 'h');
			
			if(!isPathSidewayEmpty(rook, to)) return false;
			
			
		} else {
			if(isWhiteTurn) rook = getTileAt(1, 'a');
			else 			rook = getTileAt(8, 'a');
			
			if(!isPathSidewayEmpty(rook, to)) return false;
			
		}
		return isCastlingMovePossible;
	}

	private boolean isCastlingMove(Tile from, Tile to, boolean isWhiteTurn) {
		boolean condition = to.hasKingPiece() && (isWhiteTurn ? from.isFirstRank() : from.isLastRank()) && (Math.abs(from.getFile() - to.getFile()) == 2); 
		return condition;
	}

	private boolean isGameOver(boolean isWhiteTurn) {
		Tile kingTile = null;
		if(isWhiteTurn)
			kingTile = getBlackKingTile();
		else 
			kingTile = getWhiteKingTile();
		
		return canKingMove(kingTile);
	}

	private boolean canKingMove(Tile kingTile) {
		List<Tile> opponentTilesForKingCheckAt = null;
		//King move mandatory or not
		opponentTilesForKingCheckAt = getTilesForKingCheckAt(kingTile);
		if(opponentTilesForKingCheckAt.size() == 0) {
			System.out.println("Board.isGameOver : No Possibility since king movement is not mandatory");
			return false; 
		}
		
		System.out.println("Board.isGameOver : Possibility since king movement is mandatory");
		System.out.println("Board.isGameOver(opponentTiles responsible for king movement)");
		opponentTilesForKingCheckAt.stream().forEach(e -> System.out.println(e.getPosition()));
		
		//Can't kill two pieces in one move
		if(opponentTilesForKingCheckAt.size() == 1) {
			List<Tile> userTiles =  getValidMovementTilesAt(opponentTilesForKingCheckAt.get(0));
			System.out.println("Board.isGameOver(userTiles)");
			userTiles.stream().forEach(e -> System.out.println(e.getPosition()));
			if(userTiles.size() >= opponentTilesForKingCheckAt.size()) {
				System.out.println("King Saved by Piece");
				return false;
			}
		}
		
//		System.out.println("Game over testing - Black King");
		if(isKingMovePossible(kingTile.getRankIndex() + 1, kingTile.getFileIndex() + 0, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() + 1, kingTile.getFileIndex() + 1, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() + 0, kingTile.getFileIndex() + 1, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() - 1, kingTile.getFileIndex() + 1, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() - 1, kingTile.getFileIndex() + 0, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() - 1, kingTile.getFileIndex() - 1, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() + 0, kingTile.getFileIndex() - 1, kingTile)) return false;
		if(isKingMovePossible(kingTile.getRankIndex() + 1, kingTile.getFileIndex() - 1, kingTile)) return false;
		return true;
	}
	
	private List<Tile> getValidMovementTilesAt(Tile targetTile){
		if(targetTile.isEmpty()) return null;
		List<Piece> opponentPieces = (targetTile.getPiece().getColor() == Color.BLACK ? getAllWhitePieces() : getAllBlackPieces());
		List<Tile> tiles = new ArrayList<>();
		for(Piece p: opponentPieces) {
			Tile tile = p.getTile();
			if(!p.isKingPiece() && isPieceMoveValid(tile, targetTile)) tiles.add(tile); 
		}
		
		return tiles;
	}
	
	private boolean isKingMovePossible(int r, int f, Tile sourceTile) {
		
		if(!isValidTileAt(r, f)) 
			return false;
		
//		Color color = sourceTile.getPiece().getColor();
		Tile targetTile = getTileAt(r, f);
		
		System.out.println("Board.isMovePossible(targetTile): " + targetTile.getPosition());
		boolean isMovePossible = true;
		if(targetTile.isEmpty() || !isSameOpponentPiece(sourceTile, targetTile)) {
			System.out.println("Inside");
			Piece removedPiece = sourceTile.movePieceTo(targetTile);
			
			isMovePossible = !isKingCheckAt(targetTile);
			//Valid Move
//			List<Piece> opponentPieces = (color == Color.BLACK ? getAllWhitePieces() : getAllBlackPieces());
//			for(Piece opponentPiece: opponentPieces) {
//				System.out.println("Board.isKingMovePossible(opponentPiece Tile): " + opponentPiece.getTile());
//				if(isPieceMoveValid(opponentPiece.getTile(), targetTile)) {
//					System.out.println("Board.isMovePossible(isOpponentMoveValid): " + true);
//					targetTile.movePieceTo(sourceTile, removedPiece);
//					return false;
//				} else {
//					System.out.println("Board.isMovePossible(isOpponentMoveValid): " + false);
//				}
//			}
			targetTile.movePieceTo(sourceTile, removedPiece);
		} else {
			System.out.println("Board.isMovePossible(isMoveValid): " + true);
			return false;
		}
		 
		return isMovePossible;
	}

	private boolean isPieceMoveValid(Tile from, Tile to) {
		if(from.isEmpty()) 					return false;
		if(from.equals(to)) 				return false;
		if(isSameOpponentPiece(from, to)) 	return false;
		
		boolean isPathEmpty = true;
		boolean isMoveValid = from.getPiece().validateMove(to);
		
		if(!isMoveValid) return false;
		
		if(from.isMovementDiagonal(to) && !isPathDiagonallyEmpty(from, to)) 
			isPathEmpty = false;
		if(from.isMovementSideways(to) && !isPathSidewayEmpty(from, to)) 
			isPathEmpty = false;
		
		return isPathEmpty;
	}

	private List<Piece> getAllBlackPieces() {
		List<Piece> pieces = new ArrayList<>();
		for(int rank = 0; rank < 8; rank++) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file]; 
				if(!tile.isEmpty() && tile.getPiece().getColor() == Color.BLACK) {
					pieces.add(tile.getPiece());
				}
			}
		}
		return pieces;
	}

	private Tile getTileAt(int r, int f) {
		return tiles[r][f];
	}
	
	public Tile getTileAt(int rank, char file) {
		return tiles[rank - 1][file - 97];
	}
	
	private void setPawnForPromotion(Tile tile) {
		
		BufferedReader kin = new BufferedReader(new InputStreamReader(System.in));
		try {
			String menu = "1 -> Queen, 2 -> Rook, 3 -> Knight, 4 -> Bishop";
			System.out.println(menu);
			String userInput = kin.readLine();
			int userSelection = Integer.parseInt(userInput);
			Piece promotedPiece = null;
			if(tile.isFirstRank())
				promotedPiece = Piece.getInstance(userSelection, Color.BLACK, tile);
			else
				promotedPiece = Piece.getInstance(userSelection, Color.WHITE, tile);
			tile.setPiece(promotedPiece);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isPawnPromotion(boolean isWhiteTurn, Tile tile) {
		if(isWhiteTurn) 
			return (tile.isLastRank() && tile.hasPawnPiece());
		else 
			return (tile.isFirstRank() && tile.hasPawnPiece());
	}
	
	private boolean isBlackKingCheck() {
		return isKingCheckAt(getBlackKingTile());
	}
	
	private boolean isKingCheckAt(Tile kingTile) {
		System.out.println("King Tile Present at: " + kingTile.getPosition());
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, 1, 0))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, 1, 1))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, 0, 1))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, -1, 1))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, -1, 0))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, -1, -1))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, 0, -1))) return true;
		if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, 1, -1))) return true;
		return isCheckByKnight(kingTile);
	}
	
	private List<Tile> getTilesForKingCheckAt(Tile kingTile) {
		System.out.println("King Tile Present at: " + kingTile.getPosition());
		List<Tile> tiles = new ArrayList<>();
		Tile tile = getNextNonEmptyTile(kingTile, 1, 0);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, 1, 1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, -1, 1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, -1, 0);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, -1, -1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, 0, -1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, 1, -1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tile = getNextNonEmptyTile(kingTile, 0, 1);
		if(isCheckBy(kingTile, tile)) tiles.add(tile);
		
		tiles.addAll(getTilesForKingCheckByKnightAt(kingTile));
		return tiles;
	}
	
	private List<Tile> getTilesForKingCheckByKnightAt(Tile kingTile) {
		List<Integer> i = Arrays.asList(1, -1, 2, -2);
		List<Tile> tilesc = new ArrayList<>();
		int file = kingTile.getFileIndex();
		int rank = kingTile.getRankIndex();
		for(Integer v: i) {
			for(Integer w: i) {
				if(Math.abs(v) != Math.abs(w) && isValidTileAt(v + rank, w + file) && isCheckBy(kingTile, tiles[v + rank][w + file])) {
					tilesc.add(tiles[v + rank][w + file]);
				}
			}
		}
			
		return tilesc;
	}

	private boolean isCheckByKnight(Tile kingTile) {
		List<Integer> i = Arrays.asList(1, -1, 2, -2);
		
		int file = kingTile.getFileIndex();
		int rank = kingTile.getRankIndex();
		for(Integer v: i) {
			for(Integer w: i) {
				if(Math.abs(v) != Math.abs(w) && isValidTileAt(v + rank, w + file) && isCheckBy(kingTile, tiles[v + rank][w + file])) {
					return true;
				}
			}
		}
			
		return false;
	}

	private boolean isCheckBy(Tile kingTile, Tile tile) {
		if(tile != null && !tile.isEmpty() && !isSameOpponentPiece(kingTile, tile) && tile.getPiece().validateMove(kingTile)) {
			System.out.println("Check Yes due to tile at " + tile.getPosition());
			return true;
		}
		return false;
	}

	private boolean isWhiteKingCheck() {
		return isKingCheckAt(getWhiteKingTile());
	}

	/*private boolean isCheck(boolean isWhiteTurn) {
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
	}*/

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
	
	/*private boolean isWhitePieceMovementPossibleAt(Tile tile) {
		for(Piece piece: getAllWhitePieces()) {
			if(piece.validateMove(tile)) return true;
		}
		return false;
	}*/
	
	private List<Piece> getAllWhitePieces(){
		List<Piece> pieces = new ArrayList<>();
		for(int rank = 0; rank < 8; rank++) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file]; 
				if(!tile.isEmpty() && tile.getPiece().getColor() == Color.WHITE) {
					pieces.add(tile.getPiece());
				}
			}
		}
		return pieces;
	}
	
	private boolean isValidTileAt(int fromRank, int fromFile) {
		return (fromRank <= 7 && fromRank >= 0) && (fromFile <= 7 && fromFile >= 0);
	}

	private Tile getBlackKingTile() {
		return getKingTile(Color.BLACK);
	}
	
	private Tile getKingTile(Color color) {
		for(int rank = 0; rank < 8; rank++) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				if(tile.isEmpty()) continue;
				if(tile.getPiece().getColor() != color) continue;
				if(tile.hasKingPiece()) return tile;
			}
		}
		return null;
	}
	
	private Tile getWhiteKingTile() {
		return getKingTile(Color.WHITE);
	}

	private boolean isPathSidewayEmpty(Tile from, Tile to) {
		int xOffset = new Integer(to.getRank()).compareTo(new Integer(from.getRank()));
		int yOffset = new Integer(to.getFileIndex()).compareTo(new Integer(from.getFileIndex()));
		return isPathEmpty(to, from, xOffset, yOffset);
	}

	private boolean isPathDiagonallyEmpty(Tile from, Tile to) {
		int xOffset = new Integer(to.getRank()).compareTo(new Integer(from.getRank()));
		int yOffset = new Integer(to.getFileIndex()).compareTo(new Integer(from.getFileIndex()));
		return isPathEmpty(to, from, xOffset, yOffset);
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
			System.out.println(String.format("Empty at %s:%s", tiles[fromRank][fromFile].getPosition(), tiles[fromRank][fromFile].isEmpty()));
			if(!tiles[fromRank][fromFile].isEmpty()) {
				System.out.println(tiles[fromRank][fromFile].getPosition() + " is not empty");
				return false;
			}
			fromRank += rankOffset;
			fromFile += fileOffset;
			
		}
		
		return true;
	}

	private boolean validateSourceTargetTile(Tile to, Tile from) {
		if(!isValidTile(from)) {
			System.out.println("Move Invalid due to invalid source tile");
			return false;
		}
		if(!isValidTile(to)) {
			System.out.println("Move Invalid due to invalid destination tile");
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
