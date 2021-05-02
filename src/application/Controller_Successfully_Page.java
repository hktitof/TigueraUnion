package application;


import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Controller_Successfully_Page extends LoginController implements Initializable{
	@FXML
    private JFXButton this_ok_button;
	@FXML
    private AnchorPane successfully_Pane;

    @FXML
    private Label label_successfully_changing;
    
    public static String this_is_phrase_changes = null;
    @FXML
    private ImageView image_successfully;
    @FXML
    private Label label_successfully_fix;
    
    @Override
	public void initialize(URL location, ResourceBundle arg1) {
    	
    	
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_warning_page = (screenBounds.getWidth()*73)/320;
        double height_warning_page = (screenBounds.getHeight()*149)/1080;
        
        successfully_Pane.setPrefWidth(width_warning_page);
        successfully_Pane.setPrefHeight(height_warning_page);
        
		scale_image(image_successfully,438,149,width_warning_page,height_warning_page);
		scale_button(this_ok_button,438,149,width_warning_page,height_warning_page);
		scale_label(label_successfully_fix,438,149,width_warning_page,height_warning_page);
    	scale_label(label_successfully_changing,438,149,width_warning_page,height_warning_page);
    	label_successfully_changing.setText(this_is_phrase_changes);
		
    	
    	String path = getClass().getResource("/sound/successfully_notification.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @FXML
    void action_ok_button(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    @FXML
    void pressed_ok_button(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    	}else {
    		
    	}
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
