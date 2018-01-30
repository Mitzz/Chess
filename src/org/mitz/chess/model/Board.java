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
	private Tile enPassantTile;
	
	private enum DirectionWeight{
		LEFT(-1), RIGHT(1), UP(1), DOWN(-1);
		
		private int weight;
		
		private DirectionWeight(int wight){
			this.weight = wight;
		}
		
		public int weight(){
			return weight;
		}
	}
	
	private enum Direction {
		LEFT 		(DirectionWeight.LEFT.weight()	, 0), 
		RIGHT		(DirectionWeight.RIGHT.weight(), 0), 
		UP			(0								, DirectionWeight.UP.weight()), 
		DOWN		(0								, DirectionWeight.DOWN.weight()), 
		UP_LEFT		(DirectionWeight.LEFT.weight()	, DirectionWeight.UP.weight()), 
		UP_RIGHT	(DirectionWeight.RIGHT.weight(), DirectionWeight.UP.weight()), 
		DOWN_RIGHT	(DirectionWeight.RIGHT.weight(), DirectionWeight.DOWN.weight()), 
		DOWN_LEFT	(DirectionWeight.LEFT.weight()	, DirectionWeight.DOWN.weight());
//		LEFT_LEFT_UP 		(DirectionWeight.LEFT.weight()	* 2, DirectionWeight.UP.weight()), 
//		LEFT_LEFT_DOWN 		(DirectionWeight.LEFT.weight()	* 2, DirectionWeight.DOWN.weight()),
//		UP_UP_LEFT 		(DirectionWeight.LEFT.weight(), DirectionWeight.UP.weight() * 2), 
//		UP_UP_RIGHT 		(DirectionWeight.RIGHT.weight(), DirectionWeight.UP.weight()* 2),
//		RIGHT_RIGHT_UP 		(DirectionWeight.RIGHT.weight() * 2	, DirectionWeight.UP.weight()), 
//		RIGHT_RIGHT_DOWN 		(DirectionWeight.RIGHT.weight()	* 2, DirectionWeight.DOWN.weight()),
//		DOWN_DOWN_LEFT 		(DirectionWeight.LEFT.weight(), DirectionWeight.DOWN.weight() * 2), 
//		DOWN_DOWN_RIGHT 		(DirectionWeight.RIGHT.weight(), DirectionWeight.DOWN.weight() * 2);
		
		
		final int col;
		final int row;
		
		private Direction(int col, int row){
			this.col = col;
			this.row = row;
		}
	};
	
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
		Piece capturedPiece = null;
		boolean isEnPassantMove = false;
		boolean valid = isPieceMoveValid(from, to) || (from.hasKingPiece() && isCastlingMove(to, from, isWhiteTurn)) || (isEnPassantMove = isEnPassantMove(from, to));
		boolean isKingCheckBeforeMove = false;
		if(!valid) return false;
		if(valid) {
			isKingCheckBeforeMove = from.hasKingPiece() && isKingCheckAt(from);
			capturedPiece = from.movePieceTo(to);
			successfulStep++;
		}
		boolean isKingCheckAfterMove = false;
		
		if(isWhiteTurn) isKingCheckAfterMove = isWhiteKingCheck();
		else 			isKingCheckAfterMove = isBlackKingCheck();
		
		if(isKingCheckAfterMove) {
			to.movePieceTo(from, capturedPiece);
			System.out.println("Piece Movement Blocked due to check");
			successfulStep--;
			return false;
		}
		
		boolean isCastlingMovement = false;
		if(isCastlingMove(from, to, isWhiteTurn) && (!(isCastlingMovement = isCastlingMovePossible(from, to, isWhiteTurn)) || isKingCheckBeforeMove)) {//Confused
			to.movePieceTo(from, capturedPiece);
			System.out.println("Piece Movement Blocked due to no castling possible");
			successfulStep--;
			return false;
		}
		
		if(isCastlingMovement && !isKingCheckBeforeMove) {
			doRookMovementForCastling(from, to, isWhiteTurn);
		}
		
		if(isEnPassantMove) {
			removeCapturedPieceInEnPassant(from, to);
		}
		determineCastlingPossibility(from, to, isWhiteTurn);
		determineEnPassantTile(from, to);
		if(isPawnPromotion(isWhiteTurn, to)) {
			System.out.println("Finally a promotion");
			setPawnForPromotion(to);
		}
		
		return valid;
	}
	
	
	
	private void removeCapturedPieceInEnPassant(Tile from, Tile to) {
		int rankOffset = 0;
		if(from.getRank() > to.getRank()) rankOffset = +1;
		else 							  rankOffset = -1;
		
		System.out.println("Captured Piece for 'en passant' move at: " + tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].getPosition());
		tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].removePiece();
	}

	private boolean isEnPassantMove(Tile from, Tile to) {
		System.out.println("Board.isEnPassantMove()" + (enPassantTile != null && from.hasPawnPiece() && enPassantTile.equals(to)));
		return (enPassantTile != null && from.hasPawnPiece() && enPassantTile.equals(to) );
	}

	private void determineEnPassantTile(Tile from, Tile to) {
		if(!to.isEmpty() && to.getPiece().isPawn() && from.isMovementSideways(to) && Math.abs(from.getRank() - to.getRank()) == 2) {
			int rankOffset = 0;
			if(from.getRank() > to.getRank()) rankOffset = +1;
			else 							  rankOffset = -1;
			enPassantTile = tiles[to.getRank() - 1 + rankOffset][from.getFile() - 97];
		} else {
			enPassantTile = null;
		}
		System.out.println("Board.determineEnPassantTile(EnpassantTile): " + ((enPassantTile == null) ? "None": enPassantTile.getPosition()));
	}

	private void doRookMovementForCastling(Tile from, Tile to, boolean isWhiteTurn) {
		if(to.getFile() == 'c') {
			getTileAt(to.getRank(), 'a').movePieceTo(getTileAt(to.getRank(), (char)(to.getFile() + 1)));
		} else {
			getTileAt(to.getRank(), 'h').movePieceTo(getTileAt(to.getRank(),(char)(to.getFile() - 1)));
		}
	}

	private void determineCastlingPossibility(Tile from, Tile to, boolean isWhiteTurn) {
		if(isWhiteTurn) {
			if(to.hasKingPiece()) {
				isWhiteCastlingPossibleOnQueenSide = false;
				isWhiteCastlingPossibleOnKingSide = false;
			}
			else if(from.isFirstRank() && from.getFile() == 'a') {
				isWhiteCastlingPossibleOnQueenSide = false;
			} else if(from.isFirstRank() && from.getFile() == 'h') {
				isWhiteCastlingPossibleOnKingSide = false;
			} else {
				System.out.println("White Castling Possible");
			}
		} else {
			if(to.hasKingPiece()) {
				isBlackCastlingPossibleOnQueenSide = false;
				isBlackCastlingPossibleOnKingSide = false;
			}
			else if(from.isLastRank() && from.getFile() == 'a') {
				isBlackCastlingPossibleOnQueenSide = false;
			} else if(from.isLastRank() && from.getFile() == 'h') {
				isBlackCastlingPossibleOnKingSide = false;
			}
		}
		
		System.out.println(String.format("Castling: WK - %s, WQ - %s, BK - %s, BQ - %s", isWhiteCastlingPossibleOnKingSide, isWhiteCastlingPossibleOnQueenSide, isBlackCastlingPossibleOnKingSide, isBlackCastlingPossibleOnQueenSide));
	}

	private boolean isCastlingMovePossible(Tile from, Tile to, boolean isWhiteTurn) {
		
		boolean isCastlingMovePossible = isWhiteTurn ? (isWhiteCastlingPossibleOnKingSide || isWhiteCastlingPossibleOnQueenSide) : (isBlackCastlingPossibleOnKingSide || isBlackCastlingPossibleOnQueenSide);
		if(!isCastlingMovePossible) return isCastlingMovePossible;
		boolean isCastlingKingSide = (to.getFile() - from.getFile()) == 2;
		if(isCastlingKingSide && isWhiteTurn && !isWhiteCastlingPossibleOnKingSide) 	return false;  
		if(!isCastlingKingSide && isWhiteTurn && !isWhiteCastlingPossibleOnQueenSide) 	return false;
		if(isCastlingKingSide && !isWhiteTurn && !isBlackCastlingPossibleOnKingSide) 	return false;
		if(!isCastlingKingSide && !isWhiteTurn && !isBlackCastlingPossibleOnQueenSide) 	return false;
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

	public GameState isGameOver(boolean isWhiteTurn) {
		Tile kingTile = isWhiteTurn ? getBlackKingTile() : getWhiteKingTile();
		boolean isKingMovemntMandatory = isKingMoveMandatory(kingTile);
		boolean isKingMovementPossible = isKingMovePossible(kingTile);
		if(isKingMovemntMandatory && !isKingMovementPossible) return GameState.OVER_DUE_TO_CHECKMATE;
		if(!isKingMovementPossible && !isPiecesMovementPossible(isWhiteTurn)) return GameState.OVER_DUE_TO_STALEMATE;
		return GameState.IN_PROGRESS;
	}
	
	private boolean isPiecesMovementPossible(boolean isWhiteTurn) {
		System.out.println("Checking for Stalement");
		List<Piece> allPieces = isWhiteTurn ? getAllBlackPieces() : getAllWhitePieces();
		System.out.println("Pieces on board!!!");
		allPieces.forEach(e -> System.out.println(e.getTile().getPosition()));
		boolean isMovementPossible = allPieces.stream().filter(e -> !e.isKingPiece()).anyMatch(obj -> isMovementPossibleFrom(obj.getTile()));
		return isMovementPossible;
	}
	
	private boolean isMovementPossibleFrom(Tile tile) {
		System.out.println("Board.isMovementPossibleFrom(tile): " + tile.getPosition());
		Direction[] directions = Direction.values();
		boolean[] exhausted = new boolean[directions.length];
		int step = 1;
		while(!areAllTrue(exhausted)) {
			System.out.println(String.format("************************ Step %s ****", step));
			for(Direction direction: directions) {
				int index = direction.ordinal();
				System.out.println(String.format("Exhausted at %s : %s", index, exhausted[index]));
				if(!exhausted[index]) {
					Tile nextTile = getNextTileFrom(tile, step, direction);
					if(nextTile == null) exhausted[index] = true;
					else {
						if(isPieceMoveValid(tile, nextTile)) return true;
					}
				}
			}
			System.out.println(String.format("************************ Step %s Over****", step));
			step++;
		}
		
		
		List<Integer> i = Arrays.asList(1, -1, 2, -2);
		for(Integer v: i) {
			for(Integer w: i) {
				int rank = v + tile.getRank() - 1;
				int file = w + tile.getFile() - 97;
				if(Math.abs(v) != Math.abs(w) && isValidTileAt(rank, file) && isPieceMoveValid(tile, tiles[rank][file])) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Tile getNextTileFrom(Tile tile, int step, Direction direction) {
		Tile nextTile = null;
		int rank = step * direction.row + tile.getRank() - 1;
		int file = step * direction.col + tile.getFile() - 97;
		if(isValidTileAt(rank, file)) 
			nextTile = getTileAt(rank, file);
		if(nextTile != null)
			System.out.println("Next Tile: " + nextTile.getPosition());
		else {
			System.out.println("Next Tile: " + null);
		}
		return nextTile;
	}

	public static boolean areAllTrue(boolean[] array)
	{
	    for(boolean b : array) if(!b) return false;
	    return true;
	}

	private boolean isCheckmate(boolean isWhiteTurn) {
		Tile kingTile = null;
		if(isWhiteTurn)
			kingTile = getBlackKingTile();
		else 
			kingTile = getWhiteKingTile();
		
		boolean isKingMoveMandatory = isKingMoveMandatory(kingTile);
		
		return isKingMoveMandatory && !isKingMovePossible(kingTile);
	}

	private boolean isKingMoveMandatory(Tile kingTile) {
		List<Tile> opponentTilesForKingCheckAt = null;
		//Is King Check by Opponent Piece
		opponentTilesForKingCheckAt = getOpponentTilesResponsibleForCheckToKing(kingTile);
		if(opponentTilesForKingCheckAt.size() == 0) {
			System.out.println("No Possibility of checkmate since king movement is not mandatory");
			return false; 
		}
		System.out.println("Possibility of checkmate since king movement is mandatory");
		System.out.println("opponentTiles responsible for king movement");
		opponentTilesForKingCheckAt.stream().forEach(e -> System.out.println(e.getPosition()));
		
		//Can't kill two opponent pieces in one move
		if(opponentTilesForKingCheckAt.size() == 1) {
			List<Tile> userTiles =  getValidMovementTilesAt(opponentTilesForKingCheckAt.get(0));
			System.out.println("Board.isGameOver(userTiles)");
			userTiles.stream().forEach(e -> System.out.println(e.getPosition()));
			if(userTiles.size() >= opponentTilesForKingCheckAt.size()) {
				System.out.println("King Saved by Piece");
				return false;
			}
		}
		
		return true;
	}

	private boolean isKingMovePossible(Tile kingTile) {
		for(Direction direction: Direction.values()) {
			if(isKingMovePossible(direction, kingTile)) return true;
		}
		
		return false;
	}
	
	private boolean isKingMovePossible(Direction direction, Tile kingTile) {
		return isKingMovePossible(kingTile.getRank() - 1 +  direction.row, kingTile.getFile() - 97 + direction.col, kingTile);
	}
	
	private boolean isKingMovePossible(int r, int f, Tile kingTile) {
		
		if(!isValidTileAt(r, f)) 
			return false;
		
//		Color color = sourceTile.getPiece().getColor();
		Tile possibleMovementTile = getTileAt(r, f);
		
		System.out.println("Board.isMovePossible(KingTile): " + possibleMovementTile.getPosition());
		boolean isMovePossible = true;
		if(possibleMovementTile.isEmpty() || !isSameOpponentPiece(kingTile, possibleMovementTile)) {
			System.out.println("Inside");
			Piece removedPiece = kingTile.movePieceTo(possibleMovementTile);
			isMovePossible = !isKingCheckAt(possibleMovementTile);
			possibleMovementTile.movePieceTo(kingTile, removedPiece);
		} else {
			return false;
		}
		 
		return isMovePossible;
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
	
	

	private boolean isPieceMoveValid(Tile from, Tile to) {
		if(from.isEmpty()) 					return false;
		if(from.equals(to)) 				return false;
		if(isSameOpponentPiece(from, to)) 	return false;
		
		boolean isMoveValid = from.getPiece().validateMove(to);
		if(!isMoveValid) return false;
		
		boolean isPathEmpty = true;
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
		
		for(Direction direction: Direction.values()) {
			if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, direction))) return true;
		}
		
		return isCheckByKnight(kingTile);
	}
	
	private List<Tile> getOpponentTilesResponsibleForCheckToKing(Tile kingTile) {
		System.out.println("King Tile Present at: " + kingTile.getPosition());
		List<Tile> opponentTiles = new ArrayList<>();
		Tile nonEmptyTile = null;
		for (Direction direction : Direction.values()) {
			nonEmptyTile = getNextNonEmptyTile(kingTile, direction);
			if(isCheckBy(kingTile, nonEmptyTile)) opponentTiles.add(nonEmptyTile);
		}
		opponentTiles.addAll(getTilesForKingCheckByKnightAt(kingTile));
		return opponentTiles;
	}
	
	private List<Tile> getTilesForKingCheckByKnightAt(Tile kingTile) {
		List<Integer> i = Arrays.asList(1, -1, 2, -2);
		List<Tile> tilesc = new ArrayList<>();
		int file = (kingTile.getFile() - 97);
		int rank = kingTile.getRank() - 1;
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
		System.out.println("Board.isCheckByKnight()");
		List<Integer> i = Arrays.asList(1, -1, 2, -2);
		
		int file = (kingTile.getFile() - 97);
		int rank = kingTile.getRank() - 1;
		for(Integer v: i) {
			for(Integer w: i) {
				if(Math.abs(v) != Math.abs(w) && isValidTileAt(v + rank, w + file) && isCheckBy(kingTile, tiles[v + rank][w + file])) {
					return true;
				}
			}
		}
			
		return false;
	}

	private boolean isCheckBy(Tile kingTile, Tile opponentTile) {
		
		if(opponentTile != null && !opponentTile.isEmpty() && !isSameOpponentPiece(kingTile, opponentTile) && opponentTile.getPiece().validateMove(kingTile)) {
			System.out.println("Check due to Opponent tile at " + opponentTile.getPosition());
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
	
	private Tile getNextNonEmptyTile(Tile from, Direction direction) {
		return getNextNonEmptyTile(from, direction.row, direction.col);
	}

	private Tile getNextNonEmptyTile(Tile from, int rankOffset, int fileOffset) {

		System.out.println("Board.getNextNonEmptyTile(from, rankOffset, fileOffset): " + from.getPosition() + " <-> " + rankOffset + " <-> " + fileOffset);
		Tile nonEmptyTile = null;
		int fromRank = from.getRank() - 1;
		int fromFile = from.getFile() - 97;
		fromRank += rankOffset;
		fromFile += fileOffset;
		while(isValidTileAt(fromRank, fromFile)) {
			if(!tiles[fromRank][fromFile].isEmpty()) { 
				nonEmptyTile = tiles[fromRank][fromFile];
				break;
			}
			fromRank += rankOffset;
			fromFile += fileOffset;
		}
		if(null != nonEmptyTile)
			System.out.println("Non Empty Tile: " + nonEmptyTile.getPosition());
		return nonEmptyTile;
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
		int yOffset = new Integer(to.getFile() - 97).compareTo(new Integer(from.getFile() - 97));
		return isPathEmpty(to, from, xOffset, yOffset);
	}

	private boolean isPathDiagonallyEmpty(Tile from, Tile to) {
		int xOffset = new Integer(to.getRank()).compareTo(new Integer(from.getRank()));
		int yOffset = new Integer(to.getFile() - 97).compareTo(new Integer(from.getFile() - 97));
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
		int fromRank = from.getRank() - 1;
		int fromFile = from.getFile() - 97;
		if(!validateSourceTargetTile(to, from)) {
			return false;
		}
		
		fromRank += rankOffset;
		fromFile += fileOffset;
		while(!(to.getRank() - 1 == fromRank && (to.getFile() - 97) == fromFile)) {
//			System.out.println(String.format("Empty at %s:%s", tiles[fromRank][fromFile].getPosition(), tiles[fromRank][fromFile].isEmpty()));
			if(!tiles[fromRank][fromFile].isEmpty()) {
//				System.out.println(tiles[fromRank][fromFile].getPosition() + " is not empty");
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
