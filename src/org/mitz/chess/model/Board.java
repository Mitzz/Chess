package org.mitz.chess.model;

public class Board {

	private Tile[][] tiles;
	
	public Board() {
		tiles = new Tile[8][8];
		for(int rank = 0; rank < 8; rank++)
			for(int file = 0; file < 8; file++)
				tiles[rank][file] = new Tile(rank, file);
	}

	public void move(int fromRank, int fromFile, int toRank, int toFile) {
		Tile from = tiles[fromRank][fromFile];
		Tile to = tiles[toRank][toFile];
		
		from.moveTo(to);
		
	}

	public void render() {
		String content = " ";
		for(int rank = 7; rank >= 0; rank--) {
			for(int file = 0; file < 8; file++) {
				Tile tile = tiles[rank][file];
				System.out.print(tile.getPosition() + " " + tile.getContent());
//				System.out.print(tile.getContent());
			}
			System.out.println();
		}
	}
}
