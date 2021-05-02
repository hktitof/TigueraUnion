package application;

public class Module_Advertising_ads_table {
	private int id;
	private String status;
	private String product_name;
	private float price;
	private String platform;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public int getId() {
		return id;
	}
	public Module_Advertising_ads_table() {
		super();
		
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Module_Advertising_ads_table(int id, String status, String product_name, float price,String platform) {
		super();
		this.id = id;
		this.status = status;
		this.product_name = product_name;
		this.price = price;
		this.platform=platform;
	}
	

}
