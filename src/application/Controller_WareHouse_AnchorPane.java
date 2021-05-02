package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;





public class Controller_WareHouse_AnchorPane extends Controller_Dashboard implements Initializable{
	@FXML
    private TextArea Textarea_comment;

    @FXML
    private TextField textfield_product;

    @FXML
    private TextField textfield_quantity;

    @FXML
    private TextField textfield_total_price;

    @FXML
    private TextField textfield_shipping_price;

    @FXML
    private AnchorPane Imane_AnchorPane;

    @FXML
    private JFXButton jfx_button_attach;

    @FXML
    private Label label_status_attach;

    @FXML
    private Button button_add;

    @FXML
    private Button button_update;

    @FXML
    private Button button_Delete;

    @FXML
    private Button button_clear;

    @FXML
    private Label label_price_getter;

    @FXML
    private TableView<Module_Warehouse_tablle> table;

    @FXML
    private TableColumn<Module_Warehouse_tablle, Integer> col_id;

    @FXML
    private TableColumn<Module_Warehouse_tablle, ImageView> col_image;

    @FXML
    private TableColumn<Module_Warehouse_tablle, String> col_product;

    @FXML
    private TableColumn<Module_Warehouse_tablle, Integer> col_quantity;

    @FXML
    private TableColumn<Module_Warehouse_tablle, String> col_price;

    @FXML
    private TableColumn<Module_Warehouse_tablle, Float> col_total_price;

    @FXML
    private TableColumn<Module_Warehouse_tablle, Float> col_ship_price;

    @FXML
    private TableColumn<Module_Warehouse_tablle, String> col_comment;

    @FXML
    private TextField textfield_search;

    

    

    @FXML
    private JFXCheckBox checkbox_free;
    @FXML
    private ImageView image_of_the_product;
    @FXML
    private Label label_no_image;
    @FXML
    public AnchorPane warehouse_AnchorPane;
    
    
    @FXML
    private JFXButton ok_button;
    @FXML
    private JFXButton Cancel_button;
    @FXML
    private BorderPane Delete_BorderPane;
    
    
    
    public Controller_WareHouse_AnchorPane() {
    	super();
    	
    }
    
    
    
