package org.mitz.chess.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.mitz.utility.CollectionUtility;

public class Board {
	final static Logger logger = Logger.getLogger(Board.class);
	
	private Tile[][] tiles;
	private int successfulStep;
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
		Tile from = tiles[fromRank][fromFile];
		Tile to = tiles[toRank][toFile];
		logger.info("------------Movement Initiated from " + from.getPosition() + " to " + to.getPosition() + " for " + (isWhiteTurn ? "white" : "black") + "----------");
		if(!isValidTile(from) || !isValidTile(to)){
			logger.info(String.format("Move Invalid due to invalid source tile (%s, %s) or destination tile (%s, %s)", fromRank, fromFile, toRank, toFile));
			return false;
		}
		if(!from.isEmpty() && !isMoveByTurn(isWhiteTurn, from)) {
			logger.info("Move Invalid due wrong player played");
			return false;
		}
		boolean isValidEnPassantMove = false;
		boolean isValidCastlingMove = false;
		boolean isValid = isValidPieceMove(from, to) || (isValidCastlingMove = isValidCastlingMove(from, to)) || (isValidEnPassantMove = isValidEnPassantMove(from, to));
		if(!isValid) {
			logger.info(String.format("Movement Invalid from %s to %s for piece %s", from.getPosition(), to.getPosition(), from.getPiece() == null ? "NA": from.getPiece().getDescription()));
			return false;
		}
		
		if(isKingCheckAfterPieceMovementOf(from, to)) {
			logger.info("Movement Blocked due to check");
			return false;
		} else {
			from.movePieceTo(to);
			successfulStep++;
		}
		
		if(isValidCastlingMove)  doRookMovementForCastling(from, to);
		if(isValidEnPassantMove) removeCapturedPieceInEnPassant(from, to);
		
		determineCastlingPossibility(from, to, isWhiteTurn);
		determineEnPassantTile(from, to);
		logger.info(String.format("%s. Piece %s moved from %s to %s", successfulStep, to.getPiece().getDescription(), from.getPosition(), to.getPosition()));
		if(isPawnPromotion(isWhiteTurn, to)) {
			logger.info("Pawn promotion");
			setPawnForPromotion(to);
		}
		
