//Ben Zhang
//ICS 4U
//Summitive Assignment
//Successful Recording Info Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecordingSuccessful {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecordingSuccessful window = new RecordingSuccessful();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RecordingSuccessful() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblRecordingSuccessful = new JLabel("Recording Successful!");
		lblRecordingSuccessful.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
		lblRecordingSuccessful.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblRecordingSuccessful, BorderLayout.CENTER);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnMainMenu, BorderLayout.SOUTH);
	}

}
