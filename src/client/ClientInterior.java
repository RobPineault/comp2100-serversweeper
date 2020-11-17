package client;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class ClientInterior extends Thread {
	private int x;
	private int y;
	private Button Square;
	private boolean flagOnOff = false;
	private ArrayList<ClientInterior>allTiles;

public ClientInterior (int xPos, int yPos,ArrayList<ClientInterior> tileArray) throws UnknownHostException, IOException {
	this.x = xPos;
	this.y = yPos;
	Square = new Button();
	Square.setPrefSize(50,50); //Proffered size. Redundant, but condensed.

	Square.setLayoutX(x*50);
	Square.setLayoutY(y*50);
	
	
	Square.setOnAction(new EventHandler<ActionEvent>() {
	//	Socket connectionSocket = new Socket("localhost", 1300);
		//Establishes server
	//	DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
	//	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		@Override
		public void handle(ActionEvent arg0) {
			
//			Square.setText("Clicked");	
			int r = returnData();
			Square.setText(String.format("%d",r));
			if (!flagOnOff) {
				
			
			try {
				OnlinePush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	/**		try {
				outToServer.writeBytes(String.format("%d", r));
				String serverMessage = inFromServer.readLine();
				Square.setText(String.format("%d",serverMessage));
			} catch (IOException e) {
				e.printStackTrace();
			}
	**/		}
			if (flagOnOff) {
				Square.setText("*F*");
			}
			
		}});
		
			
}

public Button getButton() {
	return Square;
}

public int returnData() {
	return x+y*10;
}

public int getX() {
	//Used to pull out X value of individual object
	return this.x; 
}
public int getY() {
	//Used to pull out X value of individual object
	return this.y; 
}

public void OnlinePush() throws UnknownHostException, IOException {
	/*
	Socket connectionSocket = new Socket("localhost", 1300);
		//Establishes server
		DataOutputStream outToServer = new DataOutputStream(connectionSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
	
		outToServer.writeBytes(getX() + "\r\n");
		outToServer.writeBytes(getY() + "\r\n");
		String serverMessage = inFromServer.readLine(); 
		if (serverMessage.equals("-1")) {
			serverMessage = "*B*";
		}
		Square.setText(serverMessage);
		outToServer.close();
		connectionSocket.close();	
		*/
}
public void setValue(String value) {
	Square.setText(value);	
}

public void flagFlip() {
	if (this.flagOnOff == false) {
		this.flagOnOff = true;
	} else if (this.flagOnOff == true) {
		this.flagOnOff = false;
	} //this.flagOnOff or just flagOnOff works too
	
		
}


public void restarter() {
	Square.setText("");
}


	
}
