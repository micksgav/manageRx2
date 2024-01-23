/**
***********************************************
 @Name: altDisplay
 @Author           : Gavin Micks
 @Creation Date    : December 22, 2023
 @Modified Date	   : January 10, 2024
   @Description    : displays drug interactions
   
************
**/
package utilities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class altDisplay extends JDialog {

	private static final long serialVersionUID = 1L; // for dialog
	private final JPanel contentPanel = new JPanel();
	static boolean selection; // user choice to proceed or not
	static String[] display; // interactions to display

	/** Method Name: showInteractions
	* @Author Gavin Micks
	* @Date December 25, 2023
	* @Modified December 30, 2024
	* @Description shows interactions for the given DINs
	* @Parameters  String[] DINS - DINS to check
	* @Returns boolean - user selection to proceed or not
	* Dependencies: getInteractions, jDialog
	* Throws/Exceptions: N/A
    */
	public static boolean showInteractions(String[] DINs) {
		String[][] interactions = getInteractions.arraySearch(DINs); // list of potential interactions
		display = new String[interactions.length];

		// Get description of interaction and store it to display
		for (int i = 0; i < display.length; i++) {
			display[i] = interactions[i][2];
		} // end for

		try {
			altDisplay dialog = new altDisplay(DINs); // creates new instance of the list of interactions
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL); // make other code wait before running
			dialog.setVisible(true);
		} catch (Exception e) {
			// log errors
			logErrors.log(e.getMessage() + "in showInteractions in altDisplay.");
		} // end try-catch
		return selection; // returns boolean to add or cancel prescription
	}// end showInteractions

	public altDisplay(String[] DINs) {
		setBounds(100, 100, 640, 300); // size of dialog
		getContentPane().setLayout(new BorderLayout()); // set to border layout
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane(); // allows for scrolling
		contentPanel.add(scrollPane);
		{
			JList list = new JList(display); // creates list to show of interactions
			scrollPane.setViewportView(list);
		}
		{
			JLabel lblNewLabel = new JLabel("Possible Interactions");
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			scrollPane.setColumnHeaderView(lblNewLabel);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selection = true; // user selected to proceed
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
						selection = false; // user selected to not proceed
						dispose(); // close dialog
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	} //end altDisplay constructor 
} // end altDisplay class
