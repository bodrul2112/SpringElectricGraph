package model.animation.force;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.animation.drawable.DrawableObj;
import model.animation.drawable.RefreshingObj;

public class FGraph extends RefreshingObj implements DrawableObj 
{

	ArrayList<FVector> fvectors = new ArrayList<FVector>();
	int minimumSpringLength = 100; 
	double springConstant =  1;
	double coloumbConstant = 1;
	
	public FGraph() {
		
		/*
		Random r = new Random();
		
		for(int i=0; i<100; i++){
			
			FVector vector = new FVector((double) r.nextInt(800), (double) r.nextInt(800));
			vector.initAcceleration();
			fvectors.add(vector);
		}
		
		for(int i=0; i<100; i++){
			int n = r.nextInt(100);
			while(!fvectors.get(i).setParentVector(fvectors.get(n))){
				 n = r.nextInt(100);
			};
		}
		*/
		
		FVector centralVector = new FVector(400d, 300d);
		centralVector.initAcceleration();
		centralVector.moveable = false;
		fvectors.add(centralVector);
		
		Random r = new Random();
		for(int i=0; i<10; i++){
			
			double _x = r.nextInt(800);
			double _y =  r.nextInt(800);
			System.out.print(_x + " : " + _y);
			FVector vector = new FVector(_x, _y);
			vector.initAcceleration();
			vector.setParentVector(centralVector);
			fvectors.add(vector);
		}
		
		
	}
	
	@Override
	public void drawSelf(Graphics2D g2d) {
		
		// draw dots
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			g2d.drawLine(fVector.getXCoord(), fVector.getYCoord(), fVector.getXCoord(), fVector.getYCoord());
		}
		
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			if(fVector.parentVector != null){
				FVector parentVector = fVector.parentVector;
				g2d.drawLine(fVector.getXCoord(), fVector.getYCoord(), parentVector.getXCoord(), parentVector.getYCoord());
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
				node.x += half_a_t_squared.x;
				node.y += half_a_t_squared.y;
				node.x *= 0.5d;
				node.y *= 0.5d;
			}
		}
		
	}

}
