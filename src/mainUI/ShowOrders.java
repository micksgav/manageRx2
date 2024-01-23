/**
***********************************************
 @Name: ShowOrders
 @Author           : Gavin Micks
 @Creation Date    : january 20, 2024
 @Modified Date	   : January 22, 2024
   @Description    : Dialog to display orders from SQL
   
************
**/
package mainUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import inventory.DrugStockLinkedList;
import utilities.SQLHelper;

import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowOrders extends JDialog {

	private static final long serialVersionUID = 1L; //for jDialog
	private final JPanel contentPanel = new JPanel(); //panel to hold content
	
	public ShowOrders() {
		SQLHelper helper = new SQLHelper(); //to make SQl calls
		String[][] orders = helper.getAllOrders(); //all orders in SQL
		String[] display = new String[orders.length]; //way of displaying orders
		
		//format for displaying in jlist
		for (int i = 0; i < display.length; i++) {
			display[i] = "Drug DIN: " + orders[i][1] + " Qty: " + orders[i][2];
		} //end for
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JList list = new JList(display); //jlist to display
				scrollPane.setViewportView(list);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Close");
				buttonPane.add(cancelButton);
			}
		}
	} //end showOrders constructor
} //end showOrders
