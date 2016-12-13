package max;

import java.math.BigDecimal;
import java.security.KeyStore.PrivateKeyEntry;

public class Exam {

	private String examName;
	
	private Integer level ;
	
	private BigDecimal goal ;

	
	
	public Exam() {
	
	}

	public Exam(String examName, Integer level, BigDecimal goal) {
		super();
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public BigDecimal getGoal() {
		return goal;
	}

	public void setGoal(BigDecimal goal) {
		this.goal = goal;
	}

	@Override
	public String toString() {
		return "Exam [examName=" + examName + ", level=" + level + ", goal=" + goal + "]";
	}
	
	

}
