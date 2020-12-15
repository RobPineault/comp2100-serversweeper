package client;

import java.io.*;

import java.net.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class ClientFXBase extends Application {

	private Socket socket;
	private DataOutputStream out;
	private BufferedReader in;

	private Stage app;
	private StackPane root;
	private Pane mainMenu;
	private Label updateLabel;
	private Label statusLabel;

	private ArrayList<Button> tileArray;
	private boolean[] flagArray;
	private boolean flagOn;
	private boolean inMultiplayer;
	private boolean inPrivateLobby;
	private int lobbyCode;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage a) {
		app = a;
		root = new StackPane();
		app.setTitle("ServerSweeper");
		app.setScene(new Scene(root, 725, 625));
		app.show();
		int port = 8080;
		try {
			// connect to server
			socket = new Socket("localhost", port);
			out = new DataOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			createMenu();
			Label title = new Label("Enter username");
			TextField username = new TextField();
			Button submit = new Button("Submit");
			submit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						if (!username.getText().equals("")) {
							out.writeBytes(username.getText() + "\r\n");
							String response = in.readLine();
							if (response.equals("logged in"))
								displayMenu();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			Rectangle background = new Rectangle(250, 100);
			background.setFill(Color.WHITE);
			background.setStroke(Color.SLATEGREY);
			HBox loginFields = new HBox(10, username, submit);
			VBox loginMenu = new VBox(10, title, loginFields);

			loginMenu.setPadding(new Insets(15, 0, 0, 15));
			root.setPadding(new Insets(50, 50, 50, 50));
			root.setAlignment(Pos.TOP_LEFT);
			root.getChildren().addAll(background, loginMenu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayMenu() {
		root.setPadding(new Insets(50, 50, 50, 50));
		root.getChildren().clear();
		root.getChildren().add(mainMenu);
	}

	private void createMenu() {
		VBox menu = new VBox(10);
		Button practice = new Button("Practice");
		practice.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					out.writeBytes("practice: \r\n");
					String response = in.readLine();
					System.out.println(response);
					startGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Button publicButton = new Button("Join public lobby");
		publicButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					out.writeBytes("public: \r\n");
					String response = in.readLine();
					System.out.println(response);
					inMultiplayer = true;
					startGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Button hostButton = new Button("Create private lobby");
		hostButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					out.writeBytes("create private: \r\n");
					String response = in.readLine();
					System.out.println(response);
					lobbyCode = Integer.parseInt(response);
					inPrivateLobby = true;
					inMultiplayer = true;
					startGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		TextField codeInput = new TextField();
		Button joinButton = new Button("Join lobby");
		joinButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {					
					out.writeBytes("join private:"+ codeInput.getText() + "\r\n");
					String response = in.readLine();
					if(response.equals("correct")) {
						lobbyCode = Integer.parseInt(codeInput.getText());
						inPrivateLobby = true;
						inMultiplayer = true;
						startGame();
					}					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		HBox joinContainer = new HBox(5, codeInput, joinButton);		
		menu.getChildren().addAll(practice, publicButton, hostButton, joinContainer);
		mainMenu = menu;
	}

	private void startGame() {
		GridPane minesweeper = new GridPane();
		Pane gameBoard = new Pane();
		tileArray = new ArrayList<>();
		flagArray = new boolean[100];
		Button tile;
		for (int y = 0; y <= 9; y++) {
			for (int x = 0; x <= 9; x++) {
				tile = createTile(x, y);
				gameBoard.getChildren().add(tile);
				tileArray.add(tile);
				flagArray[x + y * 10] = false;
			}
		}
		minesweeper.add(gameBoard, 0, 0, 1, 2);

		Button flagger = new Button("Flag");
		flagger.setPrefSize(235, 50);
		flagOn = false;
		flagger.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (flagOn) {
					flagOn = false;
					for (int i = 0; i < 99; i++) {
						if (flagArray[i]) {
							tileArray.get(i).setDisable(true);
						}
					}
				} else {
					flagOn = true;
					for (int i = 0; i < 99; i++) {
						if (flagArray[i]) {
							tileArray.get(i).setDisable(false);
						}
					}
				}
			}
		});

		Button toMenu = new Button("Back to Menu");
		toMenu.setPrefSize(235, 50);
		toMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					out.writeBytes("quit: \r\n");
					inMultiplayer = false;
					inPrivateLobby = false;
					displayMenu();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		HBox gameMenu = new HBox(30);
		gameMenu.setPadding(new Insets(25, 0, 0, 0));
		gameMenu.getChildren().addAll(flagger, toMenu);
		minesweeper.add(gameMenu, 0, 2, 1, 1);
		
		if (inMultiplayer) {
			VBox sidebar = new VBox(20);
			sidebar.setPadding(new Insets(0, 0, 0, 20));
			if(inPrivateLobby) {
				Label code = new Label("Lobby Code: " + lobbyCode);
				code.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
				//minesweeper.add(code, 1, 2, 1, 1);
				sidebar.getChildren().add(code);
			}
			
			statusLabel = new Label();			
			Label statusTitle = new Label("Game Status");
			statusTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
			
			VBox gameStatus = new VBox(5, statusTitle, statusLabel);
			//gameStatus.setPadding(new Insets(0, 0, 0, 10));
			//minesweeper.add(gameStatus, 1, 0, 1, 1);
			
			Label updateTitle = new Label("Live Updates");
			updateTitle.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
			updateLabel = new Label();
			VBox gameUpdates = new VBox(5, updateTitle, updateLabel);
			//gameUpdates.setPadding(new Insets(20, 0, 0, 20));
			
			
			sidebar.getChildren().addAll(gameStatus, gameUpdates);
			minesweeper.add(sidebar, 1, 0, 1, 3);
		}
		listen();
		root.setPadding(new Insets(25, 25, 25, 25));
		root.getChildren().clear();
		root.getChildren().add(minesweeper);
	}

	private Button createTile(int x, int y) {
		Button tile = new Button();
		tile.setPrefSize(50, 50);
		tile.setLayoutX(x * 50);
		tile.setLayoutY(y * 50);
		tile.setWrapText(true);
		tile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (flagOn) {
					if (!flagArray[x + y * 10]) {
						flagArray[x + y * 10] = true;
						tile.setText("*F*");
					} else if (flagArray[x + y * 10]) {
						flagArray[x + y * 10] = false;
						tile.setText("");
					}
				} else {
					try {
						out.writeBytes("click:" + x + " " + y + "\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		return tile;
	}

	private void updateTiles(HashMap<Integer, String> tileData) {
		System.out.println("update tiles called to update " + tileData.size());
		for (Map.Entry<Integer, String> entry : tileData.entrySet()) {
			Button tile = tileArray.get(entry.getKey());
			tile.setDisable(true);

			if (Integer.parseInt(entry.getValue()) == -1)
				tile.setText("*B*");
			else if (Integer.parseInt(entry.getValue()) > 0)
				tile.setText(entry.getValue());
		}
	}

	private void disableAll() {
		for (Button tile : tileArray) {
			tile.setDisable(true);
		}
	}
	/*
	private void enableAll() {
		for (Button tile : tileArray) {
			tile.setDisable(false);
		}
	}*/
	private void resetBoard() {
		for (boolean f : flagArray) {
			if (f)
				f = false;
		}
		for (Button tile : tileArray) {
			tile.setDisable(false);
			tile.setText("");
		}
	}

	private void handleUpdate(Deque<String> updatesList, String update) {
		if (updatesList.size() == 5)
			updatesList.poll();
		updatesList.offer(update);
		StringBuilder text = new StringBuilder();
		Iterator<String> i = updatesList.iterator();
		do {
			text.append(i.next() + "\n");
		} while (i.hasNext());
		updateLabel.setText(text.toString());
	}

	public void listen() {
		Runnable task = new Runnable() {
			public void run() {
				try {
					String message;
					boolean inGame = true;
					Deque<String> updates = new LinkedList<String>();
					final HashMap<Integer, String> tileData = new HashMap<Integer, String>();
					do {
						message = in.readLine();
						System.out.println("recieved: " + message);
						String command = message.split(":")[0];
						String data = message.split(":")[1];
						if (command.equals("tile")) {
							String[] d = data.split(" ");
							int x = Integer.parseInt(d[0]);
							int y = Integer.parseInt(d[1]);
							tileData.put(x + y * 10, d[2]);
						} else if (command.equals("update")) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									handleUpdate(updates, data);
								}
							});

						} else if (command.equals("status")) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									updateTiles(tileData);
									tileData.clear();
									if (data.equals("lose")) {
										disableAll();
										statusLabel.setText("Better luck next time!\nWaiting for next game...");
									} else if (data.equals("win")) {
										statusLabel.setText("Sweeping the competition!\nWaiting for next game...");
									}
								}
							});

						} else if (command.equals("state")) {
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									if (data.equals("GO!"))
										resetBoard();
									statusLabel.setText(data);
								}
							});
						} else if (command.equals("quit")) {
							inGame = false;
						}
					} while (inGame);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		Thread backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

}

/**
 * What to improve! 1. Make it so it's a consistent single connection, as
 * opposed to constantly opening and closing doors While having them open and
 * close is better then 100 simultanious sockets, there is still room for much
 * improvement
 * 
 * 2. Incorpate multitrheading in order to accomplish this client side, but You
 * will also need multithreading SERVER side in order to handle multiple players
 * 
 * 3. Once multithreading is incorporated, you could have a restart button to
 * redo the tiles.
 */
