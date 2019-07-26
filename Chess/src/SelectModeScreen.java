//Ben Zhang
//ICS 4U
//Summitive Assignment
//Mode Selection Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class SelectModeScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectModeScreen window = new SelectModeScreen();
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
	public SelectModeScreen() {
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
		
		JButton btnClassic = new JButton("Classic");
		btnClassic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessData.mode = "classic";
				LoginWindow.main(null);
				frame.dispose();
			}
		});
		btnClassic.setBounds(194, 108, 117, 29);
		frame.getContentPane().add(btnClassic);
		
		JButton btnChess = new JButton("Chess960");
		btnChess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessData.mode = "random";
				LoginWindow.main(null);
				frame.dispose();
			}
		});
		btnChess.setBounds(194, 155, 117, 29);
		frame.getContentPane().add(btnChess);
		
		JButton btnReallyBadChess = new JButton("Really Bad Chess");
		btnReallyBadChess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessData.mode = "bad";
				LoginWindow.main(null);
				frame.dispose();
			}
		});
		btnReallyBadChess.setBounds(183, 196, 135, 29);
		frame.getContentPane().add(btnReallyBadChess);
		
		JLabel lblSelectAMode = new JLabel("Select a Mode");
		lblSelectAMode.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblSelectAMode.setBounds(183, 59, 171, 37);
		frame.getContentPane().add(lblSelectAMode);
	}
}
