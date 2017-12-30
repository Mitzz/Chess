package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		bishopMovementTest();
	}
	
	private static void randomMovementTest() {
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
		move(game, 'g', 1, 'f', 3);
		render(game);
		move(game, 'g', 8, 'h', 6);
		render(game);
		
	}

	
	private static void bishopMovementTest() {
		Game game = new Game();
		move(game, 2, 'd', 3, 'd');
		render(game);
		move(game, 7, 'd', 6, 'd');
		render(game);
		move(game, 2, 'e', 4, 'e');
		render(game);
		move(game, 'd', 6, 'd', 5);
		render(game);
		move(game, 'd', 3, 'd', 4);
		render(game);
		move(game, 'c', 7, 'c', 5);
		render(game);
		move(game, 'd', 4, 'c', 5);
		render(game);
		move(game, 'd', 5, 'e', 4);
		render(game);
		move(game, 'g', 1, 'f', 3);
		render(game);
		move(game, 'g', 8, 'h', 6);
		render(game);
		move(game, 'f', 3, 'd', 4);
		render(game);
		move(game, 'h', 6, 'f', 5);
		render(game);
		
		move(game, 'd', 4, 'e', 6);
		render(game);
		move(game, 'f', 5, 'h', 4);
		render(game);
		move(game, 'e', 6, 'f', 4);
		render(game);
		move(game, 'h', 4, 'f', 3);
		render(game);
		move(game, 'f', 4, 'h', 3);
		render(game);
		move(game, 'f', 3, 'd', 2);
		render(game);
		move(game, 'f', 1, 'd', 3);
		render(game);

//		move(game, 'f', 8, 'e', 7);//Movement Failed from (f,8) to (e,7) due to destination piece has same color piece as that of source piece
//		render(game);

//		move(game, 'd', 3, 'a', 7);//Move Invalid due wrong player played
//		render(game);

		move(game, 'e', 7, 'e', 5);
		render(game);
		
//		move(game, 'd', 3, 'a', 7);//Movement Failed from (d,3) to (a,7) due to source piece movement invalid
//		render(game);

		move(game, 'd', 3, 'a', 6);
		render(game);

		move(game, 'f', 8, 'c', 5);
		render(game);		

		move(game, 'a', 6, 'b', 7);
		render(game);		
		
		move(game, 'c', 8, 'b', 7);
		render(game);		
	}

	private static void knightMovementTest() {
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
		move(game, 'g', 1, 'f', 3);
		render(game);
		move(game, 'g', 8, 'h', 6);
		render(game);
		move(game, 'f', 3, 'd', 4);
		render(game);
		move(game, 'h', 6, 'f', 5);
		render(game);
		
		move(game, 'd', 4, 'e', 6);
		render(game);
		move(game, 'f', 5, 'h', 4);
		render(game);
		move(game, 'e', 6, 'f', 4);
		render(game);
		move(game, 'h', 4, 'f', 3);
		render(game);
		move(game, 'f', 4, 'h', 3);
		render(game);
		move(game, 'f', 3, 'd', 2);
		render(game);
		move(game, 'h', 3, 'f', 2);
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
