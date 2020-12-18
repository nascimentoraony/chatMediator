package chatRoom;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import chatRoom.ChatClient.MessagesThread;



public class Mediator implements IMediator{

	String user, servername;
    PrintWriter pw;
    BufferedReader br;
    Socket client;
	
	@Override
	public void usuario(String usuNome, String servername) {
		this.user = usuNome;
		this.servername = servername;
        try {
			client  = new Socket(this.servername,9999);
        br = new BufferedReader( new InputStreamReader( client.getInputStream()) ) ;
        pw = new PrintWriter(client.getOutputStream(),true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        pw.println(usuNome);  // send name to server
        
	}

}
