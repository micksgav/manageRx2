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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import java.util.*;

import com.formdev.flatlaf.FlatLightLaf;

import PatientManagement.*;
import mainUI.loginUI;
import mainUI.settingsUI;
import swingHelper.AppIcon;

public class EditPatientInfoUI extends JFrame implements ActionListener, FocusListener {
	private JButton openSettings = new JButton();
	private JButton openPatientManager = new JButton();
	private JButton openStock = new JButton();
	private JButton openOrder = new JButton();
	private loginUI login = new loginUI();
	private settingsUI settings = new settingsUI();
	private patientManagerUI patientManager = new patientManagerUI();
	// private StockUI stock = new StockUI();
	// private OrderUI order = new OrderUI();

	private Patient patient;
	private PatientList patients;
	private Patient newPatient;

	// panels
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel leftMain;
	private JPanel midMain;
	private JPanel rightMain;
	private JPanel bottomMain;
	private JPanel additionalInfoButtons;
	private JPanel bottomButtonsMain;
	private JPanel insuranceGrid;
	private JPanel headerButtons;

	// header buttons
	private JButton btnOpenStock;
	private JButton btnOpenOrder;
	private JButton btnOpenSettings;
	private JButton btnOpenPatientManager;
	private JButton backButton;

	// main buttons
	private JButton cancel;
	private JButton saveRecord;
	private JButton allMedicalConditions;
	private JButton allLifestyleHabits;
	private JButton allAllergies;
	private JButton prescriptions;

	// text elements
	private JLabel familyDoc = new JLabel("Family Doctor");
	private JLabel personalInfo = new JLabel("Personal Information");
	private JLabel contactInfo = new JLabel("Contact Information");
	private JLabel insuranceInfo = new JLabel("Insurance");
	private JLabel dateOfBirthLabel = new JLabel("Date of Birth");
	private JTextField dateOfBirthField = new JTextField("0000-00-00");
	private JLabel healthCardNumLabel = new JLabel("Health Card Number");
	private JTextField healthCardNumField = new JTextField("0000-000-000-AB");
	private JLabel emailLabel = new JLabel("Email");
	private JTextField emailField = new JTextField("example");
	private JLabel phoneLabel = new JLabel("Phone Number");
	private JTextField phoneField = new JTextField("(000) 000-0000");
	private JLabel addressLabel = new JLabel("Address");
	private JTextField addressField = new JTextField("123 ABC St.");
	private JButton manageInsurance;
	private JButton removeInsurance;
	private JLabel additionalNotesLabel = new JLabel("Additional Notes");
	private JTextArea additionalNotesArea = new JTextArea();
	private JScrollPane additionalNotes;
	private JLabel docNameLabel = new JLabel("Name");
	private JTextField docNameField = new JTextField();
	private JLabel docPhoneNumberLabel = new JLabel("Phone Number");
	private JTextField docPhoneNumberField;
	private JLabel docAddressLabel = new JLabel("Address");
	private JTextField docAddressField = new JTextField("123 ABC Rd.");
	private JLabel patientNameLabel = new JLabel("Name");
	private JLabel prescriptionsLabel = new JLabel("Prescriptions");
	private JTextField patientNameField;
	private Insets textFieldPadding;

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public EditPatientInfoUI(String title, Patient patient, PatientList patients) {
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
		newPatient = new Patient();

		Font genFont = new Font("Arial", Font.PLAIN, 25);
		Font nameFont = new Font("Arial", Font.PLAIN, 35);
		Color textBoxFill = new Color(204, 204, 204);
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);
		// textFieldPadding = new Insets((int) (screenDims.height *0.15), (int)
		// (screenDims.width *0.02), (int) (screenDims.height *0.1), (int)
		// (screenDims.width *0.02));

