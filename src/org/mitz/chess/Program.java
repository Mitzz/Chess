package org.mitz.chess;

import org.mitz.chess.model.Game;

public class Program {

	public static void main(String[] args) {
		checkTest();
	}
	
	private static void checkTest() {
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
		move(game, 'd', 1, 'h', 5);
		render(game);
		move(game, 'd', 8, 'h', 4);
		render(game);
		move(game, 'h', 5, 'g', 5);
		render(game);
		move(game, 'h', 4, 'h', 5);
		render(game);
		move(game, 'g', 5, 'd', 2);
		render(game);
		move(game, 'h', 5, 'f', 5);
		render(game);
		move(game, 'd', 2, 'a', 5);
		render(game);
		move(game, 'f', 5, 'f', 7);
		render(game);
		move(game, 'e', 1, 'f', 2);
		render(game);
		move(game, 'e', 8, 'd', 7);
		render(game);
		move(game, 'f', 2, 'e', 3);
		render(game);
		move(game, 'd', 7, 'd', 8);
		render(game);
		move(game, 'e', 3, 'd', 2);
		render(game);
		move(game, 'd', 8, 'e', 7);
		render(game);
		move(game, 'd', 2, 'd', 1);
		render(game);

		//====================
		move(game, 'f', 7, 'f', 3);
		render(game);
		move(game, 'd', 1, 'd', 2);
		render(game);
		move(game, 'e', 4, 'e', 3);
		render(game);
		
		move(game, 'b', 1, 'c', 3);
		render(game);
		move(game, 'e', 7, 'd', 6);
		render(game);
		move(game, 'c', 3, 'e', 4);
		render(game);
		move(game, 'c', 5, 'b', 6);
		render(game);
		move(game, 'd', 2, 'd', 3);
		render(game);
		move(game, 'f', 3, 'd', 1);
		render(game);
		move(game, 'e', 5, 'e', 6);
		render(game);
		move(game, 'b', 8, 'c', 6);
		render(game);
		move(game, 'c', 1, 'd', 2);
		render(game);
		move(game, 'c', 6, 'e', 5);
		render(game);
		move(game, 'e', 4, 'c', 5);
		render(game);
		move(game, 'd', 6, 'd', 5);
		render(game);
		move(game, 'c', 5, 'd', 7);
		render(game);
		move(game, 'b', 6, 'c', 5);
		render(game);
		move(game, 'd', 7, 'f', 6);
		render(game);
		
		move(game, 'e', 5, 'g', 4);
		render(game);
		move(game, 'e', 6, 'e', 7);
		render(game);
		move(game, 'g', 4, 'f', 2);
		render(game);
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
	
	private static void kingMovementTest() {
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
		move(game, 'd', 1, 'h', 5);
		render(game);
		move(game, 'd', 8, 'h', 4);
		render(game);
		move(game, 'h', 5, 'g', 5);
		render(game);
		move(game, 'h', 4, 'h', 5);
		render(game);
		move(game, 'g', 5, 'd', 2);
		render(game);
		move(game, 'h', 5, 'f', 5);
		render(game);
		move(game, 'd', 2, 'a', 5);
		render(game);
		move(game, 'f', 5, 'f', 7);
		render(game);
		//==================================
		move(game, 'e', 1, 'f', 2);
		render(game);
		move(game, 'e', 8, 'd', 7);
		render(game);
		move(game, 'f', 2, 'e', 3);
		render(game);
		move(game, 'd', 7, 'd', 8);
		render(game);
		move(game, 'e', 3, 'd', 2);
		render(game);
		move(game, 'd', 8, 'e', 7);
		render(game);
		move(game, 'd', 2, 'd', 1);
		render(game);
//		move(game, 'e', 7, 'f', 7);//Movement Failed from (e,7) to (f,7) due to destination piece has same color piece as that of source piece
//		render(game);
//		move(game, 'e', 7, 'c', 7);//Movement Failed from (e,7) to (c,7) due to source piece movement invalid
//		render(game);
//		move(game, 'e', 7, 'g', 5);//Movement Failed from (e,7) to (g,5) due to source piece movement invalid
//		render(game);
		
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
		move(game, 'd', 1, 'h', 5);
		render(game);
		move(game, 'd', 8, 'h', 4);
		render(game);
		
		move(game, 'h', 5, 'g', 5);
		render(game);
		move(game, 'h', 4, 'h', 5);
		render(game);
		
//		move(game, 'g', 5, 'g', 2);//Movement Failed from (g,5) to (g,2) due to destination piece has same color piece as that of source piece
//		render(game);
//		move(game, 'g', 5, 'g', 1);//Sideway Path not empty
//		render(game);
		
		move(game, 'g', 5, 'd', 2);
		render(game);
//		move(game, 'h', 5, 'h', 3);//Movement Failed from (h,5) to (h,3) due to destination piece has same color piece as that of source piece
//		render(game);

		move(game, 'h', 5, 'f', 5);
		render(game);
		move(game, 'd', 2, 'a', 5);
		render(game);
		
//		move(game, 'f', 5, 'e', 7);//Movement Failed from (f,5) to (e,7) due to source piece movement invalid
//		render(game);

		move(game, 'f', 5, 'f', 7);
		render(game);

//		move(game, 'a', 5, 'h', 5);//Sideway Path not empty
//		render(game);

//		move(game, 'a', 5, 'd', 6);//Movement Failed from (a,5) to (d,6) due to source piece movement invalid
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
