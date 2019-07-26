//Ben Zhang
//ICS 4U
//Summitive Assignment
//Special Moves Instructions Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpecialMovesScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpecialMovesScreen window = new SpecialMovesScreen();
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
	public SpecialMovesScreen() {
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
		
		JLabel lblCastling = new JLabel("Castling - ");
		lblCastling.setBounds(17, 6, 81, 16);
		frame.getContentPane().add(lblCastling);
		
		JLabel lblEnPassant = new JLabel("En Passant -");
		lblEnPassant.setBounds(6, 128, 104, 16);
		frame.getContentPane().add(lblEnPassant);
		
		JLabel lblPromotion = new JLabel("Promotion- ");
		lblPromotion.setBounds(6, 212, 81, 16);
		frame.getContentPane().add(lblPromotion);
		
		JTextPane castling = new JTextPane();
		castling.setText("Under special circumstances, the king can move two spaces left or right and have that sides rook pass through it. These conditions are: the king has not moved, that sides rook has not moved, there are no pieces between the rook and king, the king is not currently in or passing through check.");
		castling.setBounds(98, 6, 313, 114);
		frame.getContentPane().add(castling);
		castling.setOpaque(false);
		castling.setEditable(false);
		
		JTextPane enPassent = new JTextPane();
		enPassent.setEditable(false);
		enPassent.setText("If an enemy pawn moves forward two squares, and one of your pawns is either to the right or left of it, it can capture as if the pawn moves only one square forward, but for only that turn");
		enPassent.setBounds(98, 128, 325, 72);
		frame.getContentPane().add(enPassent);
		enPassent.setOpaque(false);
		
		JTextPane promotion = new JTextPane();
		promotion.setEditable(false);
		promotion.setText("If one of your pawns reaches the enemy back rank, it becomes either your choice of a knight, bishop, rook, or queen.");
		promotion.setBounds(98, 212, 313, 48);
		frame.getContentPane().add(promotion);
		promotion.setOpaque(false);
		
		JButton btnMainMenu = new JButton("Back");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructionsScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(307, 250, 104, 22);
		frame.getContentPane().add(btnMainMenu);
	}
}
