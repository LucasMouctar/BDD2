import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientWindow extends JFrame
{
	private final int WINDOW_WIDTH = 1500;
	private final int WINDOW_HEIGHT = 1500;
	
	JLabel labelPatient = new JLabel("Bienvenue !");
	JButton buttonPatient = new JButton("Voir mes rdv antérieurs et futur");
	
	public PatientWindow(Connection conn)
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setResizable(false);
		
		setLayout(new GridLayout(2,2,5,5));
			
		buttonPatient.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					new RdvWindow(conn).setVisible(true);		
				}
			});
		add(labelPatient);
		add(buttonPatient);
	}
}