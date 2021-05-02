package application;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller_Selling_Page extends Controller_Dashboard implements Initializable{
	@FXML
    private AnchorPane selling_anchorPnane;
	@FXML
    private TableView<Module_Selling_page> table;

    @FXML
    private TableColumn<Module_Selling_page, Integer> col_id;

    @FXML
    private TableColumn<Module_Selling_page, String> col_product_name;

    @FXML
    private TableColumn<Module_Selling_page, Float> col_price;

    @FXML
    private TableColumn<Module_Selling_page, Float> col_shipping;

    @FXML
    private TableColumn<Module_Selling_page, String> col_platform;

    @FXML
    private TableColumn<Module_Selling_page, String> col_date;

    @FXML
    private TableColumn<Module_Selling_page, String> col_client;

    @FXML
    private JFXComboBox<String> combobox_product_name;

    @FXML
    private TextField textfield_price;

    @FXML
    private TextField tetfield_shipping_price;

    @FXML
    private TextArea textare_client_info;

    @FXML
    private Button button_add;

    @FXML
    private Button button_update;

    @FXML
    private Button button_Delete;

    @FXML
    private Button button_clear;

    @FXML
    private TextField textfield_platform;

    @FXML
    private JFXDatePicker DatePicker_Date_Of_sale;
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle arg1) {
    	DatePicker_Date_Of_sale.setEditable(false);
    	ObservableList<String> options = FXCollections.observableArrayList();
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
				combobox_product_name.setItems(options);
				initialize_table();
				
			}
			
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
    	

    }
    
    
    ObservableList<Module_Selling_page> data = FXCollections.observableArrayList();
    public void initialize_table() throws SQLException, IOException {
    	Connection con = DBConnection.connect();
    	if(con==null) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
    		warning_interface();
			
    	}else {
    		ResultSet rs = con.createStatement().executeQuery("select * from selling");
			while(rs.next()) {
				data.add(new Module_Selling_page(rs.getInt(1),rs.getString(2),rs.getFloat(3),rs.getFloat(4),rs.getString(5),rs.getString(6),rs.getString(7)));
			}
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
			col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			col_shipping.setCellValueFactory(new PropertyValueFactory<>("ship_price"));
			col_platform.setCellValueFactory(new PropertyValueFactory<>("platform_name"));
			col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
			col_client.setCellValueFactory(new PropertyValueFactory<>("client_info"));
			table.setItems(data);
    	}
    }
    
    
    
    @FXML
    void add_button_action(ActionEvent event) throws SQLException, IOException {
    	if(combobox_product_name.getSelectionModel().isEmpty()) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "please select a product name";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		combobox_product_name.requestFocus();
    	}else if(textfield_price.getText().trim().isEmpty()) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the price";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		textfield_price.requestFocus();
    	}else if(tetfield_shipping_price.getText().trim().isEmpty()) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the shipping name";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		tetfield_shipping_price.requestFocus();
    	}else if(textare_client_info.getText().isEmpty()) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the client info";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		textare_client_info.requestFocus();
    	}else if(textfield_platform.getText().isEmpty()) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the platform name";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		textfield_platform.requestFocus();
    	}else if(DatePicker_Date_Of_sale.getValue()==null) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Select the date";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		
    	}else if(textfield_price.getText().matches("^[0-9.]*$")==false) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		textfield_price.requestFocus();
    	}else if(tetfield_shipping_price.getText().matches("^[0-9.]*$")==false) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		tetfield_shipping_price.requestFocus();
    	}else {
    		Connection con =DBConnection.connect();
    		if(con==null) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Please connecto to DB";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
    		}else {
    			Module_Selling_page obj_sel = new Module_Selling_page();
    			obj_sel.setProduct_name(combobox_product_name.getValue());
    			obj_sel.setPrice(Float.parseFloat(textfield_price.getText().trim()));
    			obj_sel.setShip_price(Float.parseFloat(tetfield_shipping_price.getText().trim()));
    			obj_sel.setClient_info(textare_client_info.getText());
    			obj_sel.setPlatform_name(textfield_platform.getText());
    			obj_sel.setDate(DatePicker_Date_Of_sale.getValue().toString());
    			String sql ="INSERT into selling(prod_name,price,shipping_price,platform,date,client_info) VALUES(?,?,?,?,?,?)";
    			PreparedStatement stat=con.prepareStatement(sql);
    			stat.setString(1, obj_sel.getProduct_name());
    			stat.setFloat(2, obj_sel.getPrice());
    			stat.setFloat(3, obj_sel.getShip_price());
    			stat.setString(4, obj_sel.getPlatform_name());
    			stat.setString(5, obj_sel.getDate());
    			stat.setString(6, obj_sel.getClient_info());
    			int status=0;
    			status=stat.executeUpdate();
    			if(status==0) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "Operation Failed!!!";
            		try {
            			warning_interface();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
    			}else {
    				Controller_Successfully_Page.this_is_phrase_changes = "the operation is Successfully Added";
    				try {
						successfully_interface();
					} catch (IOException e) {
						e.printStackTrace();
					}
    				table.getItems().clear();
    				initialize_table();
    				float get_recovered_capital =0;
    				float get_profit =0;
    				sql = "SELECT prod_name, price FROM selling WHERE prod_name IS NOT NULL AND price IS NOT NULL";
    				stat = con.prepareStatement(sql);
    				ResultSet rs = stat.executeQuery();
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
    				
    				recovered_label_static_var.setText(String.format("%.2f", get_recovered_capital)+" €");
    				
    				profit_label_static_var.setText(String.format("%.2f", get_profit)+" €");
    				sql ="SELECT qtt FROM warehouse WHERE product_n=? AND qtt IS NOT NULL";
					stat=con.prepareStatement(sql);
					stat.setString(1, obj_sel.getProduct_name());
					rs2=stat.executeQuery();
					if(rs2.next()) {
						sql = "UPDATE warehouse SET qtt=? WHERE product_n=?";
						stat=con.prepareStatement(sql);
						stat.setInt(1, rs2.getInt(1)-1);
						stat.setString(2, obj_sel.getProduct_name());
						stat.executeUpdate();
					}
					sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
    				stat = con.prepareStatement(sql);
    				rs = stat.executeQuery();
    				float capital_total=0;
    				float price_float =0;
    				String price_string = null;
    				while(rs.next()) {
    					price_string=rs.getString(2);
    					price_string = price_string.replace(",", ".");
    					price_float=Float.parseFloat(price_string);
    					capital_total= capital_total + (rs.getInt(1)*price_float);
    				}

    				String capital =String.format("%.2f", capital_total);
    				capital_static_var.setText(capital+" €");
    				clear_all();
    				
    				
    			}
    			
    			
    			
    		}
    	}
    	

    }
    
    
    
    @FXML
    void update_button_action(ActionEvent event) throws SQLException, IOException {
    	if(table.getSelectionModel().getSelectedItem() !=null) {
    		if(combobox_product_name.getSelectionModel().isEmpty()) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "please select a product name";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		combobox_product_name.requestFocus();
        	}else if(textfield_price.getText().trim().isEmpty()) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the price";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		textfield_price.requestFocus();
        	}else if(tetfield_shipping_price.getText().trim().isEmpty()) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the shipping name";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		tetfield_shipping_price.requestFocus();
        	}else if(textare_client_info.getText().isEmpty()) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the client info";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		textare_client_info.requestFocus();
        	}else if(textfield_platform.getText().isEmpty()) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Enter the platform name";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		textfield_platform.requestFocus();
        	}else if(DatePicker_Date_Of_sale.getValue()==null) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please Select the date";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		
        	}else if(textfield_price.getText().matches("^[0-9.]*$")==false) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		textfield_price.requestFocus();
        	}else if(tetfield_shipping_price.getText().matches("^[0-9.]*$")==false) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "try 0-9 and \".\" exemple : 35.88";
        		try {
        			warning_interface();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		tetfield_shipping_price.requestFocus();
        	}else {
        		Connection con =DBConnection.connect();
        		if(con==null) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Please connecto to DB";
            		try {
            			warning_interface();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
        		}else {
        			Module_Selling_page obj_sel = new Module_Selling_page();
        			obj_sel.setProduct_name(combobox_product_name.getValue());
        			obj_sel.setPrice(Float.parseFloat(textfield_price.getText().trim()));
        			obj_sel.setShip_price(Float.parseFloat(tetfield_shipping_price.getText().trim()));
        			obj_sel.setClient_info(textare_client_info.getText());
        			obj_sel.setPlatform_name(textfield_platform.getText());
        			obj_sel.setDate(DatePicker_Date_Of_sale.getValue().toString());
        			String sql="UPDATE selling SET prod_name=?, price=?, shipping_price=?, platform=?, date=?, client_info=? WHERE id=?";
        			PreparedStatement stat=con.prepareStatement(sql);
        			stat.setString(1, obj_sel.getProduct_name());
        			stat.setFloat(2, obj_sel.getPrice());
        			stat.setFloat(3, obj_sel.getShip_price());
        			stat.setString(4, obj_sel.getPlatform_name());
        			stat.setString(5, obj_sel.getDate());
        			stat.setString(6, obj_sel.getClient_info());
        			stat.setInt(7, table.getSelectionModel().getSelectedItem().getId());
        			int status=0;
        			status=stat.executeUpdate();
        			if(status==0) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "Operation Failed!!!";
                		try {
                			warning_interface();
                		} catch (IOException e) {
                			e.printStackTrace();
                		}
        			}else {
        				Controller_Successfully_Page.this_is_phrase_changes = "the operation is Successfully Updated";
        				try {
    						successfully_interface();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
        				table.getItems().clear();
        				initialize_table();
        				float get_recovered_capital =0;
        				float get_profit =0;
        				sql = "SELECT prod_name, price FROM selling WHERE prod_name IS NOT NULL AND price IS NOT NULL";
        				stat = con.prepareStatement(sql);
        				ResultSet rs = stat.executeQuery();
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
        				recovered_label_static_var.setText(String.format("%.2f", get_recovered_capital)+" €");
        				profit_label_static_var.setText(String.format("%.2f", get_profit)+" €");
    					sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
        				stat = con.prepareStatement(sql);
        				rs = stat.executeQuery();
        				float capital_total=0;
        				float price_float =0;
        				String price_string = null;
        				while(rs.next()) {
        					price_string=rs.getString(2);
        					price_string = price_string.replace(",", ".");
        					price_float=Float.parseFloat(price_string);
        					capital_total= capital_total + (rs.getInt(1)*price_float);
        				}

        				String capital =String.format("%.2f", capital_total);
        				capital_static_var.setText(capital+" €");
        				clear_all();
        				
        			}
        			
        			
        			
        		}
        	}
    	}else {
    		;
    		Controller_Warning_missing_fields.this_is_phrase_changes = "please select an item in the table";
    		try {
    			warning_interface();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    
    @FXML
    void delete_button_action(ActionEvent event) {
    	Controller_Warning_missing_fields.this_is_phrase_changes = "you can't Delete Solded item, contact admin";
		try {
			warning_interface();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clear_all();
    }
    
    @FXML
    void clicked_on_table(MouseEvent event) {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		Module_Selling_page selected_sold_item = table.getSelectionModel().getSelectedItem();
    		combobox_product_name.setValue(selected_sold_item.getProduct_name());
    		textfield_price.setText(String.valueOf(selected_sold_item.getPrice()));
    		tetfield_shipping_price.setText(String.valueOf(selected_sold_item.getShip_price()));
    		textare_client_info.setText(selected_sold_item.getClient_info());
    		textfield_platform.setText(selected_sold_item.getPlatform_name());
    		String date = selected_sold_item.getDate();   
            String[] dateList = date.split("-");      
            String year_string = dateList [0];
            String month_string = dateList [1];
            String dayOfMonth_string = dateList [2];
            int year = Integer.parseInt(year_string);
            int month = Integer.parseInt(month_string);
            int dayOfMonth = Integer.parseInt(dayOfMonth_string);
    		DatePicker_Date_Of_sale.setValue(LocalDate.of(year, month, dayOfMonth));
    		combobox_product_name.setDisable(true);
    		
    	}
    }

    
    
    
    @FXML
    void clear_button_action(ActionEvent event) {
    	clear_all();
    }
    
    private void clear_all() {
    	combobox_product_name.setValue(null);
    	textfield_price.setText("");
    	tetfield_shipping_price.setText("");
    	textare_client_info.setText("");
    	textfield_platform.setText("");
    	DatePicker_Date_Of_sale.setValue(null);
    	table.getSelectionModel().select(null);
    	combobox_product_name.setDisable(false);
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
    @FXML
    void mouse_clicked_on_anchorPane(MouseEvent event) {
    	table.getSelectionModel().select(null);
    	selling_anchorPnane.requestFocus();
    	combobox_product_name.setDisable(false);
    }

}
