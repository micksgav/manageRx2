/**
***********************************************
 @Name: drugSelection
 @Author           : Gavin Micks
 @Creation Date    : December 30, 2023
 @Modified Date	   : January 20, 2024
   @Description    : menu to select a drug
   
************
**/
package utilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
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

	static final String sep = System.getProperty("file.separator"); // "/" Equivalent for OS

	private static final long serialVersionUID = 1L; // for dialog
	private final JPanel contentPanel = new JPanel(); // panel containing selection dialog
	String[] display; // drugs to display
	static String[] selection = new String[3]; // selected drug

	/** Method Name: getDrugSelection
	* @Author Gavin Micks
	* @Date January 05, 2024
	* @Modified January 20, 2024
	* @Description displays a window to select a drug from a name searched
	* @Parameters  String NAME - name to search
	* @Returns String[] - selected drug
	* Dependencies: jdialog, logErrors
	* Throws/Exceptions: N/A
    */
	public static String[] getDrugSelection(String NAME) {
		try {
			DrugSelection dialog = new DrugSelection(NAME); // dialog to show
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL); // makes other code wait until this is completed
			dialog.setVisible(true);

		} catch (Exception e) {
			logErrors.log(e.getMessage() + " in getDrugSelection in DrugSelection");
		} // end try-catch

		return selection; // return selected drug
	} // end getDrugSelection

	public DrugSelection(String NAME) {
		String DIN = getDIN(NAME); // DIN from name of drug

		// error handling for DIN not found
		if (DIN == null) {
			JOptionPane.showMessageDialog(contentPanel, "Drug Not Found!"); // show pop-up dialog
			dispose(); // close dialog
		} else { // end if
			String[][] alternatives = findAlternatives.findAlternative(DIN); // alternatives (other found drugs and
																				// generics)
			display = new String[alternatives.length]; // drugs to display to the user

			// Combine name, DIN, and dosage to display
			for (int i = 0; i < display.length; i++) {
				display[i] = alternatives[i][0] + " " + alternatives[i][1] + " - " + alternatives[i][2];
			} // end for

			setBounds(100, 100, 450, 300); // set size
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(null);

			JScrollPane scrollPane = new JScrollPane(); // scroll pane to house list
			scrollPane.setBounds(10, 11, 414, 206);
			contentPanel.add(scrollPane);

			JList list = new JList(display); // visible list of drugs and info
			scrollPane.setViewportView(list);
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String temp = list.getSelectedValue().toString(); // get the selected drug
							// parse selected drug
							selection[0] = temp.substring(0, temp.indexOf(" "));
							selection[1] = temp.substring(temp.indexOf(" ") + 2, temp.indexOf(" - "));
							selection[2] = temp.substring(temp.indexOf(" - ") + 3);
							dispose(); // close dialog
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
							// no selection
							selection[0] = null;
							selection[1] = null;
							selection[2] = null;
							dispose(); // close dialog
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		} // end else
	} // end DrugSelection constructor

	/** Method Name: getDIN
	* @Author Gavin Micks
	* @Date December 29, 2023
	* @Modified December 30, 2023
	* @Description gets DIN from a name of drug
	* @Parameters  String drugName - name of drug
	* @Returns String - DIn found
	* Dependencies: BufferedReader, logErrors
	* Throws/Exceptions: N/A
    */
	public static String getDIN(String drugName) {

		try {
			// reader for drug data
			BufferedReader br = new BufferedReader(new FileReader("data" + sep + "drugs" + sep + "drugData.txt"));
			String line; // line read

			// while there are lines to read
			while ((line = br.readLine()) != null) {
				br.readLine();
				// return DIN for name
				if (line.substring(line.indexOf(" ")).toLowerCase().contains(drugName.toLowerCase()))
					return (line.substring(0, line.indexOf(" ")));
			} // end while
		} catch (Exception e) {
			logErrors.log(e.getMessage() + " from getDIN in DrugSelection");
		}
		return null; // if no DIN found
	} // end getDIN
} // end Drug Selection class
