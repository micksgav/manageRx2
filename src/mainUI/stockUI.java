package mainUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;

import swingHelper.AppIcon;

import inventory.*;
import utilities.logErrors;

public class stockUI extends JFrame implements ActionListener {

	private JPanel stockPanel = new JPanel(new GridBagLayout());
	private JPanel buttonPanel; // header panel
	private JPanel headerButtons; // buttons other than back in header
	
	private JPanel checkStockPane = new JPanel(new GridBagLayout());//view stock components
	private JPanel setThresholdPane = new JPanel(new GridBagLayout());//set threshold components
	private JPanel buttonOrganizerPane = new JPanel(new GridBagLayout());//panel to hold 2 buttons
	
	private JLabel drugStockLabel = new JLabel("Check Stock");//drug for threshold 
	private JLabel drugSetThresholdLabel = new JLabel("Set Threshold");//drug for threshold 
	private JLabel drugThresholdLabel = new JLabel("Drug");//drug for threshold 
	private JLabel drugNameStockLabel = new JLabel("Drug");//drug for threshold 
	private JLabel thresholdNumLabel = new JLabel("Threshold");//threshold for drug
	
	private JTextField viewStockDrugField = new JTextField(15);
	private JTextField setThresholdDrug = new JTextField(15);
	private JTextField setThresholdNum = new JTextField(15);
	
	private JButton viewStockButton = new JButton("View Stock");
	private JButton setThresholdButton = new JButton("Set Threshold");
	private JButton incomingShipmentsButton = new JButton("Incoming Shipments");
	private JButton viewInventoryButton = new JButton("View All Inventory");

	private JButton backButton;
	
	// header buttons
		private JButton btnOpenStock; // open stock
		private JButton btnOpenOrder; // open order
		private JButton btnOpenSettings; // open settings
		private JButton btnOpenPatientManager; // open patient manager
	
	// icons
		public AppIcon stockIcon = new AppIcon("icons/box.png");// icon for stock
		public AppIcon orderIcon = new AppIcon("icons/clipboard.png");// icon for order
		public AppIcon settingsIcon = new AppIcon("icons/gear.png");// icon for settings
		public AppIcon patientsIcon = new AppIcon("icons/person.png");// icon for patients
  
  AllStock stock;
	
