package Application;

import java.sql.*;
import java.util.Properties;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	private JLabel labelFirstname = new JLabel("First name : ");
	private JLabel labelLastname = new JLabel("Last name : ");
	private JLabel labelEmail = new JLabel("Email : ");
	private JLabel labelPassword = new JLabel("Password : ");
	private JLabel labelJob = new JLabel("Actual job : ");
	private JLabel labelDatejob = new JLabel("Since : ");
	private JLabel labelMeans = new JLabel("Means of discovery : ");
	
	private JTextField textFirstname = new JTextField(20);
	private JTextField textLastname = new JTextField(20);
	private JTextField textEmail= new JTextField(20);
	private JPasswordField textPassword = new JPasswordField(20);
	private JTextField textJob = new JTextField(20);
	private JDatePickerImpl datePicker;
	private JComboBox listMeans;
	
	private JButton buttonAdd = new JButton("Add");
	
	public AddWindow(Connection conn)
	{		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setLayout(new GridLayout(8,2));
		
		try 
		{
			String[] means = new String[5];
			int i = 0;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT moyenNom_Moyen FROM MOYEN");
			
			while(rs.next())
			{
				means[i] = rs.getString(1);
				i++;
			}
			listMeans = new JComboBox(means);
			stmt.close();
			rs.close();
		} 
		catch (SQLException e) 
		{
		
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		Properties p = new Properties();
		
		p.put("text.today", "today");
		p.put("text.month", "month");
		p.put("text.year", "year");
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		
		add(labelFirstname);
		add(textFirstname);
		add(labelLastname);
		add(textLastname);
		add(labelEmail);
		add(textEmail);
		add(labelPassword);
		add(textPassword);
		add(labelJob);
		add(textJob);
		add(labelDatejob);	 
		add(datePicker);
		add(labelMeans);
		add(listMeans);
		add(new JLabel(""));
		add(buttonAdd);
		
		buttonAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					PreparedStatement stmt0 = conn.prepareStatement("SELECT moyenId_Moyen FROM MOYEN WHERE moyenNom_Moyen = ?");
					stmt0.setString(1, listMeans.getSelectedItem().toString());
					ResultSet rs0 = stmt0.executeQuery();
					
					rs0.next();
					int meansId = rs0.getInt(1);
					
					stmt0.close();
					rs0.close();
		
					PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO PATIENT (nom_Patient, prenom_Patient, email_Patient, mot_de_passe_Patient, moyenId_Moyen) VALUES (?, ? ,?, ?, ?) ");
					stmt1.setString(1, textLastname.getText());
					stmt1.setString(2, textFirstname.getText());
					stmt1.setString(3, textEmail.getText());
					stmt1.setString(4, new String(textPassword.getPassword()));
					stmt1.setInt(5, meansId);
					stmt1.executeUpdate();
					
					stmt1.close();
		
					Statement stmt2 = conn.createStatement();
					ResultSet rs2 = stmt2.executeQuery("SELECT MAX(patientId_Patient) FROM PATIENT"); // We find the id of the patient we just added
					
					rs2.next();
					int patientId = rs2.getInt(1);
		
					stmt2.close();
					rs2.close();
					
					PreparedStatement stmt3 = conn.prepareStatement("SELECT professsionId_Profession FROM PROFESSION WHERE professionNom_Profession = ?");
					stmt3.setString(1, textJob.getText());
					ResultSet rs3 = stmt3.executeQuery();
					
					int professionId;
					
					if(rs3.next()) // Si cette profession existe deja dans la bdd
					{
						professionId = rs3.getInt(1);
						stmt3.close();
						rs3.close();
					}
					else
					{
						PreparedStatement stmt4 = conn.prepareStatement("INSERT INTO PROFESSION (professionNom_Profession)  VALUES (?)"); // We create the profession
						stmt4.setString(1, textJob.getText());
						stmt4.executeUpdate();
						
						stmt4.close();
						
						Statement stmt5 = conn.createStatement();
						ResultSet rs5 = stmt5.executeQuery("SELECT MAX(professsionId_Profession) FROM PROFESSION"); // We find the ID of the profession we just added
								
						rs5.next();
						professionId = rs5.getInt(1);
						
						stmt5.close();
						rs5.close();
					}
					
					PreparedStatement stmt6 = conn.prepareStatement("INSERT INTO EMPLOI VALUES(?,?,?,?)");
					
					java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
					java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
					
					stmt6.setInt(1, patientId);
					stmt6.setInt(2, professionId);
					stmt6.setDate(3, sqlDate);
					stmt6.setDate(4, null); // We assume that the patient is in CDI
					stmt6.executeUpdate();
					
					stmt6.close();
					
					textFirstname.setText("");
					textLastname.setText("");
					textEmail.setText("");
					textPassword.setText("");
					textJob.setText("");
					
					JOptionPane.showMessageDialog(null, "Success !");	
				} 
				catch (SQLException e1) 
				{
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		
	}
}
