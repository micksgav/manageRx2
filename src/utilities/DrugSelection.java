package utilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DrugSelection extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	String[] display;
	static String[] selection = new String[3];

	/**
	 * Launch the application.
	 */

	public static String[] getDrugSelection(String DIN) {
		try {
			DrugSelection dialog = new DrugSelection(DIN);
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
	 * 
	 * @throws IOException
	 */
	public DrugSelection(String NAME) throws IOException {
		String DIN = SearchForDIN.getDIN(NAME);
		if (DIN == null) {
			JOptionPane.showMessageDialog(null, "Drug Not Found!");
		} else {

			String[][] alternatives = findAlternatives.findAlternative(DIN);
			display = new String[alternatives.length];
			for (int i = 0; i < display.length; i++) {
				display[i] = alternatives[i][0] + " " + alternatives[i][1] + " - " + alternatives[i][2];
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
							selection[0] = temp.substring(0, temp.indexOf(" "));
							selection[1] = temp.substring(temp.indexOf(" ") + 2, temp.indexOf(" - "));
							selection[2] = temp.substring(temp.indexOf(" - ") + 3);
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
							selection[0] = null;
							selection[1] = null;
							selection[2] = null;
							dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}
	}
}
