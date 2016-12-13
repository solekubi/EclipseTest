package max;

public class User {
	
	private String name;
	
	private String sex;
	
	private String address;
	
	private Integer age;

	public User() {}

	public User(String name, String sex, String address, Integer age) {
		super();
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