	public stockUI(AllStock newStock) {
    this.stock = newStock;
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


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(25, 25, 25, 25);


		
		GridBagConstraints gbc = new GridBagConstraints();
		/*content*/
		
		//get drug stock
		checkStockPane.setBorder(textBoxBorder);
		GridBagConstraints stockgbc = new GridBagConstraints();
		stockgbc.insets = new Insets(25, 25, 25, 25);		
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;

		drugStockLabel.setFont(genFont);
		stockPanel.add(drugStockLabel, gbc);
		
		
		
		
		
		
		stockgbc.gridx = 0;
		stockgbc.gridy = 0;
		drugNameStockLabel.setFont(genFont);
		checkStockPane.add(drugNameStockLabel, stockgbc);
		
		stockgbc.gridx = 0;
		stockgbc.gridy = 1;
		stockgbc.gridwidth = 3;
		viewStockDrugField.setBorder(textBoxBorder);
		viewStockDrugField.setFont(genFont);
		checkStockPane.add(viewStockDrugField, stockgbc);

		
		stockgbc.gridx = 3;
		stockgbc.gridy = 1;
		viewStockButton.addActionListener(this);
		viewStockButton.setActionCommand("viewStock");

		viewStockButton.setBorder(textBoxBorder);
		viewStockButton.setFont(genFont);
		checkStockPane.add(viewStockButton, stockgbc);

	
		//add checkStock to UI
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 2;
		gbc.gridwidth = 4;
		stockPanel.add(checkStockPane, gbc);

		//set drug threshold
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;

		drugSetThresholdLabel.setFont(genFont);
		stockPanel.add(drugSetThresholdLabel, gbc);

		setThresholdPane.setBorder(textBoxBorder);
		GridBagConstraints thresholdgbc = new GridBagConstraints();
		thresholdgbc.insets = new Insets(25, 25, 25, 25);
		
		thresholdgbc.gridx = 0;
		thresholdgbc.gridy = 0;
		drugThresholdLabel.setFont(genFont);
		setThresholdPane.add(drugThresholdLabel, thresholdgbc);
		
		thresholdgbc.gridx = 3;
		thresholdgbc.gridy = 0;
		thresholdNumLabel.setFont(genFont);
		setThresholdPane.add(thresholdNumLabel, thresholdgbc);
		
		thresholdgbc.gridx = 0;
		thresholdgbc.gridy = 1;
		thresholdgbc.gridwidth = 3;
		setThresholdDrug.setBorder(textBoxBorder);
		setThresholdDrug.setFont(genFont);
		setThresholdPane.add(setThresholdDrug, thresholdgbc);
		
		thresholdgbc.gridx = 3;
		thresholdgbc.gridy = 1;
		thresholdgbc.gridwidth = 2;
		setThresholdNum.setBorder(textBoxBorder);
		setThresholdNum.setFont(genFont);
		setThresholdPane.add(setThresholdNum, thresholdgbc);

		
		thresholdgbc.gridx = 5;
		thresholdgbc.gridy = 1;
		setThresholdButton.addActionListener(this);
		setThresholdButton.setActionCommand("setThreshold");

		setThresholdButton.setBorder(textBoxBorder);
		setThresholdButton.setFont(genFont);
		setThresholdPane.add(setThresholdButton, thresholdgbc);
		
		//add setThreshold to UI
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridheight = 2;
		gbc.gridwidth = 7;
		stockPanel.add(setThresholdPane, gbc);

		GridBagConstraints buttonOrganizergbc = new GridBagConstraints();
		buttonOrganizergbc.insets = new Insets(25, 25, 25, 25);
		buttonOrganizerPane.setBorder(textBoxBorderLine);

		// new
		buttonOrganizergbc.gridx = 0;
		buttonOrganizergbc.gridy = 0;
		viewInventoryButton.addActionListener(this);
		viewInventoryButton.setActionCommand("viewInventory");
		viewInventoryButton.setBorder(textBoxBorder);
		viewInventoryButton.setFont(genFont);
		buttonOrganizerPane.add(viewInventoryButton, buttonOrganizergbc);
		
		
		//view incoming drugs
		buttonOrganizergbc.gridx = 0;
		buttonOrganizergbc.gridy = 1;
		incomingShipmentsButton.addActionListener(this);
		incomingShipmentsButton.setActionCommand("viewIncoming");
		incomingShipmentsButton.setBorder(textBoxBorder);
		incomingShipmentsButton.setFont(genFont);
		buttonOrganizerPane.add(incomingShipmentsButton, buttonOrganizergbc);
		
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.gridheight = 2;
		stockPanel.add(buttonOrganizerPane, gbc);
		
		add(stockPanel);
		
		/*end*/
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("viewIncoming")) {
			viewIncoming();
		}
		if(e.getActionCommand().equals("setThreshold")) {
			setThreshold(setThresholdDrug.getText(), Integer.parseInt(setThresholdNum.getText()));
		}
		if(e.getActionCommand().equals("viewStock")) {
			try {
				viewStock(viewStockDrugField.getText());
			} catch (IOException e1) {
				logErrors.log("View stock IOException " + String.valueOf(e1));
			}
		}
		if(e.getActionCommand().equals("viewInventory")) {
			try {
				viewInventory();
			} catch (IOException e1) {
				logErrors.log("View inventory IOException " + String.valueOf(e1));
			}
		}
	}
	
	private void viewStock(String drug) throws IOException {
		System.out.println("View Stock: " + drug);
		boolean drugFound = stock.viewUsage(drug);
		if(drugFound == false) {
			JOptionPane.showMessageDialog(stockPanel, "Drug not found in inventory.","ERROR", JOptionPane.WARNING_MESSAGE); // frame is the name of the frame	
		}
		else {
			DrugStockUI viewStock = new DrugStockUI(stock, drug);
		}
		int checkThreshold = stock.thresholdCheck(drug);
		if(checkThreshold == 1) {
			JOptionPane.showMessageDialog(stockPanel, "Stock is below threshold.", "Threshold Alert", JOptionPane.ERROR_MESSAGE); // frame is the name of the frame
		}
		else if(checkThreshold == 2) {
			JOptionPane.showMessageDialog(stockPanel, "Stock is at threshold.", "Threshold Warning", JOptionPane.WARNING_MESSAGE); // frame is the name of the frame
		}

	}

	private void setThreshold(String drug, int threshold) {
		System.out.println("Set Threshold: " + drug + threshold);
		stock.changeThreshold(drug, threshold);
	}

	private void viewIncoming() {
		System.out.println("View Incoming: ");
	}
	
	private void viewInventory() throws IOException {
		//stock.viewFullInventory();
		inventoryUI inventory = new inventoryUI(stock);
	}
}