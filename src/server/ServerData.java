package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerData {	
	
	static int totalPlayerCount;	
	static Lobby publicGame;
	static ArrayList<Lobby> privateGames;
	
	public static void main(String argv[]) throws Exception {		
		//int mainPort = 8080;
		totalPlayerCount = 0;
		privateGames = new ArrayList<Lobby>();
		publicGame = new Lobby();		
		ServerSocket server;
        Socket socket;
        try {
        	
            server = new ServerSocket(8080);
            //game = new ServerSocket(gamePort);
            System.out.println("Waiting for connection...");
			while (true) {
				socket = server.accept();
				Player p = new Player(socket);
				new Thread(p).start();
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
