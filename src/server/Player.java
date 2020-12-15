/**
 * 
 */
package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
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
	public User user;

	/**
	 * @param socket
	 */
	Player(Socket socket) {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {
			String name = in.readLine();
			int id = ServerData.totalPlayerCount + 1;
			ServerData.totalPlayerCount++;
			user = new User(name, id);
			out.writeBytes("logged in\r\n");
			System.out.println(name + " connected");
			while (user.isActive()) {
				String message = in.readLine();
				String command = message.split(":")[0];
				String data = message.split(":")[1];
				System.out.println("command received: " + command);
				if (!user.isPlaying()) {
					// menu:					
					switch (command) {
					case "practice":
						System.out.println("executing practice");
						startPractice();
						break;
					case "public":
						joinPublic();
						break;
					case "create private":
							createPrivateLobby();
						break;
					case "join private":
							joinPrivateLobby(data);
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
						user.activeLobby.leave(this);
						user.stopPlaying();					
						out.writeBytes("quit: \r\n");
						System.out.println(user.name + " has quit!");
						break;
					case "continue":
						user.activeGame.newGame();
						break;
					case "click":
						handleTilePress(data);
						break;
					case "update":
						System.out.println("old command");
						break;
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
			
			//get bomb prox
			ArrayList<DeepBomb> tiles = user.handlePress(numX, numY);			
			//send tiles discovered
			System.out.println("sending " + tiles.size() + " tiles");
			for (DeepBomb tile : tiles) {
				out.writeBytes("tile:" + tile.getX() + " " + tile.getY() + " " + tile.getProx() + "\r\n");
			}
			//update game state
			if (tiles.get(0).getProx() != -1) {
				user.updateScore(tiles.size());
				if (user.score == 90) {
					out.writeBytes("status:win\r\n");
				} else {
					out.writeBytes("status:continue\r\n");
				}
			} else {
				user.handleLose();
				out.writeBytes("status:lose\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startPractice() {
		try {
			user.startPractice();
			out.writeBytes("ready\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("listening for tile press");
	}
	private void joinPublic() {
		try {
			out.writeBytes("joined\r\n");
			ServerData.publicGame.join(this);
			user.startPublic();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void createPrivateLobby() {
		try {
			Lobby newLobby = new Lobby();			
			ServerData.privateGames.add(newLobby);
			newLobby.join(this);
			user.startPrivate(newLobby);
			out.writeBytes(newLobby.ID + "\r\n");							
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void joinPrivateLobby(String code) {
		try {
			Lobby target = null;
			for(Lobby lobby : ServerData.privateGames) {
				if(lobby.ID == Integer.parseInt(code)) {
					target = lobby;
					break;
				}
			}
			if(target == null) {				
				out.writeBytes("incorrect\r\n");				
			}
			else {
				target.join(this);
				user.startPrivate(target);
				out.writeBytes("correct\r\n");			
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendUpdate(String m) {
		try {
			out.writeBytes("update:" + m + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stateUpdate(String m) {
		try {
			out.writeBytes("state:" + m + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
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