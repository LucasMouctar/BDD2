package Application;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMeetingWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	private JLabel startTimestampLabel = new JLabel("Start date (DD-MM-YYYY hh:mm:ss) :");
	private JLabel endTimestampLabel = new JLabel("End date (DD-MM-YYYY hh:mm:ss) :");
	private JTextField startTimestampField = new JTextField(20);
	private JTextField endTimestampField = new JTextField(20);
	private JButton confirmationButton = new JButton("Add Meeting");
	private JComboBox<JComboItem> patientSelection = new JComboBox<JComboItem>();
	
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
		
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT PATIENTID_PATIENT, PRENOM_PATIENT, NOM_PATIENT FROM PATIENT");
			ResultSet result = statement.executeQuery();
			
	        while (result.next()) {
	        	patientSelection.addItem(
        			new JComboItem(result.getString("NOM_PATIENT").toUpperCase() + ' ' + result.getString("PRENOM_PATIENT"), result.getInt("PATIENTID_PATIENT"))
    			);
        	}
	        
	        result.close();
			statement.close();
		} catch (Exception e) {
			
		}
		
		
		add(startTimestampLabel);
		add(startTimestampField);
		add(endTimestampLabel);
		add(endTimestampField);
		add(patientSelection);
		add(new JLabel(""));
		add(confirmationButton);
	}
	
	public void addMeetingDB (Connection conn, String startTimestamp, String endTimestamp) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT CRENEAUXID_CRENEAU FROM CRENEAU WHERE DATEDEBUT_CRENEAU='" + startTimestamp + "' AND DATEFIN_CRENEAU='" + endTimestamp + "'");
			ResultSet result = statement.executeQuery();
			long slotId;
			if (result.isBeforeFirst()) {
				result.next();
				slotId = result.getLong("CRENEAUXID_CRENEAU");
			} else {
				statement = conn.prepareStatement("INSERT INTO Creneau (DATEDEBUT_CRENEAU, DATEFIN_CRENEAU) VALUES('" + startTimestamp + "','" + endTimestamp + "')", new String[] { "CRENEAUXID_CRENEAU" });
				statement.executeUpdate();
				result = statement.getGeneratedKeys();
				if (result.next()) {
					System.out.println(result.getString(1));
					System.out.println(result);
					slotId = result.getLong(1);
				} else {
					result.close();
					statement.close();
					JOptionPane.showMessageDialog(null, "Incorrect syntax");
					return;
				}
				
			}
			
			statement = conn.prepareStatement(
				"INSERT INTO CONSULTATION (CRENEAUXID_CRENEAU, PATIENTID_PATIENT) VALUES(" + slotId + ", " + ((JComboItem)patientSelection.getSelectedItem()).getValue() + ")"
			);
			result = statement.executeQuery();
			
			if (!result.next()) {
				JOptionPane.showMessageDialog(null, "Incorrect syntax");
			} else {
				result.close();
				statement.close();
				dispose();
				new AddMeetingWindow(conn).setVisible(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
