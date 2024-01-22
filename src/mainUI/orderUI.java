/**
 ***********************************************
 * @Author: Brayden Johnson
 * @Creation date: December 29, 2023
 * @Modification date: January 19, 2023
 * @Description: The User interface for ordering drugs, with options to change dosage quantity and container spesifications.
 ***********************************************
 */

package mainUI;

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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;

import PatientManagement.PatientList;
import swingHelper.AppIcon;
import utilities.DrugSelection;
import utilities.SQLHelper;
import inventory.*;
import patientUI.SearchAddUI;


public class orderUI extends JFrame implements ActionListener {

	// app information
	SQLHelper helper = new SQLHelper();
	PatientList patients;
	AllStock stock;
	
	private JPanel orderPanel = new JPanel(new GridBagLayout());

	private JPanel buttonPanel; // header panel
	private JPanel headerButtons; // buttons other than back in header

	private JLabel bigOrderLabel = new JLabel("Order");
	private JLabel containerSizeLabel = new JLabel("Container Size");
	private JLabel drugToOrderLabel = new JLabel("Drug to Order");
	private JLabel numOfDrugLabel = new JLabel("Drug Qty.");
	private JLabel numOfContainerLabel = new JLabel("Container Qty.");
	private JLabel dosageOfDrugLabel = new JLabel("Dosage");

	private JRadioButton orderSmallContainer = new JRadioButton("S");
	private JRadioButton orderMediumContainer = new JRadioButton("M");
	private JRadioButton orderLargeContainer = new JRadioButton("L");
	private ButtonGroup containerSizeGroup = new ButtonGroup();

	private JTextField drugToOrder = new JTextField(15);
	private JTextField numOfDrug = new JTextField(15);
	private JTextField numOfContainer = new JTextField(15);

	private JTextField dosageOfDrug = new JTextField(6);

	private JButton placeOrder = new JButton("Place Order");
	private JButton confirmOrder = new JButton("Confirm Order");
	private JButton backButton;

	// header buttons
	private JButton btnOpenStock; // open stock
	private JButton btnOpenOrder; // open order
	private JButton btnOpenSettings; // open settings
	private JButton btnOpenPatientManager; // open patient manager

	private JTextArea orderSummary;

	// icons
	public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
	public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
	public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
	public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients

