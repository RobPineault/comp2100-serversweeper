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
    private boolean active;
    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.active = true;
    }

	 public String getName(){
	     return this.name;
	 }

	 public int getID(){
	     return this.id;
	 }
	public boolean isActive() {
		return this.active;
	}

	public void logout() {
		this.active = false;
	}
 
    }
// end class User