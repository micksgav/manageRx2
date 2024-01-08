package patientUI;

import swingHelper.*;

import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.*;

import mainUI.loginUI;
import mainUI.settingsUI;
import PatientManagement.*;

import java.awt.*;
import java.awt.event.*;

public class ViewInsuranceUI extends JFrame implements ActionListener {
	private JButton openSettings = new JButton();
	private JButton openPatientManager = new JButton();
	private JButton openStock = new JButton();
	private JButton openOrder = new JButton();
	private loginUI login = new loginUI();
	private settingsUI settings = new settingsUI();
	private patientManagerUI patientManager = new patientManagerUI();
	// private StockUI stock = new StockUI();
	// private OrderUI order = new OrderUI();

	Patient patient; // patient whose prescriptions are being viewed
	PatientList patients;

	// panels
	private JPanel buttonPanel; // header panel containing logo and buttons
	private JPanel mainPanel; // panel cotaining all prescription information
	private JPanel[] insurancePanels; // panels containing individual insurance info
	private JPanel[] editArchive; // panel containing options to edit or archive prescription
	private JPanel mainWithTopBar; // panel containing mainPanel and patient name, title, and add prescription button
	private JPanel headerButtons;

	// header buttons
	private JButton btnOpenStock; // open stock page
	private JButton btnOpenOrder; // open order page
	private JButton btnOpenSettings; // open settings page
	private JButton btnOpenPatientManager; // open patient manager page

	// main buttons
	private JButton[] editInsurance; // edit a prescription
	private JButton[] deleteInsurance; // archive a prescription
	private JButton createNewInsurance; // create new insurance
	private JButton backButton;

	// text elements
	private JLabel patientName; // patient name
	private JTextArea[] insuranceInfo; // prescription information
	private JLabel insuranceTitle = new JLabel("Insurance Information"); // title label
	String[] insuranceCompany;
	String[] insuranceNumber;
	String[] insuranceNotes;

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public ViewInsuranceUI(String title, Patient patient, PatientList patients) {
		FlatLightLaf.setup(); // custom look and feel
		setTitle(title);
		
		// set size of window
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// Rectangle screenDims = new Rectangle(1366, 768);
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// instantiate variables
		this.patient = patient;
		this.patients = patients;
		insuranceCompany = new String[patient.getInsuranceInformation().size()];
		insuranceNumber = new String[patient.getInsuranceInformation().size()];
		insuranceNotes = new String[patient.getInsuranceInformation().size()];
		insurancePanels = new JPanel[patient.getInsuranceInformation().size()];
		
		for (int i = 0; i < insuranceCompany.length; i++) {
			insuranceCompany[i] = "Company: " + patient.getInsuranceInformation().get(i).getCompany();
			insuranceNumber[i] = "Insurance Number: " + String.valueOf(patient.getInsuranceInformation().get(i).getNumber());
			insuranceNotes[i] = "Notes: " + patient.getInsuranceInformation().get(i).getNotes();
		}

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

		// fonts and borders
		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font, used for most elements
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // larger font, mostly used for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // outer border for text boxes and buttons https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01)); // inner border for text boxes and buttons
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding); // overall border for text boxes and buttons
		Border simpleLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // simple line border used for mainPanel

		// panel to hold name, create button, and all prescriptions
		mainWithTopBar = new JPanel(new GridBagLayout());

		// add patient name to screen
		GridBagConstraints nameConstraints = new GridBagConstraints();
		
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
		GridBagConstraints titleConstraints = new GridBagConstraints();

		titleConstraints.fill = GridBagConstraints.BOTH;
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 1;
		titleConstraints.gridwidth = 1;
		titleConstraints.anchor = GridBagConstraints.SOUTH;
		titleConstraints.weightx = 0.01;
		insuranceTitle.setFont(nameFont);
		insuranceTitle.setHorizontalAlignment(JLabel.LEFT);
		mainWithTopBar.add(insuranceTitle, titleConstraints);

		// add create prescription button to screen
		GridBagConstraints createConstraints = new GridBagConstraints();

		createConstraints.fill = GridBagConstraints.BOTH;
		createConstraints.gridx = 1;
		createConstraints.gridy = 1;
		createConstraints.gridwidth = 1;
		createConstraints.anchor = GridBagConstraints.SOUTHEAST;
		createNewInsurance = new JButton("Add New");
		createNewInsurance.addActionListener(this);
		createNewInsurance.setFont(genFont);
		createNewInsurance.setHorizontalAlignment(JButton.RIGHT);
		createNewInsurance.setBorder(textBoxBorder);
		createNewInsurance.setMaximumSize(new Dimension((int) (screenDims.width * 0.1), screenDims.height));
		mainWithTopBar.add(createNewInsurance, createConstraints);

