//Ben Zhang
//ICS 4U
//Summitive Assignment
//Piece Info Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class PiecesScreen {

	private JFrame frame;
	private JButton btnPawn = new JButton("Pawn");
	private JButton btnKnight = new JButton("Knight");
	private JButton btnBishop = new JButton("Bishop");
	private JButton btnRook = new JButton("Rook");
	private JButton btnQueen = new JButton("Queen");
	private JButton btnKing = new JButton("King");
	private JLabel lblClickAPiece = new JLabel("Click a Piece!");
	private JLabel gifLabel = new JLabel("");
	private JLabel lblValue = new JLabel("");
	private JLabel lblName = new JLabel("");
	private final JButton btnBack = new JButton("Back");
	private final JButton btnMainMenu = new JButton("Back");
	private final JTextPane textPane = new JTextPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PiecesScreen window = new PiecesScreen();
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
	public PiecesScreen() {
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
		
		btnPawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("Pawn");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/PawnGif.gif")));
				textPane.setText("Unlike the other pieces, pawns cannot move backwards. Normally a pawn moves by advancing a single square, but the first time a pawn moves, it has the option of advancing two squares. Pawns may not use the initial two-square advance to jump over an occupied square, or to capture.Unlike other pieces, the pawn does not capture in the same direction as it moves. A pawn captures diagonally forward one square to the left or right.");
				lblValue.setText("Value: 1 Point");
				
				hideButtons();
				showPieceGuide();
			}
		});
		btnPawn.setBounds(70, 60, 117, 29);
		frame.getContentPane().add(btnPawn);
		
		btnKnight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("Knight");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/KnightGif.gif")));
				textPane.setText("The knight move is unusual among chess pieces. It moves to a square that is two squares away horizontally and one square vertically, or two squares vertically and one square horizontally. The complete move therefore looks like the letter \"L\". Unlike all other standard chess pieces, the knight can \"jump over\" all other pieces (of either color) to its destination square. It captures an enemy piece by replacing it on its square.");
				lblValue.setText("Value: 3 Points");
				
				hideButtons();
				showPieceGuide();
			}
		});
		btnKnight.setBounds(70, 126, 117, 29);
		frame.getContentPane().add(btnKnight);
		
		btnBishop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("Bishop");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/BishopGif.gif")));
				textPane.setText("The bishop has no restrictions in distance for each move, but is limited to diagonal movement. Bishops, like all other pieces except the knight, cannot jump over other pieces. A bishop captures by occupying the square on which an enemy piece sits.");
				lblValue.setText("Value: 3 Points");
				
				hideButtons();
				showPieceGuide();
			}
		});
		btnBishop.setBounds(70, 194, 117, 29);
		frame.getContentPane().add(btnBishop);
		
		btnRook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("Rook");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/RookGif.gif")));
				textPane.setText("The rook moves horizontally or vertically, through any number of unoccupied squares. As with captures by other pieces, the rook captures by occupying the square on which the enemy piece sits. The rook also participates, with the king, in a special move called castling.");
				lblValue.setText("Value: 5 Points");
				
				hideButtons();
				showPieceGuide();
			}
		});	
		btnRook.setBounds(250, 60, 117, 29);
		frame.getContentPane().add(btnRook);
		
		btnQueen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("Queen");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/QueenGif.gif")));
				textPane.setText("The queen can be moved any number of unoccupied squares in a straight line vertically, horizontally, or diagonally, thus combining the moves of the rook and bishop. The queen captures by occupying the square on which an enemy piece sits.");
				lblValue.setText("Value: 9 Points");
				
				hideButtons();
				showPieceGuide();
			}
		});
		btnQueen.setBounds(250, 126, 117, 29);
		frame.getContentPane().add(btnQueen);
		
		btnKing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblName.setText("King");
				gifLabel.setIcon(new ImageIcon(getClass().getResource("/res/KingGif.gif")));
				textPane.setText("A king can move one square in any direction (horizontally, vertically, or diagonally) unless the square is already occupied by a friendly piece or the move would place the king in check. The king captures by occupying the square on which the enemy piece sits. The king is also involved with the rook in the special move of castling.");
				lblValue.setText("Value: Infinite Points");
				
				hideButtons();
				showPieceGuide();
			}
		});	
		btnKing.setBounds(250, 194, 117, 29);
		frame.getContentPane().add(btnKing);
		
		lblClickAPiece.setBounds(178, 32, 83, 16);
		frame.getContentPane().add(lblClickAPiece);
		
		gifLabel.setBounds(35, 47, 120, 120);
		frame.getContentPane().add(gifLabel);
		gifLabel.setVisible(false);

		lblValue.setBounds(294, 6, 150, 29);
		frame.getContentPane().add(lblValue);
		lblValue.setVisible(false);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblName.setBounds(185, 19, 61, 16);
		frame.getContentPane().add(lblName);
		lblName.setVisible(false);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showButtons();
				hidePieceGuide();
			}
		});	
		btnBack.setBounds(166, 243, 117, 29);
		frame.getContentPane().add(btnBack);
		btnBack.setVisible(false);
		
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InstructionsScreen.main(null);
				frame.dispose();
			}
		});	
		btnMainMenu.setBounds(166, 243, 117, 29);
		frame.getContentPane().add(btnMainMenu);
		
		textPane.setBounds(167, 47, 277, 193);
		frame.getContentPane().add(textPane);
		textPane.setOpaque(false);
		textPane.setEditable(false);
	}
	
	private void hideButtons() {
		btnPawn.setVisible(false);
		btnKnight.setVisible(false);
		btnBishop.setVisible(false);
		btnRook.setVisible(false);
		btnQueen.setVisible(false);
		btnKing.setVisible(false);
		lblClickAPiece.setVisible(false);
		btnMainMenu.setVisible(false);
	}
	
	private void showButtons(){
		btnPawn.setVisible(true);
		btnKnight.setVisible(true);
		btnBishop.setVisible(true);
		btnRook.setVisible(true);
		btnQueen.setVisible(true);
		btnKing.setVisible(true);
		lblClickAPiece.setVisible(true);
		btnMainMenu.setVisible(true);
	}
	
	private void showPieceGuide() {
		gifLabel.setVisible(true);
		lblValue.setVisible(true);
		lblName.setVisible(true);
		btnBack.setVisible(true);
		textPane.setVisible(true);
	}
	
	private void hidePieceGuide() {
		gifLabel.setVisible(false);
		lblValue.setVisible(false);
		lblName.setVisible(false);
		btnBack.setVisible(false);
		textPane.setVisible(false);
	}

}
