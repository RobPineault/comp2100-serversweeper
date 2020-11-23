package server;

public class SinglePlayer extends Minesweeper{
	//private boolean isActive;
	private int score;
	//private int timer;
	
	SinglePlayer(){
		//this.isActive = true;
		this.score = 0;
	}
	
	public void updateScore(int points) {
		score += points;
	}
	public boolean isComplete() {
		if(score == 90) 
			return true;
		return false;
	}
	public int getScore() {
		return score;
	}
}
