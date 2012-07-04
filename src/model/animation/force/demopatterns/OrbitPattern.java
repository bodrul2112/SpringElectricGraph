package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.FVector;

public class OrbitPattern implements IDemoPattern
{
	public OrbitPattern() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<FVector> getVectorPoints() {
		
		List<FVector> fvectors = new ArrayList<FVector>();
		FVector centralVector = new FVector(400d, 300d);
		centralVector.initAcceleration();
		centralVector.moveable = false;
		fvectors.add(centralVector);
		
		Random r = new Random();
		for(int i=0; i<10; i++){
			
			double _x = r.nextInt(800);
			double _y =  r.nextInt(800);
			System.out.print(_x + " : " + _y);
			FVector vector = new FVector(_x, _y);
			vector.initAcceleration();
			vector.setParentVector(centralVector);
			fvectors.add(vector);
		}
		
		return fvectors;
	}
	
	
}
