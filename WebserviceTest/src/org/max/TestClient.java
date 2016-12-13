/**
 * 
 */
package org.max;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * @author user
 *
 */
public class TestClient {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL("http://localhost:8969/max/?wsdl");
		QName qName=new QName("http://max.org/", "MyServiceImplService");
		 Service service=Service.create(url, qName);
		 MyService my=service.getPort(MyService.class);
		 System.out.println(my.add(1, 2));
	}

}