		// generate inner panels
		for (int i = 0; i < insurancePanels.length; i++) {
			insurancePanels[i] = new JPanel(new GridLayout(2, 1));
		}

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
			insuranceInfo[i].setText(insuranceCompany[i] + "\n" + insuranceNumber[i] + "\n" + insuranceNotes[i]);
			insurancePanels[i].setBorder(simpleLine);
			insuranceInfo[i].setEditable(false);
			insurancePanels[i].add(insuranceInfo[i]);
			insurancePanels[i].add(editArchive[i]);
		}

		// set height of mainPanel grid
		if ((double) insurancePanels.length / 2 - (int) insurancePanels.length / 2 < 0.5) {
			mainPanel = new JPanel(new GridLayout((int) Math.floor((double) insurancePanels.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		} else {
			mainPanel = new JPanel(new GridLayout((int) Math.ceil((double) insurancePanels.length / 2), 2,
					(int) (screenDims.width * 0.01), (int) (screenDims.height * 0.01)));
		}

		// add inner elements to main panel
		for (int i = 0; i < insurancePanels.length; i++) {
			mainPanel.add(insurancePanels[i]);
		}

		mainPanel.setBorder(textBoxBorder);
		
		JScrollPane mainScroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		GridBagConstraints insuranceConstraints = new GridBagConstraints();

		insuranceConstraints.fill = GridBagConstraints.BOTH;
		insuranceConstraints.gridx = 0;
		insuranceConstraints.gridy = 2;
		insuranceConstraints.gridwidth = 2;
		insuranceConstraints.anchor = GridBagConstraints.NORTH;
		insuranceConstraints.ipady = (int) (screenDims.height * 0.7);
		mainWithTopBar.add(mainScroll, insuranceConstraints);

		add(mainWithTopBar);
	}

	@Override
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
		if (e.getActionCommand().equals("Back")) {
			EditPatientInfoUI openEdit = new EditPatientInfoUI("ManageRx", patient, patients);
			openEdit.setVisible(true);
			setVisible(false);
		}
		if (e.getActionCommand().equals("Add New")) {
			AddNewInsuranceUI openAddNew = new AddNewInsuranceUI("ManageRx", patient, patients);
			openAddNew.setVisible(true);
			setVisible(false);
		}
		for (int i  = 0; i < insurancePanels.length; i ++) {
			if (e.getActionCommand().equals("edit" + i)) {
				insuranceInfo[i].setEditable(true);
				editInsurance[i].setText("Cancel");
				editInsurance[i].setActionCommand("cancel" + i);
				deleteInsurance[i].setText("Save");
				deleteInsurance[i].setActionCommand("save" + i);
				// TODO: add cancel and save buttons when this is pressed, as well as updating insurance info
			}
			if (e.getActionCommand().equals("delete" + i)) {
				patient.removeInsurance(i);
				insurancePanels[i].setVisible(false);
			}
			if (deleteInsurance[i].getText().equals("Save")) {
				if (e.getActionCommand().equals("save" + i)) {
					String insuranceInfoString = insuranceInfo[i].getText().replaceAll("Company: ", "").replaceAll("Insurance Number: ", "").replaceAll("Notes: ", "").trim();
					String[] info = insuranceInfoString.split("\n");
					patient.getInsuranceInformation().get(i).setCompany(info[0]);
					patient.getInsuranceInformation().get(i).setNumber(Integer.parseInt(info[1]));
					
					String notes = "";
					for (int j = 2; j < info.length; j++) {
						notes += info[j] + " ";
					}
					patient.getInsuranceInformation().get(i).setNotes(notes.trim());

					editInsurance[i].setText("Edit");
					editInsurance[i].setActionCommand("edit" + i);
					deleteInsurance[i].setText("Delete");
					deleteInsurance[i].setActionCommand("archive" + i);
				}
				if (e.getActionCommand().equals("cancel" + i)) {
					editInsurance[i].setText("Edit");
					editInsurance[i].setActionCommand("edit" + i);
					deleteInsurance[i].setText("Delete");
					deleteInsurance[i].setActionCommand("archive" + i);
				}
		}

	}
}
}
