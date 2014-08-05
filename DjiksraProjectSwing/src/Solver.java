import java.util.List;

import javax.swing.SwingWorker;


public class Solver extends SwingWorker<BasicDijkstraForward, String>
{
	private BasicDijkstraForward bdf;
	private int source;
	private int target;
	private NetworkMatrix a;
	
	public Solver(int source, int target, NetworkMatrix a)
	{
		this.source = source;
		this.target = target;
		this.a = a;
	}
	
	@Override
	protected BasicDijkstraForward doInBackground() throws Exception
	{
		this.bdf = new BasicDijkstraForward(source, target, a);
		return this.bdf;
	}
	
	@Override
	protected void process(List<String> chunks)
	{
		super.process(chunks);
	}
}
	

	
