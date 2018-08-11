package beans;

public class Card {
	private String number;
	private String pin;
	
	public Card() {
		super();
	}
	public Card(String number, String pin) {
		super();
		this.number = number;
		this.pin = pin;
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
	
	
}
