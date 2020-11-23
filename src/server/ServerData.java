package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerData {
	
	static ArrayList<Player> players;
	static int totalPlayerCount;
	
	static Minesweeper publicGame;
	static ArrayList<Minesweeper> privateGames;
	
	
	public static void main(String argv[]) throws Exception {
		
		int port = 80;
		players = new ArrayList<Player>();
		totalPlayerCount = 0;
		
		ServerSocket server = null;
		Socket socket;
		try {
			server = new ServerSocket(port);
			System.out.println("Waiting for connection...");
			while (true) {
				socket = server.accept();
				Player p = new Player(socket);
				players.add(p);
				new Thread(p).start();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}
	//public static void joinPublic(Player p) {
		
	//}
	//public static void joinPrivate(Player p, ) {
		
	//}
}
