package server;

import java.io.*;
import java.net.*;

public class Connection
{
	Socket socket;
	DataOutputStream out;
	DataInputStream in;
	String name = "default";
	
	public Connection(Socket socket)
	{
		this.socket = socket;
		try
		{
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}		
	}		
}