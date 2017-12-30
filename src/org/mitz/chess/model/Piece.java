package org.mitz.chess.model;

import java.awt.Color;

public class Piece {
	
	private Tile tile;
	private Color color;

	public Piece(Color color, Tile tile) {
		this.color = color;
		this.tile = tile;
	}

	public void move(Tile from, Tile to) {

	}

	public void validateMove(Tile to) {

	}

	public void remove() {
	}

	
}

