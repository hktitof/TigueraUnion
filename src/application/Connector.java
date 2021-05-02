package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
	
	

	public class Connector extends Controller_Dashboard{
		@FXML
	    private Label label_capital;
		@FXML
	    private AnchorPane dashboard_full_AnchorPane;

	    public Connector() {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/Dashboard_page.fxml"));
	        fxmlLoader.setRoot(this);
	        fxmlLoader.setController(this);

	        try {
	        	fxmlLoader.load();
	            
	            dashboard_full_AnchorPane.getChildren().clear();
				dashboard_full_AnchorPane.getChildren().add(fxmlLoader.load());
				dashboard_full_AnchorPane.setLayoutX(0);
				dashboard_full_AnchorPane.setLayoutY(0);
	        } catch (IOException exception) {
	            throw new RuntimeException(exception);
	        }
	    }



	    public Label getLabel_capital() {
			return label_capital;
		}

		public void setLabel_capital(String capital) {
			this.label_capital.setText(capital+" Euro");
		}

		@FXML
	    protected void doSomething() {
	        System.out.println("The button was clicked!");
	    }
	

}
