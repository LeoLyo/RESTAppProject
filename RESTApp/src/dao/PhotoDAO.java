package dao;

import java.util.ArrayList;

import beans.Photo;

public class PhotoDAO {

	
	private ArrayList<Photo> photos = new ArrayList<Photo>();
	
	public PhotoDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
	
}
	