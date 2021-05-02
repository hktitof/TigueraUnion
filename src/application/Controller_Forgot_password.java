package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Screen;

public class Controller_Forgot_password extends LoginController implements Initializable{
	
	@FXML
    private AnchorPane forogot_password_AnchorPane;

    @FXML
    private ImageView image_logo;

    @FXML
    private Label label_7;

    @FXML
    private Label label_6;

    @FXML
    private Label label_5;

    @FXML
    private Label label_1;

    @FXML
    private Line line_1;

    @FXML
    private Line line_3;

    @FXML
    private Line line_4;

    @FXML
    private Line line_2;

    @FXML
    private Label label_2;

    @FXML
    private Label label_3;

    @FXML
    private Label label_4;

    @FXML
    private ImageView image_logo_under_big_logo;
	
	
	
	
    
    
	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_forgot_password = (screenBounds.getWidth()*5)/32;
	    double height_forgot_password = (screenBounds.getHeight()*10)/27;
		double old_width = forogot_password_AnchorPane.getPrefWidth();
		double old_height = forogot_password_AnchorPane.getPrefHeight();
		
		
		forogot_password_AnchorPane.setPrefSize(width_forgot_password, height_forgot_password);
		scale_image(image_logo, 300, 400, width_forgot_password,height_forgot_password);
		scale_image(image_logo_under_big_logo, 300, 400, width_forgot_password, height_forgot_password);
		scale_label(label_1, 300, 400, width_forgot_password, height_forgot_password);
		scale_label(label_2, old_width, old_height, width_forgot_password, height_forgot_password);
		scale_label(label_3, old_width, old_height, width_forgot_password, height_forgot_password);
		scale_label(label_4, old_width, old_height, width_forgot_password, height_forgot_password);
		scale_label(label_5, old_width, old_height, width_forgot_password, height_forgot_password);
		scale_label(label_6, old_width, old_height, width_forgot_password, height_forgot_password);
		scale_label(label_7, old_width, old_height, width_forgot_password, height_forgot_password);
		
		
		
    	String path = getClass().getResource("/sound/my_voice.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        
        
    }
	

}
