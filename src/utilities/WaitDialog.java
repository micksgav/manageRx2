package utilities;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitDialog extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static ExecutorService service;
	static WaitDialog frame;
	static boolean animation = true;
	static String importing;
	static int i;

	/**
	 * Launch the application.
	 */

	public static void showWait(String importing) {
		service = Executors.newFixedThreadPool(1);
		service.submit(new Runnable() {
			public void run() {
				try {
					animation = true;
					i = 0;
					frame = new WaitDialog(true, importing);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void disposeWait() {
		animation = false;

	}

	public static void updateProgress(String importing) {
		WaitDialog.importing = importing;
		i++;
	}

	/**
	 * Create the frame.
	 * 
	 * @throws InterruptedException
	 */
	public WaitDialog(boolean status, String importing) {
		// setup screen attributes
		FlatLightLaf.setup();
		setTitle("Loading");
		Rectangle screenDims = GraphicsEnvironment.getLocalGraphicsEnvironment().getLocalGraphicsEnvironment()
				.getMaximumWindowBounds(); // dimensions of screen from
											// https://stackoverflow.com/questions/11570356/jframe-in-full-screen-java

		setBounds((int) (screenDims.width * 0.3), (int) (screenDims.height * 0.4), (int) (screenDims.width * 0.4),
				(int) (screenDims.width * 0.2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JProgressBar progress = new JProgressBar();
		progress.setValue(0);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1));

		JPanel panel = new JPanel(new FlowLayout());

		Label label = new Label("Please Wait...");
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 58));
		contentPane.add(label);
		panel.add(progress);

		contentPane.add(panel);

		setVisible(true);
		progress.setIndeterminate(true);

		if (!animation) {
			dispose();
		}
	}

	public WaitDialog() {
		// lol
	}
}
