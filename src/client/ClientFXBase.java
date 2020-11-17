package client;
import java.io.*;

import java.net.*;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;

public class ClientFXBase extends Application{
	
	

		@Override
		public void start(Stage mineBoard) {
			try {
				mineBoard.setTitle("Let's play MineSweeper!");
				final Pane plain = new Pane();
				int total = 100;
				mineBoard.setScene(new Scene(plain, 500, 550));
				mineBoard.show();
				
				ArrayList<ClientInterior>TileArray = new ArrayList<>();
				
				for (int y = 0; y <= 9; y++) {
					for (int x = 0; x<=9; x++) { 
						TileArray.add(new ClientInterior(x,y,TileArray));
					}}
				
				 for (int i = 0; i<total; i++)  //this loop reveals and places all 100 tiles
					 plain.getChildren().add(TileArray.get(i).getButton());
				 
				 Button Flagger = new Button();
					Flagger.setPrefSize(200,50); 
					Flagger.setLayoutX(25);
					Flagger.setLayoutY(500);
					Flagger.setText("Flag");
					plain.getChildren().add(Flagger); 
				
					
					Button Restarter = new Button();
					 Restarter.setPrefSize(200,50);

					 Restarter.setLayoutX(275);
					 Restarter.setLayoutY(500);
					 plain.getChildren().add(Restarter);
					 Restarter.setText("Restart");
					 
					Flagger.setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					   // 	Flagger.setText("Flagger");
					    for (int r = 0; r<100; r++) 
					   TileArray.get(r).flagFlip();
					    }});
					
					Restarter.setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					   // 	Flagger.setText("Flagger");
					    //	System.exit(0);
					    	  for (int r = 0; r<100; r++) 
								   TileArray.get(r).restarter();
								    
					    }});
					
					

		} 
			catch(Exception e) {
			e.printStackTrace();}

	}

		public static void main(String[] args) {
			int port = 80;
			Socket socket;
			try {
				socket = new Socket("localhost", port);

				DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
				BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				Thread send = new ClientSender(outToServer);
				send.start();
				Thread receive = new ClientReciever(inFromServer);
				receive.start();

			} catch (IOException e) {
				e.printStackTrace();
			}
			launch(args);
		}

}


/**
 * What to improve!
 * 1. Make it so it's a consistent single connection, as opposed to constantly opening and closing doors
 * While having them open and close is better then 100 simultanious sockets, there is still room for much improvement
 * 
 * 2. Incorpate multitrheading in order to accomplish this client side, but
 *  You will also need multithreading SERVER side in order to handle multiple players
 *  
 *  3. Once multithreading is incorporated, you could have a restart button to redo the tiles.
 */



