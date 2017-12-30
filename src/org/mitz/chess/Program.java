package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		System.out.println("Welcome to chess");
		Game game = new Game();
		
		int fromRank = 2;
		char fromFile = 'd';
		
		int toRank = 4;
		char toFile = 'd';
		
		game.move(fromRank, fromFile, toRank, toFile);
	}
}
