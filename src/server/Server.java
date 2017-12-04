package server;

import java.io.*;
import java.net.*;

public class Server extends Thread
{
	private static final int PORT = 7777;
	private ServerSocket serverSocket;
	
	public Server()
	{
		start();
	}

	@Override
	public void run()
	{
		try
		{
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server was started on port " + PORT + "!");
			while(true)
			{
				Connection con = new Connection(serverSocket.accept());
				System.out.println("Connection was added from " + con.socket.getInetAddress().getHostName());
				Rooms.getRoom("default").addUser(con);
				synchronized (ListOfUsers.getI())
				{
					ListOfUsers.getI().add(new User("name", "login", "password"));
				}
			} 
		}
		catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}