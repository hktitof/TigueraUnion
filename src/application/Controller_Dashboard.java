package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Controller_Dashboard implements Initializable{
	
	
	
	@FXML
    private HBox hbox_top;
	@FXML
    public AnchorPane StackPane_Changer;
	@FXML
    private Label label_username;
	@FXML
    public  Label label_capital;
	@FXML
    public AnchorPane dashboard_full_AnchorPane;
	@FXML
    private Label label_advertising;
	@FXML
	private Label label_recovered_capital;
	@FXML
    private Label label_profit;
	@FXML
    private Label label_loss;
	
	
	public static String label_username_var;
	public static String label_capital_var="";
	public static Label capital_static_var = new Label("");
	public static Label advertising_capital_static_var = new Label("");
	public static Label recovered_label_static_var = new Label("");
	public static Label profit_label_static_var = new Label("");
	public static Label loss_label_static = new Label("");
	
	
	ObservableList<Module_for_get_capital> data = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		
		
		
		
		try {
			Connection con =DBConnection.connect();
			if(con==null) {
				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
			}else {
				String sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
				PreparedStatement stat ;
				stat = con.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();
				float capital_total=0;
				float price_float =0;
				String price_string = null;
				while(rs.next()) {
					price_string=rs.getString(2);
					price_string = price_string.replace(",", ".");
					price_float=Float.parseFloat(price_string);
					capital_total= capital_total + (rs.getInt(1)*price_float);
				}
				label_capital.setText(String.format("%.2f", capital_total)+" €");
				capital_static_var=label_capital;
				float advertising_capital=0;
				sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
				stat = con.prepareStatement(sql);
				rs = stat.executeQuery();
				while(rs.next()) {
					advertising_capital = advertising_capital + rs.getFloat(1);
				}
				label_advertising.setText(String.format("%.2f", advertising_capital)+" €");
				advertising_capital_static_var=label_advertising;
				float get_recovered_capital =0;
				float get_profit =0;
				sql = "SELECT prod_name, price FROM selling WHERE prod_name IS NOT NULL AND price IS NOT NULL";
				stat = con.prepareStatement(sql);
				rs = stat.executeQuery();
				ResultSet rs2;
				String price_from_wareHouse_string;
				while(rs.next()) {
					sql = "SELECT `prc` FROM `warehouse` WHERE `product_n` = ? AND `prc` IS NOT NULL ";
					stat=con.prepareStatement(sql);
					stat.setString(1, rs.getString(1));
					rs2=stat.executeQuery();
					if(rs2.next()) {
						price_from_wareHouse_string=rs2.getString(1).replace(",", ".");
						get_profit=get_profit+(rs.getFloat(2)-Float.parseFloat(price_from_wareHouse_string));
						get_recovered_capital=get_recovered_capital+Float.parseFloat(price_from_wareHouse_string);
					}
					
				}
				label_recovered_capital.setText(String.format("%.2f", get_recovered_capital)+" €");
				recovered_label_static_var=label_recovered_capital;
				label_profit.setText(String.format("%.2f", get_profit)+" €");
				profit_label_static_var=label_profit;
				loss_label_static= label_loss;
				sql = "SELECT sum(return_price) FROM returns";
				stat=con.prepareStatement(sql);
				rs2=stat.executeQuery();
				if(rs2.next()) {
					if(rs2.getString(1)==null) {
						loss_label_static.setText("0,00 €");
					}else {
						loss_label_static.setText(rs2.getString(1)+" €");
					}
					
				}
				/*String get_capital_string=capital_static_var.getText();
				String[] get_capital_string_list=get_capital_string.split(" ");
				Float get_capital_float = Float.parseFloat(get_capital_string_list[0]);
				sql = "select round(sum(warehouse.prc),2) from warehouse,returns where warehouse.product_n = returns.product_n ";
				stat=con.prepareStatement(sql);
				rs2=stat.executeQuery();
				if(rs2.next()) {
					get_capital_float= get_capital_float + Float.parseFloat(rs2.getString(1));
					capital_static_var.setText(String.valueOf(get_capital_float)+ " €");
				}*/
				
				
				
				
				
				
				
				
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Home_page.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
		} catch (Exception e) {
		// TODO: handle exception
		}
		
		label_username.setText(label_username_var);
		String path = getClass().getResource("/sound/startup_second_interface.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
        
		
		
		
	}
	
	
	
	
	
	
	
	
	@FXML
    void page1(MouseEvent event) {
		try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Home_page.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
	} catch (Exception e) {
		// TODO: handle exception
	}

    }

    @FXML
    void page2(MouseEvent event) {
    	try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    public void page3(MouseEvent event) {
    	try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Advertising.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void page4(MouseEvent event) {
    	try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Selling_Page.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    @FXML
    void page5(MouseEvent event) {
    	try {
			
			StackPane_Changer.getChildren().clear();
			StackPane_Changer.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Return_page.fxml")));
			StackPane_Changer.setLayoutX(0);
			StackPane_Changer.setLayoutY(0);
	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void warning_interface() throws IOException{
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_warning_page = (screenBounds.getWidth()*73)/320;
        double height_warning_page = (screenBounds.getHeight()*149)/1080;
    	Stage primaryStage = new Stage();
    	Parent root;
    	root = FXMLLoader.load(getClass().getResource("/application/Error_missing fields.fxml"));
		Scene scene = new Scene(root,width_warning_page,height_warning_page-12);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
		primaryStage.getIcons().add(new Image("/images/error icon.png"));
		primaryStage.setTitle("Tiguera Union");
    }
    
    
    public void change_capital_label() throws SQLException {
    	dashboard_full_AnchorPane.getChildren().clear();
		try {
			dashboard_full_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dashboard_full_AnchorPane.setLayoutX(0);
		dashboard_full_AnchorPane.setLayoutY(0);
		
    }

}
