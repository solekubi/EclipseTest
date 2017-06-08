package max;

public class Test {

	public static void main(String[] args) {
		
		   /*User user = new User("xiaoming", "nan", "1234", 45);
	       UserVo  vo  = (UserVo)BeanMapperUtils.getObject(user, UserVo.class);
	       System.out.println(vo.toString());*/
		String re = "tommy.chan@dmahk.com;admin@dmahk.com;jeffrey.kwok@dmahk.com";
		System.out.println(ArrayToString(re.split(";")));
	       
	}

	  public static  String ArrayToString(String[] strings) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < strings.length; i++) {
	            if (i == 0) {
	                sb.append(strings[i]);
	            } else {
	                sb.append(";" + strings[i]);
	            }
	        }
	        return sb.toString();
	    }
}
