package patientUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.FlatLightLaf;

import PatientManagement.*;
import utilities.*;
import mainUI.loginUI;
import mainUI.settingsUI;
import swingHelper.AppIcon;

public class AddNewInsuranceUI extends JFrame implements ActionListener {

	// patient information
	Patient patient; // currently selected patient
	PatientList patients; // complete list of patients

	// panels
	private JPanel buttonPanel; // panel for header
	private JPanel mainPanel; // main panel containing centreMain and bottomMain
	private JPanel centreMain; // panel to add insurance info
	private JPanel bottomMain; // panel for bottom buttons
	private JPanel headerButtons; // buttons in header

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton cancel; // cancel and go back
	private JButton saveGoBack; // save and go back
	private JButton backButton; // go back
	private JButton saveAddMore; // save and add another insurance plan

	// text elements
	private JLabel insuranceTitle = new JLabel("Insurance Information"); // title for page
	private JLabel insuranceCompanyLabel = new JLabel("Company"); // company label
	private JTextField insuranceCompanyField; // company field
	private JLabel insuranceNumberLabel = new JLabel("Insurance Number"); // number label
	private JTextField insuranceNumberField; // number field
	private JLabel notesLabel = new JLabel("Notes"); // notes label
	private JTextArea notesArea; // notes area
	private JLabel patientName; // name of patient
	private Insets gridBagPadding; // padding for some GridBagLayout elements
	private Insets leftButtonPadding; // padding for left button
	private Insets rightButtonsPadding; // padding for right button

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public AddNewInsuranceUI(String title, Patient patient, PatientList patients) {

		// setup screen attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// initialize patients
		this.patient = patient;
		this.patients = patients;

		// make header
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx"); // header title
		label.setFont(new Font("Arial", Font.BOLD, 20));
		this.buttonPanel.add(label);

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

		headerButtons = new JPanel(new FlowLayout());

		// add header buttons to sub panel
		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		headerButtons.add(btnOpenSettings);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for buttons other than
																				// back button and title of header

		overallButtonConstraints.gridx = 2;
		overallButtonConstraints.gridy = 0;
		overallButtonConstraints.gridwidth = 1;
		overallButtonConstraints.weightx = 0.55;
		overallButtonConstraints.anchor = GridBagConstraints.WEST;
		this.buttonPanel.add(headerButtons, overallButtonConstraints);

		// add header to screen
		add(this.buttonPanel, BorderLayout.NORTH);

		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
														// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // font for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);
		gridBagPadding = new Insets(0, (int) (screenDims.width * 0.07), 0, (int) (screenDims.width * 0.07));
		leftButtonPadding = new Insets((int) (screenDims.height * 0.01), 0, (int) (screenDims.height * 0.01),
				(int) (screenDims.width * 0.25));
		rightButtonsPadding = new Insets((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));

		
		// add patient name to screen
		GridBagConstraints nameConstraints = new GridBagConstraints(); // constraints for patient name

		nameConstraints.fill = GridBagConstraints.BOTH;
		nameConstraints.gridx = 0;
		nameConstraints.gridy = 1;
		nameConstraints.gridwidth = 2;
		nameConstraints.anchor = GridBagConstraints.NORTH;
		patientName = new JLabel(patient.getName());
		patientName.setFont(nameFont);
		patientName.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(patientName, nameConstraints);

		centreMain = new JPanel(new GridLayout(6, 1));

		// add insurance title to screen
		GridBagConstraints insuranceTitleConstraints = new GridBagConstraints(); // constraints for insurance title

		insuranceTitleConstraints.fill = GridBagConstraints.BOTH;
		insuranceTitleConstraints.gridx = 0;
		insuranceTitleConstraints.gridy = 2;
		insuranceTitleConstraints.gridwidth = 1;
		insuranceTitleConstraints.anchor = GridBagConstraints.SOUTH;
		insuranceTitleConstraints.insets = gridBagPadding;
		insuranceTitle.setFont(nameFont);
		insuranceTitle.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(insuranceTitle, insuranceTitleConstraints);

		// add insurance company label and field
		insuranceCompanyField = new JTextField();
		insuranceCompanyField.setBorder(textBoxBorder);
		insuranceCompanyLabel.setFont(genFont);
		insuranceCompanyField.setFont(genFont);
		centreMain.add(insuranceCompanyLabel);
		centreMain.add(insuranceCompanyField);

		// add insurance number label and field
		insuranceNumberField = new JTextField();
		insuranceNumberField.setBorder(textBoxBorder);
		insuranceNumberField.setFont(genFont);
		insuranceNumberLabel.setFont(genFont);
		centreMain.add(insuranceNumberLabel);
		centreMain.add(insuranceNumberField);

		// add notes label and area
		notesArea = new JTextArea();
		notesArea.setBorder(textBoxBorder);
		notesArea.setFont(genFont);
		notesLabel.setFont(genFont);
		centreMain.add(notesLabel);
		JScrollPane scrollBar = new JScrollPane(notesArea); // scroll bar for notes
		centreMain.add(scrollBar);

		centreMain.setBorder(textBoxBorder);

		GridBagConstraints centrePanelConstraints = new GridBagConstraints(); // constraints for centre panel

		centrePanelConstraints.fill = GridBagConstraints.BOTH;
		centrePanelConstraints.gridx = 0;
		centrePanelConstraints.gridy = 3;
		centrePanelConstraints.gridwidth = 2;
		centrePanelConstraints.anchor = GridBagConstraints.CENTER;
		centrePanelConstraints.ipadx = (int) (screenDims.width * 0.5);
		centrePanelConstraints.ipady = (int) (screenDims.height * 0.2);
		centrePanelConstraints.insets = gridBagPadding;
		mainPanel.add(centreMain, centrePanelConstraints);

		
		// add bottom buttons to screen
		bottomMain = new JPanel(new GridBagLayout());

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		saveGoBack = new JButton("Save and Go Back");
		saveGoBack.addActionListener(this);
		saveAddMore = new JButton("Save and Add Another Insurance Plan");
		saveAddMore.addActionListener(this);
		cancel.setFont(genFont);
		saveGoBack.setFont(genFont);
		saveAddMore.setFont(genFont);
		cancel.setBorder(textBoxBorder);
		saveGoBack.setBorder(textBoxBorder);
		saveAddMore.setBorder(textBoxBorder);

		// add cancel button
		GridBagConstraints cancelConstraints = new GridBagConstraints(); // constraints for cancel button

		cancelConstraints.fill = GridBagConstraints.BOTH;
		cancelConstraints.gridx = 0;
		cancelConstraints.gridy = 1;
		cancelConstraints.gridwidth = 1;
		cancelConstraints.anchor = GridBagConstraints.WEST;
		cancelConstraints.weightx = 0.1;
		cancelConstraints.insets = leftButtonPadding;
		bottomMain.add(cancel, cancelConstraints);

		// add save and go back button
		GridBagConstraints saveGoBackConstraints = new GridBagConstraints(); // constraints for the save and go back button

		saveGoBackConstraints.fill = GridBagConstraints.BOTH;
		saveGoBackConstraints.gridx = 1;
		saveGoBackConstraints.gridy = 1;
		saveGoBackConstraints.gridwidth = 1;
		saveGoBackConstraints.anchor = GridBagConstraints.EAST;
		saveGoBackConstraints.insets = rightButtonsPadding;
		bottomMain.add(saveGoBack, saveGoBackConstraints);

		// add save and add more button
		GridBagConstraints saveAddMoreConstraints = new GridBagConstraints(); // constraints for save and add more button

		saveAddMoreConstraints.fill = GridBagConstraints.BOTH;
		saveAddMoreConstraints.gridx = 2;
		saveAddMoreConstraints.gridy = 1;
		saveAddMoreConstraints.gridwidth = 1;
		saveAddMoreConstraints.anchor = GridBagConstraints.EAST;
		saveAddMoreConstraints.insets = rightButtonsPadding;
		bottomMain.add(saveAddMore, saveAddMoreConstraints);

		// add bottom buttons
		GridBagConstraints buttonConstraints = new GridBagConstraints(); // constraints for all bottom buttons

		buttonConstraints.fill = GridBagConstraints.BOTH;
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 5;
		buttonConstraints.gridwidth = 3;
		buttonConstraints.anchor = GridBagConstraints.SOUTH;
		buttonConstraints.insets = new Insets((int) (screenDims.height * 0.1), 0, 0, 0);
		mainPanel.add(bottomMain, buttonConstraints);

		add(mainPanel, BorderLayout.CENTER);

	} // end AddNewInsuranceUI

