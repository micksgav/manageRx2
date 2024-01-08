package patientUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.FlatLightLaf;

import PatientManagement.*;
import inventory.*;
import mainUI.loginUI;
import mainUI.settingsUI;
import swingHelper.AppIcon;

public class AddNewPrescriptionUI extends JFrame implements ActionListener {

	// patient information
	Patient patient; // patient currently selected
	PatientList patients; // list containing all patients

	// panels
	private JPanel buttonPanel; // header panel
	private JPanel mainPanel; // main panel excluding header
	private JPanel leftMain; // left panel in main panel
	private JPanel rightMain; // right panel in main panel
	private JPanel bottomMain; // bottom panel in main panel
	private JPanel headerButtons; // buttons and title in header other than back button

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton cancel; // cancel and go back
	private JButton saveGoBack; // save and go back
	private JButton saveAddMore; // save and add another prescription
	private JButton backButton; // go back

	// text elements
	private JLabel drugTitle = new JLabel("Drug Information"); // drug information title
	private JLabel drugNameLabel = new JLabel("Name"); // drug name label
	private JTextField drugNameField; // drug name field
	private JLabel atcLabel = new JLabel("ATC"); // atc label
	private JTextField atcField; // atc field
	private JLabel rxcuiLabel = new JLabel("RxCUI"); // rxcui label
	private JTextField rxcuiField; // rxcui field
	private JLabel dinLabel = new JLabel("DIN"); // din label
	private JTextField dinField; // din field
	private JLabel genInfoTitle = new JLabel("General Info"); // general info about prescription title
	private JLabel datePrescribedLabel = new JLabel("Date Prescribed"); // date prescribed label
	private JTextField datePrescribedField = new JTextField("Current date"); // date prescribed text field
	private JLabel numRefillsLabel = new JLabel("Number of Refills"); // number of refills label
	private JTextField numRefillsField; // number of refills field
	private JLabel quantityLabel = new JLabel("Quantity"); // quantity label
	private JTextField quantityField; // quantity field
	private JLabel dosageLabel = new JLabel("Dosage"); // dosage label
	private JTextField dosageField; // dosage field
	private JLabel instructionsLabel = new JLabel("Instructions"); // instructions label
	private JTextArea instructionsArea; // instructions field
	private JLabel prescribedDurationLabel = new JLabel("Prescribed Duration"); // prescribed duration label
	private JTextField prescribedDurationField; // prescribed duration field
	private JLabel formLabel = new JLabel("Form"); // form of drug label
	private JTextField formField; // form of drug field
	private JLabel classDrugLabel = new JLabel("Drug Class"); // drug class label
	private JTextField classDrugField; // drug class field
	private JLabel dpcLabel = new JLabel("DPC"); // dpc label
	private JTextField dpcField; // dpc field
	private JLabel patientName; // patient name
	private Insets gridBagPadding; // padding for some GridBagConstraints
	private Insets leftButtonPadding; // padding for left button
	private Insets rightButtonsPadding; // padding for right button

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public AddNewPrescriptionUI(String title, Patient patient, PatientList patients) {
		// setup display attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// Rectangle screenDims = new Rectangle(1366, 768);
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// initialize patient information
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

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text elements
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

		leftMain = new JPanel(new GridLayout(14, 1));

		// add drug title
		GridBagConstraints drugTitleConstraints = new GridBagConstraints(); // constraints for drug title

		drugTitleConstraints.fill = GridBagConstraints.BOTH;
		drugTitleConstraints.gridx = 0;
		drugTitleConstraints.gridy = 2;
		drugTitleConstraints.gridwidth = 1;
		drugTitleConstraints.anchor = GridBagConstraints.SOUTH;
		drugTitleConstraints.insets = gridBagPadding;
		drugTitle.setFont(nameFont);
		drugTitle.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(drugTitle, drugTitleConstraints);

		// add drug name label and field
		drugNameField = new JTextField();
		drugNameField.setBorder(textBoxBorder);
		drugNameLabel.setFont(genFont);
		drugNameField.setFont(genFont);
		leftMain.add(drugNameLabel);
		leftMain.add(drugNameField);

		// add atc label and field
		atcField = new JTextField();
		atcField.setBorder(textBoxBorder);
		atcLabel.setFont(genFont);
		atcField.setFont(genFont);
		leftMain.add(atcLabel);
		leftMain.add(atcField);

		// add rxcui label and field
		rxcuiField = new JTextField();
		rxcuiField.setBorder(textBoxBorder);
		rxcuiField.setFont(genFont);
		rxcuiLabel.setFont(genFont);
		leftMain.add(rxcuiLabel);
		leftMain.add(rxcuiField);

		// add din label and field
		dinField = new JTextField();
		dinField.setBorder(textBoxBorder);
		dinLabel.setFont(genFont);
		dinField.setFont(genFont);
		leftMain.add(dinLabel);
		leftMain.add(dinField);

		// and drug class label and field
		classDrugField = new JTextField();
		classDrugField.setBorder(textBoxBorder);
		classDrugField.setFont(genFont);
		classDrugLabel.setFont(genFont);
		leftMain.add(classDrugLabel);
		leftMain.add(classDrugField);

		// add drug form label and field
		formField = new JTextField();
		formField.setBorder(textBoxBorder);
		formField.setFont(genFont);
		formLabel.setFont(genFont);
		leftMain.add(formLabel);
		leftMain.add(formField);

		// add dpc label and field
		dpcField = new JTextField();
		dpcField.setBorder(textBoxBorder);
		dpcField.setFont(genFont);
		dpcLabel.setFont(genFont);
		leftMain.add(dpcLabel);
		leftMain.add(dpcField);

		leftMain.setBorder(textBoxBorder);

		// add left panel to main panel
		GridBagConstraints leftPanelConstraints = new GridBagConstraints(); // constraints for left panel

		leftPanelConstraints.fill = GridBagConstraints.BOTH;
		leftPanelConstraints.gridx = 0;
		leftPanelConstraints.gridy = 3;
		leftPanelConstraints.gridwidth = 1;
		leftPanelConstraints.anchor = GridBagConstraints.WEST;
		leftPanelConstraints.ipadx = (int) (screenDims.width * 0.1);
		leftPanelConstraints.insets = gridBagPadding;
		mainPanel.add(leftMain, leftPanelConstraints);

		rightMain = new JPanel(new GridLayout(12, 1));

		// add general info title
		GridBagConstraints genInfoTitleConstraints = new GridBagConstraints(); // constraints for general information title

		genInfoTitleConstraints.fill = GridBagConstraints.BOTH;
		genInfoTitleConstraints.gridx = 1;
		genInfoTitleConstraints.gridy = 2;
		genInfoTitleConstraints.gridwidth = 1;
		genInfoTitleConstraints.anchor = GridBagConstraints.SOUTH;
		genInfoTitleConstraints.insets = gridBagPadding;
		genInfoTitle.setFont(nameFont);
		genInfoTitle.setHorizontalAlignment(JLabel.LEFT);
		mainPanel.add(genInfoTitle, genInfoTitleConstraints);

		// add date prescribed label and field
		datePrescribedField = new JTextField();
		datePrescribedField.setBorder(textBoxBorder);
		datePrescribedLabel.setFont(genFont);
		datePrescribedField.setFont(genFont);
		rightMain.add(datePrescribedLabel);
		rightMain.add(datePrescribedField);

		// add number of refills label and field
		numRefillsField = new JTextField();
		numRefillsField.setBorder(textBoxBorder);
		numRefillsLabel.setFont(genFont);
		numRefillsField.setFont(genFont);
		rightMain.add(numRefillsLabel);
		rightMain.add(numRefillsField);

		// add quantity label and field
		quantityField = new JTextField();
		quantityField.setBorder(textBoxBorder);
		quantityField.setFont(genFont);
		quantityLabel.setFont(genFont);
		rightMain.add(quantityLabel);
		rightMain.add(quantityField);

		// add dosage label and field
		dosageField = new JTextField();
		dosageField.setBorder(textBoxBorder);
		dosageField.setFont(genFont);
		dosageLabel.setFont(genFont);
		rightMain.add(dosageLabel);
		rightMain.add(dosageField);

		// add instrutions label and field
		instructionsArea = new JTextArea();
		instructionsArea.setBorder(textBoxBorder);
		instructionsArea.setFont(genFont);
		instructionsLabel.setFont(genFont);
		JScrollPane scroll = new JScrollPane(instructionsArea);
		rightMain.add(instructionsLabel);
		rightMain.add(scroll);

		// add prescribed duration label and field
		prescribedDurationField = new JTextField();
		prescribedDurationField.setBorder(textBoxBorder);
		prescribedDurationField.setFont(genFont);
		prescribedDurationLabel.setFont(genFont);
		rightMain.add(prescribedDurationLabel);
		rightMain.add(prescribedDurationField);

		rightMain.setBorder(textBoxBorder);

		// add right panel to main panel
		GridBagConstraints rightPanelConstraints = new GridBagConstraints(); // constraints for right panel

		rightPanelConstraints.fill = GridBagConstraints.BOTH;
		rightPanelConstraints.gridx = 1;
		rightPanelConstraints.gridy = 3;
		rightPanelConstraints.gridwidth = 1;
		rightPanelConstraints.gridheight = 2;
		rightPanelConstraints.anchor = GridBagConstraints.WEST;
		rightPanelConstraints.ipadx = (int) (screenDims.width * 0.15);
		rightPanelConstraints.insets = gridBagPadding;
		mainPanel.add(rightMain, rightPanelConstraints);

		bottomMain = new JPanel(new GridBagLayout());

		// add bottom buttons
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		saveGoBack = new JButton("Save and Go Back");
		saveGoBack.addActionListener(this);
		saveAddMore = new JButton("Save and Add Another Prescription");
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
		cancelConstraints.gridy = 0;
		cancelConstraints.gridwidth = 1;
		cancelConstraints.anchor = GridBagConstraints.WEST;
		cancelConstraints.weightx = 0.1;
		cancelConstraints.insets = leftButtonPadding;
		bottomMain.add(cancel, cancelConstraints);

		// add save and go back button
		GridBagConstraints saveGoBackConstraints = new GridBagConstraints(); // constraints for save and go back button

		saveGoBackConstraints.fill = GridBagConstraints.BOTH;
		saveGoBackConstraints.gridx = 1;
		saveGoBackConstraints.gridy = 0;
		saveGoBackConstraints.gridwidth = 1;
		saveGoBackConstraints.anchor = GridBagConstraints.EAST;
		saveGoBackConstraints.insets = rightButtonsPadding;
		bottomMain.add(saveGoBack, saveGoBackConstraints);

		// add save and add more button
		GridBagConstraints saveAddMoreConstraints = new GridBagConstraints(); // constraints for save and add more button

		saveAddMoreConstraints.fill = GridBagConstraints.BOTH;
		saveAddMoreConstraints.gridx = 2;
		saveAddMoreConstraints.gridy = 0;
		saveAddMoreConstraints.gridwidth = 1;
		saveAddMoreConstraints.anchor = GridBagConstraints.EAST;
		saveAddMoreConstraints.insets = rightButtonsPadding;
		bottomMain.add(saveAddMore, saveAddMoreConstraints);
		
		// add bottom buttons to main panel
		GridBagConstraints buttonConstraints = new GridBagConstraints(); // constraints for bottom buttons

		buttonConstraints.fill = GridBagConstraints.BOTH;
		buttonConstraints.gridx = 0;
		buttonConstraints.gridy = 5;
		buttonConstraints.gridwidth = 3;
		buttonConstraints.anchor = GridBagConstraints.SOUTH;
		mainPanel.add(bottomMain, buttonConstraints);

		add(mainPanel, BorderLayout.CENTER);

	} // end AddNewPrescriptionUI

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
		// cancel or back button pressed
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			// open current prescriptions page
			CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients);
			openCurrent.setVisible(true);
			setVisible(false);
		} // end if
		// save and go back button pressed
		if (e.getActionCommand().equals("Save and Go Back")) {
			// make sure field have been filled in
			if (atcField.getText().length() > 0 && rxcuiField.getText().length() > 0 && dinField.getText().length() > 0
					&& datePrescribedField.getText().length() > 0 && numRefillsField.getText().length() > 0
					&& quantityField.getText().length() > 0 && dosageField.getText().length() > 0
					&& instructionsArea.getText().length() > 0 && prescribedDurationField.getText().length() > 0
					&& formField.getText().length() > 0 && classDrugField.getText().length() > 0
					&& dpcField.getText().length() > 0) {
				// create a drug and prescription from input, then add to patient
				String[][] drugDosage = new String[1][1];
				drugDosage[0][0] = dosageField.getText().trim();
				Drug newDrug = new Drug(dinField.getText().trim(), drugNameField.getText().trim(),
						classDrugField.getText().trim(), null, null, null, formField.getText().trim(), drugDosage,
						rxcuiField.getText().trim(), dpcField.getText().trim(), atcField.getText().trim(), null);
				Prescription newScript = new Prescription(newDrug, datePrescribedField.getText().trim(),
						Integer.parseInt(numRefillsField.getText().trim()),
						Integer.parseInt(quantityField.getText().trim()),
						Integer.parseInt(dosageField.getText().trim()), instructionsArea.getText().trim(),
						prescribedDurationField.getText().trim());
				patient.addActivePrescription(newScript);

				// open current prescriptions paged
				CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients);
				openCurrent.setVisible(true);
				setVisible(false);
			}  // end if
			// if not all fields have been filled in, open a popup
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to add prescription.");
			} // end else
		} // end if
		// save and add another button pressed
		if (e.getActionCommand().equals("Save and Add Another Prescription")) {
			// make sure all field have been filled in
			if (atcField.getText().length() > 0 && rxcuiField.getText().length() > 0 && dinField.getText().length() > 0
					&& datePrescribedField.getText().length() > 0 && numRefillsField.getText().length() > 0
					&& quantityField.getText().length() > 0 && dosageField.getText().length() > 0
					&& instructionsArea.getText().length() > 0 && prescribedDurationField.getText().length() > 0
					&& formField.getText().length() > 0 && classDrugField.getText().length() > 0
					&& dpcField.getText().length() > 0) {
				// create a drug and a prescription, then add to patient
				String[][] drugDosage = new String[1][1];
				drugDosage[0][0] = dosageField.getText().trim();
				Drug newDrug = new Drug(dinField.getText().trim(), drugNameField.getText().trim(),
						classDrugField.getText().trim(), null, null, null, formField.getText().trim(), drugDosage,
						rxcuiField.getText().trim(), dpcField.getText().trim(), atcField.getText().trim(), null);
				Prescription newScript = new Prescription(newDrug, datePrescribedField.getText().trim(),
						Integer.parseInt(numRefillsField.getText().trim()),
						Integer.parseInt(quantityField.getText().trim()),
						Integer.parseInt(dosageField.getText().trim()), instructionsArea.getText().trim(),
						prescribedDurationField.getText().trim());
				patient.addActivePrescription(newScript);

				// open a new add prescription window
				AddNewPrescriptionUI openAddNew = new AddNewPrescriptionUI("ManageRx", patient, patients);
				openAddNew.setVisible(true);
				setVisible(false);
			} // end if 
			// if not all fields have been filled in, open a popup
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to add prescription.");
			} // end else
		} // end if
	} // end actionPerformed
} // end AddNewPrescriptionUI
