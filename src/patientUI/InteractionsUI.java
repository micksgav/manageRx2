package patientUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.SwingConstants;

import inventory.drugFinder;
import utilities.*;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InteractionsUI {

	private JFrame frame;
	static String[] display;

	public boolean showInts(String[] DINs) {
		InteractionsUI UI = new InteractionsUI(DINs);
		
	}
	
	/**
	 * Create the application.
	 */
	public InteractionsUI(String[] DINs) {
		String[][] interactions = getInteractions.arraySearch(DINs);
		display = new String[interactions.length];
		for (int i = 0; i < display.length; i++) {

			display[i] = interactions[i][2];
		}
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 695, 334);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Potential Interactions:");
		lblNewLabel.setBounds(10, 11, 659, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 659, 214);
		frame.getContentPane().add(scrollPane);
		
		JList list = new JList(display);
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Proceed");
		btnNewButton.setBounds(10, 261, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(108, 261, 89, 23);
		frame.getContentPane().add(btnCancel);
		
		frame.setVisible(true);
	}
}
