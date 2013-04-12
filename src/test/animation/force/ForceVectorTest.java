package test.animation.force;

import static org.junit.Assert.*;

import model.animation.force.ForceVector;

import org.junit.Before;
import org.junit.Test;

public class ForceVectorTest {
	
	private ForceVector forceVector = new ForceVector(10, 10);

	@Before
	public void setUp() throws Exception 
	{
		forceVector.initWithRandomAcceleration();
	}

	@Test
	public void testThatItDoesNotAddItselfAsAParentVector() 
	{
		assertFalse(forceVector.setParentVector(forceVector));
	}
	
	@Test
	public void testAddingTwoVectors() 
	{
		forceVector.forceMoveXY(10, 10);
		ForceVector resultVector = forceVector.add(new ForceVector(2, 5));
		
		assertEquals(resultVector.getXCoord(), 12);
		assertEquals(resultVector.getYCoord(), 15);
	}
	
	@Test
	public void testSubtractingTwoVectors() 
	{
		forceVector.forceMoveXY(10, 10);
		ForceVector resultVector = forceVector.subtract(new ForceVector(2, 5));
		
		assertEquals(resultVector.getXCoord(), 8);
		assertEquals(resultVector.getYCoord(), 5);
	}
	
	@Test
	public void testMultiplyingTwoVectors() 
	{
		forceVector.forceMoveXY(10, 10);
		ForceVector resultVector = forceVector.multiply(new ForceVector(2, 5));
		
		assertEquals(resultVector.getXCoord(), 20);
		assertEquals(resultVector.getYCoord(), 50);
	}
	
	@Test
	public void testModulus() 
	{
		forceVector.forceMoveXY(10, 10);
		double modulus = forceVector.modulus();
		
		assertTrue(modulus>14d && modulus<14.2d);
	}
	
	@Test
	public void testNormalize() 
	{
		forceVector.forceMoveXY(10, 32);
		ForceVector resultVector = forceVector.normalize();

		assertTrue(resultVector.x>0.29d && resultVector.x<0.30d);
		assertTrue(resultVector.y>0.95d && resultVector.y<0.96d);
	}
	
	@Test
	public void testTheta() 
	{
		forceVector.forceMoveXY(11, 12);
		ForceVector secondForce = new ForceVector(1,1);
		secondForce.forceMoveXY(25, 66);
		
		double theta = forceVector.theta(secondForce);

		assertTrue(theta>75.46d && theta<75.47d);
	}
	
}
