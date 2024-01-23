package utilities;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Label;
import java.awt.Rectangle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitDialog extends JFrame {

	private static final long serialVersionUID = 1L; // for jDialog
	private JPanel contentPane; // pane
	static ExecutorService service; // for multithreading
	static WaitDialog frame; // frame
	static String importing; // what is being imported

	/** Method Name: showWait
	* @Author Gavin Micks
	* @Date December 18, 2023
	* @Modified December 21, 2024
	* @Description hides waiting screen
	* @Parameters  importing - thing being imported
	* @Returns N/A
	* Dependencies: jframe, executors, logErrors
	* Throws/Exceptions: N/A
    */
	public static void showWait(String importing) {
		// multithreading
		service = Executors.newFixedThreadPool(1);
		service.submit(new Runnable() {
			public void run() {
				try {
					frame = new WaitDialog(true, importing); // make new wait dialog on thread
				} catch (Exception e) {
					logErrors.log(e.getMessage() + " in showWait in WaitDialog");
				} // end try-catch
			}
		});
	} // end showWait

	/** Method Name: disposeWait
	* @Author Gavin Micks
	* @Date December 18, 2023
	* @Modified December 21, 2024
	* @Description hides waiting screen
	* @Parameters  N/A
	* @Returns N/A
	* Dependencies: jframe
	* Throws/Exceptions: N/A
    */
	public static void disposeWait() {
		frame.dispose(); // hides wait dialog??
	} // end disposeWait

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

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1));

		JPanel panel = new JPanel(new FlowLayout());

		Label label = new Label(importing);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(label);
		progress.setIndeterminate(true);
		panel.add(progress);
		contentPane.add(panel);

		setVisible(true); // make visible
	} // end WaitDialog constructor

	public WaitDialog() {
		// nothing needed
	} // end WaitDialog constructor
} // end WaitDialog
