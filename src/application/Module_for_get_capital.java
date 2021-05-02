package application;

public class Module_for_get_capital {
	private int quantity;
	private float price;
	public Module_for_get_capital(int quantity, float price) {
		super();
		this.quantity = quantity;
		this.price = price;
	}
	public Module_for_get_capital() {
		
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

}
