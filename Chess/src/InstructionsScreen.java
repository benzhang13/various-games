//Ben Zhang
//ICS 4U
//Summitive Assignment
//Instructions Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InstructionsScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InstructionsScreen window = new InstructionsScreen();
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
	public InstructionsScreen() {
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
		
		JButton btnPieces = new JButton("Pieces");
		btnPieces.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PiecesScreen.main(null);
				frame.dispose();
			}
		});
		btnPieces.setBounds(191, 109, 117, 29);
		frame.getContentPane().add(btnPieces);
		
		JButton btnSpecialMoves = new JButton("Special Moves");
		btnSpecialMoves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SpecialMovesScreen.main(null);
				frame.dispose();
			}
		});
		btnSpecialMoves.setBounds(191, 144, 117, 29);
		frame.getContentPane().add(btnSpecialMoves);
		
		JButton btnBasicRules = new JButton("Basic Rules");
		btnBasicRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasicRulesScreen.main(null);
				frame.dispose();
			}
		});
		btnBasicRules.setBounds(191, 68, 117, 29);
		frame.getContentPane().add(btnBasicRules);
		
		JButton btnVariations = new JButton("Variations");
		btnVariations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VariationsScreen.main(null);
				frame.dispose();
			}
		});
		btnVariations.setBounds(191, 185, 117, 29);
		frame.getContentPane().add(btnVariations);
		
		JLabel lblInstructions = new JLabel("Instructions");
		lblInstructions.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblInstructions.setBounds(191, 20, 172, 27);
		frame.getContentPane().add(lblInstructions);
		
		JButton btnQut = new JButton("Main Menu");
		btnQut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnQut.setBounds(191, 226, 117, 29);
		frame.getContentPane().add(btnQut);
	}
}
