package application;


import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controlller_Return_page extends Controller_Dashboard implements Initializable{
	
	@FXML
    private AnchorPane AnchorPane_return;

    @FXML
    private Button button_add;

    @FXML
    private Button button_update;

    @FXML
    private Button button_clear;

    @FXML
    private TextField textfield_find;

    @FXML
    private TableView<Module_Return_page> table;

    @FXML
    private TableColumn<Module_Return_page, Integer> col_id;

    @FXML
    private TableColumn<Module_Return_page, String> col_product_name;

    @FXML
    private TableColumn<Module_Return_page, String> col_refund_by;

    @FXML
    private TableColumn<Module_Return_page, String> col_price;

    @FXML
    private TableColumn<Module_Return_page, String> col_date;

    @FXML
    private JFXComboBox<String> combox_product_name;

    @FXML
    private JFXCheckBox checkbox_seller;

    @FXML
    private JFXCheckBox checkbox_buyer;

    @FXML
    private JFXDatePicker datepicker_date_of_refund;
    @FXML
    private TextField textfield_price;
    
    
    @Override
    public void initialize(URL location, ResourceBundle arg1) {
    	textfield_price.setDisable(true);
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
				String sql = "select distinct prod_name from selling where prod_name is not null" ;
				PreparedStatement stat ;
				stat = con.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();
				while(rs.next()) {
					options.add(rs.getString(1));
				}
				combox_product_name.setItems(options);
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			initialize_table();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    
    
    ObservableList<Module_Return_page> data = FXCollections.observableArrayList();
    public void initialize_table() throws SQLException, IOException {
    	Connection con = DBConnection.connect();
    	if(con==null) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected, please connect to DB";
    		warning_interface();
			
    	}else {
    		ResultSet rs = con.createStatement().executeQuery("select * from returns");
			while(rs.next()) {
				data.add(new Module_Return_page(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getString(5)));
			}
			col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
			col_product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
			col_refund_by.setCellValueFactory(new PropertyValueFactory<>("refund_by"));
			col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
			col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
			table.setItems(data);
    	}
    }
    
    
    
    
    
    
    @FXML
    void add_button_action(ActionEvent event) throws IOException, SQLException {
    	if(((combox_product_name.getValue() == null) || textfield_price.getText().trim().isEmpty()) || ((checkbox_seller.isSelected()==false) && (checkbox_buyer.isSelected() == false))) {	
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Missing fields";	
			warning_interface();
    	}else {
    		Connection con = DBConnection.connect();
    		if(con==null) {
    			Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected";	
    			warning_interface();
    		}else {
    			Module_Return_page obj = new Module_Return_page();
    			obj.setProduct_name(combox_product_name.getValue());
    			if(checkbox_seller.isSelected()) {
    				obj.setRefund_by("Seller");
    				obj.setPrice(Float.parseFloat(textfield_price.getText().toString()));
    			}else {
    				obj.setRefund_by("Buyer");
    				obj.setPrice(Float.parseFloat("0"));
    			}
    			
    			obj.setDate(datepicker_date_of_refund.getValue().toString());
    			
    			String sql ="INSERT into returns(product_n,	refund_by,return_price,date_of_refund) VALUES(?,?,?,?)";
    			PreparedStatement stat=con.prepareStatement(sql);
    			stat.setString(1, obj.getProduct_name());
    			stat.setString(2, obj.getRefund_by());
    			stat.setFloat(3, obj.getPrice());
    			stat.setString(4, obj.getDate());
    			int status=0;
    			status= stat.executeUpdate();
    			if(status==0) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "Return is not added to the table";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
    			}else {
    				Controller_Successfully_Page.this_is_phrase_changes = "Return is Successfully Added";
    				try {
						successfully_interface();
					} catch (IOException e) {
						e.printStackTrace();
					}
    				sql = "SELECT sum(return_price) FROM returns";
    				stat=con.prepareStatement(sql);
    				ResultSet rs2=stat.executeQuery();
    				if(rs2.next()) {
    					loss_label_static.setText(rs2.getString(1)+" €");
    				}
    				sql = "update warehouse set qtt=qtt+1 where product_n=?";
    				stat=con.prepareStatement(sql);
    				stat.setString(1, combox_product_name.getValue().toString());
    				stat.executeUpdate();
    				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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

    				String capital =String.format("%.2f", capital_total);
    				capital_static_var.setText(capital+" €");
    				String get_capital_string_recovred = recovered_label_static_var.getText();
    				String[] get_capital_string_recovred_list = get_capital_string_recovred.split(" ");
    				get_capital_string_recovred_list[0] = get_capital_string_recovred_list[0].replace(",", ".");
    				float get_capital_recovred_string = Float.parseFloat(get_capital_string_recovred_list[0]);
    				sql = "select prc from warehouse where product_n=?";
    				stat = con.prepareStatement(sql);
    				stat.setString(1, combox_product_name.getValue());
    				rs = stat.executeQuery();
    				while(rs.next()) {
    					get_capital_recovred_string= get_capital_recovred_string - Float.parseFloat(rs.getString(1).replace(",", "."));
    				}
    				recovered_label_static_var.setText(get_capital_recovred_string+" €");
    				System.out.println(get_capital_recovred_string);
    				
    				
    				
    				initiliaze_AnchorPane();
    			}
    			
    			
    		}
    	}
    	

    }

    @FXML
    void clear_button_action(ActionEvent event) {
    	

    }

    @FXML
    void mouse_clicked_on_anchorPane(MouseEvent event) {
    	table.getSelectionModel().select(null);
    	AnchorPane_return.requestFocus();

    }

    @FXML
    void update_button_action(ActionEvent event) throws IOException, SQLException {
    	if(table.getSelectionModel().getSelectedItem() !=null) {
    		if(((combox_product_name.getValue() == null) || textfield_price.getText().trim().isEmpty()) || ((checkbox_seller.isSelected()==false) && (checkbox_buyer.isSelected() == false))) {	
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Missing fields";	
    			warning_interface();
        	}else {
        		Connection con = DBConnection.connect();
        		if(con==null) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected";	
        			warning_interface();
        		}else {
        			Module_Return_page obj = new Module_Return_page();
        			obj.setProduct_name(combox_product_name.getValue());
        			if(checkbox_seller.isSelected()) {
        				obj.setRefund_by("Seller");
        				obj.setPrice(Float.parseFloat(textfield_price.getText().toString()));
        			}else {
        				obj.setRefund_by("Buyer");
        				obj.setPrice(Float.parseFloat("0"));
        			}
        			
        			obj.setDate(datepicker_date_of_refund.getValue().toString());
        			
        			String sql ="UPDATE returns set refund_by=?,return_price=?,date_of_refund=? where id=?";
        			PreparedStatement stat=con.prepareStatement(sql);
        			stat.setString(1, obj.getRefund_by());
        			stat.setFloat(2, obj.getPrice());
        			stat.setString(3, obj.getDate());
        			stat.setInt(4, table.getSelectionModel().getSelectedItem().getId());
        			int status=0;
        			status= stat.executeUpdate();
        			if(status==0) {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "Return is not Updated to the table";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
        			}else {
        				Controller_Successfully_Page.this_is_phrase_changes = "Return is Successfully Updated to the table";
        				try {
    						successfully_interface();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
        				sql = "SELECT sum(return_price) FROM returns";
        				stat=con.prepareStatement(sql);
        				ResultSet rs2=stat.executeQuery();
        				if(rs2.next()) {
        					loss_label_static.setText(rs2.getString(1)+" €");
        				}
        				sql = "update warehouse set qtt=qtt+1 where product_n=?";
        				stat=con.prepareStatement(sql);
        				stat.setString(1, combox_product_name.getValue().toString());
        				stat.executeUpdate();
        				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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

        				String capital =String.format("%.2f", capital_total);
        				capital_static_var.setText(capital+" €");
        				initiliaze_AnchorPane();
        				
        				
        				
        				
        			}
        			
        			
        		}
        	}
    		
    		
    	}else {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please select a return line on the right table";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}

    }
    
    public void initiliaze_AnchorPane() {
    	try {
			
			AnchorPane_return.getChildren().clear();
			AnchorPane_return.getChildren().add(FXMLLoader.load(getClass().getResource("/application/Return_page.fxml")));
			AnchorPane_return.setLayoutX(0);
			AnchorPane_return.setLayoutY(0);
	
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    
    @FXML
    void table_clicked_on_mouse(MouseEvent event) {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		Module_Return_page selected_product = table.getSelectionModel().getSelectedItem();
    		if(selected_product.getRefund_by().equals("Buyer")) {
    			combox_product_name.setValue(selected_product.getProduct_name());
    			checkbox_buyer.setSelected(true);
    			checkbox_seller.setSelected(false);
    			textfield_price.setText("");
    			textfield_price.setDisable(true);
    			String date = selected_product.getDate();   
                String[] dateList = date.split("-");      
                String year_string = dateList [0];
                String month_string = dateList [1];
                String dayOfMonth_string = dateList [2];
                int year = Integer.parseInt(year_string);
                int month = Integer.parseInt(month_string);
                int dayOfMonth = Integer.parseInt(dayOfMonth_string);
        		datepicker_date_of_refund.setValue(LocalDate.of(year, month, dayOfMonth));
    			
    			
    		}else {
    			combox_product_name.setValue(selected_product.getProduct_name());
    			checkbox_buyer.setSelected(false);
    			checkbox_seller.setSelected(true);
    			textfield_price.setText(String.valueOf(selected_product.getPrice()));
    			textfield_price.setDisable(false);
    			String date = selected_product.getDate();   
                String[] dateList = date.split("-");      
                String year_string = dateList [0];
                String month_string = dateList [1];
                String dayOfMonth_string = dateList [2];
                int year = Integer.parseInt(year_string);
                int month = Integer.parseInt(month_string);
                int dayOfMonth = Integer.parseInt(dayOfMonth_string);
        		datepicker_date_of_refund.setValue(LocalDate.of(year, month, dayOfMonth));
    			
    			
    		}
    	}

    }
    
    public void clear() {
    	combox_product_name.setValue(null);
    	textfield_find.setText("");
    	textfield_price.setText("");
    	datepicker_date_of_refund.setValue(null);
    	table.getSelectionModel().select(null);
    	AnchorPane_return.requestFocus();
    	
    }
    
    
    
    
    @FXML
    void action_checkbox_buyer(ActionEvent event) {
    	if(checkbox_buyer.isSelected()) {
    		checkbox_seller.setDisable(true);
    		textfield_price.setDisable(true);
    		textfield_price.setText("0");
    		checkbox_seller.setSelected(false);
    	}else {
    		checkbox_seller.setDisable(false);
    		textfield_price.setDisable(true);
    		textfield_price.setText("");
    		checkbox_seller.setSelected(false);
    	}

    }

    @FXML
    void action_checkbox_seller(ActionEvent event) {
    	if(checkbox_seller.isSelected()) {
    		textfield_price.setText("");
    		checkbox_buyer.setDisable(true);
    		textfield_price.setDisable(false);
    		checkbox_buyer.setSelected(false);
    		
    	}else {
    		textfield_price.setText("");
    		checkbox_buyer.setDisable(false);
    		textfield_price.setDisable(true);
    		checkbox_buyer.setSelected(false);
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
	

}
