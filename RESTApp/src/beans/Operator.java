package beans;

public class Operator extends User {
	
	private boolean firstTime;
	
	public Operator() {
		super();
		setActivated(true);
		setuType(2);
		firstTime=true;
	}
	
	public Operator(String username, String password) {
		super(username,password);
		setActivated(true);
		setuType(2);
		firstTime=true;
	}

	
	public boolean isFirstTime() {
		return firstTime;
	}

	public void setFirstTime(boolean firstTime) {
		this.firstTime = firstTime;
	}
	
	
	

}
