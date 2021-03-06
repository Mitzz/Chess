package org.mitz.chess.model;

import java.util.Collection;

import org.apache.log4j.Logger;

public class Game {

	private static final Logger logger = Logger.getLogger(Game.class);
	private Board board;
	private boolean isWhiteTurn;
	private GameState status;
	
	public Game() {
		board = new Board();
		isWhiteTurn = true;
		setStatus(GameState.NEW);
	}

	public boolean move(int fromRank, char fromFile, int toRank, char toFile) {
		boolean isMoveValid = board.move(isWhiteTurn, fromRank, fromFile, toRank , toFile );
		logger.info("Movement Status: "	 + isMoveValid);
		if(isMoveValid) {
			GameState gameState = board.getGameStatus(isWhiteTurn);
			setStatus(gameState);
			logger.info("Game State: " + gameState);
			isWhiteTurn = !isWhiteTurn;
			return true;
		}
		return false;
	}

	public void render() {
		board.render();
	}
	
	public Board getBoard() {
		return board;
	}

	public GameState getStatus() {
		return status;
	}

	public void setStatus(GameState status) {
		this.status = status;
	}
	
	public void clear() {
		if(GameState.NEW == getStatus()) {
			for(int rank = 1; rank <= 8; rank++) {
				for(char file = 'a'; file <= 'h'; file++) {
					board.getTileAt(file, rank).removePiece();
				}
			}
		}
	}

	public boolean isWhiteTurn() {
		return isWhiteTurn;
	}

	public Collection<Tile> getMovableTiles() {
		return board.getMovableTiles(isWhiteTurn);
	}
	
}