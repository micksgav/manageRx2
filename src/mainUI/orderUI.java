package mainUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class orderUI extends JFrame implements ActionListener {

	private JPanel orderPanel = new JPanel(new GridBagLayout());
	
	private JLabel bigOrderLabel = new JLabel("Order");
	private JLabel containerSizeLabel = new JLabel("Container Size");
	private JLabel drugToOrderLabel = new JLabel("Drug to Order");
	private JLabel numOfDrugLabel = new JLabel("Drug Qty.");
	private JLabel numOfContainerLabel = new JLabel("Container Qty.");
	private JLabel dosageOfDrugLabel = new JLabel("Dosage");
	
	private JRadioButton orderSmallContainer = new JRadioButton("S");
	private JRadioButton orderMediumContainer = new JRadioButton("M");
	private JRadioButton orderLargeContainer = new JRadioButton("L");
	private ButtonGroup containerSizeGroup = new ButtonGroup();
	
	private JTextField drugToOrder = new JTextField(15);
	private JTextField numOfDrug = new JTextField(15);
	private JTextField numOfContainer = new JTextField(15);
	
	private JComboBox dosageOfDrug = new JComboBox();
	
	private JButton placeOrder = new JButton("Place Order");
	private JButton confirmOrder = new JButton("Confirm Order");
	
	private JTextArea orderSummary;
	
	
	
	public orderUI() {
		/*start*/
		super("Order - ManageRx");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagConstraints gbc = new GridBagConstraints();//start gridbagconstraints
		gbc.insets = new Insets(10, 10, 10, 10);//set insets 
		
		/*content*/
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bigOrderLabel.setFont(new Font("Vardana", Font.PLAIN, 24));
		gbc.anchor = GridBagConstraints.WEST;
		orderPanel.add(bigOrderLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		orderPanel.add(drugToOrderLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		orderPanel.add(drugToOrder, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		orderPanel.add(dosageOfDrugLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		orderPanel.add(dosageOfDrug, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		orderPanel.add(numOfDrugLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		orderPanel.add(numOfDrug, gbc);
		
		containerSizeGroup.add(orderSmallContainer);
		containerSizeGroup.add(orderMediumContainer);
		containerSizeGroup.add(orderLargeContainer);
		
		JPanel containerSizePane = new JPanel(new FlowLayout());
		
		containerSizePane.add(orderSmallContainer);
		containerSizePane.add(orderMediumContainer);
		containerSizePane.add(orderLargeContainer);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		orderPanel.add(containerSizeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		orderPanel.add(containerSizePane, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		orderPanel.add(numOfContainerLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		orderPanel.add(numOfContainer, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.EAST;
		placeOrder.addActionListener(this);
		placeOrder.setActionCommand("placeOrder");
		orderPanel.add(placeOrder, gbc);
		
		add(orderPanel, BorderLayout.CENTER);
		
		
		
		/*end*/
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals("placeOrder")) {
			System.out.println("placed");
			
			JTextArea textArea = new JTextArea(5, 20);
	        textArea.setLineWrap(true);
	        JScrollPane scrollPane = new JScrollPane(textArea);

	        JPanel panel = new JPanel(new BorderLayout());
	        panel.add(new JLabel("Order Summary"), BorderLayout.NORTH);
	        panel.add(scrollPane, BorderLayout.CENTER);
			
			int result = JOptionPane.showOptionDialog(
					null,
	                panel,
	                "Confirmation",
	                JOptionPane.OK_CANCEL_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                new Object[]{"Confirm", "Cancel"},
	                "Cancel"
					);
			System.out.println(result);
			if(result == JOptionPane.OK_OPTION) {
				placeOrder("", "", 1, 2, 3, true);
			}
		}
		
	}
	
    public void placeOrder(String drugToOrder, String containerSize, int drugQty, int containterQty, int dosageIndex, boolean confirmOrder) {
    	System.out.println("order Placed");
    }

}