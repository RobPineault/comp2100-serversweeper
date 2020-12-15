package server;

import java.util.ArrayList;
import java.util.Random;

public class Minesweeper {
	private ArrayList<DeepBomb> allTiles;
	private ArrayList<DeepBomb> tileSet;

	Minesweeper() {
		newGame();
	}
	Minesweeper(ArrayList<DeepBomb> tiles) {
		allTiles = tiles;
	}
	public ArrayList<DeepBomb> getTiles() {
		return allTiles;
	}
	public void newGame() {		
		int total = 100;
		int bombs = 10; // determines how many bombs remaining can be pressed on grid	
		
		Random bomberMan = new Random();
		ArrayList<Integer> bombLocations = new ArrayList<Integer>();
		int location;
		while (bombLocations.size() < bombs) {
			do {
				location = bomberMan.nextInt(99);
			} while (bombLocations.contains(location));
			bombLocations.add(location);
		}
		
		allTiles = new ArrayList<>();
		int x = 0;
		int y = 0;
		for (int m = 0; m < total; m++) {
			allTiles.add(new DeepBomb(x, y, bombLocations.contains(m)));
			x++;
			if (x == 10) {
				y++;
				x = 0;
			}
		}
	}
	public ArrayList<DeepBomb> getTiles(int x, int y) {
		tileSet = new ArrayList<DeepBomb>();
		processTile(x, y);
		return tileSet;
	}
	
	private void processTile(int x, int y) {
		DeepBomb tile = allTiles.get((x + y * 10));
		if (!tile.isProcessed()) {
			tile.BombProx(allTiles); 
			tileSet.add(tile);
			if (tile.getProx() == 0) {
				System.out.println("Zero tile \nX: " + x + " Y: " + y + " value: " + tile.getProx());
				if (x > 0) {
					processTile(x - 1, y);
				} // left center

				if (y > 0) {
					processTile(x, y - 1);
				} // top center

				if (x < 9) {
					processTile(x + 1, y);
				} // right center

				if (y < 9) {
					processTile(x, y + 1);
				} // down center

				if (x > 0 && y > 0) {
					processTile(x - 1, y - 1);
				} // top left

				if (x < 9 && y > 0) {
					processTile(x + 1, y - 1);
				} // top right

				if (x > 0 && y < 9) {
					processTile(x - 1, y + 1);
				} // bottom left
				if (x < 9 && y < 9) {
					processTile(x + 1, y + 1);
				} // bottom right
			} else {
				System.out.println("not zero\nX: " + x + " Y: " + y + " value: " + tile.getProx());
			}
		}
	}

	
}

