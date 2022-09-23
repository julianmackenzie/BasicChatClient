import java.io.Serializable;
import java.util.ArrayList;
import javafx.util.Pair;

public class Info implements Serializable {
	String msg = "";
	int cmd = 0;
	
	int clientNumber;
	
	
	ArrayList<Integer> clientList = new ArrayList<Integer>();
	ArrayList<Integer> sendList = new ArrayList<Integer>();
	
	boolean toAll = true;
	
	
	private static final long serialVersionUID = 1L;
}
