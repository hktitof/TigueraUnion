package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller_Admin_page extends LoginController implements Initializable{
	@FXML
    private TableView<Module_Admin_page> table;

    @FXML
    private TableColumn<Module_Admin_page, Integer> col_id;

    @FXML
    private TableColumn<Module_Admin_page, String> col_username;

    @FXML
    private TableColumn<Module_Admin_page, String> col_password;
    @FXML
    private AnchorPane admin_page_AnchorPane;
    @FXML
    private Label label_logout;
    @FXML
    private Label label_set_selected_item;
    @FXML
    private JFXButton change_button;
    @FXML
    private JFXTextField textfield_new_password;
    
    
    ObservableList<Module_Admin_page> oblist = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		
		String path = getClass().getResource("/sound/startup_second_interface.mp3").toString();
        Media media = new Media(path);
        MediaPlayer mp = new MediaPlayer(media);
        mp.play();
		
		admin_page_AnchorPane.requestFocus();
		
		
			
				try {
					java.sql.Connection con = DBConnection.connect();
					ResultSet rs = con.createStatement().executeQuery("select * from users");
					while(rs.next()) {
						oblist.add(new Module_Admin_page(rs.getInt("id"),rs.getString("username"),rs.getString("password")));
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
		col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		table.setItems(oblist);
		admin_page_AnchorPane.requestFocus();
		
	}
	
	
	
	
	
	
	@FXML
    void logout_pressed(MouseEvent event) {
		((Stage)(((Label)event.getSource()).getScene().getWindow())).close();
		Stage primaryStage = new Stage();
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width = ((screenBounds.getWidth())*5)/12;
        double height = ((screenBounds.getHeight())*277)/540;
        
        
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

    }
	
	@FXML
    void change_button_action(ActionEvent event) {
		if(label_set_selected_item.getText().isEmpty()) {
			Controller_Warning_missing_fields.this_is_phrase_changes = "Please select a user";
			try {
				warning_interface();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			textfield_new_password.requestFocus();
			
		}else {
			if(textfield_new_password.getText().trim().isEmpty()) {
				Controller_Warning_missing_fields.this_is_phrase_changes = "field is empty";
				try {
					warning_interface();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				textfield_new_password.requestFocus();
			}else {
				int st=0;
				int var_con_check = 1;
				try {
        			Connection con = DBConnection.connect();
        			if(con==null) {
        				var_con_check = 0;
        			}else {
        				Module_Admin_page selected_user_Object_for_change = table.getSelectionModel().getSelectedItem();
        				String sql = "UPDATE users SET password=? WHERE id=?";
        				PreparedStatement stat;
        				stat = con.prepareStatement(sql);
        				stat.setString(1,textfield_new_password.getText());
        				stat.setInt(2, selected_user_Object_for_change.getId());
        				st = stat.executeUpdate();
        				con.close();
        			}
					
				} catch (Exception e) {
					e.getStackTrace();
				}
				if(var_con_check==0) {
					Controller_Warning_missing_fields.this_is_phrase_changes = "Connection Failed try to connect to DB";
    				try {
    					warning_interface();
    				} catch (IOException e) {
    					
    					e.printStackTrace();
    				}
    				textfield_new_password.requestFocus();
				}else {
					if(st==0) {
        				
        				System.out.println("not changed changed");
        			}else {
        				
        				Controller_Successfully_Page.this_is_phrase_changes = "password for \""+label_set_selected_item.getText()+"\" is changed";
        				try {
							successfully_interface();
						} catch (IOException e) {
							e.printStackTrace();
						}
        				textfield_new_password.setText("");
        				label_set_selected_item.setText("");
        				admin_page_AnchorPane.requestFocus();
        				table.getItems().clear();
        				initialize_after_update();
        				
        				
        				
        			}
				}
				
			}
		}
		

    }
	
	
	
	void initialize_after_update() {
		try {
				java.sql.Connection con = DBConnection.connect();
				ResultSet rs = con.createStatement().executeQuery("select * from users");
				while(rs.next()) {
				oblist.add(new Module_Admin_page(rs.getInt("id"),rs.getString("username"),rs.getString("password")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
		col_password.setCellValueFactory(new PropertyValueFactory<>("password"));

		table.setItems(oblist);
		admin_page_AnchorPane.requestFocus();
	}
	
	
	
	public void successfully_interface() throws IOException{
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_warning_page = (screenBounds.getWidth()*73)/320;
        double height_warning_page = (screenBounds.getHeight()*149)/1080;
    	Stage primaryStage = new Stage();
    	Parent root;
    	root = FXMLLoader.load(getClass().getResource("/application/Successfully_Page.fxml"));
		Scene scene = new Scene(root,width_warning_page,height_warning_page-12);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
		primaryStage.getIcons().add(new Image("/images/successfully_image.png"));
		primaryStage.setTitle("Tiguera Union");
    }
	
	
	@FXML
    void select_a_user_method(MouseEvent event) {
		Module_Admin_page selected_user = table.getSelectionModel().getSelectedItem();
		label_set_selected_item.setText(selected_user.getUsername());

    }
	
	@FXML
    void mouse_pressedOn_anchorPane(MouseEvent event) {
		admin_page_AnchorPane.requestFocus();

    }
	

}
