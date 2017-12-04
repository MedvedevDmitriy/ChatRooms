package client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import chat_client_api.User;

@SuppressWarnings("serial")
public class ClientGUIPanel extends JPanel
{
	User user;

	JTextField msg;
	JTextField room;
	
	DefaultListModel<String> chatModel;
	JList<String> chat_list;
	
	DefaultListModel<String> usersModel;
	JList<String> users_list;

	DefaultListModel<String> roomsModel;
	JList<String> rooms_list;

	public ClientGUIPanel()
	{
		setLayout(null);
		setSize(600,300);
		setBackground(Color.CYAN);

		String name = JOptionPane.showInputDialog(null, "Enter your nickname:", "Hello!", JOptionPane.QUESTION_MESSAGE);
		initializeUserName(name);

		new Thread(new GUIReciever()).start();

		chatModel = new DefaultListModel<>();
		chat_list = new JList<String>(chatModel);
		usersModel = new DefaultListModel<>();	
		users_list = new JList<String>(usersModel);
		roomsModel = new DefaultListModel<>();	
		rooms_list = new JList<String>(roomsModel);
			
		chat_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chat_list.setLayoutOrientation(JList.VERTICAL);
		users_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		users_list.setLayoutOrientation(JList.VERTICAL);
		rooms_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rooms_list.setLayoutOrientation(JList.VERTICAL);
	
		JScrollPane chatScroller = new JScrollPane(chat_list);
		JScrollPane roomsScroller = new JScrollPane(rooms_list);
		JScrollPane usersScroller = new JScrollPane(users_list);
		roomsScroller.setBounds(20, 20, 100, 100);
		usersScroller.setBounds(120, 20, 100, 100);
		chatScroller.setBounds(230, 20, 200, 100);	
		add(chatScroller);
		add(usersScroller);
		add(roomsScroller);		

		JButton bJoin = new JButton("Join");
		JButton bCreate = new JButton("Create");
		JButton bDelete = new JButton("Delete");
		JButton bSend = new JButton("Send");
		JButton bExit = new JButton("Exit");
		bJoin.setBounds(20, 160, 200, 20);
		bCreate.setBounds(20, 190, 200, 20);
		bDelete.setBounds(20, 220, 200, 20);
		bSend.setBounds(230, 160, 200, 20);
		bExit.setBounds(440, 130, 100, 20);
		bJoin.addActionListener(new ActionRoom());
		bCreate.addActionListener(new ActionCreate());
		bDelete.addActionListener(new ActionDelete());
		bSend.addActionListener(new ActionSend());
		bExit.addActionListener(new ActionExit());

		add(bJoin);
		add(bCreate);
		add(bDelete);
		add(bSend);
		add(bExit);

		msg = new JTextField();
		room = new JTextField();
		msg.setBounds(230,130,200,20);
		room.setBounds(20, 130, 200, 20);
		add(msg);
		add(room);
	}

	private void initializeUserName(String name)
	{
		try
		{
			// DEDICATION OF USER (SOCKET)
			user = User.createUser();
			user.login(name);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	class ActionRoom implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				user.leave();
				Thread.sleep(50);
				chatModel.clear();
				String selected_room = rooms_list.getSelectedValue();
				if (selected_room != null)
				{
					user.setRoom(selected_room);
				}
				user.welcome();
			}
			catch (IOException | InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	class ActionSend implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				user.sendMsg(msg.getText());
				msg.setText("");
			} 
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	class ActionExit implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				user.exit();
				System.exit(0);
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}	

	class ActionCreate implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String room_name = room.getText();
				if (room_name != null && !room_name.equals(""))
				{
					user.createRoom(room_name);
				}				
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	class ActionDelete implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String room_name = rooms_list.getSelectedValue();
				if (room_name != null && !room_name.equals(""))
				{
					user.deleteRoom(room_name);
				}				
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}
	}

	class GUIReciever implements Runnable
	{
		@Override
		public void run()
		{
			while(true)
			{
				String str = user.getMsg();
				if (!str.equals(""))
				{
					String cmd = str.substring(0, str.indexOf(":"));
					String msg = str.substring(str.indexOf(":") + 1);
					switch(cmd)
					{
					case "msg":
						chatModel.addElement(msg);
						break;						
					case "data":
						String[] rooms = msg.split(",");
						roomsModel.clear();
						for (String room : rooms)
						{
							roomsModel.addElement(room);
						}	
						break;
					}
				}
			}
		}
	}
}