package model.animation.force;

public class ModulusVector extends ForceVector
{

	public ModulusVector(double x, double y) {
		super(x, y);
	}
	
	
	@Override
	public int getXCoord() {
		throw new IllegalAccessError("Don't use getXCoord on a modulus vector, it will always be zero");
	}
	
	@Override
	public int getXCoord(double SCALE_FACTOR, int X_TRANSLATION) {
		throw new IllegalAccessError("Don't use getXCoord on a modulus vector, it will always be zero");
	}
	
	@Override
	public int getYCoord() {
		throw new IllegalAccessError("Don't use getXCoord on a modulus vector, it will always be zero");
	}
	
	@Override
	public int getYCoord(double SCALE_FACTOR, int Y_TRANSLATION) {
		throw new IllegalAccessError("Don't use getXCoord on a modulus vector, it will always be zero");
	}
}
