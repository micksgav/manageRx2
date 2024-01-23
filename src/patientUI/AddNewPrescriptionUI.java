
/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 23, 2023
 * @Last Modified: January 21, 2024
 * @Description: Add new prescription page in the patient management section of ManageRx
 ***********************************************
 */

package patientUI;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;

import com.formdev.flatlaf.FlatLightLaf;

import patientManagement.*;
import inventory.*;
import mainUI.orderUI;
import mainUI.stockUI;
import swingHelper.AppIcon;
import utilities.DrugSelection;
import utilities.SQLHelper;
import utilities.altDisplay;

public class AddNewPrescriptionUI extends JFrame implements ActionListener, FocusListener {

	// app information
	Patient patient; // patient currently selected
	PatientList patients; // list containing all patients
	AllStock stock; // complete stock for pharmacy

	// panels
	private JPanel buttonPanel; // header panel
	private JPanel mainPanel; // main panel excluding header
	private JPanel leftMain; // left panel in main panel
	private JPanel rightMain; // right panel in main panel
	private JPanel bottomMain; // bottom panel in main panel
	private JPanel headerButtons; // buttons and title in header other than back button
	private JPanel docPrescribed; // doctor who prescribed prescription panel
	private JPanel panel; // panel containing search button and drug name field

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton cancel; // cancel and go back
	private JButton saveGoBack; // save and go back
	private JButton saveAddMore; // save and add another prescription
	private JButton backButton; // go back

	// text elements
	private JLabel drugTitle = new JLabel("Drug Information"); // drug information title
	private JLabel drugNameLabel = new JLabel("Drug Name"); // drug name label
	private JTextField drugNameField; // drug name field
	private JLabel dinLabel = new JLabel("DIN"); // din label
	private JTextField dinField; // din field
	private JLabel genInfoTitle = new JLabel("General Info"); // general info about prescription title
	private JLabel datePrescribedLabel = new JLabel("Date Prescribed"); // date prescribed label
	private JFormattedTextField datePrescribedField; // date prescribed text field
	private JLabel numRefillsLabel = new JLabel("Number of Refills"); // number of refills label
	private JTextField numRefillsField; // number of refills field
	private JLabel quantityLabel = new JLabel("Quantity"); // quantity label
	private JTextField quantityField; // quantity field
	private JLabel dosageLabel = new JLabel("Dosage"); // dosage label
	private JTextField dosageField = new JTextField("0 units"); // dosage field
	private JLabel instructionsLabel = new JLabel("Instructions"); // instructions label
	private JTextArea instructionsArea; // instructions field
	private JLabel prescribedDurationLabel = new JLabel("Prescribed Duration"); // prescribed duration label
	private JTextField prescribedDurationField = new JTextField("6 months"); // prescribed duration field
	private JLabel formLabel = new JLabel("Form"); // form of drug label
	private JTextField formField; // form of drug field
	private Insets gridBagPadding; // padding for some GridBagConstraints
	private Insets leftButtonPadding; // padding for left button
	private Insets rightButtonsPadding; // padding for right button
	private JLabel docPrescribedNameLabel = new JLabel("Doctor's Name"); // name of doctor who prescribed meds label
	private JTextField docPrescribedNameField = new JTextField("First Last"); // name of doctor who prescribed meds
																				// field
	private JLabel docPrescribedAddressLabel = new JLabel("Doctor's Address"); // address of doctor who prescribed meds
																				// label
	private JTextField docPrescribedAddressField = new JTextField("123 ABC Street, City"); // address of doctor who
																							// prescribed meds field
	private JLabel docPrescribedPhoneLabel = new JLabel("Doctor's Phone Number"); // phone number of doctor who
																					// prescribed meds label
	private JFormattedTextField docPrescribedPhoneField; // phone number of doctor who prescribed meds field
	private JLabel docPrescribedFaxLabel = new JLabel("Doctor's Fax Number"); // fax number of doctor who prescribed
																				// meds label
	private JFormattedTextField docPrescribedFaxField; // fax number of doctor who prescribed meds field
	private MaskFormatter dateOfBirthFormat; // format for date of birth
	private MaskFormatter phoneFormat; // format for phone and fax
	private JButton searchButton; // search for drug button

