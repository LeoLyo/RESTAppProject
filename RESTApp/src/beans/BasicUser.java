package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BasicUser extends User {
	
	private String email;
	private String country;
	private Cart cart;
	
	private ArrayList<Photo> test = new ArrayList<Photo>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	private Map<String, Cart> history = new HashMap<>();


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

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}

	public boolean removePhoto(String id) {
		for(int i=0;i<photos.size();i++) {
			if(photos.get(i).getId().equals(id)) {
				photos.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean owns(Photo photo) {
		for(int i=0;i<photos.size();i++) {
			if(photos.get(i).getId().equals(photo.getId())) {
				return true;
			}
		}		return false;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Map<String, Cart> getHistory() {
		return history;
	}

	public void setHistory(Map<String, Cart> history) {
		this.history = history;
	}
	
	public void addToHistory(String date, Cart cart) {
		this.history.put(date, cart);
	}
	
	public ArrayList<Photo> getTest() {
		return test;
	}

	public void setTest(ArrayList<Photo> test) {
		this.test = test;
	}

	public void addPictureToTest(Photo photo) {
		test.add(photo);
		
	}
}
