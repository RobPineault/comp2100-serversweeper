package server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

public class ServerData {
	
	static ArrayList<Player> players;
	static int totalPlayerCount;
	
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
	public static void generateTiles() {
		//ten random bombs
		int total = 100;
		//Improved versions may incorporate a command to determine difficulty or other options
		int b = 10;
		//determines how many bombs remaining can be pressed on grid
		Random bomberMan = new Random(); //Used for randomized bomb placement
		ArrayList<DeepBomb>allPoints = new ArrayList<>();
		//used for data calling 
		for (int m = 0; m<total; m++) {
			int j = bomberMan.nextInt(10); //0-9, 9 == bomb. 1/10 chance of a bomb.
			if (b == 0) { j = 0; }
			if (b >= 100 - m) j = 9;
			allPoints.add(new DeepBomb(j,allPoints));
			if (j == 9) b--;
			}
		
	}
}
