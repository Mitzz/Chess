package org.mitz.chess.model;

import java.awt.Color;

public class Tile {

	private int rank;
	private int file;
	private Color color;
	private Piece piece;
	
	public Tile(int rank, int file) {
		this.rank = rank;
		this.file = file;
		this.piece = getPiece();
		this.color = (this.rank + this.file) % 2 == 0? Color.BLACK : Color.WHITE;
	}

	private Piece getPiece() {
		
		int rank = this.rank + 1;
		int file = this.file + 1;
		Color color = null;
		if(rank == 1 || rank == 2)
			color = Color.WHITE;
		if(rank == 8 || rank == 7)
			color = Color.BLACK;
		
		if(rank == 1 && (file == 1 && file == 8)) return new Rook(color, this);
		if(rank == 1 && (file == 2 && file == 7)) return new Knight(color, this);
		if(rank == 1 && (file == 3 && file == 6)) return new Bishop(color, this);
		if(rank == 1 && file == 4) return new Queen(color, this);
		if(rank == 1 && file == 5) return new King(color, this);
		if(rank == 2 && (1 <= file || file <= 8)) return new Pawn(color, this);
		
		if(rank == 8 && (file == 1 && file == 8)) return new Rook(color, this);
		if(rank == 8 && (file == 2 && file == 7)) return new Knight(color, this);
		if(rank == 8 && (file == 3 && file == 6)) return new Bishop(color, this);
		if(rank == 8 && file == 4) return new Queen(color, this);
		if(rank == 8 && file == 5) return new King(color, this);
		if(rank == 7 && (1 <= file || file <= 8)) return new Pawn(color, this);
		
		return null;
	}
}
