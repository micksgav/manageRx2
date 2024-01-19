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

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class inventoryUI{

	private JFrame frame;
	String[] display;
	String selection;
	
	private JLabel nameLabel = new JLabel("\tDrug Name:");
	private JLabel DINLabel = new JLabel("\t\tDIN:");
	private JLabel stockLabel = new JLabel("Current Stock:");
	private static JTextArea drugName = new JTextArea();
	private static JTextArea drugDIN = new JTextArea();
	private static JTextArea drugStock = new JTextArea();
	 
	private AllStock stock;

	public inventoryUI(AllStock fullStock) throws IOException {
		stock = fullStock;
		String[][] fullInventory = stock.viewFullInventory();
		drugName.setText("");
		drugDIN.setText("");
		drugStock.setText("");
		drugName.setEditable(false);
		drugDIN.setEditable(false);
		drugStock.setEditable(false);
        drugName.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        drugDIN.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        drugStock.setFont(new Font("Sans Serif", Font.PLAIN, 16));


		for(int i = 0; i < fullInventory.length; i++) {
			drugName.append(fullInventory[i][0] + "\n");
			drugDIN.append(fullInventory[i][1] + "\n");
			drugStock.append(fullInventory[i][2] + "\n");
		}
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 695, 341);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		
		JLabel lblNewLabel = new JLabel("Full Inventory:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		frame.add(lblNewLabel, BorderLayout.NORTH);
		
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
		frame.add(panel, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// button action
				frame.dispose();
			}
		});
		btnNewButton.setBounds(549, 261, 120, 30);
		frame.add(btnNewButton, BorderLayout.SOUTH);

		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
	}
}
