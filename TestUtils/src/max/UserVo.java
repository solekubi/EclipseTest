package max;

import java.math.BigDecimal;

public class UserVo {
	
	private String name;
	
	private String sex;
	
	private String address;
	
	private Integer age;
	
	private String  examName;
	
	private Integer level ;
	
	private BigDecimal goal ;
	
	public UserVo(){}

	public UserVo(String name, String sex, String address, Integer age, String examName, Integer level,
			BigDecimal goal) {
		super();
		this.name = name;
		this.sex = sex;
		this.address = address;
		this.age = age;
		this.examName = examName;
		this.level = level;
		this.goal = goal;
	}





	public String getExamName() {
		return examName;
	}





	public void setExamName(String examName) {
		this.examName = examName;
	}





	public BigDecimal getGoal() {
		return goal;
	}

	public void setGoal(BigDecimal goal) {
		this.goal = goal;
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "UserVo [name=" + name + ", sex=" + sex + ", address=" + address + ", age=" + age + ", examName="
				+ examName + ", level=" + level + ", goal=" + goal + "]";
	}

	
	
	
}

