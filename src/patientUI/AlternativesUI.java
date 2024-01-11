package patientUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import inventory.Drug;
import inventory.drugFinder;
import utilities.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlternativesUI {

	private JFrame frame;
	String[] display;
	String selection;

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public AlternativesUI(String DIN) throws IOException {
		String[][] alternatives = findAlternatives.findAlternative(DIN);
		display = new String[alternatives.length];
		for (int i = 0; i < display.length; i++) {
			display[i] = alternatives[i][0] + " " + alternatives[i][1];
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
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Potential Alternatives:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 659, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 659, 214);
		frame.getContentPane().add(scrollPane);
		
		JList list = new JList(display);
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// button action
				frame.dispose();
			}
		});
		btnNewButton.setBounds(549, 261, 120, 30);
		frame.getContentPane().add(btnNewButton);
		
		list.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent sel) {
                if (!sel.getValueIsAdjusting()) {
                	String temp = list.getSelectedValue().toString();
                  selection = temp.substring(0, temp.indexOf(" "));
                  System.out.println(selection);
                }
            }
        });
		
		frame.setVisible(true);
	}
}
