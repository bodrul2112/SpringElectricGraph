package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.ForceVector;

public class LinePattern implements IDemoPattern
{

	int charge = 3;
	
	@Override
	public List<ForceVector> getVectorPoints() {
		
		List<ForceVector> fvectors = new ArrayList<ForceVector>();
		ForceVector firstVector = new ForceVector(0, 0);
		fvectors.add(firstVector);
		
		
		ForceVector previousVector = firstVector;
		Random r = new Random();
		int start_x = 0;
		for(int i=0; i<10; i++){
			
			start_x +=10;
			double _x = start_x;
			double _y =  0;
			ForceVector vector = new ForceVector(_x, _y);
			vector.initWithRandomAcceleration();
			vector.setParentVector(previousVector);
			vector.allow_move_y = false;
			vector.charge = charge; 
			previousVector = vector;
			fvectors.add(vector);
		}
		ForceVector lastVector = new ForceVector(500, 0);
		lastVector.setParentVector(fvectors.get(fvectors.size()-1));
		fvectors.add(lastVector);
		
		
		firstVector.initWithRandomAcceleration();
		firstVector.disableAutoMove();
		firstVector.always_stationary = true;
		firstVector.charge=charge;
		
		lastVector.initWithRandomAcceleration();
		lastVector.disableAutoMove();
		lastVector.always_stationary = true;
		lastVector.charge = charge;
		
		return fvectors;
	}
	
}
