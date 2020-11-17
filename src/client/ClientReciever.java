/**
 * 
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 
 *
 * @author pineaultr
 * @version 1.0.0 2020-11-15 Initial implementation
 *
 */
public class ClientReciever extends Thread {
	private BufferedReader in;
	//private boolean active;

	/**
	 * @param br
	 */
	ClientReciever(BufferedReader br) {
		this.in = br;
		//this.active = true;
	}
	@Override
	public void run() {
		while (true) {
			try {
				String tileValue = this.in.readLine();
				
				//if (message.equals("{quit}"))
					//this.active = false;
				

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
	// end class ClientReciever