package org.mitz.chess.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitz.chess.model.Board;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.GameState;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Tile;

public class ChessBoard extends JPanel implements MouseListener{
	private Game game;
	private int squareSize = 25;
	private int xOffset = 1;
	private int yOffset = 1;
	private JLabel player1Label;
	private JLabel player2Label;
	private int previousClickedRank;
	private char previousClickedFile;
	private int currentClickedRank;
	private char currentClickedFile;
	private Collection<Tile> possibleMovementTiles = new ArrayList<>();
	
	public ChessBoard() {
		init();
	}

	public ChessBoard(JLabel player1Label, JLabel player2Label) {
		this.player1Label = player1Label;
		this.player2Label = player2Label;
		init();
	}

	private void init() {
		game = new Game();
		possibleMovementTiles.clear();
	}
	
	public void reset() {
		init();
		updatePlayerTurn();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawLabel(g);
		drawBoard(g);
	}

	private void updatePlayerTurn() {
		if(game.getStatus() == GameState.IN_PROGRESS || game.getStatus() == GameState.NEW) {
			if(game.isWhiteTurn()) {
				player1Label.setText("White Player Turn");
				player2Label.setText("Black Player");
			} else {
				player2Label.setText("Black Player Turn");
				player1Label.setText("White Player");
			}
		}
	}

	private void drawLabel(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < xOffset; i++) {
			g.drawRect(i, i, getSize().width - (i * 2 + 1), getSize().height - (i * 2 + 1));
		}

		int c = 0;
		int r = 0;
		RectangleComponent rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.interiorColor(Color.BLACK).draw(g);

		r = 9;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.interiorColor(Color.BLACK).draw(g);

		c = 9;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.interiorColor(Color.BLACK).draw(g);
		
		r = 0;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.interiorColor(Color.BLACK).draw(g);

		for(c = 1; c <= 8; c++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.interiorColor(Color.GRAY).draw(g)
								.borderThickness(1).borderColor(Color.BLACK).border(g)
								.drawChar(g, (char)(96 + c), Color.BLACK);
		}
		
		r = 9;
		for(c = 1; c <= 8; c++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.interiorColor(Color.GRAY).draw(g)
								.borderThickness(1).borderColor(Color.BLACK).border(g)
								.drawChar(g, (char)(96 + c), Color.BLACK);
		}
		
		for(r = 1; r <= 8; r++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.interiorColor(Color.GRAY).draw(g)
								.borderThickness(1).borderColor(Color.BLACK).border(g)
								.drawChar(g, (char)((9 - r) + 48), Color.BLACK);
		}
		
		c = 0;
		for(r = 1; r <= 8; r++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.interiorColor(Color.GRAY).draw(g)
								.borderThickness(1).borderColor(Color.BLACK).border(g)
								.drawChar(g, (char)((9 - r) + 48), Color.BLACK);
		}
	}

	private void drawBoard(Graphics g) {
		Board board = game.getBoard();
		boolean isMovementValid = false;
		if(isPresent(possibleMovementTiles, currentClickedRank, currentClickedFile)) {
			isMovementValid  = game.move(previousClickedRank, previousClickedFile, currentClickedRank, currentClickedFile);
		}
		if(isMovementValid) {
			possibleMovementTiles.clear();
		}
		Collection<Tile> movableTiles = game.getMovableTiles();
		if(isPresent(movableTiles, currentClickedRank, currentClickedFile)) {
			possibleMovementTiles = board.getPossibleMovementTilesFrom(board.getTileAt(currentClickedRank, currentClickedFile));
		}
		
		RectangleComponent rectangleComponent = null;
		for(int rank = 1; rank <= 8; rank++) {
			for(char file = 'a'; file <= 'h'; file++) {
				rectangleComponent = new RectangleComponent(((int)file - 96)  * squareSize + xOffset, rank * squareSize + yOffset, squareSize, squareSize);
				rectangleComponent.interiorColor((((int)file + rank) % 2 == 0) ? new Color(240, 220, 130) : new Color(138, 51, 36)).draw(g);
				if(isPresent(movableTiles, 9 - rank, file)) {
					rectangleComponent.borderColor(Color.BLACK).borderThickness(2).border(g);
				}
				if(isPresent(movableTiles, currentClickedRank, currentClickedFile) && currentClickedFile == file && currentClickedRank == (9 - rank)) {
					rectangleComponent.borderColor(Color.CYAN).borderThickness(2).border(g);
				}
				if(isPresent(possibleMovementTiles, 9 - rank, file)) {
					rectangleComponent.borderColor(Color.white).borderThickness(2).border(g);
				}
				Tile tile = board.getTileAt(9 - rank, file);
				if(!tile.isEmpty()) {
					Piece piece = tile.getPiece();
					rectangleComponent.drawUnicode(g, piece.getUnicodeCharacter(), piece.getColor());
				}
			}
		}
	}
	
	
	
	private boolean isPresent(Collection<Tile> tiles, int rank, char file) {
		return  (!tiles.isEmpty() && tiles.stream().anyMatch(tile -> isEqual(tile, rank, file)));
	}
	
	private boolean isEqual(Tile tile, int rank, char file) {
		return tile.getFile() == file && tile.getRank() == rank;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		previousClickedRank = currentClickedRank;
		previousClickedFile = currentClickedFile;
		currentClickedRank = 9 - (e.getY() / 25);
		currentClickedFile = (char)(e.getX() / 25 - 1 + 97);
		
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("Mouse Entered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("Mouse Exited");
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("Mouse Pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("Mouse Released");
	}
	
	public boolean isWhiteTurn() {
		return game.isWhiteTurn();
	}
}
