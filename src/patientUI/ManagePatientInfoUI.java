package patientUI;

import swingHelper.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.FlatLightLaf;

import mainUI.loginUI;
import mainUI.settingsUI;
import PatientManagement.*;

import java.util.*;

import java.awt.*;
import java.awt.event.*;

public class ManagePatientInfoUI extends JFrame implements ActionListener {

	// patient information
	Patient patient; // current patient
	PatientList patients; // list of all patient

	String[] insuranceCompanyArray; // array containing all insurance companies belonging to patient
	String[] insuranceNumberArray; // array containing all insurance numbers belonging to patient

	// panels
	private JPanel buttonPanel; // header panel
	private JPanel mainPanel; // main panel containing leftMain, midMain, rightMain, bottomMain,
								// bottomButtonsMain
	private JPanel leftMain; // left panel
	private JPanel midMain; // middle panel
	private JPanel rightMain; // right panel
	private JPanel bottomMain; // bottom panel
	private JPanel bottomButtonsMain; // bottom panel for buttons
	private JPanel headerButtons; // buttons other than back in header

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient management page

	// main buttons
	private JButton cancel; // cancel
	private JButton editRecord; // edit patient record
	private JButton prescriptions; // view patient prescriptions
	private JButton backButton; // go back to previous page

	// text elements
	private JLabel familyDoc = new JLabel("Family Doctor"); // family doctor title
	private JLabel dateOfBirthLabel = new JLabel("Date of Birth"); // date of birth label
	private JTextField dateOfBirthField; // date of birth field
	private JLabel healthCardNumLabel = new JLabel("Health Card Number"); // health card number label
	private JTextField healthCardNumField; // health card number field
	private JLabel emailLabel = new JLabel("Email"); // email label
	private JTextField emailField; // email field
	private JLabel phoneLabel = new JLabel("Phone Number"); // phone number label
	private JTextField phoneField; // phone number field
	private JLabel addressLabel = new JLabel("Address"); // address label
	private JTextField addressField; // address field
	private JLabel insuranceCompanyLabel = new JLabel("Insurance Company"); // insurance company label
	private JComboBox insuranceCompanyField; // insurance company field
	private JLabel insuranceNumberLabel = new JLabel("Insurance Number"); // insurance number label
	private JTextField insuranceNumberField; // insurance number field
	private JLabel additionalNotesLabel = new JLabel("Additional Notes"); // additional notes label
	private JTextArea additionalNotesArea; // additional notes area
	private JScrollPane additionalNotes; // additional notes scroll bar
	private JLabel docNameLabel = new JLabel("Name"); // family doc name label
	private JTextField docNameField; // family doc name field
	private JLabel docPhoneNumberLabel = new JLabel("Phone Number"); // family doc phone number label
	private JTextField docPhoneNumberField; // family doc phone number field
	private JLabel docAddressLabel = new JLabel("Address"); // family doc address label
	private JTextField docAddressField; // family doc address field
	private JLabel patientName; // patient name

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public ManagePatientInfoUI(String title, Patient patient, PatientList patients) {

		// setup screen attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// Rectangle screenDims = new Rectangle(1366, 768);
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// instantiate variables
		this.patient = patient;
		this.patients = patients;

		// add buttons to header
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

		// add rest of header buttons
		headerButtons = new JPanel(new FlowLayout());

		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		headerButtons.add(btnOpenSettings);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for buttons in header
																				// other than back

		overallButtonConstraints.gridx = 2;
		overallButtonConstraints.gridy = 0;
		overallButtonConstraints.gridwidth = 1;
		overallButtonConstraints.weightx = 0.55;
		overallButtonConstraints.anchor = GridBagConstraints.WEST;
		this.buttonPanel.add(headerButtons, overallButtonConstraints);

		add(this.buttonPanel, BorderLayout.NORTH);

		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
														// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

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

		// add patient name
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

		// add left elements
		leftMain = new JPanel(new GridLayout(8, 1));

		dateOfBirthField = new JTextField(patient.getBirthday());
		dateOfBirthField.setEditable(false);
		dateOfBirthField.setBorder(textBoxBorder);
		dateOfBirthLabel.setFont(genFont);
		dateOfBirthField.setFont(genFont);
		dateOfBirthField.setBackground(textBoxFill);
		leftMain.add(dateOfBirthLabel);
		leftMain.add(dateOfBirthField);

		healthCardNumField = new JTextField(patient.getHealthCardNumber());
		healthCardNumField.setEditable(false);
		healthCardNumField.setBorder(textBoxBorder);
		healthCardNumLabel.setFont(genFont);
		healthCardNumField.setFont(genFont);
		healthCardNumField.setBackground(textBoxFill);
		leftMain.add(healthCardNumLabel);
		leftMain.add(healthCardNumField);

		emailField = new JTextField(patient.getEmail());
		emailField.setEditable(false);
		emailField.setBorder(textBoxBorder);
		emailLabel.setFont(genFont);
		emailField.setFont(genFont);
		emailField.setBackground(textBoxFill);
		leftMain.add(emailLabel);
		leftMain.add(emailField);

		phoneField = new JTextField(String.valueOf(patient.getPhoneNumber()));
		phoneField.setEditable(false);
		phoneField.setBorder(textBoxBorder);
		phoneLabel.setFont(genFont);
		phoneField.setFont(genFont);
		phoneField.setBackground(textBoxFill);
		leftMain.add(phoneLabel);
		leftMain.add(phoneField);

		GridBagConstraints lConstraints = new GridBagConstraints(); // constraints for left panel

		lConstraints.fill = GridBagConstraints.BOTH;
		lConstraints.gridx = 0;
		lConstraints.gridy = 2;
		lConstraints.gridheight = 2;
		lConstraints.anchor = GridBagConstraints.NORTH;
		lConstraints.insets = new Insets(0, 0, 0, (int) (screenDims.width * 0.01));
		lConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(leftMain, lConstraints);

		// add middle panel elements
		midMain = new JPanel(new GridLayout(6, 1));

		addressField = new JTextField(patient.getAddress());
		addressField.setEditable(false);
		addressField.setBorder(textBoxBorder);
		addressLabel.setFont(genFont);
		addressField.setFont(genFont);
		addressField.setBackground(textBoxFill);
		midMain.add(addressLabel);
		midMain.add(addressField);

		// generate insurance combobox with all patient insurance
		if (patient.getInsuranceInformation() != null) {
			insuranceCompanyArray = new String[patient.getInsuranceInformation().size() + 1];
			insuranceNumberArray = new String[patient.getInsuranceInformation().size() + 1];
			for (int i = 0; i < insuranceCompanyArray.length - 1; i++) {
				insuranceCompanyArray[i] = patient.getInsuranceInformation().get(i).getCompany();
				insuranceNumberArray[i] = String.valueOf(patient.getInsuranceInformation().get(i).getNumber());
			} // end for
			insuranceCompanyField = new JComboBox(insuranceCompanyArray);
			insuranceCompanyField.addActionListener(this);
		} // end if
		else {
			insuranceCompanyField = new JComboBox();
			insuranceCompanyField.addActionListener(this);
		} // end else

		insuranceCompanyField.setEditable(false);
		insuranceCompanyField.setBorder(textBoxBorder);
		insuranceCompanyLabel.setFont(genFont);
		insuranceCompanyField.setFont(genFont);
		insuranceCompanyField.setBackground(textBoxFill);
		midMain.add(insuranceCompanyLabel);
		midMain.add(insuranceCompanyField);

		insuranceNumberField.setEditable(false);
		insuranceNumberField.setBorder(textBoxBorder);
		insuranceNumberLabel.setFont(genFont);
		insuranceNumberField.setFont(genFont);
		insuranceNumberField.setBackground(textBoxFill);
		// set text to first insurance plan
		if (insuranceNumberArray != null) {
			insuranceNumberField.setText(insuranceNumberArray[0]);
		} // end if
		midMain.add(insuranceNumberLabel);
		midMain.add(insuranceNumberField);

		GridBagConstraints mConstraints = new GridBagConstraints(); // constraints for middle panels

		mConstraints.fill = GridBagConstraints.BOTH;
		mConstraints.gridx = 1;
		mConstraints.gridy = 2;
		mConstraints.gridheight = 2;
		mConstraints.anchor = GridBagConstraints.NORTH;
		mConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(midMain, mConstraints);

		// add right panel elements
		rightMain = new JPanel(new GridLayout(6, 1));

		GridBagConstraints familyDocTitleConstraints = new GridBagConstraints(); // constraints for family doctor title

		familyDocTitleConstraints.fill = GridBagConstraints.BOTH;
		familyDocTitleConstraints.gridx = 2;
		familyDocTitleConstraints.gridy = 1;
		familyDocTitleConstraints.anchor = GridBagConstraints.NORTH;
		familyDocTitleConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		familyDocTitleConstraints.ipadx = screenDims.width / 7;
		familyDoc.setFont(nameFont);
		mainPanel.add(familyDoc, familyDocTitleConstraints);

		docNameField = new JTextField(patient.getFamilyDoctorName());
		docNameField.setEditable(false);
		docNameField.setBorder(textBoxBorder);
		docNameLabel.setFont(genFont);
		docNameField.setFont(genFont);
		docNameField.setBackground(textBoxFill);
		rightMain.add(docNameLabel);
		rightMain.add(docNameField);

		docPhoneNumberField = new JTextField(String.valueOf(patient.getFamilyDoctorNumber()));
		docPhoneNumberField.setEditable(false);
		docPhoneNumberField.setBorder(textBoxBorder);
		docPhoneNumberLabel.setFont(genFont);
		docPhoneNumberField.setFont(genFont);
		docPhoneNumberField.setBackground(textBoxFill);
		rightMain.add(docPhoneNumberLabel);
		rightMain.add(docPhoneNumberField);

		docAddressField = new JTextField(patient.getFamilyDoctorAddress());
		docAddressField.setEditable(false);
		docAddressField.setBorder(textBoxBorder);
		docAddressLabel.setFont(genFont);
		docAddressField.setFont(genFont);
		docAddressField.setBackground(textBoxFill);
		rightMain.add(docAddressLabel);
		rightMain.add(docAddressField);

		GridBagConstraints rConstraints = new GridBagConstraints(); // constraints for right panel

		rConstraints.fill = GridBagConstraints.BOTH;
		rConstraints.gridx = 2;
		rConstraints.gridy = 3;
		rConstraints.gridheight = 1;
		rConstraints.anchor = GridBagConstraints.NORTH;
		rConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		rConstraints.ipadx = screenDims.width / 7;
		mainPanel.add(rightMain, rConstraints);

		// add additional notes section
		bottomMain = new JPanel(new GridLayout(1, 1));

		additionalNotesArea = new JTextArea(
				"Medical Conditions:\n" + "Lifestyle habits:\n" + "Allergies/Dietary Restrictions:\n");
		additionalNotesArea.setBackground(getBackground());
		additionalNotesArea.setEditable(false);
		additionalNotesArea.setBorder(textBoxBorder);
		additionalNotesLabel.setFont(genFont);
		additionalNotesLabel.setVerticalAlignment(JLabel.BOTTOM);
		additionalNotesArea.setFont(genFont);
		additionalNotesArea.setLineWrap(true);
		additionalNotes = new JScrollPane(additionalNotesArea);
		additionalNotes.setMinimumSize(new Dimension(0, screenDims.height / 10));
		bottomMain.add(additionalNotesLabel);
		bottomMain.add(additionalNotes);

		GridBagConstraints bConstraints = new GridBagConstraints(); // constraints for additional notes

		bConstraints.fill = GridBagConstraints.BOTH;
		bConstraints.gridx = 1;
		bConstraints.gridy = 4;
		bConstraints.gridheight = 1;
		bConstraints.gridwidth = 2;
		bConstraints.anchor = GridBagConstraints.NORTH;
		bConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomMain, bConstraints);

