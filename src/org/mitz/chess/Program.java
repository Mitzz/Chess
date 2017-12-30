package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		queenMovementTest();
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

	private static void queenMovementTest() {
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
		move(game, 'e', 7, 'e', 5);
		render(game);
		move(game, 'd', 3, 'a', 6);
		render(game);
		move(game, 'f', 8, 'c', 5);
		render(game);		
		move(game, 'a', 6, 'b', 7);
		render(game);		
		move(game, 'c', 8, 'b', 7);
		render(game);
		move(game, 'h', 1, 'f', 1);
		render(game);		
		move(game, 'f', 7, 'f', 5);
		render(game);		
		move(game, 'f', 2, 'f', 4);
		render(game);		
		move(game, 'h', 8, 'f', 8);
		render(game);		
		move(game, 'f', 1, 'f', 2);
		render(game);		
		move(game, 'f', 8, 'f', 7);
		render(game);		
		move(game, 'f', 2, 'f', 3);
		render(game);		
		move(game, 'f', 7, 'f', 6);
		render(game);		
		move(game, 'f', 4, 'e', 5);
		render(game);		
		move(game, 'f', 6, 'h', 6);
		render(game);
		move(game, 'f', 3, 'f', 5);
		render(game);
		move(game, 'h', 6, 'h', 3);
		render(game);
		
		//=================
		move(game, 'e', 1, 'e', 2);
		render(game);
		move(game, 'e', 8, 'e', 7);
		render(game);
		
		move(game, 'e', 2, 'h', 5);
		render(game);
		move(game, 'e', 7, 'a', 2);
		render(game);
		
//		move(game, 'h', 5, 'e', 5);//Sideway Path not empty
//		render(game);

//		move(game, 'h', 5, 'f', 5);//Movement Failed from (h,5) to (f,5) due to destination piece has same color piece as that of source piece
//		render(game);
		
		move(game, 'h', 5, 'h', 3);
		render(game);
		move(game, 'a', 2, 'b', 1);
		render(game);

//		move(game, 'h', 3, 'f', 5);/Movement Failed from (h,3) to (f,5) due to destination piece has same color piece as that of source piece
//		render(game);

		
	}
	
	private static void rookMovementTest() {
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
		move(game, 'e', 7, 'e', 5);
		render(game);
		
		move(game, 'd', 3, 'a', 6);
		render(game);

		move(game, 'f', 8, 'c', 5);
		render(game);		

		move(game, 'a', 6, 'b', 7);
		render(game);		
		
		move(game, 'c', 8, 'b', 7);
		render(game);
		
		move(game, 'h', 1, 'f', 1);
		render(game);		
		
		move(game, 'f', 7, 'f', 5);
		render(game);		
		move(game, 'f', 2, 'f', 4);
		render(game);		
		move(game, 'h', 8, 'f', 8);
		render(game);		
		
		move(game, 'f', 1, 'f', 2);
		render(game);		
		move(game, 'f', 8, 'f', 7);
		render(game);		
		move(game, 'f', 2, 'f', 3);
		render(game);		
		move(game, 'f', 7, 'f', 6);
		render(game);		

//		move(game, 'f', 3, 'h', 3);//Movement Failed from (f,3) to (h,3) due to destination piece has same color piece as that of source piece
//		render(game);		

//		move(game, 'f', 3, 'g', 2);//Movement Failed from (f,3) to (g,2) due to destination piece has same color piece as that of source piece
//		render(game);		

//		move(game, 'f', 3, 'g', 4);//Movement Failed from (f,3) to (g,4) due to source piece movement invalid
//		render(game);		

		move(game, 'f', 4, 'e', 5);//Movement Failed from (f,3) to (g,4) due to source piece movement invalid
		render(game);		

		move(game, 'f', 6, 'h', 6);
		render(game);
		
		move(game, 'f', 3, 'f', 5);
		render(game);
		
		move(game, 'h', 6, 'h', 3);
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