		return isValid;
	}
	
	private void removeCapturedPieceInEnPassant(Tile from, Tile to) {
		int rankOffset = 0;
		if(from.getRank() > to.getRank()) rankOffset = +1;
		else 							  rankOffset = -1;
		
		logger.debug("Captured Piece for 'en passant' move at: " + tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].getPosition());
		tiles[enPassantTile.getRank() - 1 + rankOffset][enPassantTile.getFile() - 97].removePiece();
	}

	private boolean isValidEnPassantMove(Tile from, Tile to) {
		logger.debug("Enpassant Movement Possibility Check");
		boolean isEnPassantMovePossible = (enPassantTile != null && from.hasPawnPiece() && enPassantTile.equals(to) && 
					Arrays.asList(Direction.DOWN_LEFT, Direction.DOWN_RIGHT, Direction.UP_LEFT, Direction.UP_RIGHT).stream()
						.map(dir -> getNextTileFrom(from, 1, dir))
						.filter(this::isValidTile)
						.anyMatch(tile -> tile.equals(enPassantTile))); 
		logger.debug("Enpassant Movement: " + isEnPassantMovePossible);
		return isEnPassantMovePossible;
	}

	private Board determineEnPassantTile(Tile from, Tile to) {
		if(!to.isEmpty() && to.getPiece().isPawn() && from.isMovementSideways(to) && Math.abs(from.getRank() - to.getRank()) == 2) {
			int rankOffset = 0;
			if(from.getRank() > to.getRank()) rankOffset = +1;
			else 							  rankOffset = -1;
			enPassantTile = tiles[to.getRank() - 1 + rankOffset][from.getFile() - 97];
		} else {
			enPassantTile = null;
		}
		logger.debug("EnpassantTile: " + ((enPassantTile == null) ? "None": enPassantTile.getPosition()));
		return this;
	}

	private Board doRookMovementForCastling(Tile from, Tile to) {
		boolean isCastlingKingSide = to.getFile() == 'c';
		if(isCastlingKingSide) {
			getTileAt('a', to.getRank()).movePieceTo(getTileAt((char)(to.getFile() + 1), to.getRank()));
		} else {
			getTileAt('h', to.getRank()).movePieceTo(getTileAt((char)(to.getFile() - 1), to.getRank()));
		}
		return this;
	}

	private Board determineCastlingPossibility(Tile from, Tile to, boolean isWhiteTurn) {
		if(isWhiteTurn) {
			if(to.hasKingPiece()) {
				isWhiteCastlingPossibleOnQueenSide = false;
				isWhiteCastlingPossibleOnKingSide = false;
			}
			else if(from.isFirstRank() && from.getFile() == 'a') {
				isWhiteCastlingPossibleOnQueenSide = false;
			} else if(from.isFirstRank() && from.getFile() == 'h') {
				isWhiteCastlingPossibleOnKingSide = false;
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
		
		logger.debug(String.format("Castling Status: WK - %s, WQ - %s, BK - %s, BQ - %s", isWhiteCastlingPossibleOnKingSide, isWhiteCastlingPossibleOnQueenSide, isBlackCastlingPossibleOnKingSide, isBlackCastlingPossibleOnQueenSide));
		return this;
	}

	private boolean isValidCastlingMove(Tile from, Tile to) {
		logger.debug("Castling Movement Possibility Check");
		if(to.getRank() != from.getRank()) 	return false;
		if(!to.isEmpty()) 					return false;
		if(!from.hasKingPiece()) 			return false;
		if(isKingCheckAt(from)) 			return false;
		boolean isWhite = from.getPiece().getColor() == Color.white;
				
		boolean isCastlingMovePossible = isWhite ? (isWhiteCastlingPossibleOnKingSide || isWhiteCastlingPossibleOnQueenSide) : (isBlackCastlingPossibleOnKingSide || isBlackCastlingPossibleOnQueenSide);
		if(!isCastlingMovePossible) return isCastlingMovePossible;
		boolean isCastlingKingSide = (to.getFile() - from.getFile()) == 2;
		if(!isCastlingKingSide && to.getFile() != 'c') return false; 
		if(isCastlingKingSide && isWhite && !isWhiteCastlingPossibleOnKingSide) 	return false;  
		if(!isCastlingKingSide && isWhite && !isWhiteCastlingPossibleOnQueenSide) 	return false;
		if(isCastlingKingSide && !isWhite && !isBlackCastlingPossibleOnKingSide) 	return false;
		if(!isCastlingKingSide && !isWhite && !isBlackCastlingPossibleOnQueenSide) 	return false;

		isCastlingMovePossible = isPathSidewayEmpty(from, to);

		logger.debug("Castling Movement: " + isCastlingMovePossible);
		return isCastlingMovePossible;
	}
	
	public GameState getGameStatus(boolean isWhiteTurn) {
		Tile kingTile = isWhiteTurn ? getKingTileOf(Color.black) : getKingTileOf(Color.white);
		logger.info("*****Game Over Check********");
		boolean isKingMovemntMandatory = isKingMoveMandatory(kingTile);
		boolean isKingMovementPossible = isKingMovePossible(kingTile);
		if(isKingMovemntMandatory && !isKingMovementPossible) return GameState.OVER_DUE_TO_CHECKMATE;
		if(!isKingMovementPossible && !isPiecesMovementPossible(isWhiteTurn)) return GameState.OVER_DUE_TO_STALEMATE;
		return GameState.IN_PROGRESS;
	}
	
	private boolean isPiecesMovementPossible(boolean isWhiteTurn) {
		logger.info("Checking for Stalement");
		List<Piece> allPieces = isWhiteTurn ? getAllPiecesOf(Color.black) : getAllPiecesOf(Color.white);
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
						if(isValidPieceMove(tile, nextTile) || isValidEnPassantMove(tile, nextTile)) return true;
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
				if(Math.abs(v) != Math.abs(w) && isValidTileAt(rank, file) && isValidPieceMove(tile, tiles[rank][file])) {
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
			nextTile = getTileAt((char)(step * direction.col + tile.getFile()), step * direction.row + tile.getRank());
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
		} else {
			logger.info(String.format("Possibility of checkmate since king movement is check at %s by %s pieces", kingTile.getPosition(), opponentTilesForKingCheckAt.size()));
			logger.info("Opponent Tiles responsible for king movement");
			opponentTilesForKingCheckAt.stream().forEach(e -> logger.info(e.getPosition()));
		}
		
		//Can't kill two opponent pieces in one move
		if(opponentTilesForKingCheckAt.size() == 1) {
			List<Tile> userTiles =  getValidMovementTilesAt(opponentTilesForKingCheckAt.get(0));
			userTiles.stream().forEach(e -> logger.debug(e.getPosition()));
			if(userTiles.size() >= opponentTilesForKingCheckAt.size()) {
				logger.info("King Saved by Piece");
				return false;
			} else {
				boolean canCheckBlocked = canCheckBeBlock(kingTile, opponentTilesForKingCheckAt);
				if(canCheckBlocked) {
					logger.info("King Saved by blocking the path");
					return false;
				}
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
		int r = kingTile.getRank() - 1 +  direction.row;
		int f = kingTile.getFile() - 97 + direction.col;
		if(!isValidTileAt(r, f)) 
			return false;
		return isKingMovePossible(kingTile.getRank() + direction.row, (char)(kingTile.getFile() + direction.col), kingTile);
	}
	
	private boolean isKingMovePossible(int r, char f, Tile kingTile) {
		
		Tile possibleMovementTile = getTileAt(f, r);
		
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
		List<Piece> opponentPieces = (targetTile.getPiece().getColor() == Color.BLACK ? getAllPiecesOf(Color.white) : getAllPiecesOf(Color.black));
		List<Tile> tiles = new ArrayList<>();
		for(Piece p: opponentPieces) {
			Tile tile = p.getTile();
			if(!p.isKingPiece() && isValidPieceMove(tile, targetTile) && !isKingCheckAfterPieceRemovalOf(tile)) tiles.add(tile); 
		}
		
		return tiles;
	}
	
	public Collection<Tile> getPossibleMovementTilesFrom(Tile sourceTile){
		if(!isValidTile(sourceTile) || sourceTile.isEmpty()) return new ArrayList<>();
		Collection<Tile> targetTiles = (sourceTile.getPiece().getColor() == Color.BLACK ? getNonBlackTiles() : getNonWhiteTiles());
		Collection<Tile> possibleMovementTiles = 
				targetTiles.stream()
					.filter(targetTile -> !isKingCheckAfterPieceMovementOf(sourceTile, targetTile) && (isValidPieceMove(sourceTile, targetTile) || isValidEnPassantMove(sourceTile, targetTile) || isValidCastlingMove(sourceTile, targetTile))) 
					.collect(Collectors.toList());
		return possibleMovementTiles;
	}

	private boolean isValidPieceMove(Tile from, Tile to) {
		if(from.isEmpty()) 					return false;
		if(from.equals(to)) 				return false;
		if(isSameOpponentPiece(from, to)) 	return false;
		
		boolean isMoveValid = from.getPiece().validateMove(to);
		logger.debug("Piece move valid: " + isMoveValid);
		if(!isMoveValid) return false;
		
		boolean isPathEmpty = true;
		if(from.isMovementDiagonal(to) && !isPathDiagonallyEmpty(from, to)) 
			isPathEmpty = false;
		if(from.isMovementSideways(to) && !isPathSidewayEmpty(from, to)) 
			isPathEmpty = false;
		logger.debug("Path Empty: " + isPathEmpty);
		return isPathEmpty;
	}
	
//	private boolean isDebugPoint(Tile from, Tile to) {
//		return from.hasKingPiece() && to.isEmpty() && to.getFile() == 'b' && (to.getRank() == 8 || to.getRank() == 1);
//	}

	private List<Piece> getAllPiecesOf(Color color) {
		return getTilesOf(color).stream().map(Tile::getPiece).collect(Collectors.toList());
	}
	
	public Tile getTileAt(char file, int rank) {
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
			logger.debug(String.format("Check at %s due to Opponent Piece %s at %s", kingTile.getPosition(), opponentTile.getPiece().getDescription(), opponentTile.getPosition()));
			return true;
		}
		return false;
	}

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
	
	private boolean isValidTileAt(int rank, int file) {
		return isValidTileAt((char)(file + 97), rank + 1);
	}
	
	private boolean isValidTileAt(char file, int rank) {
		return (rank <= 8 && rank >= 1) && isInRange(file, 'a', 'h');
	}

	private Tile getKingTileOf(Color color) {
		boolean isWhite = Color.WHITE == color;
		Predicate<Tile> pieceColorPredicate = isWhite ? isWhitePieceTile() : isBlackPieceTile();
		return getTiles(isNonEmptyTile().and(Tile::hasKingPiece).and(pieceColorPredicate)).stream().findFirst().orElse(null);
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
		return (tile != null && tile.getRank() <= 8 && tile.getRank() >= 1) &&
					isInRange(tile.getFile(), 'a', 'h');
	}
	
	private boolean isInRange(char ch, char lo, char hi) {
		return lo <= ch && ch <= hi;
	}

	private boolean isMoveByTurn(boolean isWhiteTurn, Tile tile) {
		return (isWhiteTurn && tile.getPiece().getColor() == Color.WHITE) || (!isWhiteTurn && tile.getPiece().getColor() == Color.BLACK);
	}
	
	private boolean isPathEmpty(Tile to, Tile from, int rankOffset, int fileOffset) {
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
			logger.debug("Move Invalid due to invalid source tile");
			return false;
		}
		if(!isValidTile(to)) {
			logger.debug("Move Invalid due to invalid destination tile");
			return false;
		}
		
		return true;
	}

	public void render() {
		
		for(int rank = 7; rank >= 0; rank--) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				System.out.print(String.format("%s%s%3$-13s ", tile.getPosition(), tile.getContent(), (tile.isEmpty() ? "" : tile.getPiece().getDescription())));
			}
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
		System.out.println("---------------------");
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
	
	private Collection<Tile> getTilesOf(Color color) {
		boolean isWhite = Color.white == color;
		Predicate<Tile> t = isWhite ? isWhitePieceTile() : isBlackPieceTile();
		return getTiles(isNonEmptyTile().and(t));
	}
	
	private Collection<Tile> getTiles(Predicate<Tile> predicate){
		return CollectionUtility.getList(tiles)
			.stream()
			.filter(predicate)
			.collect(Collectors.toList());
	}
	
	public Collection<Tile> getMovableTilesOf(Color color){
		Collection<Tile> movableTiles = new HashSet<>();
		boolean isWhite = Color.WHITE == color;
		Tile kingTile = null;
		if(isKingCheckAt(kingTile = getKingTileOf(isWhite ? Color.WHITE : Color.BLACK))) {
			logger.info("King Check of white?" + isWhite);
			List<Tile> opponentTilesResponsibleForCheckToKing = getOpponentTilesResponsibleForCheckToKing(kingTile);
			logger.info(String.format("Opponent Tiles(Size:%s) responsible for check", opponentTilesResponsibleForCheckToKing.size()));
			opponentTilesResponsibleForCheckToKing.stream().forEach(e -> logger.info(String.format("Opponent Tile position '%s' responsible for check", e.getPosition())));
			
			if(opponentTilesResponsibleForCheckToKing.size() == 1) {
				List<Tile> killTiles = getValidMovementTilesAt(opponentTilesResponsibleForCheckToKing.get(0));
				movableTiles.addAll(killTiles);
				
				List<Piece> pieces = getAllPiecesOf(kingTile.getPiece().getColor());
				Collection<Tile> emptyTiles = getAllEmptyTileBetween(kingTile, opponentTilesResponsibleForCheckToKing.get(0));
				Tile tile = kingTile;
				for(Tile emptyTile: emptyTiles) {
					movableTiles.addAll(pieces.stream()
									.filter(piece -> !piece.getTile().equals(tile))
									.filter(piece -> isValidPieceMove(piece.getTile(), emptyTile))
									.filter(piece -> !isKingCheckAfterPieceMovementOf(piece.getTile(), emptyTile))
									.map(piece -> piece.getTile())
									.collect(Collectors.toSet()));
				}
			}
		} else {
			Collection<Tile> tiles = (isWhite ? getTilesOf(Color.white) : getTilesOf(Color.black));
			Collection<Tile> otherTiles = (isWhite ? getNonWhiteTiles() : getNonBlackTiles());
			movableTiles.addAll(tiles.stream()
				.filter(tile -> !isKingCheckAfterPieceRemovalOf(tile))
				.filter(tile -> (otherTiles.stream().anyMatch(otherTile -> (isValidPieceMove(tile, otherTile) || isValidCastlingMove(tile, otherTile) || isValidEnPassantMove(tile, otherTile)))))
				.collect(Collectors.toSet()));
		}
		return movableTiles;
	}

	public Collection<Tile> getMovableTiles(boolean isWhiteTurn) {
		return (isWhiteTurn ? getMovableTilesOf(Color.white): getMovableTilesOf(Color.BLACK));
	}
	
	private boolean canCheckBeBlock(Tile kingTile, List<Tile> opponentTiles) {
		Map<Tile, Boolean> opponentTileToBlock = new HashMap<>();
		opponentTiles.forEach(opponentTile -> opponentTileToBlock.put(opponentTile, false));
		opponentTiles.stream()
			.filter(opponentTile -> (opponentTile.isMovementSideways(kingTile) || opponentTile.isMovementDiagonal(kingTile)))
			.forEach(opponentTile -> opponentTileToBlock.put(opponentTile, canPathBeBlock(opponentTile, kingTile)));
		return opponentTileToBlock.entrySet().stream().allMatch(e -> e.getValue());
	}
	
	private boolean canPathBeBlock(Tile from, Tile to) {
		boolean isKingCheckAfterMovement = false;
		List<Piece> pieces = getAllPiecesOf(to.getPiece().getColor());
		Collection<Tile> emptyTiles = getAllEmptyTileBetween(from, to);
		for(Tile emptyTile: emptyTiles) {
			isKingCheckAfterMovement = pieces.stream()
							.filter(piece -> !piece.getTile().equals(to))
							.filter(piece -> isValidPieceMove(piece.getTile(), emptyTile))
							.allMatch(piece -> isKingCheckAfterPieceMovementOf(piece.getTile(), emptyTile));
			if(!isKingCheckAfterMovement) return true;
		}
		return false;
	}
	
	private Collection<Tile> getAllEmptyTileBetween(Tile from, Tile to) {
		Collection<Tile> tiles = new HashSet<>();
		Tile emptyTile = from;
		while(emptyTile != null) {
			emptyTile = getNextEmptyTile(emptyTile, to);
			if(emptyTile != null)
				tiles.add(emptyTile);
		}
		return tiles;
	}
	
	private Tile getNextEmptyTile(Tile from, Tile to) {
		if(from.equals(to)) return null;
		int rank = from.getRank() - 1;
		int file = from.getFile() - 97;
		int finalRank = rank;
		int finalFile = file;
		if(!isAdjacent(from, to) && (from.isMovementDiagonal(to) || from.isMovementSideways(to))) {
			finalRank += new Integer(to.getRank()).compareTo(from.getRank());
			finalFile += new Integer(to.getFile()).compareTo((int) from.getFile());
			return tiles[finalRank][finalFile];
		}
		return null;
	}
	
	private boolean isKingCheckAfterPieceMovementOf(Tile tile, Tile to) {
		Piece capturedPiece = tile.movePieceTo(to);
		boolean isKingCheckAfterMove = isKingCheckAt(getKingTileOf(to.getPiece().getColor()));
		to.movePieceTo(tile, capturedPiece);
		
		return isKingCheckAfterMove;
	}
	
	private boolean isAdjacent(Tile from, Tile to) {
		boolean isDiagonalAdjacent = Math.abs(from.getRank() - to.getRank()) == 1 && Math.abs(from.getFile() - to.getFile()) == 1;
		boolean isSidewayAdjacent = Math.abs(from.getRank() - to.getRank()) == 1 || Math.abs(from.getFile() - to.getFile()) == 1;
		return isDiagonalAdjacent || isSidewayAdjacent;
	}
	
	public boolean isPawnPromotion(char file, int rank) {
		Tile tile = getTileAt(file, rank);
		if(isValidTile(tile) && !tile.isEmpty()) {
			return isPawnPromotion(Color.WHITE == tile.getPiece().getColor(), tile);
		} else {
			return false;
		}
	}
	
	public void setPawnForPromotion(char file, int rank, String userInput) {
		Tile tile = getTileAt(file, rank);
		int userSelection = Integer.parseInt(userInput);
		Piece promotedPiece = null;
		if(tile.isFirstRank())
			promotedPiece = Piece.getInstance(userSelection, Color.BLACK, tile);
		else
			promotedPiece = Piece.getInstance(userSelection, Color.WHITE, tile);
		tile.setPiece(promotedPiece);
	}
	
	private boolean isKingCheckAfterPieceRemovalOf(Tile tile) {
		if(tile.hasKingPiece()) return !isKingMovePossible(tile);
		Piece capturedPiece = tile.getPiece();
		tile.removePiece();
		boolean isKingCheckAfterMove = isKingCheckAt(getKingTileOf(capturedPiece.getColor()));
		if(isKingCheckAfterMove) {
			Tile kingTile = getKingTileOf(capturedPiece.getColor());
			List<Tile> opponentTilesResponsibleForCheckToKing = getOpponentTilesResponsibleForCheckToKing(kingTile);
			tile.setPiece(capturedPiece);
			if(opponentTilesResponsibleForCheckToKing.size() == 1) {
				boolean isValidMove = isValidPieceMove(tile, opponentTilesResponsibleForCheckToKing.get(0)) || isValidCastlingMove(tile, opponentTilesResponsibleForCheckToKing.get(0));
				if(isValidMove) isKingCheckAfterMove = false;
			}
		}
		else {
			tile.setPiece(capturedPiece);
		}
		
		return isKingCheckAfterMove;
	}
}
