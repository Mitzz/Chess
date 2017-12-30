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
		this.piece = initializePiece();
		this.color = (this.rank + this.file) % 2 == 0? Color.BLACK : Color.WHITE;
	}

	private Piece initializePiece() {
		
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
	
	public Piece getPiece() {
		return piece;
	}

	public boolean isEmpty() {
		return piece == null;
	}

	public void moveTo(Tile to) {
		if(validateMove(to)) {
			
		}
	}

	private boolean validateMove(Tile to) {
		
		String error = "Movement Successful from " + this.getPosition() + " to " + to.getPosition();
		
		boolean valid = true;
		
		//Case I - Piece of Same Color
		if(!to.isEmpty() && this.getPiece().getColor() == to.getPiece().getColor()) {
			error = "Movement Failed from " + this.getPosition() + " to " + to.getPosition() + " due to destination piece has same color piece as that of source piece";
			valid = false;
		}
		//Case II - Piece Movement Valid
		this.getPiece().validateMove(to);
		System.out.println(error);
		return valid;
	}
	
	public char getFile() {
		return (char)(97 + this.file);
	}
	
	public int getRank() {
		return 1 + this.rank;
	}
	
	public String getPosition() {
		return "(" + getFile() + "," + getRank() + ")";
	}
}
