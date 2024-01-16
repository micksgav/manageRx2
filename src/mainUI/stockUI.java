package mainUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import inventory.*;
import utilities.logErrors;

public class stockUI extends JFrame implements ActionListener {

	private JPanel stockPanel = new JPanel(new GridBagLayout());
	
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
	
	AllStock stock;
	
	public stockUI(AllStock newStock) {
		/*start*/
		super("Stock - ManageRx");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints gbc = new GridBagConstraints();//start gridbagconstraints
		gbc.insets = new Insets(10, 10, 10, 10);//set insets 
		
		this.stock = newStock;
		
		/*content*/
		
		//get drug stock
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		stockPanel.add(drugStockLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		stockPanel.add(viewStockDrugField, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		viewStockButton.addActionListener(this);
		viewStockButton.setActionCommand("viewStock");
		stockPanel.add(viewStockButton, gbc);
		
		//set drug threshold
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		stockPanel.add(drugThresholdLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		stockPanel.add(thresholdNumLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		stockPanel.add(setThresholdDrug, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		stockPanel.add(setThresholdNum, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 3;
		setThresholdButton.addActionListener(this);
		setThresholdButton.setActionCommand("setThreshold");
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
			viewStock(viewStockDrugField.getText());
		}
		if(e.getActionCommand().equals("viewInventory")) {
			try {
				viewInventory();
			} catch (IOException e1) {
				logErrors.log("IOException " + String.valueOf(e1));
			}
		}
	}
	
	private void viewStock(String drug) {
		System.out.println("View Stock: " + drug);
		stock.viewUsage(drug);
	}

	private void setThreshold(String drug, int threshold) {
		System.out.println("Set Threshold: " + drug + threshold);
		stock.changeThreshold(drug, threshold);
	}

	private void viewIncoming() {
		System.out.println("View Incoming: ");
	}
	
	private void viewInventory() throws IOException {
		stock.viewFullInventory();
		inventoryUI inventory = new inventoryUI(stock);
	}
}