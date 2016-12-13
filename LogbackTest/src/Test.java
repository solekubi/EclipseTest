import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

	public static final Logger logger = LoggerFactory.getLogger(Test.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.trace("======trace");  
		logger.debug("======debug");  
		logger.info("======info");  
		logger.warn("======warn");  
		logger.error("======error");  
		System.out.println(Test.class.getClassLoader().getResource("logback.xml"));
	}

}
