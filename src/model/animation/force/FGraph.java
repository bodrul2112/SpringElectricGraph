package model.animation.force;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import model.animation.drawable.DrawableObj;
import model.animation.drawable.RefreshingObj;
import model.animation.force.demopatterns.LinePattern;
import model.animation.force.demopatterns.MultiLayerOrbitPattern;
import model.animation.force.demopatterns.NetPattern;

public class FGraph extends RefreshingObj implements DrawableObj, MouseWheelListener, MouseListener, MouseMotionListener
{

	List<FVector> fvectors = new ArrayList<FVector>();
	double minimumSpringLength = 1d; 
	double springConstant =  1d;
	double coloumbConstant = 1d;
	
	double SCALE_FACTOR = 0.3d; 
	int X_TRANSLATION = 0;
	int Y_TRANSLATION = 0;
	boolean DEBUG = true;
	
	public FGraph() {
		
		/*
		this.fvectors = (new MultiLayerOrbitPattern(3,5)).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 0.1d; 
		X_TRANSLATION = 400;
		Y_TRANSLATION = 300;
		*/
		
		/*
		this.fvectors = (new LinePattern()).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 0;
		Y_TRANSLATION = 300;
		*/
		
		this.fvectors = (new NetPattern(10,10)).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 0;
		Y_TRANSLATION = 300;
	
	}
	
	@Override
	public void drawSelf(Graphics2D g2d) {
		
		// draw dots
		g2d.setStroke(new BasicStroke(3));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			if(fVector.isMoveable()){
				g2d.setColor(Color.BLACK);
			}else{
				g2d.setColor(Color.RED);
			}
			g2d.drawLine(fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
		}
		
		// draw lines
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<fvectors.size(); i++){
			FVector fVector = fvectors.get(i);
			if(fVector.parentVector != null){
				FVector parentVector = fVector.parentVector;
				g2d.drawLine(fVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), fVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), parentVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), parentVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
			}
		}
		
		// draw click radius
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<fvectors.size(); i++){
			
			if(!fvectors.get(i).isMoveable()){
				Rectangle rect = fvectors.get(i).getClickArea(SCALE_FACTOR, X_TRANSLATION, Y_TRANSLATION);
				g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		
		//if(DEBUG){
		Font font = new Font("Serif", Font.PLAIN, 14);
	    g2d.setFont(font);
			g2d.setColor(Color.BLACK);
			g2d.drawString("SCALE FACTOR : " + SCALE_FACTOR, 10, 20);
		//}
		
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
			if(node.allow_move_x || node.allow_move_y){
				FVector half_a_t_squared = node.nextAcceleration.multiply(0.5d);
				
				if(Math.abs(half_a_t_squared.x)>500){
					//half_a_t_squared.x *= 0.5;
				}
				
				if(Math.abs(half_a_t_squared.y)>500){
					//half_a_t_squared.x *= 0.5;
				}
				
				node.moveXY_by(half_a_t_squared.x, half_a_t_squared.y);
				node.x *= 0.3d;
				node.y *= 0.3d;
			}
		}
		
	}

	/* Mouse Wheel Listener */
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		int rotation = e.getWheelRotation();
		if(rotation < 0){
			// Mouse Up
			SCALE_FACTOR += 0.1d;
		}else{
			
			if(SCALE_FACTOR > 0.1d){
				SCALE_FACTOR -= 0.1d; 
			}
		}
	}

	/* Mouse Motion Listener */ 
	
	boolean pressed = false;
	int startx = 0;
	int starty = 0;
	int X_TRANS_ORIG = 0;
	int Y_TRANS_ORIG = 0;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	FVector selectedVector = null; 
	int selectedVector_ORIG_X;
	int selectedVector_ORIG_Y;
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	
		startx = arg0.getX();
		starty = arg0.getY();
		
		boolean selected = false;
		for(int i=0; i<fvectors.size(); i++){
			if(fvectors.get(i).getClickArea(SCALE_FACTOR, X_TRANSLATION, Y_TRANSLATION).contains(arg0.getX(), arg0.getY())){
				
				selected = true;
				selectedVector = fvectors.get(i);
				selectedVector_ORIG_X = selectedVector.getXCoord(SCALE_FACTOR, X_TRANSLATION);
				selectedVector_ORIG_Y = selectedVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION);
				selectedVector.disableAutoMove();
				break;
			}
		}
		
		if(selected){
			if(arg0.getButton()==2 || arg0.getButton()==3){
				selectedVector.enableAutoMove();
				selectedVector = null;
			}
		}
		else if(!selected){
			if(arg0.getButton()==2 || arg0.getButton()==3){
				// release all forced nodes that are not stationary nodes
				for(int i=0; i<fvectors.size(); i++){
					fvectors.get(i).enableAutoMove();
				}
			}
			pressed = true;
			X_TRANS_ORIG = X_TRANSLATION;
			Y_TRANS_ORIG = Y_TRANSLATION;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

		pressed = false; 
		selectedVector = null;
	}

	/* Mouse Motion Listener */
	
	@Override
	public void mouseDragged(MouseEvent arg0) {

		
		if(selectedVector != null){
			int x_diff = startx - arg0.getX();
			int y_diff = starty - arg0.getY();
			//selectedVector.forceMoveXY(selectedVector_ORIG_X-x_diff, selectedVector_ORIG_Y-y_diff);
			
			int _x = arg0.getX()-X_TRANSLATION;
			int _y = arg0.getY()-Y_TRANSLATION;
			_x = (int) ((_x/SCALE_FACTOR));
			_y = (int) ((_y/SCALE_FACTOR));
			
			selectedVector.forceMoveXY(_x, _y);
		}
		else if(pressed){
			int x_diff = startx - arg0.getX();
			int y_diff = starty - arg0.getY();
			X_TRANSLATION = X_TRANS_ORIG-x_diff;
			Y_TRANSLATION = Y_TRANS_ORIG-y_diff;
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
