package org.mitz.chess.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.mitz.chess.model.Bishop;
import org.mitz.chess.model.Board;
import org.mitz.chess.model.Constants;
import org.mitz.chess.model.Game;
import org.mitz.chess.model.GameState;
import org.mitz.chess.model.King;
import org.mitz.chess.model.Knight;
import org.mitz.chess.model.Pawn;
import org.mitz.chess.model.Piece;
import org.mitz.chess.model.Queen;
import org.mitz.chess.model.Rook;
import org.mitz.chess.model.Tile;

public class ChessBoard extends JPanel implements MouseListener, ActionListener{
	private Game game;
	private int squareSize = 25;
	private int xOffset = 1;
	private int yOffset = 1;
	private int previousClickedRank;
	private char previousClickedFile;
	private int currentClickedRank;
	private char currentClickedFile;
	private Collection<Tile> possibleMovementTiles = new ArrayList<>();
	private JLabel gameStatusLabel;
	private JButton newGameBtn;
	private JButton resignGameBtn;
	
	public ChessBoard() {
		init();
	}

	public ChessBoard(JLabel gameStatusLabel, JButton newGameBtn, JButton resignGameBtn) {
		this.gameStatusLabel = gameStatusLabel;
		this.newGameBtn = newGameBtn;
		this.resignGameBtn = resignGameBtn;
		init();
	}

	private void init() {
		game = new Game();
		setup();
		possibleMovementTiles.clear();
	}
	
	public void reset() {
		init();
		repaint();
	}
	
	private void setup() {
		game.clear();
		Board board = game.getBoard();
		Tile t = board.getTileAt(1, 'e');
		t.setPiece(new King(Color.WHITE, t));
		
		t = board.getTileAt(1, 'd');
		t.setPiece(new Rook(Color.WHITE, t));
		
		t = board.getTileAt(6, 'a');
		t.setPiece(new Queen(Color.WHITE, t));
		
		t = board.getTileAt('a', 4);
		t.setPiece(new Bishop(Color.WHITE, t));
		
		t = board.getTileAt(8, 'e');
		t.setPiece(new King(Color.BLACK, t));
		
		t = board.getTileAt(7, 'e');
		t.setPiece(new Knight(Color.BLACK, t));
		
		t = board.getTileAt(7, 'f');
		t.setPiece(new Pawn(Color.BLACK, t));
		
		t = board.getTileAt(8, 'f');
		t.setPiece(new Rook(Color.BLACK, t));
		
		t = board.getTileAt('c', 6);
		t.setPiece(new Rook(Color.BLACK, t));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawLabel(g);
		drawBoard(g);
		updateGameStatus();
	}

//	private void updatePlayerTurn() {
//		if(game.getStatus() == GameState.IN_PROGRESS) {
//			if(game.isWhiteTurn()) {
//				player1Label.setText("White Player Turn");
//				player2Label.setText("Black Player");
//			} else {
//				player2Label.setText("Black Player Turn");
//				player1Label.setText("White Player");
//			}
//		} else if(game.getStatus() == GameState.OVER_DUE_TO_CHECKMATE) {
//			if(game.isWhiteTurn()) {
//				player1Label.setText("White Player");
//				player2Label.setText("Black Player");
//			} else {
//				player2Label.setText("Black Player");
//				player1Label.setText("White Player");
//			}
//			
//		}
//	}

	private void drawLabel(Graphics g) {
		g.setColor(Color.BLACK);
		for(int i = 0; i < xOffset; i++) {
			g.drawRect(i, i, getSize().width - (i * 2 + 1), getSize().height - (i * 2 + 1));
		}

		int c = 0;
		int r = 0;
		RectangleComponent rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.fillColor(Color.BLACK).render(g);

		r = 9;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.fillColor(Color.BLACK).render(g);

		c = 9;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.fillColor(Color.BLACK).render(g);
		
		r = 0;
		rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
		rectangleComponent.fillColor(Color.BLACK).render(g);

		for(c = 1; c <= 8; c++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.fillColor(Color.GRAY).render(g)
								.borderThickness(1).borderColor(Color.BLACK).renderBorder(g)
								.drawChar(g, (char)(96 + c), Color.BLACK);
		}
		
		r = 9;
		for(c = 1; c <= 8; c++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.fillColor(Color.GRAY).render(g)
								.borderThickness(1).borderColor(Color.BLACK).renderBorder(g)
								.drawChar(g, (char)(96 + c), Color.BLACK);
		}
		
		for(r = 1; r <= 8; r++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.fillColor(Color.GRAY).render(g)
								.borderThickness(1).borderColor(Color.BLACK).renderBorder(g)
								.drawChar(g, (char)((9 - r) + 48), Color.BLACK);
		}
		
		c = 0;
		for(r = 1; r <= 8; r++) {
			rectangleComponent = new RectangleComponent(c * squareSize + xOffset, r * squareSize + yOffset, squareSize, squareSize);
			rectangleComponent.fillColor(Color.GRAY).render(g)
								.borderThickness(1).borderColor(Color.BLACK).renderBorder(g)
								.drawChar(g, (char)((9 - r) + 48), Color.BLACK);
		}
	}