    ObservableList<Module_Warehouse_tablle> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle arg1) {
    	
    	label_status_attach.setText("");
    	initilize_table();
    	filtering();
    	clear_fields_and_image();
    	Platform.runLater(new Runnable() {
            @Override
            public void run() {
                warehouse_AnchorPane.requestFocus();
            }
        });
			
    	
    	
    }
    
    
    
    public void filtering() {
		FilteredList<Module_Warehouse_tablle> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        textfield_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Module_Warehouse_tablle -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(Module_Warehouse_tablle.getProduct_name()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                } else if (String.valueOf(Module_Warehouse_tablle.getTotal_price()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }  else if (String.valueOf(Module_Warehouse_tablle.getShip_price()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } 

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Module_Warehouse_tablle> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
	}
    
    private void clear_fields_and_image() {
    	Delete_BorderPane.setVisible(false);
    	textfield_product.setText("");
		textfield_quantity.setText("");
		textfield_shipping_price.setText("");
		textfield_total_price.setText("");
		Textarea_comment.setText("");
		image_of_the_product.setImage(null);
		textfield_search.setText("");
		label_no_image.setVisible(true);
		label_status_attach.setText("");
		label_price_getter.setText("");
		table.getSelectionModel().select(null);
		textfield_quantity.setDisable(false);
		textfield_shipping_price.setDisable(false);
		checkbox_free.setSelected(false);
		checkbox_free.setDisable(false);
		textfield_total_price.setDisable(false);
		textfield_product.setDisable(false);
    }
    private void initilize_table() {
    	image = null;
		
    	int var_con_checker=0;
    	Connection con=null;
    	try {
			con = DBConnection.connect();
			if(con == null) {
				var_con_checker=1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (var_con_checker==1) {
			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			
			
			try {
				con = DBConnection.connect();
				ResultSet rs = con.createStatement().executeQuery("select * from warehouse");
				while(rs.next()) {
					
					Image image = null;
					try {
						image = new Image(new FileInputStream(rs.getString(2)));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						Controller_Warning_missing_fields.this_is_phrase_changes = "Missing Images";
						try {
							warning_interface();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					
					ImageView imageview = new ImageView(image);
					
					data.add(new Module_Warehouse_tablle(rs.getInt(1), imageview,rs.getString(3), rs.getInt(4), rs.getString(5), rs.getFloat(6), rs.getFloat(7), rs.getString(8)));
				}

				col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
				col_image.setPrefWidth(151);
				col_image.setCellValueFactory(new PropertyValueFactory<>("image_in_table"));
				col_product.setCellValueFactory(new PropertyValueFactory<>("product_name"));
				col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
				col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
				col_total_price.setCellValueFactory(new PropertyValueFactory<>("total_price"));
				col_ship_price.setCellValueFactory(new PropertyValueFactory<>("ship_price"));
				col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
	    	

	    	table.setItems(data);
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
		}
    }
    
    
    @FXML
    void add_action(ActionEvent event) throws SQLException, FileNotFoundException {
    	if(textfield_product.getText().trim().isEmpty() || textfield_quantity.getText().trim().isEmpty() || Textarea_comment.getText().trim().isEmpty() || textfield_shipping_price.getText().trim().isEmpty() || textfield_total_price.getText().trim().isEmpty() ) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
			try {
				warning_interface();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(textfield_product.getText().trim().isEmpty()) {
				textfield_product.requestFocus();
			}else if(textfield_quantity.getText().trim().isEmpty()) {
				textfield_quantity.requestFocus();
			}else if(textfield_total_price.getText().trim().isEmpty()) {
				textfield_total_price.requestFocus();
			}else if(textfield_shipping_price.getText().trim().isEmpty()) {
				textfield_shipping_price.requestFocus();
			}else if(Textarea_comment.getText().trim().isEmpty()) {
				Textarea_comment.requestFocus();
			}
    	}else {
        	if(textfield_quantity.getText().trim().matches("^[0-9]*$") == false) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error... use numbers 1 to 9";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_quantity.requestFocus();
        	}else if(textfield_total_price.getText().trim().matches("^[0-9.]*$") == false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error use 1 to 9 and \".\", example : \"22.60\"";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_total_price.setStyle("-fx-text-fill:red");
    			textfield_total_price.requestFocus();
        	}else if(textfield_shipping_price.getText().trim().matches("^[0-9.]*$") == false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error use 1 to 9 and \".\", example : \"34.60\"";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_shipping_price.setStyle("-fx-text-fill:red");
    			textfield_shipping_price.requestFocus();
        	}else if(label_status_attach.getText().equals("Uploaded") ==false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please upload the image";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
        	}else {
        		
        		Module_Warehouse_tablle obj = new Module_Warehouse_tablle();
        		obj.setProduct_name(textfield_product.getText().toString());
        		obj.setQuantity(Integer.parseInt(textfield_quantity.getText().trim().toString()));
        		obj.setTotal_price(Float.parseFloat(textfield_total_price.getText().trim().toString()));
        		obj.setShip_price(Float.parseFloat(textfield_shipping_price.getText().trim().toString()));
        		obj.setComment(Textarea_comment.getText().toString());
        		obj.setImage(fileName);
        		Connection con = DBConnection.connect();
        		int status_of_sql =0;
        		if (con==null) {
        			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
					try {
						warning_interface();
					} catch (IOException e) {
						e.printStackTrace();
					}
        		}else {
        			if(selectedFile!=null) {
        				
        				String sql2_for_check_exist_prod = "SELECT product_n FROM warehouse WHERE product_n = ?";
        				
        				PreparedStatement ps2 =con.prepareStatement(sql2_for_check_exist_prod);
        				ps2.setString(1, textfield_product.getText());
        				ResultSet resultat =ps2.executeQuery();
        				String get_prod_name=null;
        				if(resultat.next()) {
        					get_prod_name =resultat.getString(1);
        				}
        				if(get_prod_name != null) {
        					if(get_prod_name.equals(textfield_product.getText())) {
        						Controller_Warning_missing_fields.this_is_phrase_changes = "product name is exist, try to change the name";
                    			try {
                    				warning_interface();
                    			} catch (IOException e) {
                    				e.printStackTrace();
                    			}
        					}else {
        						float price = (Float.parseFloat(textfield_total_price.getText().trim().toString())+Float.parseFloat(textfield_shipping_price.getText().trim().toString()))/Float.parseFloat(textfield_quantity.getText().trim().toString());
                    			String sql = "INSERT into warehouse(image,product_n,qtt,prc,t_prc,s_prc,comment) VALUES(?,?,?,?,?,?,?)";
                    			PreparedStatement ps  = con.prepareStatement(sql);
                    			ps.setString(1, obj.getImage());
                    			ps.setString(2, obj.getProduct_name());
                    			ps.setInt(3, obj.getQuantity());
                    			String price1 = String.format("%.2f", price);
                    			obj.setPrice(price1);
                    			ps.setString(4, obj.getPrice());
                    			ps.setFloat(5, obj.getTotal_price());
                    			ps.setFloat(6, obj.getShip_price());
                    			ps.setString(7, obj.getComment());
                    			status_of_sql = ps.executeUpdate();
                    			if(status_of_sql==0) {
                    				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Added to Databse";
                        			try {
                        				warning_interface();
                        			} catch (IOException e) {
                        				e.printStackTrace();
                        			}
                    			}else {
                    				Controller_Successfully_Page.this_is_phrase_changes = "the product is Successfully Added";
                    				try {
            							successfully_interface();
            						} catch (IOException e) {
            							e.printStackTrace();
            						}
                    				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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
                    				
                    				
                    				String capital =String.format("%.2f", capital_total);
                    				capital_static_var.setText(capital+" €");
                    				selectedFile=null;
                    				fileName=null;
                    				warehouse_AnchorPane.getChildren().clear();
                    				try {
        								warehouse_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
        							} catch (IOException e) {
        								// TODO Auto-generated catch block
        								e.printStackTrace();
        							}
                    				warehouse_AnchorPane.setLayoutX(0);
                    				warehouse_AnchorPane.setLayoutY(0);
                    				clear_fields_and_image();
                    				
                    			}
        					}
        					
        				}else {
        					float price = (Float.parseFloat(textfield_total_price.getText().trim().toString())+Float.parseFloat(textfield_shipping_price.getText().trim().toString()))/Float.parseFloat(textfield_quantity.getText().trim().toString());
                			String sql = "INSERT into warehouse(image,product_n,qtt,prc,t_prc,s_prc,comment) VALUES(?,?,?,?,?,?,?)";
                			PreparedStatement ps  = con.prepareStatement(sql);
                			ps.setString(1, obj.getImage());
                			ps.setString(2, obj.getProduct_name());
                			ps.setInt(3, obj.getQuantity());
                			String price1 = String.format("%.2f", price);
                			obj.setPrice(price1);
                			ps.setString(4, obj.getPrice());
                			ps.setFloat(5, obj.getTotal_price());
                			ps.setFloat(6, obj.getShip_price());
                			ps.setString(7, obj.getComment());
                			status_of_sql = ps.executeUpdate();
                			if(status_of_sql==0) {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Added to Databse";
                    			try {
                    				warning_interface();
                    			} catch (IOException e) {
                    				e.printStackTrace();
                    			}
                			}else {
                				Controller_Successfully_Page.this_is_phrase_changes = "the product is Successfully Added";
                				try {
        							successfully_interface();
        						} catch (IOException e) {
        							e.printStackTrace();
        						}
                				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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
                				
                				
                				String capital =String.format("%.2f", capital_total);
                				capital_static_var.setText(capital+" €");
                				selectedFile=null;
                				fileName=null;
                				warehouse_AnchorPane.getChildren().clear();
                				try {
    								warehouse_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
                				warehouse_AnchorPane.setLayoutX(0);
                				warehouse_AnchorPane.setLayoutY(0);
                				clear_fields_and_image();
                				
                			}
        				}
        				
        				
            			
        			}else {
        				Controller_Warning_missing_fields.this_is_phrase_changes = "you can't add same product, try \"clear\" button";
            			try {
            				warning_interface();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}

        			}
        			
        			
        		}
        		
        		
        	}
        	
        }

    }
    
    
    @FXML
    void Update_button_clicked(MouseEvent event) throws SQLException {
    	if(textfield_product.getText().trim().isEmpty() || textfield_quantity.getText().trim().isEmpty() || Textarea_comment.getText().trim().isEmpty() || textfield_shipping_price.getText().trim().isEmpty() || textfield_total_price.getText().trim().isEmpty() ) {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Missing some fields...";
			try {
				warning_interface();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(textfield_product.getText().trim().isEmpty()) {
				textfield_product.requestFocus();
			}else if(textfield_quantity.getText().trim().isEmpty()) {
				textfield_quantity.requestFocus();
			}else if(textfield_total_price.getText().trim().isEmpty()) {
				textfield_total_price.requestFocus();
			}else if(textfield_shipping_price.getText().trim().isEmpty()) {
				textfield_shipping_price.requestFocus();
			}else if(Textarea_comment.getText().trim().isEmpty()) {
				Textarea_comment.requestFocus();
			}
    	}else {
        	if(textfield_quantity.getText().trim().matches("^[0-9]*$") == false) {
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error... use numbers 1 to 9";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_quantity.requestFocus();
        	}else if(textfield_total_price.getText().trim().matches("^[0-9.]*$") == false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error use 1 to 9 and \".\", example : \"22.60\"";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_total_price.setStyle("-fx-text-fill:red");
    			textfield_total_price.requestFocus();
        	}else if(textfield_shipping_price.getText().trim().matches("^[0-9.]*$") == false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Error use 1 to 9 and \".\", example : \"34.60\"";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			textfield_shipping_price.setStyle("-fx-text-fill:red");
    			textfield_shipping_price.requestFocus();
        	}else if(label_status_attach.getText().equals("Uploaded") ==false){
        		Controller_Warning_missing_fields.this_is_phrase_changes = "Please upload the image";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
        	}else {
        		if(fileName==null) {
            		Connection con = DBConnection.connect();
            		int status_of_sql =0;
            		if (con==null) {
            			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
    					try {
    						warning_interface();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
            		}else {
            			if (table.getSelectionModel().getSelectedItem() != null) {
            				Module_Warehouse_tablle obj = new Module_Warehouse_tablle();
                    		obj.setProduct_name(textfield_product.getText().toString());
                    		obj.setQuantity(Integer.parseInt(textfield_quantity.getText().trim().toString()));
                    		obj.setTotal_price(Float.parseFloat(textfield_total_price.getText().trim().toString()));
                    		obj.setShip_price(Float.parseFloat(textfield_shipping_price.getText().trim().toString()));
                    		obj.setComment(Textarea_comment.getText().toString());
            				float price = (Float.parseFloat(textfield_total_price.getText().trim().toString())+Float.parseFloat(textfield_shipping_price.getText().trim().toString()))/Float.parseFloat(textfield_quantity.getText().trim().toString());
                			String sql ="UPDATE warehouse SET product_n=?, qtt=?, prc=?, t_prc=?, s_prc=?, comment=? WHERE id=?";
                			PreparedStatement ps  = con.prepareStatement(sql);
                			ps.setString(1, obj.getProduct_name());
                			ps.setInt(2, obj.getQuantity());
                			String price1 = String.format("%.2f", price);
                			obj.setPrice(price1);
                			ps.setString(3, obj.getPrice());
                			ps.setFloat(4, obj.getTotal_price());
                			ps.setFloat(5, obj.getShip_price());
                			ps.setString(6, obj.getComment());
                			ps.setInt(7, table.getSelectionModel().getSelectedItem().getId());
                			status_of_sql = ps.executeUpdate();
                			if(status_of_sql==0) {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Updated";
                    			try {
                    				warning_interface();
                    			} catch (IOException e) {
                    				e.printStackTrace();
                    			}
                			}else {
                				
                				Controller_Successfully_Page.this_is_phrase_changes = "the product is Successfully Updated";
                				try {
        							successfully_interface();
        						} catch (IOException e) {
        							e.printStackTrace();
        						}
                				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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
                				
                				
                				String capital =String.format("%.2f", capital_total);
                				
                				
                				capital_static_var.setText(capital+" €");
                				
                				warehouse_AnchorPane.getChildren().clear();
                				try {
    								warehouse_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
                				warehouse_AnchorPane.setLayoutX(0);
                				warehouse_AnchorPane.setLayoutY(0);
                				clear_fields_and_image();
                				
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
        		}else {
        			Connection con = DBConnection.connect();
            		int status_of_sql =0;
            		if (con==null) {
            			Controller_Warning_missing_fields.this_is_phrase_changes = "not connected, please connect to DB";
    					try {
    						warning_interface();
    					} catch (IOException e) {
    						e.printStackTrace();
    					}
            		}else {
            			if (table.getSelectionModel().getSelectedItem() != null) {
            				Module_Warehouse_tablle obj = new Module_Warehouse_tablle();
                    		obj.setProduct_name(textfield_product.getText().toString());
                    		obj.setQuantity(Integer.parseInt(textfield_quantity.getText().trim().toString()));
                    		obj.setTotal_price(Float.parseFloat(textfield_total_price.getText().trim().toString()));
                    		obj.setShip_price(Float.parseFloat(textfield_shipping_price.getText().trim().toString()));
                    		obj.setComment(Textarea_comment.getText().toString());
            				float price = (Float.parseFloat(textfield_total_price.getText().trim().toString())+Float.parseFloat(textfield_shipping_price.getText().trim().toString()))/Float.parseFloat(textfield_quantity.getText().trim().toString());
                			String sql ="UPDATE warehouse SET product_n=?, qtt=?, prc=?, t_prc=?, s_prc=?, comment=?, image=? WHERE id=?";
                			PreparedStatement ps  = con.prepareStatement(sql);
                			ps.setString(1, obj.getProduct_name());
                			ps.setInt(2, obj.getQuantity());
                			String price1 = String.format("%.2f", price);
                			obj.setPrice(price1);
                			ps.setString(3, obj.getPrice());
                			ps.setFloat(4, obj.getTotal_price());
                			ps.setFloat(5, obj.getShip_price());
                			ps.setString(6, obj.getComment());
                			obj.setImage(fileName);
                			ps.setString(7, obj.getImage());
                			ps.setInt(8, table.getSelectionModel().getSelectedItem().getId());
                			status_of_sql = ps.executeUpdate();
                			if(status_of_sql==0) {
                				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Updated";
                    			try {
                    				warning_interface();
                    			} catch (IOException e) {
                    				e.printStackTrace();
                    			}
                			}else {
                				Controller_Successfully_Page.this_is_phrase_changes = "the product is Successfully Updated";
                				try {
        							successfully_interface();
        						} catch (IOException e) {
        							e.printStackTrace();
        						}
                				sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
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

                				String capital =String.format("%.2f", capital_total);
                				capital_static_var.setText(capital+" €");
                				warehouse_AnchorPane.getChildren().clear();
                				try {
    								warehouse_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
    							} catch (IOException e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
                				warehouse_AnchorPane.setLayoutX(0);
                				warehouse_AnchorPane.setLayoutY(0);
                				clear_fields_and_image();
                				
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
        		}
        		
        		
        		
        	}
        	
        }
    	
    	
    	
    }
    
    public  int id_selected_product=0;
    public  int st=0;
    @FXML
    void delete_button_action(ActionEvent event) {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		st=0;
    		try {
    			Connection con = DBConnection.connect();
    			if(con ==null) {
    				Controller_Warning_missing_fields.this_is_phrase_changes = "Not Connected";
        			try {
        				warning_interface();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
    			}else {
        				id_selected_product= table.getSelectionModel().getSelectedItem().getId();
        				String path = getClass().getResource("/sound/error_sound.mp3").toString();
        		        Media media = new Media(path);
        		        MediaPlayer mp = new MediaPlayer(media);
        		        mp.play();
        				Delete_BorderPane.setVisible(true);
        				
    			}
    			
    		} catch (SQLException e) {
    			
    			e.printStackTrace();
    		}
    	}else {
    		Controller_Warning_missing_fields.this_is_phrase_changes = "Please select a product in the table above";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
		
    }
    
    
    Image image = null;
    String fileName = null;
    File selectedFile=null;
    @FXML
    void action_attach(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image with size 158*81");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.jpg"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
        image = null;
        if (selectedFile != null) {
        	String one = "\\";
        	String two = "\\\\";
            fileName = selectedFile.getAbsolutePath().toString();
            BufferedImage image1=null;
            try {
				image1 = ImageIO.read(selectedFile);
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
            int widhth = image1.getWidth();
            int height = image1.getHeight();
            if((widhth!= 158) || (height != 81)) {
            	Controller_Warning_missing_fields.this_is_phrase_changes = "Error... choose 158*81 size";
    			try {
    				warning_interface();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			image_of_the_product.setImage(null);
    			label_status_attach.setText("Error...");
            }else {
            	fileName = fileName.replace(one, two);
                try {
    				image = new Image(new FileInputStream(fileName));
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
    			}
                //Image image = new Image(fileName.toString());
                image_of_the_product.setImage(image);
                label_status_attach.setText("Uploaded");
                label_no_image.setVisible(false);
            }
            
            
        }

    }
    
    
    @FXML
    void table_clicked_on_mouse(MouseEvent event) {
    	if (table.getSelectionModel().getSelectedItem() != null) {
    		Delete_BorderPane.setVisible(false);
    		Module_Warehouse_tablle selected_product = table.getSelectionModel().getSelectedItem();
    		textfield_product.setText(selected_product.getProduct_name());
    		int qauntity_getter = selected_product.getQuantity();
    		String quantity_getter_string = String.valueOf(qauntity_getter);
    		textfield_quantity.setText(quantity_getter_string);
    		Float total_price_getter = selected_product.getTotal_price();
    		String total_price_getter_string = String.valueOf(total_price_getter);
    		textfield_total_price.setText(total_price_getter_string);
    		Float shipping_price_getter = selected_product.getShip_price();
    		String shipping_price_getter_string = String.valueOf(shipping_price_getter);
    		textfield_shipping_price.setText(shipping_price_getter_string);
    		label_price_getter.setText(selected_product.getPrice());
    		Textarea_comment.setText(selected_product.getComment());
    		label_no_image.setVisible(false);
    		textfield_product.setDisable(true);
    		image_of_the_product.setImage(selected_product.getImage_in_table().getImage());
    		label_status_attach.setText("Uploaded");
    		textfield_quantity.setDisable(true);
    		textfield_shipping_price.setDisable(true);
    		checkbox_free.setSelected(false);
    		checkbox_free.setDisable(true);
    		textfield_total_price.setDisable(true);
    		
    	}else {
    		textfield_quantity.setDisable(false);
    		textfield_shipping_price.setDisable(false);
    		checkbox_free.setSelected(false);
    		checkbox_free.setDisable(false);
    		textfield_total_price.setDisable(false);
    	}
    }
    
    @FXML
    void checkbox_action(ActionEvent event) {
    	if(checkbox_free.isSelected()) {
    		textfield_shipping_price.setDisable(true);
    		textfield_shipping_price.setText("0");
    		count=0;
        	if((textfield_quantity.getText().trim().isEmpty()==false) && (textfield_quantity.getText().trim().matches("^[0-9]*$"))&& textfield_total_price.getText().trim().isEmpty()==false) {
        		if(textfield_total_price.getText().trim().matches("^[0-9.]*$")) {
        			float var_get_price_float = Float.parseFloat(textfield_total_price.getText().trim().toString())/Float.parseFloat(textfield_quantity.getText().trim().toString());
					label_price_getter.setText(String.format("%.2f", var_get_price_float)+" euro");
        		}
        	}
    		
    		
    		
    	}else {
    		textfield_shipping_price.setDisable(false);
    		textfield_shipping_price.setText("");
    	}
    }
    
    
   
    
    int count=0;
    @FXML
    void typing_on_total_price(KeyEvent event) {
    	if(event.getCharacter().matches("^[0-9.]*$")&&textfield_total_price.getText().matches("^[0-9.]*$")) {
    		textfield_total_price.setStyle("-fx-text-fill:black");
    		
    	}else {
    		textfield_total_price.setStyle("-fx-text-fill:red");
    	}
    	if(checking_text_before_caretPosition && checking_text_after_caretPosition) {
    		textfield_total_price.setStyle("-fx-text-fill:black");
    		checking_text_before_caretPosition=false;
    		checking_text_after_caretPosition = false;
    	}
    	if(var_for_ctrl_v) {
    		if(textfield_total_price.getText().matches("^[0-9.]*$")) {
    			textfield_total_price.setStyle("-fx-text-fill:black");
    			var_for_ctrl_v=false;
        		
        	}else {
        		textfield_total_price.setStyle("-fx-text-fill:red");
        		var_for_ctrl_v=false;
        	}
    	}
    }
    
    
    
    @FXML
    void typing_on_shipping_price(KeyEvent event) {
    	if(event.getCharacter().matches("^[0-9.]*$")&&textfield_shipping_price.getText().matches("^[0-9.]*$")) {
    		textfield_shipping_price.setStyle("-fx-text-fill:black");
    		
    	}else {
    		textfield_shipping_price.setStyle("-fx-text-fill:red");
    	}
    	if(checking_text_before_caretPosition && checking_text_after_caretPosition) {
    		textfield_shipping_price.setStyle("-fx-text-fill:black");
    		checking_text_before_caretPosition=false;
    		checking_text_after_caretPosition = false;
    	}
    	if(var_for_ctrl_v) {
    		if(textfield_shipping_price.getText().matches("^[0-9.]*$")) {
    			textfield_shipping_price.setStyle("-fx-text-fill:black");
    			var_for_ctrl_v=false;
        		
        	}else {
        		textfield_shipping_price.setStyle("-fx-text-fill:red");
        		var_for_ctrl_v=false;
        	}
    	}
    }
    
    
    
    
    @FXML
    void typing_on_quantity(KeyEvent event) {
    	if(event.getCharacter().matches("^[0-9]*$")&&textfield_quantity.getText().matches("^[0-9]*$")) {
    		textfield_quantity.setStyle("-fx-text-fill:black");
    		
    	}else {
    		textfield_quantity.setStyle("-fx-text-fill:red");
    	}
    	if(checking_text_before_caretPosition && checking_text_after_caretPosition) {
    		textfield_quantity.setStyle("-fx-text-fill:black");
    		checking_text_before_caretPosition=false;
    		checking_text_after_caretPosition = false;
    	}
    	if(var_for_ctrl_v) {
    		if(textfield_quantity.getText().matches("^[0-9]*$")) {
    			textfield_quantity.setStyle("-fx-text-fill:black");
    			var_for_ctrl_v=false;
        		
        	}else {
        		textfield_quantity.setStyle("-fx-text-fill:red");
        		var_for_ctrl_v=false;
        	}
    	}
    }
    public Boolean checking_text_before_caretPosition = false;
    public Boolean checking_text_after_caretPosition = false;
    @FXML
    void pressing_on_quantity(KeyEvent event) {
    	if((event.getCode() == KeyCode.BACK_SPACE)){
    		String text_before_caretPosition="";
    		String text_before_caretPosition_before_the_last_char ="";
    		String text_after_caretPosition = "";
        	for(int  i = textfield_quantity.getCaretPosition();  i < textfield_quantity.getText().length(); i++) {
        		text_after_caretPosition = text_after_caretPosition+textfield_quantity.getText().charAt(i);
        	}
        	if(text_after_caretPosition.matches("^[0-9]*$")) {
        		checking_text_after_caretPosition=true;
        	}
    		for(int i=0;i<textfield_quantity.getCaretPosition();i++) {
    			text_before_caretPosition = text_before_caretPosition + textfield_quantity.getText().charAt(i);
    		}
    		for(int i=0;i<text_before_caretPosition.length()-1;i++) {
    			text_before_caretPosition_before_the_last_char = text_before_caretPosition_before_the_last_char + text_before_caretPosition.charAt(i);
    		}
    		
    		if(text_before_caretPosition_before_the_last_char.matches("^[0-9]*$")) {
    			checking_text_before_caretPosition=true;
    		}
    	}else if(event.isControlDown() && event.getCode() == KeyCode.V) {
    		var_for_ctrl_v=true;
    	}
    }
    
    @FXML
    void action_on_quantity(ActionEvent event) {
    	
    	
    }
    
    
    public Boolean var_for_ctrl_v =false;

    
    @FXML
    void pressed_on_total_price(KeyEvent event) {
    	if((event.getCode() == KeyCode.BACK_SPACE)){
    		String text_before_caretPosition="";
    		String text_before_caretPosition_before_the_last_char ="";
    		String text_after_caretPosition = "";
        	for(int  i = textfield_total_price.getCaretPosition();  i < textfield_total_price.getText().length(); i++) {
        		text_after_caretPosition = text_after_caretPosition+textfield_total_price.getText().charAt(i);
        	}
        	if(text_after_caretPosition.matches("^[0-9.]*$")) {
        		checking_text_after_caretPosition=true;
        	}
    		for(int i=0;i<textfield_total_price.getCaretPosition();i++) {
    			text_before_caretPosition = text_before_caretPosition + textfield_total_price.getText().charAt(i);
    		}
    		for(int i=0;i<text_before_caretPosition.length()-1;i++) {
    			text_before_caretPosition_before_the_last_char = text_before_caretPosition_before_the_last_char + text_before_caretPosition.charAt(i);
    		}
    		
    		if(text_before_caretPosition_before_the_last_char.matches("^[0-9.]*$")) {
    			checking_text_before_caretPosition=true;
    		}
    	}else if(event.isControlDown() && event.getCode() == KeyCode.V) {
    		var_for_ctrl_v=true;
    	}
    	
    }
    
    @FXML
    void pressing_on_shipping(KeyEvent event) {
    	if((event.getCode() == KeyCode.BACK_SPACE)){
    		String text_before_caretPosition="";
    		String text_before_caretPosition_before_the_last_char ="";
    		String text_after_caretPosition = "";
        	for(int  i = textfield_shipping_price.getCaretPosition();  i < textfield_shipping_price.getText().length(); i++) {
        		text_after_caretPosition = text_after_caretPosition+textfield_shipping_price.getText().charAt(i);
        	}
        	if(text_after_caretPosition.matches("^[0-9.]*$")) {
        		checking_text_after_caretPosition=true;
        	}
    		for(int i=0;i<textfield_shipping_price.getCaretPosition();i++) {
    			text_before_caretPosition = text_before_caretPosition + textfield_shipping_price.getText().charAt(i);
    		}
    		for(int i=0;i<text_before_caretPosition.length()-1;i++) {
    			text_before_caretPosition_before_the_last_char = text_before_caretPosition_before_the_last_char + text_before_caretPosition.charAt(i);
    		}
    		
    		if(text_before_caretPosition_before_the_last_char.matches("^[0-9.]*$")) {
    			checking_text_before_caretPosition=true;
    		}
    	}else if(event.isControlDown() && event.getCode() == KeyCode.V) {
    		var_for_ctrl_v=true;
    	}
	
    }
    
    
    @FXML
    void action_total_price(ActionEvent event) {
    	
    }
    
    @FXML
    void Clear_all_fields_button(ActionEvent event) {
    	clear_fields_and_image();
    }
    
    @FXML
    void Clicked_on_warehouse_AnchorPane(MouseEvent event) {
    	warehouse_AnchorPane.requestFocus();
    	Delete_BorderPane.setVisible(false);
    	table.getSelectionModel().select(null);
    	textfield_quantity.setDisable(false);
    	textfield_shipping_price.setDisable(false);
    	checkbox_free.setSelected(false);
		checkbox_free.setDisable(false);
		textfield_total_price.setDisable(false);
		textfield_product.setDisable(false);
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
    void action_Cancel_button(ActionEvent event) {
    	Delete_BorderPane.setVisible(false);
    	warehouse_AnchorPane.requestFocus();

    }
    
    @FXML
    void action_ok_button(ActionEvent event) throws SQLException {
    	Connection con = DBConnection.connect();
    	PreparedStatement stat;
		String sql = "DELETE FROM warehouse WHERE id=?";
		
		stat = con.prepareStatement(sql);
		stat.setInt(1, table.getSelectionModel().getSelectedItem().getId());
		st = stat.executeUpdate();
		if(st!=0) {
			Controller_Successfully_Page.this_is_phrase_changes = "the product is Successfully Deleted";
			try {
				successfully_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sql = "SELECT qtt, prc FROM warehouse WHERE qtt IS NOT NULL AND prc IS NOT NULL";
			PreparedStatement stat2 ;
			stat2 = con.prepareStatement(sql);
			ResultSet rs = stat2.executeQuery();
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
			Delete_BorderPane.setVisible(false);
			warehouse_AnchorPane.getChildren().clear();
			try {
				warehouse_AnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/application/WasreHouse_AnchorPane.fxml")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			warehouse_AnchorPane.setLayoutX(0);
			warehouse_AnchorPane.setLayoutY(0);
			clear_fields_and_image();
		}else {
			Controller_Warning_missing_fields.this_is_phrase_changes = "Error.. the product is not deleted";
			try {
				warning_interface();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    }
    
    

}
