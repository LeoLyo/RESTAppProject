package beans;

public class Firm extends User{
	
	private String name;
	private String location;
	private String pib;
	
	
	
	public Firm() {
		super();
	}
	
	
	public Firm(String username, String password, String name, String location, String pib) {
		super(username,password);
		this.name=name;
		this.location=location;
		this.pib=pib;
	}



	public String getLocation() {
		return location;
	}





	public void setLocation(String location) {
		this.location = location;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getPib() {
		return pib;
	}





	public void setPib(String pib) {
		this.pib = pib;
	}

}
