package server;

import java.util.ArrayList;
import java.util.Random;

public abstract class Minesweeper {
	private int id;
	private ArrayList<DeepBomb> allPoints;
	private ArrayList<DeepBomb> tiles;

	Minesweeper() {
		generateTiles();
	}

	public void generateTiles() {
		int total = 100; // Improved versions may incorporate a command to determine difficulty or other
							// options
		int bombs = 10; // determines how many bombs remaining can be pressed on grid
		Random bomberMan = new Random(); // Used for randomized bomb placement
		allPoints = new ArrayList<>(); // used for data calling
		int x = 0;
		int y = 0;
		for (int m = 0; m < total; m++) {
			int j = bomberMan.nextInt(10); // 0-9, 9 == bomb. 1/10 chance of a bomb.
			if (bombs == 0) {
				j = 0;
			}
			if (bombs >= 100 - m)
				j = 9;
			allPoints.add(new DeepBomb(j, x, y));
			if (j == 9)
				bombs--;
			if (x == 9) {
				y++;
				x = 0;
			}
			x++;
		}
	}

	public void getBombProx(int x, int y) {
		DeepBomb tile = allPoints.get((y * 10 + x));
		tile.BombProx(allPoints);
		if (tile.prox == 0) {
			if (x > 0) {
				getBombProx(x - 1, y);
			} // left center

			if (y > 0) {
				getBombProx(x, y + 1);
			} // top center

			if (x < 9) {
				getBombProx(x + 1, y);
			} // right center

			if (y < 9) {
				getBombProx(x, y - 1);
			} // down center

			if (x > 0 && y > 0) {
				getBombProx(x - 1, y + 1);
			} // top left

			if (x < 9 && y > 0) {
				getBombProx(x + 1, y + 1);
			} // top right

			if (x > 0 && y < 9) {
				getBombProx(x - 1, y - 1);
			} // bottom left
			if (x < 9 && y < 9) {
				getBombProx(x + 1, y - 1);
			} // bottom right
		}
		tiles.add(tile);
	}

	public ArrayList<DeepBomb> getTiles(int x, int y) {
		tiles = new ArrayList<DeepBomb>();
		getBombProx(x, y);
		return tiles;
	}

	public abstract void updateScore(int points);

	//public abstract void handleBomb();
	
	public abstract boolean isComplete();
	
	public abstract int getScore();
}
//single
//new game
//(Player) game.isBomb(x,y) => array<int> 
//(Player) if not a bomb, score += array.length
//(Player) if score = tiles - bombs, send won message
//(Player) Game.quit
//(Player) Game.playAgain
