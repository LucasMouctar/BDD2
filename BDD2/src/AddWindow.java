import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWindow extends JFrame
{
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 800;
	
	JLabel labelAdd = new JLabel("En construction");
	
	public AddWindow(Connection conn)
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setResizable(false);
		
		setLayout(new GridLayout(1,1));
		
		add(labelAdd);
	}
}
