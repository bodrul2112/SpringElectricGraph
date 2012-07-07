package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.FVector;

public class LinePattern implements IDemoPattern
{

	int charge = 3;
	
	@Override
	public List<FVector> getVectorPoints() {
		
		List<FVector> fvectors = new ArrayList<FVector>();
		FVector firstVector = new FVector(0, 0);
		fvectors.add(firstVector);
		
		
		FVector previousVector = firstVector;
		Random r = new Random();
		int start_x = 0;
		for(int i=0; i<10; i++){
			
			start_x +=10;
			double _x = start_x;
			double _y =  0;
			FVector vector = new FVector(_x, _y);
			vector.initAcceleration();
			vector.setParentVector(previousVector);
			vector.allow_move_y = false;
			vector.charge = charge; 
			previousVector = vector;
			fvectors.add(vector);
		}
		FVector lastVector = new FVector(500, 0);
		lastVector.setParentVector(fvectors.get(fvectors.size()-1));
		fvectors.add(lastVector);
		
		
		firstVector.initAcceleration();
		firstVector.disableAutoMove();
		firstVector.always_stationary = true;
		firstVector.charge=charge;
		
		lastVector.initAcceleration();
		lastVector.disableAutoMove();
		lastVector.always_stationary = true;
		lastVector.charge = charge;
		
		return fvectors;
	}
	
}
