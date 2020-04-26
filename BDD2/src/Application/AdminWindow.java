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
	JButton buttonAddPatient = new JButton("Inscription d'un nouveau patient");
	JButton buttonSeeMeeting = new JButton("Voir mes rendez-vous");
	JButton buttonAddMeeting = new JButton("Ajouter un rendez-vous");
	
	public AdminWindow(Connection conn)
	{		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(3,2,5,5));
			
		buttonSeeMeeting.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// new RdvWindow(conn).setVisible(true);
				}
			}
		);
		
		buttonAddMeeting.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new AddMeetingWindow(conn).setVisible(true);
				}
			}
		);	
		
		buttonAddPatient.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new AddWindow(conn).setVisible(true);
				}
			}
		);	
		
		add(labelAdmin);
		add(buttonSeeMeeting);
		add(buttonAddMeeting);
		add(buttonAddPatient);
		//Surement d'autres boutons à ajouter
	}
}