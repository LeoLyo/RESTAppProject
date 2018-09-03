package beans;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
	
	private Map<String, Photo> photos = new HashMap<>();
	private String dateID;
	
	public Cart() {
		super();
	}
	
	public Cart(Map<String, Photo> photos, String dateID) {
		super();
		this.photos = photos;
		this.setDateID(dateID);
	}

	public Map<String, Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<String, Photo> photos) {
		this.photos = photos;
	}

	public String getDateID() {
		return dateID;
	}

	public void setDateID(String dateID) {
		this.dateID = dateID;
	}
	
	
	
	
}
