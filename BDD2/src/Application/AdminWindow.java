package Application;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	JLabel labelAdmin = new JLabel("Bienvenue !");
	JButton buttonAdd = new JButton("Inscription d'un nouveau patient");
	JButton buttonRdv1 = new JButton("Voir mes rdv antérieurs et futur");
	JButton buttonRdv2 = new JButton("Voir mes rdv pour 1 jour");
	
	public AdminWindow(Connection conn)
	{		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(4,2,5,5));
			
		buttonRdv1.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new AdminAllMeetingWindow(conn).setVisible(true);
				}
			});
		
		buttonRdv2.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new AdminWeeklyOrDaylyMeeting(conn).setVisible(true);
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
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				if(JOptionPane.showConfirmDialog(null, "Sure ?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					try 
					{
						conn.close();
					} 
					catch (SQLException e) 
					{
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
					System.exit(0);
				}
			}
		});
		
		add(labelAdmin);
		add(buttonRdv1);
		add(buttonRdv2);
		add(buttonAdd);
		//Surement d'autres boutons à ajouter
	}
}