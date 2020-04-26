package Application;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMeetingWindow extends JFrame {
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	private JLabel startTimestampLabel = new JLabel("Start date (DD-MM-YYYY hh:mm:ss) :");
	private JLabel endTimestampLabel = new JLabel("End date (DD-MM-YYYY hh:mm:ss) :");
	private JTextField startTimestampField = new JTextField(20);
	private JTextField endTimestampField = new JTextField(20);
	private JButton confirmationButton = new JButton("Add Meeting");
	
	public AddMeetingWindow (Connection conn) {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(new GridLayout(3,2,35,5));
		
		confirmationButton.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addMeetingDB(conn, startTimestampField.getText(), endTimestampField.getText());
				}
			}
		);
		
		add(startTimestampLabel);
		add(startTimestampField);
		add(endTimestampLabel);
		add(endTimestampField);
		add(new JLabel(""));
		add(confirmationButton);
	}
	
	public void addMeetingDB (Connection conn, String startTimestamp, String endTimestamp) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Creneau (DATEDEBUT_CRENEAU, DATEFIN_CRENEAU) VALUES('" + startTimestamp + "','" + endTimestamp + "')");
			
			ResultSet rset = stmt.executeQuery();
			if (!rset.next()) {
				JOptionPane.showMessageDialog(null, "Incorrect syntax");
			} else {
				rset.close();
				stmt.close();
				dispose();
				new AddMeetingWindow(conn).setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
