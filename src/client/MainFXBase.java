package client;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


public class MainFXBase extends Application {
	
	
	@Override
	public void start(Stage mineBoard) {
		//determines name of stage
		try {
			mineBoard.setTitle("Let's play MineSweeper!");
			final Pane plain = new Pane();
	
			
			
			mineBoard.setScene(new Scene(plain, 500, 550));
			//initiates game. 
			// dimensions are established here, not upon creation
			mineBoard.show();
			
			//Go! it's show time! Reveals game tiles. Without this, the player will not interact.
			int total = 100;
			//Determines how many *tiles* are generated*
			int totalReplica = total;
			
			int x = 0;
			int y = 0;
			//Do not modify, the loops run on these.
			
			int b = 10;
			//determines how many bombs remaining can be pressed on grid
	
			Random bomberMan = new Random(); //Used for randomized bomb placement
			int j; //Randomizer's returned value 
			ArrayList<ButtonClassic>SquareArray = new ArrayList<>();
			
			for ( y = 0; y <= 9; y++) {
				for (x = 0; x<=9; x++) {
			
					j = bomberMan.nextInt(10); //0-9, 9 == bomb. 1/10 chance of a bomb.
					if (j == 9 && b > 0) { //if ^ bomb AND there are bombs left to place
						b = b-1;
						SquareArray.add(new ButtonClassic(x,y,1,SquareArray));
					}
				else if (b>=total) {
					b = b-1;
					SquareArray.add(new ButtonClassic(x,y,1,SquareArray));
					}
				else {
					SquareArray.add(new ButtonClassic(x,y,0,SquareArray));
				}
					total = total - 1;
		
				}	/** closes x loop **/ } /*closes y loop*/
		
			 for (int i = 0; i<totalReplica; i++)  //this loop reveals and places all 100 tiles
				 plain.getChildren().add(SquareArray.get(i).getButton());
			 //each object exists within a greater arrayList, their properties storred as a set of data points
			 //these being x,y,bombStatus
			 
			 
				
				Button Restarter = new Button();
				 Restarter.setPrefSize(200,50);

				 Restarter.setLayoutX(275);
				 Restarter.setLayoutY(500);
				 plain.getChildren().add(Restarter);
				 Restarter.setText("Close Game");
			 

				Button Flagger = new Button();
				Flagger.setPrefSize(200,50); 
				Flagger.setLayoutX(25);
				Flagger.setLayoutY(500);
				Flagger.setText("Flag");
				plain.getChildren().add(Flagger); 
			
				
				Flagger.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				   // 	Flagger.setText("Flagger");
				    for (int r = 0; r<totalReplica; r++) 
				    SquareArray.get(r).flagFlip();
				    }});

				Restarter.setOnAction(new EventHandler<ActionEvent>() {
				    @Override public void handle(ActionEvent e) {
				   // 	Flagger.setText("Flagger");
				    	System.exit(0);
				    }});
				

							
									
			
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	public static void main(String[] args) {
		launch(args);
	
	//	final SingleStats p = new SingleStats();
		
		
	}
}
