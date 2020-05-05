package Application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminAllMeetingWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
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
		

		model.addColumn("");
		model.addColumn("");
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
		

		JTable table = new JTable(model);
		try 
		{
			PreparedStatement stmt1 = conn.prepareStatement("SELECT CLASSIFICATION_CONSULTATION, PRENOM_PATIENT, NOM_PATIENT, CON.CRENEAUXID_CRENEAU, CON.PATIENTID_PATIENT, DATEDEBUT_CRENEAU, DATEFIN_CRENEAU, POSTURE_CONSULTATION, MOT_CLEF_CONSULTATION, COMPORTEMENT_CONSULTATION, NOTEANXIETE_CONSULTATION, PRIX_CONSULTATION, TYPEREGLEMENT_CONSULTATION, DATEREGLEMENT_CONSULTATION FROM CONSULTATION CON JOIN PATIENT P ON CON.PATIENTID_PATIENT = P.PATIENTID_PATIENT JOIN CRENEAU CRE ON CRE.CRENEAUXID_CRENEAU = CON.CRENEAUXID_CRENEAU");
			ResultSet rset1 = stmt1.executeQuery();
			
	        while(rset1.next()) {
	            model.addRow(new Object[]{
	            		"‎🗑",
	            		"‎✎",
	            		rset1.getString("CLASSIFICATION_CONSULTATION"),
	            		rset1.getString("PRENOM_PATIENT"),
	            		rset1.getString("NOM_PATIENT"),
	            		rset1.getDate("DATEDEBUT_CRENEAU"),
	            		rset1.getTime("DATEDEBUT_CRENEAU"),
	            		rset1.getTime("DATEFIN_CRENEAU"),
	            		rset1.getString("POSTURE_CONSULTATION"),
	            		rset1.getString("MOT_CLEF_CONSULTATION"),
	            		rset1.getString("COMPORTEMENT_CONSULTATION"),
	            		rset1.getInt("NOTEANXIETE_CONSULTATION"),
	            		rset1.getInt("PRIX_CONSULTATION"),
	            		rset1.getString("TYPEREGLEMENT_CONSULTATION"),
	            		rset1.getDate("DATEREGLEMENT_CONSULTATION")
        		});
	            table = new JTable(model);
	            
	            String action = "DELETE FROM CONSULTATION WHERE CRENEAUXID_CRENEAU=" + rset1.getInt("CRENEAUXID_CRENEAU") + " AND PATIENTID_PATIENT=" + rset1.getInt("PATIENTID_PATIENT");
	            new ButtonColumn(
            		table,
            		new AbstractAction() {
						private static final long serialVersionUID = 1L;
						// Supprime la consultation associée puis supprime tous les créneaux qui n'ont pas de consultations associées
						@Override
						public void actionPerformed(ActionEvent event) {
							PreparedStatement stmt2;
							ResultSet rset2;
							try {
								stmt2 = conn.prepareStatement(action);
								rset2 = stmt2.executeQuery();
								rset2.next();

								stmt2 = conn.prepareStatement("DELETE FROM CRENEAU WHERE NOT EXISTS(SELECT NULL FROM CONSULTATION WHERE CONSULTATION.CRENEAUXID_CRENEAU = CRENEAU.CRENEAUXID_CRENEAU)");
								rset2 = stmt2.executeQuery();
								rset2.next();
								rset2.close();
								stmt2.close();
								dispose();
								new AdminAllMeetingWindow(conn).setVisible(true);
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
					},
            		0
    			);
	            
	            long slotId = rset1.getLong("CRENEAUXID_CRENEAU");
	            long patientId = rset1.getLong("PATIENTID_PATIENT");
	            new ButtonColumn(
            		table,
            		new AbstractAction() {
						private static final long serialVersionUID = 1L;
						@Override
						public void actionPerformed(ActionEvent event) {
							new EditMeetingWindow(conn, slotId, patientId).setVisible(true);
						}
					},
            		1
    			);
	        }
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			TableColumnAdjuster tca = new TableColumnAdjuster(table); // Using TableColumnAdjuster (opensource code) tool we can easily resize column width
			tca.adjustColumns();
			add(new JScrollPane(table));

			rset1.close();
			stmt1.close();
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
