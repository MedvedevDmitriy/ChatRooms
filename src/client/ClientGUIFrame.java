package client;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ClientGUIFrame extends JFrame
{
	public ClientGUIFrame()
	{
		setBounds(50, 50, 600, 300);
		setTitle("Client");
		add(new ClientGUIPanel());
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		setVisible(true);
	}
}