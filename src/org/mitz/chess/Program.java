package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		System.out.println("Welcome to chess");
		Game game = new Game();
		
		int fromRank = 3;
		char fromFile = 'd';
		
		int toRank = 3;
		char toFile = 'e';
		
		game.move(fromRank, fromFile, toRank, toFile);
	}
}
