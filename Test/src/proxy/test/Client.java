package proxy.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
          Subject rSubject = new RealSubject();
          
          InvocationHandler iHandler = new DymaicProxy(rSubject);
          
          Subject subject = (Subject)Proxy.newProxyInstance(iHandler.getClass().getClassLoader(), 
        		  rSubject.getClass().getInterfaces(), iHandler);
          
          System.out.println(subject.getClass().getName());
          
          subject.rent();
          
          subject.hello("world");
	}

}
