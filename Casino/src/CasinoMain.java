import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CasinoMain {

	private JFrame frame;
	
	private URL floorURL = getClass().getResource("/res/CasinoFloor.png");
	private URL buckURL = getClass().getResource("/res/MatheusBuck.png");
	private URL fountainURL = getClass().getResource("/res/CasinoFountain.gif");
	
	private ImageIcon floor = new ImageIcon(floorURL);
	private ImageIcon buck = new ImageIcon(buckURL);
	private ImageIcon fountain = new ImageIcon(fountainURL);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CasinoMain window = new CasinoMain();
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
	public CasinoMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Passion Project Casino");
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnPlayBlackjack = new JButton("Play Blackjack");
		btnPlayBlackjack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BlackjackGUI.main(null);
				frame.dispose();
			}
		});
		btnPlayBlackjack.setBounds(115, 313, 117, 29);
		frame.getContentPane().add(btnPlayBlackjack);
		
		JButton btnPlayBaccarat = new JButton("Play Baccarat");
		btnPlayBaccarat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaccaratGUI.main(null);
				frame.dispose();
			}
		});
		btnPlayBaccarat.setBounds(780, 313, 117, 29);
		frame.getContentPane().add(btnPlayBaccarat);
		
		JButton btnPlaySlots = new JButton("Play Slots");
		btnPlaySlots.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SlotsGUI.main(null);
				frame.dispose();
			}
		});
		btnPlaySlots.setBounds(115, 597, 117, 29);
		frame.getContentPane().add(btnPlaySlots);
		
		JButton btnPlayRoulette = new JButton("Play Roulette");
		btnPlayRoulette.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RouletteGUI.main(null);
				frame.dispose();
			}
		});
		btnPlayRoulette.setBounds(780, 597, 117, 29);
		frame.getContentPane().add(btnPlayRoulette);
		
		JLabel lblMatheus = new JLabel("");
		lblMatheus.setBounds(41, 41, 90, 43);
		lblMatheus.setIcon(buck);
		frame.getContentPane().add(lblMatheus);
		
		JLabel label_1 = new JLabel(Integer.toString(CasinoData.balance));
		label_1.setBounds(152, 52, 61, 16);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label_1.setForeground(Color.WHITE);
		frame.getContentPane().add(label_1);
		
		JLabel lblFountain = new JLabel("");
		lblFountain.setBounds(460, 412, 104, 132);
		lblFountain.setIcon(fountain);
		frame.getContentPane().add(lblFountain);
		
		JLabel lblFloor = new JLabel("");
		lblFloor.setBounds(0, 0, 1000, 1000);
		lblFloor.setIcon(floor);
		frame.getContentPane().add(lblFloor);
		
	}
}
