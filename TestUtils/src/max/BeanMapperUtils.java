package max;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class BeanMapperUtils {
	
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /** 
     * @Description: 将目标对象转换为指定对象，相同属性名进行属性值复制 
     *  
     * @Title: EntityObjectConverter.java 
     * @Copyright: Copyright (c) 2013 
     * 
     * @author Comsys-LZP 
     * @date 2013-11-4 下午02:32:34 
     * @version V2.0 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getObject(Object source,Class<T> cls){  
        if (source==null) {  
            return null;  
        }  
        return (T) dozer.map(source, cls);  
    }
    
    /** 
     * @Description: 两个对象之间相同属性名的属性值复制 
     *  
     * @Title: EntityObjectConverter.java 
     * @Copyright: Copyright (c) 2013 
     * 
     * @author Comsys-LZP 
     * @date 2013-11-4 下午02:33:56 
     * @version V2.0 
     */  
    public static void setObject(Object source,Object target){  
        dozer.map(source, target);  
    }  

    /** 
     * @Description: 对象集合中对象相同属性名的属性值复制 
     *  
     * @Title: EntityObjectConverter.java 
     *  
     * @Copyright: Copyright (c) 2013 
     * @author Comsys-LZP 
     * @date 2013-11-4 下午02:34:26 
     * @version V2.0 
     */  
    @SuppressWarnings("unchecked")  
    public static List getList(List source,Class cls){  
        List listTarget = new ArrayList();  
        if(source != null){  
            for (Object object : source) {  
                Object objTarget = getObject(object, cls);  
                listTarget.add(objTarget);  
            }  
        }  
        return listTarget;  
    }  
    
    @SuppressWarnings("unchecked")  
    public static Object getObjectBySource(List source,Class cls) throws Exception, IllegalAccessException{  
    	Object destinat = cls.newInstance();
    	if(source != null){  
    		for (Object object : source) {  
    			dozer.map(object, destinat);   
    		}  
    	}  
    	return destinat;  
    }  
    public static void main(String[] args) throws Exception, IllegalAccessException {
		
		   User user = new User("xiaoming", "nan", "1234", 45);
		   Exam exam = new Exam("haoci",23, new BigDecimal(56));
		   /*UserVo vo = new UserVo();
	       setObject(user, vo);
	       setObject(exam, vo);
	        System.out.println(vo.toString());
	       */
		   List<Object> objects = new ArrayList<>();
		   objects.add(user);
		   objects.add(exam);
//		   List list = getList(objects, UserVo.class);
		   UserVo vo = (UserVo)getObjectBySource(objects, UserVo.class);
		   System.out.println(vo.toString());
		   
		  
		   Method[] methods = vo.getClass().getMethods();
			Field[] fields=vo.getClass().getDeclaredFields();
			System.out.println(fields[0].getAnnotations().length);
	}
}
