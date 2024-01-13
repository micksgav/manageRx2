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

public class SearchForPatientUI extends JFrame implements ActionListener, FocusListener {

	/*
	 * Method Name: multipleIndex Author: John Brown Creation Date: January 5, 2024
	 * Modified Date: January 8, 2024 Description: searches addresses of patients
	 * whose indexes are in the array index until it finds a match
	 *
	 * @Paramaters: String address: address being checked for each patient, int[]
	 * index: contains indexes of patients whose addresses are being compared,
	 * PatientList patients: list of all patients
	 * 
	 * @Return Value: index[i] if found, or -1 if not found Data Type: int
	 * Dependencies: PatientList Throws/Exceptions: returns -1 if not found
	 */
	public static int multipleIndex(String address, int[] index, PatientList patients) {
		for (int i = 0; i < index.length; i++) {

			if (patients.returnData(index[i]).getAddress().equals(address)) {
				return index[i];
			} // end if
		} // end for
		return -1;
	} // end multipleIndex

	/*
	 * Method Name: searchPatient Author: John Brown Creation Date: January 5, 2024
	 * Modified Date: January 5, 2024 Description: finds indexes of all patients
	 * with the same birthday and returns an array of those indexes
	 *
	 * @Paramaters: PatientList patients: list of all patients, String birthDay:
	 * birthday being checked with each patient, String patientNameFieldText: user
	 * inputted patient name
	 * 
	 * @Return Value: array containing all found indexes Data Type: int
	 * Dependencies: PatientList Throws/Exceptions: returns array containing only -1
	 * if no patients found
	 */
	public static int[] searchPatient(PatientList patients, String birthday, String patientNameFieldText) {
		int[] index = patients.findPatientByBirthday(patientNameFieldText,
				Integer.parseInt(birthday.substring(birthday.indexOf(' '), birthday.indexOf(' ') + 3).trim()),
				Integer.parseInt(birthday.substring(0, birthday.indexOf(' ')).trim()),
				Integer.parseInt(birthday.substring(birthday.indexOf(' ') + 3).trim())); // indexes of patients

		int newSizeCounter = 0; // size of smaller array to return
		for (int i = 0; i < index.length; i++) {
			if (index[i] != -1) {
				newSizeCounter++;
			} // end if
			else {
				break;
			} // end else
		} // end for

		if (newSizeCounter > 0) {
			int[] reducedSizeIndex = new int[newSizeCounter]; // reduced size array of indexes, only has indexes with
																// same birthday

			for (int i = 0; i < newSizeCounter; i++) {
				reducedSizeIndex[i] = index[i];
			} // end for
			return reducedSizeIndex;
		} // end if
		else {
			int[] returnMinusOne = { -1 };
			return returnMinusOne;
		} // end else
	} // end searchPatient

	// patient information
	PatientList patients; // list of all patients
	Patient patient; // patient that is passed into other UIs

	// panels
	private JPanel buttonPanel; // header panel
	private JPanel mainPanel; // main panel containing mainGrid
	private JPanel mainGrid; // panel containing all textboxes and buttons
	private JPanel headerButtons; // buttons other than back in header

	// header buttons
	private JButton btnOpenStock; // open stock
	private JButton btnOpenOrder; // open order
	private JButton btnOpenSettings; // open settings
	private JButton btnOpenPatientManager; // open patient manager

	// main buttons
	private JButton search; // search for patient
	private JButton backButton; // go back to previous page

