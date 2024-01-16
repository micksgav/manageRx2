package mainUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class loginUI extends JFrame implements ActionListener {
	
	//JPanels
	private JPanel loginPane = new JPanel(new GridBagLayout());//panel for login components
	//Jlabels
	private JLabel managerxLabel = new JLabel("ManageRx");//label for managerx
	private JLabel usernameLabel = new JLabel("User ID");//label for user id/credentials
	private JLabel passwordLabel = new JLabel("Password");//label for user password
	//JFields
	private JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);
	//JButtons
	private JButton loginButton = new JButton("Login");
	
	
	
	public loginUI() {
		super("Login - ManageRx");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		managerxLabel.setFont(new Font("Vardana", Font.PLAIN, 24));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.EAST;
		loginPane.add(managerxLabel, gbc);
		
		usernameLabel.setFont(new Font("Vardana", Font.PLAIN, 16));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		loginPane.add(usernameLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 5;
		loginPane.add(usernameField, gbc);
		
		passwordLabel.setFont(new Font("Vardana", Font.PLAIN, 16));
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.WEST;
		loginPane.add(passwordLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 5;
		loginPane.add(passwordField, gbc);
		
		loginButton.addActionListener(this);
		loginButton.setActionCommand("loginButtonAction");
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		loginPane.add(loginButton, gbc);
		
		add(loginPane, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("loginButtonAction")) {
			verifyLogin();
		}
		
	}
	
	private boolean verifyLogin() {
		boolean login = true;
		if(!usernameField.getText().equals("username")) {
			login = false;
		}
		
		if(!passwordField.getText().equals("password")) {
			login = false;
		}
		
		if(login) {
			System.out.println("Logged In");
		} else {
			System.out.println("Not Logged In\nUsername:" + usernameField.getText() + "\nPassword:" + passwordField.getText());
		}
		return false;
	}
	
}