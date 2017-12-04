package chat_client_api;

import java.io.IOException;

public class User
{
	String name = "";
	ChatAPI chat = null;
	
	public User(ChatAPI chat)
	{
		this.chat = chat;
	}
	
	public static User createUser()
	{
		ChatAPI chat = null;
		
		try
		{
			chat = new ChatAPI();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return new User(chat);
	}
	
	public void login(String name) throws IOException
	{
		this.name = name;
		chat.out.writeUTF("login:" + name);
		chat.out.flush();
	}
	public void sendMsg(String msg) throws IOException
	{
		chat.out.writeUTF("msg:" + msg);
		chat.out.flush();
	}
	public void leave() throws IOException
	{
		chat.out.writeUTF("leave:");
		chat.out.flush();
	}
	public void exit() throws IOException
	{
		chat.out.writeUTF("exit:");
		chat.out.flush();
	}
	public void welcome() throws IOException
	{
		chat.out.writeUTF("welcome:");
		chat.out.flush();
	}
	public void setRoom(String room_name) throws IOException
	{
		chat.out.writeUTF("setroom:" + room_name);
		chat.out.flush();
	}

	public void createRoom(String room_name) throws IOException
	{
		chat.out.writeUTF("createroom:" + room_name);
		chat.out.flush();
	}
	public void deleteRoom(String room_name) throws IOException
	{
		chat.out.writeUTF("deleteroom:" + room_name);
		chat.out.flush();
	}
	
	public String getMsg()
	{
		String result = "";	
		try
		{
			if (chat.in.available() > 0)
			{
				result = chat.in.readUTF();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		return result;
	}

	public String getName()
	{
		return name;
	}
}