
import org.ejml.simple.SimpleMatrix;

public class NetworkMatrix
{
	SimpleMatrix m;
	int n;
	public NetworkMatrix(int size)
	{
		this.m = new SimpleMatrix(size, size);
		this.n = size;
	}
	
	public NetworkMatrix(SimpleMatrix m)
	{
		this.m=m;
		//assumes square
		this.n=m.numRows();
	}
	
	
	public void setStaticCost(int row, int col, double val)
	{
		this.m.set(row, col, val);
	}
	
	public double getStaticCost(int row, int col)
	{
		return m.get(row-1, col-1);
	}
	
	public SimpleMatrix getMatrix()
	{
		return this.m;
	}
	
	public int getSize()
	{
		return this.n;
	}
}
