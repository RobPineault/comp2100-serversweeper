/**
 * 
 */
package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 *
 * @author pineaultr
 * @version 1.0.0 2020-11-15 Initial implementation
 *
 */
public class ClientSender extends Thread {
	private DataOutputStream out;
	private boolean active;
	private int x;
	private int y;
	private boolean process;
	/**
	 * @param dos
	 */
	ClientSender(DataOutputStream dos) {
		this.out = dos;
		this.active = true;
	}
	public void handleClick(int x, int y) {
		this.x = x;
		this.y = y;
		this.process = true;
	}
	@Override
	public void run() {
		System.out.println("Welcome to ServerSweeper!");
		
		while (this.active) {
			try {
				while(process) {
					out.writeBytes(x + "\r\n");
					out.writeBytes(y + "\r\n");
					process = false;
				}
				//if (message.equals("{quit}")) {
					//this.s.close();
					//this.active = false;
				//}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
}
	// end class ClientSender