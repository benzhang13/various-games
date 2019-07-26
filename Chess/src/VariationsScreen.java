//Ben Zhang
//ICS 4U
//Summitive Assignment
//Variations Info Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;

public class VariationsScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VariationsScreen window = new VariationsScreen();
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
	public VariationsScreen() {
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
		btnMainMenu.setBounds(184, 243, 117, 29);
		frame.getContentPane().add(btnMainMenu);
		
		JLabel lblChess = new JLabel("Chess960 -");
		lblChess.setBounds(42, 40, 80, 16);
		frame.getContentPane().add(lblChess);
		
		JLabel lblReallyBadChess = new JLabel("Really Bad Chess -");
		lblReallyBadChess.setBounds(5, 168, 117, 16);
		frame.getContentPane().add(lblReallyBadChess);
		
		JTextPane txtpnChessAlsoCalled = new JTextPane();
		txtpnChessAlsoCalled.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtpnChessAlsoCalled.setText("Chess960, also called Fischer Random Chess (originally Fischerandom), is a variant of chess invented and advocated by former world chess champion Bobby Fischer, announced publicly on June 19, 1996, in Buenos Aires, Argentina. It employs the same board and pieces as standard chess, but the starting position of the pieces on the players' home ranks is randomized. ");
		txtpnChessAlsoCalled.setOpaque(false);
		txtpnChessAlsoCalled.setEditable(false);
		txtpnChessAlsoCalled.setBounds(134, 40, 298, 120);
		frame.getContentPane().add(txtpnChessAlsoCalled);
		
		JTextPane txtpnReallyBadChess = new JTextPane();
		txtpnReallyBadChess.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtpnReallyBadChess.setText("Really Bad Chess was originally made as a mobile app in May of 2017 by Zach Gage. Much like Chess960, the back rank is randomized, however, not only are the pieces' positions randomized, but so are the pieces themselves.");
		txtpnReallyBadChess.setOpaque(false);
		txtpnReallyBadChess.setEditable(false);
		txtpnReallyBadChess.setBounds(134, 168, 310, 75);
		frame.getContentPane().add(txtpnReallyBadChess);
	}

}
