import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

public class ErrorWindow extends JFrame
{
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 150;
	
	JLabel labelError = new JLabel();
	JButton buttonError = new JButton("OK");
	
	public ErrorWindow(String error)
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setResizable(false);
		
		setLayout(new GridLayout(2,2,5,5));
		
		labelError.setText(error);
		add(labelError);
		
		buttonError.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dispose();
					
				}
			});
		add(buttonError);
	}

}
