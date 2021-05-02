package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller_Advertising extends Controller_Dashboard implements Initializable{
	
	
	@FXML
    private TableView<Module_Advertising_ads_table> table;

    @FXML
    private TableColumn<Module_Advertising_ads_table, Integer> col_id;

    @FXML
    private TableColumn<Module_Advertising_ads_table, String> col_status;

    @FXML
    private TableColumn<Module_Advertising_ads_table, String> col_prod_name;

    @FXML
    private TableColumn<Module_Advertising_ads_table, Float> col_price;

    @FXML
    private TableColumn<Module_Advertising_ads_table, String> col_platform;
    
	@FXML
    private TextField before_textfield_prod_name;

    @FXML
    private TextField after_textfield_price;

    @FXML
    private TextField before_textfield_prod_price;

    @FXML
    private TextField before_platform;

    @FXML
    private TextField after_textfield_platform;

    @FXML
    private JFXCheckBox checkbox_before;

    @FXML
    private JFXCheckBox checkbox_after;

    @FXML
    private JFXComboBox<String> after_combox;
    
    @FXML
    private JFXButton Cancel_button;
    @FXML
    private JFXButton ok_button;
    @FXML
    private BorderPane Delete_BorderPane;
    @FXML
    private AnchorPane Adevertising_AnchorPane;
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle arg1) {
    	ObservableList<String> options = FXCollections.observableArrayList();
    	before_platform.setDisable(true);
    	before_textfield_prod_name.setDisable(true);
    	before_textfield_prod_price.setDisable(true);
    	after_combox.setDisable(true);
    	after_textfield_platform.setDisable(true);
    	after_textfield_price.setDisable(true);
    	try {
			Connection con =DBConnection.connect();
			
			if(con==null) {
				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
			}else {
				String sql = "SELECT product_n FROM warehouse WHERE product_n IS NOT NULL" ;
				PreparedStatement stat ;
				stat = con.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();
				while(rs.next()) {
					options.add(rs.getString(1));
				}
				after_combox.setItems(options);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			initialize_table();
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}
    	
    	
    }
    
    
    @FXML
    void add_button_action(ActionEvent event) throws SQLException {
    	if(checkbox_before.isSelected()) {
    		if(before_textfield_prod_name.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the product name";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			before_textfield_prod_name.requestFocus();
    		}else if(before_textfield_prod_price.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the price field";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			before_textfield_prod_price.requestFocus();
    			
    		}else if(before_platform.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the platform name";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			before_platform.requestFocus();
    			
    		}else if(before_textfield_prod_price.getText().trim().matches("^[0-9.]*$") ==false) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			before_textfield_prod_price.requestFocus();
    		}else {
    			Connection con = DBConnection.connect();
    			if(con ==null) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
    			}else {
    				Module_Advertising_ads_table ads_obj = new Module_Advertising_ads_table();
        			ads_obj.setProduct_name(before_textfield_prod_name.getText());
        			Float price = Float.parseFloat(before_textfield_prod_price.getText().trim().toString());
        			ads_obj.setPrice(price);
        			ads_obj.setStatus("Before");
        			ads_obj.setPlatform(before_platform.getText());
        			String sql ="INSERT into advertising(sts,p_name,pce,pltfm) VALUES(?,?,?,?)";
        			PreparedStatement stat=con.prepareStatement(sql);
        			stat.setString(1, ads_obj.getStatus());
        			stat.setString(2, ads_obj.getProduct_name());
        			stat.setFloat(3, ads_obj.getPrice());
        			stat.setString(4, ads_obj.getPlatform());
        			int status=0;
        			status= stat.executeUpdate();
        			if(status==0) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "ads not added to the table";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
        			}else {
        				Controller_Successfully_Page.this_is_phrase_changes = "the ads is Successfully Added";
        				try {
							successfully_interface();
						} catch (IOException e) {
							e.printStackTrace();
						}
        				clear_before();
        				float advertising_capital=0;
        				sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
        				stat = con.prepareStatement(sql);
        				ResultSet rs = stat.executeQuery();
        				while(rs.next()) {
        					advertising_capital = advertising_capital + rs.getFloat(1);
        				}
        				advertising_capital_static_var.setText(String.format("%.2f", advertising_capital)+" €");
        				
        				table.getItems().clear();
        				try {
							initialize_table();
						} catch (SQLException | IOException e) {
							
							e.printStackTrace();
						}
        				
        			}
    			}
    		}
    		
    	}else if(checkbox_after.isSelected()) {
    		if(after_combox.getSelectionModel().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "please select a product name";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			after_combox.requestFocus();
    		}else if(after_textfield_price.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the price field";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			after_textfield_price.requestFocus();
    			
    		}else if(after_textfield_platform.getText().trim().isEmpty()) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the platform name";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			after_textfield_platform.requestFocus();
    			
    		}else if(after_textfield_price.getText().trim().matches("^[0-9.]*$") ==false) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			after_textfield_price.requestFocus();
    		}else {
    			Connection con = DBConnection.connect();
    			if(con ==null) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
    			}else {
    				Module_Advertising_ads_table ads_obj = new Module_Advertising_ads_table();
        			ads_obj.setProduct_name(after_combox.getValue());
        			Float price = Float.parseFloat(after_textfield_price.getText().trim().toString());
        			ads_obj.setPrice(price);
        			ads_obj.setStatus("After");
        			ads_obj.setPlatform(after_textfield_platform.getText());
        			String sql ="INSERT into advertising(sts,p_name,pce,pltfm) VALUES(?,?,?,?)";
        			PreparedStatement stat=con.prepareStatement(sql);
        			stat.setString(1, ads_obj.getStatus());
        			stat.setString(2, ads_obj.getProduct_name());
        			stat.setFloat(3, ads_obj.getPrice());
        			stat.setString(4, ads_obj.getPlatform());
        			int status=0;
        			status= stat.executeUpdate();
        			if(status==0) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "ads not added to the table";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
        			}else {
        				Controller_Successfully_Page.this_is_phrase_changes = "the ads is Successfully Added";
        				try {
    						successfully_interface();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
        				clear_before();
        				float advertising_capital=0;
        				sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
        				stat = con.prepareStatement(sql);
        				ResultSet rs = stat.executeQuery();
        				while(rs.next()) {
        					advertising_capital = advertising_capital + rs.getFloat(1);
        				}
        				advertising_capital_static_var.setText(String.format("%.2f", advertising_capital)+" €");
        				table.getItems().clear();
        				try {
    						initialize_table();
    					} catch (SQLException | IOException e) {
    						
    						e.printStackTrace();
    					}
        				
        			}
        			
        		}
    		}
    			
    		
    		
    	}else {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Select After Or Before";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	

    }
    
    @FXML
    void update_button_action(ActionEvent event) throws SQLException {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		if(checkbox_before.isSelected()) {
    			if(before_textfield_prod_name.getText().trim().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the product name";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			before_textfield_prod_name.requestFocus();
        		}else if(before_textfield_prod_price.getText().trim().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the price field";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			before_textfield_prod_price.requestFocus();
        			
        		}else if(before_platform.getText().trim().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the platform name";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			before_platform.requestFocus();
        			
        		}else if(before_textfield_prod_price.getText().trim().matches("^[0-9.]*$") ==false) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			before_textfield_prod_price.requestFocus();
        		}else {
        			Connection con =DBConnection.connect();
        			if(con==null) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
        			}else {
        				Module_Advertising_ads_table ads_obj = new Module_Advertising_ads_table();
            			ads_obj.setProduct_name(before_textfield_prod_name.getText());
            			Float price = Float.parseFloat(before_textfield_prod_price.getText().trim().toString());
            			ads_obj.setPrice(price);
            			ads_obj.setPlatform(before_platform.getText());
            			String sql ="UPDATE advertising SET p_name=?, pce=?, pltfm=? WHERE id=?";
            			PreparedStatement stat=con.prepareStatement(sql);
            			stat.setString(1, ads_obj.getProduct_name());
            			stat.setFloat(2, ads_obj.getPrice());
            			stat.setString(3, ads_obj.getPlatform());
            			stat.setInt(4, table.getSelectionModel().getSelectedItem().getId());
            			int status=0;
            			status= stat.executeUpdate();
            			if(status==0) {
            				Controller_Warning_missing_fields.this_is_phrase_changes = "ads not Updated Try Again";
                			try {
                				warning_interface();
                			} catch (IOException e) {
                				e.printStackTrace();
                			}
            			}else {
            				Controller_Successfully_Page.this_is_phrase_changes = "the ads is Successfully Updated";
            				try {
    							successfully_interface();
    						} catch (IOException e) {
    							e.printStackTrace();
    						}
            				clear_before();
            				float advertising_capital=0;
            				sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
            				stat = con.prepareStatement(sql);
            				ResultSet rs = stat.executeQuery();
            				while(rs.next()) {
            					advertising_capital = advertising_capital + rs.getFloat(1);
            				}
            				advertising_capital_static_var.setText(String.format("%.2f", advertising_capital)+" €");
            				table.getItems().clear();
            				try {
    							initialize_table();
    						} catch (SQLException | IOException e) {
    							
    							e.printStackTrace();
    						}
            				
            			}
        			}
        		}
    		}else {
    			
    			if(after_combox.getSelectionModel().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "please select a product name";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			after_combox.requestFocus();
        		}else if(after_textfield_price.getText().trim().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the price field";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			after_textfield_price.requestFocus();
        			
        		}else if(after_textfield_platform.getText().trim().isEmpty()) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Missing the platform name";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			after_textfield_platform.requestFocus();
        			
        		}else if(after_textfield_price.getText().trim().matches("^[0-9.]*$") ==false) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        			after_textfield_price.requestFocus();
        		}else {
        			Connection con = DBConnection.connect();
        			if(con ==null) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
        			}else {
        				Module_Advertising_ads_table ads_obj = new Module_Advertising_ads_table();
            			ads_obj.setProduct_name(after_combox.getValue());
            			Float price = Float.parseFloat(after_textfield_price.getText().trim().toString());
            			ads_obj.setPrice(price);
            			ads_obj.setPlatform(after_textfield_platform.getText());
            			String sql ="UPDATE advertising SET p_name=?, pce=?, pltfm=? WHERE id=?";
            			PreparedStatement stat=con.prepareStatement(sql);
            			stat.setString(1, ads_obj.getProduct_name());
            			stat.setFloat(2, ads_obj.getPrice());
            			stat.setString(3, ads_obj.getPlatform());
            			stat.setInt(4, table.getSelectionModel().getSelectedItem().getId());
            			int status=0;
            			status= stat.executeUpdate();
            			if(status==0) {
            				Controller_Warning_missing_fields.this_is_phrase_changes = "ads not Ypdated try again";
                			try {
                				warning_interface();
                			} catch (IOException e) {
                				e.printStackTrace();
                			}
            			}else {
            				Controller_Successfully_Page.this_is_phrase_changes = "the ads is Successfully Updated";
            				try {
        						successfully_interface();
        					} catch (IOException e) {
        						e.printStackTrace();
        					}
            				clear_before();
            				float advertising_capital=0;
            				sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
            				stat = con.prepareStatement(sql);
            				ResultSet rs = stat.executeQuery();
            				while(rs.next()) {
            					advertising_capital = advertising_capital + rs.getFloat(1);
            				}
            				advertising_capital_static_var.setText(String.format("%.2f", advertising_capital)+" €");
            				table.getItems().clear();
            				try {
        						initialize_table();
        					} catch (SQLException | IOException e) {
        						
        						e.printStackTrace();
        					}
            				
            			}
            			
            		}
        		}
    			
    		}
    		
    	}else {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Select a product in the table";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void delete_button_action(ActionEvent event) throws SQLException {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		Connection con = DBConnection.connect();
    		if(con==null){
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}else {
    			
				String path = getClass().getResource("/sound/error_sound.mp3").toString();
		        Media media = new Media(path);
		        MediaPlayer mp = new MediaPlayer(media);
		        mp.play();
				Delete_BorderPane.setVisible(true);
    			
    		}
    		
    	}else {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Select a product in the table";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    @FXML
    void action_Cancel_button(ActionEvent event) {
    	Delete_BorderPane.setVisible(false);
    	Adevertising_AnchorPane.requestFocus();

    }
    
    @FXML
    void action_ok_button(ActionEvent event) throws SQLException, IOException {
    	Connection con=DBConnection.connect();
    	PreparedStatement stat;
		String sql = "DELETE FROM advertising WHERE id=?";
		
		stat = con.prepareStatement(sql);
		stat.setInt(1, table.getSelectionModel().getSelectedItem().getId());
		int st = stat.executeUpdate();
		if(st!=0) {
			Controller_Successfully_Page.this_is_phrase_changes = "the ads is Successfully Deleted";
			try {
				successfully_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
			float advertising_capital=0;
			sql = "SELECT pce FROM advertising WHERE pce IS NOT NULL ";
			stat = con.prepareStatement(sql);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				advertising_capital = advertising_capital + rs.getFloat(1);
			}
			advertising_capital_static_var.setText(String.format("%.2f", advertising_capital)+" €");
			Delete_BorderPane.setVisible(false);
			table.getItems().clear();
			initialize_table();
			clear_before();
		}else {
			Controller_Warning_missing_fields.this_is_phrase_changes = "Error.. the ads is not deleted";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    }
    
    @FXML
    void table_clicked_on_mouse(MouseEvent event) {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		Module_Advertising_ads_table selected_product = table.getSelectionModel().getSelectedItem();
    		if(selected_product.getStatus().equals("After")) {
    			clear_before();
    			checkbox_before.setSelected(false);
    			checkbox_before.setDisable(false);
    			before_textfield_prod_name.setDisable(true);
    			before_platform.setDisable(true);
    			before_textfield_prod_price.setDisable(true);
    			checkbox_after.setSelected(true);
    			checkbox_after.setDisable(false);
    			after_combox.setDisable(false);
    			after_textfield_price.setDisable(false);
    			after_textfield_platform.setDisable(false);
    			after_combox.setValue(selected_product.getProduct_name());
    			String price_setter = String.valueOf(selected_product.getPrice());
    			after_textfield_price.setText(price_setter);
    			after_textfield_platform.setText(selected_product.getPlatform());
    			
    			
    		}else {
    			clear_before();
    			checkbox_after.setSelected(false);
    			checkbox_after.setDisable(false);
    			after_combox.setDisable(true);
    			after_textfield_platform.setDisable(true);
    			after_textfield_price.setDisable(true);
    			checkbox_before.setSelected(true);
    			checkbox_before.setDisable(false);
    			before_textfield_prod_name.setDisable(false);
    			before_textfield_prod_price.setDisable(false);
    			before_platform.setDisable(false);
    			before_textfield_prod_name.setText(selected_product.getProduct_name());
    			String price_setter = String.valueOf(selected_product.getPrice());
    			before_textfield_prod_price.setText(price_setter);
    			before_platform.setText(selected_product.getPlatform());
    			
    		}
    	}

    }
    
    ObservableList<Module_Advertising_ads_table> data = FXCollections.observableArrayList();
    public void initialize_table() throws SQLException, IOException {
    	Connection con = DBConnection.connect();
    	if(con==null) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
    		warning_interface();
			
    	}else {
    		ResultSet rs = con.createStatement().executeQuery("select * from advertising");
			while(rs.next()) {
				data.add(new Module_Advertising_ads_table(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getString(5)));
			}
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
			col_prod_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
			col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			col_platform.setCellValueFactory(new PropertyValueFactory<>("platform"));
			table.setItems(data);
    	}
    }
    
    @FXML
    void after_checkbox_action(ActionEvent event) {
    	if(checkbox_after.isSelected()) {
    		checkbox_before.setSelected(false);
    		after_combox.setDisable(false);
        	after_textfield_price.setDisable(false);
        	after_textfield_platform.setDisable(false);
        	before_platform.setDisable(true);
        	before_textfield_prod_price.setDisable(true);
        	before_textfield_prod_name.setDisable(true);
        	after_textfield_platform.setText("");
        	after_textfield_price.setText("");
        	before_platform.setText("");
        	before_textfield_prod_name.setText("");
        	before_textfield_prod_price.setText("");
        	table.getSelectionModel().select(null);
        	
    		
    	}else {
    		
    		after_combox.setDisable(true);
        	after_textfield_price.setDisable(true);
        	after_textfield_platform.setDisable(true);
        	after_textfield_platform.setText("");
        	after_textfield_price.setText("");
        	before_platform.setText("");
        	before_textfield_prod_name.setText("");
        	before_textfield_prod_price.setText("");
        	table.getSelectionModel().select(null);
        	
    	}

    }

    @FXML
    void before_checkbox_action(ActionEvent event) {
    	if(checkbox_before.isSelected()) {
    		checkbox_after.setSelected(false);
    		before_platform.setDisable(false);
        	before_textfield_prod_price.setDisable(false);
        	before_textfield_prod_name.setDisable(false);
        	after_combox.setDisable(true);
        	after_textfield_price.setDisable(true);
        	after_textfield_platform.setDisable(true);
        	before_platform.setText("");
        	before_textfield_prod_name.setText("");
        	before_textfield_prod_price.setText("");
        	after_textfield_platform.setText("");
        	after_textfield_price.setText("");
        	after_combox.setValue(null);
        	table.getSelectionModel().select(null);
        	
        	
    		
    	}else {
    		
    		before_platform.setDisable(true);
        	before_textfield_prod_price.setDisable(true);
        	before_textfield_prod_name.setDisable(true);
        	before_platform.setText("");
        	before_textfield_prod_name.setText("");
        	before_textfield_prod_price.setText("");
        	after_textfield_platform.setText("");
        	after_textfield_price.setText("");
        	table.getSelectionModel().select(null);
        	
    	}
    }
    
    
    @FXML
    void cleat_button_acion(ActionEvent event) {
    	clear_before();
    	checkbox_after.setSelected(false);
    	checkbox_before.setSelected(false);
    	before_platform.setDisable(true);
    	before_textfield_prod_price.setDisable(true);
    	before_textfield_prod_name.setDisable(true);
    	before_platform.setText("");
    	before_textfield_prod_name.setText("");
    	before_textfield_prod_price.setText("");
    	after_textfield_platform.setText("");
    	after_textfield_price.setText("");
    	table.getSelectionModel().select(null);
    	after_combox.setDisable(true);
    	after_textfield_price.setDisable(true);
    	after_textfield_platform.setDisable(true);

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
    
    private void clear_before() {
    	before_platform.setText("");
    	before_textfield_prod_name.setText("");
    	before_textfield_prod_price.setText("");
    	after_textfield_platform.setText("");
    	after_textfield_price.setText("");
    	after_combox.setValue(null);
    }
    
    @FXML
    void mouse_pressed_on_anchorPane(MouseEvent event) {
    	table.getSelectionModel().select(null);
    	Adevertising_AnchorPane.requestFocus();
    }
	

}
