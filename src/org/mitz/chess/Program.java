package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		Game game = new Game();
		move(game, 2, 'd', 3, 'd');
		render(game);
		move(game, 7, 'd', 6, 'd');
		render(game);
		move(game, 2, 'e', 4, 'e');
		render(game);
		move(game, 'd', 6, 'd', 5);
		render(game);
		move(game, 'e', 4, 'e', 6);
		render(game);
		move(game, 'd', 3, 'd', 4);
		render(game);
		move(game, 'd', 5, 'd', 4);
		render(game);
		move(game, 'c', 7, 'c', 5);
		render(game);
		move(game, 'd', 4, 'c', 5);
		render(game);
		move(game, 'd', 5, 'e', 4);
		render(game);
	}

	private static void render(Game game) {
		game.render();
	}

	private static void move(Game game, int fromRank, char fromFile, int toRank, char toFile) {
		game.move(fromRank, fromFile, toRank, toFile);
		sleep(100);
	}
	
	private static void move(Game game, char fromFile, int fromRank, char toFile, int toRank) {
		game.move(fromRank, fromFile, toRank, toFile);
		sleep(100);
	}

	private static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
