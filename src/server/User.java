package server;

public class User
{
	private String name;	
	private String login;	
	private String password;
	
	public User() {}
	
	public User(String name, String login, String password)
	{
		this.name = name;
		this.login = login;
		this.password = password;
	}
	
	public String getLogin()
	{
		return login;
	}
	public String getPassword()
	{
		return password;
	}
	public String getName()
	{
		return name;
	}
	public void setLogin(String login)
	{
		this.login = login;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
}