	// last page
	boolean last; // if last is true, the last page was PatientManagementUI. If last is false, the
					// last page was EditPatientInfoUI

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public AddNewPrescriptionUI(String title, Patient patient, PatientList patients, boolean last, AllStock stock)
			throws ParseException {
		// setup display attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		// initialize app variables
		this.patient = patient;
		this.patients = patients;
		this.last = last;
		this.stock = stock;

		// formats
		dateOfBirthFormat = new MaskFormatter("##/##/####");
		phoneFormat = new MaskFormatter("(###) ### - ####");

		// make header
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx"); // header title
		label.setFont(new Font("Arial", Font.BOLD, 20));

		btnOpenStock = new JButton("Stock");
		btnOpenStock.setIcon(stockIcon);
		btnOpenStock.setActionCommand("openStock");
		btnOpenStock.addActionListener(this);

		btnOpenOrder = new JButton("Order");
		btnOpenOrder.setIcon(orderIcon);
		btnOpenOrder.setActionCommand("openOrder");
		btnOpenOrder.addActionListener(this);

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
		getContentPane().add(this.buttonPanel, BorderLayout.NORTH);

		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
		// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text elements
		Font smallGen = new Font("Arial", Font.PLAIN, 15); // smaller general font where gen font won't fit fully
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // font for names and titles
		Color textBoxFill = new Color(204, 204, 204); // fill for textboxes
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // line
																													// for
																													// textBoxBorder
																													// https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01)); // padding for textBoxBorder
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // border made up of
																								// textBoxBorderLine and
																								// textFieldPadding
		gridBagPadding = new Insets(0, (int) (screenDims.width * 0.07), 0, (int) (screenDims.width * 0.07));
		Insets leftPanelPadding = new Insets(0, (int) (screenDims.width * 0.07), 0, (int) (screenDims.width * 0.07)); // padding
																														// for
																														// left
																														// panel
		leftButtonPadding = new Insets((int) (screenDims.height * 0.01), 0, (int) (screenDims.height * 0.01),
				(int) (screenDims.width * 0.25));
		rightButtonsPadding = new Insets((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));

		leftMain = new JPanel(new GridLayout(0, 1, 0, (int) (screenDims.height * 0.05)));

		JPanel innerLeftMain = new JPanel(new GridLayout(0, 1)); // panel for drug information
		docPrescribed = new JPanel(new GridLayout(0, 1));

		// fix sizing glitch from
		// https://stackoverflow.com/questions/4061010/setmaximumsize-not-working-in-java
		leftMain.setMaximumSize(new Dimension((int) (screenDims.width * 0.13), (int) (screenDims.height * 0.8)));
		leftMain.setPreferredSize(new Dimension((int) (screenDims.width * 0.13), (int) (screenDims.height * 0.8)));
		leftMain.setMinimumSize(new Dimension((int) (screenDims.width * 0.13), (int) (screenDims.height * 0.8)));
		leftMain.setSize(new Dimension((int) (screenDims.width * 0.13), (int) (screenDims.height * 0.8)));
		leftMain.revalidate();

		// add drug title
		GridBagConstraints drugTitleConstraints = new GridBagConstraints(); // constraints for drug title

		drugTitleConstraints.fill = GridBagConstraints.BOTH;
		drugTitleConstraints.gridx = 0;
		drugTitleConstraints.gridy = 0;
		drugTitleConstraints.gridwidth = 1;
		drugTitleConstraints.anchor = GridBagConstraints.SOUTH;
		drugTitleConstraints.insets = gridBagPadding;
		drugTitle.setFont(nameFont);
		drugTitle.setHorizontalAlignment(JLabel.LEFT);
		drugNameLabel.setFont(genFont);
		innerLeftMain.add(drugNameLabel);

		panel = new JPanel();
		innerLeftMain.add(panel);
		panel.setLayout(new GridLayout(1, 0, (int) (screenDims.width * 0.01), 0));
		// mainPanel.add(drugTitle, drugTitleConstraints);

		// add drug name label and field
		drugNameField = new JTextField();
		panel.add(drugNameField);
		drugNameField.setBorder(textBoxBorder);
		drugNameField.setFont(smallGen);

		// add din label and field, search button
		dinField = new JTextField();
		dinField.setBorder(textBoxBorder);
		dinLabel.setFont(genFont);
		dinField.setFont(smallGen);

		searchButton = new JButton("Search");
		searchButton.setBorder(textBoxBorder);
		searchButton.setFont(genFont);
		innerLeftMain.add(searchButton);
		searchButton.addActionListener(this);
		innerLeftMain.add(dinLabel);
		innerLeftMain.add(dinField);

		// add drug form label and field
		formField = new JTextField();
		formField.setBorder(textBoxBorder);
		formField.setFont(smallGen);
		formLabel.setFont(genFont);
		innerLeftMain.add(formLabel);
		innerLeftMain.add(formField);

		docPrescribedNameField.setBorder(textBoxBorder);
		docPrescribedNameField.setForeground(textBoxFill);
		docPrescribedNameField.addFocusListener(this);
		docPrescribedNameLabel.setFont(genFont);
		docPrescribedNameField.setFont(smallGen);
		docPrescribed.add(docPrescribedNameLabel);
		docPrescribed.add(docPrescribedNameField);

		docPrescribedAddressField.setBorder(textBoxBorder);
		docPrescribedAddressField.setForeground(textBoxFill);
		docPrescribedAddressField.addFocusListener(this);
		docPrescribedAddressLabel.setFont(genFont);
		docPrescribedAddressField.setFont(smallGen);
		docPrescribed.add(docPrescribedAddressLabel);
		docPrescribed.add(docPrescribedAddressField);

		docPrescribedPhoneField = new JFormattedTextField(phoneFormat);
		docPrescribedPhoneField.setBorder(textBoxBorder);
		docPrescribedPhoneLabel.setFont(genFont);
		docPrescribedPhoneField.setFont(smallGen);
		docPrescribed.add(docPrescribedPhoneLabel);
		docPrescribed.add(docPrescribedPhoneField);

		docPrescribedFaxField = new JFormattedTextField(phoneFormat);
		docPrescribedFaxField.setBorder(textBoxBorder);
		docPrescribedFaxLabel.setFont(genFont);
		docPrescribedFaxField.setFont(smallGen);
		docPrescribed.add(docPrescribedFaxLabel);
		docPrescribed.add(docPrescribedFaxField);

		docPrescribed.setBorder(textBoxBorder);

		innerLeftMain.setBorder(textBoxBorder);

		leftMain.add(innerLeftMain);
		leftMain.add(docPrescribed);

		// add left panel to main panel
		GridBagConstraints leftPanelConstraints = new GridBagConstraints(); // constraints for left panel

		leftPanelConstraints.fill = GridBagConstraints.BOTH;
		leftPanelConstraints.gridx = 0;
		leftPanelConstraints.gridy = 0;
		leftPanelConstraints.gridwidth = 1;
		leftPanelConstraints.anchor = GridBagConstraints.WEST;
		leftPanelConstraints.ipadx = (int) (screenDims.width * 0.1);
		leftPanelConstraints.ipady = (int) (screenDims.height * 0.001);
		leftPanelConstraints.insets = leftPanelPadding;
		mainPanel.add(leftMain, leftPanelConstraints);

		rightMain = new JPanel(new GridLayout(12, 1));

		rightMain.setMaximumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.8)));
		rightMain.setPreferredSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.8)));
		rightMain.setMinimumSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.8)));
		rightMain.setSize(new Dimension((int) (screenDims.width * 0.15), (int) (screenDims.height * 0.8)));
		rightMain.revalidate();

		// add general info title
		GridBagConstraints genInfoTitleConstraints = new GridBagConstraints(); // constraints for general information
																				// title

		genInfoTitleConstraints.fill = GridBagConstraints.BOTH;
		genInfoTitleConstraints.gridx = 1;
		genInfoTitleConstraints.gridy = 0;
		genInfoTitleConstraints.gridwidth = 1;
		genInfoTitleConstraints.anchor = GridBagConstraints.SOUTH;
		genInfoTitleConstraints.insets = gridBagPadding;
		genInfoTitle.setFont(nameFont);
		genInfoTitle.setHorizontalAlignment(JLabel.LEFT);

		// add date prescribed label and field
		datePrescribedField = new JFormattedTextField(dateOfBirthFormat);
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
		dosageField.setBorder(textBoxBorder);
		dosageField.setForeground(textBoxFill);
		dosageField.addFocusListener(this);
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
		prescribedDurationField.setBorder(textBoxBorder);
		prescribedDurationField.setForeground(textBoxFill);
		prescribedDurationField.addFocusListener(this);
		prescribedDurationField.setFont(genFont);
		prescribedDurationLabel.setFont(genFont);
		rightMain.add(prescribedDurationLabel);
		rightMain.add(prescribedDurationField);

		rightMain.setBorder(textBoxBorder);

		// add right panel to main panel
		GridBagConstraints rightPanelConstraints = new GridBagConstraints(); // constraints for right panel

		rightPanelConstraints.fill = GridBagConstraints.BOTH;
		rightPanelConstraints.gridx = 1;
		rightPanelConstraints.gridy = 0;
		rightPanelConstraints.gridwidth = 1;
		rightPanelConstraints.gridheight = 1;
		rightPanelConstraints.anchor = GridBagConstraints.WEST;
		rightPanelConstraints.ipadx = (int) (screenDims.width * 0.15);
		rightPanelConstraints.ipady = (int) (screenDims.height * 0.001);
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
		GridBagConstraints saveAddMoreConstraints = new GridBagConstraints(); // constraints for save and add more
																				// button

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
		buttonConstraints.gridy = 1;
		buttonConstraints.gridwidth = 2;
		buttonConstraints.anchor = GridBagConstraints.SOUTH;
		mainPanel.add(bottomMain, buttonConstraints);

		getContentPane().add(mainPanel, BorderLayout.CENTER);

	} // end AddNewPrescriptionUI

	public void actionPerformed(ActionEvent e) {
		SQLHelper helper = new SQLHelper(); // connect to sql

		// search button
		if (e.getActionCommand().equals("Search")) {
			if (drugNameField.getText().trim().length() > 0) {
			String[] selection = DrugSelection.getDrugSelection(drugNameField.getText());
			dinField.setText(selection[0]);
			drugNameField.setText(selection[1]);
			dosageField.setText(selection[2]);
			dosageField.setForeground(Color.black);
			} // end if
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please Enter a Drug to Search");
			}
		} // end if
			// open stock button pressed
		if (e.getActionCommand().equals("openStock")) {
			stockUI openStock = new stockUI("ManageRx", patients, stock);
			openStock.setVisible(true);
			setVisible(false);
		} // end if
			// open order button pressed
		if (e.getActionCommand().equals("openOrder")) {
			orderUI openOrder = new orderUI("ManageRx", patients, stock);
			openOrder.setVisible(true);
			setVisible(false);
		} // end if
			// open patient manager button pressed
		if (e.getActionCommand().equals("openPatientManager")) {
			// open patient manager page
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patients, stock);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
			// cancel or back button pressed
		if (e.getActionCommand().equals("Cancel") || e.getActionCommand().equals("Back")) {
			// open current prescriptions page
			CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients, last, stock);
			openCurrent.setVisible(true);
			setVisible(false);
		} // end if
			// save and go back button pressed
		if (e.getActionCommand().equals("Save and Go Back")) {
			if (quantityField.getText().length() > 0) {
				try {
					int i = Integer.parseInt(quantityField.getText());
					if (i <= 0) {
						JOptionPane.showMessageDialog(mainPanel,
								"Error, quantity must be a whole number greater than 0.");
					} // end if
				} // end try
				catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(mainPanel, "Error, quantity must be a whole number greater than 0.");
				} // end catch
			} // end if
			if (numRefillsField.getText().length() > 0) {
				try {
					int i = Integer.parseInt(numRefillsField.getText());
					if (i < 0) {
						JOptionPane.showMessageDialog(mainPanel,
								"Error, number of refills must be a whole number greater than or equal to 0.");
					} // end if
				} // end try
				catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(mainPanel,
							"Error, number of refills must be a whole number greater than or equal to 0.");
				} // end catch
			} // end if
				// make sure field have been filled in
			if (dinField.getText().length() > 0 && datePrescribedField.getText().length() == 10
					&& numRefillsField.getText().length() > 0 && quantityField.getText().length() > 0
					&& dosageField.getText().length() > 0 && instructionsArea.getText().length() > 0
					&& prescribedDurationField.getText().length() > 0 && formField.getText().length() > 0
					&& drugNameField.getText().length() > 0 && docPrescribedNameField.getText().length() > 0
					&& docPrescribedAddressField.getText().length() > 0
					&& docPrescribedPhoneField.getText().length() == 16
					&& docPrescribedFaxField.getText().length() == 16
					&& !(docPrescribedNameField.getText().equals("First Last") && !(docPrescribedAddressField.getText()
							.equals("123 ABC Street, City")
							&& !(dosageField.getText().equals("00 units")
									&& !(prescribedDurationField.getForeground().equals(new Color(204, 204, 204))))))) {
				// create a drug and prescription from input, then add to patient

				String[] allDins = new String[patient.getActivePrescriptions().length() + 1];
				for (int i = 0; i < allDins.length - 1; i++) {
					allDins[i] = patient.getActivePrescriptions().atIndex(i).getDIN();
				} // end for
				allDins[allDins.length - 1] = dinField.getText();
				if (altDisplay.showInteractions(allDins)) {
					String drugDosage = dosageField.getText().trim();
					Drug newDrug = new Drug(dinField.getText().trim(), drugNameField.getText().trim(), null, null, null,
							null, formField.getText().trim(), drugDosage);
					Prescription newScript = new Prescription(newDrug, datePrescribedField.getText().trim(),
							Integer.parseInt(numRefillsField.getText().trim()),
							Integer.parseInt(quantityField.getText().trim()), drugDosage,
							instructionsArea.getText().trim(), prescribedDurationField.getText().trim(),
							docPrescribedNameField.getText().trim(), docPrescribedAddressField.getText().trim(),
							docPrescribedPhoneField.getText().trim(), docPrescribedFaxField.getText().trim());
					newScript.setPatientID(patient.getId());
					patient.addActivePrescription(newScript);
					helper.addPrescriptionBG(newScript);

					CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients, last,
							stock);
					openCurrent.setVisible(true);

					setVisible(false);
				} // end if

			} // end if
				// if not all fields have been filled in, open a popup
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to add prescription.");
			} // end else
		} // end if
			// save and add another button pressed
		if (e.getActionCommand().equals("Save and Add Another Prescription")) {
			if (quantityField.getText().length() > 0) {
				try {
					int i = Integer.parseInt(quantityField.getText());
					if (i <= 0) {
						JOptionPane.showMessageDialog(mainPanel,
								"Error, quantity must be a whole number greater than 0.");
					} // end if
				} // end try
				catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(mainPanel, "Error, quantity must be a whole number greater than 0.");
				} // end catch
			} // end if
			if (numRefillsField.getText().length() > 0) {
				try {
					int i = Integer.parseInt(numRefillsField.getText());
					if (i < 0) {
						JOptionPane.showMessageDialog(mainPanel,
								"Error, number of refills must be a whole number greater than or equal to 0.");
					} // end if
				} // end try
				catch (NumberFormatException f) {
					JOptionPane.showMessageDialog(mainPanel,
							"Error, number of refills must be a whole number greater than or equal to 0.");
				} // end catch
			} // end if
			// make sure all field have been filled in
			if (dinField.getText().length() > 0 && datePrescribedField.getText().length() == 10
					&& numRefillsField.getText().length() > 0 && quantityField.getText().length() > 0
					&& dosageField.getText().length() > 0 && instructionsArea.getText().length() > 0
					&& prescribedDurationField.getText().length() > 0 && formField.getText().length() > 0
					&& drugNameField.getText().length() > 0 && docPrescribedNameField.getText().length() > 0
					&& docPrescribedAddressField.getText().length() > 0
					&& docPrescribedPhoneField.getText().length() == 16
					&& docPrescribedFaxField.getText().length() == 16
					&& !(docPrescribedNameField.getText().equals("First Last") && !(docPrescribedAddressField.getText()
							.equals("123 ABC Street, City")
							&& !(dosageField.getText().equals("00 units")
									&& !(prescribedDurationField.getForeground().equals(new Color(204, 204, 204))))))) {
				// create a drug and a prescription, then add to patient
				String[] allDins = new String[patient.getActivePrescriptions().length() + 1];
				for (int i = 0; i < allDins.length - 1; i++) {
					allDins[i] = patient.getActivePrescriptions().atIndex(i).getDIN();
				} // end for
				allDins[allDins.length - 1] = dinField.getText();
				if (altDisplay.showInteractions(allDins)) {
					Drug newDrug = new Drug(dinField.getText().trim(), drugNameField.getText().trim(), null, null, null,
							null, formField.getText().trim(), dosageField.getText());
					Prescription newScript = new Prescription(newDrug, datePrescribedField.getText().trim(),
							Integer.parseInt(numRefillsField.getText().trim()),
							Integer.parseInt(quantityField.getText().trim()), dosageField.getText(),
							instructionsArea.getText().trim(), prescribedDurationField.getText().trim(),
							docPrescribedNameField.getText().trim(), docPrescribedAddressField.getText().trim(),
							docPrescribedPhoneField.getText().trim(), docPrescribedFaxField.getText().trim());
					newScript.setPatientID(patient.getId());
					patient.addActivePrescription(newScript);
					helper.addPrescriptionBG(newScript);

					// open a new add prescription window
					AddNewPrescriptionUI openAddNew;
					try {
						openAddNew = new AddNewPrescriptionUI("ManageRx", patient, patients, last, stock);
						openAddNew.setVisible(true);
						setVisible(false);
					} // end try
					catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // end catch
				} // end if
			} // end if
				// if not all fields have been filled in, open a popup
			else {
				JOptionPane.showMessageDialog(mainPanel,
						"Please fill in all required fields in order to add prescription.");
			} // end else
		} // end if
	} // end actionPerformed

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getComponent().equals(docPrescribedNameField)) {
			docPrescribedNameField.setText("");
			docPrescribedNameField.setForeground(Color.black);
		} // end if
		if (e.getComponent().equals(docPrescribedAddressField)) {
			docPrescribedAddressField.setText("");
			docPrescribedAddressField.setForeground(Color.black);
		} // end if
		if (e.getComponent().equals(dosageField) && dosageField.getText().equals("0 units")) {
			dosageField.setText("");
			dosageField.setForeground(Color.black);
		} // end if
		if (e.getComponent().equals(prescribedDurationField)) {
			prescribedDurationField.setText("");
			prescribedDurationField.setForeground(Color.black);
		} // end if

	} // end focusGained

	@Override
	public void focusLost(FocusEvent e) {
	} // end focusLost
} // end AddNewPrescriptionUI
