import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;


public class SceneGenerator
{
	
	private int nSize;
	private Text text;
	private Scene scene;

	

	public SceneGenerator()
	{
		setUpScene();
	}
	
	
	private void setUpScene()
	{	AnchorPane ap = new AnchorPane();
		this.scene = new Scene(ap, 800, 600, Color.BLACK);
	    this.text = new Text("Steps go here");
	    Group root = new Group();
	    root.getChildren().add(this.text);
        //Set square matrix size
        HBox sizeBox = new HBox();
        sizeBox.setPadding(new Insets(10, 5, 10, 5));
        sizeBox.setSpacing(10);
        //sizeBox.setStyle("-fx-background-color:");   //set color if need be
        
        Label rowSizeLabel = new Label("Square Matrix Size:");
        rowSizeLabel.setTextFill(Color.WHITE);
        rowSizeLabel.autosize();
        final TextField rowSize = new TextField();
//        Button sizeSetButton = new Button("Set Size");
//        sizeSetButton.setOnAction(
//        		new SetMatrixEventHandler(rowSize));
//        Button pickMatrixButton = new Button("Pick Matrix");
//        pickMatrixButton.setOnAction(new MatrixFileChooseEventHandler());
//       
//        sizeBox.getChildren().addAll(rowSizeLabel, rowSize, sizeSetButton, pickMatrixButton);
        String s = rowSize.getText();
       
        try
		{
        	Integer.parseInt(s);
		} catch (NumberFormatException e)
		{
			//init call, do nothing.
		}
        
        Group matButtons = new Group();
//        ap.getChildren().add(sizeBox);
//
//        AnchorPane.setLeftAnchor(sizeBox, 5.0);
	}
	
	public void setMatrixSize(int size)
	{
		//sets matrix size and turns a generic set of nodes
		this.nSize = size;
	    generateNodes(scene);
	}
	
	public Scene getScene()
	{
		//BorderPane ap = new BorderPane();

	    return this.scene;
	}
	
	public void setSceneTest(String s)
	{
		this.text.setText(s);
	}

	private Group generateNodes(Scene scene)
	{
		//Creates nodes
	    Group nodes = new Group();
	    for (int i = 0; i != this.nSize; i++) 
	    {
	       Circle circle = new Circle(15, Color.web("white", 0.05));
	       circle.setStrokeType(StrokeType.OUTSIDE);
	       circle.setStroke(Color.web("white", 0.16));
	       circle.setStrokeWidth(4);
	       circle.setCenterX(scene.getWidth()/2);
	       circle.setCenterY(scene.getHeight()/2);
	       nodes.getChildren().add(circle);
	    }
	    return nodes;
	}
}
