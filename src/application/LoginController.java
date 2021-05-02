package application;


import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController implements Initializable{
	    
	@FXML
    private JFXTextField textfield_password;

    @FXML
    private JFXTextField textfield_username;

    @FXML
    private JFXPasswordField passwordfield_password;

    @FXML
    private JFXCheckBox checkbox_show_password;

    @FXML
    private JFXButton login_button;

    @FXML
    private JFXButton quit_button;
    @FXML
    private AnchorPane login_interface;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private AnchorPane login_AnchorPane;
    @FXML
    private Label label_copyrights;
    @FXML
    private AnchorPane blueAnchorpane;
    @FXML
    private ImageView image_tiguera_big_logo;
    @FXML
    private Label label_internationa_ecom;
    @FXML
    private Label label_created_by;
    @FXML
    private Label label_forgot_your_passeord;
    @FXML
    private ImageView user_big_logo;

    @FXML
    private ImageView user_icon;

    @FXML
    private ImageView password_icon;
    
    
    
    public double width;
	public double height;
   

    
	public void initialize(URL location, ResourceBundle arg1) {
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		this.width = (screenBounds.getWidth()*5)/12;
        this.height = (screenBounds.getHeight()*277)/540;
        
        scale_label(label_copyrights,800,554,this.width,this.height);
        scale_AnchorPane(blueAnchorpane);
        scale_image(image_tiguera_big_logo,800,554,this.width,this.height);
        scale_label(label_internationa_ecom,800,554,this.width,this.height);
        scale_label(label_created_by,800,554,this.width,this.height);
        scale_label(label_forgot_your_passeord,800,554,this.width,this.height);
        scale_button(login_button,800,554,this.width,this.height);
        scale_button(quit_button,800,554,this.width,this.height);
    	scale_image(user_big_logo,800,554,this.width,this.height);
    	scale_image(user_icon,800,554,this.width,this.height);
    	scale_image(password_icon,800,554,this.width,this.height);
    	scale_textfield(textfield_username);
    	scale_textfield(textfield_password);
    	scale_Passwordfield(passwordfield_password);
    	scale_checkbox(checkbox_show_password);
    	textfield_password.setVisible(false);
    	
    	
    	
    	
    }
    

    
    @FXML
    void action_login_button(ActionEvent event) throws IOException  {
    		int all_is_filled_check_var =1;
    	
    		if(textfield_username.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "please enter your username";
				try {
					warning_interface();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			textfield_username.requestFocus();
    			all_is_filled_check_var=0;
    		}else if (checkbox_show_password.isSelected()){
    			if(textfield_password.getText().isEmpty()){
    				Controller_Warning_missing_fields.this_is_phrase_changes = "please enter your password";
					try {
						warning_interface();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textfield_password.requestFocus();
					all_is_filled_check_var=0;
    			}
    		}else {
    			if(passwordfield_password.getText().isEmpty()){
    				Controller_Warning_missing_fields.this_is_phrase_changes = "please enter your password";
					try {
						warning_interface();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					passwordfield_password.requestFocus();
					all_is_filled_check_var=0;
    			}
    			
    		}
    		if(all_is_filled_check_var==1) {
    			int check_con_var = 1;
    			String get_username_var=null;
        		try {
        			Connection con = DBConnection.connect();
        			if(con==null) {
    					check_con_var=0;
        			}else {
        				String sql = "SELECT username FROM users WHERE username=?";
        				PreparedStatement stat ;
        				stat = con.prepareStatement(sql);
        				stat.setString(1, textfield_username.getText().toLowerCase().trim());
        				ResultSet rs = stat.executeQuery();
        				if(rs.next()) {
        					get_username_var = rs.getString(1);
            			}
        			}
					
				} catch (Exception e) {
					e.getStackTrace();
				}
    			if(check_con_var==0) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
					warning_interface();
    				
    			}else {
    				if(get_username_var == null) {
    					Controller_Warning_missing_fields.this_is_phrase_changes = textfield_username.getText().trim().toLowerCase()+" is not exist";
    					warning_interface();
    					textfield_username.requestFocus();
            		}else{
            			if(checkbox_show_password.isSelected()) {
            				int check_con_var2 = 1;
                			String get_password_var=null;
                    		try {
                    			Connection con = DBConnection.connect();
                    			if(con==null) {
                    				check_con_var2=0;
                    			}else {
                    				String sql = "SELECT password FROM users WHERE username=?";
                    				PreparedStatement stat ;
                    				stat = con.prepareStatement(sql);
                    				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                    				ResultSet rs = stat.executeQuery();
                    				if(rs.next()) {
                    					get_password_var = rs.getString(1);
                        			}
                    			}
            					
            				} catch (Exception e) {
            					e.getStackTrace();
            				}
                    		if(check_con_var2==0) {
                    			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
            					warning_interface();
                    		}else {
                    			if(get_password_var.equals(textfield_password.getText())) {
                    				((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                					if(textfield_username.getText().equals("admin")) {
                						try {
                        					Stage primaryStage = new Stage();
                        					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                        					Scene scene = new Scene(root,800,400);
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
                					}else {
                						((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
                						try {
                        					Stage primaryStage = new Stage();
                        					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                        					Scene scene = new Scene(root,1102-12,689-12);
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
                    			}else {
                    				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+textfield_password.getText()+" is incorrect";
                					warning_interface();
                					textfield_password.requestFocus();
                    				
                    			}
                    		}
            				
            			}else {
            				int check_con_var3 = 1;
                			String get_password_var3=null;
                    		try {
                    			Connection con = DBConnection.connect();
                    			if(con==null) {
                    				check_con_var3=0;
                    			}else {
                    				String sql = "SELECT password FROM users WHERE username=?";
                    				PreparedStatement stat ;
                    				stat = con.prepareStatement(sql);
                    				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                    				ResultSet rs = stat.executeQuery();
                    				if(rs.next()) {
                    					get_password_var3 = rs.getString(1);
                        			}
                    			}
            					
            				} catch (Exception e) {
            					e.getStackTrace();
            				}
                    		if(check_con_var3==0) {
                    			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
            					warning_interface();
                    		}else {
                    			if(get_password_var3.equals(passwordfield_password.getText())) {
                    				
                    				if(textfield_username.getText().equals("admin")) {
                    					((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                						try {
                        					Stage primaryStage = new Stage();
                        					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                        					Scene scene = new Scene(root,800,400);
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
                					}else {
                						((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
                						try {
                        					Stage primaryStage = new Stage();
                        					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                        					Scene scene = new Scene(root,1102-12,689-12);
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
                    				
                    			}else {
                    				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+passwordfield_password.getText()+" is incorrect";
                					warning_interface();
                					passwordfield_password.requestFocus();
                    				
                    			}
                    		}
            				
            			}
            			
            		}
    				
    			}
    
        		
        		
    		}
    		
    	}

    
    @FXML
    void checkbox_view(ActionEvent event) {
    	if(checkbox_show_password.isSelected()) {
    		textfield_password.setVisible(true);
    		textfield_password.setText(passwordfield_password.getText());
    		passwordfield_password.setVisible(false);
	
    	}else {
    		textfield_password.setVisible(false);
    		passwordfield_password.setText(textfield_password.getText());
    		passwordfield_password.setVisible(true);

    	}

    }
    
    
    @FXML
    void action_quit_button(ActionEvent event) {
    	((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    
    @FXML
    void quit_button_pressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    	}else {
    		login_button.requestFocus();
    	}
    }
    
    
    @FXML
    void password_1_typing(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		try {
    			action_textfiedl_login_3(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}

    }

    @FXML
    void password_2_typing(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		try {
    			action_textfiedl_login_3(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    @FXML
    void username_typing(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		try {
    			action_textfiedl_login_3(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    
    @FXML
    void check_checkbox_method(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		if(checkbox_show_password.isSelected()) {
    			checkbox_show_password.setSelected(false);
    			checkbox_view(null);
    		}else {
    			checkbox_show_password.setSelected(true);
    			checkbox_view(null);
    		}
    	}

    }
    @FXML
    void login_button_pressed(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		try {
    			action_login_button_2(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else if(event.getCode() == KeyCode.TAB) {
        		quit_button.requestFocus();
    	}else {
    		
    	}
    	

    }
    
    
    @FXML
    void mouse_pressed_forgot_password(MouseEvent event) throws IOException {
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		double width_forgot_password = (screenBounds.getWidth()*5)/32;
        double height_forgot_password = (screenBounds.getHeight()*10)/27;
    	Stage primaryStage = new Stage();
    	Parent root;
    	root = FXMLLoader.load(getClass().getResource("/application/Forgot_password.fxml"));
		Scene scene = new Scene(root,width_forgot_password,height_forgot_password-12);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		
		primaryStage.setResizable(false);
		primaryStage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
		primaryStage.getIcons().add(new Image("/images/ico png.png"));
		primaryStage.setTitle("Tiguera Union");

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
    
    
    
    
    public void scale_label(Label give_label, double w,double h, double current_width,double current_height) {
    	double new_layout_x = (give_label.getLayoutX()/w)*current_width;
        double new_layout_y = (give_label.getLayoutY()/h)*current_height;
        double new_pref_w = ((give_label.getPrefWidth()/w)*current_width);
        double new_pref_h = ((give_label.getPrefHeight()/h)*current_height);
        double new_font = (new_pref_w / give_label.getPrefWidth()) * give_label.getFont().getSize();
        give_label.setLayoutX(new_layout_x);
        give_label.setLayoutY(new_layout_y);
        give_label.setPrefWidth(new_pref_w);
        give_label.setPrefHeight(new_pref_h);
        give_label.setFont(new Font(new_font));
    }
    
    public void scale_AnchorPane(AnchorPane give_anchorpane) {
    	double new_pref_w = (give_anchorpane.getPrefWidth()/800)*width;
    	double new_pref_h = (give_anchorpane.getPrefHeight()/554)*height;
    	give_anchorpane.setPrefWidth(new_pref_w);
    	give_anchorpane.setPrefHeight(new_pref_h);
    	
    }
    
    public void scale_image(ImageView give_image, double w,double h, double current_width,double current_height) {
    	double new_fit_w = (give_image.getFitWidth()/w)*current_width;
    	double new_fit_h = (give_image.getFitHeight()/h)*current_height;
    	double new_layout_x = (give_image.getLayoutX()/w)*current_width;
        double new_layout_y = (give_image.getLayoutY()/h)*current_height;
        give_image.setLayoutX(new_layout_x);
        give_image.setLayoutY(new_layout_y);
    	give_image.setFitWidth(new_fit_w);
    	give_image.setFitHeight(new_fit_h);
    }
    
    public void scale_button(JFXButton give_button, double w,double h, double current_width,double current_height) {
    	double new_fit_w = (give_button.getPrefWidth()/w)*current_width;
    	double new_fit_h = (give_button.getPrefHeight()/h)*current_height;
    	double new_layout_x = (give_button.getLayoutX()/w)*current_width;
        double new_layout_y = (give_button.getLayoutY()/h)*current_height;
        double new_font = (new_fit_w / give_button.getPrefWidth()) * give_button.getFont().getSize();
        give_button.setLayoutX(new_layout_x);
        give_button.setLayoutY(new_layout_y);
        give_button.setPrefSize(new_fit_w, new_fit_h);
        give_button.setFont(new Font(new_font));
        
    }
    public void scale_textfield(JFXTextField give_this) {
    	double new_fit_w = (give_this.getPrefWidth()/800)*width;
    	double new_fit_h = (give_this.getPrefHeight()/554)*height;
    	double new_layout_x = (give_this.getLayoutX()/800)*width;
        double new_layout_y = (give_this.getLayoutY()/554)*height;
        
        give_this.setLayoutX(new_layout_x);
        give_this.setLayoutY(new_layout_y);
        give_this.setPrefWidth(new_fit_w);
        give_this.setPrefHeight(new_fit_h);
        
    }
    public void scale_Passwordfield(JFXPasswordField give_this) {
    	double new_fit_w = (give_this.getPrefWidth()/800)*width;
    	double new_fit_h = (give_this.getPrefHeight()/554)*height;
    	double new_layout_x = (give_this.getLayoutX()/800)*width;
        double new_layout_y = (give_this.getLayoutY()/554)*height;
       
        give_this.setLayoutX(new_layout_x);
        give_this.setLayoutY(new_layout_y);
        give_this.setPrefWidth(new_fit_w);
        give_this.setPrefHeight(new_fit_h);
        
    }
    
    public void scale_checkbox(JFXCheckBox give_this) {
    	double new_fit_w = (give_this.getPrefWidth()/800)*width;
    	double new_fit_h = (give_this.getPrefHeight()/554)*height;
    	double new_layout_x = (give_this.getLayoutX()/800)*width;
        double new_layout_y = (give_this.getLayoutY()/554)*height;
        double new_font = (new_fit_w / give_this.getPrefWidth()) * give_this.getFont().getSize();
        give_this.setLayoutX(new_layout_x);
        give_this.setLayoutY(new_layout_y);
        give_this.setPrefWidth(new_fit_w);
        give_this.setPrefHeight(new_fit_h);
        give_this.setFont(new Font(new_font));
    }
    
    
    
    void action_login_button_2(KeyEvent event) throws IOException{
    	int all_is_filled_check_var =1;
    	
		if(textfield_username.getText().trim().isEmpty()) {
			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
			try {
				warning_interface();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textfield_username.requestFocus();
			all_is_filled_check_var=0;
		}else if (checkbox_show_password.isSelected()){
			if(textfield_password.getText().isEmpty()){
				Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
				try {
					warning_interface();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textfield_password.requestFocus();
				all_is_filled_check_var=0;
			}
		}else {
			if(passwordfield_password.getText().isEmpty()){
				Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
				try {
					warning_interface();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				passwordfield_password.requestFocus();
				all_is_filled_check_var=0;
			}
			
		}
		if(all_is_filled_check_var==1) {
			int check_con_var = 1;
			String get_username_var=null;
    		try {
    			Connection con = DBConnection.connect();
    			if(con==null) {
					check_con_var=0;
    			}else {
    				String sql = "SELECT username FROM users WHERE username=?";
    				PreparedStatement stat ;
    				stat = con.prepareStatement(sql);
    				stat.setString(1, textfield_username.getText().toLowerCase().trim());
    				ResultSet rs = stat.executeQuery();
    				if(rs.next()) {
    					get_username_var = rs.getString(1);
        			}
    			}
				
			} catch (Exception e) {
				e.getStackTrace();
			}
			if(check_con_var==0) {
				Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
				warning_interface();
				
			}else {
				if(get_username_var == null) {
					Controller_Warning_missing_fields.this_is_phrase_changes = textfield_username.getText().trim().toLowerCase()+" is not exist";
					warning_interface();
					textfield_username.requestFocus();
        		}else{
        			if(checkbox_show_password.isSelected()) {
        				int check_con_var2 = 1;
            			String get_password_var=null;
                		try {
                			Connection con = DBConnection.connect();
                			if(con==null) {
                				check_con_var2=0;
                			}else {
                				String sql = "SELECT password FROM users WHERE username=?";
                				PreparedStatement stat ;
                				stat = con.prepareStatement(sql);
                				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                				ResultSet rs = stat.executeQuery();
                				if(rs.next()) {
                					get_password_var = rs.getString(1);
                    			}
                			}
        					
        				} catch (Exception e) {
        					e.getStackTrace();
        				}
                		if(check_con_var2==0) {
                			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
        					warning_interface();
                		}else {
                			if(get_password_var.equals(textfield_password.getText())) {
                				((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            					if(textfield_username.getText().equals("admin")) {
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                    					Scene scene = new Scene(root,800,400);
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
            					}else {
            						((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                    					Scene scene = new Scene(root,1102-12,689-12);
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
                			}else {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+textfield_password.getText()+" is incorrect";
            					warning_interface();
            					textfield_password.requestFocus();
                				
                			}
                		}
        				
        			}else {
        				int check_con_var3 = 1;
            			String get_password_var3=null;
                		try {
                			Connection con = DBConnection.connect();
                			if(con==null) {
                				check_con_var3=0;
                			}else {
                				String sql = "SELECT password FROM users WHERE username=?";
                				PreparedStatement stat ;
                				stat = con.prepareStatement(sql);
                				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                				ResultSet rs = stat.executeQuery();
                				if(rs.next()) {
                					get_password_var3 = rs.getString(1);
                    			}
                			}
        					
        				} catch (Exception e) {
        					e.getStackTrace();
        				}
                		if(check_con_var3==0) {
                			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
        					warning_interface();
                		}else {
                			if(get_password_var3.equals(passwordfield_password.getText())) {
                				
                				if(textfield_username.getText().equals("admin")) {
                					((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                    					Scene scene = new Scene(root,800,400);
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
            					}else {
            						((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                    					Scene scene = new Scene(root,1102-12,689-12);
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
                				
                			}else {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+passwordfield_password.getText()+" is incorrect";
            					warning_interface();
            					passwordfield_password.requestFocus();
                				
                			}
                		}
        				
        			}
        			
        		}
				
			}

    		
    		
		}
    }
	
    void action_textfiedl_login_3(KeyEvent event) throws IOException{
    	int all_is_filled_check_var =1;
    	
		if(textfield_username.getText().trim().isEmpty()) {
			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
			try {
				warning_interface();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			textfield_username.requestFocus();
			all_is_filled_check_var=0;
		}else if (checkbox_show_password.isSelected()){
			if(textfield_password.getText().isEmpty()){
				Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
				try {
					warning_interface();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				textfield_password.requestFocus();
				all_is_filled_check_var=0;
			}
		}else {
			if(passwordfield_password.getText().isEmpty()){
				Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
				try {
					warning_interface();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				passwordfield_password.requestFocus();
				all_is_filled_check_var=0;
			}
			
		}
		if(all_is_filled_check_var==1) {
			int check_con_var = 1;
			String get_username_var=null;
    		try {
    			Connection con = DBConnection.connect();
    			if(con==null) {
					check_con_var=0;
    			}else {
    				String sql = "SELECT username FROM users WHERE username=?";
    				PreparedStatement stat ;
    				stat = con.prepareStatement(sql);
    				stat.setString(1, textfield_username.getText().toLowerCase().trim());
    				ResultSet rs = stat.executeQuery();
    				if(rs.next()) {
    					get_username_var = rs.getString(1);
        			}
    			}
				
			} catch (Exception e) {
				e.getStackTrace();
			}
			if(check_con_var==0) {
				Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
				warning_interface();
				
			}else {
				if(get_username_var == null) {
					Controller_Warning_missing_fields.this_is_phrase_changes = textfield_username.getText().trim().toLowerCase()+" is not exist";
					warning_interface();
					textfield_username.requestFocus();
        		}else{
        			if(checkbox_show_password.isSelected()) {
        				int check_con_var2 = 1;
            			String get_password_var=null;
                		try {
                			Connection con = DBConnection.connect();
                			if(con==null) {
                				check_con_var2=0;
                			}else {
                				String sql = "SELECT password FROM users WHERE username=?";
                				PreparedStatement stat ;
                				stat = con.prepareStatement(sql);
                				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                				ResultSet rs = stat.executeQuery();
                				if(rs.next()) {
                					get_password_var = rs.getString(1);
                    			}
                			}
        					
        				} catch (Exception e) {
        					e.getStackTrace();
        				}
                		if(check_con_var2==0) {
                			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
        					warning_interface();
                		}else {
                			if(get_password_var.equals(textfield_password.getText())) {
                				((Stage)(((TextField)event.getSource()).getScene().getWindow())).close();
            					if(textfield_username.getText().equals("admin")) {
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                    					Scene scene = new Scene(root,800,400);
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
            					}else {
            						((Stage)(((TextField)event.getSource()).getScene().getWindow())).close();
            						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                    					Scene scene = new Scene(root,1102-12,689-12);
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
                			}else {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+textfield_password.getText()+" is incorrect";
            					warning_interface();
            					textfield_password.requestFocus();
                				
                			}
                		}
        				
        			}else {
        				int check_con_var3 = 1;
            			String get_password_var3=null;
                		try {
                			Connection con = DBConnection.connect();
                			if(con==null) {
                				check_con_var3=0;
                			}else {
                				String sql = "SELECT password FROM users WHERE username=?";
                				PreparedStatement stat ;
                				stat = con.prepareStatement(sql);
                				stat.setString(1, textfield_username.getText().toLowerCase().trim());
                				ResultSet rs = stat.executeQuery();
                				if(rs.next()) {
                					get_password_var3 = rs.getString(1);
                    			}
                			}
        					
        				} catch (Exception e) {
        					e.getStackTrace();
        				}
                		if(check_con_var3==0) {
                			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
        					warning_interface();
                		}else {
                			if(get_password_var3.equals(passwordfield_password.getText())) {
                				
                				if(textfield_username.getText().equals("admin")) {
                					((Stage)(((TextField)event.getSource()).getScene().getWindow())).close();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Admin_page.fxml"));
                    					Scene scene = new Scene(root,800,400);
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
            					}else {
            						((Stage)(((TextField)event.getSource()).getScene().getWindow())).close();
            						Controller_Dashboard.label_username_var = textfield_username.getText().trim();
            						try {
                    					Stage primaryStage = new Stage();
                    					Parent root = FXMLLoader.load(getClass().getResource("/application/Dashboard_page.fxml"));
                    					Scene scene = new Scene(root,1102-12,689-12);
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
                				
                			}else {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "password "+passwordfield_password.getText()+" is incorrect";
            					warning_interface();
            					passwordfield_password.requestFocus();
                				
                			}
                		}
        				
        			}
        			
        		}
				
			}

    		
    		
		}
    }
	

}
