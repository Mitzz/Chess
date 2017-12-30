package org.mitz.chess.model;

public class Game {

	private Board board;
	private boolean isWhiteTurn;
	
	public Game() {
		board = new Board();
		isWhiteTurn = true;
	}

	public void move(int fromRank, char fromFile, int toRank, char toFile) {
		if(board.move(isWhiteTurn, fromRank - 1, (int)fromFile - 97, toRank - 1, (int)toFile - 97)) {
			isWhiteTurn = !isWhiteTurn;
		}
		
	}

	public void render() {
		board.render();
	}
}