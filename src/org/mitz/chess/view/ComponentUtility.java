package org.mitz.chess.view;

import java.awt.Point;

public class ComponentUtility {

	public static Point getCenter(Point topLeft, int width, int height){
		return new Point(width/2 + (int)topLeft.getX(), height/2 + (int)topLeft.getY());
	}
	
	public static Point getTopLeft(Point center, int width, int height){
		return new Point((int)center.getX() - width/2, (int)center.getY() - height/2);
	}
}
