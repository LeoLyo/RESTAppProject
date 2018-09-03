package beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BasicUser extends User {
	
	private String email;
	private String country;
	private Cart cart;
	private boolean blocked;
	private String wTimer;
	private String dTimer;
	private int dimage;
	private int wimage;
	
	private ArrayList<Photo> test = new ArrayList<Photo>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	private Map<String, Cart> history = new HashMap<>();


	public BasicUser() {
		super();
		blocked=false;
		dimage=0;
		wimage=0;
		wTimer="";
		dTimer="";
		cart= new Cart();
	}
	
	public BasicUser(String username, String password, String email, String country) {
		super(username,password);
		this.email = email;
		this.country = country;
		blocked=false;
		dimage=0;
		wimage=0;
		wTimer="";
		dTimer="";
		cart= new Cart();

	}

	
	
	public void incrementDimage() {
		dimage++;
	}
	public void incrementWimage() {
		wimage++;
	}

	public int getDimage() {
		return dimage;
	}

	public void setDimage(int dimage) {
		this.dimage = dimage;
	}

	public int getWimage() {
		return wimage;
	}

	public void setWimage(int wimage) {
		this.wimage = wimage;
	}

	public String getwTimer() {
		return wTimer;
	}

	public void setwTimer(String wTimer) {
		this.wTimer = wTimer;
	}

	public String getdTimer() {
		return dTimer;
	}

	public void setdTimer(String dTimer) {
		this.dTimer = dTimer;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
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
	public void addCard(Card card) {
		cards.add(card);
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
	public void addPhotographToAuction(Photo photo) {
		photos.add(photo);
		photo.setAuthor(getUsername());
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formatedDateTime = now.format(formatter);
		photo.setDateOfAuctioning(formatedDateTime);
		String uid = photo.getName()+"Q"+photo.getAuthor()+"Q"+formatedDateTime;
		photo.setId(uid);
	}

	public void approvePhoto(String name) {
		for(int i=0;i<photos.size();i++) {
			if(photos.get(i).getName().equals(name)) {
				photos.get(i).setApproved(true);
				photos.get(i).setlPrice(photos.get(i).getResolutions().get(0).getPrice());
				//System.out.println("Lowest price of res: " + photos.get(i).getResolutions().get(0).getPrice());
				System.out.println("Photo " + name + " has been approved, Hurray! ^_^");
				break;
			}
		}
		
	}
	public void receiveIncome(int transaction) {
		cards.get(0).addMoney(transaction);
	}
}
