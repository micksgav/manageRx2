
/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 23, 2023
 * @Last Modified: December 16, 2023
 * @Description: View archived prescriptions page in the patient management section of ManageRx
 ***********************************************
 */

package patientUI;

import swingHelper.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.*;

import mainUI.loginUI;
import mainUI.orderUI;
import mainUI.stockUI;
import PatientManagement.*;
import inventory.AllStock;

import java.awt.*;
import java.awt.event.*;

public class ArchivedPrescriptionsUI extends JFrame implements ActionListener {

	// app information
	Patient patient; // patient whose prescriptions are being viewed
	PatientList patients; // list of all patients
	AllStock stock;

	// panels
	private JPanel buttonPanel; // header panel containing logo and buttons
	private JPanel mainPanel; // panel cotaining all prescription information
	private JPanel[] prescriptionPanels; // panels containing individual prescription info
	private JPanel mainWithTopBar; // panel containing mainPanel and patient name, title, and add prescription
									// button
	private JPanel headerButtons; // buttons other than back for header

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton viewActive; // view active prescriptions
	private JButton backButton; // back button

	// text elements
	private JLabel patientName; // patient name
	private JTextArea[] prescriptionInfo; // prescription information
	private JLabel archivedPrescriptions = new JLabel("Archived Prescriptions"); // title label
	String[] drugBrandName; // array containing all archived drug names
	String[] drugGenName;
	String[] datePrescribed; // array containing all archived dates prescribed
	String[] numRefills; // array containing all archived number of refills
	String[] quantity; // array containing all archived quantities
	String[] dosage; // array containing all archived dosages
	String[] instructions; // array contaiing all archived instructions
	String[] prescribedDuration; // array containing all archived prescribed durations
	String[] docName; // array containing all doctor names belonging to patient
	String[] docPhone; // array containing all doctor phone numbers belonging to patient
	String[] docFax; // array containing all doctor fax numbers belonging to patient
	String[] docAddress; // array containing all doctor addresses belonging to patient

