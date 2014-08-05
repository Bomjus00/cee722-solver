import java.util.LinkedHashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.ejml.simple.SimpleMatrix;


public class MatrixInputWindow
{
	private TextField tTargetNode;
	private TextField tSourceNode;
	private int source;
	private int target;
	private NetworkMatrix a;
	private Map<TextField, RowColIndex> values;
	private double[][] mValues;
	private TilePane mPane;
	private Stage s;

	public MatrixInputWindow(int size)
	{
		values = new LinkedHashMap<>();
		addMatrixInputWindowStage(size);
	}
	
	private void addMatrixInputWindowStage(int nSize)
	{
		s = new Stage(StageStyle.UTILITY);
		TilePane tpane = new TilePane(.5, .5);
		

		final int N = nSize;
		//tpane.getChildren().add(entry);
		
		//ask for source
		HBox sourceTargetBox= new HBox();
		sourceTargetBox.setPadding(new Insets(2, 2, 2, 2));
		sourceTargetBox.setSpacing(2);

		HBox sourceBox = new HBox();
		sourceBox.getChildren().add(new Label("Enter source node"));
		tSourceNode = new TextField();
		sourceBox.getChildren().add(tSourceNode);
		
		HBox tarBox = new HBox();
		tarBox.getChildren().add(new Label("Enter target node"));
		tTargetNode = new TextField();
		tarBox.getChildren().add(tTargetNode);
		

		
		tpane.getChildren().add(sourceBox);
		tpane.getChildren().add(tarBox);
		mPane = new TilePane(.5, .5);
		mPane.autosize();
		for(int x=0; x!=N; x++)
		{
			HBox rowBox= new HBox();
			rowBox.setPadding(new Insets(2, 2, 2, 2));
			rowBox.setSpacing(2);
			for(int y=0; y!=N; y++)
			{			
				TextField t = new TextField();
				t.setMaxWidth(50.0);
				t.setMinWidth(50.0);
				rowBox.getChildren().add(t);
				values.put(t, new RowColIndex(x, y));
			}	
			mPane.getChildren().add(rowBox);
		}
		mPane.autosize();
		tpane.getChildren().add(mPane);
		Button b = new Button("Submit");
		b.setOnAction(new ExtractMatrixEventHandler());
		tpane.getChildren().add(b);
		tpane.autosize();
		s.setScene(new Scene(tpane, 450, 450));
		s.sizeToScene();
		s.showAndWait();
	}
	
	class ExtractMatrixEventHandler implements EventHandler<ActionEvent>
	{
		
		@Override
		public void handle(ActionEvent event)
		{
			source = getTextToInt(tSourceNode.getText());
			target = getTextToInt(tTargetNode.getText());
			for(Node h: mPane.getChildren())
			{
				if(h instanceof HBox)
				{
					for(Node n :((HBox) h).getChildren())
					{
						if(n instanceof TextField)
						{
							TextField tf = ((TextField)n);
							if(values.containsKey(tf))
							{
								String s = tf.getText();
								if(s != null)
								{
									try
									{
										RowColIndex x = values.get(tf);
										double val = Double.parseDouble(s);
										mValues[x.getI()][x.getJ()]=val;
									} catch (Exception e)
									{
										System.out.println("Not valid double for matrix");
									}
								}
							}
						}
					}
				}
			}
			a=new NetworkMatrix(new SimpleMatrix(mValues));
			s.close();
			
		}
	}
	
	public int getTextToInt(String s)
	{
		int val = 0;
		if(s != null)
		{
			try
			{
				val = Integer.parseInt(s);
				
			} catch (Exception e)
			{
				System.out.println("Not valid int for " + s);
			}
		}
		return val;
	}
	
	public int getTarget()
	{
		return this.target;
	}
	
	public int getSource()
	{
		return this.source;
	}
	
	public NetworkMatrix getMatrix()
	{
		return this.a;
	}
	
	
	
}

class RowColIndex
{
	private int i;
	private int j;
	
	public RowColIndex(int i, int j)
	{
		this.i= i;
		this.j= j;
	}
	
	public int getI()
	{
		return this.i;
	}
	
	public int getJ()
	{
		return this.j;
	}
}
