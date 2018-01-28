package org.mitz.chess.model;

public class Game {

	private Board board;
	private boolean isWhiteTurn;
	private GameState status;
	
	public Game() {
		board = new Board();
		isWhiteTurn = true;
		setStatus(GameState.NEW);
	}

	public boolean move(int fromRank, char fromFile, int toRank, char toFile) {
		setStatus(GameState.IN_PROGRESS);
		if(board.move(isWhiteTurn, fromRank - 1, (int)fromFile - 97, toRank - 1, (int)toFile - 97)) {
			System.out.println("Checking for GAME OVER!!!!");
			if(board.isGameOver(isWhiteTurn)) {
				System.out.println("GAME OVER!!!!");
				setStatus(GameState.OVER);
			}
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

	private void setStatus(GameState status) {
		this.status = status;
	}
}