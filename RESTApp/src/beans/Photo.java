package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Photo {
	
	private String id;
	private String name;
	private String originalWidth;
	private String originalHeight;
	private String newWidth;
	private String newHeight;
	private String byteArray;
	private String author;
	private String category;
	private String description;
	private String location;
	private String price;
	private String dateOfAuctioning;
	private String lPrice;
	private int noOfSales;
	private int grade;
	private int purchases = 0;
	private boolean approved = false;
	private boolean officialWare = false;
	
	private HashMap<String, Integer> grades = new HashMap<String,Integer>();
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<Resolution> resolutions = new ArrayList<Resolution>();
	
	public Photo() {
		super();
	}

	
	
	
	public String getNewWidth() {
		return newWidth;
	}
	public void setNewWidth(String newWidth) {
		this.newWidth = newWidth;
	}
	public String getNewHeight() {
		return newHeight;
	}
	public void setNewHeight(String newHeight) {
		this.newHeight = newHeight;
	}
	public boolean isOfficialWare() {
		return officialWare;
	}
	public void setOfficialWare(boolean officialWare) {
		this.officialWare = officialWare;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getlPrice() {
		return lPrice;
	}
	public void setlPrice(String lPrice) {
		this.lPrice = lPrice;
	}
	public int getNoOfSales() {
		return noOfSales;
	}
	public void setNoOfSales(int noOfSales) {
		this.noOfSales = noOfSales;
	}
	public String getDateOfAuctioning() {
		return dateOfAuctioning;
	}
	public void setDateOfAuctioning(String dateOfAuctioning) {
		this.dateOfAuctioning = dateOfAuctioning;
	}
	public ArrayList<Resolution> getResolutions() {
		return resolutions;
	}
	public void setResolutions(ArrayList<Resolution> resolutions) {
		this.resolutions = resolutions;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
