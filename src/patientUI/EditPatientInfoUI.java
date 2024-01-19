
/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 23, 2023
 * @Last Modified: December 16, 2023
 * @Description: Edit patient information page/create new patient page in the patient management section of ManageRx
 ***********************************************
 */

package patientUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import java.util.*;

import com.formdev.flatlaf.FlatLightLaf;

import utilities.*;
import PatientManagement.*;
import inventory.AllStock;
import mainUI.loginUI;
import mainUI.orderUI;
import mainUI.settingsUI;
import mainUI.stockUI;
import swingHelper.AppIcon;

public class EditPatientInfoUI extends JFrame implements ActionListener, FocusListener {

	// app information
	private Patient patient; // patient used if editing a current patient
	private PatientList patients; // list containing all patients
	private Patient newPatient; // patient used if creating a new patient
	private AllStock stock;

	// panels
	private JPanel buttonPanel; // panel for header
	private JPanel mainPanel; // main panel containing leftMain, midMain, rightMain, bottomMain, and
								// bottomButtonsMain
	private JPanel leftMain; // left panel in mainPanel
	private JPanel midMain; // middle panel in mainPanel
	private JPanel rightMain; // right panel in mainPanel
	private JPanel bottomMain; // bottom panel in mainPanel
	private JPanel bottomButtonsMain; // button panel at the bottom of the screen -- in main panel
	private JPanel insuranceGrid; // panel for manage insurance button
	private JPanel headerButtons; // panel for buttons in header other than back

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient management page
	private JButton backButton; // go back to previous page

	// main buttons
	private JButton cancel; // cancel button
	private JButton saveRecord; // save patient info
	private JButton prescriptions; // view prescriptions button
	private JButton manageInsurance; // manage insurance button

	// text elements
	private JLabel familyDoc = new JLabel("Family Doctor"); // family doctor title
	private JLabel personalInfo = new JLabel("Personal Information"); // personal info title
	private JLabel contactInfo = new JLabel("Contact Information"); // contact info title
	private JLabel insuranceInfo = new JLabel("Insurance"); // insurance info title
	private JLabel dateOfBirthLabel = new JLabel("Date of Birth (DD/MM/YYYY)"); // date of birth label
	private JFormattedTextField dateOfBirthField; // date of birth field
	private JLabel healthCardNumLabel = new JLabel("Health Card Number"); // health card number label
	private JFormattedTextField healthCardNumField; // health card number field
	private JLabel emailLabel = new JLabel("Email"); // email label
	private JTextField emailField; // email field
	private JLabel phoneLabel = new JLabel("Phone Number"); // phone number label
	private JFormattedTextField phoneField; // phone number field
	private JLabel addressLabel = new JLabel("Address"); // address label
	private JTextField addressField; // address field
	private JLabel additionalNotesLabel = new JLabel("Additional Notes"); // additional notes label
	private JTextArea additionalNotesArea; // additional notes area
	private JScrollPane additionalNotes; // additional notes scroll bar
	private JLabel docNameLabel = new JLabel("Name"); // doctor name label
	private JTextField docNameField; // doctor name field
	private JLabel docPhoneNumberLabel = new JLabel("Phone Number"); // doctor phone number label
	private JFormattedTextField docPhoneNumberField; // doctor phone number field
	private JLabel docFaxLabel = new JLabel("Fax Number");
	private JTextField docFaxField;
	private JLabel docAddressLabel = new JLabel("Address"); // doctor address label
	private JTextField docAddressField; // doctor address field
	private JLabel patientNameLabel = new JLabel("Name"); // patient name label
	private JTextField patientNameField; // patient name field
	private JLabel weightLabel = new JLabel("Weight (kg)");
	private JTextField weightField;
	private JLabel genderLabel = new JLabel("Gender");
	private JTextField genderField;

