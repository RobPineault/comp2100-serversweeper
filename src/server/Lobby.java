package server;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {
	private ArrayList<DeepBomb> allTiles;
	private ArrayList<Player> players;
	public int ID;
	Lobby(){
		players = new ArrayList<Player>();
		newGame();
		Random random = new Random();
		int randomID;
		do {
			randomID = random.nextInt(9999) + 1;
		} while (!uniqueID(randomID));
		ID = randomID;
	}
	private boolean uniqueID(int id) {
		boolean unique = true;
		for(Lobby game : ServerData.privateGames) {
				if(game.ID == id)
					unique = false;
		}
		return unique;
	}	

	private void newGame() {
		allTiles = new Minesweeper().getTiles();
		for(Player p : players) {			
			p.user.updateBoard(copyTiles());
			p.stateUpdate("GO!");
		}
	}
	public void join(Player p) {
		players.add(p);
		if(players.size() == 1)
			p.sendUpdate("Waiting for 1 more player");
		if(players.size() == 2) {
			userBroadcast(p.user, "has joined!");	
			delayStart(10);
		}					
	}
	public ArrayList<DeepBomb> copyTiles(){
		ArrayList<DeepBomb> copy = new ArrayList<DeepBomb>();
		for(DeepBomb tile : allTiles) {
			copy.add(new DeepBomb(tile.getX(), tile.getY(), tile.getB()));
		}
		return copy;
	}
	public void leave(Player p) {
		players.remove(p);
		userBroadcast(p.user, "has left!");
	}
	public void handleWinEvent(User u) {
		userBroadcast(u, "Won!");
		serverBroadcast("Next game starts in 10 seconds!");
		delayStart(10);
	}
	public void handleLoseEvent(User u) {
		userBroadcast(u, "Hit a bomb!");
		boolean playerLeft = false;
		for(Player p : players) {
			if(p.user.inGame)
				playerLeft = true;
		}
		if(!playerLeft) {					
			serverBroadcast("Next game starts in 10 seconds!");
			delayStart(10);
		}
	}
	public void handleProgressEvent(User u) {
		userBroadcast(u, "Is half way done!");
	}
	public int numPlayers() {
		return players.size();
	}
	private void userBroadcast(User u, String message) {
		for(Player p : players) {
			if(p.user.id != u.id)
				p.sendUpdate(u.name + " " + message);
		}
	}
	private void serverBroadcast(String message) {
		for(Player p : players) {
			p.stateUpdate(message);
		}
	}
	private void delayStart(int delayTime) {
        Runnable task = new Runnable() {
            public void run() {
                try {
                    Thread.sleep((delayTime-3) * 1000);
                    for(int i = 3; i > 0; i--) {
            			serverBroadcast("starting in " + i);
                    	Thread.sleep(1000);
                    }                                       
                    newGame();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Thread backgroundThread = new Thread(task);
        backgroundThread.start();
    }
}
