package Application;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientMeetingWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	JLabel labelRdv = new JLabel("En construction");
	
	public PatientMeetingWindow(Connection conn, int patient_id)
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(1,1));
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("DEBUT");
        model.addColumn("FIN");
        model.addColumn("PRIX");
        model.addColumn("REGLEMENT");
        model.addColumn("DATE REGLEMENT");
		
		
		try 
		{
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CONSULTATION WHERE PATIENTID_PATIENT = ?");
			stmt.setInt(1, patient_id);
			ResultSet rset = stmt.executeQuery();
			
	        while(rset.next())
	        {
	            model.addRow(new Object[]{rset.getInt(1), rset.getString(2),rset.getString(3),rset.getString(4)});
	        }
	        
			JTable table = new JTable( model );
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		add(labelRdv);
	}
}