	private void drawBoard(Graphics g) {
		Board board = game.getBoard();
		Collection<Tile> movableTiles = new ArrayList<>();
		if(game.getStatus() == GameState.IN_PROGRESS) {
			
			boolean isMovementValid = false;
			if(isPresent(possibleMovementTiles, currentClickedRank, currentClickedFile)) {
				isMovementValid  = game.move(previousClickedRank, previousClickedFile, currentClickedRank, currentClickedFile);
			}
			if(isMovementValid) {
				possibleMovementTiles.clear();
			}
			movableTiles = game.getMovableTiles();
			if(isPresent(movableTiles, currentClickedRank, currentClickedFile)) {
				possibleMovementTiles = board.getPossibleMovementTilesFrom(board.getTileAt(currentClickedRank, currentClickedFile));
			}
		}
		RectangleComponent rectangleComponent = null;
		for(int rank = 1; rank <= 8; rank++) {
			for(char file = 'a'; file <= 'h'; file++) {
				rectangleComponent = new RectangleComponent(((int)file - 96)  * squareSize + xOffset, rank * squareSize + yOffset, squareSize, squareSize);
				rectangleComponent.fillColor((((int)file + rank) % 2 == 0) ? new Color(240, 220, 130) : new Color(138, 51, 36)).render(g);
				if(isPresent(movableTiles, 9 - rank, file)) {
					rectangleComponent.borderColor(Color.BLACK).borderThickness(2).renderBorder(g);
				}
				if(isPresent(movableTiles, currentClickedRank, currentClickedFile) && currentClickedFile == file && currentClickedRank == (9 - rank)) {
					rectangleComponent.borderColor(Color.CYAN).borderThickness(2).renderBorder(g);
				}
				if(isPresent(possibleMovementTiles, 9 - rank, file)) {
					rectangleComponent.borderColor(Color.white).borderThickness(2).renderBorder(g);
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

	private void updateGameStatus() {
		GameState gameStatus = game.getStatus();
		if(gameStatus == GameState.IN_PROGRESS) {
			gameStatusLabel.setText(String.format("Game in Progress: %s Player Turns ", (game.isWhiteTurn() ? "White" : "Black")));
		}
		if(gameStatus == GameState.NEW) {
			gameStatusLabel.setText(String.format("Game not Started"));
		}
		if(gameStatus == GameState.OVER_DUE_TO_CHECKMATE) {
			gameStatusLabel.setText(String.format("%s Player won", (game.isWhiteTurn() ? "Black" : "White")));
			newGameBtn.setEnabled(true);
			resignGameBtn.setEnabled(false);
			repaint();
		}
		if(gameStatus == GameState.OVER_DUE_TO_STALEMATE) {
			gameStatusLabel.setText(String.format("Match drawn due to %s Player not have legal moves to move", (!game.isWhiteTurn() ? "Black" : "White")));
		}
	}

	public boolean isWhiteTurn() {
		return game.isWhiteTurn();
	}

	public boolean isGameOver() {
		return (GameState.OVER_DUE_TO_CHECKMATE == game.getStatus() || GameState.OVER_DUE_TO_STALEMATE == game.getStatus());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isGameOver()) {
			previousClickedRank = currentClickedRank;
			previousClickedFile = currentClickedFile;
			currentClickedRank = 9 - (e.getY() / 25);
			currentClickedFile = (char)(e.getX() / 25 - 1 + 97);
			repaint();
		}
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand == Constants.NEWGAME) {
			reset();
			game.setStatus(GameState.IN_PROGRESS);
			repaint();
		}
	}
}