	// text elements
	private JLabel searchTitle = new JLabel("Search for a Patient"); // search title
	private JLabel patientNameLabel = new JLabel("Enter Patient's First and Last Name"); // patient name label
	private JTextField patientNameField; // patient name field
	private JLabel patientBirthdayLabel = new JLabel("Enter Patient's Birthday"); // patient birthday label
	private JTextField patientBirthdayField; // patient birthday field
	private Insets gridBagPadding; // padding for GridBagConstraints

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public SearchForPatientUI(String title, Patient patient, PatientList patients) {

		// setup screen attributes
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
		// screenDims.height /= 1.5;
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// instantiate variables
		this.patients = patients;
		this.patient = patient;

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
		headerButtons.add(btnOpenSettings);
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

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for text boxes and labels
		Font nameFont = new Font("Arial", Font.PLAIN, 75); // title font
		Color textBoxFill = new Color(204, 204, 204); // text box fill
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // compounded border for
																								// boxes and buttons
		gridBagPadding = new Insets((int) (screenDims.height * 0.05), 0, (int) (screenDims.height * 0.08), 0);

		// add main panel elements
		mainPanel = new JPanel(new GridBagLayout());

		// add page title
		GridBagConstraints titleConstraints = new GridBagConstraints(); // constraints for page title

		titleConstraints.gridx = 2;
		titleConstraints.gridy = 1;
		titleConstraints.anchor = GridBagConstraints.NORTH;
		titleConstraints.insets = gridBagPadding;
		searchTitle.setFont(nameFont);
		searchTitle.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(searchTitle, titleConstraints);

		// add main grid elements (text boxes, labels)
		mainGrid = new JPanel(new GridLayout(4, 1, 0, (int) (screenDims.height * 0.01)));

		patientNameField = new JTextField();
		patientNameField.setFont(genFont);
		patientNameLabel.setFont(genFont);
		patientNameField.setBorder(textBoxBorder);
		mainGrid.add(patientNameLabel);
		mainGrid.add(patientNameField);

		patientBirthdayField = new JTextField("DD/MM/YYYY");
		patientBirthdayField.setFont(genFont);
		patientBirthdayLabel.setFont(genFont);
		patientBirthdayField.setBorder(textBoxBorder);
		patientBirthdayField.setForeground(textBoxFill);
		patientBirthdayField.addFocusListener(this);
		mainGrid.add(patientBirthdayLabel);
		mainGrid.add(patientBirthdayField);

		GridBagConstraints gridConstraints = new GridBagConstraints(); // constraints for main grid

		gridConstraints.gridx = 2;
		gridConstraints.gridy = 2;
		gridConstraints.anchor = GridBagConstraints.NORTH;
		mainPanel.add(mainGrid, gridConstraints);

		// add search button
		GridBagConstraints searchConstraints = new GridBagConstraints(); // constraints for search button

		searchConstraints.gridx = 2;
		searchConstraints.gridy = 3;
		searchConstraints.ipadx = (int) (screenDims.width * 0.05);
		searchConstraints.insets = gridBagPadding;
		searchConstraints.anchor = GridBagConstraints.SOUTH;
		search = new JButton("Search");
		search.setActionCommand("Search");
		search.addActionListener(this);
		search.setFont(genFont);
		search.setBorder(textBoxBorder);
		mainPanel.add(search, searchConstraints);

		add(mainPanel, BorderLayout.CENTER);

	} // end SearchForPatientUI

	public void actionPerformed(ActionEvent e) {
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
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		} // end if
			// search for patient and open manage patient page
		if (e.getActionCommand().equals("Search")) {
			int[] index = { -1 }; // index of patient with same birthday and name as fields
			// ensure fields are filled in
			if (patientNameField.getText().length() > 0 && !(patientBirthdayField.getText().equals("DD/MM/YYYY"))
					&& patientBirthdayField.getText().length() > 0) {
				String birthday = patientBirthdayField.getText().replace('/', ' '); // patient birthday
				index = searchPatient(patients, birthday, patientNameField.getText().trim());
			} // end if
			if (index[0] >= 0) {
				if (index.length == 1) {
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patients.returnData(index[0]),
							patients);
					openManage.setVisible(true);
					setVisible(false);
				} // end if
				else {
					// ask for address if at least 2 patients have same name and birthday, then open
					// manage patient page
					String address = JOptionPane.showInputDialog(mainPanel,
							"Two or More Patients With the Specified Name and Birthday Have Been Found. Please Enter Patient's Address to Continue.",
							"Multiple Patients Found", JOptionPane.INFORMATION_MESSAGE); // patient address
					int finalIndex = multipleIndex(address, index, patients);
					if (finalIndex >= 0) {
						ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx",
								patients.returnData(finalIndex), patients);
						openManage.setVisible(true);
						setVisible(false);
					} // end if
					else {
						JOptionPane.showMessageDialog(mainPanel, "Error, Patient Not Found.", "Patient Not Found",
								JOptionPane.INFORMATION_MESSAGE);
					} // end else
				} // end else
			} // end if
			else {
				JOptionPane.showMessageDialog(mainPanel, "Error, Patient Not Found.", "Patient Not Found",
						JOptionPane.INFORMATION_MESSAGE);
			} // end else
		} // end if
	} // end actionPerformed

	// https://stackoverflow.com/questions/18103839/display-disappear-default-text-in-textfield-when-user-enters-data-erase-the-text
	// used for focus event
	public void focusGained(FocusEvent e) {
		// birthday box pressed
		if (patientBirthdayField.getText().trim().equals("DD/MM/YYYY")) {
			patientBirthdayField.setText("");
			patientBirthdayField.setForeground(Color.black);
		} // end if
	} // end focusGained

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	} // end focusLost
} // end SearchForPatientUI
