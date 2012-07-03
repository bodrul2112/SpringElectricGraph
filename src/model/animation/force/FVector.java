package model.animation.force;

public class FVector 
{
	
	FVector parentVector;
	private final double y;
	private final double x;
	

	
	public FVector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setParentVector(FVector parentVector) {
		this.parentVector = parentVector;
	}
	
	public int getXCoord() {
		return (int) x;
	}
	
	public int getYCoord() {
		return (int) y;
	}
	
	public FVector add(FVector fvector){
		return null;
	}
	
	public FVector subtract(FVector fvector){
		return null;
	}
	
	public FVector multiply(FVector fvector){
		return null;
	}
	
	public FVector devide(FVector fvector){
		return null;
	}
	
	
}
