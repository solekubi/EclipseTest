package max;

public class Test {

	public static void main(String[] args) {
		
		   User user = new User("xiaoming", "nan", "1234", 45);
	       UserVo  vo  = (UserVo)BeanMapperUtils.getObject(user, UserVo.class);
	       System.out.println(vo.toString());
	}

}
