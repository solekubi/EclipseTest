/**
 * 
 */
package org.max;

import javax.jws.WebService;

/**
 * @author user
 *
 */
@WebService(endpointInterface="org.max.MyService",name="max")
public class MyServiceImpl implements MyService {

	@Override
	public int add(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println(x+"+"+y+"="+(x+y));
		return x+y;
	}

	@Override
	public int dec(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println(x+"-"+y+"="+(x-y));
		return x-y;
	}

}
