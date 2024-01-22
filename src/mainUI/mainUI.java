/**
 ***********************************************
 * @Author: Brayden Johnson
 * @Creation date: December 29, 2023
 * @Modification date: January 17, 2023
 * @Description: This User interface is for navigating between the main sections of the application at ease
 ***********************************************
 */

package mainUI;
import patientUI.*;
import swingHelper.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainUI extends JFrame implements ActionListener {

	//panels
	private JPanel headerPanel = new JPanel(new FlowLayout());
	private JPanel bPanel = new JPanel(new GridBagLayout());
	
	//buttons
    private JButton btnOpenStock;//button to open stock ui
    private JButton btnOpenOrder;//button to open order ui
    private JButton btnOpenPatientManager;//button to open patient manager ui
    private JButton backButton; // go back to previous page
    
    //icons
    public AppIcon stockIcon = new AppIcon("icons/box.png");//icon for stock
    public AppIcon orderIcon = new AppIcon("icons/clipboard.png");//icon for order
    public AppIcon settingsIcon = new AppIcon("icons/gear.png");//icon for settings
    public AppIcon patientsIcon = new AppIcon("icons/person.png");//icon for patients
    
    
    public mainUI() {
    	FlatLightLaf.setup();
		setTitle("ManageRx");
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment().getMaximumWindowBounds(); // dimensions of screen from
		// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java
		// screenDims.width /= 1.5;
		// screenDims.height /= 1.5;
		this.setSize(new Dimension(screenDims.width, screenDims.height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Font font = new Font("Arial", Font.PLAIN, 25); // font for buttons

		//set the icon scales and 
        stockIcon = stockIcon.setScale(0.25);
        orderIcon = orderIcon.setScale(0.25);
        patientsIcon = patientsIcon.setScale(0.25);
        this.headerPanel.setBorder(new LineBorder(Color.BLACK, 2));
        
        //add managerx label
        JLabel label = new JLabel("ManageRx");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        this.headerPanel.add(label);
        
        GridBagConstraints gbc = new GridBagConstraints();//grid constraints 
        gbc.insets = new Insets(20, 20, 20, 20);//set insets 
        
        
        //constraints and styles for stock ui button
        gbc.gridx = 0;
        gbc.gridy = 0;
        btnOpenStock = new JButton("Stock");
        btnOpenStock.setIcon(stockIcon);
        btnOpenStock.setActionCommand("openStock");
        btnOpenStock.addActionListener(this);
        btnOpenStock.setPreferredSize(new Dimension(180*2, 100*2));
        btnOpenStock.setFont(font);
        this.bPanel.add(btnOpenStock, gbc);
        
        //constraints and styles for order ui button
        gbc.gridx = 1;
        gbc.gridy = 0;
        btnOpenOrder = new JButton("Order");
        btnOpenOrder.setIcon(orderIcon);
        btnOpenOrder.setActionCommand("openOrder");
        btnOpenOrder.addActionListener(this);
        btnOpenOrder.setPreferredSize(new Dimension(180*2, 100*2));
        btnOpenOrder.setFont(font);
        this.bPanel.add(btnOpenOrder, gbc);
        
        //constraints and styles for patient manager button
        gbc.gridx = 2;
        gbc.gridy = 0;
        btnOpenPatientManager = new JButton("Patients");
        btnOpenPatientManager.setIcon(patientsIcon);
        btnOpenPatientManager.setActionCommand("openPatientManager");
        btnOpenPatientManager.addActionListener(this);
        btnOpenPatientManager.setPreferredSize(new Dimension(180*2, 100*2));
        btnOpenPatientManager.setFont(font);
        this.bPanel.add(btnOpenPatientManager, gbc);
        
        add(this.headerPanel, BorderLayout.NORTH);
        add(this.bPanel, BorderLayout.CENTER);
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("openStock")) {
            System.out.println("Stock");
        }
        if (e.getActionCommand().equals("openOrder")) {
        	System.out.println("Order");
        }
        if (e.getActionCommand().equals("openPatientManager")) {
        	System.out.println("Patients");
        	
        }
    }
    
    /*private void login(String username, String password) {
	    // Method body goes here
	}
	
	private void updateSaveFile() {
	    // Method body goes here
	}
	
	private DrugStock suggestAlt(AllStock allStock, String drugName) {
	    // Method body goes here
	}
	
	private String findDrugInfo(String drugName) {
	    // Method body goes here
	}*/
    
}
