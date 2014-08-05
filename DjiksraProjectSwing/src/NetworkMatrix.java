
import org.la4j.factory.CRSFactory;
import org.la4j.matrix.Matrix;
import org.la4j.matrix.sparse.CRSMatrix;
import org.la4j.matrix.sparse.SparseMatrix;

public class NetworkMatrix
{
	SparseMatrix m;
	int n;
	public NetworkMatrix(int size)
	{
		CRSFactory cr = new CRSFactory();
		this.m = new CRSMatrix(cr.createSquareMatrix(size));
		this.n = size;
	}
	
	public NetworkMatrix(Matrix m)
	{
		this.m=new CRSMatrix(m);
		//assumes square
		this.n=m.rows();
	}
	
	
	public void setStaticCost(int row, int col, double val)
	{
		this.m.set(row, col, val);
	}
	
	public double getStaticCost(int row, int col)
	{
		return m.get(row-1, col-1);
	}
	
	public Matrix getMatrix()
	{
		return this.m;
	}
	
	public int getSize()
	{
		return this.n;
	}
}
