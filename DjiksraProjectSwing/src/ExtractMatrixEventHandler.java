import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ExtractMatrixEventHandler<T> implements EventHandler<ActionEvent>
{

	@Override
	public void handle(ActionEvent arg0)
	{
		arg0.getSource();
		System.out.println(arg0.getSource());
	}

}
