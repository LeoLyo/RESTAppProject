package beans;

public class Card {
	private String number;
	private String pin;
	private int money;
	
	public Card() {
		super();
		money=0;
	}
	public Card(String number, String pin) {
		super();
		this.number = number;
		this.pin = pin;
		money=0;
	}
	
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public void addMoney(int transaction) {
		money+=transaction;
		
	}
	
	
}
