import junit.framework.TestCase;

import org.ejml.simple.SimpleMatrix;
import org.junit.Test;


public class BasicDijkstraForwardTest extends TestCase
{
	@Override
	protected void setUp() throws Exception
	{

		super.setUp();
	}
	
	@Test
	public void testHW()
	{
		// A simple 2D array matrix
		SimpleMatrix a = new SimpleMatrix(new double[][]{
		  {0, 25.0, 35.0, 0.0, 0.0 },
		  {0.0, 0.0, 0.0, 15.0, 0.0 },
		  {20.0, 45.0, 0.0, 0.0, 0.0 },
		  {0.0, 0.0, 15.0, 0.0, 45.0},
		  {0.0, 0.0, 25.0, 35.0, 0.0}
		});
		NetworkMatrix nm = new NetworkMatrix(a);
		BasicDijkstraForward bdf = new BasicDijkstraForward(5,1,nm);
		bdf.getD();
		System.out.println("D array is: " + bdf.getD());
		bdf.getPredec();
		System.out.println("Predec array is : " + bdf.getPredec());
		assertTrue(!bdf.getD().isEmpty());
		assertTrue(!bdf.getPredec().isEmpty());
	}
	
	@Override
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}
	

}
