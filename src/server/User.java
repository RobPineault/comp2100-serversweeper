package server;

import java.util.ArrayList;

/**
 * 
 */

/**
 * 
 *
 * @author pineaultr
 * @version 1.0.0 2020-11-04 Initial implementation
 *
 */
public class User {
	public String name;
	public int id;
	public Minesweeper activeGame;
	public Lobby activeLobby;
	public int updatesSent;
	public int score;
	public boolean inGame;
	private boolean isActive;
	private boolean inPublic;
	private boolean inPractice;
	private boolean inPrivate;	
	private boolean isHalfway;
	
	public User(String name, int id) {
		this.name = name;
		this.id = id;	
		isActive = true;
	}
	public boolean isPlaying() {
		return (inPublic || inPractice || inPrivate);
	}
	public boolean isActive() {
		return isActive;
	}
	public void logout() {
		isActive = false;
	}
	public void startPractice() {
		activeGame = new Minesweeper();
		inPractice = true;		
	}
	public void startPublic() {		
		activeLobby = ServerData.publicGame;
		inPublic = true;
		updateBoard(activeLobby.copyTiles());
	}	
	public void startPrivate(Lobby l) {		
		activeLobby = l;
		inPrivate = true;
		updateBoard(activeLobby.copyTiles());
	}
	public boolean inPrivate() {
		return inPrivate;
	}
	public boolean inPractice() {
		return inPractice;
	}
	public void stopPlaying() {
		inPublic = false;
		inPractice = false;
		inPrivate = false;
		activeLobby = null;
		activeGame = null;
	}
	public ArrayList<DeepBomb> handlePress(int x, int y){
		return activeGame.getTiles(x, y);
	}
	public void updateScore(int points) {
		score += points;
		if(inPublic || inPrivate) {
			if(!isHalfway && score >= 45) 
				activeLobby.handleProgressEvent(this);
			if(score == 90)
				activeLobby.handleWinEvent(this);
		}
	}
	public void handleLose() {
		if(inPublic || inPrivate) {
			inGame = false;
			activeLobby.handleLoseEvent(this);			
		}
	}
	public void updateBoard(ArrayList<DeepBomb> board) {
		activeGame = new Minesweeper(board);
		score = 0;
		isHalfway = false;
		inGame = true;
	}
}
// end class User