/**
***********************************************
 @Name: DrugStockUI
 @Author           : Christina Wong
 @Creation Date    : January 16, 2024
 @Modified Date	   : January 19, 2024
   @Description    : This displays information for a specific drug stocked in the inventory.
   
***********************************************
*/
package inventory;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import PatientManagement.PatientList;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DrugStockUI {

	private JFrame frame;
	
	private JLabel dateLabel = new JLabel("Date:");
	private JLabel changeLabel = new JLabel("Inventory Change:");
	private JLabel amountLabel = new JLabel("Amount:");
	private JLabel curLabel = new JLabel("Current Stock:");
	private static JTextArea changeDate = new JTextArea();
	private static JTextArea change = new JTextArea();
	private static JTextArea stockAmt = new JTextArea();
	private static JTextArea currentStock = new JTextArea();
	 
	private AllStock stock; // inventory
	private String[][] stockInventory; // drug's inventory information

	public DrugStockUI(String title, PatientList patients, AllStock fullStock, String DIN) throws IOException {
		stock = fullStock;
		stock.viewUsage(DIN);
		stockInventory = stock.viewDrugInventory(DIN);
		
		// fields for inventory information
		changeDate.setText("");
		change.setText("");
		stockAmt.setText("");
		currentStock.setText("");
		changeDate.setEditable(false);
		change.setEditable(false);
		stockAmt.setEditable(false);
		currentStock.setEditable(false);
        changeDate.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        change.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        stockAmt.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        currentStock.setFont(new Font("Sans Serif", Font.PLAIN, 16));

        // adds all information to fields
		for(int i = 1; i < stockInventory.length; i++) {
			if(stockInventory[i][0].length() != 0) {
				changeDate.append(stockInventory[i][0] + "\n");
				change.append(stockInventory[i][1] + "\n");
				stockAmt.append(stockInventory[i][2] + "\n");
				currentStock.append(stockInventory[i][3] + "\n");
			} // end if
		} // end for
		
		initialize();
		
	} // end DrugStockUI constructor

	/** Method Name: initialize
	* @Author Christina Wong 
	* @Date January 16, 2024
	* @Modified January 19, 2024
	* @Description This creates the interface for the drug inventory information.
	* @Parameters N/A
	* @Returns void
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 695, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		JLabel lblNewLabel = new JLabel("Inventory for " + stockInventory[0][0] + " (" + stockInventory[0][1] + ")");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		frame.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 4, 20, 30));
		
		dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
		changeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		changeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
		amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
		curLabel.setHorizontalAlignment(SwingConstants.CENTER);
		curLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));

		panel.add(dateLabel);
		panel.add(changeLabel);
		panel.add(amountLabel);
		panel.add(curLabel);
		
		JScrollPane scroll1 = new JScrollPane(changeDate);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll1);
		
		JScrollPane scroll2 = new JScrollPane(change);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll2);
		
		JScrollPane scroll3 = new JScrollPane(stockAmt);
        scroll3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll3);
		
		JScrollPane scroll4 = new JScrollPane(currentStock);
        scroll4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll4);
		
		frame.add(panel, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Close");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// button action
				frame.dispose();
			} // end actionPerformed
		});
		
		btnNewButton.setBounds(549, 261, 120, 30);
		frame.add(btnNewButton, BorderLayout.SOUTH);

		frame.setVisible(true);
	} // end initialize
	
} // end DrugStockUI