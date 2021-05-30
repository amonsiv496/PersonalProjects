package appgraphics;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame{
	
	/**
	 * Main Menu
	 */
	private static final long serialVersionUID = 7035950052745077398L;

	public MainMenu() {
		// Main menu Window Creation
		setTitle("Main Menu");
		setSize(400, 300); // Set the frame size
		setLocationRelativeTo(null); // Center a frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel for vertical alignment
		JPanel panel = new JPanel();
		
		// Set the BoxLayout to be the Y_AXIS from top to bottom
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// Buttons defined
		JButton jbtAdminLogin = new JButton("Admin Login");
		JButton jbtLibrarianLogin = new JButton("Librarian Login");
		
		// Label defined
		JLabel title = new JLabel("Library Management");
		
		// Buttons size set
		jbtAdminLogin.setMaximumSize(new Dimension(150, 50));
		jbtLibrarianLogin.setMaximumSize(new Dimension(150, 50));
		
		// Center stuff horizontally
		title.setAlignmentX(CENTER_ALIGNMENT);
		jbtAdminLogin.setAlignmentX(CENTER_ALIGNMENT);
		jbtLibrarianLogin.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 23)));
		panel.add(title);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(jbtAdminLogin);
		panel.add(Box.createRigidArea(new Dimension(0, 12)));
		panel.add(jbtLibrarianLogin);
		
		add(panel);
		setVisible(true); // Display the frame
		
		
		jbtAdminLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdminLogin admingLogin = new AdminLogin(); // adminLoginListener
			}
			
		});
		
		
		jbtLibrarianLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				LibrarianLogin librarianLogin = new LibrarianLogin();
			}
		});
		
	}

}
