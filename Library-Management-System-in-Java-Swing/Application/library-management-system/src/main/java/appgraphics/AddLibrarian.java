package appgraphics;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class AddLibrarian extends JFrame {
	
	public AddLibrarian() {
		setTitle("Add Librarian");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		SpringLayout springLayout = new SpringLayout();
		
		contentPane.setLayout(springLayout);
		
		JLabel addLibrarianLbl = new JLabel("Add Librarian");
		JLabel nameLbl = new JLabel("Name:");
		JLabel passwordLbl = new JLabel("Password:");
		JLabel emailLbl = new JLabel("Email:");
		JLabel addressLbl = new JLabel("Address:");
		JLabel cityLbl = new JLabel("City:");
		JLabel contactNoLbl = new JLabel("Contact No:");
		
		JTextField nameTextInput = new JTextField();
		nameTextInput.setColumns(20);
		JPasswordField passwordInput = new JPasswordField();
		passwordInput.setColumns(nameTextInput.getColumns());
		JTextField emailTextInput = new JTextField();
		emailTextInput.setColumns(nameTextInput.getColumns());
		JTextField addressTextInput = new JTextField();
		addressTextInput.setColumns(nameTextInput.getColumns());
		JTextField cityTextInput = new JTextField();
		cityTextInput.setColumns(nameTextInput.getColumns());
		JTextField contactNoTextInput = new JTextField();
		contactNoTextInput.setColumns(nameTextInput.getColumns());
		
		JButton addLibrarianBtn = new JButton("Add Librarian");
		JButton backBtn = new JButton("Back");
		
		springLayout.putConstraint(SpringLayout.NORTH, addLibrarianLbl, 15, SpringLayout.NORTH, contentPane);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addLibrarianLbl, 15, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, nameLbl, 15, SpringLayout.SOUTH, addLibrarianLbl);
		springLayout.putConstraint(SpringLayout.WEST, nameLbl, -140, SpringLayout.WEST, addLibrarianLbl);
		springLayout.putConstraint(SpringLayout.NORTH, nameTextInput, 15, SpringLayout.SOUTH, addLibrarianLbl);
		springLayout.putConstraint(SpringLayout.WEST, nameTextInput, 55, SpringLayout.EAST, nameLbl);
		
		springLayout.putConstraint(SpringLayout.NORTH, passwordLbl, 15, SpringLayout.SOUTH, nameLbl);
		springLayout.putConstraint(SpringLayout.WEST, passwordLbl, 0, SpringLayout.WEST, nameLbl);
		springLayout.putConstraint(SpringLayout.NORTH, passwordInput, 15, SpringLayout.SOUTH, nameLbl);
		springLayout.putConstraint(SpringLayout.WEST, passwordInput, 0, SpringLayout.WEST, nameTextInput);
		
		springLayout.putConstraint(SpringLayout.NORTH, emailLbl, 15, SpringLayout.SOUTH, passwordLbl);
		springLayout.putConstraint(SpringLayout.WEST, emailLbl, 0, SpringLayout.WEST, nameLbl);
		springLayout.putConstraint(SpringLayout.NORTH, emailTextInput, 15, SpringLayout.SOUTH, passwordLbl);
		springLayout.putConstraint(SpringLayout.WEST, emailTextInput, 0, SpringLayout.WEST, nameTextInput);
		
		springLayout.putConstraint(SpringLayout.NORTH, addressLbl, 15, SpringLayout.SOUTH, emailLbl);
		springLayout.putConstraint(SpringLayout.WEST, addressLbl, 0, SpringLayout.WEST, nameLbl);
		springLayout.putConstraint(SpringLayout.NORTH, addressTextInput, 15, SpringLayout.SOUTH, emailLbl);
		springLayout.putConstraint(SpringLayout.WEST, addressTextInput, 0, SpringLayout.WEST, nameTextInput);
		
		springLayout.putConstraint(SpringLayout.NORTH, cityLbl, 15, SpringLayout.SOUTH, addressLbl);
		springLayout.putConstraint(SpringLayout.WEST, cityLbl, 0, SpringLayout.WEST, nameLbl);
		springLayout.putConstraint(SpringLayout.NORTH, cityTextInput, 15, SpringLayout.SOUTH, addressLbl);
		springLayout.putConstraint(SpringLayout.WEST, cityTextInput, 0, SpringLayout.WEST, nameTextInput);
		
		springLayout.putConstraint(SpringLayout.NORTH, contactNoLbl, 15, SpringLayout.SOUTH, cityLbl);
		springLayout.putConstraint(SpringLayout.WEST, contactNoLbl, 0, SpringLayout.WEST, nameLbl);
		springLayout.putConstraint(SpringLayout.NORTH, contactNoTextInput, 15, SpringLayout.SOUTH, cityLbl);
		springLayout.putConstraint(SpringLayout.WEST, contactNoTextInput, 0, SpringLayout.WEST, nameTextInput);
		
		springLayout.putConstraint(SpringLayout.NORTH, addLibrarianBtn, 25, SpringLayout.SOUTH, contactNoLbl);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, addLibrarianBtn, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		
		springLayout.putConstraint(SpringLayout.NORTH, backBtn, 35, SpringLayout.SOUTH, addLibrarianBtn);
		springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		
		add(addLibrarianLbl);
		add(nameLbl);
		add(nameTextInput);
		add(passwordLbl);
		add(passwordInput);
		add(emailLbl);
		add(emailTextInput);
		add(addressLbl);
		add(addressTextInput);
		add(cityLbl);
		add(cityTextInput);
		add(contactNoLbl);
		add(contactNoTextInput);
		add(addLibrarianBtn);
		add(backBtn);
		
		
		addLibrarianBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String url = "jdbc:sqlserver://DESKTOP-GUG0PM4;databaseName=LibraryManagementSystem;";
				String user = "tester";
				String password = "tester";
				
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					System.out.println("Connected to Microsoft SQL Server");
					
					String insert = "INSERT INTO [dbo].[librarian] (name, password, email, address, city, contact) VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStatement = connection.prepareStatement(insert);
					
					preparedStatement.setString(1, nameTextInput.getText());
					preparedStatement.setString(2, passwordInput.getText());
					preparedStatement.setString(3, emailTextInput.getText());
					preparedStatement.setString(4, addressTextInput.getText());
					preparedStatement.setString(5, cityTextInput.getText());
					preparedStatement.setString(6, contactNoTextInput.getText());
					
					int i = preparedStatement.executeUpdate();
					
					if (i == 1) {
						dispose();
						JFrame frame = new JFrame();
						frame.setSize(300, 200);
						frame.setTitle("Message");
						frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
						frame.setLocationRelativeTo(null);
						frame.setVisible(true);
						
						BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
						
						frame.setLayout(boxLayout);
						
						JLabel addedMsg = new JLabel("Librarian Added Successfully!");
						
						JButton okBtn = new JButton("OK");
						
						addedMsg.setAlignmentX(CENTER_ALIGNMENT);
						okBtn.setAlignmentX(CENTER_ALIGNMENT);
						
						frame.add(Box.createRigidArea(new Dimension(0,25)));
						frame.add(addedMsg);
						frame.add(Box.createRigidArea(new Dimension(0,25)));
						frame.add(okBtn);
						
						okBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
								AdminSection adminSection = new AdminSection();
							}
							
						});
					}
					
					System.out.println("Closing Connection");
					connection.close();
				} catch (SQLException e1) {
					System.out.println("Opps, there's an error");
					e1.printStackTrace();
				}
				
			}
		});
		
		setVisible(true);
	}
	
}
