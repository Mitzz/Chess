package org.mitz.chess.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class RectangleComponent{
	private Point center;
	private int width;
	private int height;
	private Color fillColor;
	private Color borderColor;
	private int borderThickness;
	
	public RectangleComponent(Point center, int width, int height) {
		this.center = center;
		this.width = width;
		this.height = height;
	}
	
	public RectangleComponent(int left, int top, int width, int height) {
		this.center = ComponentUtility.getCenter(new Point(left, top), width, height);
		this.width = width;
		this.height = height;
	}
	
	public RectangleComponent fillColor(Color interiorColor){
		this.fillColor = interiorColor;
		return this;
	}
	
	public RectangleComponent render(Graphics g){
		g.setColor(fillColor);
		g.fillRect((int)this.center.getX() - (width/2), (int)this.center.getY() - (height/2), width, height);
		return this;
	}
	
	public RectangleComponent borderColor(Color borderColor){
		this.borderColor = borderColor;
		return this;
	}
	
	public RectangleComponent borderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		return this;
	}

	public RectangleComponent renderThinBorder(Graphics g){
		g.setColor(borderColor);
		g.drawRect((int)this.center.getX() - (width/2), (int)this.center.getY() - (height/2), width, height);
		return this;
	}
	
	public RectangleComponent renderBorder(Graphics g){
		g.setColor(borderColor);
		Point topLeft = ComponentUtility.getTopLeft(center, width, height);
		for(int i = 0; i < borderThickness; i++){
			g.drawRect(topLeft.x + i, topLeft.y + i, width - (2 * i + 1), height - (2 * i + 1));
		}
		return this;
	}
	
	public RectangleComponent incrementCenter(Point p){
		center.x += p.x;
		center.y += p.y;
		return this;
	}
	
	public boolean isCoordinateInside(Point p){
		return (center.getX() - (width/2) <= p.x && center.getX() + (width/2) > p.x) &&
				(center.getY() - (height/2) <= p.y && center.getY() + (height/2) > p.y);
	}

	public RectangleComponent drawInnerCircle(Graphics g, int circleSize, Color color){
		g.setColor(color);
		Point topLeft = ComponentUtility.getTopLeft(center, width, height);
		int x = (width - circleSize) / 2;
		int y = (height - circleSize) / 2;
		g.fillOval(topLeft.x + x, topLeft.y + y, circleSize, circleSize);
		return this;
	}
	
	public RectangleComponent drawChar(Graphics g, char ch, Color color) {
		return drawUnicode(g, String.valueOf(ch), color);
	}
	
	public RectangleComponent drawUnicode(Graphics g, String ch, Color color) {
		g.setColor(color);
//		Point topLeft = ComponentUtility.getTopLeft(center, width, height);
		g.drawString(ch, center.x - 3, center.y + 3);
		return this;
	}
}
