//Ben Zhang
//ICS 4U
//Summitive Assignment
//Basic Rules Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BasicRulesScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasicRulesScreen window = new BasicRulesScreen();
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
	public BasicRulesScreen() {
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
		
		JButton btnMainMenu = new JButton("Back");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructionsScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(191, 243, 117, 29);
		frame.getContentPane().add(btnMainMenu);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		textPane.setBounds(6, 6, 438, 234);
		frame.getContentPane().add(textPane);
		textPane.setEditable(false);
		textPane.setOpaque(false);
		textPane.setText("Chess is a two-player game, where one player is assigned white pieces and the other black. Each player has 16 pieces to start the game: one king, one queen, two rooks, two bishops, two knights and eight pawns. The object of the game is to capture the other player's king. This capture is never actually completed, but once a king is under attack and unable to avoid capture, it is said to be checkmated and the game is over. The game is started in the position shown below on a chess board consisting of 64 squares in an 8x8 grid. The White player moves first. Then each player takes a single turn. In fact, a player must move in turn. In other words a move cannot be skipped. The primary objective in chess is to checkmate your opponent's King. When a King cannot avoid capture then it is checkmated and the game is immediately over. The game is drawn when the player to move has no legal move and his king is not in check. The game is said to end in 'stalemate'. This immediately ends the game. ");
	}

}
