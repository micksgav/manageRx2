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

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	static boolean selection;
	static String[] display;

	/**
	 * Launch the application.
	 */
	public static boolean showInteractions(String[] DINs) {
		String[][] interactions = getInteractions.arraySearch(DINs);
		display = new String[interactions.length];
		for (int i = 0; i < display.length; i++) {
			display[i] = interactions[i][2];
		}
		try {
			altDisplay dialog = new altDisplay(DINs);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		boolean temp = selection;
		selection = false;
		return temp;
	}

	/**
	 * Create the dialog.
	 */
	public altDisplay(String[] DINs) {
		setBounds(100, 100, 640, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				JList list = new JList(display);
				scrollPane.setViewportView(list);
			}
			{
				JLabel lblNewLabel = new JLabel("Possible Interactions");
				lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				scrollPane.setColumnHeaderView(lblNewLabel);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selection = true;
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
						selection = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
