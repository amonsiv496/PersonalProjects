package appgraphics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AdminLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	
	public AdminLogin() {
		// Admin Login Form Window
		setTitle("Admin Login Form");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		SpringLayout springLayout = new SpringLayout();
		
		contentPane.setLayout(springLayout);
		
		JLabel adminLoginForm = new JLabel("Admin Login Form");
		JLabel enterNameLabel = new JLabel("Enter Name:");
		JLabel enterPasswordLabel = new JLabel("Enter Passowrd:");
		
		JTextField nameInputTextField = new JTextField("", 17);		
		JPasswordField passwordInputField = new JPasswordField("", nameInputTextField.getColumns());
		
		JButton loginBtn = new JButton("Login");
		
		contentPane.add(adminLoginForm);
		contentPane.add(enterNameLabel);
		contentPane.add(nameInputTextField);
		contentPane.add(enterPasswordLabel);
		contentPane.add(passwordInputField);
		contentPane.add(loginBtn);
	
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, adminLoginForm, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		springLayout.putConstraint(SpringLayout.NORTH, adminLoginForm, 30, SpringLayout.NORTH, contentPane);
		
		springLayout.putConstraint(SpringLayout.WEST, enterNameLabel, -120, SpringLayout.WEST, adminLoginForm);
		springLayout.putConstraint(SpringLayout.NORTH, enterNameLabel, 30, SpringLayout.SOUTH, adminLoginForm);
		springLayout.putConstraint(SpringLayout.WEST, nameInputTextField, 55, SpringLayout.EAST, enterNameLabel);
		springLayout.putConstraint(SpringLayout.NORTH, nameInputTextField, 30, SpringLayout.SOUTH, adminLoginForm);
		
		springLayout.putConstraint(SpringLayout.NORTH, enterPasswordLabel, 40, SpringLayout.SOUTH, enterNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, enterPasswordLabel, 0, SpringLayout.WEST, enterNameLabel);
		springLayout.putConstraint(SpringLayout.WEST, passwordInputField, 0, SpringLayout.WEST, nameInputTextField);
		springLayout.putConstraint(SpringLayout.NORTH, passwordInputField, 34, SpringLayout.SOUTH, nameInputTextField);
		
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, loginBtn, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		springLayout.putConstraint(SpringLayout.NORTH, loginBtn, 30, SpringLayout.SOUTH, enterPasswordLabel);
		
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
					System.out.println("Connected to Microsoft SQL Server");
					
					String selectStmnt = "SELECT * FROM dbo.users";
					
					Statement statement = connection.createStatement();
					ResultSet result = statement.executeQuery(selectStmnt);
					
					result.next();
					String username = result.getString("username");
					String password1 = result.getString("password");
					
					if (nameInputTextField.getText().equals(username)) {
						if (passwordInputField.getText().equals(password1)) {
							System.out.println("Logged in successfully!");
							dispose();
							AdminSection adminSecion = new AdminSection();
						}
						else {
							System.out.println("password does not match to that user");
						}
					} else {
						System.out.println("username does not exist");
					}
					
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