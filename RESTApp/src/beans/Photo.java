package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Photo {
	
	private UUID id;
	private String name;
	private Double price;
	private String resolution;
	private String location;
	private String owner;
	private String category;
	private String description;
	private int purchases = 0;
	private HashMap<String, Integer> grades = new HashMap<String,Integer>();
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	
	public Photo() {
		super();
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPurchases() {
		return purchases;
	}
	public void setPurchases(int purchases) {
		this.purchases = purchases;
	}
	public HashMap<String, Integer> getGrades() {
		return grades;
	}
	public void setGrades(HashMap<String, Integer> grades) {
		this.grades = grades;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	
	
}
