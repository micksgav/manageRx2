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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;


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
	
	private JLabel drugStockLabel = new JLabel("Check Stock");//drug for threshold 
	private JLabel drugThresholdLabel = new JLabel("Drug");//drug for threshold 
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
		

		Font genFont = new Font("Arial", Font.PLAIN, 25); // general font for most text
		Font nameFont = new Font("Arial", Font.PLAIN, 35); // font for names and titles
		Border textBoxBorderLine = BorderFactory.createLineBorder(new Color(89, 89, 89), screenDims.width / 700); // https://docs.oracle.com/javase%2Ftutorial%2Fuiswing%2F%2F/components/border.html#:~:text=To%20put%20a%20border%20around,a%20variable%20of%20type%20Border%20.
		Border textFieldPadding = new EmptyBorder((int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01),
				(int) (screenDims.height * 0.01), (int) (screenDims.width * 0.01));
		CompoundBorder textBoxBorder = new CompoundBorder(textBoxBorderLine, textFieldPadding);

		
		/*content*/
		
		//get drug stock
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		drugStockLabel.setFont(genFont);
		stockPanel.add(drugStockLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		viewStockDrugField.setBorder(textBoxBorder);
		viewStockDrugField.setFont(genFont);
		stockPanel.add(viewStockDrugField, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		viewStockButton.addActionListener(this);
		viewStockButton.setActionCommand("viewStock");
		viewStockButton.setBorder(textBoxBorder);
		viewStockButton.setFont(genFont);
		stockPanel.add(viewStockButton, gbc);
		
		//set drug threshold
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		drugThresholdLabel.setFont(genFont);
		stockPanel.add(drugThresholdLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		thresholdNumLabel.setFont(genFont);
		stockPanel.add(thresholdNumLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		setThresholdDrug.setBorder(textBoxBorder);
		setThresholdDrug.setFont(genFont);
		stockPanel.add(setThresholdDrug, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		setThresholdNum.setBorder(textBoxBorder);
		setThresholdNum.setFont(genFont);
		stockPanel.add(setThresholdNum, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 3;
		setThresholdButton.addActionListener(this);
		setThresholdButton.setActionCommand("setThreshold");
		setThresholdButton.setBorder(textBoxBorder);
		setThresholdButton.setFont(genFont);
		stockPanel.add(setThresholdButton, gbc);
		
		// new
		gbc.gridx = 3;
		gbc.gridy = 0;
		viewInventoryButton.addActionListener(this);
		viewInventoryButton.setActionCommand("viewInventory");
		stockPanel.add(viewInventoryButton, gbc);
		
		
		//view incoming drugs
		gbc.gridx = 5;
		gbc.gridy = 0;
		incomingShipmentsButton.addActionListener(this);
		incomingShipmentsButton.setActionCommand("viewIncoming");
		stockPanel.add(incomingShipmentsButton, gbc);
		
		
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