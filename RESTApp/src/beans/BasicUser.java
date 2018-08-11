package beans;

import java.util.ArrayList;

public class BasicUser extends User {
	private String email;
	private String country;
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	

	public BasicUser() {
		super();
	}
	
	public BasicUser(String username, String password, String email, String country) {
		super(username,password);
		this.email = email;
		this.country = country;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	
}
