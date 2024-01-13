package patientUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.FlatLightLaf;

import PatientManagement.*;
import mainUI.loginUI;
import mainUI.settingsUI;
import swingHelper.AppIcon;

public class SearchAddUI extends JFrame implements ActionListener {

	// panels
	private JPanel buttonPanel; // header panel
	private JPanel mainPanel; // panel containing the main buttons
	private JPanel mainContainer; // panel containing everything but header

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton searchExisting; // search for an existing patient
	private JButton addNew; // add a new patient

	// patient information
	Patient patient; // patient to pass through to other UIs
	PatientList patients; // list containing all patient info

	// text elements
	private JLabel pageTitle = new JLabel("Patient Manager"); // patient manager title

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public SearchAddUI(String title, Patient patient, PatientList patients) {

		// setup screen attributes
		FlatLightLaf.setup(); // custom look and feel
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// instantiate variables
		this.patient = patient;
		this.patients = patients;

		// setup header panel, adding all buttons
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new FlowLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx");
		label.setFont(new Font("Arial", Font.BOLD, 20));
		this.buttonPanel.add(label);

		btnOpenStock = new JButton("Stock");
		btnOpenStock.setIcon(stockIcon);
		btnOpenStock.setActionCommand("openStock");
		btnOpenStock.addActionListener(this);
		this.buttonPanel.add(btnOpenStock, BorderLayout.CENTER);

		btnOpenOrder = new JButton("Order");
		btnOpenOrder.setIcon(orderIcon);
		btnOpenOrder.setActionCommand("openOrder");
		btnOpenOrder.addActionListener(this);
		this.buttonPanel.add(btnOpenOrder, BorderLayout.CENTER);

		btnOpenSettings = new JButton("Settings");
		btnOpenSettings.setIcon(settingsIcon);
		btnOpenSettings.setActionCommand("openSettings");
		btnOpenSettings.addActionListener(this);
		this.buttonPanel.add(btnOpenSettings, BorderLayout.CENTER);

		btnOpenPatientManager = new JButton("Patients");
		btnOpenPatientManager.setIcon(patientsIcon);
		btnOpenPatientManager.setActionCommand("openPatientManager");
		btnOpenPatientManager.addActionListener(this);
		this.buttonPanel.add(btnOpenPatientManager, BorderLayout.CENTER);

		add(this.buttonPanel, BorderLayout.NORTH);

		mainPanel = new JPanel(new GridLayout(1, 2, (int) (screenDims.width * 0.1), 0));
		mainContainer = new JPanel(new GridBagLayout());

		// font and borders
		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font used for most text elements
		Font nameFont = new Font("Arial", Font.PLAIN, 75); // font used for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // outer
																													// border
																													// for
																													// boxes
																													// and
																													// buttons
																													// https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.1), (int) (screenDims.width * 0.1),
				(int) (screenDims.height * 0.1), (int) (screenDims.width * 0.1)); // inner border for boxes and buttons
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // compounded border for
																								// boxes and buttons

		// add title
		pageTitle.setFont(nameFont);

		GridBagConstraints titleConstraints = new GridBagConstraints(); // constraints for page title

		titleConstraints.gridx = 0;
		titleConstraints.gridy = 0;
		titleConstraints.insets = new Insets(0, 0, (int) (screenDims.height * 0.1), 0);
		pageTitle.setHorizontalAlignment(JLabel.CENTER);
		mainContainer.add(pageTitle, titleConstraints);

		// setup buttons and add to main panel
		searchExisting = new JButton("Search For Existing Patient");
		searchExisting.addActionListener(this);
		addNew = new JButton("Add a New Patient");
		addNew.addActionListener(this);
		searchExisting.setFont(genFont);
		searchExisting.setBorder(textBoxBorder);
		addNew.setFont(genFont);
		addNew.setBorder(textBoxBorder);

		mainPanel.add(searchExisting);
		mainPanel.add(addNew);

		// align buttons on screen in mainContainer
		GridBagConstraints constraints = new GridBagConstraints(); // constraints for buttons

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipady = (int) (screenDims.height * 0.1);
		constraints.anchor = GridBagConstraints.CENTER;
		mainContainer.add(mainPanel, constraints);

		add(mainContainer, BorderLayout.CENTER);

	} // end SearchAddUI

	public void actionPerformed(ActionEvent e) {
		// open stock page
		if (e.getActionCommand().equals("openStock")) {
			System.out.println("Stock");
		} // end if
			// open order page
		if (e.getActionCommand().equals("openOrder")) {
			System.out.println("Order");
		} // end if
			// open patient management page
		if (e.getActionCommand().equals("openPatientManager")) {
			System.out.println("Patients");
		} // end if
			// open search page
		if (e.getActionCommand().equals("Search For Existing Patient")) {
			SearchForPatientUI openSearch = new SearchForPatientUI("ManageRx", patient, patients);
			openSearch.setVisible(true);
			setVisible(false);
		} // end if
			// open add page
		if (e.getActionCommand().equals("Add a New Patient")) {
			EditPatientInfoUI openCreate = new EditPatientInfoUI("ManageRx", null, patients);
			openCreate.setVisible(true);
			setVisible(false);
		} // end if
	} // end actionPerformed
} // end SearchAddUI