	// formats
	MaskFormatter dateOfBirthFormat;
	MaskFormatter healthCardFormat;
	MaskFormatter phoneFormat;

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public EditPatientInfoUI(String title, Patient patient, PatientList patients, AllStock stock) throws ParseException {

		dateOfBirthFormat = new MaskFormatter("##/##/####");
		healthCardFormat = new MaskFormatter("#### - ### - ### - UU");
		phoneFormat = new MaskFormatter("(###) ### - ####");

		// setup screen attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
//		screenDims.height /=1.5;
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.patient = patient;
		this.patients = patients;
		this.stock = stock;
		newPatient = new Patient();

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font used for most text elements
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // font used for names and titles
		Color textBoxFill = new Color(204, 204, 204); // fill for textboxes
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // outer
																													// border
																													// for
																													// boxes
																													// and
																													// buttons
																													// https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01)); // inner border for boxes and
																					// buttons
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // compounded border for
																								// boxes and buttons

		// set all text boxes to default values if no patient
		if (patient == null) {
			patientNameField = new JTextField("First Last");
			dateOfBirthField = new JFormattedTextField(dateOfBirthFormat);
			healthCardNumField = new JFormattedTextField(healthCardFormat);
			emailField = new JTextField("example@domain.com");
			phoneField = new JFormattedTextField(phoneFormat);
			addressField = new JTextField("123 ABC Street, City");
			additionalNotesArea = new JTextArea(
					"Medical Conditions:\n\n" + "Lifestyle habits:\n\n" + "Allergies/Dietary Restrictions:\n");
			docNameField = new JTextField("Dr. First Last");
			docPhoneNumberField = new JFormattedTextField(phoneFormat);
			docAddressField = new JTextField("123 ABC Street, City");
			genderField = new JTextField("");
			weightField = new JTextField("");
			docFaxField = new JFormattedTextField(phoneFormat);

			patientNameField.setForeground(textBoxFill);
			emailField.setForeground(textBoxFill);
			addressField.setForeground(textBoxFill);
			docNameField.setForeground(textBoxFill);
			docAddressField.setForeground(textBoxFill);

			// focus listeners to get rid of default text if pressed
			patientNameField.addFocusListener(this);
			emailField.addFocusListener(this);
			addressField.addFocusListener(this);
			docNameField.addFocusListener(this);
			docAddressField.addFocusListener(this);
			// set values to patient values if there is a patient
		} // end if
		else {
			patientNameField = new JTextField(patient.getName());
			dateOfBirthField = new JFormattedTextField(dateOfBirthFormat);
			dateOfBirthField.setText(patient.getBirthday());
			healthCardNumField = new JFormattedTextField(healthCardFormat);
			healthCardNumField.setText(patient.getHealthCardNumber());
			emailField = new JTextField(patient.getEmail());
			phoneField = new JFormattedTextField(phoneFormat);
			phoneField.setText(String.valueOf(patient.getPhoneNumber()));
			addressField = new JTextField(patient.getAddress());
			docNameField = new JTextField(patient.getFamilyDoctorName());
			docPhoneNumberField = new JFormattedTextField(phoneFormat);
			docPhoneNumberField.setText(String.valueOf(patient.getFamilyDoctorNumber()));
			docAddressField = new JTextField(patient.getFamilyDoctorAddress());
			additionalNotesArea = new JTextArea(patient.getAdditionalNotes());
			genderField = new JTextField(patient.getGender());
			weightField = new JTextField(String.valueOf(patient.getWeight()));
			docFaxField = new JTextField(patient.getFamilyDoctor().getFax());
		} // end else

		// add buttons and title to header
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx");
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
		headerButtons.add(btnOpenSettings);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for buttons other than
																				// back button in header

		overallButtonConstraints.gridx = 2;
		overallButtonConstraints.gridy = 0;
		overallButtonConstraints.gridwidth = 1;
		overallButtonConstraints.weightx = 0.55;
		overallButtonConstraints.anchor = GridBagConstraints.WEST;
		this.buttonPanel.add(headerButtons, overallButtonConstraints);

		add(this.buttonPanel, BorderLayout.NORTH);

		// add main panel
		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
														// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

		GridBagConstraints personalInfoConstraints = new GridBagConstraints(); // constraints for personal info title

		personalInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
		personalInfoConstraints.gridx = 0;
		personalInfoConstraints.gridy = 1;
		personalInfoConstraints.gridwidth = 1;
		personalInfoConstraints.anchor = GridBagConstraints.NORTH;
		personalInfo.setFont(nameFont);
		personalInfo.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(personalInfo, personalInfoConstraints);

		GridBagConstraints contactInfoConstraints = new GridBagConstraints(); // constraints for contact info title
		contactInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
		contactInfoConstraints.gridx = 1;
		contactInfoConstraints.gridy = 1;
		contactInfoConstraints.gridwidth = 1;
		contactInfoConstraints.anchor = GridBagConstraints.NORTH;
		contactInfo.setFont(nameFont);
		contactInfo.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(contactInfo, contactInfoConstraints);

		GridBagConstraints familyDocTitleConstraints = new GridBagConstraints(); // constraints for family doctor title
		familyDocTitleConstraints.fill = GridBagConstraints.HORIZONTAL;
		familyDocTitleConstraints.gridx = 2;
		familyDocTitleConstraints.gridy = 1;
		familyDocTitleConstraints.anchor = GridBagConstraints.NORTH;
		familyDoc.setHorizontalAlignment(JLabel.CENTER);
		familyDoc.setFont(nameFont);
		mainPanel.add(familyDoc, familyDocTitleConstraints);

		// add left main elements
		leftMain = new JPanel(new GridLayout(7, 1, 0, (int) (screenDims.height * 0.005)));

		patientNameField.setBorder(textBoxBorder);
		patientNameLabel.setFont(genFont);
		patientNameField.setFont(genFont);
		leftMain.add(patientNameLabel);
		leftMain.add(patientNameField);

		dateOfBirthField.setBorder(textBoxBorder);
		dateOfBirthLabel.setFont(genFont);
		dateOfBirthField.setFont(genFont);
		leftMain.add(dateOfBirthLabel);
		leftMain.add(dateOfBirthField);

		healthCardNumField.setBorder(textBoxBorder);
		healthCardNumLabel.setFont(genFont);
		healthCardNumField.setFont(genFont);
		leftMain.add(healthCardNumLabel);
		leftMain.add(healthCardNumField);

		JPanel weightGenderGrid = new JPanel(new GridLayout(1, 0, (int) (screenDims.width * 0.01), 0));

		genderField.setBorder(textBoxBorder);
		genderLabel.setFont(genFont);
		genderField.setFont(genFont);
		genderLabel.setHorizontalAlignment(JLabel.RIGHT);
		weightGenderGrid.add(genderLabel);
		weightGenderGrid.add(genderField);

		weightField.setBorder(textBoxBorder);
		weightLabel.setFont(genFont);
		weightField.setFont(genFont);
		weightLabel.setHorizontalAlignment(JLabel.RIGHT);
		weightGenderGrid.add(weightLabel);
		weightGenderGrid.add(weightField);

		leftMain.add(weightGenderGrid);

		// fix sizing glitch from
		// https://stackoverflow.com/questions/4061010/setmaximumsize-not-working-in-java
		leftMain.setMaximumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		leftMain.setPreferredSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		leftMain.setMinimumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		leftMain.setSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		leftMain.revalidate();

		GridBagConstraints lConstraints = new GridBagConstraints(); // constraints for left panel

		lConstraints.fill = GridBagConstraints.HORIZONTAL;
		lConstraints.gridx = 0;
		lConstraints.gridy = 2;
		lConstraints.gridheight = 1;
		lConstraints.anchor = GridBagConstraints.NORTH;
		lConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		lConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(leftMain, lConstraints);

		// add insurance button and title to left panel

		insuranceInfo.setFont(nameFont);
		manageInsurance = new JButton("Manage Insurance Information");
		manageInsurance.addActionListener(this);
		manageInsurance.setBorder(textBoxBorder);
		manageInsurance.setFont(genFont);

		insuranceGrid = new JPanel(new GridLayout(2, 1));

		insuranceInfo.setHorizontalAlignment(JLabel.CENTER);
		insuranceGrid.add(insuranceInfo);
		insuranceGrid.add(manageInsurance);

		GridBagConstraints insuranceConstraints = new GridBagConstraints(); // constraints for insurance button and
																			// title

		insuranceConstraints.fill = GridBagConstraints.HORIZONTAL;
		insuranceConstraints.gridx = 0;
		insuranceConstraints.gridy = 3;
		insuranceConstraints.gridheight = 1;
		insuranceConstraints.anchor = GridBagConstraints.CENTER;
		insuranceConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		insuranceConstraints.ipadx = screenDims.width / 7;
		insuranceConstraints.ipady = screenDims.height / 10;
		mainPanel.add(insuranceGrid, insuranceConstraints);

		// add middle panel elements

		midMain = new JPanel(new GridLayout(6, 1));

		emailField.setBorder(textBoxBorder);
		emailLabel.setFont(genFont);
		emailField.setFont(genFont);
		midMain.add(emailLabel);
		midMain.add(emailField);

		phoneField.setBorder(textBoxBorder);
		phoneLabel.setFont(genFont);
		phoneField.setFont(genFont);
		midMain.add(phoneLabel);
		midMain.add(phoneField);

		addressField.setBorder(textBoxBorder);
		addressLabel.setFont(genFont);
		addressField.setFont(genFont);
		midMain.add(addressLabel);
		midMain.add(addressField);

		GridBagConstraints mConstraints = new GridBagConstraints(); // constraints for middle panel

		mConstraints.fill = GridBagConstraints.HORIZONTAL;
		mConstraints.gridx = 1;
		mConstraints.gridy = 2;
		mConstraints.gridheight = 2;
		mConstraints.anchor = GridBagConstraints.NORTH;
		mConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(midMain, mConstraints);

		// add right panel elements

		rightMain = new JPanel(new GridLayout(8, 1));

		docNameField.setBorder(textBoxBorder);
		docNameLabel.setFont(genFont);
		docNameField.setFont(genFont);
		rightMain.add(docNameLabel);
		rightMain.add(docNameField);

		docPhoneNumberField.setBorder(textBoxBorder);
		docPhoneNumberLabel.setFont(genFont);
		docPhoneNumberField.setFont(genFont);
		rightMain.add(docPhoneNumberLabel);
		rightMain.add(docPhoneNumberField);

		docFaxField.setBorder(textBoxBorder);
		docFaxLabel.setFont(genFont);
		docFaxField.setFont(genFont);
		rightMain.add(docFaxLabel);
		rightMain.add(docFaxField);

		docAddressField.setBorder(textBoxBorder);
		docAddressLabel.setFont(genFont);
		docAddressField.setFont(genFont);
		rightMain.add(docAddressLabel);
		rightMain.add(docAddressField);

		// fix sizing glitch from
		// https://stackoverflow.com/questions/4061010/setmaximumsize-not-working-in-java
		rightMain.setMaximumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		rightMain.setPreferredSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		rightMain.setMinimumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		rightMain.setSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.4)));
		rightMain.revalidate();

		GridBagConstraints rConstraints = new GridBagConstraints(); // constraints for right panel

		rConstraints.fill = GridBagConstraints.HORIZONTAL;
		rConstraints.gridx = 2;
		rConstraints.gridy = 2;
		rConstraints.gridheight = 1;
		rConstraints.anchor = GridBagConstraints.NORTH;
		rConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		rConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(rightMain, rConstraints);

		// add additional notes section

		bottomMain = new JPanel(new GridLayout(3, 1));

		additionalNotesArea.setBorder(textBoxBorder);
		additionalNotesLabel.setFont(genFont);
		additionalNotesLabel.setVerticalAlignment(JLabel.BOTTOM);
		additionalNotesArea.setFont(genFont);
		additionalNotesArea.setLineWrap(true);
		additionalNotes = new JScrollPane(additionalNotesArea);
		additionalNotes.setMinimumSize(new Dimension(0, screenDims.height / 8));
		bottomMain.add(additionalNotesLabel);
		bottomMain.add(additionalNotes);

		GridBagConstraints bConstraints = new GridBagConstraints(); // constraints for additional notes

		bConstraints.fill = GridBagConstraints.HORIZONTAL;
		bConstraints.gridx = 1;
		bConstraints.gridy = 3;
		bConstraints.gridheight = 1;
		bConstraints.gridwidth = 2;
		bConstraints.anchor = GridBagConstraints.NORTH;
		bConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomMain, bConstraints);

		// add bottom buttons to page

		bottomButtonsMain = new JPanel(new GridLayout(1, 2, (int) (screenDims.width * 0.05), 0));

		cancel = new JButton("Cancel");
		cancel.setBorder(textBoxBorder);
		cancel.addActionListener(this);
		saveRecord = new JButton("Save Record");
		saveRecord.setBorder(textBoxBorder);
		saveRecord.addActionListener(this);
		cancel.setFont(genFont);
		saveRecord.setFont(genFont);
		bottomButtonsMain.add(cancel);
		bottomButtonsMain.add(saveRecord);

		GridBagConstraints buttonConstraints = new GridBagConstraints(); // constraints for bottom buttons

		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.gridx = 2;
		buttonConstraints.gridy = 4;
		buttonConstraints.gridheight = 1;
		buttonConstraints.anchor = GridBagConstraints.NORTH;
		buttonConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomButtonsMain, buttonConstraints);

		add(mainPanel, BorderLayout.CENTER);

		// add edit prescriptions button

		prescriptions = new JButton("Edit Prescriptions");
		prescriptions.addActionListener(this);
		prescriptions.setFont(genFont);
		prescriptions.setBorder(textBoxBorder);

		GridBagConstraints prescriptionsConstraints = new GridBagConstraints(); // constraints for prescription button

		prescriptionsConstraints.fill = GridBagConstraints.BOTH;
		prescriptionsConstraints.gridx = 0;
		prescriptionsConstraints.gridy = 4;
		prescriptionsConstraints.ipadx = screenDims.width / 7;
		prescriptionsConstraints.anchor = GridBagConstraints.NORTH;
		mainPanel.add(prescriptions, prescriptionsConstraints);

	} // end EditPatientInfoUI

	public void actionPerformed(ActionEvent e) {
		//open stock button pressed
		if (e.getActionCommand().equals("openStock")) {
			stockUI openStock = new stockUI(stock);
			openStock.setVisible(true);
			setVisible(false);
		} // end if
		// open order button pressed
		if (e.getActionCommand().equals("openOrder")) {
			orderUI openOrder = new orderUI();
			openOrder.setVisible(true);
			setVisible(false);
		} // end if
		if (e.getActionCommand().equals("openPatientManager")) {
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients, stock);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			if (patient == null) {
				SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", null, patients, stock);
				openSearchAdd.setVisible(true);
				setVisible(false);
			} // end if
			else {
				ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patient, patients, stock);
				openManage.setVisible(true);
				setVisible(false);
			} // end else
		} // end if
			// update patient when save record pressed
		if (e.getActionCommand().equals("Save Record")) {
			SQLHelper SQLHelper = new SQLHelper(); // used to interact with sql
			// ensure required fields are filled in
			if ((patientNameField.getText().length() > 0 && dateOfBirthField.getText().length() > 0
					&& addressField.getText().length() > 0 && emailField.getText().length() > 0
					&& phoneField.getText().length() > 0 && healthCardNumField.getText().length() > 0)
					&& !(patientNameField.getText().equals("First Last"))) {
				// if patient is null, create a new one
				if (patient == null) {
					newPatient.setName(patientNameField.getText().trim());
					newPatient.setBirthYear(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 4).trim()));
					newPatient.setDateOfBirthMonth(Integer.parseInt(
							dateOfBirthField.getText().trim().substring(dateOfBirthField.getText().indexOf('/') + 1,
									dateOfBirthField.getText().indexOf('/') + 3).trim()));
					newPatient.setDateOfBirthDay(Integer.parseInt(
							dateOfBirthField.getText().substring(0, dateOfBirthField.getText().indexOf('/')).trim()));
					newPatient.setAddress(addressField.getText().trim());
					newPatient.setEmail(emailField.getText().trim());
					newPatient.setPhoneNumber(phoneField.getText().trim());
					newPatient.setHealthCardNumber(healthCardNumField.getText().trim());
					newPatient.newFamilyDoctor(docNameField.getText().trim(), docAddressField.getText().trim(),
							docPhoneNumberField.getText().trim(), docFaxField.getText());
					newPatient.setAdditionalNotes(additionalNotesArea.getText().trim());
					newPatient.setId(patients.numRecs());
					newPatient.setGender(genderField.getText());
					newPatient.setWeight(Double.parseDouble(weightField.getText()));
					patients.insert(newPatient);
					SQLHelper.addPatient(newPatient);
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", newPatient, patients, stock);
					openManage.setVisible(true);
					setVisible(false);
				} // end if
					// if patient isn't null, update current one
				else {
					patient.setName(patientNameField.getText().trim());
					SQLHelper.update("PatientInfo", "name", patient.getName(), patient.getId());
					patient.setBirthYear(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 4).trim()));
					SQLHelper.update("PatientInfo", "birthYear", patient.getBirthYear(), patient.getId());
					patient.setDateOfBirthMonth(Integer.parseInt(
							dateOfBirthField.getText().trim().substring(dateOfBirthField.getText().indexOf('/') + 1,
									dateOfBirthField.getText().indexOf('/') + 3).trim()));
					SQLHelper.update("PatientInfo", "birthMonth", patient.getDateOfBirthMonth(), patient.getId());
					patient.setDateOfBirthDay(Integer.parseInt(
							dateOfBirthField.getText().substring(0, dateOfBirthField.getText().indexOf('/')).trim()));
					SQLHelper.updateBG("PatientInfo", "birthDay", patient.getDateOfBirthDay(), patient.getId());
					patient.setAddress(addressField.getText().trim());
					SQLHelper.updateBG("PatientInfo", "address", patient.getAddress(), patient.getId());
					patient.setEmail(emailField.getText().trim());
					SQLHelper.updateBG("PatientInfo", "email", patient.getEmail(), patient.getId());
					patient.setPhoneNumber(phoneField.getText().trim());
					SQLHelper.updateBG("PatientInfo", "phoneNumber", patient.getPhoneNumber(), patient.getId());
					patient.setHealthCardNumber(healthCardNumField.getText().trim());
					SQLHelper.updateBG("PatientInfo", "healthCard", patient.getHealthCardNumber(), patient.getId());
					patient.newFamilyDoctor(docNameField.getText().trim(), docAddressField.getText().trim(),
							docPhoneNumberField.getText().trim(), docFaxField.getText());
					SQLHelper.updateBG("PatientInfo", "familyDoctorName", patient.getFamilyDoctorName(),
							patient.getId());
					SQLHelper.updateBG("PatientInfo", "familyDoctorAddress", patient.getFamilyDoctorAddress(),
							patient.getId());
					SQLHelper.updateBG("PatientInfo", "familyDoctorPhoneNumber", patient.getFamilyDoctorNumber(),
							patient.getId());
					patient.setGender(genderField.getText());
					SQLHelper.updateBG("PatientInfo", "gender", patient.getGender(),
							patient.getId());
					patient.setWeight(Double.parseDouble(weightField.getText()));
					SQLHelper.updateBG("PatientInfo", "weight", patient.getWeight(),
							patient.getId());
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patient, patients, stock);
					openManage.setVisible(true);
					setVisible(false);
				} // end else
			} // end if
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to save patient details.");
			} // end else
		} // end if
			// open current prescriptions
		if (e.getActionCommand().equals("Edit Prescriptions")) {
			if (patient == null) {
				newPatient.newPrescriptionList();
				CurrentPrescriptions openPrescriptions = new CurrentPrescriptions("ManageRx", newPatient, patients,
						false, stock);
				openPrescriptions.setVisible(true);
				setVisible(false);
			} // end if
			else {
				CurrentPrescriptions openPrescriptions = new CurrentPrescriptions("ManageRx", patient, patients, false, stock);
				openPrescriptions.setVisible(true);
				setVisible(false);
			} // end else
		} // end if
			// open insurance page
		if (e.getActionCommand().equals("Manage Insurance Information")) {
			if (patient == null) {
				newPatient.newInsuranceList();
				ViewInsuranceUI openInsurance = new ViewInsuranceUI("ManageRx", newPatient, patients, stock);
				openInsurance.setVisible(true);
				setVisible(false);
			} // end if
			else {
				ViewInsuranceUI openInsurance = new ViewInsuranceUI("ManageRx", patient, patients, stock);
				openInsurance.setVisible(true);
				setVisible(false);
			} // end else
		} // end if
	} // end actionPerformed

	// https://stackoverflow.com/questions/18103839/display-disappear-default-text-in-textfield-when-user-enters-data-erase-the-text
	// used for focus event
	public void focusGained(FocusEvent e) {
		if (e.getComponent().equals(patientNameField)) {
			if (patientNameField.getText().trim().equals("First Last")) {
				patientNameField.setText("");
				patientNameField.setForeground(Color.black);
			} // end if
		} // end if
		if (e.getComponent().equals(emailField)) {
			if (emailField.getText().trim().equals("example@domain.com")) {
				emailField.setText("");
				emailField.setForeground(Color.black);
			} // end if
		} // end if
		if (e.getComponent().equals(addressField)) {
			if (addressField.getText().trim().equals("123 ABC Street, City")) {
				addressField.setText("");
				addressField.setForeground(Color.black);
			} // end if
		} // end if
		if (e.getComponent().equals(docNameField)) {
			if (docNameField.getText().trim().equals("Dr. First Last")) {
				docNameField.setText("");
				docNameField.setForeground(Color.black);
			} // end if
		} // end if
		if (e.getComponent().equals(docAddressField)) {
			if (docAddressField.getText().trim().equals("123 ABC Street, City")) {
				docAddressField.setText("");
				docAddressField.setForeground(Color.black);
			} // end if
		} // end if
	} // end focusGained

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	} // end focusLost
} // end EditPatientInfoUI
