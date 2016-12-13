package org.max;

import javax.xml.ws.Endpoint;

public class ServicePublic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Endpoint.publish("http://localhost:8969/max/", new MyServiceImpl());
	}

}
