package Application;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientAllMeetingWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	public PatientAllMeetingWindow(Connection conn, int patient_id)
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(1,1));
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("DATE");
		model.addColumn("DEBUT");
        model.addColumn("FIN");
        model.addColumn("PRIX");
        model.addColumn("REGLEMENT");
        model.addColumn("DATE REGLEMENT");
		
		
		try 
		{
			PreparedStatement stmt1 = conn.prepareStatement("SELECT * FROM CONSULTATION WHERE PATIENTID_PATIENT = ?");
			stmt1.setInt(1, patient_id);
		
			ResultSet rset1 = stmt1.executeQuery();
			
	        while(rset1.next())
	        {
	        	PreparedStatement stmt2 = conn.prepareStatement("SELECT * FROM CRENEAU WHERE CRENEAUXID_CRENEAU = ?");
	        	stmt2.setInt(1,rset1.getInt("CRENEAUXID_CRENEAU"));
	        	ResultSet rset2 = stmt2.executeQuery();
	        	rset2.next();

	            model.addRow(new Object[]{rset2.getDate("DATEDEBUT_CRENEAU"), rset2.getTime("DATEDEBUT_CRENEAU"), rset2.getTime("DATEFIN_CRENEAU"),rset1.getInt("PRIX_CONSULTATION"), rset1.getString("TYPEREGLEMENT_CONSULTATION"),rset1.getDate("DATEREGLEMENT_CONSULTATION")});
	        }
	        
			JTable table = new JTable(model);
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnAdjuster tca = new TableColumnAdjuster(table); // Using TableColumnAdjuster (opensource code) tool we can easily resize column width
			tca.adjustColumns();
			
			add(new JScrollPane(table));
		} 
		catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
