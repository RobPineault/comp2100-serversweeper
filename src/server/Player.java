/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

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
	private Minesweeper activeGame = null;

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
			int id = ServerData.totalPlayerCount + 1;
			ServerData.totalPlayerCount++;
			this.user = new User(name, id);
			this.out.writeBytes("connected\r\n");

			while (this.user.isActive()) {
				String message = this.in.readLine();
				String command = message.split(":")[0];
				String data = message.split(":")[1];
				if (!user.isPlaying()) {
					// menu:
					// gamemode selection, new lobby, get lobbies, join lobby, exit
					switch (command) {
					case "practice":
						startPractice();
						break;
					case "public":
						// joinPublic();
						break;
					case "new private":
						// newPrivateLobby();
						break;
					case "get private":
						// getPrivateLobbies();
						break;
					case "join private":
						// joinPrivateLobby();
						break;
					case "exit":
						user.logout();
						break;
					}
				} else {
					// game:
					// tile press, stop playing

					switch (command) {
					case "quit":
						user.stopPlaying();
						break;
					case "progress":
						user.stopPlaying();
						break;
					default:
						handleTilePress(data);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleTilePress(String tileCoordinates) {
		try {
			String[] nums = tileCoordinates.split(" ");
			int numX = Integer.parseInt(nums[0]);
			int numY = Integer.parseInt(nums[1]);
			System.out.println("RECEIVED: " + numX + "x" + numY + "y");
			ArrayList<DeepBomb> tiles = activeGame.getTiles(numX, numY);

			for (DeepBomb tile : tiles) {
				this.out.writeBytes("location:" + tile.x + " " + tile.y);
				this.out.writeBytes("value:" + tile.prox);
			}
			if(this.activeGame.isComplete()) {				
				
			}
			// System.out.println(prox + " bombs nearby");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startPractice() {
		// this.activeGame = ServerData.practice();
		this.activeGame = new SinglePlayer();
		user.startPlaying();
		System.out.println("listening for tile press");
	}

	/*
	 * commands: menu: gamemode selection, new lobby, get lobbies, join lobby, exit
	 * game: tile press, progress updates, stop playing
	 */

	/*
	 * quit logic this.user.logout(); this.out.writeBytes(message + "\r\n"); // tell
	 * receive thread to stop listening ServerData.players.remove(this);
	 * this.in.close(); this.out.close();
	 */
}
// end class PlayerThread