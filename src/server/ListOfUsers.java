package server;

import java.util.*;

public class ListOfUsers
{
	private static List<User> users;
	
	public static List<User> getI()
	{
		if ( users == null )
			 users = Collections.synchronizedList(new LinkedList<User>());
		return users;
	}
}