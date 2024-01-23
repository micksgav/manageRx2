
/**
 ***********************************************
 * @Author : John Brown
 * @Originally made : December 23, 2023
 * @Last Modified: January 21, 2024
 * @Description: View insurance page in the patient management section of ManageRx
 ***********************************************
 */

package patientUI;

import swingHelper.*;
import utilities.SQLHelper;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.*;

import mainUI.orderUI;
import mainUI.stockUI;
import patientManagement.*;
import inventory.AllStock;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

public class ViewInsuranceUI extends JFrame implements ActionListener {

	/*
	 * Method Name: deleteInsurnace Author: John Brown Creation Date: January 18,
	 * 2024 Modified Date: January 18, 2024 Description: deletes an insurance plan
	 * from a patient
	 *
	 * @Paramaters: Patient patient: patient who is having an insurance plan removed
	 * 
	 * @Return Value: none Data Type: void Dependencies: Patient Throws/Exceptions:
	 * none
	 */
	public static void deleteInsurance(Patient patient) {
		for (int i = 0; i < patient.getInsuranceInformation().size(); i++) {
			if (patient.getInsuranceInformation().get(i).getDelete() == true) {
				patient.removeActivePrescription(patient.getActivePrescriptions().atIndex(i));
			} // end if
		} // end for
	} // end deleteInsurance

	// app information
	Patient patient; // patient whose insurance is being viewed
	PatientList patients; // list of all patients
	AllStock stock; // complete stock for pharmacy

	// panels
	private JPanel buttonPanel; // header panel containing logo and buttons
	private JPanel mainPanel; // panel cotaining all prescription information
	private JPanel[] insurancePanels; // panels containing individual insurance info
	private JPanel[] editArchive; // panel containing options to edit or archive prescription
	private JPanel mainWithTopBar; // panel containing mainPanel and patient name, title, and add prescription
									// button
	private JPanel headerButtons; // buttons in header other than back

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton[] editInsurance; // edit a prescription
	private JButton[] deleteInsurance; // archive a prescription
	private JButton createNewInsurance; // create new insurance
	private JButton backButton; // back button

