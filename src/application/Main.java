package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Main extends Application {
	public double width;
	public double height;
	@Override
	public void start(Stage primaryStage) {
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		this.width = ((screenBounds.getWidth())*5)/12;
        this.height = ((screenBounds.getHeight())*277)/540;
        
        
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/Login_page.fxml"));
			Scene scene = new Scene(root,width,height-12);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			
			
			primaryStage.show();
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			
	        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
	        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
			primaryStage.getIcons().add(new Image("/images/ico png.png"));
			primaryStage.setTitle("Tiguera Union");
		} catch(Exception e) {
			e.printStackTrace();
		}
		String path = getClass().getResource("/sound/startup_first_interface.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
