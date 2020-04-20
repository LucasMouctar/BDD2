package Application;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	JLabel labelAdmin = new JLabel("Bienvenue !");
	JButton buttonAdd = new JButton("Inscription d'un nouveau patient");
	JButton buttonRdv = new JButton("Voir mes rdv antérieurs et futur");
	
	public AdminWindow(Connection conn)
	{		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(3,2,5,5));
			
		buttonRdv.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new AdminAllMeetingWindow(conn).setVisible(true);
				}
			});
		
		buttonAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new AddWindow(conn).setVisible(true);
			}
		});		
		
		add(labelAdmin);
		add(buttonRdv);
		add(buttonAdd);
		//Surement d'autres boutons à ajouter
	}
}