package application;

public class Module_Return_page {
	private int id;
	private String product_name;
	private String refund_by;
	private float price;
	private String date;
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
	public String getRefund_by() {
		return refund_by;
	}
	public void setRefund_by(String refund_by) {
		this.refund_by = refund_by;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Module_Return_page(int id, String product_name, String refund_by, float price, String date) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.refund_by = refund_by;
		this.price = price;
		this.date = date;
	}
	
	public Module_Return_page() {
		
	}
	

}
