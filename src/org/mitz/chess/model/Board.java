package org.mitz.chess.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.mitz.utility.CollectionUtility;

public class Board {
	final static Logger logger = Logger.getLogger(Board.class);
	
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
		fromRank = fromRank - 1;
		fromFile = (int)fromFile - 97;
		toRank = toRank - 1;
		toFile = (int)toFile - 97;
		if(!isValidTileAt(fromRank, fromFile) || !isValidTileAt(toRank, toFile)){
			logger.info(String.format("Move Invalid due to invalid source tile (%s, %s) or destination tile (%s, %s)", fromRank, fromFile, toRank, toFile));
			return false;
		}
		Tile from = tiles[fromRank][fromFile];
		Tile to = tiles[toRank][toFile];
		logger.info("------------Movement Step " + successfulStep + " Initiated from " + from.getPosition() + " to " + to.getPosition() + "for " + (isWhiteTurn ? "white" : "black") + "----------");
		if(!from.isEmpty() && !isMoveByTurn(isWhiteTurn, from)) {
			logger.info("Move Invalid due wrong player played");
			return false;
		}
		Piece capturedPiece = null;
		boolean isEnPassantMove = false;
		boolean isCastlingMove = false;
		boolean valid = isPieceMoveValid(from, to) || (isCastlingMove = isCastlingMovePossible(from, to, isWhiteTurn)) || (isEnPassantMove = isEnPassantMove(from, to));
		if(!valid) {
			logger.info("Movement Invalid");
			return false;
		}
		if(valid) {
			capturedPiece = from.movePieceTo(to);
			successfulStep++;
		}
		boolean isKingCheckAfterMove = false;
		
		if(isWhiteTurn) isKingCheckAfterMove = isWhiteKingCheck();
		else 			isKingCheckAfterMove = isBlackKingCheck();
		
		if(isKingCheckAfterMove) {
			to.movePieceTo(from, capturedPiece);
			logger.info("Movement Blocked due to check");
			successfulStep--;
			return false;
		}
		
		if(isCastlingMove)  doRookMovementForCastling(from, to, isWhiteTurn);
		if(isEnPassantMove) removeCapturedPieceInEnPassant(from, to);
		
		determineCastlingPossibility(from, to, isWhiteTurn);
		determineEnPassantTile(from, to);
		if(isPawnPromotion(isWhiteTurn, to)) {
			logger.info("Pawn promotion");
			setPawnForPromotion(to);
		}
		
