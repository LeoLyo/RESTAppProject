package beans;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cart {
	
	private Map<UUID, Photo> photos = new HashMap<>();
	private String dateID;
	
	public Cart() {
		super();
	}
	
	public Cart(Map<UUID, Photo> photos, String dateID) {
		super();
		this.photos = photos;
		this.setDateID(dateID);
	}

	public Map<UUID, Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Map<UUID, Photo> photos) {
		this.photos = photos;
	}

	public String getDateID() {
		return dateID;
	}

	public void setDateID(String dateID) {
		this.dateID = dateID;
	}
	
	
	
	
}
