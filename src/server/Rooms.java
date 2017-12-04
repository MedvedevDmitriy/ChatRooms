package server;

public class Rooms
{
	public static Room getRoom(String roomName)
	{
		Room newRoom = null;
		synchronized (ListOfRooms.getI())
		{
			for (Room room : ListOfRooms.getI())
			{
				if (room.getName().equals(roomName))
				{
					sendRoomList();
					return room;
				}
			}
			newRoom = new Room(roomName);
			ListOfRooms.getI().add(newRoom);
		}
		sendRoomList();
		return newRoom;
	}

	public static void removeRoom(String room_name)
	{
		if (room_name.equals("default"))
			return;
		
		synchronized (ListOfRooms.getI())
		{
			for (Room room : ListOfRooms.getI())
			{
				if (room.getName().equals(room_name))
				{
					if (room.isEmpty())
					{
						room.stopRoom();
						ListOfRooms.getI().remove(room);
					}
				}
			}
		}
		sendRoomList();
	}

	private static void sendRoomList()
	{
		String list = "";
		synchronized (ListOfRooms.getI())
		{
			int size = ListOfRooms.getI().size();
			for (int i = 0; i < size; i++)
			{
				list += ListOfRooms.getI().get(i).getName();
				if (i < size - 1)
					list += ",";
			}
			for (Room room : ListOfRooms.getI())
			{
				try
				{
					room.updateTableList(list);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}