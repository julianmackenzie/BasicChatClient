
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Label;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.util.Pair;


public class GuiServer extends Application{

	
	TextField s1,s2,s3,s4, c1;
	TextField sendBox;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Client clientConnection;
	
	Label clientList;
	
	ListView<String> listItems, listItems2;

	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Networked Client/Server GUI Example");
		
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 300px");
		
		this.serverChoice.setOnAction(e->{ primaryStage.setScene(sceneMap.get("server"));
											primaryStage.setTitle("This is the Server");
				serverConnection = new Server(data->{
					Info info = (Info) data;
					Platform.runLater(()->{listItems.getItems().add(info.msg);});
					

				});
											
		});
		
		
		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 300px");
		
		this.clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
											primaryStage.setTitle("This is a client");
											clientConnection = new Client(data->{
												Info info = (Info) data;
												String msg = info.msg;
												if (info.cmd == -5) { // client disconnect
													Platform.runLater(()->{
														clientList.setText("Connected clients: " + info.clientList);
													});
													info.cmd = 1;
												}
												if (info.cmd == 5) { // client connect
													Platform.runLater(()->{
														
														clientList.setText("Connected clients: " + info.clientList);
														
													});
													info.cmd = 1;
												}
												
												if (info.cmd == 1) {
													Platform.runLater(()->{listItems2.getItems().add(msg);});
												}
												
											});
							
											clientConnection.start();
		});
		
		this.buttonBox = new HBox(400, serverChoice, clientChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		
		startScene = new Scene(startPane, 800,800);
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		
		c1 = new TextField();
		c1.setPromptText("Enter message here");
		sendBox = new TextField();
		sendBox.setPromptText("Enter client numbers separated by spaces to DM or leave empty to message all");
		b1 = new Button("Send");
		b1.setOnAction(e->{clientConnection.send(c1.getText(), sendBox.getText()); c1.clear();});
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("server",  createServerGui());
		sceneMap.put("client",  createClientGui());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 500, 400);
		
		
	}
	
	public Scene createClientGui() {
		clientList = new Label("Connected clients: ");
		clientBox = new VBox(10, clientList, c1, sendBox,b1,listItems2);
		clientBox.setStyle("-fx-background-color: lightblue");
		return new Scene(clientBox, 500, 400);
		
	}

}
