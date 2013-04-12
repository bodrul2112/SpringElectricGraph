package model.animation.force.demopatterns;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.animation.force.ForceVector;

public class NetPattern implements IDemoPattern
{
	
	int cols;
	int rows;
	int cellSpace = 10;

	public NetPattern(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
	}
	
	@Override
	public List<ForceVector> getVectorPoints() {
		
		
		List<ForceVector> fvectors = new ArrayList<ForceVector>();
		
		ForceVector[][] vectorNet = new ForceVector[rows][cols];
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				vectorNet[i][j] = new ForceVector(i,j);
				fvectors.add(vectorNet[i][j]);
				vectorNet[i][j].initWithRandomAcceleration();
				vectorNet[i][j].charge = 5;
			}
		}
		
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols-1; j++){
				vectorNet[i][j].setParentVector(vectorNet[i][j+1]);
			}
		}
		
		
		int start_y = 0; 
		for(int i=0; i<rows; i++){
			for(int j=0; j<1; j++){
				vectorNet[i][j].disableAutoMove();
				vectorNet[i][j].forceMoveXY(0, start_y);
				vectorNet[i][j].always_stationary = true;
				start_y += cellSpace;
			}
		}
		
		start_y = 0; 
		int start_x = cellSpace * cols;
		for(int i=0; i<rows; i++){
			for(int j=cols-1; j<cols; j++){
				vectorNet[i][j].disableAutoMove();
				vectorNet[i][j].forceMoveXY(start_x, start_y);
				vectorNet[i][j].always_stationary = true;
				start_y += cellSpace;
			}
		}
		
		
		return fvectors;
	}

}
