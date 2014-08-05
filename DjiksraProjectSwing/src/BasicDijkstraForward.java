import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BasicDijkstraForward
{
	private Set<Integer> s_f;
	private Set<Integer> s_prime_f;
	private List<Double> d;
	private List<Integer> predes;
	private Map<Integer, Set<Integer>> connectedNodes;
	private NetworkMatrix staticCost;
	private Integer target;
	private Integer source;
	private int N;

	public BasicDijkstraForward(int source, int target, NetworkMatrix sc)
	{
		this.s_f = new LinkedHashSet<>();
		this.s_prime_f = new LinkedHashSet<>();
		this.d = new LinkedList<>();
		this.N=sc.getSize();
		this.target = target;
		this.source = source;
		this.staticCost	= sc;
		this.connectedNodes = new LinkedHashMap<>();
		predes = new LinkedList<>();
		solveForward();
		
	   
	}
	
	public void setSourceNode(int source)
	{
		this.source = source;
	}
	
	public void setTargetnode(int target)
	{
		this.target = target;
	}
	
	public int getMatrixSize()
	{
		return this.N;
	}
	
	public int getSource()
	{
		return this.source;
	}
	
	public int getTarget()
	{
		return this.target;
	}
	
	public List<Double> getD()
	{
		return new ArrayList<Double>(this.d);
	}
	
	public List<Integer> getPredec()
	{
		return new ArrayList<Integer>(this.predes);
	}
	
	private void solveForward()
	{
		System.out.println("Initializing d, predes arrays");
		//initialize d and predec to all zeros;
		this.d = new ArrayList<Double>(this.N);
		this.predes = new ArrayList<Integer>(this.N);
		for(int c = 0; c!=N; c++)
		{
			this.d.add(c, Double.MAX_VALUE);
			this.predes.add(c, 0);
		}
		
		System.out.println("Initializing the network");
		//initialize connected nodes from passed in matrix
		for(int i=1; i<N+1; i++)
		{	
			for(int j=1; j<N+1; j++)
			{
				
				double d = this.staticCost.getStaticCost(i, j);
				String s = "Node " + i + " is " + d + " to ";
				if(i==j && d > 0)
				{
					System.out.println("Throwing out " + d + 
							" for Node " + i + " as Nodes cannot be connected to themselves");
				}
				else if(d > 0 && this.connectedNodes.get(i) == null)
			    {
			    	Set<Integer> l = new LinkedHashSet<>();
			    	l.add(j);
			    	this.connectedNodes.put(i,l);
			    	System.out.println(s + j);
			    }
			    else if(d > 0)
			    {
			    	Set<Integer> cn = this.connectedNodes.get(i);
			    	cn.add(j);
			    	System.out.println(s + j); 
			    }
			}
		}
		

		System.out.println("Initializing s'");
		//Add all nodes to s_f
		this.s_prime_f.addAll(this.connectedNodes.keySet());
		
		//Move source node to s_f, indicating origin 
		int sourceToMove = 0;
		for(Integer i: this.s_prime_f)
		{
			if(i.intValue() == this.source.intValue())
			{
				System.out.println("Node " + this.source + " is the source node");
				sourceToMove = this.source;
				this.s_f.add(this.source);
				System.out.println("Setting d to 0 for source Node " + i);
				this.d.set(this.source-1,0.0);
			}
		}
		this.s_prime_f.remove(sourceToMove);
		
		while (!s_prime_f.isEmpty())
		{
			// get LASTEST or LAST node from s_f
			Integer latestSF = 0;
			for(Integer i:this.s_f)
			{
				latestSF = i;
			}
			System.out.println("Lastest node in s is " + latestSF);
			
			// find all outgoing nodes from lastest node
			Double dOut = this.d.get(latestSF - 1);

			for (Integer in : this.connectedNodes.get(latestSF))
			{
				System.out.println("All out going nodes from " + latestSF 
						+ " are " + this.connectedNodes.get(latestSF));
				// find d of latest;
				Double cs = this.staticCost.getStaticCost(latestSF,in);
				System.out.println("Cost from " + latestSF + " to "
						+ in + " is " + cs);
				double dOutCost = dOut + cs;
				System.out.println("Cost + d for Node " 
						+ latestSF + " is " + dOutCost);
				Double dIn = this.d.get(in - 1);
				System.out.println("Node " + in + " is: " + dIn);
				if (dIn > dOutCost)
				{
					Double val = new Double(dOut + cs);
					System.out.println("Node " + latestSF + " is: " + dOut);
					this.d.set(in-1, val);
					this.predes.set(in, latestSF);
					System.out.println("Updating D for Node " + in
							+ ": " + this.d);
					System.out.println("Updating Predec for Node " + in
							+ ": " + this.predes);
				}
			}
				
			// find minimum node and move from s_prime_f to s_f
			System.out.println("Finding mininmum S prime node...");
			double min = Double.MAX_VALUE;
			int smallestNode = 0;
			System.out.println("For S prime:");
			for (int i : this.s_prime_f)
			{
				double val = this.d.get(i-1);
				System.out.println("Node " + i + " is: " + val);
				if (val < min)
				{
					min = val;
					smallestNode = i;
				}
			}
			System.out.println("The node with smallest d is " + smallestNode);
			// Move source node to s_f, indicating origin

			System.out.println("Removing node " + smallestNode + " and adding it to s");
			this.s_prime_f.remove(smallestNode);
			this.s_f.add(smallestNode);
		}
	}
}