		if (patient == null) {
			patientNameField = new JTextField("First Last");
			dateOfBirthField = new JTextField("DD/MM/YYYY");
			healthCardNumField = new JTextField("0000-000-000-AB");
			emailField = new JTextField("example@domain.com");
			phoneField = new JTextField("(000) 000-0000");
			addressField = new JTextField("123 ABC Street, City");
			additionalNotesArea = new JTextArea(
					"Medical Conditions:\n\n" + "Lifestyle habits:\n\n" + "Allergies/Dietary Restrictions:\n");
			docNameField = new JTextField("Dr. First Last");
			docPhoneNumberField = new JTextField("(000) 000-0000");
			docAddressField = new JTextField("123 ABC Street, City");

			patientNameField.setForeground(textBoxFill);
			dateOfBirthField.setForeground(textBoxFill);
			healthCardNumField.setForeground(textBoxFill);
			emailField.setForeground(textBoxFill);
			phoneField.setForeground(textBoxFill);
			addressField.setForeground(textBoxFill);
			docNameField.setForeground(textBoxFill);
			docPhoneNumberField.setForeground(textBoxFill);
			docAddressField.setForeground(textBoxFill);

			patientNameField.addFocusListener(this);
			dateOfBirthField.addFocusListener(this);
			healthCardNumField.addFocusListener(this);
			emailField.addFocusListener(this);
			phoneField.addFocusListener(this);
			addressField.addFocusListener(this);
			docNameField.addFocusListener(this);
			docPhoneNumberField.addFocusListener(this);
			docAddressField.addFocusListener(this);
		} else {
			patientNameField = new JTextField(patient.getName());
			dateOfBirthField = new JTextField(patient.getBirthday());
			healthCardNumField = new JTextField(patient.getHealthCardNumber());
			emailField = new JTextField(patient.getEmail());
			phoneField = new JTextField(String.valueOf(patient.getPhoneNumber()));
			addressField = new JTextField(patient.getAddress());
			docNameField = new JTextField(patient.getFamilyDoctorName());
			docPhoneNumberField = new JTextField(String.valueOf(patient.getFamilyDoctorNumber()));
			docAddressField = new JTextField(patient.getFamilyDoctorAddress());
			additionalNotesArea = new JTextArea(patient.getAdditionalNotes());
		}

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
		
		backButton = new JButton("Back");
		backButton.addActionListener(this);

		GridBagConstraints backConstraints = new GridBagConstraints();

		backConstraints.gridx = 0;
		backConstraints.gridy = 0;
		backConstraints.gridwidth = 1;
		backConstraints.anchor = GridBagConstraints.WEST;
		backConstraints.ipadx = (int) (screenDims.width * 0.02);
		backConstraints.weightx = 0.45;
		backConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		this.buttonPanel.add(backButton, backConstraints);
		
		headerButtons = new JPanel(new FlowLayout());
		
		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		headerButtons.add(btnOpenSettings);
		headerButtons.add(btnOpenPatientManager);
		
		GridBagConstraints overallButtonConstraints = new GridBagConstraints();
		
		overallButtonConstraints.gridx = 2;
		overallButtonConstraints.gridy = 0;
		overallButtonConstraints.gridwidth = 1;
		overallButtonConstraints.weightx = 0.55;
		overallButtonConstraints.anchor = GridBagConstraints.WEST;
		this.buttonPanel.add(headerButtons, overallButtonConstraints);

		add(this.buttonPanel, BorderLayout.NORTH);

		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
														// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

		GridBagConstraints personalInfoConstraints = new GridBagConstraints();
		
		personalInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
		personalInfoConstraints.gridx = 0;
		personalInfoConstraints.gridy = 1;
		personalInfoConstraints.gridwidth = 1;
		personalInfoConstraints.anchor = GridBagConstraints.NORTH;
		personalInfo.setFont(nameFont);
		personalInfo.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(personalInfo, personalInfoConstraints);

		GridBagConstraints contactInfoConstraints = new GridBagConstraints();
		contactInfoConstraints.fill = GridBagConstraints.HORIZONTAL;
		contactInfoConstraints.gridx = 1;
		contactInfoConstraints.gridy = 1;
		contactInfoConstraints.gridwidth = 1;
		contactInfoConstraints.anchor = GridBagConstraints.NORTH;
		contactInfo.setFont(nameFont);
		contactInfo.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(contactInfo, contactInfoConstraints);

		GridBagConstraints familyDocTitleConstraints = new GridBagConstraints();
		familyDocTitleConstraints.fill = GridBagConstraints.HORIZONTAL;
		familyDocTitleConstraints.gridx = 2;
		familyDocTitleConstraints.gridy = 1;
		familyDocTitleConstraints.anchor = GridBagConstraints.NORTH;
		familyDoc.setHorizontalAlignment(JLabel.CENTER);
		familyDoc.setFont(nameFont);
		mainPanel.add(familyDoc, familyDocTitleConstraints);

		leftMain = new JPanel(new GridLayout(6, 1));

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

		GridBagConstraints lConstraints = new GridBagConstraints();

		lConstraints.fill = GridBagConstraints.HORIZONTAL;
		lConstraints.gridx = 0;
		lConstraints.gridy = 2;
		lConstraints.gridheight = 1;
		lConstraints.anchor = GridBagConstraints.NORTH;
		lConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		lConstraints.ipadx = screenDims.width / 5;
		mainPanel.add(leftMain, lConstraints);

		insuranceInfo.setFont(nameFont);
		manageInsurance = new JButton("Manage Insurance Information");
		manageInsurance.addActionListener(this);
		manageInsurance.setBorder(textBoxBorder);
		manageInsurance.setFont(genFont);

		insuranceGrid = new JPanel(new GridLayout(2, 1, 0, (int) (screenDims.height * 0.01)));

