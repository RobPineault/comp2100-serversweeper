package client;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonClassic {
	private int x;
	private int y;
	private int b;
	//Essentially a boolean: 0 is false, 1 is true. Makes generating the bombs easier
	private Button oopButton;
	
	private boolean flagOnOff = false;
//used for buttons to check OTHER buttons
private ArrayList<ButtonClassic>allButtons;

public ButtonClassic(int x, int y, int bomb, ArrayList<ButtonClassic>allButtons) {
		this.x = x;
		this.y = y;
		this.b= bomb;
		this.allButtons = allButtons;
		//standard array works as well
	
	    oopButton = new Button();
					 oopButton.setMinSize(50, 50); //minimum button size
					 oopButton.setMaxSize(50,50);  //maximum button size
					 oopButton.setPrefSize(50,50); //Proffered size. Redundant, but condensed.
		
					 oopButton.setLayoutX(x*50);
					 oopButton.setLayoutY(y*50);
					 //this determines the placement of the tiles. 
					 //directly scales the x and y terms from the loops
					 
					
					 //oopButton.setText(String.format("%d", x+y*10));
					 //UNCOMMENT THIS if you want to see each tiles individual xy values
					
					 oopButton.setOnAction(new EventHandler<ActionEvent>() {
						 
						@Override
						public void handle(ActionEvent arg0) {
							
							//				allButtons.get(0).getButton().setText(String.format("%d", x+y*10));
											//sample of how to draw information from other areas
							
							oopButton.setText("Clicked");
							//never seen, lol. But if you disable everything, this shows the click function still works.
			
							 if (flagOnOff==false) {
						if (b == 0) {
							int f = 0;
							/** f is a cumulative interval that increases with each proximity test 
							 * Each Test contains an IF command, to account for corner situations
							 * For example, if your tile was boarding the left wall, you wouldn't want to test the tile to it's left
							 * As that would wrap around up and over to the far right.
							 * If a bomb is detected on that tile, the value of f is increased by 1
							 * F is returned after all pertinent tests are activated.
							 * 
							 * because of the 0/10 structuring, the x and y intervals actually line up 1 to 1 with their documentation
							 * For example, tile 35 is y value 3*10 + value x being 5
							 * (Although, it should be noted the counting system employed is from 0-9 instead of 1-10
							 * The first tile being 00, the final tile 
							 */
							
							if (x > 0) {
								if (allButtons.get((y*10+x)-1).getB() == 1)		
									f=f+1;
							} //left center
							
							if (y>0) {
								if (allButtons.get((y*10+x)-10).getB() == 1)	
									f=f+1;
							} //top center
							
							if (x<9) {
								if (allButtons.get((y*10+x)+1).getB() == 1)
									f=f+1;
							} //right center
							
							if (y<9) {
								if (allButtons.get((y*10+x)+10).getB() == 1)	
									f=f+1;
							} //down center
			
							if (x>0 && y>0) {
								if (allButtons.get((y*10+x)-11).getB() == 1)	
									f=f+1;
							} //top left
							
							if (x<9 && y>0) {
								if (allButtons.get((y*10+x)-9).getB() == 1)		
									f=f+1;
							} //top right
							
							if (x>0 && y<9) {
								if (allButtons.get((y*10+x)+9).getB() == 1)		
									f=f+1;
							} //bottom left
							if (x<9 && y<9) {
								if (allButtons.get((y*10+x)+11).getB() == 1)
									f=f+1;						
							} //bottom right
							
							
							oopButton.setText(String.format("%d",f));
						
						}//end bracket for not bomb tests
							 
							if (b == 1) {
								oopButton.setText("*B*");
							} //if it's a bomb, return a bomb symbol 
							 }//closes if flag is false
							 if (flagOnOff==true) {
								 oopButton.setText("*F*");
								 
							 }
						}});
					 /** if true is for flagged, will be altered later Closes brackets!**/
					 
					 //e is the event handler
	 /** @param x
	 * @param y
	 * @param bomb
	 */
		  
		 
	}

public Button getButton() {
	return oopButton;
	
}
	public double getX() {
		//Used to pull out X value of individual object
		return this.x; 
	}
	public double getY() {
		//Used to pull out X value of individual object
		return this.y; 
	}
	private double getB() {
		//Used to pull out whether the designated tile is a bomb(1) or not(0)
		return this.b;
	}
	
public void flagFlip() {
	if (this.flagOnOff == false) {
		this.flagOnOff = true;
	} else if (this.flagOnOff == true) {
		this.flagOnOff = false;
	} //this.flagOnOff or just flagOnOff works too
	
		
}
	
}

