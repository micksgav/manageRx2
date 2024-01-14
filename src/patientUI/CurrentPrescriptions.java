
/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 23, 2023
 * @Last Modified: December 16, 2023
 * @Description: View current prescriptions page in the patient management section of ManageRx
 ***********************************************
 */

package patientUI;

import swingHelper.*;
import utilities.SQLHelper;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.*;

import mainUI.loginUI;
import mainUI.settingsUI;
import PatientManagement.*;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class CurrentPrescriptions extends JFrame implements ActionListener {

	Patient patient; // patient whose prescriptions are being viewed
	PatientList patients; // list containing all patient information

	// panels
	private JPanel buttonPanel; // header panel containing logo and buttons
	private JPanel mainPanel; // panel cotaining all prescription information
	private JPanel[] prescriptionPanels; // panels containing individual prescription info
	private JPanel[] editArchive; // panel containing options to edit or archive prescription
	private JPanel mainWithTopBar; // panel containing mainPanel and patient name, title, and add prescription
									// button
	private JPanel headerButtons; // panel for buttons other than back button in header

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton[] editPrescription; // edit a prescription
	private JButton[] archivePrescription; // archive a prescription
	private JButton createNewPrescription; // create a new prescription
	private JButton viewArchived; // view archived prescriptions
	private JButton backButton;

	// text elements
	private JLabel patientName; // patient name
	private JTextArea[] prescriptionInfo; // prescription information
	private JLabel currentPrescriptions = new JLabel("Current Prescriptions"); // current prescriptions title
	String[] drugBrandName; // array containing all drug names belonging to patient
	String[] drugGenName;
	String[] datePrescribed; // array containing all dates prescribed belonging to patient
	String[] numRefills; // array containing all number of refills belonging to patient
	String[] quantity; // array containing all quantities belonging to patient
	String[] dosage; // array containing all dosages belonging to patient
	String[] instructions; // array containing all instructions belonging to patient
	String[] prescribedDuration; // array containing all prescribed durations belonging to patient

	// last page
	boolean last; // if last is true, the last page was PatientManagementUI. If last is false, the last page was EditPatientInfoUI
	
	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public CurrentPrescriptions(String title, Patient patient, PatientList patients, boolean last) {

		// setup screen attributes
		FlatLightLaf.setup(); // custom look and feel
		setTitle(title);

		// set size of window
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// instantiate variables
		this.patient = patient;
		this.patients = patients;
		this.last = last;
		drugBrandName = new String[patient.getActivePrescriptions().length()];
		drugGenName = new String[patient.getActivePrescriptions().length()];
		datePrescribed = new String[patient.getActivePrescriptions().length()];
		numRefills = new String[patient.getActivePrescriptions().length()];
		quantity = new String[patient.getActivePrescriptions().length()];
		dosage = new String[patient.getActivePrescriptions().length()];
		instructions = new String[patient.getActivePrescriptions().length()];
		prescribedDuration = new String[patient.getActivePrescriptions().length()];
		prescriptionPanels = new JPanel[patient.getActivePrescriptions().length()];

		// add patient info to all arrays
		for (int i = 0; i < drugBrandName.length; i++) {
			drugBrandName[i] = "Brand Name: " + patient.getActivePrescriptions().atIndex(i).getBrandName();
			datePrescribed[i] = "Date Prescribed: " + patient.getActivePrescriptions().atIndex(i).getDate();
			numRefills[i] = "Number of Refills: "
					+ String.valueOf(patient.getActivePrescriptions().atIndex(i).getRefills());
			quantity[i] = "Quantity: " + String.valueOf(patient.getActivePrescriptions().atIndex(i).getQuantity());
			dosage[i] = "Dosage: " + String.valueOf(patient.getActivePrescriptions().atIndex(i).getDosage()[0][0]);
			instructions[i] = "Instructions: " + patient.getActivePrescriptions().atIndex(i).getInstructions();
			prescribedDuration[i] = "Prescribed Duration: " + patient.getActivePrescriptions().atIndex(i).getDuration();
		} // end for

		// setup all buttons for the header
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

		// add back button to header
		GridBagConstraints backConstraints = new GridBagConstraints(); // contstraints for back button

		backConstraints.gridx = 0;
		backConstraints.gridy = 0;
		backConstraints.gridwidth = 1;
		backConstraints.anchor = GridBagConstraints.WEST;
		backConstraints.ipadx = (int) (screenDims.width * 0.02);
		backConstraints.weightx = 0.45;
		backConstraints.insets = new Insets(0, (int) (screenDims.width * 0.01), 0, 0);
		this.buttonPanel.add(backButton, backConstraints);

		// add buttons to header
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

		mainPanel = new JPanel(new GridBagLayout()); // information about GridBagLayout from
														// https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html

		// fonts and borders
		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font, used for most elements
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // larger font, mostly used for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // outer
																													// border
																													// for
																													// text
																													// boxes
																													// and
																													// buttons
																													// https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01)); // inner border for text boxes and
																					// buttons
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // overall border for
																								// text boxes and
																								// buttons
		Border simpleLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // simple
																											// line
																											// border
																											// used for
																											// mainPanel

		// panel to hold name, create button, and all prescriptions
		mainWithTopBar = new JPanel(new GridBagLayout());

		// add patient name to screen
		GridBagConstraints nameConstraints = new GridBagConstraints(); // constraints for patient namae

		nameConstraints.fill = GridBagConstraints.BOTH;
		nameConstraints.gridx = 0;
		nameConstraints.gridy = 1;
		nameConstraints.gridwidth = 2;
		nameConstraints.anchor = GridBagConstraints.NORTH;
		patientName = new JLabel(patient.getName());
		patientName.setFont(nameFont);
		patientName.setHorizontalAlignment(JLabel.CENTER);
		mainWithTopBar.add(patientName, nameConstraints);

		// add page title to screen
		GridBagConstraints titleConstraints = new GridBagConstraints(); // constraints for page title

		titleConstraints.fill = GridBagConstraints.BOTH;
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 1;
		titleConstraints.gridwidth = 1;
		titleConstraints.anchor = GridBagConstraints.SOUTH;
		titleConstraints.weightx = 0.01;
		currentPrescriptions.setFont(nameFont);
		currentPrescriptions.setHorizontalAlignment(JLabel.LEFT);
		mainWithTopBar.add(currentPrescriptions, titleConstraints);

		// add create prescription button to screen
		GridBagConstraints createConstraints = new GridBagConstraints(); // constraints for create button

		createConstraints.fill = GridBagConstraints.BOTH;
		createConstraints.gridx = 1;
		createConstraints.gridy = 1;
		createConstraints.gridwidth = 1;
		createConstraints.anchor = GridBagConstraints.SOUTHEAST;
		createNewPrescription = new JButton("Add New Prescription");
		createNewPrescription.addActionListener(this);
		createNewPrescription.setFont(genFont);
		createNewPrescription.setHorizontalAlignment(JButton.RIGHT);
		createNewPrescription.setBorder(textBoxBorder);
		createNewPrescription.setMaximumSize(new Dimension((int) (screenDims.width * 0.1), screenDims.height));
		mainWithTopBar.add(createNewPrescription, createConstraints);

		// generate inner panels
		for (int i = 0; i < prescriptionPanels.length; i++) {
			prescriptionPanels[i] = new JPanel(new GridLayout(2, 1));
		} // end for

		prescriptionInfo = new JTextArea[drugBrandName.length];
		editArchive = new JPanel[drugBrandName.length];
		editPrescription = new JButton[drugBrandName.length];
		archivePrescription = new JButton[drugBrandName.length];

		// add buttons and text to each inner panel
		for (int i = 0; i < drugBrandName.length; i++) {
			editPrescription[i] = new JButton("Edit Prescription");
			editPrescription[i].setActionCommand("edit" + i);
			archivePrescription[i] = new JButton("Archive Prescription");
			archivePrescription[i].setActionCommand("archive" + i);
			editPrescription[i].addActionListener(this);
			archivePrescription[i].addActionListener(this);
			editPrescription[i].setFont(genFont);
			archivePrescription[i].setFont(genFont);
			editPrescription[i].setBorder(textBoxBorder);
			archivePrescription[i].setBorder(textBoxBorder);
			prescriptionInfo[i] = new JTextArea();
			editArchive[i] = new JPanel(new GridLayout(1, 2, (int) (screenDims.width * 0.005), 0));
			editArchive[i].add(editPrescription[i]);
			editArchive[i].add(archivePrescription[i]);
			prescriptionInfo[i].setText(drugBrandName[i] + "\n" + datePrescribed[i] + "\n" + numRefills[i] + "\n"
					+ quantity[i] + "\n" + dosage[i] + "\n" + instructions[i] + "\n" + prescribedDuration[i]);
			prescriptionPanels[i].setBorder(simpleLine);
			prescriptionInfo[i].setEditable(false);
			prescriptionPanels[i].add(prescriptionInfo[i]);
			prescriptionPanels[i].add(editArchive[i]);
		} // end for

		// set height of mainPanel grid ****** redundant?
		if ((double) drugBrandName.length / 2 - (int) drugBrandName.length / 2 < 0.5) {
			mainPanel = new JPanel(new GridLayout((int) Math.floor((double) drugBrandName.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		} // end if
		else {
			mainPanel = new JPanel(new GridLayout((int) Math.ceil((double) drugBrandName.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		} // end else

		// add inner elements to main panel
		for (int i = 0; i < prescriptionPanels.length; i++) {
			mainPanel.add(prescriptionPanels[i]);
		} // end for

		mainPanel.setBorder(textBoxBorder);

		JScrollPane mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // scroll bar for current prescriptions

		GridBagConstraints prescriptionConstraints = new GridBagConstraints(); // constraints for all prescriptions
																				// panel

		prescriptionConstraints.fill = GridBagConstraints.BOTH;
		prescriptionConstraints.gridx = 0;
		prescriptionConstraints.gridy = 2;
		prescriptionConstraints.gridwidth = 2;
		prescriptionConstraints.anchor = GridBagConstraints.NORTH;
		prescriptionConstraints.ipady = (int) (screenDims.height * 0.7);
		mainWithTopBar.add(mainScroll, prescriptionConstraints);

		// add view archived button
		viewArchived = new JButton("View Archived Prescriptions");
		viewArchived.addActionListener(this);
		viewArchived.setBorder(textBoxBorder);
		viewArchived.setFont(genFont);

		GridBagConstraints viewArchivedConstraints = new GridBagConstraints(); // constraints for view archived button

		viewArchivedConstraints.fill = GridBagConstraints.BOTH;
		viewArchivedConstraints.gridx = 0;
		viewArchivedConstraints.gridy = 3;
		viewArchivedConstraints.gridwidth = 1;
		viewArchivedConstraints.anchor = GridBagConstraints.WEST;
		viewArchivedConstraints.insets = new Insets((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				0, (int) (screenDims.width * 0.5));
		mainWithTopBar.add(viewArchived, viewArchivedConstraints);

		add(mainWithTopBar);
	} // end CurrentPrescriptions

	@Override
	public void actionPerformed(ActionEvent e) {
		SQLHelper SQLHelper = new SQLHelper(); // sql interactions
		// open stock page
		if (e.getActionCommand().equals("openStock")) {
			System.out.println("Stock");
		} // end if
		// open order page
		if (e.getActionCommand().equals("openOrder")) {
			System.out.println("Order");
		} // end if
		// open settings page
		if (e.getActionCommand().equals("openSettings")) {
			System.out.println("Settings");
		} // end if
		// open patient management page
		if (e.getActionCommand().equals("openPatientManager")) {
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
		// go back to previous page
		if (e.getActionCommand().equals("Back")) {
			if (last == true) {
			ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patient, patients);
			openManage.setVisible(true);
			setVisible(false);
			} // end if
			else {
				EditPatientInfoUI openEdit;
				try {
					openEdit = new EditPatientInfoUI("ManageRx", patient, patients);
					openEdit.setVisible(true);
					setVisible(false);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} //end else
		} // end if
		// open archived prescriptions page, if patient has any
		if (e.getActionCommand().equals("View Archived Prescriptions")) {
			if (patient.getArchivedPrescriptions().length() > 0) {
				ArchivedPrescriptionsUI openArchive = new ArchivedPrescriptionsUI("ManageRx", patient, patients, last);
				openArchive.setVisible(true);
				setVisible(false);
			} // end if
			else {
				JOptionPane.showMessageDialog(mainPanel, "Patient Does Not Have Any Archived Prescriptions");
			} // end else
		} // end if
		// open add prescription page
		if (e.getActionCommand().equals("Add New Prescription")) {
			AddNewPrescriptionUI openAddNew = new AddNewPrescriptionUI("ManageRx", patient, patients, last);
			openAddNew.setVisible(true);
			setVisible(false);
		} // end if
		// change button text/delete if edit/archive is pressed
		for (int i = 0; i < drugBrandName.length; i++) {
			String keyEdit = "edit" + i; // edit command key
			if (e.getActionCommand().equals(keyEdit)) {
				prescriptionInfo[i].setEditable(true);
				editPrescription[i].setText("Cancel");
				editPrescription[i].setActionCommand("cancel" + i);
				archivePrescription[i].setText("Save");
				archivePrescription[i].setActionCommand("save" + i);

			} // end if
			String keyArchive = "archive" + i; // archive command key
			if (e.getActionCommand().equals(keyArchive)) {
				patient.removeActivePrescription(patient.getActivePrescriptions().atIndex(i));
				prescriptionPanels[i].setVisible(false);

			} // end if
			// save prescription info to patient and update sql
			if (archivePrescription[i].getText().equals("Save")) {
				if (e.getActionCommand().equals("save" + i)) {
					String prescriptionInfoString = prescriptionInfo[i].getText().replaceAll("Brand Name: ", "")
							.replaceAll("Date Prescribed: ", "").replaceAll("Number of Refills: ", "")
							.replaceAll("Quantity: ", "").replaceAll("Dosage: ", "").replaceAll("Dosage: ", "")
							.replaceAll("Instructions: ", "").replaceAll("Prescribed Duration: ", "").trim(); // all info in prescription area
					String[] info = prescriptionInfoString.split("\n"); // info in prescription area broken up by line
					String[][] drugDosage = new String[1][1]; // drug dosage
					drugDosage[0][0] = info[4];
					patient.getActivePrescriptions().atIndex(i).setBrandName(info[0]);
					SQLHelper.updateBG("PrescriptionInfo", "drugName",
							patient.getActivePrescriptions().atIndex(i).getBrandName(), patient.getId());
					patient.getActivePrescriptions().atIndex(i).setDate(info[1]);
					SQLHelper.updateBG("PrescriptionInfo", "datePrescribed",
							patient.getActivePrescriptions().atIndex(i).getDate(), patient.getId());
					patient.getActivePrescriptions().atIndex(i).setRefills(Integer.parseInt(info[2]));
					SQLHelper.updateBG("PrescriptionInfo", "numRefills",
							patient.getActivePrescriptions().atIndex(i).getRefills(), patient.getId());
					patient.getActivePrescriptions().atIndex(i).setQuantity(Integer.parseInt(info[3]));
					SQLHelper.updateBG("PrescriptionInfo", "quantity",
							patient.getActivePrescriptions().atIndex(i).getQuantity(), patient.getId());
					patient.getActivePrescriptions().atIndex(i).setDosage(drugDosage);
					SQLHelper.updateBG("PrescriptionInfo", "dosage",
							patient.getActivePrescriptions().atIndex(i).getDosage(), patient.getId());
					patient.getActivePrescriptions().atIndex(i).setDuration(info[info.length - 1]);
					SQLHelper.updateBG("PrescriptionInfo", "prescribedDuration",
							patient.getActivePrescriptions().atIndex(i).getDuration(), patient.getId());
					String instructions = ""; // new prescriptions
					for (int j = 5; j <= info.length - 2; j++) {
						instructions += info[j] + " ";
					} // end for
					patient.getActivePrescriptions().atIndex(i).setInstructions(instructions.trim());
					SQLHelper.updateBG("PrescriptionInfo", "instructions",
							patient.getActivePrescriptions().atIndex(i).getInstructions(), patient.getId());

					editPrescription[i].setText("Edit Prescription");
					editPrescription[i].setActionCommand("edit" + i);
					archivePrescription[i].setText("Archive Prescription");
					archivePrescription[i].setActionCommand("archive" + i);
				} // end if
				// update button text
				if (e.getActionCommand().equals("cancel" + i)) {
					editPrescription[i].setText("Edit Prescription");
					editPrescription[i].setActionCommand("edit" + i);
					archivePrescription[i].setText("Archive Prescription");
					archivePrescription[i].setActionCommand("archive" + i);
				} // end if
			} // end if
		} // end for
	} // end actionPerformed
} // end CurrentPrescriptions