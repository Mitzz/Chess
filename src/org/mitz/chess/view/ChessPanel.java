package org.mitz.chess.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChessPanel extends JPanel {

	public ChessPanel() {
		setLayout(null);
		setBackground(new Color(0, 100, 0));
		
		ChessBoard board = new ChessBoard();
		board.setBounds(25, 30, 252, 252);
		add(board);
		
		JButton newGameBtn = new JButton("New Game");
		newGameBtn.setBounds(300, 70, 110, 24);
		add(newGameBtn);
		
		JButton resignBtn = new JButton("Resign");
		resignBtn.setBounds(300, 130, 110, 24);
		add(resignBtn);
		
		JLabel player2Label = new JLabel("Player 2");
		player2Label.setBounds(130, 8, 210, 24);
		player2Label.setForeground(Color.CYAN);
		add(player2Label);
		
		JLabel player1Label = new JLabel("Player 1");
		player1Label.setBounds(130, 280, 210, 24);
		player1Label.setForeground(Color.CYAN);
		add(player1Label);
		
		JLabel gameStatusLabel = new JLabel("Click 'New Game' to start new game");
		gameStatusLabel.setBounds(30, 315, 210, 24);
		gameStatusLabel.setForeground(Color.CYAN);
		add(gameStatusLabel);
		
		JLabel movementStatusLabel = new JLabel("Movement Status");
		movementStatusLabel.setBounds(30, 350, 210, 24);
		movementStatusLabel.setForeground(Color.CYAN);
		add(movementStatusLabel);
		
	}
}