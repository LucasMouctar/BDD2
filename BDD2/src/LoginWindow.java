import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginWindow extends JFrame
{
	private final int WINDOW_WIDTH = 300;
	private final int WINDOW_HEIGHT = 150;
	
	private JLabel labelEmail = new JLabel("Enter email : ");
	private JLabel labelPassword = new JLabel("Enter password : ");
	private JTextField textEmail = new JTextField(20);
	private JPasswordField textPassword = new JPasswordField(20);
	private JButton buttonLogin = new JButton("Login");
	
	public LoginWindow(Connection conn)
	{
		super("Login");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setResizable(false);
		
		setLayout(new GridLayout(3,2,5,5));
		
		buttonLogin.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{		
					if(!textEmail.getText().equals("admin"))
					{	
						PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PATIENT WHERE email_Patient = ? AND mot_de_passe_Patient = ?");
						stmt.setString(1, textEmail.getText());
						stmt.setString(2, new String(textPassword.getPassword()));
						
						ResultSet rset = stmt.executeQuery();
					
						if(!rset.next())
						{
							new ErrorWindow("Wrong username/password").setVisible(true);
						}
						else
						{
							rset.close();
							stmt.close();
							dispose();
							new PatientWindow(conn).setVisible(true);
						}
					}
					else
					{
						String password = new String(textPassword.getPassword());
						if(password.equals("admin"))
						{
							dispose();
							new AdminWindow(conn).setVisible(true);
						}
						else 
						{
							new ErrorWindow("Wrong username/password").setVisible(true);
						}
					}
				}
				catch (SQLException e1) 
				{
					new ErrorWindow(e1.getMessage()).setVisible(true);
				}
		
			}
		});
		
		
		add(labelEmail);
		add(textEmail);
		add(labelPassword);
		add(textPassword);
		add(new JLabel(""));
		add(buttonLogin);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent windowEvent)
			{
				if(JOptionPane.showConfirmDialog(null, "Sure ?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					try 
					{
						conn.close();
					} 
					catch (SQLException e) 
					{
						new ErrorWindow(e.getMessage()).setVisible(true);
					}
					System.exit(0);
				}
			}
		});
		
	}
	
	public static void main(String[] args) throws SQLException 
	{	
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","pj_admin","oracle");
			
			new LoginWindow(conn).setVisible(true);
			
			//rset.close();
			//stmt.close();
			//conn.close(); 
		}  
		catch(SQLException e)
		{
			new ErrorWindow(e.getMessage()).setVisible(true);
		}
		catch(ClassNotFoundException e)
		{
			new ErrorWindow(e.getMessage()).setVisible(true);
		}
	}
}
