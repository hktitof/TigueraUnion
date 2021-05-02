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

public class Controller_Warning_missing_fields extends LoginController implements Initializable{
	@FXML
    private AnchorPane warning_page;

	@FXML
    private JFXButton this_ok_button;
	@FXML
    private Label label_for_warning;
	
	@FXML
    private ImageView image_warning;
	@FXML
    private Label label_warning_fix;
	
	public static String this_is_phrase_changes = null;
	
	
	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_warning_page = (screenBounds.getWidth()*73)/320;
        double height_warning_page = (screenBounds.getHeight()*149)/1080;
        
        warning_page.setPrefWidth(width_warning_page);
        warning_page.setPrefHeight(height_warning_page);
        
		scale_image(image_warning,438,149,width_warning_page,height_warning_page);
		scale_button(this_ok_button,438,149,width_warning_page,height_warning_page);
		scale_label(label_warning_fix,438,149,width_warning_page,height_warning_page);
    	label_for_warning.setText(this_is_phrase_changes);
    	scale_label(label_for_warning,438,149,width_warning_page,height_warning_page);
    	
    	//System.out.println(mp3MusicFile);
    	
    	
    	String path = getClass().getResource("/sound/error_sound.mp3").toString();
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
    
    
    void action_ok_button_2(KeyEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    	

    }

}
