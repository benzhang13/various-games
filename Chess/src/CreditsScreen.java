//Ben Zhang
//ICS 4U
//Summitive Assignment
//Credits Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreditsScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreditsScreen window = new CreditsScreen();
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
	public CreditsScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCredits = new JLabel("Credits");
		lblCredits.setBounds(6, 21, 450, 16);
		lblCredits.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblCredits);
		
		JLabel lblBenZhang = new JLabel("Ben Zhang - Everything Else");
		lblBenZhang.setBounds(62, 133, 251, 16);
		frame.getContentPane().add(lblBenZhang);
		
		JLabel lblChesscom = new JLabel("Chess.com - Piece Sprites and Regular Sounds");
		lblChesscom.setBounds(62, 49, 305, 16);
		frame.getContentPane().add(lblChesscom);
		
		JLabel lblAgadmator = new JLabel("Agadmator - Agadmator Soundpack");
		lblAgadmator.setBounds(62, 77, 305, 16);
		frame.getContentPane().add(lblAgadmator);
		
		JLabel lblMahadRehan = new JLabel("Mahad Rehan - Cropped Image of Agadmator's Head");
		lblMahadRehan.setBounds(62, 105, 358, 16);
		frame.getContentPane().add(lblMahadRehan);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(16, 16, 117, 29);
		frame.getContentPane().add(btnMainMenu);
	}
}
