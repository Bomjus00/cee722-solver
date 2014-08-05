

import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ejml.simple.SimpleMatrix;


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
		// Set Motif L&F on any platform
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
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        frame = new JFrame();
        final JFXPanel fxPanel = new JFXPanel();
        JToolBar b = buildToolBar();
        JPanel panel = new JPanel();
        frame.add(panel) ;
        panel.add(b);
        panel.add(fxPanel);
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
	    rowSize.setPreferredSize(new Dimension(5,5));
		JButton sizeSetButton = new JButton("Set Size");
		toolBar.setFloatable(false);
//		SetMatrixEventHandler smeh = new SetMatrixEventHandler();
//		sizeSetButton.addActionListener(smeh);
		JButton pickMatrixButton = new JButton("Pick Matrix");
		pickMatrixButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{

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
							nm = new NetworkMatrix(SimpleMatrix.loadCSV(file.getPath()));
//							Scanner in = new Scanner(file);
//							in.useDelimiter(",");
//							while(in.hasNextLine())
//							{
//								List<Double> l = new ArrayList<>();
//								while(in.hasNextDouble())
//								{
//									l.add(in.nextDouble());
//								}
//								inArray.add(l);
//								in.nextLine();
//							}
//							
//							in.close();
//							nSize = inArray.size(); //counts number of arrays since square
//							double[][] valMatrix = new double[nSize][nSize];
//							for(int i=0; i < inArray.size(); i++)
//							{
//								for(int j=0; j < inArray.size(); j++)
//								{
//									valMatrix[i][j] = inArray.get(i).get(j).doubleValue();
//								}
//							}
//							System.out.println(inArray);
//							System.out.println(valMatrix);
//							nm = new NetworkMatrix(new SimpleMatrix(valMatrix));
//							System.out.println(doubleArray);
						}
					} catch (IOException ex)
					{
						System.out.println(); // no file read.k
					}
				}
			}
		});
		//add labels and buttons to the toolbar
		toolBar.add(rowSizeLabel);
		toolBar.add(rowSize);
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
 