package application;

import javafx.scene.image.ImageView;

public class Module_Warehouse_tablle {
	private int id;
	private String product_name;
	private int quantity;
	private float total_price;
	private String price;
	private float ship_price;
	private String comment;
	private String image;
	private ImageView image_in_table;
	public ImageView getImage_in_table() {
		return image_in_table;
	}
	public void setImage_in_table(ImageView image_in_table) {
		this.image_in_table = image_in_table;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getTotal_price() {
		return total_price;
	}
	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public float getShip_price() {
		return ship_price;
	}
	public void setShip_price(float ship_price) {
		this.ship_price = ship_price;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Module_Warehouse_tablle(int id,ImageView image_in_table,String product_name, int quantity, String price, float total_price,
			float ship_price, String comment) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.quantity = quantity;
		this.total_price = total_price;
		this.price = price;
		this.ship_price = ship_price;
		this.comment = comment;
		this.image_in_table=image_in_table;
	}
	public Module_Warehouse_tablle() {
		
	}
	

}
