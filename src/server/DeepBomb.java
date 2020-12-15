package server;

import java.util.ArrayList;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;

public class DeepBomb {

	private boolean isBomb;
	private int x;
	private int y;
	private int prox;
	private boolean processed;
	public DeepBomb(int x, int y, boolean isBomb) {
		this.isBomb = isBomb;
		this.x = x;
		this.y = y;
		this.prox = 0;
		this.processed = false;
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
		processed= true;
	}
	public boolean isProcessed() {
		return processed;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getProx() {
		return prox;
	}
	public boolean getB() {
		return isBomb;
	}
}
