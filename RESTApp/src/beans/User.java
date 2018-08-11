package beans;

public class User {
	private String username;
	private String password;
	private int uType = 0;
	private boolean deleted = false;

	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public User() {
		super();
	}
	

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getuType() {
		return uType;
	}

	public void setuType(int uType) {
		this.uType = uType;
	}

	

	
}
