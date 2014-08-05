

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;

import org.la4j.matrix.dense.Basic2DMatrix;


public class Dijkstra
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame;
	BasicDijkstraForward forwardSolver;
	int target;
	int source;
	static NetworkMatrix nm;
	Map<Integer, List<Double>> doubleArray;
	static double[][] inputArray;
	static int nSize;
	
//	private final class MatrixFileChooseEventHandler implements
//			ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent event)
//		{
//			doubleArray = new LinkedHashMap<>(); 
//			JFileChooser chooser = new JFileChooser();
//			chooser.setDialogTitle("Choose a csv file for the matrix");
//			String path = new File("").getAbsolutePath();
//			File initPath= new File(path+"/TestMatrices");
//			chooser.setCurrentDirectory(initPath);
//			int retVal = chooser.showOpenDialog(Dijkstra.this);
//			if(retVal == JFileChooser.APPROVE_OPTION)
//			{
//				File file = chooser.getSelectedFile();
//				try
//				{
//					if (file != null)
//					{
//						Scanner in = new Scanner(file);
//						in.useDelimiter(",");
//						int lines = 0;
//						while(in.hasNext())
//						{
//							lines++;
//							List<Double> doubles = new ArrayList<>();
//							//System.out.print(in.next()+"|"); 
//							doubles.add(in.nextDouble());
//							doubleArray.put(lines, doubles);
//							
//						}
//						in.close();
//						System.out.println(doubleArray);
//					}
//				} catch (IOException ex)
//				{
//					System.out.println(); // no file read.k
//				}
//			}
//			for(Integer i: doubleArray.keySet())
//			{
//				int c = 0;
//				for(double d: doubleArray.get(i))
//				{
//					inputArray[i][c] = d;
//					c++;
//				}
//
//			}
//			nm = new NetworkMatrix(new Basic2DMatrix(inputArray));
//		}
//	}


	private final class SetMatrixEventHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event)
		{
			Integer size = Dijkstra.nSize;
//			try
//			{
//				nSize = Integer.parseInt(size);
//				
//			} catch (NumberFormatException e)
//			{
//				System.out.println(size + " is not a whole number for a square matrix"); 
//			}
			
			
			if(size != null)
			{
				MatrixInputWindow miw = new MatrixInputWindow(size);
				target = miw.getTarget();
				source = miw.getSource();
				nm = miw.getMatrix();
				
				final Solver solver = new Solver(target, source, nm);
				SwingUtilities.invokeLater(new Runnable()
				{
					
					@Override
					public void run()
					{
						solver.execute();
						 
				       
	
					}
				});
				
				solver.addPropertyChangeListener(new PropertyChangeListener()
				{
					
					@Override
					public void propertyChange(PropertyChangeEvent evt)
					{
						if (evt.getNewValue() instanceof StateValue)
						{
							StateValue sv = (StateValue) evt.getNewValue();
							switch (sv) {
							case DONE:
								// update any swing components when complete
								try
								{
									final BasicDijkstraForward bdf = solver
											.get();
									// while solver is working update the graph
									// node // generateNodeCircles();

									System.out.println("D array is: "
											+ bdf.getD());
									System.out.println("Predec array is : "
											+ bdf.getPredec());
								} catch (final CancellationException e)
								{
									JOptionPane.showMessageDialog(
											frame,
											"Solver cancelled", 
											"Solver",
											JOptionPane.WARNING_MESSAGE);
								} catch (final Exception e)
								{
									JOptionPane.showMessageDialog(
											frame,
											"Solver cancelled",
											"Solver",
											JOptionPane.ERROR_MESSAGE);
								}
								break;
							case STARTED:
							case PENDING:
								
								// Pending Activites go here...
								// maybe update STEPS!
								// CancelAction.putValue(Action.NAME, "Cancel");

								break;
							}
						}
					}
					
				});
			}
		}
	}

	
	public static void main(String[] args) 
	{
		 SwingUtilities.invokeLater(new Runnable() 
		 {

			@Override
		      public void run() 
		      {
					initGUI();
		      }
		 });
    }
	
	private static void initGUI()
	{
		
        frame = new JFrame();
        final JFXPanel fxPanel = new JFXPanel();
        JToolBar b = buildToolBar();
        frame.add(b);
        frame.add(fxPanel);
        frame.setTitle("Dijkstra Shortest Path Tutorial");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
       
        Platform.runLater(new Runnable()
		{
			
			@Override
			public void run()
			{
				initFX(fxPanel);
				
			}
		});
	}
	
	public static JToolBar buildToolBar()
	{
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		JLabel rowSizeLabel = new JLabel("Square Matrix Size:");
	    JTextField rowSize = new JTextField();
//		JButton sizeSetButton = new JButton("Set Size");
//		SetMatrixEventHandler smeh = new SetMatrixEventHandler();
//		sizeSetButton.addActionListener(smeh);
		JButton pickMatrixButton = new JButton("Pick Matrix");
		pickMatrixButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				Map<Integer, List<Double>> doubleArray = new LinkedHashMap<>(); 
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choose a csv file for the matrix");
				String path = new File("").getAbsolutePath();
				File initPath= new File(path+"/TestMatrices");
				chooser.setCurrentDirectory(initPath);
				int retVal = chooser.showOpenDialog(Dijkstra.frame);
				if(retVal == JFileChooser.APPROVE_OPTION)
				{
					File file = chooser.getSelectedFile();
					try
					{
						if (file != null)
						{
							Scanner in = new Scanner(file);
							in.useDelimiter(",");
							int lines = 0;
							while(in.hasNext())
							{
								lines++;
								List<Double> doubles = new ArrayList<>();
								//System.out.print(in.next()+"|"); 
								doubles.add(in.nextDouble());
								doubleArray.put(lines, doubles);
								
							}
							in.close();
							System.out.println(doubleArray);
						}
					} catch (IOException ex)
					{
						System.out.println(); // no file read.k
					}
				}
				for(Integer i: doubleArray.keySet())
				{
					int c = 0;
					for(double d: doubleArray.get(i))
					{
						inputArray[i][c] = d;
						c++;
					}

				}
				nm = new NetworkMatrix(new Basic2DMatrix(inputArray));
			}
		});
		//add labels and buttons to the toolbar
		toolBar.add(rowSizeLabel);
//		toolBar.add(sizeSetButton);
		toolBar.add(pickMatrixButton);
		return toolBar;
	}
	
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = new SceneGenerator().getScene();
        fxPanel.setScene(scene);
    }
	
}
 