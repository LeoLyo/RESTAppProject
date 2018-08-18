package beans;

public class Admin extends User {

	public Admin(String string, String string2) {
		super(string,string2);
		this.setuType(3);
		this.setActivated(true);
	}

}
