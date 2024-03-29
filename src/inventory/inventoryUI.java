/**
***********************************************
 @Name: inventoryUI
 @Author           : Christina Wong
 @Creation Date    : January 16, 2024
 @Modified Date	   : January 19, 2024
   @Description    : This displays information for all existing drugs currently in the inventory.
   
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
import utilities.Report;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class inventoryUI{

	private JFrame frame;
	
	private JLabel nameLabel = new JLabel("\tDrug Name:");
	private JLabel DINLabel = new JLabel("\t\tDIN:");
	private JLabel stockLabel = new JLabel("Current Stock:");
	private static JTextArea drugName = new JTextArea();
	private static JTextArea drugDIN = new JTextArea();
	private static JTextArea drugStock = new JTextArea();
	 
	private AllStock stock;

	public inventoryUI(String title, PatientList patients, AllStock fullStock) throws IOException {
		stock = fullStock;
		String[][] fullInventory = stock.viewFullInventory();
		
		// fields for inventory information
		drugName.setText("");
		drugDIN.setText("");
		drugStock.setText("");
		drugName.setEditable(false);
		drugDIN.setEditable(false);
		drugStock.setEditable(false);
        drugName.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        drugDIN.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        drugStock.setFont(new Font("Sans Serif", Font.PLAIN, 16));

        // adds all information to all fields
		for(int i = 0; i < fullInventory.length; i++) {
			drugName.append(fullInventory[i][0] + "\n");
			drugDIN.append(fullInventory[i][1] + "\n");
			drugStock.append(fullInventory[i][2] + "\n");
		} // end for
		
		initialize();	
	} // end inventoryUI constructor

	/** Method Name: initialize
	* @Author Christina Wong 
	* @Date January 16, 2024
	* @Modified January 19, 2024
	* @Description This creates the interface for the inventory information.
	* @Parameters N/A
	* @Returns void
	* Dependencies: N/A
	* Throws/Exceptions: N/A
    */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 695, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());

		JLabel lblNewLabel = new JLabel("Full Inventory:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3, 20, 30));
		
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
		DINLabel.setHorizontalAlignment(SwingConstants.CENTER);
		DINLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));
		stockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		stockLabel.setFont(new Font("Sans Serif", Font.PLAIN, 18));

		panel.add(nameLabel);
		panel.add(DINLabel);
		panel.add(stockLabel);
		
		JScrollPane scroll1 = new JScrollPane(drugName);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll1);
		
		JScrollPane scroll2 = new JScrollPane(drugDIN);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll2);
		
		JScrollPane scroll3 = new JScrollPane(drugStock);
        scroll3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		panel.add(scroll3);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Close");
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// button action
				frame.dispose();
			} // end actionPerformed
		});
		btnNewButton.setBounds(549, 261, 120, 30);
		
		JButton stockPrint = new JButton("Print Stock List");
		stockPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Report.stockReport(); // where is linked list?
			}
		});
		panel_1.add(stockPrint);

		frame.setVisible(true);
	} // end initialize

} // end inventoryUI