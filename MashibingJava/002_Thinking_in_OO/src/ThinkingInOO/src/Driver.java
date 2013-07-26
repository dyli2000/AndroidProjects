
public class Driver {
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void drive(Vihecle v) {
		v.go(new Address("¶«±±"));
	}
	
	
	
	public void drive(Car c, Address dest) {
		c.go(dest);
	}
}
