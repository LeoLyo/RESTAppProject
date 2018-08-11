package beans;

public class Comment {
	
	private String username;
	private String value;
	
	public Comment() {
		super();
	}
	
	public Comment(String username, String value) {
		super();
		this.username = username;
		this.value = value;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
