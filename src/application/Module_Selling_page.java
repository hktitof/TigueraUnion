package application;

public class Module_Selling_page {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String product_name;
	private float price;
	private float ship_price;
	private String client_info;
	private String platform_name;
	private String date;
	public Module_Selling_page(Integer id,String product_name, float price, float ship_price,String platform_name ,String date, String client_info) {
		super();
		this.product_name = product_name;
		this.price = price;
		this.ship_price = ship_price;
		this.client_info = client_info;
		this.platform_name = platform_name;
		this.date = date;
		this.id=id;
	}
	public Module_Selling_page() {
		
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getShip_price() {
		return ship_price;
	}
	public void setShip_price(float ship_price) {
		this.ship_price = ship_price;
	}
	public String getClient_info() {
		return client_info;
	}
	public void setClient_info(String client_info) {
		this.client_info = client_info;
	}
	public String getPlatform_name() {
		return platform_name;
	}
	public void setPlatform_name(String platform_name) {
		this.platform_name = platform_name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
