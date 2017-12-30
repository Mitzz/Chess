package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		System.out.println("Welcome to chess");
		Game game = new Game();
		
		int fromRank = 2;
		char fromFile = 'd';
		
		int toRank = 3;
		char toFile = 'e';
		
		game.move(fromRank, fromFile, toRank, toFile);
		sleep(1000);
		game.render();
		
	}

	private static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