		insuranceInfo.setHorizontalAlignment(JLabel.CENTER);
		insuranceGrid.add(insuranceInfo);
		insuranceGrid.add(manageInsurance);

		GridBagConstraints insuranceConstraints = new GridBagConstraints();

		insuranceConstraints.fill = GridBagConstraints.HORIZONTAL;
		insuranceConstraints.gridx = 0;
		insuranceConstraints.gridy = 3;
		insuranceConstraints.gridheight = 1;
		insuranceConstraints.anchor = GridBagConstraints.CENTER;
		insuranceConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		insuranceConstraints.ipadx = screenDims.width / 5;
		insuranceConstraints.ipady = screenDims.height / 10;
		mainPanel.add(insuranceGrid, insuranceConstraints);

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

		GridBagConstraints mConstraints = new GridBagConstraints();

		mConstraints.fill = GridBagConstraints.HORIZONTAL;
		mConstraints.gridx = 1;
		mConstraints.gridy = 2;
		mConstraints.gridheight = 2;
		mConstraints.anchor = GridBagConstraints.NORTH;
		mConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mConstraints.ipadx = screenDims.width / 5;
		mainPanel.add(midMain, mConstraints);

		rightMain = new JPanel(new GridLayout(6, 1));

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

		docAddressField.setBorder(textBoxBorder);
		docAddressLabel.setFont(genFont);
		docAddressField.setFont(genFont);
		rightMain.add(docAddressLabel);
		rightMain.add(docAddressField);

		GridBagConstraints rConstraints = new GridBagConstraints();

		rConstraints.fill = GridBagConstraints.HORIZONTAL;
		rConstraints.gridx = 2;
		rConstraints.gridy = 2;
		rConstraints.gridheight = 1;
		rConstraints.anchor = GridBagConstraints.NORTH;
		rConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		rConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(rightMain, rConstraints);

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

		GridBagConstraints bConstraints = new GridBagConstraints();

