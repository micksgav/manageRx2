package mainUI;

import utilities.Encrypt;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
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
	
	private Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
			.getMaximumWindowBounds(); // dimensions of screen from
	
	private Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text
	private Font nameFont = new Font("Arial", Font.PLAIN, 35); // font for names and titles
	private Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
	private Border redBoxBorderLine = BorderFactory.createLineBorder(new Color(255, 0, 0), screenDims.width / 700);
	private Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
			(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
	private CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);
	private CompoundBorder incorrectFieldBorder = new CompoundBorder(redBoxBorderLine,textFieldPadding);

	public loginUI() {
		
		// setup screen attributes
		FlatLightLaf.setup();
		setTitle("ManageRx");
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
		
		
		loginPane.setBorder(textBoxBorder);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(25, 25, 25, 25);

		managerxLabel.setFont(nameFont);
		gbc.gridx = 2;
		gbc.gridy = 0;
		loginPane.add(managerxLabel, gbc);

		usernameLabel.setFont(genFont);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		loginPane.add(usernameLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		usernameField.setFont(genFont);
		usernameField.setBorder(textBoxBorder);
		loginPane.add(usernameField, gbc);

		passwordLabel.setFont(genFont);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		loginPane.add(passwordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		passwordField.setFont(genFont);
		passwordField.setBorder(textBoxBorder);
		loginPane.add(passwordField, gbc);

		loginButton.addActionListener(this);
		loginButton.setActionCommand("loginButtonAction");
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		loginButton.setFont(genFont);
		loginButton.setBorder(textBoxBorder);
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
		boolean login = true;//value for login
		
		//reset the red border on the boxes 
		if(usernameField.getBorder() == incorrectFieldBorder) {
			usernameField.setBorder(textBoxBorder);
		}
		if(passwordField.getBorder() == incorrectFieldBorder) {
			passwordField.setBorder(textBoxBorder);
		}
		
		//check username
		if(!usernameField.getText().equals("username")) {
			login = false;
			usernameField.setBorder(incorrectFieldBorder);
		}
		//if username/login-identifier found in db get user password hash 
		//compare password hash's
		if(!getPassword().equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")) {
			login = false;
			passwordField.setBorder(incorrectFieldBorder);
		}

		//handle login events
		if(login) {
			System.out.println("Logged In");
			mainUI UI = new mainUI();
			UI.setVisible(true);
			setVisible(false);
		}
		return false;
	}
	
	private String getPassword() {
		String password = new String(passwordField.getPassword());
		String encryptedPassword = Encrypt.SHA256(password);
		return encryptedPassword;
	}

}