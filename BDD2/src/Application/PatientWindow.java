package Application;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientWindow extends JFrame
{
	/**
	 * Fenêtre côté patient
	*/
	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 1500;
	private final int WINDOW_HEIGHT = 1500;
	
	JLabel labelPatient = new JLabel("Bienvenue !");
	JButton buttonPatient = new JButton("Voir mes rdv antérieurs et futurs");
	
	public PatientWindow(Connection conn, int patient_id)
	{	
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(2,2,5,5));
			
		buttonPatient.addActionListener(new ActionListener()
		{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new PatientMeetingWindow(conn, patient_id).setVisible(true);		
				}
		});
		
		add(new JLabel(""+patient_id));
		add(buttonPatient);
	}
}