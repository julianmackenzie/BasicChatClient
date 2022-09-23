import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.util.Pair;

/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	
	Info info = new Info();
	
	boolean newData = false;
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread {
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				info.msg = "client has connected to server: " + "client #" + count;
				info.cmd = 5;
				callback.accept(info);
				
				synchronized(clients) {
					clients.add(c);
				}
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					info.msg = "Server socket did not launch";
					info.cmd = 1;
					callback.accept(info);
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public void updateClients(String message, int cmd, boolean toAll, ArrayList<Integer> sendList) {
				if (toAll) {
					for(int i = 0; i < clients.size(); i++) {
						ClientThread t = clients.get(i);
						try {
						info.msg = message;
						info.cmd = cmd;
						t.out.writeObject(info);
						t.out.reset();
						}
						catch(Exception e) {}
					}
				}
				else  {
					System.out.println("Sending to " + sendList);
					
					for(int i = 0; i < clients.size(); i++) {
						ClientThread t = clients.get(i);
						if (sendList.contains(t.count)) {
							try {
								info.msg = message;
								info.cmd = cmd;
								t.out.writeObject(info);
								t.out.reset();
								}
								catch(Exception e) {}
						}
					}
				}
				
				
				
				
				
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				info.clientList.add(this.count);
				info.clientNumber = this.count;
				updateClients("new client on server: client #"+count, 5, true, info.sendList);
					
				 while(true) {
					 Info clientInfo = new Info();
					    try {
					    	clientInfo = (Info) in.readObject();
					    	newData = true;
					    	}
					    catch(Exception e) {
					    	newData = false;
					    	info.msg = "OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!";
					    	info.cmd = -5;
					    	int index = info.clientList.indexOf(this.count);
					    	info.clientList.remove(index);
					    	
					    	info.clientNumber = this.count;
					    	callback.accept(info);
					    	updateClients("Client #"+count+" has left the server!", -5, true, info.sendList);
					    	synchronized(clients) {
					    		clients.remove(this);
					    	}
					    	break;
					    }
					    
					    if (newData) {
					    	System.out.println(clientInfo.msg);
					    	if (clientInfo.toAll) clientInfo.msg = "client #"+count+" said: "+clientInfo.msg;
					    	else if (!clientInfo.toAll) clientInfo.msg = "client #"+count+" said to client(s) " + clientInfo.sendList + ": "+clientInfo.msg;
					    	
					    	callback.accept(clientInfo);
					    	
					    	if (clientInfo.toAll) {
					    		updateClients(clientInfo.msg, 1, true, clientInfo.sendList);
					    	} else {
					    		updateClients(clientInfo.msg, 1, false, clientInfo.sendList);
					    	}
					    	
					    	newData = false;
					    }
					    
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
