package model.animation.force;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.animation.drawable.DrawableObj;
import model.animation.drawable.RefreshingObj;

public class FGraph extends RefreshingObj implements DrawableObj 
{

	ArrayList<FVector> fvectors = new ArrayList<FVector>();
	
	public FGraph() {
		
		Random r = new Random();
		
		for(int i=0; i<100; i++){
			fvectors.add(new FVector((double) r.nextInt(800), (double) r.nextInt(800)));
		}
		
		for(int i=0; i<100; i++){
			int n = r.nextInt(100);
			if(n<5){
				fvectors.get(r.nextInt(fvectors.size()-1)).setParentVector(fvectors.get(r.nextInt(fvectors.size()-1)));
			}
			fvectors.add(new FVector((double) r.nextInt(800), (double) r.nextInt(800)));
		}
		
	}
	
	@Override
	public void drawSelf(Graphics2D g2d) {
		
		// draw dots
		g2d.setColor(Color.BLACK);
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			g2d.drawLine(fVector.getXCoord(), fVector.getYCoord(), fVector.getXCoord(), fVector.getYCoord());
		}
		
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	}

}
