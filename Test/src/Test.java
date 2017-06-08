import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Test {

	public static void main(String[] args) {
		
	/*	String re = "tracy@hkicpa.org;marianna@hkicpa.org.hk;";
		System.out.println(left(ArrayToString(re.split(";")),4000));*/
		
		String[] str = new String[]{"a","b"};
		
		List list = Arrays.asList(str);
		
		List list2 = new ArrayList<>(list);
		
		List list3 = Collections.synchronizedList(list2);
	    
		list3.add("c");
		
		str[0] = "ko";
		
		System.out.println(list);
		
		System.out.println(list2);
		
		System.out.println(list3.get(0));
	}

	  public static String ArrayToString(String[] strings) {
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
	  public static String left(String str, int len) {
	        if (str == null) {
	            return null;
	        }
	        if (len < 0) {
	            return "";
	        }
	        if (str.length() <= len) {
	            return str;
	        }
	        return str.substring(0, len);
	    }
}
