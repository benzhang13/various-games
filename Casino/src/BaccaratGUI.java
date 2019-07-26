import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class BaccaratGUI {
	
	private URL tableURL = getClass().getResource("/res/BaccaratTable.png");
	private URL buckURL = getClass().getResource("/res/MatheusBuck.png");
	
	private ImageIcon table = new ImageIcon(tableURL);
	private ImageIcon buck = new ImageIcon(buckURL);

	private int[][] playerHand = new int[3][2];
	private int[][] bankerHand = new int[3][2];

	private boolean[][] used = new boolean[13][4];

	private int playerTotal = 0;
	private int bankerTotal = 0;

	private boolean playerTurn = false;
	private boolean bankerTurn = false;

	private boolean playerNatural = false;
	private boolean bankerNatural = false;

	private int playerBet = 0;
	private int bankerBet = 0;
	private int tieBet = 0;

	private JFrame frame;
	private JButton btnPlayer = new JButton("Bet on Player");
	private JButton btnBanker = new JButton("Bet on Banker");
	private JButton btnTie = new JButton("Bet on Tie");

	private JLabel playerCard1 = new JLabel();
	private JLabel playerCard2 = new JLabel();
	private JLabel playerCard3 = new JLabel();

	private JLabel bankerCard1 = new JLabel();
	private JLabel bankerCard2 = new JLabel();
	private JLabel bankerCard3 = new JLabel();
	private JTextField textField;
	private JButton btnNext = new JButton("Next");
	private final JButton btnBackToCasino = new JButton("Back to Casino");
	private final JLabel lblEvent = new JLabel("");
	private final JLabel lblBackground = new JLabel("");
	private final JLabel lblMatheusbuck = new JLabel("");
	private final JLabel label = new JLabel(Integer.toString(CasinoData.balance));
	
	private void setPlayerCardImage() {	
		clearPlayerCardIcons();
		int numCards = 0;

		for(int i = 0; i < 3; i++) {
			if(playerHand[i][0] == -1) {
				break;
			} else {
				numCards += 1;
			}
		}

		for(int i = 0; i < numCards; i++) {
			String path = "/res/" + translateValue(playerHand[i][0]) + translateSuit(playerHand[i][1]) + ".png";
			URL cardURL = getClass().getResource(path);
			ImageIcon cardIcon = new ImageIcon(cardURL);

			if(playerCard1.getIcon() == null) {
				playerCard1.setIcon(cardIcon);
			}
			else if(playerCard2.getIcon() == null) {
				playerCard2.setIcon(cardIcon);
			}
			else if(playerCard3.getIcon() == null) {
				playerCard3.setIcon(cardIcon);
			}

		}

	}
	
	private void setBankerCardImage() {	
		clearBankerCardIcons();
		int numCards = 0;

		for(int i = 0; i < 3; i++) {
			if(bankerHand[i][0] == -1) {
				break;
			} else {
				numCards += 1;
			}
		}

		for(int i = 0; i < numCards; i++) {
			String path = "/res/" + translateValue(bankerHand[i][0]) + translateSuit(bankerHand[i][1]) + ".png";
			URL cardURL = getClass().getResource(path);
			ImageIcon cardIcon = new ImageIcon(cardURL);

			if(bankerCard1.getIcon() == null) {
				bankerCard1.setIcon(cardIcon);
			}
			else if(bankerCard2.getIcon() == null) {
				bankerCard2.setIcon(cardIcon);
			}
			else if(bankerCard3.getIcon() == null) {
				bankerCard3.setIcon(cardIcon);
			}

		}

	}
	
	private void clearPlayerCardIcons() {
		playerCard1.setIcon(null);
		playerCard2.setIcon(null);
		playerCard3.setIcon(null);
	}

	private void clearBankerCardIcons() {
		bankerCard1.setIcon(null);
		bankerCard2.setIcon(null);
		bankerCard3.setIcon(null);
	}
	
	private String translateValue(int num) {
		if(num == 0) {
			return "Ace";
		}
		else if(num == 1) {
			return "Two";
		}
		else if(num == 2) {
			return "Three";
		}
		else if(num == 3) {
			return "Four";
		}
		else if(num == 4) {
			return "Five";
		}
		else if(num == 5) {
			return "Six";
		}
		else if(num == 6) {
			return "Seven";
		}
		else if(num == 7) {
			return "Eight";
		}
		else if(num == 8) {
			return "Nine";
		}
		else if(num == 9) {
			return "Ten";
		}
		else if(num == 10) {
			return "Jack";
		}
		else if(num == 11) {
			return "Queen";
		} else {
			return "King";
		}
	}

	private String translateSuit(int suit) {
		if(suit == 0) {
			return "S";
		}
		else if(suit == 1) {
			return "C";
		}
		else if(suit == 2) {
			return "D";
		} else {
			return "H";
		}
	}


	private int convertValue(int card) {
		if(card >= 9) {
			return 0;
		} else {
			return card + 1;
		}
	}

	private void playerMove() {
		if(playerTotal >= 6) {
			playerTurn = false;
			bankerTurn = true;
			lblEvent.setText("Player Stands!");
		} else {
			dealPlayerCard();
			playerTotal += convertValue(playerHand[2][0]);
			playerTotal = playerTotal % 10;
			playerTurn = false;
			bankerTurn = true;
			lblEvent.setText("Player Hits!");
			setPlayerCardImage();
		}
	}

	private void bankerMove() {
		if(bankerTotal <= 2) {
			dealBankerCard();
			bankerTotal += convertValue(bankerHand[2][0]);
			bankerTotal = bankerTotal % 10;
			bankerTurn = false;
			lblEvent.setText("Banker Hits!");
			setBankerCardImage();
		}
		else if(bankerTotal == 3) {
			if(playerTotal == 8) {
				bankerTurn = false;
				lblEvent.setText("Banker Stands!");
			} else {
				dealBankerCard();
				bankerTotal += convertValue(bankerHand[2][0]);
				bankerTotal = bankerTotal % 10;
				bankerTurn = false;
				lblEvent.setText("Banker Hits!");
				setBankerCardImage();
			}
		}
		else if(bankerTotal == 4) {
			if(playerTotal == 1 || playerTotal == 8 || playerTotal == 9 || playerTotal == 0) {
				bankerTurn = false;
				lblEvent.setText("Banker Stands!");
			} else {
				dealBankerCard();
				bankerTotal += convertValue(bankerHand[2][0]);
				bankerTotal = bankerTotal % 10;
				bankerTurn = false;
				lblEvent.setText("Banker Hits!");
				setBankerCardImage();
			}
		}
		else if(bankerTotal == 5) {
			if(playerTotal == 4 || playerTotal == 5 || playerTotal == 6 || playerTotal == 7) {
				dealBankerCard();
				bankerTotal += convertValue(bankerHand[2][0]);
				bankerTotal = bankerTotal % 10;
				bankerTurn = false;
				lblEvent.setText("Banker Hits!");
				setBankerCardImage();
			} else {
				bankerTurn = false;
				lblEvent.setText("Banker Stands!");
			}
		}
		else if(bankerTotal == 6) {
			if(playerTotal == 6 || playerTotal == 7) {
				dealBankerCard();
				bankerTotal += convertValue(bankerHand[2][0]);
				bankerTotal = bankerTotal % 10;
				bankerTurn = false;
				lblEvent.setText("Banker Hits!");
				setBankerCardImage();
			} else {
				bankerTurn = false;
				lblEvent.setText("Banker Stands!");
			}
		} else {
			bankerTurn = false;
			lblEvent.setText("Banker Stands!");
		}
	}

	private void dealStartingHands(){

		Random rand = new Random();

		for(int i = 0; i < 2; i++){

			int suit = rand.nextInt(4);
			int value = rand.nextInt(13);

			if(used[value][suit] == false){
				playerHand[i][0] = value;
				playerHand[i][1] = suit;

				used[value][suit] = true;
			} else {
				while(used[value][suit] == true){
					suit = rand.nextInt(4);
					value = rand.nextInt(13);

					if(used[value][suit] == false){
						playerHand[i][0] = value;
						playerHand[i][1] = suit;

						used[value][suit] = true;
						break;
					}
				}
			}
		}

		for(int i = 0; i < 2; i++){

			int suit = rand.nextInt(4);
			int value = rand.nextInt(13);

			if(used[value][suit] == false){
				bankerHand[i][0] = value;
				bankerHand[i][1] = suit;

				used[value][suit] = true;
			} else {
				while(used[value][suit] == true){
					suit = rand.nextInt(4);
					value = rand.nextInt(13);

					if(used[value][suit] == false){
						bankerHand[i][0] = value;
						bankerHand[i][1] = suit;

						used[value][suit] = true;
						break;
					}
				}
			}
		}
	}

	private void dealPlayerCard(){

		Random rand = new Random();
		int suit = rand.nextInt(4);
		int value = rand.nextInt(13);

		if(used[value][suit] == false){
			playerHand[2][0] = value;
			playerHand[2][1] = suit;

			used[value][suit] = true;
		} else {
			while(used[value][suit] == true){
				suit = rand.nextInt(4);
				value = rand.nextInt(13);

				if(used[value][suit] == false){
					playerHand[2][0] = value;
					playerHand[2][1] = suit;

					used[value][suit] = true;
					break;
				}
			}
		}
	}

	private void dealBankerCard(){
		Random rand = new Random();

		int suit = rand.nextInt(4);
		int value = rand.nextInt(13);

		if(used[value][suit] == false){
			bankerHand[2][0] = value;
			bankerHand[2][1] = suit;

			used[value][suit] = true;
		} else {
			while(used[value][suit] == true){
				suit = rand.nextInt(4);
				value = rand.nextInt(13);

				if(used[value][suit] == false){
					bankerHand[2][0] = value;
					bankerHand[2][1] = suit;

					used[value][suit] = true;
					break;
				}
			}
		}
	}


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaccaratGUI window = new BaccaratGUI();
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
	public BaccaratGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Baccarat");
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("Insufficient Balance")) {
					textField.setText(null);
				}
				lblEvent.setText("");

				if(Integer.parseInt(textField.getText()) > CasinoData.balance) {
					textField.setText("Insufficient Balance");
				} else {
					startGame();
					playerBet += Integer.parseInt(textField.getText());
					CasinoData.balance -= Integer.parseInt(textField.getText());
					label.setText(Integer.toString(CasinoData.balance));
					textField.setText(null);
					hideBetButtons();
				}
			}
		});
		btnPlayer.setBounds(253, 357, 117, 29);
		frame.getContentPane().add(btnPlayer);
		
		btnBanker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("Insufficient Balance")) {
					textField.setText(null);
				}
				lblEvent.setText("");

				if(Integer.parseInt(textField.getText()) > CasinoData.balance) {
					textField.setText("Insufficient Balance");
				} else {
					startGame();
					bankerBet += Integer.parseInt(textField.getText());
					CasinoData.balance -= Integer.parseInt(textField.getText());
					label.setText(Integer.toString(CasinoData.balance));
					textField.setText(null);
					hideBetButtons();
				}
			}
		});
		btnBanker.setBounds(253, 316, 117, 29);
		frame.getContentPane().add(btnBanker);
		
		btnTie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals("Insufficient Balance")) {
					textField.setText(null);
				}
				lblEvent.setText("");

				if(Integer.parseInt(textField.getText()) > CasinoData.balance) {
					textField.setText("Insufficient Balance");
				} else {
					startGame();
					tieBet += Integer.parseInt(textField.getText());
					CasinoData.balance -= Integer.parseInt(textField.getText());
					label.setText(Integer.toString(CasinoData.balance));
					textField.setText(null);
					hideBetButtons();
				}
			}
		});

		btnTie.setBounds(253, 275, 117, 29);
		frame.getContentPane().add(btnTie);

		playerCard3.setBounds(415, 115, 60, 90);
		frame.getContentPane().add(playerCard3);	
		playerCard2.setBounds(400, 115, 60, 90);
		frame.getContentPane().add(playerCard2);		
		playerCard1.setBounds(385, 115, 60, 90);
		frame.getContentPane().add(playerCard1);

		bankerCard3.setBounds(600, 115, 60, 90);
		frame.getContentPane().add(bankerCard3);	
		bankerCard2.setBounds(585, 115, 60, 90);
		frame.getContentPane().add(bankerCard2);		
		bankerCard1.setBounds(570, 115, 60, 90);
		frame.getContentPane().add(bankerCard1);

		textField = new JTextField();
		textField.setBounds(229, 237, 167, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playerNatural && bankerNatural) {
					if(playerTotal > bankerTotal) {
						lblEvent.setText("Both Natural! Player Wins!");
						showBetButtons();
						CasinoData.balance += playerBet * 2;
						label.setText(Integer.toString(CasinoData.balance));
					}
					else if(bankerTotal > playerTotal) {
						lblEvent.setText("Both Natural! Banker Wins!");
						showBetButtons();
						CasinoData.balance += bankerBet * 2;
						label.setText(Integer.toString(CasinoData.balance));
					} else {
						lblEvent.setText("Both Natural! Tie!");
						showBetButtons();
						CasinoData.balance += tieBet * 8;
						label.setText(Integer.toString(CasinoData.balance));
					}
				}
				else if(playerNatural) {
					lblEvent.setText("Natural! Player Wins!");
					showBetButtons();
					CasinoData.balance += playerBet * 2;
					label.setText(Integer.toString(CasinoData.balance));
				}
				else if(bankerNatural) {
					lblEvent.setText("Natural! Banker Wins!");
					showBetButtons();
					CasinoData.balance += bankerBet * 2;
					label.setText(Integer.toString(CasinoData.balance));
				}
				else if(playerTurn) {
					playerMove();
				}
				else if(bankerTurn) {
					bankerMove();
				} else {
					if(playerTotal > bankerTotal) {
						lblEvent.setText("Player Wins!");
						showBetButtons();
						CasinoData.balance += playerBet * 2;
						label.setText(Integer.toString(CasinoData.balance));
					}
					else if(bankerTotal > playerTotal) {
						lblEvent.setText("Banker Wins!");
						showBetButtons();
						CasinoData.balance += bankerBet * 2;
						label.setText(Integer.toString(CasinoData.balance));
					} else {
						lblEvent.setText("Tie!");
						showBetButtons();
						CasinoData.balance += tieBet * 8;
						label.setText(Integer.toString(CasinoData.balance));
					}
				}
			}
		});
		btnNext.setVisible(false);
		btnNext.setBounds(444, 492, 117, 29);
		frame.getContentPane().add(btnNext);
		btnBackToCasino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CasinoMain.main(null);
				frame.dispose();
			}
		});
		btnBackToCasino.setBounds(819, 18, 117, 29);

		frame.getContentPane().add(btnBackToCasino);

		lblEvent.setHorizontalAlignment(SwingConstants.CENTER);
		lblEvent.setBounds(134, 196, 271, 19);
		lblEvent.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblEvent.setForeground(Color.WHITE);
		frame.getContentPane().add(lblEvent);
		
		lblMatheusbuck.setBounds(44, 48, 90, 43);
		lblMatheusbuck.setIcon(buck);
		frame.getContentPane().add(lblMatheusbuck);

		label.setBounds(146, 60, 100, 16);
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label.setForeground(Color.WHITE);
		frame.getContentPane().add(label);
		
		lblBackground.setBounds(0, 0, 1000, 800);
		lblBackground.setIcon(table);
		frame.getContentPane().add(lblBackground);

	}

	private void startGame() {
		for(int[] row : playerHand) {
			Arrays.fill(row, -1);
		}

		for(int[] row : bankerHand) {
			Arrays.fill(row, -1);
		}

		for(boolean[] row : used) {
			Arrays.fill(row, false);
		}

		playerTurn = true;
		playerNatural = false;
		bankerTurn = false;
		bankerNatural = false;

		playerTotal = 0;
		bankerTotal = 0;

		dealStartingHands();

		playerTotal += convertValue(playerHand[0][0]);
		playerTotal += convertValue(playerHand[1][0]);
		playerTotal = playerTotal % 10;

		bankerTotal += convertValue(bankerHand[0][0]);
		bankerTotal += convertValue(bankerHand[1][0]);
		bankerTotal = bankerTotal % 10;

		if(playerTotal == 8 || playerTotal == 9) {
			playerNatural = true;
		}
		if(bankerTotal == 8 || bankerTotal == 9) {
			bankerNatural = true;
		}
		
		setPlayerCardImage();
		setBankerCardImage();
	}

	private void hideBetButtons() {
		btnPlayer.setVisible(false);
		btnBanker.setVisible(false);
		btnTie.setVisible(false);
		textField.setVisible(false);
		btnNext.setVisible(true);
	}

	private void showBetButtons() {
		btnPlayer.setVisible(true);
		btnBanker.setVisible(true);
		btnTie.setVisible(true);
		textField.setVisible(true);
		btnNext.setVisible(false);
	}
}
