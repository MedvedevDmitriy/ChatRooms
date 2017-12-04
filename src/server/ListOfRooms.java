package server;

import java.util.*;

public class ListOfRooms
{
	private static List<Room> rooms;
	
	public static List<Room> getI()
	{
		if ( rooms == null )
			 rooms = Collections.synchronizedList(new LinkedList<Room>());
		return rooms;
	}
}