

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.ejml.simple.SimpleMatrix;


public class Dijkstra
{
	static JFrame frame;
	BasicDijkstraForward forwardSolver;
	int target;
	int source;
	static NetworkMatrix nm;
	static int nSize;
	
	private static class MatrixFileChooseActionListener implements
			ActionListener
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
								System.out.println(nm.getMatrix().getMatrix().toString());
							}
						} catch (IOException ex)
						{
							System.out.println(); // no file read.k
						}
					}
					
					List<String> s=new ArrayList<>();
					for(int c = 0; c < nm.getSize(); c++)
					{
						s.add(String.valueOf(c));
					}
					

					 String[] choices = s.toArray(new String[s.size()]) ;
					 String input = (String) JOptionPane.showInputDialog(null, "Choose Source Node",
					        "Please select source node", JOptionPane.QUESTION_MESSAGE, null, // Use
					                                                                        // default
					                                                                        // icon
					        choices, // Array of choices
					        choices[1]); // Initial choice
				    
					 List<String> s2=new ArrayList<>();
					for(int c = 0; c < nm.getSize(); c++)
					{
						s2.add(String.valueOf(c));
					}
					 
					 String[] choices2 = s2.toArray(new String[s2.size()]) ;
					 String input2 = (String) JOptionPane.showInputDialog(null, "Choose Target",
					        "Please select target node", JOptionPane.QUESTION_MESSAGE, null, // Use
					                                                                        // default
					                                                                        // icon
					        choices2, // Array of choices
					        choices2[1]); // Initial choice
					 int source=0;
					 int target=0;
					 try
					 {
						 source = Integer.parseInt(input);
						 target = Integer.parseInt(input2);
					 }catch (NumberFormatException e) 
					 {
						// TODO: handle exception
					}

					
					//use solver to attempt to solve the network
					final Solver solver = new Solver(source, target, nm);
					SwingUtilities.invokeLater(new Runnable()
					{
						
						@Override
						public void run()
						{
							solver.execute();
						}
					});
				}
			};


	

	
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
		JButton generateMatrix = new JButton("Generate Matrix:");
		generateMatrix.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//creates a random matrix up to 25 by 25
				int n = (int)(Math.random()*25+1);
				int source = (int)(Math.random()*25+1);
				int target = (int)(Math.random()*25+1);
				nm=new NetworkMatrix(SimpleMatrix.random(n, n, 0, 25, new Random()));
				System.out.println("Random matrix is size " + n + ". " + nm.getMatrix().toString());
				System.out.println("Source is: " + source);
				System.out.println("Target is: " + target);
				final Solver solver = new Solver(source, target, nm);
				SwingUtilities.invokeLater(new Runnable()
				{
					
					@Override
					public void run()
					{
						solver.execute();
					}
				});
				
				
			}
		});
		toolBar.setFloatable(false);

		JButton pickMatrixButton = new JButton("Pick Matrix From File");
		pickMatrixButton.addActionListener(new MatrixFileChooseActionListener());
		
		//add labels and buttons to the toolbar
		toolBar.add(generateMatrix);
		toolBar.add(pickMatrixButton);
		return toolBar;
	}
	
    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = new SceneGenerator().getScene();
        fxPanel.setScene(scene);
    }
	
}
 