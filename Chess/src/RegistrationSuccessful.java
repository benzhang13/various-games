//Ben Zhang
//ICS 4U
//Summitive Assignment
//Successful Registration Info Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrationSuccessful {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistrationSuccessful window = new RegistrationSuccessful();
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
	public RegistrationSuccessful() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistrationSuccessul = new JLabel("Registration Successful!");
		lblRegistrationSuccessul.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblRegistrationSuccessul.setBounds(93, 78, 305, 65);
		frame.getContentPane().add(lblRegistrationSuccessul);
		
		JButton btnReturnToMain = new JButton("Return to Main Menu");
		btnReturnToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnReturnToMain.setBounds(139, 183, 174, 29);
		frame.getContentPane().add(btnReturnToMain);
	}

}
