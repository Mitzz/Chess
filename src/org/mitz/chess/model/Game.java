package org.mitz.chess.model;

public class Game {

	private Board board;
	
	public Game() {
		board = new Board();
	}

	public void move(int fromRank, char fromFile, int toRank, char toFile) {
		board.move(fromRank - 1, (int)fromFile - 97, toRank - 1, (int)toFile - 97);
	}

	public void render() {
		board.render();
	}
}