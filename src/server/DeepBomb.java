package server;

import java.util.ArrayList;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;

public class DeepBomb {

	private boolean isBomb;
	int x;
	int y;
	int prox;
	public DeepBomb(int bomb, int x, int y) {
		if (bomb == 9)
			this.isBomb = true;
		else {
			this.isBomb = false;
		}
		this.x = x;
		this.y = y;
		this.prox = 0;
	}

	public void BombProx(ArrayList<DeepBomb> aPoint) {
		if (getB() == false) {			
			if (x > 0) {
				if (aPoint.get((y * 10 + x) - 1).getB() == true)
					prox += 1;
			} // left center

			if (y > 0) {
				if (aPoint.get((y * 10 + x) - 10).getB() == true)
					prox += 1;
			} // top center

			if (x < 9) {
				if (aPoint.get((y * 10 + x) + 1).getB() == true)
					prox += 1;
			} // right center

			if (y < 9) {
				if (aPoint.get((y * 10 + x) + 10).getB() == true)
					prox += 1;
			} // down center

			if (x > 0 && y > 0) {
				if (aPoint.get((y * 10 + x) - 11).getB() == true)
					prox += 1;
			} // top left

			if (x < 9 && y > 0) {
				if (aPoint.get((y * 10 + x) - 9).getB() == true)
					prox += 1;
			} // top right

			if (x > 0 && y < 9) {
				if (aPoint.get((y * 10 + x) + 9).getB() == true)
					prox += 1;
			} // bottom left
			if (x < 9 && y < 9) {
				if (aPoint.get((y * 10 + x) + 11).getB() == true)
					prox += 1;
			} // bottom right	
		} else
			prox = -1;			
	}

	private boolean getB() {
		// Used to pull out whether the designated tile is a bomb(1) or not(0)
		return this.isBomb;
	}
}
