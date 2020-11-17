/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;

/**
 * 
 *
 * @author pineaultr
 * @version 1.0.0 2020-11-17 Initial implementation
 *
 */
public class Player implements Runnable {
	private BufferedReader in;
	private DataOutputStream out;
	private User user;

	/**
	 * @param socket
	 */
	Player(Socket socket) {
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			String name = this.in.readLine();
			int id = ServerData.totalPlayerCount;
			ServerData.totalPlayerCount++;
			this.user = new User(name, id);
			this.out.writeBytes("connected\r\n");

			while (this.user.isActive()) {
				String message = this.in.readLine();				
				String command = message.split(":")[0]; 
				String data = message.split(":")[1];
				// handle command with proper method
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* commands: 
	 * 		menu: gamemode selection, new lobby, get lobbies, join lobby, exit game.
			game: tile press, progress updates, stop playing
	 */
	
	/* quit logic
	this.user.logout();
	this.out.writeBytes(message + "\r\n"); // tell receive thread to stop listening
	ServerData.players.remove(this);
	this.in.close();
	this.out.close();
	*/
}
// end class PlayerThread