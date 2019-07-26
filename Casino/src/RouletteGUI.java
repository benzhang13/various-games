import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RouletteGUI {

	private JFrame frame;
	
	private URL plainTableURL = getClass().getResource("/res/PlainGame.png");
	private URL buckURL = getClass().getResource("/res/MatheusBuck.png");
	
	private ImageIcon plainTable = new ImageIcon(plainTableURL);
	private ImageIcon buck = new ImageIcon(buckURL);
	
	private int redBet = 0;
	private int blackBet = 0;
	private int[] numBet = new int[37];
	private JTextField betAmount;
	private JTextField betNumber;
	final int[] spinOrder = {0,32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26};
	private int index = 0;
	
	private JButton btnBetOnRed = new JButton("Bet on Red");
	private JButton btnSpin = new JButton("Spin");
	private JButton btnBetOnBlack = new JButton("Bet on Black");
	private JButton btnBetOnNumber = new JButton("Bet on Number");
	private JLabel lblTable = new JLabel("");
	private final JLabel lblBuck = new JLabel("");
	private final JLabel label = new JLabel(Integer.toString(CasinoData.balance));
	private final JButton btnBackToCasino = new JButton("Back to Casino");
	private final JLabel lblWins = new JLabel("");

	private void update(int index) {
		String path = "/res/BallOn" + Integer.toString(spinOrder[index]) + ".png";
		URL url = getClass().getResource(path);
		ImageIcon image = new ImageIcon(url);
		lblTable.setIcon(image);
	}
	
	private void changeIndex(){
		if(index == 36) {
			index = 0;
		} else {
			index += 1;
		}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RouletteGUI window = new RouletteGUI();
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
	public RouletteGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		betAmount = new JTextField();
		betAmount.setBounds(407, 376, 182, 26);
		frame.getContentPane().add(betAmount);
		betAmount.setColumns(10);
		
		betNumber = new JTextField();
		betNumber.setBounds(625, 452, 50, 26);
		frame.getContentPane().add(betNumber);
		betNumber.setColumns(10);
		
		btnSpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				int num = rand.nextInt(37);
				index = num;
				
				hideButtons();
				
				Timer timer = new Timer(10, null);
				
				timer.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent evt) {
				        changeIndex();
				        update(index);

				        if(timer.getDelay() <= 100) {
				        	timer.setDelay(timer.getDelay() + 1);
				        }
				        else if(timer.getDelay() <= 200) {
				        	timer.setDelay(timer.getDelay() + 10);
				        }
				        else if(timer.getDelay() <= 300) {
				        	timer.setDelay(timer.getDelay() + 20);
				        }
				        else if(timer.getDelay() <= 400) {
				        	timer.setDelay(timer.getDelay() + 50);
				        }
				        else if(timer.getDelay() <= 500) {
				        	timer.setDelay(timer.getDelay() + 70);
				        }
				        else if(timer.getDelay() <= 600) {
				        	timer.setDelay(timer.getDelay() + 80);
				        } else {
				        	timer.setDelay(timer.getDelay() + 100);
				        }

				        
				        if(timer.getDelay() >= 800) {
				        	CasinoData.balance += 35 * numBet[spinOrder[index]];
							label.setText(Integer.toString(CasinoData.balance));
							lblWins.setText(spinOrder[index] + " wins!");
				        	if(index != 0) {
				        		if(index % 2 == 0) {
				        			CasinoData.balance += 2 * blackBet;
									label.setText(Integer.toString(CasinoData.balance));
				        		} else {
				        			CasinoData.balance += 2 * redBet;
									label.setText(Integer.toString(CasinoData.balance));
				        		}
				        	}
				        	showButtons();
				        	timer.stop();
				        }
				    }
				});
				
				timer.start();
			}
		});
		btnSpin.setBounds(438, 335, 117, 29);
		frame.getContentPane().add(btnSpin);
		
		btnBetOnNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(betAmount.getText().equals("Insufficient Balance")) {
					betAmount.setText(null);
				}
				if(Integer.parseInt(betAmount.getText()) > CasinoData.balance) {
					betAmount.setText("Insufficient Balance");
				} else {
					numBet[Integer.parseInt(betAmount.getText())] += Integer.parseInt(betAmount.getText());
					CasinoData.balance -= Integer.parseInt(betAmount.getText());
					label.setText(Integer.toString(CasinoData.balance));
					betAmount.setText(null);
					betNumber.setText(null);
				}
			}
		});
		btnBetOnNumber.setBounds(590, 414, 117, 29);
		frame.getContentPane().add(btnBetOnNumber);
		
		btnBetOnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(betAmount.getText().equals("Insufficient Balance")) {
					betAmount.setText(null);
				}
				if(Integer.parseInt(betAmount.getText()) > CasinoData.balance) {
					betAmount.setText("Insufficient Balance");
				} else {
					blackBet += Integer.parseInt(betAmount.getText());
					CasinoData.balance -= Integer.parseInt(betAmount.getText());
					label.setText(Integer.toString(CasinoData.balance));
					betAmount.setText(null);
				}
			}
		});
		btnBetOnBlack.setBounds(438, 414, 117, 29);
		frame.getContentPane().add(btnBetOnBlack);
		
		btnBetOnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(betAmount.getText().equals("Insufficient Balance")) {
					betAmount.setText(null);
				}
				if(Integer.parseInt(betAmount.getText()) > CasinoData.balance) {
					betAmount.setText("Insufficient Balance");
				} else {
					redBet += Integer.parseInt(betAmount.getText());
					CasinoData.balance -= Integer.parseInt(betAmount.getText());
					label.setText(Integer.toString(CasinoData.balance));
					betAmount.setText(null);
				}
			}
		});
		btnBetOnRed.setBounds(298, 414, 117, 29);
		frame.getContentPane().add(btnBetOnRed);
		
		lblBuck.setBounds(24, 22, 90, 43);
		lblBuck.setIcon(buck);
		frame.getContentPane().add(lblBuck);
		
		label.setBounds(137, 37, 78, 16);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);
		frame.getContentPane().add(label);
		btnBackToCasino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CasinoMain.main(null);
				frame.dispose();
			}
		});
		
		btnBackToCasino.setBounds(827, 22, 117, 29);
		frame.getContentPane().add(btnBackToCasino);
		lblWins.setForeground(Color.WHITE);
		lblWins.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblWins.setBounds(450, 253, 121, 16);
		frame.getContentPane().add(lblWins);
		
		lblTable.setBounds(0, 0, 1000, 1000);
		lblTable.setIcon(plainTable);
		frame.getContentPane().add(lblTable);
	}
	
	private void hideButtons() {
		btnBetOnRed.setVisible(false);
		btnBetOnBlack.setVisible(false);
		btnBetOnNumber.setVisible(false);
		betAmount.setVisible(false);
		betNumber.setVisible(false);
		btnSpin.setVisible(false);
	}
	
	private void showButtons() {
		btnBetOnRed.setVisible(true);
		btnBetOnBlack.setVisible(true);
		btnBetOnNumber.setVisible(true);
		betAmount.setVisible(true);
		betNumber.setVisible(true);
		btnSpin.setVisible(true);
	}

}
