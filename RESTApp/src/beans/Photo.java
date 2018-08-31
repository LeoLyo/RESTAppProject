package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Photo {
	
	private String id;
	private String name;
	private Double price;
	private String originalWidth;
	private String originalHeight;
	private String byteArray;
	private String owner;
	private String category;
	private String description;
	private int grade;
	private int purchases = 0;
	private boolean approved = false;
	
	private HashMap<String, Integer> grades = new HashMap<String,Integer>();
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	
	public Photo() {
		super();
	}
	
	
	
	
	public boolean isApproved() {
		return approved;
	}




	public void setApproved(boolean approved) {
		this.approved = approved;
	}




	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getOriginalWidth() {
		return originalWidth;
	}
	public void setOriginalWidth(String originalWidth) {
		this.originalWidth = originalWidth;
	}
	public String getOriginalHeight() {
		return originalHeight;
	}
	public void setOriginalHeight(String originalHeight) {
		this.originalHeight = originalHeight;
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

	public String getByteArray() {
		return byteArray;
	}

	public void setByteArray(String byteArray) {
		this.byteArray = byteArray;
	}

	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}

	
	
}
