package appgraphics;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;

public class AdminSection extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3302817414323633689L;

	
	public AdminSection() {
		setTitle("Admin Section");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel adminSectionLbl = new JLabel("Admin Section");
		
		JButton addLibrarianBtn = new JButton("Add Librarian");
		JButton viewLibrarianBtn = new JButton("View Librarian");
		JButton deleteLibrarianBtn = new JButton("Delete Librarian");
		JButton logOutBtn = new JButton("Logout");
		
		addLibrarianBtn.setMaximumSize(new Dimension(150, 50));
		viewLibrarianBtn.setMaximumSize(new Dimension(150, 50));
		deleteLibrarianBtn.setMaximumSize(new Dimension(150, 50));
		logOutBtn.setMaximumSize(new Dimension(150, 50));
		
		addLibrarianBtn.setAlignmentX(CENTER_ALIGNMENT);
		adminSectionLbl.setAlignmentX(CENTER_ALIGNMENT);
		viewLibrarianBtn.setAlignmentX(CENTER_ALIGNMENT);
		deleteLibrarianBtn.setAlignmentX(CENTER_ALIGNMENT);
		logOutBtn.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(adminSectionLbl);
		panel.add(Box.createRigidArea(new Dimension(0, 35)));
		panel.add(addLibrarianBtn);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(viewLibrarianBtn);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(deleteLibrarianBtn);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		panel.add(logOutBtn);
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		add(panel);
		
		setVisible(true);
		
		addLibrarianBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				AddLibrarian addLibrarian = new AddLibrarian();
			}
			
		});
		
		viewLibrarianBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				JFrame frame2 = new JFrame();
				frame2.setSize(800, 400);
				frame2.setTitle("View Librarians");
				frame2.setLocationRelativeTo(null);
				frame2.setDefaultCloseOperation(frame2.EXIT_ON_CLOSE);
				frame2.setVisible(true);
				
				String url = "jdbc:sqlserver://DESKTOP-GUG0PM4;databaseName=LibraryManagementSystem;";
				String user = "tester";
				String password = "tester";
				
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					System.out.println("Connected to Microsoft SQL Server");
					
					Statement statement = connection.createStatement();
					String selectQuery = "SELECT * FROM dbo.librarian";
					ResultSet resultSet = statement.executeQuery(selectQuery);
					
