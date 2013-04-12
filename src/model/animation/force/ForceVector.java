package model.animation.force;

import java.awt.Rectangle;
import java.util.Random;

public class ForceVector 
{
	
	ForceVector parentVector;
	ForceVector currentAcceleration = null; //new FVector(1, 1);
	ForceVector nextAcceleration = null; //new FVector(1,1);
	public double y;
	public double x;
	
	public double charge = 200; 
	
	public boolean always_stationary = false;
	public boolean allow_move_x = true;
	public boolean allow_move_y = true;
	
	int clickRadius = 4; 
	

	
	public ForceVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void initWithRandomAcceleration()
	{
		
		Random r = new Random();
		double nX = -3d + ((double) r.nextInt(6));
		double nY = -3d + ((double) r.nextInt(6));
		//System.out.println(" || " +  nX + " < > " + nY);
		//currentAcceleration = new FVector(-3d+nX, -3d+nY);
		//nextAcceleration = new FVector(-3d+nX, -3d+nY);
		
		currentAcceleration = new ForceVector(x, y);
		nextAcceleration = new ForceVector(x, y);
	}

	public boolean setParentVector(ForceVector parentVector) 
	{
		if(!this.equals(parentVector)){
			this.parentVector = parentVector;
			return true;
		}
		return false; 
	}
	
	public void moveXY_by(double x, double y){
		
		if(allow_move_x){
			this.x+=x;
		}
		
		if(allow_move_y){
			this.y+=y;
		}
	}
	
	public void forceMoveXY(double x, double y){
		this.x = x;
		this.y = y; 
	}
	
	public int getXCoord() {
		return (int) x;
	}
	
	public int getXCoord(double SCALE_FACTOR, int X_TRANSLATION) {
		return (int) ((x*SCALE_FACTOR))+X_TRANSLATION;
	}
	
	public int getYCoord() {
		return (int) y;
	}
	
	public int getYCoord(double SCALE_FACTOR, int Y_TRANSLATION) {
		return (int) ((y*SCALE_FACTOR))+Y_TRANSLATION;
	}
	
	public Rectangle getClickArea(double SCALE_FACTOR, int X_TRANSLATION, int Y_TRANSLATION){
		
		int x = getXCoord(SCALE_FACTOR, X_TRANSLATION);
		int y = getYCoord(SCALE_FACTOR, Y_TRANSLATION);
		return new Rectangle(x-clickRadius, y-clickRadius, (clickRadius*2), (clickRadius*2));
	}
	
	public void disableAutoMove(){
		this.allow_move_x = false;
		this.allow_move_y = false;
	}
	
	public void enableAutoMove(){
		if(!this.always_stationary){
			this.allow_move_x = true;
			this.allow_move_y = true;
		}
	}
	
	public boolean isMoveable(){
		return(this.allow_move_x || this.allow_move_y);
	}
	
	/* vector math */
	
	public ForceVector add(ForceVector fvector){
		return new ForceVector(this.x + fvector.x, this.y + fvector.y );
	}
	
	public ForceVector subtract(ForceVector fvector){
		return new ForceVector(this.x - fvector.x, this.y - fvector.y );
	}
	
	public ForceVector multiply(ForceVector fvector){
		return new ForceVector(this.x * fvector.x, this.y * fvector.y );
	}
	
	public ForceVector multiply(double n){
		
		return new ForceVector(this.x * n, this.y * n);
	}
	
	
	/*
	public FVector dotProduct(FVector fvector){
		//return this.modulus() * fvector.modulus() * Math.cos(getAngle(fvector));
		return add(fvector);
	}
	*/
	
	public double modulus(){
		
		return Math.sqrt((Math.pow(this.x, 2d)) + (Math.pow(this.y, 2d)));
	}
	
	public ForceVector normalize(){
		
		double mod = modulus();
		return new ModulusVector(this.x/mod , this.y/mod ); 
	}
	
	public double theta(ForceVector fvector)
	{
		double deltaY = fvector.y - this.y;
		double deltaX = fvector.x - this.x;
		
		double angleInDegrees = Math.atan(deltaY/deltaX) * 180 / Math.PI;
		return angleInDegrees;
	}
	
}