	public orderUI(String title, PatientList patients, AllStock stock) {
		// setup screen attributes
		FlatLightLaf.setup();
		setTitle("ManageRx");
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
		// screenDims.height /= 1.5;
		this.setPreferredSize(new Dimension(screenDims.width, screenDims.height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// instantiate app variables
		this.patients = patients;
		this.stock = stock;

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
		btnOpenStock.setActionCommand("openStock");//action command
		btnOpenStock.addActionListener(this);

		btnOpenOrder = new JButton("Order");
		btnOpenOrder.setIcon(orderIcon);
		btnOpenOrder.setActionCommand("openOrder");//action command
		btnOpenOrder.addActionListener(this);

		btnOpenSettings = new JButton("Settings");
		btnOpenSettings.setIcon(settingsIcon);
		btnOpenSettings.setActionCommand("openSettings");//action command
		btnOpenSettings.addActionListener(this);

		btnOpenPatientManager = new JButton("Patients");
		btnOpenPatientManager.setIcon(patientsIcon);
		btnOpenPatientManager.setActionCommand("openPatientManager");//action command
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
		// headerButtons.add(btnOpenSettings);
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

		GridBagConstraints gbc = new GridBagConstraints();// start gridbagconstraints
		gbc.insets = new Insets(25, 25, 25, 25);// set insets
		
		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // font for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);

		/* content */

		//add the order label with constraints and labels
		gbc.gridx = 0;
		gbc.gridy = 0;
		bigOrderLabel.setFont(nameFont);
		gbc.anchor = GridBagConstraints.WEST;
		orderPanel.add(bigOrderLabel, gbc);

		//constraints and styles for drug to order label
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		drugToOrderLabel.setFont(genFont);
		orderPanel.add(drugToOrderLabel, gbc);
    
		//constraints and styles for the drug to order
		JPanel orderFieldAndSearch = new JPanel(new GridLayout(2, 1));
		orderFieldAndSearch.add(drugToOrder);
		
		JButton search = new JButton("Search");
		search.addActionListener(this);
		search.setBorder(textBoxBorder);
		search.setFont(genFont);
		orderFieldAndSearch.add(search);
    
		gbc.gridx = 0;
		gbc.gridy = 2;
		drugToOrder.setBorder(textBoxBorder);
		drugToOrder.setFont(genFont);
		orderPanel.add(orderFieldAndSearch, gbc);

		//constraints and styles for drug dosage label
		gbc.gridx = 0;
		gbc.gridy = 3;
		dosageOfDrugLabel.setFont(genFont);
		orderPanel.add(dosageOfDrugLabel, gbc);

		//constraints and styles for dosage of selected drug
		gbc.gridx = 0;
		gbc.gridy = 4;
		dosageOfDrug.setBorder(textBoxBorder);
		dosageOfDrug.setFont(genFont);
		orderPanel.add(dosageOfDrug, gbc);

		//constraints and styles for quantity of drug label 
		gbc.gridx = 0;
		gbc.gridy = 5;
		numOfDrugLabel.setFont(genFont);
		orderPanel.add(numOfDrugLabel, gbc);

		//constraints and styles for quantity of drugs 
		gbc.gridx = 0;
		gbc.gridy = 6;
		numOfDrug.setBorder(textBoxBorder);
		numOfDrug.setFont(genFont);
		orderPanel.add(numOfDrug, gbc);
		
		//add radio buttons to button group
		containerSizeGroup.add(orderSmallContainer);
		containerSizeGroup.add(orderMediumContainer);
		containerSizeGroup.add(orderLargeContainer);
		
		JPanel containerSizePane = new JPanel(new FlowLayout());//initialize pane for container size buttons

		//add padding to radio buttons
		orderSmallContainer.setBorder(textFieldPadding);
		orderMediumContainer.setBorder(textFieldPadding);
		orderLargeContainer.setBorder(textFieldPadding);
		
		//add radio buttons to container panel
		containerSizePane.add(orderSmallContainer);
		containerSizePane.add(orderMediumContainer);
		containerSizePane.add(orderLargeContainer);

		//constraints and styles for container size label
		gbc.gridx = 1;
		gbc.gridy = 1;
		containerSizeLabel.setFont(genFont);
		orderPanel.add(containerSizeLabel, gbc);

		//constraints and styles for panel for container size buttons 
		gbc.gridx = 1;
		gbc.gridy = 2;
		containerSizePane.setBorder(textBoxBorderLine);
		containerSizePane.setFont(genFont);
		orderPanel.add(containerSizePane, gbc);

		//constraints and styles for container qty. label
		gbc.gridx = 1;
		gbc.gridy = 3;
		numOfContainerLabel.setFont(genFont);
		orderPanel.add(numOfContainerLabel, gbc);

		//constraints and styles for quantities of containers
		gbc.gridx = 1;
		gbc.gridy = 4;
		numOfContainer.setBorder(textBoxBorder);
		numOfContainer.setFont(genFont);
		orderPanel.add(numOfContainer, gbc);

		
		//constraints and styles for place order button
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.EAST;
		placeOrder.addActionListener(this);
		placeOrder.setActionCommand("placeOrder");//action command
		placeOrder.setBorder(textBoxBorder);
		placeOrder.setFont(genFont);
		orderPanel.add(placeOrder, gbc);

		add(orderPanel, BorderLayout.CENTER);

		/* end */
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		//if place order is pressed show a option pane for confirmation

		// search button
				if (e.getActionCommand().equals("Search")) {
					String[] selection = DrugSelection.getDrugSelection(drugToOrder.getText());
					drugToOrder.setText(selection[1]);
					dosageOfDrug.setText(selection[2]);
				}
		 if (e.getActionCommand().equals("openStock")) {
	            stockUI openStock = new stockUI("ManageRx", patients, stock);
	            openStock.setVisible(true);
	            setVisible(false);
	        }
	        if (e.getActionCommand().equals("openOrder")) {
	        	orderUI openOrder = new orderUI("ManageRx", patients, stock);
	        	openOrder.setVisible(true);
	        	setVisible(false);
	        }
	        if (e.getActionCommand().equals("openPatientManager")) {
	        	SearchAddUI openSearchAdd = new SearchAddUI("ManageRx", patients, stock);
	        	openSearchAdd.setVisible(true);
	        	setVisible(false);
	        }

		if (e.getActionCommand().equals("placeOrder")) {

			JTextArea textArea = new JTextArea(5, 20);
			textArea.setLineWrap(true);
			JScrollPane scrollPane = new JScrollPane(textArea);

			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel("Order Summary"), BorderLayout.NORTH);
			panel.add(scrollPane, BorderLayout.CENTER);

			int result = JOptionPane.showOptionDialog(null, panel, "Confirmation", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Confirm", "Cancel" }, "Cancel");
			if (result == JOptionPane.OK_OPTION) {
				
				placeOrder(drugToOrder.getText(), "m", Integer.parseInt(numOfDrug.getText()), Integer.parseInt(numOfContainer.getText()), dosageOfDrug.getText()); //fix
			}
		}
		

	}

	public void placeOrder(String drugToOrder, String containerSize, int drugQty, int containerQty, String dosage) {
		helper.addOrder(drugToOrder, drugQty, containerSize, dosage, containerQty);
	}

}