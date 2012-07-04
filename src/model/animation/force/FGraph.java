package model.animation.force;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import model.animation.drawable.DrawableObj;
import model.animation.drawable.RefreshingObj;
import model.animation.force.demopatterns.MultiLayerOrbitPattern;

public class FGraph extends RefreshingObj implements DrawableObj, MouseWheelListener
{

	List<FVector> fvectors = new ArrayList<FVector>();
	int minimumSpringLength = 1; 
	double springConstant =  1;
	double coloumbConstant = 1;
	
	double SCALE_FACTOR = 0.3d; 
	int X_TRANSLATION = 250;
	int Y_TRANSLATION = 200;
	
	public FGraph() {
		
		this.fvectors = (new MultiLayerOrbitPattern(2,5)).getVectorPoints();
	}
	
	@Override
	public void drawSelf(Graphics2D g2d) {
		
		// draw dots
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			g2d.drawLine(fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
		}
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			if(fVector.parentVector != null){
				FVector parentVector = fVector.parentVector;
				g2d.drawLine(fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), parentVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), parentVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
			}
		}
		
	}

	@Override
	public void step() {
		
		// Hooke's Law
		// F = -k x
		for(int i=0; i<fvectors.size(); i++){
			
			FVector childVector = fvectors.get(i);
			
			if(childVector.parentVector != null){
				FVector parentVector = childVector.parentVector;
				FVector distance = parentVector.subtract(childVector);
				FVector restingDistance = distance.normalize().multiply(minimumSpringLength);
				FVector force = distance.subtract(restingDistance).multiply(-springConstant);
				
				parentVector.nextAcceleration = parentVector.nextAcceleration.add(force);
				childVector.nextAcceleration = childVector.nextAcceleration.subtract(force);
			}
		}
		
		// Columb's Law
		// F = Q1 Q2 / d^2
		for(int i=0; i<fvectors.size(); i++){
			
			FVector a_node = fvectors.get(i);
			for(int j=0; j<fvectors.size(); j++){
				if(!a_node.equals(fvectors.get(j))){
					FVector b_node = fvectors.get(j);
					
					FVector distance = a_node.subtract(b_node);
					double modulus = distance.modulus();
					FVector direction = distance.multiply((double) (1/modulus));
					double modulus_squared = Math.pow(modulus, 2);
					double k_qq_dij = coloumbConstant * ((a_node.charge * b_node.charge)/modulus_squared);
					
					FVector force = direction.multiply(k_qq_dij);
					
					a_node.nextAcceleration = a_node.nextAcceleration.add(force);
					b_node.nextAcceleration = b_node.nextAcceleration.subtract(force);
				}
			}
		}
		
		for(int i=0; i<fvectors.size(); i++){
			
			FVector node = fvectors.get(i);
			if(node.moveable){
				FVector half_a_t_squared = node.nextAcceleration.multiply(0.5d);
				
				if(Math.abs(half_a_t_squared.x)>500){
					//half_a_t_squared.x *= 0.5;
				}
				
				if(Math.abs(half_a_t_squared.y)>500){
					//half_a_t_squared.x *= 0.5;
				}
				
				node.x += half_a_t_squared.x;
				node.y += half_a_t_squared.y;
				node.x *= 0.3d;
				node.y *= 0.3d;
			}
		}
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int rotation = e.getWheelRotation();
		if(rotation < 0){
			// Mouse Up
			
		}else{
			
			
		}
	}

}
