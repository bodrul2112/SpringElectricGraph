package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.ForceVector;

public class OrbitPattern implements IDemoPattern
{
	private int orbits;

	public OrbitPattern(int orbits) {
		this.orbits = orbits;
	}

	@Override
	public List<ForceVector> getVectorPoints() {
		
		List<ForceVector> fvectors = new ArrayList<ForceVector>();
		ForceVector centralVector = new ForceVector(0, 0);
		centralVector.initWithRandomAcceleration();
		centralVector.allow_move_x = false;
		centralVector.allow_move_y = false;
		fvectors.add(centralVector);
		
		centralVector.disableAutoMove();
		
		Random r = new Random();
		for(int i=0; i<orbits; i++){
			
			double _x = r.nextInt(800);
			double _y =  r.nextInt(800);
			System.out.print(_x + " : " + _y);
			ForceVector vector = new ForceVector(_x, _y);
			vector.initWithRandomAcceleration();
			vector.setParentVector(centralVector);
			fvectors.add(vector);
		}
		
		return fvectors;
	}
	
	
}
