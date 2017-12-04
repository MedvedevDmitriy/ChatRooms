package server;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Room
{
	private List<Connection> users;
	private LinkedBlockingQueue<String> msg_q;
	String name;
	boolean working;
	
	public Room(String name)
	{
		this.name = name;
		this.working = true;
		users = Collections.synchronizedList(new LinkedList<Connection>());
		msg_q = new LinkedBlockingQueue<String>();
		new Thread(new RoomReciever()).start();
		new Thread(new RoomSender()).start();
	}

	public void addUser(Connection con) throws IOException, InterruptedException
	{
		synchronized (users)
		{
			users.add(con);
		}
	}

	public void removeUser(Connection con) throws IOException, InterruptedException
	{
		synchronized (users)
		{
			users.remove(con);
		}
	}
	
	public String getName()
	{
		return name;
	}

	public void stopRoom()
	{
		working = false;
	}

	public boolean isEmpty()
	{
		return users.size() == 0 ? true : false;
	}

	class RoomReciever implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while (working)
				{
					synchronized (users)
					{
						for (Connection con: users)
						{
							if(con.in.available() > 0)
							{
								String str = con.in.readUTF();
								String cmd = str.substring(0, str.indexOf(':'));
								String msg = str.substring(str.indexOf(':') + 1);

								switch(cmd)
								{
								case "login":
									con.name = msg;	
									
									msg_q.put("msg:User " + con.name + " has joined!");
									break;
								case "msg":
									msg_q.put("msg:User " + con.name + " says: " + msg);
									break;
								case "exit":
									msg_q.put("msg:User " + con.name + " has left!");
									System.out.println("Connection was closed from" + con.socket.getInetAddress().getHostName());
									users.remove(con);
									break;

								case "setroom":
									Rooms.getRoom(msg).addUser(con);
									removeUser(con);
									break;
								case "createroom":
									Rooms.getRoom(msg);
									break;
								case "deleteroom":
									Rooms.removeRoom(msg);
									break;
								case "leave":
									msg_q.put("msg:User " + con.name + " has left!");
									break;
								case "welcome":
									msg_q.put("msg:User " + con.name + " has joined!");
									break;
								default: break;
								}
								System.out.println("Command " + cmd + " executed for " + con.name);
							}	
						}
					}
					Thread.sleep(10);
				}
			}
			catch (InterruptedException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	class RoomSender implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while (working)
				{
					if (!msg_q.isEmpty())
					{
						String str = msg_q.remove();
						synchronized (msg_q)
						{
							for (Connection con: users)
							{
								con.out.writeUTF(str);
								con.out.flush();
							}
							System.out.println("Message '" + str + "' is sent in room " + name);
						}
					}
					Thread.sleep(10);
				} 
			}
			catch (InterruptedException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void updateTableList(String list) throws InterruptedException
	{
		synchronized (msg_q)
		{
			msg_q.put("data:" + list);
		}
	}
}