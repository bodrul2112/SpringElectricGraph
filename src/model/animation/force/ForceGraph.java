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
import model.animation.force.demopatterns.OrbitPattern;

public class ForceGraph extends RefreshingObj implements DrawableObj, MouseWheelListener, MouseListener, MouseMotionListener
{

	List<ForceVector> ForceVectors = new ArrayList<ForceVector>();
	double minimumSpringLength = 1d; 
	double springConstant =  1d;
	double coloumbConstant = 1d;
	
	double SCALE_FACTOR = 0.3d; 
	int X_TRANSLATION = 0;
	int Y_TRANSLATION = 0;
	boolean DEBUG = true;
	
	public ForceGraph() {
		
		
		this.ForceVectors = (new OrbitPattern(25)).getVectorPoints();
		minimumSpringLength = 3d; 
		springConstant =  0.5d;
		coloumbConstant = 0.5d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 400;
		Y_TRANSLATION = 300;
		
		/*
		this.ForceVectors = (new MultiLayerOrbitPattern(2,5)).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 400;
		Y_TRANSLATION = 300;
		
		*/
		
		/*
		this.ForceVectors = (new LinePattern()).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 0;
		Y_TRANSLATION = 300;
		
		this.ForceVectors = (new NetPattern(10,10)).getVectorPoints();
		minimumSpringLength = 1d; 
		springConstant =  1d;
		coloumbConstant = 1d;
		
		SCALE_FACTOR = 1d; 
		X_TRANSLATION = 0;
		Y_TRANSLATION = 300;
		*/
	}
	
	@Override
	public void drawSelf(Graphics2D g2d) {
		
		// draw dots
		g2d.setStroke(new BasicStroke(3));
		for(int i=0; i<ForceVectors.size(); i++){
			ForceVector ForceVector = ForceVectors.get(i);
			if(ForceVector.isMoveable()){
				g2d.setColor(Color.BLACK);
			}else{
				g2d.setColor(Color.RED);
			}
			g2d.drawLine(ForceVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), ForceVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), ForceVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), ForceVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
		}
		
		// draw lines
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<ForceVectors.size(); i++){
			ForceVector ForceVector = ForceVectors.get(i);
			if(ForceVector.parentVector != null){
				ForceVector parentVector = ForceVector.parentVector;
				g2d.drawLine(ForceVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), ForceVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION), parentVector.getXCoord(SCALE_FACTOR, X_TRANSLATION), parentVector.getYCoord(SCALE_FACTOR, Y_TRANSLATION));
			}
		}
		
		// draw click radius
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.setStroke(new BasicStroke(1));
		for(int i=0; i<ForceVectors.size(); i++){
			
			if(!ForceVectors.get(i).isMoveable()){
				Rectangle rect = ForceVectors.get(i).getClickArea(SCALE_FACTOR, X_TRANSLATION, Y_TRANSLATION);
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
		for(int i=0; i<ForceVectors.size(); i++){
			
			ForceVector childVector = ForceVectors.get(i);
			
			if(childVector.parentVector != null){
				ForceVector parentVector = childVector.parentVector;
				ForceVector distance = parentVector.subtract(childVector);
				ForceVector restingDistance = distance.normalize().multiply(minimumSpringLength);
				ForceVector force = distance.subtract(restingDistance).multiply(-springConstant);
				
				parentVector.nextAcceleration = parentVector.nextAcceleration.add(force);
				childVector.nextAcceleration = childVector.nextAcceleration.subtract(force);
			}
		}
		
		// Columb's Law
		// F = Q1 Q2 / d^2
		for(int i=0; i<ForceVectors.size(); i++){
			
			ForceVector a_node = ForceVectors.get(i);
			for(int j=0; j<ForceVectors.size(); j++){
				if(!a_node.equals(ForceVectors.get(j))){
					ForceVector b_node = ForceVectors.get(j);
					
					ForceVector distance = a_node.subtract(b_node);
					double modulus = distance.modulus();
					ForceVector direction = distance.multiply((double) (1/modulus));
					double modulus_squared = Math.pow(modulus, 2);
					double k_qq_dij = coloumbConstant * ((a_node.charge * b_node.charge)/modulus_squared);
					
					ForceVector force = direction.multiply(k_qq_dij);
					
					a_node.nextAcceleration = a_node.nextAcceleration.add(force);
					b_node.nextAcceleration = b_node.nextAcceleration.subtract(force);
					
				}
			}
		}
		
		for(int i=0; i<ForceVectors.size(); i++)
		{
			ForceVector node = ForceVectors.get(i);
			if(node.allow_move_x || node.allow_move_y){
				ForceVector half_a_t_squared = node.nextAcceleration.multiply(0.5d);
				
				System.out.println("half at sq " + half_a_t_squared.x +","+ half_a_t_squared.y);
				
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

	ForceVector selectedVector = null; 
	int selectedVector_ORIG_X;
	int selectedVector_ORIG_Y;
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	
		startx = arg0.getX();
		starty = arg0.getY();
		
		boolean selected = false;
		for(int i=0; i<ForceVectors.size(); i++){
			if(ForceVectors.get(i).getClickArea(SCALE_FACTOR, X_TRANSLATION, Y_TRANSLATION).contains(arg0.getX(), arg0.getY())){
				
				selected = true;
				selectedVector = ForceVectors.get(i);
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
				for(int i=0; i<ForceVectors.size(); i++){
					ForceVectors.get(i).enableAutoMove();
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
