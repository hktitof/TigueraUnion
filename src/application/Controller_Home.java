package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class Controller_Home extends Controller_Dashboard implements Initializable{
	@FXML
    private Label Home_capital_label;

    @FXML
    private Label Home_recovered_capital;

    @FXML
    private Label Home_profit_label;

    @FXML
    private Label Home_advertising_label;
    @FXML
    private Label label_loss_in_home_page;
    @Override
	public void initialize(URL location, ResourceBundle arg1) {
    	Home_capital_label.setText(capital_static_var.getText());
    	Home_recovered_capital.setText(recovered_label_static_var.getText());
    	Home_profit_label.setText(profit_label_static_var.getText());
    	Home_advertising_label.setText(advertising_capital_static_var.getText()) ;
    	label_loss_in_home_page.setText(loss_label_static.getText());
    	
    }

}
