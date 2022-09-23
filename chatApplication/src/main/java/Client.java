import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	boolean newData = false;
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call){
	
		callback = call;
	}
	
	Info info = new Info();
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			info = (Info) in.readObject();
			newData = true;
			}
			catch(Exception e) {newData = false;}
			
			if (newData) {
				
				callback.accept(info);
				info.msg = "";
				info.cmd = 0;
			}
		}
	
    }
	
	public void send(String data, String sendText) {
		
		try {
			info.sendList.clear();
			String[] sendParse = sendText.split(" ");
			if (sendText != "") {
				for (String s : sendParse) {
					
					info.sendList.add(Integer.valueOf(s));
					System.out.println(s + " in send list");
				}
			}
			
			
			if (info.sendList.size() == 0) {
				info.toAll = true;
			}
			else {
				info.toAll = false;
			}
			
			
			info.msg = data;
			info.cmd = 1;
			out.writeObject(info);
			out.reset();
			info.msg = "";
			info.cmd = 0;
		} catch (IOException e) {
			System.out.println("Failed to send message");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
