package server;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class DeepBomb {

private boolean bomb;
private ArrayList<DeepBomb>aPoint;
	public DeepBomb (int bomb, ArrayList<DeepBomb>allPoints) {
		if (bomb == 9)
			this.bomb = true;
		else { this.bomb = false; }
		this.aPoint = allPoints;
	}

	public int BombProx(int x, int y) {
		if (getB() == false) {
		int f = 0;
		if (x > 0) {
			if (aPoint.get((y*10+x)-1).getB() == true)		
				f=f+1;
		} //left center
		
		if (y>0) {
			if (aPoint.get((y*10+x)-10).getB() == true)	
				f=f+1;
		} //top center
		
		if (x<9) {
			if (aPoint.get((y*10+x)+1).getB() == true)
				f=f+1;
		} //right center
		
		if (y<9) {
			if (aPoint.get((y*10+x)+10).getB() == true)	
				f=f+1;
		} //down center

		if (x>0 && y>0) {
			if (aPoint.get((y*10+x)-11).getB() == true)	
				f=f+1;
		} //top left
		
		if (x<9 && y>0) {
			if (aPoint.get((y*10+x)-9).getB() == true)		
				f=f+1;
		} //top right
		
		if (x>0 && y<9) {
			if (aPoint.get((y*10+x)+9).getB() == true)		
				f=f+1;
		} //bottom left
		if (x<9 && y<9) {
			if (aPoint.get((y*10+x)+11).getB() == true)
				f=f+1;						
		} //bottom right
		return f;
		}
		else return -1;
	}
	private boolean getB() {
		//Used to pull out whether the designated tile is a bomb(1) or not(0)
		return this.bomb;
	}
}
	
