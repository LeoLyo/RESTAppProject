package beans;

public class Resolution {
	private String name;
	private String height;
	private String width;
	
	
	
	
	public Resolution(String name, String height, String width) {
		super();
		this.name = name;
		this.height = height;
		this.width = width;
	}


	public Resolution() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
	

}