		return valid;
	}
	
	private void removeCapturedPieceInEnPassant(Tile from, Tile to) {
		int rankOffset = 0;
		if(from.getRank() > to.getRank()) rankOffset = +1;
		else 							  rankOffset = -1;
		
		logger.debug("Captured Piece for 'en passant' move at: " + tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].getPosition());
		tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].removePiece();
	}

	private boolean isEnPassantMove(Tile from, Tile to) {
		logger.info("Enpassant Movement Possibility Check");
		boolean isEnPassantMovePossible = (enPassantTile != null && from.hasPawnPiece() && enPassantTile.equals(to) ); 
		logger.info("Enpassant Movement: " + isEnPassantMovePossible);
		return isEnPassantMovePossible;
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
		logger.debug("EnpassantTile: " + ((enPassantTile == null) ? "None": enPassantTile.getPosition()));
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
				logger.debug("White Castling Possible");
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
		
		logger.info(String.format("Castling Status: WK - %s, WQ - %s, BK - %s, BQ - %s", isWhiteCastlingPossibleOnKingSide, isWhiteCastlingPossibleOnQueenSide, isBlackCastlingPossibleOnKingSide, isBlackCastlingPossibleOnQueenSide));
	}

	private boolean isCastlingMovePossible(Tile from, Tile to, boolean isWhiteTurn) {
		logger.info("Castling Movement Possibility Check");
		if(!from.hasKingPiece()) 	return false;
		if(isKingCheckAt(from)) 	return false;
		if(!to.isEmpty()) 			return false;
		boolean isCastlingMovePossible = isWhiteTurn ? (isWhiteCastlingPossibleOnKingSide || isWhiteCastlingPossibleOnQueenSide) : (isBlackCastlingPossibleOnKingSide || isBlackCastlingPossibleOnQueenSide);
		if(!isCastlingMovePossible) return isCastlingMovePossible;
		boolean isCastlingKingSide = (to.getFile() - from.getFile()) == 2;
		if(isCastlingKingSide && isWhiteTurn && !isWhiteCastlingPossibleOnKingSide) 	return false;  
		if(!isCastlingKingSide && isWhiteTurn && !isWhiteCastlingPossibleOnQueenSide) 	return false;
		if(isCastlingKingSide && !isWhiteTurn && !isBlackCastlingPossibleOnKingSide) 	return false;
		if(!isCastlingKingSide && !isWhiteTurn && !isBlackCastlingPossibleOnQueenSide) 	return false;

		isCastlingMovePossible = isPathEmptyForCastling(to, isWhiteTurn, isCastlingKingSide);

		logger.info("Castling Movement: " + isCastlingMovePossible);
		return isCastlingMovePossible;
	}
	
	private boolean isPathEmptyForCastling(Tile to, boolean isWhiteTurn, boolean isCastlingKingSide) {
		return isKingPathEmptyForCastling(to, isWhiteTurn, isCastlingKingSide) && isRookPathEmptyForCastling(to, isWhiteTurn, isCastlingKingSide);
	}

	private boolean isRookPathEmptyForCastling(Tile to, boolean isWhiteTurn, boolean isCastlingKingSide) {
		char file = ' ';
		if(isCastlingKingSide) 	file = 'h';
		else 					file = 'a';
		
		return isPathEmptyForCastling(to, isWhiteTurn, file);
	}

	private boolean isKingPathEmptyForCastling(Tile to, boolean isWhiteTurn, boolean isCastlingKingSide) {
		return isPathEmptyForCastling(to, isWhiteTurn, 'e');
	}

	private boolean isPathEmptyForCastling(Tile to, boolean isWhiteTurn, char file) {
		Tile sourceTile;
		if(isWhiteTurn) sourceTile = getTileAt(1, file);
		else 			sourceTile = getTileAt(8, file);
		return isPathSidewayEmpty(sourceTile, to);
	}

	public GameState getGameStatus(boolean isWhiteTurn) {
		Tile kingTile = isWhiteTurn ? getBlackKingTile() : getWhiteKingTile();
		logger.info("*****Game Over Check********");
		boolean isKingMovemntMandatory = isKingMoveMandatory(kingTile);
		boolean isKingMovementPossible = isKingMovePossible(kingTile);
		if(isKingMovemntMandatory && !isKingMovementPossible) return GameState.OVER_DUE_TO_CHECKMATE;
		if(!isKingMovementPossible && !isPiecesMovementPossible(isWhiteTurn)) return GameState.OVER_DUE_TO_STALEMATE;
		return GameState.IN_PROGRESS;
	}
	
	private boolean isPiecesMovementPossible(boolean isWhiteTurn) {
		logger.debug("Checking for Stalement");
		List<Piece> allPieces = isWhiteTurn ? getAllBlackPieces() : getAllWhitePieces();
		logger.debug("Pieces on board!!!");
		allPieces.forEach(e -> logger.debug(e.getTile().getPosition()));
		boolean isMovementPossible = allPieces.stream().filter(e -> !e.isKingPiece()).anyMatch(obj -> isMovementPossibleFrom(obj.getTile()));
		return isMovementPossible;
	}
	
	private boolean isMovementPossibleFrom(Tile tile) {
		logger.debug("Board.isMovementPossibleFrom(tile): " + tile.getPosition());
		Direction[] directions = Direction.values();
		boolean[] exhausted = new boolean[directions.length];
		int step = 1;
		while(!areAllTrue(exhausted)) {
			logger.debug(String.format("************************ Step %s ****", step));
			for(Direction direction: directions) {
				int index = direction.ordinal();
				logger.debug(String.format("Exhausted at %s : %s", index, exhausted[index]));
				if(!exhausted[index]) {
					Tile nextTile = getNextTileFrom(tile, step, direction);
					if(nextTile == null) exhausted[index] = true;
					else {
						if(isPieceMoveValid(tile, nextTile) || isEnPassantMove(tile, nextTile)) return true;
					}
				}
			}
			logger.debug(String.format("************************ Step %s Over****", step));
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
			logger.debug("Next Tile: " + nextTile.getPosition());
		else {
			logger.debug("Next Tile: " + null);
		}
		return nextTile;
	}

	public static boolean areAllTrue(boolean[] array)
	{
	    for(boolean b : array) if(!b) return false;
	    return true;
	}

	private boolean isKingMoveMandatory(Tile kingTile) {
		List<Tile> opponentTilesForKingCheckAt = null;
		//Is King Check by Opponent Piece
		opponentTilesForKingCheckAt = getOpponentTilesResponsibleForCheckToKing(kingTile);
		if(opponentTilesForKingCheckAt.size() == 0) {
			logger.debug("No Possibility of checkmate since king movement is not mandatory");
			return false; 
		}
		logger.debug("Possibility of checkmate since king movement is mandatory");
		logger.debug("opponentTiles responsible for king movement");
		opponentTilesForKingCheckAt.stream().forEach(e -> logger.debug(e.getPosition()));
		
		//Can't kill two opponent pieces in one move
		if(opponentTilesForKingCheckAt.size() == 1) {
			List<Tile> userTiles =  getValidMovementTilesAt(opponentTilesForKingCheckAt.get(0));
			logger.debug("Board.isGameOver(userTiles)");
			userTiles.stream().forEach(e -> logger.debug(e.getPosition()));
			if(userTiles.size() >= opponentTilesForKingCheckAt.size()) {
				logger.debug("King Saved by Piece");
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
		
		logger.debug("Board.isMovePossible(KingTile): " + possibleMovementTile.getPosition());
		boolean isMovePossible = true;
		if(possibleMovementTile.isEmpty() || !isSameOpponentPiece(kingTile, possibleMovementTile)) {
			logger.debug("Inside");
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
	
	public Collection<Tile> getPossibleMovementTilesFrom(Tile sourceTile){
		if(!isValidTile(sourceTile) || sourceTile.isEmpty()) return new ArrayList<>();
		Collection<Tile> targetTiles = (sourceTile.getPiece().getColor() == Color.BLACK ? getNonBlackTiles() : getNonWhiteTiles());
		Collection<Tile> possibleMovementTiles = 
				targetTiles.stream()
					.filter(targetTile -> isPieceMoveValid(sourceTile, targetTile))
					.collect(Collectors.toList());
		return possibleMovementTiles;
	}

	private boolean isPieceMoveValid(Tile from, Tile to) {
		if(from.isEmpty()) 					return false;
		if(from.equals(to)) 				return false;
		if(isSameOpponentPiece(from, to)) 	return false;
		
		boolean isMoveValid = from.getPiece().validateMove(to);
		logger.info("Piece move valid: " + isMoveValid);
		if(!isMoveValid) return false;
		
		boolean isPathEmpty = true;
		if(from.isMovementDiagonal(to) && !isPathDiagonallyEmpty(from, to)) 
			isPathEmpty = false;
		if(from.isMovementSideways(to) && !isPathSidewayEmpty(from, to)) 
			isPathEmpty = false;
		logger.info("Path Empty: " + isPathEmpty);
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
			logger.debug(menu);
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
		logger.debug("King Tile Present at: " + kingTile.getPosition());
		
		for(Direction direction: Direction.values()) {
			if(isCheckBy(kingTile, getNextNonEmptyTile(kingTile, direction))) return true;
		}
		
		return isCheckByKnight(kingTile);
	}
	
	private List<Tile> getOpponentTilesResponsibleForCheckToKing(Tile kingTile) {
		logger.debug("King Tile Present at: " + kingTile.getPosition());
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
		logger.debug("Board.isCheckByKnight()");
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
			logger.debug("Check due to Opponent tile at " + opponentTile.getPosition());
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
		logger.debug("King Tile Present at: " + kingTile.getPosition());
		Tile nextTile = null;
		nextTile = getNextNonEmptyTile(kingTile, 1, 0);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 1, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 0, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, 1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, 0);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, -1, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 0, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
			return true;
		}
		nextTile = getNextNonEmptyTile(kingTile, 1, -1);
		if(nextTile != null && !isSameOpponentPiece(kingTile, nextTile) && nextTile.getPiece().validateMove(kingTile)) {
			logger.debug("Check Yes due to tile at " + nextTile.getPosition());
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
					logger.debug("Check Yes due to tile at " + nextTile.getPosition());
					return true;
				}
			}
		}
		
		logger.debug("No Check");
		return false;
	}*/

	private boolean isSameOpponentPiece(Tile v, Tile w) {
		return (!v.isEmpty() && !w.isEmpty() && v.getPiece().getColor() == w.getPiece().getColor());
	
	}
	
	private Tile getNextNonEmptyTile(Tile from, Direction direction) {
		return getNextNonEmptyTile(from, direction.row, direction.col);
	}

	private Tile getNextNonEmptyTile(Tile from, int rankOffset, int fileOffset) {

		logger.debug("Board.getNextNonEmptyTile(from, rankOffset, fileOffset): " + from.getPosition() + " <-> " + rankOffset + " <-> " + fileOffset);
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
			logger.debug("Non Empty Tile: " + nonEmptyTile.getPosition());
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

	private boolean isValidTile(Tile tile) {
		return (tile.getRank() <= 8 && tile.getRank() >= 1) && 
				(tile.getFile() == 'a' || tile.getFile() == 'b' || tile.getFile() == 'c' || tile.getFile() == 'd' || tile.getFile() == 'e' || tile.getFile() == 'f' || tile.getFile() == 'g' || tile.getFile() == 'h' );
	}

	private boolean isMoveByTurn(boolean isWhiteTurn, Tile tile) {
		return (isWhiteTurn && tile.getPiece().getColor() == Color.WHITE) || (!isWhiteTurn && tile.getPiece().getColor() == Color.BLACK);
	}
	
	public boolean isPathEmpty(Tile to, Tile from, int rankOffset, int fileOffset) {
//		logger.debug("Rank Offset: " + rankOffset + ", File Offset: " + fileOffset);
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
//			logger.debug(String.format("Empty at %s:%s", tiles[fromRank][fromFile].getPosition(), tiles[fromRank][fromFile].isEmpty()));
			if(!tiles[fromRank][fromFile].isEmpty()) {
//				logger.debug(tiles[fromRank][fromFile].getPosition() + " is not empty");
				return false;
			}
			fromRank += rankOffset;
			fromFile += fileOffset;
			
		}
		
		return true;
	}

	private boolean validateSourceTargetTile(Tile to, Tile from) {
		if(!isValidTile(from)) {
			logger.info("Move Invalid due to invalid source tile");
			return false;
		}
		if(!isValidTile(to)) {
			logger.info("Move Invalid due to invalid destination tile");
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
	
	private Predicate<Tile> isEmptyTile() {
		return Tile::isEmpty;
	}
	
	private Predicate<Tile> isNonEmptyTile() {
		return isEmptyTile().negate();
	}
	
	private Predicate<Tile> isWhitePieceTile() {
		return (tile -> tile.getPiece().getColor() == Color.WHITE);
	}
	
	private Predicate<Tile> isBlackPieceTile() {
		return (tile -> tile.getPiece().getColor() == Color.BLACK);
	}
	
	private Collection<Tile> getNonWhiteTiles() {
		return getTiles(isEmptyTile().or(isBlackPieceTile()));
	}
	
	private Collection<Tile> getNonBlackTiles() {
		return getTiles(isEmptyTile().or(isWhitePieceTile()));
	}
	
	private Collection<Tile> getWhiteTiles() {
		return getTiles(isNonEmptyTile().and(isWhitePieceTile()));
	}
	
	private Collection<Tile> getBlackTiles() {
		return getTiles(isNonEmptyTile().and(isBlackPieceTile()));
	}
	
	private Collection<Tile> getTiles(Predicate<Tile> predicate){
		return CollectionUtility.getList(tiles)
			.stream()
			.filter(predicate)
			.collect(Collectors.toList());
	}
	
	public Collection<Tile> getWhiteMovableTiles(){
		return getMovableTilesOf(Color.white);
	}
	
	public Collection<Tile> getBlackMovableTiles(){
		return getMovableTilesOf(Color.BLACK);
	}
	
	public Collection<Tile> getMovableTilesOf(Color color){
		Collection<Tile> movableTiles = new HashSet<>();
		Collection<Tile> tiles = (Color.white == color ? getWhiteTiles() : getBlackTiles());
		Collection<Tile> otherTiles = (Color.white == color ? getNonWhiteTiles() : getNonBlackTiles());
		
		movableTiles = tiles.stream().filter(tile -> (otherTiles.stream().anyMatch(otherTile -> isPieceMoveValid(tile, otherTile)))).collect(Collectors.toSet());
		return movableTiles;
	}

	public Collection<Tile> getMovableTiles(boolean isWhiteTurn) {
		return (isWhiteTurn ? getWhiteMovableTiles(): getBlackMovableTiles());
	}
}