		// add bottom buttons
		bottomButtonsMain = new JPanel(new GridLayout(1, 2, (int) (screenDims.width * 0.05), 0));

		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		cancel.setBorder(textBoxBorder);
		editRecord = new JButton("Edit Record");
		editRecord.addActionListener(this);
		editRecord.setBorder(textBoxBorder);
		cancel.setFont(genFont);
		editRecord.setFont(genFont);
		bottomButtonsMain.add(cancel);
		bottomButtonsMain.add(editRecord);

		GridBagConstraints buttonConstraints = new GridBagConstraints(); // constraints for bottom buttons

		buttonConstraints.fill = GridBagConstraints.BOTH;
		buttonConstraints.gridx = 2;
		buttonConstraints.gridy = 5;
		buttonConstraints.gridheight = 1;
		buttonConstraints.anchor = GridBagConstraints.NORTH;
		buttonConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.01));
		mainPanel.add(bottomButtonsMain, buttonConstraints);

		// add prescriptions button
		prescriptions = new JButton("View Prescriptions");
		prescriptions.addActionListener(this);
		prescriptions.setBorder(textBoxBorder);
		prescriptions.setFont(genFont);

		GridBagConstraints prescriptionsConstraints = new GridBagConstraints(); // constraints for prescription button

		prescriptionsConstraints.fill = GridBagConstraints.BOTH;
		prescriptionsConstraints.gridx = 0;
		prescriptionsConstraints.gridy = 4;
		prescriptionsConstraints.gridheight = 1;
		prescriptionsConstraints.anchor = GridBagConstraints.NORTH;
		prescriptionsConstraints.insets = new Insets((int) (screenDims.height * 0.05), 0,
				(int) (screenDims.height * 0.1), (int) (screenDims.width * 0.02));
		mainPanel.add(prescriptions, prescriptionsConstraints);

		add(mainPanel, BorderLayout.CENTER);

	} // end ManagePatientInfo

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("openStock")) {
			System.out.println("Stock");
		} // end if
		if (e.getActionCommand().equals("openOrder")) {
			System.out.println("Order");
		} // end if
			// open patient management
		if (e.getActionCommand().equals("openPatientManager")) {
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
			// open search for patient
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			SearchForPatientUI openSearch = new SearchForPatientUI("ManageRx", patient, patients);
			openSearch.setVisible(true);
			setVisible(false);
		} // end if
			// open edit patient record
		if (e.getActionCommand().equals("Edit Record")) {
			EditPatientInfoUI openEdit = new EditPatientInfoUI("ManageRx", patient, patients);
			openEdit.setVisible(true);
			setVisible(false);
		} // end if
			// open current prescriptions if there are any, else show popup
		if (e.getActionCommand().equals("View Prescriptions")) {
			if (patient.getActivePrescriptions() == null && patient.getArchivedPrescriptions() == null) {
				JOptionPane.showMessageDialog(mainPanel, "Patient Does Not Have Any Current Or Past Prescriptions",
						"No Prescriptions", JOptionPane.INFORMATION_MESSAGE);
			} // end if
			else if (patient.getActivePrescriptions() == null && patient.getArchivedPrescriptions() != null) {
				ArchivedPrescriptionsUI openArchived = new ArchivedPrescriptionsUI("ManageRx", patient, patients);
				openArchived.setVisible(true);
				setVisible(false);
			} // end else if
			else {
				CurrentPrescriptions openPrescriptions = new CurrentPrescriptions("ManageRx", patient, patients);
				openPrescriptions.setVisible(true);
				setVisible(false);
			} // end else
		} // end if
			// refresh insurance number based on company
		if (insuranceCompanyArray != null) {
			for (int i = 0; i < insuranceCompanyArray.length; i++) {
				if (((String) insuranceCompanyField.getSelectedItem()).equals(insuranceCompanyArray[i])) {
					insuranceNumberField.setText(insuranceNumberArray[i]);
				} // end if
			} // end for
		} // end if
	} // end actionPerformed
} // end ManagePatientInfoUI