package org.mitz.chess.view;

import javax.swing.JFrame;

public class ChessView {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Chess");
		frame.setLocation(100, 100);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ChessPanel());
		frame.setVisible(true);
	}
}