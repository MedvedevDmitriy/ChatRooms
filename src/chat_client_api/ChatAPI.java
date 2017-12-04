package chat_client_api;

import java.io.*;
import java.net.*;

public class ChatAPI
{
	private static final String HOST = "localhost";
	private static final int PORT = 7777;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	public ChatAPI() throws UnknownHostException, IOException
	{
		socket = new Socket(HOST, PORT);
		in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));	
	}
	
	public User createUser()
	{
		return null;
		//return new User();
	}
}