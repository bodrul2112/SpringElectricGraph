package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.ForceVector;

public class MultiLayerOrbitPattern implements IDemoPattern
{
	
	int layers = 1; 
	int orbits = 1; 
	public MultiLayerOrbitPattern(int layers, int orbits) {
		this.layers = layers; 
		this.orbits = orbits;
	}

	@Override
	public List<ForceVector> getVectorPoints() {
		List<ForceVector> fvectors = new ArrayList<ForceVector>();
		ForceVector centralVector = new ForceVector(0, 0);
		centralVector.initWithRandomAcceleration();
		centralVector.disableAutoMove();
		centralVector.always_stationary = true;
		fvectors.add(centralVector);
		
		attachOrbitsTo(fvectors, centralVector,orbits,0);
		
		/*
		for(int i=0; i<fvectors.size(); i++){
			FVector layerOneVector = fvectors.get(i);
			if(!layerOneVector.equals(centralVector)){
				attachOrbitsTo(fvectors, layerOneVector, 5);
			}
		}
		*/
		return fvectors;
	}

	private void attachOrbitsTo(List<ForceVector> fvectors, ForceVector centralVector, int numberOfOrbits, int currentLayer) {
		Random r = new Random();
		for(int i=0; i<numberOfOrbits; i++){
			
			double _x = r.nextInt(800);
			double _y =  r.nextInt(800);
			ForceVector vector = new ForceVector(-400+_x,-400+_y);
			vector.initWithRandomAcceleration();
			vector.setParentVector(centralVector);
			fvectors.add(vector);
			
			if(currentLayer<layers){
				attachOrbitsTo(fvectors, vector, orbits, currentLayer+1);
			}
		}
	}

}
