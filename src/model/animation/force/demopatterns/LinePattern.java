package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.FVector;

public class LinePattern implements IDemoPattern
{

	@Override
	public List<FVector> getVectorPoints() {
		
		List<FVector> fvectors = new ArrayList<FVector>();
		FVector centralVector = new FVector(0, 0);
		centralVector.initAcceleration();
		centralVector.allow_move_x = false;
		centralVector.allow_move_y = false;
		fvectors.add(centralVector);
		
		
		FVector previousVector = centralVector;
		Random r = new Random();
		for(int i=0; i<10; i++){
			
			double _x = r.nextInt(100);
			double _y =  0;
			FVector vector = new FVector(_x, _y);
			vector.initAcceleration();
			vector.setParentVector(previousVector);
			centralVector.allow_move_y = false;
			previousVector = vector;
			fvectors.add(vector);
		}
		FVector lastVector = new FVector(500, 0);
		lastVector.setParentVector(fvectors.get(fvectors.size()-1));
		lastVector.allow_move_x = false;
		lastVector.allow_move_y = false;
		lastVector.initAcceleration();
		fvectors.add(lastVector);
		
		
		return fvectors;
	}
	
}