	// text elements
	private JLabel patientName; // patient name
	private JTextArea[] insuranceInfo; // prescription information
	private JLabel insuranceTitle = new JLabel("Insurance Information"); // title label
	String[] insuranceCompany; // array containing all insurance company info
	String[] insuranceNumber; // array containing all insurance number info

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public ViewInsuranceUI(String title, Patient patient, PatientList patients, AllStock stock) {

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
		this.stock = stock;
		insuranceCompany = new String[patient.getInsuranceInformation().size()];
		insuranceNumber = new String[patient.getInsuranceInformation().size()];
		insurancePanels = new JPanel[patient.getInsuranceInformation().size()];

		for (int i = 0; i < insuranceCompany.length; i++) {
			insuranceCompany[i] = "Company: " + patient.getInsuranceInformation().get(i).getCompany();
			insuranceNumber[i] = "Insurance Number: "
					+ String.valueOf(patient.getInsuranceInformation().get(i).getNumber());
		} // end for

		// setup all buttons for the header
		stockIcon = stockIcon.setScale(0.12);
		orderIcon = orderIcon.setScale(0.12);
		settingsIcon = settingsIcon.setScale(0.12);
		patientsIcon = patientsIcon.setScale(0.12);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.buttonPanel.setBorder(new LineBorder(Color.BLACK, 2));

		JLabel label = new JLabel("ManageRx"); // header label
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

		// add all other buttons to header
		headerButtons = new JPanel(new FlowLayout());

		headerButtons.add(label);
		headerButtons.add(btnOpenStock);
		headerButtons.add(btnOpenOrder);
		headerButtons.add(btnOpenPatientManager);

		GridBagConstraints overallButtonConstraints = new GridBagConstraints(); // constraints for header buttons other
																				// than back

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

		// add main panel elements
		mainWithTopBar = new JPanel(new GridBagLayout());

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
		mainWithTopBar.add(patientName, nameConstraints);

		// add page title to screen
		GridBagConstraints titleConstraints = new GridBagConstraints(); // constraints for page title

		titleConstraints.fill = GridBagConstraints.BOTH;
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 1;
		titleConstraints.gridwidth = 1;
		titleConstraints.anchor = GridBagConstraints.SOUTH;
		titleConstraints.weightx = 0.01;
		insuranceTitle.setFont(nameFont);
		insuranceTitle.setHorizontalAlignment(JLabel.LEFT);
		mainWithTopBar.add(insuranceTitle, titleConstraints);

		// add create insurance button to screen
		GridBagConstraints createConstraints = new GridBagConstraints(); // constraints for create insurance button

		createConstraints.fill = GridBagConstraints.BOTH;
		createConstraints.gridx = 1;
		createConstraints.gridy = 1;
		createConstraints.gridwidth = 1;
		createConstraints.anchor = GridBagConstraints.SOUTHEAST;
		createNewInsurance = new JButton("Add New Insurance");
		createNewInsurance.addActionListener(this);
		createNewInsurance.setFont(genFont);
		createNewInsurance.setHorizontalAlignment(JButton.RIGHT);
		createNewInsurance.setBorder(textBoxBorder);
		createNewInsurance.setMaximumSize(new Dimension((int) (screenDims.width * 0.1), screenDims.height));
		mainWithTopBar.add(createNewInsurance, createConstraints);

		// generate inner panels
		for (int i = 0; i < insurancePanels.length; i++) {
			insurancePanels[i] = new JPanel(new GridLayout(2, 1));
		} // end for

		insuranceInfo = new JTextArea[insuranceCompany.length];
		editArchive = new JPanel[insuranceCompany.length];
		editInsurance = new JButton[insuranceCompany.length];
		deleteInsurance = new JButton[insuranceCompany.length];

		for (int i = 0; i < insuranceCompany.length; i++) {
			editInsurance[i] = new JButton("Edit");
			editInsurance[i].addActionListener(this);
			editInsurance[i].setActionCommand("edit" + i);
			deleteInsurance[i] = new JButton("Delete");
			deleteInsurance[i].addActionListener(this);
			deleteInsurance[i].setActionCommand("delete" + i);
			editInsurance[i].setFont(genFont);
			deleteInsurance[i].setFont(genFont);
			editInsurance[i].setBorder(textBoxBorder);
			deleteInsurance[i].setBorder(textBoxBorder);
			insuranceInfo[i] = new JTextArea();
			editArchive[i] = new JPanel(new GridLayout(1, 2, (int) (screenDims.width * 0.005), 0));
			editArchive[i].add(editInsurance[i]);
			editArchive[i].add(deleteInsurance[i]);
			insuranceInfo[i].setText(insuranceCompany[i] + "\n" + insuranceNumber[i]);
			insurancePanels[i].setBorder(simpleLine);
			insuranceInfo[i].setEditable(false);
			insurancePanels[i].add(insuranceInfo[i]);
			insurancePanels[i].add(editArchive[i]);
		} // end for

		// set height of mainPanel grid
		if ((double) insurancePanels.length / 2 - (int) insurancePanels.length / 2 < 0.5) {
			mainPanel = new JPanel(new GridLayout((int) Math.floor((double) insurancePanels.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		} // end if
		else {
			mainPanel = new JPanel(new GridLayout((int) Math.ceil((double) insurancePanels.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		} // end else

		// add inner elements to main panel
		for (int i = 0; i < insurancePanels.length; i++) {
			mainPanel.add(insurancePanels[i]);
		} // end for

		mainPanel.setBorder(textBoxBorder);

		// add insurance info to mainPanel
		JScrollPane mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // scroll bar for main panel

		GridBagConstraints insuranceConstraints = new GridBagConstraints(); // constraints for insurance info

		insuranceConstraints.fill = GridBagConstraints.BOTH;
		insuranceConstraints.gridx = 0;
		insuranceConstraints.gridy = 2;
		insuranceConstraints.gridwidth = 2;
		insuranceConstraints.anchor = GridBagConstraints.NORTH;
		insuranceConstraints.ipady = (int) (screenDims.height * 0.7);
		mainWithTopBar.add(mainScroll, insuranceConstraints);

		add(mainWithTopBar);
	} // end ViewInsuranceUI

	@Override
	public void actionPerformed(ActionEvent e) {
		SQLHelper helper = new SQLHelper(); // connect to sql
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
			// go back to previous page
		if (e.getActionCommand().equals("Back")) {
			deleteInsurance(patient);
			EditPatientInfoUI openEdit;
			try {
				openEdit = new EditPatientInfoUI("ManageRx", patient, patients, stock);
				openEdit.setVisible(true);
				setVisible(false);
			} // end try
			catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // end catch
		} // end if
			// open add new insurance page
		if (e.getActionCommand().equals("Add New Insurance")) {
			deleteInsurance(patient);
			AddNewInsuranceUI openAddNew = new AddNewInsuranceUI("ManageRx", patient, patients, stock);
			openAddNew.setVisible(true);
			setVisible(false);
		} // end if
			// edit/delete insurance plan, then change the text on buttons to cancel/save
			// while in edit menu. If edit is saved, update insurance info
		for (int i = 0; i < patient.getInsuranceInformation().size(); i++) {
			if (e.getActionCommand().equals("edit" + i)) {
				insuranceInfo[i].setEditable(true);
				editInsurance[i].setText("Cancel");
				editInsurance[i].setActionCommand("cancel" + i);
				deleteInsurance[i].setText("Save");
				deleteInsurance[i].setActionCommand("save" + i);
			} // end if
			if (e.getActionCommand().equals("delete" + i)) {
				helper.removeInsurance(patient.getInsuranceInformation().get(i).getID()); // may not work
				patient.getInsuranceInformation().get(i).setDelete(true);
				insurancePanels[i].setVisible(false);
			} // end if
			if (deleteInsurance[i].getText().equals("Save")) {
				if (e.getActionCommand().equals("save" + i)) {
					String insuranceInfoString = insuranceInfo[i].getText().replaceAll("Company: ", "")
							.replaceAll("Insurance Number: ", "").trim(); // full insurance
																			// info field
					String[] info = insuranceInfoString.split("\n"); // insurance info field split into lines
					patient.getInsuranceInformation().get(i).setCompany(info[0]);
					patient.getInsuranceInformation().get(i).setNumber(Integer.parseInt(info[1]));

					helper.updateInsuranceBG("InsuranceInfo", "company",
							patient.getInsuranceInformation().get(i).getCompany(),
							patient.getInsuranceInformation().get(i).getID());
					helper.updateInsuranceBG("InsuranceInfo", "number",
							patient.getInsuranceInformation().get(i).getNumber(),
							patient.getInsuranceInformation().get(i).getID());

					editInsurance[i].setText("Edit");
					editInsurance[i].setActionCommand("edit" + i);
					deleteInsurance[i].setText("Delete");
					deleteInsurance[i].setActionCommand("archive" + i);
				} // end if
				if (e.getActionCommand().equals("cancel" + i)) {
					editInsurance[i].setText("Edit");
					editInsurance[i].setActionCommand("edit" + i);
					deleteInsurance[i].setText("Delete");
					deleteInsurance[i].setActionCommand("archive" + i);
				} // end if
			} // end if
		} // end for
	} // end actionPerformed
} // end ViewInsuranceUI