	// last page
	boolean last; // if last is true, the last page was PatientManagementUI. If last is false, the
					// last page was EditPatientInfoUI

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public ArchivedPrescriptionsUI(String title, Patient patient, PatientList patients, boolean last, AllStock stock) {

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
		this.last = last;
		this.stock = stock;
		drugBrandName = new String[patient.getArchivedPrescriptions().length()];
		drugGenName = new String[patient.getArchivedPrescriptions().length()];
		datePrescribed = new String[patient.getArchivedPrescriptions().length()];
		numRefills = new String[patient.getArchivedPrescriptions().length()];
		quantity = new String[patient.getArchivedPrescriptions().length()];
		dosage = new String[patient.getArchivedPrescriptions().length()];
		instructions = new String[patient.getArchivedPrescriptions().length()];
		prescribedDuration = new String[patient.getArchivedPrescriptions().length()];
		docName = new String[patient.getArchivedPrescriptions().length()];
		docPhone = new String[patient.getArchivedPrescriptions().length()];
		docFax = new String[patient.getArchivedPrescriptions().length()];
		docAddress = new String[patient.getArchivedPrescriptions().length()];
		prescriptionPanels = new JPanel[patient.getArchivedPrescriptions().length()];

		for (int i = 0; i < drugBrandName.length; i++) {
			drugBrandName[i] = "Brand Name: " + patient.getArchivedPrescriptions().atIndex(i).getBrandName();
			datePrescribed[i] = "Date Prescribed: " + patient.getArchivedPrescriptions().atIndex(i).getDate();
			numRefills[i] = "Number of Refills: "
					+ String.valueOf(patient.getArchivedPrescriptions().atIndex(i).getRefills());
			quantity[i] = "Quantity: " + String.valueOf(patient.getArchivedPrescriptions().atIndex(i).getQuantity());
			dosage[i] = "Dosage: " + String.valueOf(patient.getArchivedPrescriptions().atIndex(i).getDosage());
			instructions[i] = "Instructions: " + patient.getArchivedPrescriptions().atIndex(i).getInstructions();
			prescribedDuration[i] = "Prescribed Duration: "
					+ patient.getArchivedPrescriptions().atIndex(i).getDuration();
			docName[i] = "Doctor's Name: " + patient.getArchivedPrescriptions().atIndex(i).getDocName();
			docPhone[i] = "Doctor's Phone Number: " + patient.getArchivedPrescriptions().atIndex(i).getDocPhone();
			docFax[i] = "Doctor's Fax Number: " + patient.getArchivedPrescriptions().atIndex(i).getDocFax();
			docAddress[i] = "Doctor's Address: " + patient.getArchivedPrescriptions().atIndex(i).getDocAddress();
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

		// add all buttons other than back to header
		headerButtons = new JPanel(new FlowLayout());

		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for buttons other than
																				// back

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

		// panel containing everything below header
		mainWithTopBar = new JPanel(new GridBagLayout());

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
		mainWithTopBar.add(patientName, nameConstraints);

		// add page title to screen
		GridBagConstraints titleConstraints = new GridBagConstraints(); // constraints for page title

		titleConstraints.fill = GridBagConstraints.BOTH;
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 1;
		titleConstraints.gridwidth = 1;
		titleConstraints.anchor = GridBagConstraints.SOUTH;
		titleConstraints.weightx = 0.01;
		archivedPrescriptions.setFont(nameFont);
		archivedPrescriptions.setHorizontalAlignment(JLabel.LEFT);
		mainWithTopBar.add(archivedPrescriptions, titleConstraints);

		// add archived prescription panels

		// generate inner panels
		for (int i = 0; i < prescriptionPanels.length; i++) {
			prescriptionPanels[i] = new JPanel(new GridLayout(1, 1));
		} // end for

		prescriptionInfo = new JTextArea[drugBrandName.length];

		for (int i = 0; i < drugBrandName.length; i++) {
			prescriptionInfo[i] = new JTextArea();
			prescriptionInfo[i].setText(drugBrandName[i] + "\n" + datePrescribed[i] + "\n" + numRefills[i] + "\n"
					+ quantity[i] + "\n" + dosage[i] + "\n" + docName[i] + "\n" + docPhone[i] + "\n" + docFax[i] + "\n"
					+ docAddress[i] + "\n" + instructions[i] + "\n" + prescribedDuration[i]);
			prescriptionPanels[i].setBorder(simpleLine);
			prescriptionInfo[i].setEditable(false);
			prescriptionPanels[i].add(prescriptionInfo[i]);
		} // end for

		// set height of mainPanel grid
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
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // scroll bar for main panel

		GridBagConstraints prescriptionConstraints = new GridBagConstraints(); // constraints for prescription info
																				// panels

		prescriptionConstraints.fill = GridBagConstraints.BOTH;
		prescriptionConstraints.gridx = 0;
		prescriptionConstraints.gridy = 2;
		prescriptionConstraints.gridwidth = 2;
		prescriptionConstraints.anchor = GridBagConstraints.NORTH;
		prescriptionConstraints.ipady = (int) (screenDims.height * 0.7);
		mainWithTopBar.add(mainScroll, prescriptionConstraints);

		viewActive = new JButton("View Active Prescriptions");
		viewActive.addActionListener(this);
		viewActive.setBorder(textBoxBorder);
		viewActive.setFont(genFont);

		GridBagConstraints viewActivePrescriptionsConstraints = new GridBagConstraints(); // constraints for view active
																							// button

		viewActivePrescriptionsConstraints.fill = GridBagConstraints.BOTH;
		viewActivePrescriptionsConstraints.gridx = 0;
		viewActivePrescriptionsConstraints.gridy = 3;
		viewActivePrescriptionsConstraints.gridwidth = 1;
		viewActivePrescriptionsConstraints.anchor = GridBagConstraints.WEST;
		viewActivePrescriptionsConstraints.insets = new Insets((int) (screenDims.height * 0.01),
				(int) (screenDims.width * 0.01), 0, (int) (screenDims.width * 0.5));
		mainWithTopBar.add(viewActive, viewActivePrescriptionsConstraints);

		add(mainWithTopBar);
	} // end ArchivedPrescriptionsUI

	@Override
	public void actionPerformed(ActionEvent e) {
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
		// open active prescriptions page
		if (e.getActionCommand().equals("View Active Prescriptions")) {
			CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients, last, stock);
			openCurrent.setVisible(true);
			setVisible(false);
		} // end if
			// go back to previous page
		if (e.getActionCommand().equals("Back")) {
			if (last) {
				CurrentPrescriptions openCurrent = new CurrentPrescriptions("ManageRx", patient, patients, last, stock);
				openCurrent.setVisible(true);
				setVisible(false);
			}
		} // end if
	} // end actionPerformed
} // end ArchivedPrescriptions