	public void actionPerformed(ActionEvent e) {
		// open stock button pressed
		if (e.getActionCommand().equals("openStock")) {
			System.out.println("Stock");
		} // end if
		// open order button pressed
		if (e.getActionCommand().equals("openOrder")) {
			System.out.println("Order");
		} // end if
		// open settings button pressed
		if (e.getActionCommand().equals("openSettings")) {
			System.out.println("Settings");
		} // end if
		// open patient manager button pressed
		if (e.getActionCommand().equals("openPatientManager")) {
			// open patient manager page
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
		// cancel or back buttons pressed
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			// open view insurance page
			ViewInsuranceUI openView = new ViewInsuranceUI("ManageRx", patient, patients);
			openView.setVisible(true);
			setVisible(false);
		} // end if
		// save and go back button pressed
		if (e.getActionCommand().equals("Save and Go Back")) {
			// add the new insurance to patient and open view insurance page
			patient.addNewInsuranceInfo(insuranceCompanyField.getText().trim(),
					Integer.parseInt(insuranceNumberField.getText().trim()), notesArea.getText());
			//SQLHelper.addInsurance(insuranceCompanyField.getText().trim(), insuranceNumberField.getText().trim(), notesArea.getText());
			ViewInsuranceUI openView = new ViewInsuranceUI("ManageRx", patient, patients);
			openView.setVisible(true);
			setVisible(false);
		} // end if
		// save and add another button pressed
		if (e.getActionCommand().equals("Save and Add Another Insurance Plan")) {
			// add new insurance to patient and clear field
			patient.addNewInsuranceInfo(insuranceCompanyField.getText().trim(),
					Integer.parseInt(insuranceNumberField.getText().trim()), notesArea.getText());
			//SQLHelper.addInsurance(insuranceCompanyField.getText().trim(), insuranceNumberField.getText().trim(), notesArea.getText());
			insuranceCompanyField.setText("");
			insuranceNumberField.setText("");
			notesArea.setText("");
		} // end if
	} // end actionPerformed
} // end AddNewInsuranceUI
