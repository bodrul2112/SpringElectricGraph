package model.animation.force;

import java.util.Random;

public class FVector 
{
	
	FVector parentVector;
	FVector currentAcceleration = null; //new FVector(1, 1);
	FVector nextAcceleration = null; //new FVector(1,1);
	public double y;
	public double x;
	
	public double charge = 100; 
	public boolean moveable = true; 
	

	
	public FVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void initAcceleration(){
		
		Random r = new Random();
		double nX = -3d + ((double) r.nextInt(6));
		double nY = -3d + ((double) r.nextInt(6));
		//System.out.println(" || " +  nX + " < > " + nY);
		//currentAcceleration = new FVector(-3d+nX, -3d+nY);
		//nextAcceleration = new FVector(-3d+nX, -3d+nY);
		
		currentAcceleration = new FVector(x, y);
		nextAcceleration = new FVector(x, y);
	}

	public boolean setParentVector(FVector parentVector) {
		if(!this.equals(parentVector)){
			this.parentVector = parentVector;
			return true;
		}
		return false; 
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
	
	public FVector add(FVector fvector){
		return new FVector(this.x + fvector.x, this.y + fvector.y );
	}
	
	public FVector subtract(FVector fvector){
		return new FVector(this.x - fvector.x, this.y - fvector.y );
	}
	
	public FVector multiply(FVector fvector){
		return new FVector(this.x * fvector.x, this.y * fvector.y );
	}
	
	public FVector multiply(double n){
		
		return new FVector(this.x * n, this.y * n);
	}
	
	public FVector divide(FVector fvector){
		return null;
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
	
	public FVector normalize(){
		
		double mod = modulus();
		return new FVector(this.x/mod , this.y/mod ); 
	}
	
	public double theta(FVector fvector){
		
		double deltaY = fvector.y - this.y;
		double deltaX = fvector.x - this.x;
		
		double angleInDegrees = Math.atan(deltaY/deltaX) * 180 / Math.PI;
		return angleInDegrees;
	}
	
}
