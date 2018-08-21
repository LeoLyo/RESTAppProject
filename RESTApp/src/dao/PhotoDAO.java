package dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import beans.Photo;

public class PhotoDAO {

	
	private Map<UUID, Photo> photos = new HashMap<>();

	
	public PhotoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Map<UUID, Photo> getPhotos() {
		return photos;
	}



	public void setPhotos(Map<UUID, Photo> photos) {
		this.photos = photos;
	}
	
	
	public Photo find(UUID id, String byteArray) {
		if (!photos.containsKey(id)) {
			return null;
		}
		Photo photo = photos.get(id);
		if (!photo.getByteArray().equals(byteArray)) {
			return null;
		}
		return photo;
	}
	
	public boolean remove(UUID id) {
		if(photos.containsKey(id)) {
			photos.remove(id);
			return true;
		} else {
			return false;
		}
	}
	
}
	