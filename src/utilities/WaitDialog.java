package utilities;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Label;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static ExecutorService service;
	static WaitDialog frame;
	static boolean animation = true;

	/**
	 * Launch the application.
	 */
	
	public static void showWait() {
		service = Executors.newFixedThreadPool(1);
		service.submit(new Runnable() {
			public void run() {
				try {
					animation = true;
					frame = new WaitDialog(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void disposeWait() {
		animation = false;
		
	}
	/**
	 * Create the frame.
	 * @throws InterruptedException 
	 */
	public WaitDialog(boolean status) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 163);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "name_647601548439200");
		panel.setLayout(null);
		
		Label label = new Label("Please Wait...");
		label.setAlignment(Label.LEFT);
		label.setFont(new Font("Dialog", Font.BOLD, 58));
		label.setBounds(0, 21, 414, 83);
		panel.add(label);
		
		setVisible(true);
		
		while(animation) {
			try {
			label.setText("Please Wait.");
			Thread.sleep(500);
			label.setText("Please Wait..");
			Thread.sleep(500);
			label.setText("Please Wait...");
			Thread.sleep(500);
			} catch (Exception e) { }
		}
		dispose();
	}
	
	public WaitDialog() {
		//lol
	}
}
