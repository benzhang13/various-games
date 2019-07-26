import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SlotsGUI {

	private JFrame frame;

	private URL win7URL = getClass().getResource("/res/SlotsSevensWin.png");
	private URL winApplesURL = getClass().getResource("/res/SlotsApplesWin.png");
	private URL winBellsURL = getClass().getResource("/res/SlotsBellsWin.png");
	private URL lose1URL = getClass().getResource("/res/SlotsLose1.png");
	private URL lose2URL = getClass().getResource("/res/SlotsLose2.png");
	private URL lose3URL = getClass().getResource("/res/SlotsLose3.png");
	private URL lose4URL = getClass().getResource("/res/SlotsLose4.png");
	private URL lose5URL = getClass().getResource("/res/SlotsLose5.png");
	private URL lose6URL = getClass().getResource("/res/SlotsLose6.png");
	private URL buckURL = getClass().getResource("/res/MatheusBuck.png");

	private ImageIcon win7 = new ImageIcon(win7URL);
	private ImageIcon winApples = new ImageIcon(winApplesURL);
	private ImageIcon winBells = new ImageIcon(winBellsURL);
	private ImageIcon lose1 = new ImageIcon(lose1URL);
	private ImageIcon lose2 = new ImageIcon(lose2URL);
	private ImageIcon lose3 = new ImageIcon(lose3URL);
	private ImageIcon lose4 = new ImageIcon(lose4URL);
	private ImageIcon lose5 = new ImageIcon(lose5URL);
	private ImageIcon lose6 = new ImageIcon(lose6URL);
	private ImageIcon buck = new ImageIcon(buckURL);

	private JLabel lblMachine = new JLabel("");
	JButton btnSpin = new JButton("Spin for 100!");

	private int counter = 0;
	private final JLabel lblMatheus = new JLabel("");
	private final JLabel label = new JLabel(Integer.toString(CasinoData.balance));

	private void endImage(int num) {
		if(num <= 3) {
			lblMachine.setIcon(win7);
			CasinoData.balance += 5000;
			label.setText(Integer.toString(CasinoData.balance));
		}
		else if(num <= 15) {
			lblMachine.setIcon(winBells);
			CasinoData.balance += 1000;
			label.setText(Integer.toString(CasinoData.balance));
		}
		else if(num <= 40) {
			lblMachine.setIcon(winApples);
			CasinoData.balance += 500;
			label.setText(Integer.toString(CasinoData.balance));
		} else {
			Random rand = new Random();
			int result = rand.nextInt(6);

			if(result == 0) {
				lblMachine.setIcon(lose1);
			}
			else if(result == 1) {
				lblMachine.setIcon(lose2);
			}
			else if(result == 2) {
				lblMachine.setIcon(lose2);
			}
			else if(result == 3) {
				lblMachine.setIcon(lose2);
			}
			else if(result == 4) {
				lblMachine.setIcon(lose2);
			}
			else if(result == 5) {
				lblMachine.setIcon(lose2);
			}
		}
	}

	private void changeImage() {
		Random rand = new Random();

		int num = rand.nextInt(9);

		if(num == 0) {
			lblMachine.setIcon(win7);
		}
		else if(num == 1) {
			lblMachine.setIcon(winApples);
		}
		else if(num == 2) {
			lblMachine.setIcon(winBells);
		}
		else if(num == 3) {
			lblMachine.setIcon(lose1);
		}
		else if(num == 4) {
			lblMachine.setIcon(lose2);
		}
		else if(num == 5) {
			lblMachine.setIcon(lose3);
		}
		else if(num == 6) {
			lblMachine.setIcon(lose4);
		}
		else if(num == 7) {
			lblMachine.setIcon(lose5);
		}
		else {
			lblMachine.setIcon(lose6);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SlotsGUI window = new SlotsGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void updateCounter() {
		counter += 1;
	}

	/**
	 * Create the application.
	 */
	public SlotsGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnSpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(CasinoData.balance > 100) {
					counter = 0;
					CasinoData.balance -= 100;
					label.setText(Integer.toString(CasinoData.balance));
					hideButtons();
					Random rand = new Random();
					int num = rand.nextInt(100);

					Timer timer = new Timer(50, null);
					timer.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							changeImage();
							updateCounter();

							if(counter == 100) {
								endImage(num);
								showButtons();
								timer.stop();
							}
						}
					});
					timer.start();
				}
			}
		});
		btnSpin.setBounds(269, 625, 117, 29);
		frame.getContentPane().add(btnSpin);

		JButton btnBackToCasino = new JButton("Back to Casino");
		btnBackToCasino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CasinoMain.main(null);
				frame.dispose();
			}
		});
		btnBackToCasino.setBounds(606, 16, 117, 29);
		frame.getContentPane().add(btnBackToCasino);

		label.setBounds(126, 33, 61, 16);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);
		frame.getContentPane().add(label);

		lblMatheus.setBounds(25, 21, 90, 43);
		lblMatheus.setIcon(buck);
		frame.getContentPane().add(lblMatheus);

		lblMachine.setBounds(0, 0, 800, 800);
		lblMachine.setIcon(win7);
		frame.getContentPane().add(lblMachine);
	}

	private void hideButtons() {
		btnSpin.setVisible(false);
	}

	private void showButtons() {
		btnSpin.setVisible(true);
	}

}
