//Ben Zhang
//ICS 4U
//Summitive Assignment
//Main Menu Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MainMenuScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuScreen window = new MainMenuScreen();
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
	public MainMenuScreen() {
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
		
		JLabel lblChess = new JLabel("Chess");
		lblChess.setHorizontalAlignment(SwingConstants.CENTER);
		lblChess.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblChess.setBounds(30, 34, 420, 62);
		frame.getContentPane().add(lblChess);
		
		if(chessData.agadMode) {
			lblChess.setText("AGADMATOR");
		}
		
		JButton btnRegisterAnAccount = new JButton("Register an Account");
		btnRegisterAnAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterScreen.main(null);
				frame.dispose();
			}
		});
		btnRegisterAnAccount.setBounds(143, 124, 191, 29);
		frame.getContentPane().add(btnRegisterAnAccount);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectModeScreen.main(null);
				frame.dispose();
			}
		});
		btnPlay.setBounds(181, 95, 117, 29);
		frame.getContentPane().add(btnPlay);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnExit.setBounds(181, 232, 117, 29);
		frame.getContentPane().add(btnExit);
		
		JButton btnLeaderboard = new JButton("Leaderboard");
		btnLeaderboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LeaderboardScreen.main(null);
				frame.dispose();
			}
		});
		btnLeaderboard.setBounds(181, 179, 117, 29);
		frame.getContentPane().add(btnLeaderboard);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructionsScreen.main(null);
				frame.dispose();
			}
		});
		btnInstructions.setBounds(181, 152, 117, 29);
		frame.getContentPane().add(btnInstructions);
		
		ImageIcon agadRegular = new ImageIcon(MainMenuScreen.class.getResource("/res/agadmatorbare.png"));
		ImageIcon agadEvil = new ImageIcon(MainMenuScreen.class.getResource("/res/export.png"));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chessData.agadMode == false) {
					lblChess.setText("AGADMATOR");
					button.setIcon(agadEvil);
					chessData.agadMode = true;
				} else {
					lblChess.setText("Chess");
					button.setIcon(agadRegular);
					chessData.agadMode = false;
				}
			}
		});
		
		if(chessData.agadMode) {
			button.setIcon(agadEvil);
		} else {
			button.setIcon(agadRegular);
		}
		button.setBorderPainted(false);
		button.setBorder(null);
		button.setBounds(6, 190, 55, 80);
		frame.getContentPane().add(button);
		
		JButton btnCredits = new JButton("Credits");
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreditsScreen.main(null);
				frame.dispose();
			}
		});
		btnCredits.setBounds(181, 205, 117, 29);
		frame.getContentPane().add(btnCredits);
		
	}
}
