package dao;

import java.util.HashMap;
import java.util.Map;

import beans.Photo;

public class PhotoDAO {

	
	private Map<String, Photo> photos = new HashMap<>();

	
	public PhotoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Map<String, Photo> getPhotos() {
		return photos;
	}



	public void setPhotos(Map<String, Photo> photos) {
		this.photos = photos;
	}
	
	
	public Photo find(String id, String byteArray) {
		if (!photos.containsKey(id)) {
			return null;
		}
		Photo photo = photos.get(id);
		if (!photo.getByteArray().equals(byteArray)) {
			return null;
		}
		return photo;
	}
	
	public boolean remove(String id) {
		if(photos.containsKey(id)) {
			photos.remove(id);
			return true;
		} else {
			return false;
		}
	}
	
}
	