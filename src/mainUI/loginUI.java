package mainUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;

import swingHelper.AppIcon;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class loginUI extends JFrame implements ActionListener {

	// JPanels
	private JPanel loginPane = new JPanel(new GridBagLayout());// panel for login components
	private JPanel buttonPanel; // header panel
	private JPanel headerButtons; // buttons other than back in header
	// Jlabels
	private JLabel managerxLabel = new JLabel("ManageRx");// label for managerx
	private JLabel usernameLabel = new JLabel("User ID");// label for user id/credentials
	private JLabel passwordLabel = new JLabel("Password");// label for user password
	// JFields
	private JTextField usernameField = new JTextField(20);
	private JPasswordField passwordField = new JPasswordField(20);
	// JButtons
	private JButton loginButton = new JButton("Login");
	

	// header buttons
	private JButton btnOpenStock; // open stock
	private JButton btnOpenOrder; // open order
	private JButton btnOpenSettings; // open settings
	private JButton btnOpenPatientManager; // open patient manager

	// main buttons
	private JButton backButton; // go back to previous page

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public loginUI() {
		
		// setup screen attributes
		FlatLightLaf.setup();
		setTitle("ManageRx");
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
		// screenDims.height /= 1.5;
		this.setPreferredSize(new Dimension(screenDims.width, screenDims.height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// add all buttons to header, then add header to mainPanel
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx");
		label.setFont(new Font("Arial", Font.BOLD, 20));

		btnOpenStock = new JButton("Stock");
		btnOpenStock.setIcon(stockIcon);
		btnOpenStock.setActionCommand("openStock");
		btnOpenStock.addActionListener(this);

		btnOpenOrder = new JButton("Order");
		btnOpenOrder.setIcon(orderIcon);
		btnOpenOrder.setActionCommand("openOrder");
		btnOpenOrder.addActionListener(this);

		btnOpenSettings = new JButton("Settings");
		btnOpenSettings.setIcon(settingsIcon);
		btnOpenSettings.setActionCommand("openSettings");
		btnOpenSettings.addActionListener(this);

		btnOpenPatientManager = new JButton("Patients");
		btnOpenPatientManager.setIcon(patientsIcon);
		btnOpenPatientManager.setActionCommand("openPatientManager");
		btnOpenPatientManager.addActionListener(this);

		// add back button to header
		backButton = new JButton("Back");
		backButton.addActionListener(this);

		GridBagConstraints backConstraints = new GridBagConstraints(); // constraints for back button

		backConstraints.gridx = 0;
		backConstraints.gridy = 0;
		backConstraints.gridwidth = 1;
		backConstraints.anchor = GridBagConstraints.WEST;
		backConstraints.ipadx = (int) (screenDims.width * 0.02);
		backConstraints.weightx = 0.45;
		backConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		this.buttonPanel.add(backButton, backConstraints);

		// add buttons other than back to header
		headerButtons = new JPanel(new FlowLayout());

		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		// headerButtons.add(btnOpenSettings);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for buttons other than
																				// back in header

		overallButtonConstraints.gridx = 2;
		overallButtonConstraints.gridy = 0;
		overallButtonConstraints.gridwidth = 1;
		overallButtonConstraints.weightx = 0.55;
		overallButtonConstraints.anchor = GridBagConstraints.WEST;
		this.buttonPanel.add(headerButtons, overallButtonConstraints);

		add(this.buttonPanel, BorderLayout.NORTH);

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

		if (e.getActionCommand().equals("loginButtonAction")) {
			verifyLogin();
		}

	}

	private boolean verifyLogin() {
		boolean login = true;
		if (!usernameField.getText().equals("username")) {
			login = false;
		}

		if (!passwordField.getText().equals("password")) {
			login = false;
		}

		if (login) {
			System.out.println("Logged In");
		} else {
			System.out.println(
					"Not Logged In\nUsername:" + usernameField.getText() + "\nPassword:" + passwordField.getText());
		}
		return false;
	}

}