//					String data[][] = new String[250][250];
//					String columnName[] = {"id", "name", "password", "email", "address", "city", "contact"};
					
					Vector<String> header = new Vector<String>();
					header.add("Id");
					header.add("Name");
					header.add("Password");
					header.add("Email");
					header.add("Address");
					header.add("City");
					header.add("Contact");
					
					System.out.println(header.size());
					
					Vector<Vector<String>> doubleVector = new Vector<Vector<String>>();
					
					
					for(int i = 0; resultSet.next(); i++) {
						Vector<String> singleVector = new Vector<String>();
						for (int j = 0; j < 7; j++) {
							singleVector.add(resultSet.getString(j + 1));
						}
						doubleVector.add(singleVector);
					}
					
					JTable jTable = new JTable(doubleVector, header);
					JScrollPane sp = new JScrollPane(jTable);
					
					jTable.getColumnModel().getColumn(0).setPreferredWidth(50);
					jTable.getColumnModel().getColumn(1).setPreferredWidth(350);
					jTable.getColumnModel().getColumn(2).setPreferredWidth(250);
					jTable.getColumnModel().getColumn(3).setPreferredWidth(500);
					jTable.getColumnModel().getColumn(4).setPreferredWidth(250);
					jTable.getColumnModel().getColumn(5).setPreferredWidth(250);
					jTable.getColumnModel().getColumn(6).setPreferredWidth(250);
					
					DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
					renderer.setHorizontalAlignment(JLabel.LEFT);
					
					for (int i = 0; i < header.size(); i++) {
						jTable.getColumnModel().getColumn(i).setHeaderRenderer(renderer);
					}
					
					frame2.add(sp);
					
					System.out.println("Closing Connection");
					connection.close();
				} catch (SQLException e1) {
					System.out.println("Opps, there's an error");
					e1.printStackTrace();
				}
				
			}
		});
		
		deleteLibrarianBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				JFrame frame2 = new JFrame();
				frame2.setSize(400, 300);
				frame2.setTitle("Delete Librarian");
				frame2.setLocationRelativeTo(null);
				frame2.setDefaultCloseOperation(frame2.EXIT_ON_CLOSE);
				frame2.setVisible(true);
				
				String url = "jdbc:sqlserver://DESKTOP-GUG0PM4;databaseName=LibraryManagementSystem;";
				String user = "tester";
				String password = "tester";
				
				try {
					Connection connection = DriverManager.getConnection(url, user, password);
					System.out.println("Connected to Microsoft SQL Server");
					
					Container contentPane = frame2.getContentPane();
					SpringLayout springLayout = new SpringLayout();
					
					contentPane.setLayout(springLayout);
					
					JLabel enterIdLbl = new JLabel("Enter Id:");
					
					JTextField idTextFieldInput = new JTextField();
					idTextFieldInput.setColumns(20);
					
					JButton deleteBtn = new JButton("Delete");
					JButton backBtn = new JButton("Back");
					
					springLayout.putConstraint(SpringLayout.NORTH, enterIdLbl, 20, SpringLayout.NORTH, contentPane);
					springLayout.putConstraint(SpringLayout.WEST, enterIdLbl, 20, SpringLayout.WEST, contentPane);
					
					springLayout.putConstraint(SpringLayout.WEST, idTextFieldInput, 20, SpringLayout.EAST, enterIdLbl);
					springLayout.putConstraint(SpringLayout.SOUTH, idTextFieldInput, 20, SpringLayout.NORTH, enterIdLbl);
					
					springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, deleteBtn, 0, SpringLayout.HORIZONTAL_CENTER, idTextFieldInput);
					springLayout.putConstraint(SpringLayout.NORTH, deleteBtn, 20, SpringLayout.SOUTH, idTextFieldInput);
					
					springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backBtn, 0, SpringLayout.HORIZONTAL_CENTER, deleteBtn);
					springLayout.putConstraint(SpringLayout.NORTH, backBtn, 20, SpringLayout.SOUTH, deleteBtn);
					
					frame2.add(enterIdLbl);
					frame2.add(idTextFieldInput);
					frame2.add(deleteBtn);
					frame2.add(backBtn);
					
					deleteBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							
							String url = "jdbc:sqlserver://DESKTOP-GUG0PM4;databaseName=LibraryManagementSystem;";
							String user = "tester";
							String password = "tester";
							
							try {
								Connection connection = DriverManager.getConnection(url, user, password);
								System.out.println("Connected to Microsoft SQL Server");
								
								String delete = "DELETE FROM dbo.librarian WHERE librarian_id = ?";
								PreparedStatement preparedStatement = connection.prepareStatement(delete);
								
								if (idTextFieldInput.getText().length() != 0) {
									preparedStatement.setLong(1, Long.parseLong(idTextFieldInput.getText()));
									preparedStatement.execute();
									
									frame2.dispose();
									
									JOptionPane.showMessageDialog(null, "Record Deleted Successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
									
									System.out.println("Deletion Completed!");
								}
								
								System.out.println("Closing Connection");
								connection.close();
							} catch (SQLException e1) {
								System.out.println("Opps, there's an error");
								e1.printStackTrace();
							}
						}
						
					});
					System.out.println("Closing Connection");
					connection.close();
					
					backBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							frame2.dispose();
							AdminSection adminSection = new AdminSection();
						}
					});
					
				} catch (SQLException e1) {
					System.out.println("Opps, there's an error");
					e1.printStackTrace();
				}
			}
		});
		
	}

}