package appgraphics;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LibrarianLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7753907110007647207L;

	LibrarianLogin(){
		setTitle("Librarian Login");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		SpringLayout springLayout = new SpringLayout();
		
		contentPane.setLayout(springLayout);
		
		JLabel librarianLoginFormLbl = new JLabel("Librarian Login Form");
		JLabel enterNameLabel = new JLabel("Enter Name:");
		JLabel enterPasswordLabel = new JLabel("Enter Password:");
		
		JTextField nameInputTextField = new JTextField();
		nameInputTextField.setColumns(25);
		JPasswordField passwordField = new JPasswordField("", nameInputTextField.getColumns());
		
		JButton loginBtn = new JButton("Login");
		
		contentPane.add(librarianLoginFormLbl);
		contentPane.add(enterNameLabel);
		contentPane.add(nameInputTextField);
		contentPane.add(enterPasswordLabel);
		contentPane.add(passwordField);
		contentPane.add(loginBtn);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, librarianLoginFormLbl, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		springLayout.putConstraint(SpringLayout.NORTH, librarianLoginFormLbl, 30, SpringLayout.NORTH, contentPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, enterNameLabel, 50, SpringLayout.SOUTH, librarianLoginFormLbl);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, enterNameLabel, -180, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		springLayout.putConstraint(SpringLayout.WEST, nameInputTextField, 50, SpringLayout.EAST, enterNameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, nameInputTextField, 50, SpringLayout.SOUTH, librarianLoginFormLbl);
		
		springLayout.putConstraint(SpringLayout.WEST, enterPasswordLabel, 0, SpringLayout.WEST, enterNameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, enterPasswordLabel, 40, SpringLayout.SOUTH, enterNameLabel);
		
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, nameInputTextField);
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, 40, SpringLayout.SOUTH, enterNameLabel);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBtn, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		springLayout.putConstraint(SpringLayout.NORTH, loginBtn, 40, SpringLayout.SOUTH, passwordField);
		
		setVisible(true);
		
		// Register Listeners
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = "jdbc:sqlserver://DESKTOP-GUG0PM4;databaseName=LibraryManagementSystem;";
				String user = "tester";
				String password = "tester";
				
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					
					PreparedStatement statement = connection.prepareStatement("SELECT * FROM dbo.librarian WHERE name = ?");
					
					statement.setString(1, nameInputTextField.getText());
					ResultSet result = statement.executeQuery();
					
					// goes to first row
					result.next();
					
					String name = result.getString("name");
					String password1 = result.getString("password");
					
					if(nameInputTextField.getText().equals(name)) {
						if (passwordField.getText().equals(password1)) {
							System.out.println("Logged in successfully!");
							dispose();
							LibrarianSection librarianSection = new LibrarianSection();
						} else {
							System.out.println("password does not match to that user");
						}
					} else {
						System.out.println("username does not exist");
					}
					
					System.out.println("Connected to Microsoft SQL Server");
					System.out.println("Closing Connection");
					connection.close();
				} catch (SQLException e1) {
					System.out.println("Opps, there's an error");
					e1.printStackTrace();
				}
			}
		});
	}
}