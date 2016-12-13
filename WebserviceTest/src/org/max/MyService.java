/**
 * 
 */
package org.max;

import javax.jws.WebService;

/**
 * @author user
 *
 */
@WebService
public interface MyService {
	public int add(int x,int y);
	public int dec(int x,int y);
}
