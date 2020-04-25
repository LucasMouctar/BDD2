package Application;

import java.awt.Component;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class AdminAllMeetingWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	public AdminAllMeetingWindow(Connection conn)
	{
		super("Historique des rendez-vous");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(1,1));
		
		getAllMeetings(conn);
	}
	
	public void getAllMeetings(Connection conn)
	{
		DefaultTableModel model = new DefaultTableModel();
		
		
		model.addColumn("CLASSIFICATION");
		model.addColumn("PRENOM");
		model.addColumn("NOM");
		model.addColumn("DATE");
		model.addColumn("DEBUT");
        model.addColumn("FIN");
        model.addColumn("POSTURE");
        model.addColumn("MOT CLEF");
        model.addColumn("COMPORTEMENT");
        model.addColumn("ANXIETE");
        model.addColumn("PRIX");
        model.addColumn("REGLEMENT");
        model.addColumn("DATE REGLEMENT");
		
		
		try 
		{
			PreparedStatement stmt1 = conn.prepareStatement("SELECT CLASSIFICATION_CONSULTATION, PRENOM_PATIENT, NOM_PATIENT, DATEDEBUT_CRENEAU, DATEFIN_CRENEAU, POSTURE_CONSULTATION, MOT_CLEF_CONSULTATION, COMPORTEMENT_CONSULTATION, NOTEANXIETE_CONSULTATION, PRIX_CONSULTATION, TYPEREGLEMENT_CONSULTATION, DATEREGLEMENT_CONSULTATION FROM CONSULTATION CON JOIN PATIENT P ON CON.PATIENTID_PATIENT = P.PATIENTID_PATIENT JOIN CRENEAU CRE ON CRE.CRENEAUXID_CRENEAU = CON.CRENEAUXID_CRENEAU");
		
			ResultSet rset1 = stmt1.executeQuery();
			
	        while(rset1.next())
	        {
	            model.addRow(new Object[]{rset1.getString("CLASSIFICATION_CONSULTATION"), rset1.getString("PRENOM_PATIENT"), rset1.getString("NOM_PATIENT"),rset1.getDate("DATEDEBUT_CRENEAU"), rset1.getTime("DATEDEBUT_CRENEAU"), rset1.getTime("DATEFIN_CRENEAU"), rset1.getString("POSTURE_CONSULTATION"), rset1.getString("MOT_CLEF_CONSULTATION"), rset1.getString("COMPORTEMENT_CONSULTATION"), rset1.getInt("NOTEANXIETE_CONSULTATION"), rset1.getInt("PRIX_CONSULTATION"), rset1.getString("TYPEREGLEMENT_CONSULTATION"),rset1.getDate("DATEREGLEMENT_CONSULTATION")});
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
