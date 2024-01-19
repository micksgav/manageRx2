package patientUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utilities.findAlternatives;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectAlternativeUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	String[] display;
	static String selection;

	/**
	 * Launch the application.
	 */
	
	public static String getAltSelection(String DIN) {
		try {
			SelectAlternativeUI dialog = new SelectAlternativeUI(DIN);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selection;
	}

	/**
	 * Create the dialog.
	 */
	public SelectAlternativeUI(String DIN) {
		String[][] alternatives = findAlternatives.findAlternative(DIN);
		display = new String[alternatives.length];
		for (int i = 0; i < display.length; i++) {
			display[i] = alternatives[i][0] + " " + alternatives[i][1];
		}
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 206);
		contentPanel.add(scrollPane);
		
		JList list = new JList(display);
		scrollPane.setViewportView(list);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String temp = list.getSelectedValue().toString();
						selection = temp.substring(0, temp.indexOf(" "));
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selection = null;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