		bConstraints.fill = GridBagConstraints.HORIZONTAL;
		bConstraints.gridx = 1;
		bConstraints.gridy = 3;
		bConstraints.gridheight = 1;
		bConstraints.gridwidth = 2;
		bConstraints.anchor = GridBagConstraints.NORTH;
		bConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomMain, bConstraints);

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

		GridBagConstraints buttonConstraints = new GridBagConstraints();

		buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
		buttonConstraints.gridx = 2;
		buttonConstraints.gridy = 5;
		buttonConstraints.gridheight = 1;
		buttonConstraints.anchor = GridBagConstraints.NORTH;
		buttonConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomButtonsMain, buttonConstraints);

		add(mainPanel, BorderLayout.CENTER);

		prescriptions = new JButton("Edit Prescriptions");
		prescriptions.addActionListener(this);
		prescriptions.setFont(genFont);
		prescriptions.setBorder(textBoxBorder);
		prescriptionsLabel.setFont(nameFont);

		GridBagConstraints prescriptionsLabelConstraints = new GridBagConstraints();

		prescriptionsLabelConstraints.fill = GridBagConstraints.BOTH;
		prescriptionsLabelConstraints.gridx = 0;
		prescriptionsLabelConstraints.gridy = 3;
		prescriptionsLabelConstraints.anchor = GridBagConstraints.NORTH;
		prescriptionsLabelConstraints.insets = new Insets(0, 0, (int) (screenDims.height * 0.01), 0);
		prescriptionsLabel.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(prescriptionsLabel, prescriptionsLabelConstraints);

		GridBagConstraints prescriptionsConstraints = new GridBagConstraints();

		prescriptionsConstraints.fill = GridBagConstraints.BOTH;
		prescriptionsConstraints.gridx = 0;
		prescriptionsConstraints.gridy = 4;
		prescriptionsConstraints.anchor = GridBagConstraints.SOUTH;
		mainPanel.add(prescriptions, prescriptionsConstraints);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("openStock")) {
			System.out.println("Stock");
		}
		if (e.getActionCommand().equals("openOrder")) {
			System.out.println("Order");
		}
		if (e.getActionCommand().equals("openSettings")) {
			System.out.println("Settings");
		}
		if (e.getActionCommand().equals("openPatientManager")) {
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		}
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			if (patient == null) {
				SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", null, patients);
				openSearchAdd.setVisible(true);
				setVisible(false);
			} else {
				ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patient, patients);
				openManage.setVisible(true);
				setVisible(false);
			}
		}
		if (e.getActionCommand().equals("Save Record")) {
			if ((patientNameField.getText().length() > 0 && dateOfBirthField.getText().length() > 0
					&& addressField.getText().length() > 0 && emailField.getText().length() > 0
					&& phoneField.getText().length() > 0 && healthCardNumField.getText().length() > 0) && !(patientNameField.getText().equals("First last"))) {
				if (patient == null) {
					newPatient.setName(patientNameField.getText().trim());
					newPatient.setBirthYear(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 4).trim()));
					newPatient.setDateOfBirthMonth(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 1, dateOfBirthField.getText().indexOf('/') + 3)
							.trim()));
					newPatient.setDateOfBirthDay(
							Integer.parseInt(dateOfBirthField.getText().substring(0, dateOfBirthField.getText().indexOf('/')).trim()));
					newPatient.setAddress(addressField.getText().trim());
					newPatient.setEmail(emailField.getText().trim());
					newPatient.setPhoneNumber(phoneField.getText().trim());
					newPatient.setHealthCardNumber(healthCardNumField.getText().trim());
					newPatient.newFamilyDoctor(docNameField.getText().trim(), docAddressField.getText().trim(),
							docPhoneNumberField.getText().trim());
					patients.insert(newPatient);
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", newPatient, patients);
					openManage.setVisible(true);
					setVisible(false);
				} else {
					patient.setName(patientNameField.getText().trim());
					patient.setBirthYear(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 4).trim()));
					patient.setDateOfBirthMonth(Integer.parseInt(dateOfBirthField.getText().trim()
							.substring(dateOfBirthField.getText().indexOf('/') + 1, dateOfBirthField.getText().indexOf('/') + 3)
							.trim()));
					patient.setDateOfBirthDay(
							Integer.parseInt(dateOfBirthField.getText().substring(0, dateOfBirthField.getText().indexOf('/')).trim()));
					patient.setAddress(addressField.getText().trim());
					patient.setEmail(emailField.getText().trim());
					patient.setPhoneNumber(phoneField.getText().trim());
					patient.setHealthCardNumber(healthCardNumField.getText().trim());
					patient.newFamilyDoctor(docNameField.getText().trim(), docAddressField.getText().trim(),
							docPhoneNumberField.getText().trim());
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patient, patients);
					openManage.setVisible(true);
					setVisible(false);
				}
			} else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to save patient details.");
			}
		}
		if (e.getActionCommand().equals("Edit Prescriptions")) {
			if (patient == null) {
				newPatient.newPrescriptionList();
				CurrentPrescriptions openPrescriptions = new CurrentPrescriptions("ManageRx", newPatient, patients);
				openPrescriptions.setVisible(true);
				setVisible(false);
			} else {
				CurrentPrescriptions openPrescriptions = new CurrentPrescriptions("ManageRx", patient, patients);
				openPrescriptions.setVisible(true);
				setVisible(false);
			}
		}
		if (e.getActionCommand().equals("Manage Insurance Information")) {
			if (patient == null) {
				newPatient.newInsuranceList();
				ViewInsuranceUI openInsurance = new ViewInsuranceUI("ManageRx", newPatient, patients);
				openInsurance.setVisible(true);
				setVisible(false);
			} else {
				ViewInsuranceUI openInsurance = new ViewInsuranceUI("ManageRx", patient, patients);
				openInsurance.setVisible(true);
				setVisible(false);
			}
		}
	}

	// https://stackoverflow.com/questions/18103839/display-disappear-default-text-in-textfield-when-user-enters-data-erase-the-text
	// used for focus event
	public void focusGained(FocusEvent e) {
		if (e.getComponent().equals(patientNameField)) {
			if (patientNameField.getText().trim().equals("First Last")) {
				patientNameField.setText("");
				patientNameField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(dateOfBirthField)) {
			if (dateOfBirthField.getText().trim().equals("DD/MM/YYYY")) {
				dateOfBirthField.setText("");
				dateOfBirthField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(healthCardNumField)) {
			if (healthCardNumField.getText().trim().equals("0000-000-000-AB")) {
				healthCardNumField.setText("");
				healthCardNumField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(emailField)) {
			if (emailField.getText().trim().equals("example@domain.com")) {
				emailField.setText("");
				emailField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(phoneField)) {
			if (phoneField.getText().trim().equals("(000) 000-0000")) {
				phoneField.setText("");
				phoneField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(addressField)) {
			if (addressField.getText().trim().equals("123 ABC Street, City")) {
				addressField.setText("");
				addressField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(docNameField)) {
			if (docNameField.getText().trim().equals("Dr. First Last")) {
				docNameField.setText("");
				docNameField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(docPhoneNumberField)) {
			if (docPhoneNumberField.getText().trim().equals("(000) 000-0000")) {
				docPhoneNumberField.setText("");
				docPhoneNumberField.setForeground(Color.black);
			}
		}
		if (e.getComponent().equals(docAddressField)) {
			if (docAddressField.getText().trim().equals("123 ABC Street, City")) {
				docAddressField.setText("");
				docAddressField.setForeground(Color.black);
			}
		}

	}

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}
}
