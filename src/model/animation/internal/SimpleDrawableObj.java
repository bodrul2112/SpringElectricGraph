package model.animation.internal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import model.animation.drawable.DrawableObj;
import model.animation.drawable.RefreshingObj;

public class SimpleDrawableObj extends RefreshingObj implements DrawableObj 
{
	
	int sliderX;
	Color sliderColer = Color.BLUE;
	BasicStroke stroke = new BasicStroke(3);
	
	int height;
	int width;
	int direction;
	Color color;
	
	public SimpleDrawableObj(int width, int height, int direction, Color color) {
		this.width = width;
		this.height = height;
		this.direction = direction;
		this.color = color;
	}

	@Override
	public void drawSelf(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.setStroke(stroke);
		g2d.drawLine(sliderX, 0, sliderX, height);
	}

	@Override
	public void step() {

		sliderX+=direction; 
		if(sliderX > width){
			sliderX=0;
		}
		
		if(sliderX < 0){
			sliderX=width;
		}
	}

}
