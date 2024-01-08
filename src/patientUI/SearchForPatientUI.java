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

	public static int multipleIndex(String address, int[] index, PatientList patients) {
		for (int i = 0; i < index.length; i++) {

			if (patients.returnData(index[i]).getAddress().equals(address)) {
				return index[i];
			}
		}
		return -1;
	}

	// convert a month from int to String value
	public static String convertNumToMonth(int month) {
		switch (month) {
		case 1:
			return "january";
		case 2:
			return "february";
		case 3:
			return "march";
		case 4:
			return "april";
		case 5:
			return "may";
		case 6:
			return "june";
		case 7:
			return "july";
		case 8:
			return "august";
		case 9:
			return "september";
		case 10:
			return "october";
		case 11:
			return "november";
		case 12:
			return "december";
		default:
			return null;
		}
	}

	public static int[] searchPatient(PatientList patients, String birthday, String patientNameFieldText) {
		int[] index = patients.findPatientByBirthday(patientNameFieldText,
				Integer.parseInt(birthday.substring(birthday.indexOf(' '), birthday.indexOf(' ') + 3).trim()),
				Integer.parseInt(birthday.substring(0, birthday.indexOf(' ')).trim()),
				Integer.parseInt(birthday.substring(birthday.indexOf(' ') + 3).trim()));

		int newSizeCounter = 0;
		for (int i = 0; i < index.length; i++) {
			if (index[i] != -1) {
				newSizeCounter++;
			} else {
				break;
			}
		}

		if (newSizeCounter > 0) {
			int[] reducedSizeIndex = new int[newSizeCounter];

			for (int i = 0; i < newSizeCounter; i++) {
				reducedSizeIndex[i] = index[i];
			}
			return reducedSizeIndex;
		} else {
			int[] returnMinusOne = { -1 };
			return returnMinusOne;
		}
	}

	private JButton openSettings = new JButton();
	private JButton openPatientManager = new JButton();
	private JButton openStock = new JButton();
	private JButton openOrder = new JButton();
	private loginUI login = new loginUI();
	private settingsUI settings = new settingsUI();
	private patientManagerUI patientManager = new patientManagerUI();
	// private StockUI stock = new StockUI();
	// private OrderUI order = new OrderUI();

	PatientList patients;
	Patient patient;

	// panels
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel mainGrid;
	private JPanel headerButtons;

	// header buttons
	private JButton btnOpenStock;
	private JButton btnOpenOrder;
	private JButton btnOpenSettings;
	private JButton btnOpenPatientManager;

	// main buttons
	private JButton search;
	private JButton backButton;

	// text elements
	private JLabel searchTitle = new JLabel("Search for a Patient");
	private JLabel patientNameLabel = new JLabel("Enter Patient's First and Last Name");
	private JTextField patientNameField;
	private JLabel patientBirthdayLabel = new JLabel("Enter Patient's Birthday");
	private JTextField patientBirthdayField;
	private Insets gridBagPadding;

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public SearchForPatientUI(String title, Patient patient, PatientList patients) {
		FlatLightLaf.setup();
		setTitle(title);
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
		 //screenDims.height /= 1.5;
		setSize(screenDims.width, screenDims.height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.patients = patients;
		this.patient = patient;
		// textFieldPadding = new Insets((int) (screenDims.height *0.15), (int)
		// (screenDims.width *0.02), (int) (screenDims.height *0.1), (int)
		// (screenDims.width *0.02));

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

		Font genFont = new Font("Arial", Font.PLAIN, 25);
		Font nameFont = new Font("Arial", Font.PLAIN, 75);
		Color textBoxFill = new Color(204, 204, 204);
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);
		gridBagPadding = new Insets((int) (screenDims.height * 0.05), 0, (int) (screenDims.height * 0.08), 0);

		mainPanel = new JPanel(new GridBagLayout());

		GridBagConstraints titleConstraints = new GridBagConstraints();

		titleConstraints.gridx = 2;
		titleConstraints.gridy = 1;
		titleConstraints.anchor = GridBagConstraints.NORTH;
		titleConstraints.insets = gridBagPadding;
		searchTitle.setFont(nameFont);
		searchTitle.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(searchTitle, titleConstraints);

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

		GridBagConstraints gridConstraints = new GridBagConstraints();

		gridConstraints.gridx = 2;
		gridConstraints.gridy = 2;
		gridConstraints.anchor = GridBagConstraints.NORTH;
		mainPanel.add(mainGrid, gridConstraints);

		GridBagConstraints searchConstraints = new GridBagConstraints();

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
		if (e.getActionCommand().equals("Back")) {
			SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patient, patients);
			openSearchAdd.setVisible(true);
			setVisible(false);
		}
		if (e.getActionCommand().equals("Search")) {
			int[] index = { -1 };
			if (patientNameField.getText().length() > 0 && !(patientBirthdayField.getText().equals("DD/MM/YYYY"))
					&& patientBirthdayField.getText().length() > 0) {
				String birthday = patientBirthdayField.getText().replace('/', ' ');
				index = searchPatient(patients, birthday, patientNameField.getText().trim());
			}
			if (index[0] >= 0) {
				if (index.length == 1) {
					System.out.println(patients.returnData(index[0]));
					ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx", patients.returnData(index[0]),
							patients);
					openManage.setVisible(true);
					setVisible(false);
				} else {
					String address = JOptionPane.showInputDialog(mainPanel,
							"Two or More Patients With the Specified Name and Birthday Have Been Found. Please Enter Patient's Address to Continue.",
							"Multiple Patients Found", JOptionPane.INFORMATION_MESSAGE);
					int finalIndex = multipleIndex(address, index, patients);
					if (finalIndex >= 0) {
						ManagePatientInfoUI openManage = new ManagePatientInfoUI("ManageRx",
								patients.returnData(finalIndex), patients);
						openManage.setVisible(true);
						setVisible(false);
					} else {
						JOptionPane.showMessageDialog(mainPanel, "Error, Patient Not Found.", "Patient Not Found",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(mainPanel, "Error, Patient Not Found.", "Patient Not Found",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// https://stackoverflow.com/questions/18103839/display-disappear-default-text-in-textfield-when-user-enters-data-erase-the-text
	// used for focus event
	public void focusGained(FocusEvent e) {
		if (patientBirthdayField.getText().trim().equals("DD/MM/YYYY")) {
			patientBirthdayField.setText("");
			patientBirthdayField.setForeground(Color.black);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}
}
