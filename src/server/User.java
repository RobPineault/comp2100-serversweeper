package server;

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
	private String name;
	private int id;
	private boolean isActive;
	private boolean isPlaying;

	public User(String name, int id) {
		this.name = name;
		this.id = id;
		this.isPlaying = false;
		this.isActive = true;
	}

	public String getName() {
		return this.name;
	}

	public int getID() {
		return this.id;
	}
	public void startPlaying() {
		this.isPlaying = true;
	}
	public void stopPlaying() {
		this.isPlaying = false;
	}
	public boolean isPlaying() {
		return this.isPlaying;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void logout() {
		this.isActive = false;
	}

}
// end class User