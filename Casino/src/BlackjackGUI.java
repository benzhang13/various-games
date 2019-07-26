import java.awt.Color;
import java.awt.EventQueue;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BlackjackGUI {

	private URL tableURL = getClass().getResource("/res/BlackjackTable.png");
	private URL buckURL = getClass().getResource("/res/MatheusBuck.png");

	private ImageIcon table = new ImageIcon(tableURL);
	private ImageIcon buck = new ImageIcon(buckURL);

	private JFrame frame;
	private boolean[][] used = new boolean[13][4];
	private int[][] dealerHand = new int[10][2];
	private int dealerTotal = 0;
	private int[] numDealerAces = new int[1];
	private int[][][] playerHand = new int[4][10][2];
	private int[] playerTotal = new int[4];
	private int[] numPlayerAces = new int[4];
	private int handNum = 0;
	private boolean alreadyHit = false;
	private int bet = 0;

	private int showingHandNum = 0;

	private JButton btnHit = new JButton("Hit");
	private JButton btnStand = new JButton("Stand");
	private JButton btnDouble = new JButton("Double");
	private JButton btnSplit = new JButton("Split");
	private JButton btnSurrender = new JButton("Surrender");
	private JButton btnDealNext = new JButton("Deal Next Hand");
	private JLabel lblError = new JLabel("");

	private JLabel playerCard1 = new JLabel();
	private JLabel playerCard2 = new JLabel();
	private JLabel playerCard3 = new JLabel();
	private JLabel playerCard4 = new JLabel();
	private JLabel playerCard5 = new JLabel();
	private JLabel playerCard6 = new JLabel();
	private JLabel playerCard7 = new JLabel();
	private JLabel playerCard8 = new JLabel();
	private JLabel playerCard9 = new JLabel();
	private JLabel playerCard10 = new JLabel();

	private JLabel dealerCard1 = new JLabel();
	private JLabel dealerCard2 = new JLabel();
	private JLabel dealerCard3 = new JLabel();
	private JLabel dealerCard4 = new JLabel();
	private JLabel dealerCard5 = new JLabel();
	private JLabel dealerCard6 = new JLabel();
	private JLabel dealerCard7 = new JLabel();
	private JLabel dealerCard8 = new JLabel();
	private JLabel dealerCard9 = new JLabel();
	private JLabel dealerCard10 = new JLabel();
	private JTextField textField;
	private final JButton btnHand1 = new JButton("Hand 1");
	private final JButton btnHand2 = new JButton("Hand 2");
	private final JButton btnHand3 = new JButton("Hand 3");
	private final JButton btnHand4 = new JButton("Hand 4");
	private final JLabel lblCurrentlyPlaying = new JLabel("Currently playing: Hand 1");
	private final JLabel label = new JLabel("");
	private final JLabel label_1 = new JLabel(Integer.toString(CasinoData.balance));
	private final JButton btnBackToCasino = new JButton("Back to Casino");

	private void dealerTurn(int[] playerTotal, int[] numPlayerAces, int originalBet) {
		dealerTotal += convertValue(dealerHand[0][0], numDealerAces, 0);
		dealerTotal += convertValue(dealerHand[1][0], numDealerAces, 0);

		setDealerCardImage();

		int nextCard = -1;

		while(true) {
			if(nextCard != -1){
				dealerTotal += convertValue(dealerHand[nextCard][0], numDealerAces, 0);
				setDealerCardImage();
			}

			if(numDealerAces[0] > 1){
				if(dealerTotal + numDealerAces[0] >= 17){
					dealerStand(dealerTotal + numDealerAces[0], playerTotal, numPlayerAces, originalBet);
					break;
				}
				else if(dealerTotal + 11 + numDealerAces[0] - 1 >= 17 && dealerTotal + 11 + numDealerAces[0] - 1 <= 21){
					dealerStand(dealerTotal + 11 + numDealerAces[0] - 1, playerTotal, numPlayerAces, originalBet);
					break;
				}
			}
			else if(numDealerAces[0] == 1){
				if(dealerTotal + 1 >= 17){
					dealerStand(dealerTotal + 1, playerTotal, numPlayerAces, originalBet);
					break;
				}
				else if(dealerTotal + 11 >= 17 && dealerTotal + 11 <= 21){
					dealerStand(dealerTotal + 11, playerTotal, numPlayerAces, originalBet);
					break;
				}
			} else {
				if(dealerTotal >= 17){
					dealerStand(dealerTotal, playerTotal, numPlayerAces, originalBet);
					break;
				}
			}

			for(int i = 0; i < 10; i++){
				if(dealerHand[i][0] == -1){
					nextCard = i;
					break;
				}
			}
			dealDealerCard(dealerHand, nextCard);
		}
	}

	private void dealerStand(int dealerFinal, int playerTotal[], int[] playerAces, int originalBet) {

		setDealerCardImage();

		if(dealerFinal > 21){
			lblError.setText("Dealer busts, you win!");
			hideButtons();
			textField.setVisible(true);
			btnDealNext.setVisible(true);
			CasinoData.balance += bet * 2;
			label_1.setText(Integer.toString(CasinoData.balance));
		} else {
			int[] maxValues = new int[4];
			int bestHand = 0;

			for(int i = 0; i < 4; i++){
				if(playerTotal[i] == 0 && playerAces[i] == 0){
					break;
				} else {
					int maxValue;
					if(playerAces[i] == 0){
						maxValue = playerTotal[i];
					} else {
						if(playerTotal[i] + 11 + playerAces[i] - 1 <= 21){
							maxValue = playerTotal[i] + 11 + playerAces[i] - 1;
						} else {
							maxValue = playerTotal[i] + playerAces[i];
						}
					}

					maxValues[i] = maxValue;
				}
			}
			for(int i = 0; i < 4; i++) {
				if(maxValues[i] > bestHand && maxValues[i] <= 21) {
					bestHand = maxValues[i];
				}
			}

			if(bestHand == 0) {
				lblError.setText("All your hand bust, you lose!");
				hideButtons();
				textField.setVisible(true);
				btnDealNext.setVisible(true);
			}
			else if(bestHand > dealerFinal) {
				lblError.setText("You win!");
				hideButtons();
				textField.setVisible(true);
				btnDealNext.setVisible(true);
				CasinoData.balance += bet * 2;
				label_1.setText(Integer.toString(CasinoData.balance));
			}
			else if(bestHand < dealerFinal) {
				lblError.setText("Dealer wins!");
				hideButtons();
				textField.setVisible(true);
				btnDealNext.setVisible(true);
			} else {
				lblError.setText("Tie!");
				hideButtons();
				textField.setVisible(true);
				btnDealNext.setVisible(true);
				CasinoData.balance += bet;
				label_1.setText(Integer.toString(CasinoData.balance));
			}
		}
	}

	private void setPlayerCardImage(int[][][] hand) {	
		clearPlayerCardIcons();
		int numCards = 0;

		for(int i = 0; i < hand[showingHandNum].length; i++) {
			if(hand[showingHandNum][i][0] == -1) {
				break;
			} else {
				numCards += 1;
			}
		}

		for(int i = 0; i < numCards; i++) {
			String path = "/res/" + translateValue(hand[showingHandNum][i][0]) + translateSuit(hand[showingHandNum][i][1]) + ".png";
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
			else if(playerCard4.getIcon() == null) {
				playerCard4.setIcon(cardIcon);
			}
			else if(playerCard5.getIcon() == null) {
				playerCard5.setIcon(cardIcon);
			}
			else if(playerCard6.getIcon() == null) {
				playerCard6.setIcon(cardIcon);
			}
			else if(playerCard7.getIcon() == null) {
				playerCard7.setIcon(cardIcon);
			}
			else if(playerCard8.getIcon() == null) {
				playerCard8.setIcon(cardIcon);
			}
			else if(playerCard9.getIcon() == null) {
				playerCard9.setIcon(cardIcon);
			} else {
				playerCard10.setIcon(cardIcon);
			}

		}

	}

	private void setDealerInitialCardImage() {
		clearDealerCardIcons();
		String path = "/res/" + translateValue(dealerHand[0][0]) + translateSuit(dealerHand[0][1]) + ".png";
		URL cardURL = getClass().getResource(path);
		ImageIcon cardIcon = new ImageIcon(cardURL);

		URL cardBackURL = getClass().getResource("/res/BackCard.png");
		ImageIcon cardBack = new ImageIcon(cardBackURL);

		dealerCard1.setIcon(cardIcon);
		dealerCard2.setIcon(cardBack);
	}

	private void setDealerCardImage() {	
		clearDealerCardIcons();
		int numCards = 0;

		for(int i = 0; i < used[showingHandNum].length; i++) {
			if(dealerHand[i][0] == -1) {
				break;
			} else {
				numCards += 1;
			}
		}

		for(int i = 0; i < numCards; i++) {
			String path = "/res/" + translateValue(dealerHand[i][0]) + translateSuit(dealerHand[i][1]) + ".png";
			URL cardURL = getClass().getResource(path);
			ImageIcon cardIcon = new ImageIcon(cardURL);

			if(dealerCard1.getIcon() == null) {
				dealerCard1.setIcon(cardIcon);
			}
			else if(dealerCard2.getIcon() == null) {
				dealerCard2.setIcon(cardIcon);
			}
			else if(dealerCard3.getIcon() == null) {
				dealerCard3.setIcon(cardIcon);
			}
			else if(dealerCard4.getIcon() == null) {
				dealerCard4.setIcon(cardIcon);
			}
			else if(dealerCard5.getIcon() == null) {
				dealerCard5.setIcon(cardIcon);
			}
			else if(dealerCard6.getIcon() == null) {
				dealerCard6.setIcon(cardIcon);
			}
			else if(dealerCard7.getIcon() == null) {
				dealerCard7.setIcon(cardIcon);
			}
			else if(dealerCard8.getIcon() == null) {
				dealerCard8.setIcon(cardIcon);
			}
			else if(dealerCard9.getIcon() == null) {
				dealerCard9.setIcon(cardIcon);
			} else {
				dealerCard10.setIcon(cardIcon);
			}

		}

	}

	private void clearPlayerCardIcons() {
		playerCard1.setIcon(null);
		playerCard2.setIcon(null);
		playerCard3.setIcon(null);
		playerCard4.setIcon(null);
		playerCard5.setIcon(null);
		playerCard6.setIcon(null);
		playerCard7.setIcon(null);
		playerCard8.setIcon(null);
		playerCard9.setIcon(null);
		playerCard10.setIcon(null);
	}

	private void clearDealerCardIcons() {
		dealerCard1.setIcon(null);
		dealerCard2.setIcon(null);
		dealerCard3.setIcon(null);
		dealerCard4.setIcon(null);
		dealerCard5.setIcon(null);
		dealerCard6.setIcon(null);
		dealerCard7.setIcon(null);
		dealerCard8.setIcon(null);
		dealerCard9.setIcon(null);
		dealerCard10.setIcon(null);
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

	private void dealStartingHands(int[][][] player, int[][] dealer){

		Random rand = new Random();

		for(int i = 0; i < 2; i++){

			int suit = rand.nextInt(4);
			int value = rand.nextInt(13);

			if(used[value][suit] == false){
				player[0][i][0] = value;
				player[0][i][1] = suit;

				used[value][suit] = true;
			} else {
				while(used[value][suit] == true){
					suit = rand.nextInt(4);
					value = rand.nextInt(13);

					if(used[value][suit] == false){
						player[0][i][0] = value;
						player[0][i][1] = suit;

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
				dealer[i][0] = value;
				dealer[i][1] = suit;

				used[value][suit] = true;
			} else {
				while(used[value][suit] == true){
					suit = rand.nextInt(4);
					value = rand.nextInt(13);

					if(used[value][suit] == false){
						dealer[i][0] = value;
						dealer[i][1] = suit;

						used[value][suit] = true;
						break;
					}
				}
			}
		}
	}

	private void dealPlayerCard(int[][][] hand, int handNum, int cardNum){

		Random rand = new Random();
		int suit = rand.nextInt(4);
		int value = rand.nextInt(13);

		if(used[value][suit] == false){
			hand[handNum][cardNum][0] = value;
			hand[handNum][cardNum][1] = suit;

			used[value][suit] = true;
		} else {
			while(used[value][suit] == true){
				suit = rand.nextInt(4);
				value = rand.nextInt(13);

				if(used[value][suit] == false){
					hand[handNum][cardNum][0] = value;
					hand[handNum][cardNum][1] = suit;

					used[value][suit] = true;
					break;
				}
			}
		}
	}

	private void dealDealerCard(int[][] hand, int cardNum){
		Random rand = new Random();

		int suit = rand.nextInt(4);
		int value = rand.nextInt(13);

		if(used[value][suit] == false){
			hand[cardNum][0] = value;
			hand[cardNum][1] = suit;

			used[value][suit] = true;
		} else {
			while(used[value][suit] == true){
				suit = rand.nextInt(4);
				value = rand.nextInt(13);

				if(used[value][suit] == false){
					hand[cardNum][0] = value;
					hand[cardNum][1] = suit;

					used[value][suit] = true;
					break;
				}
			}
		}
	}

	//ace has value 0, two has value 1, three has value 2, etc.
	private int convertValue(int value, int[] aces, int handNum){
		if(value < 10 && value != 0){
			return value + 1;
		}
		else if(value >= 10){
			return 10;
		} else {
			aces[handNum] += 1;
			return 0;
		}
	}

	private int removeValue(int value, int[] aces, int handNum){
		if(value < 10 && value != 0){
			return value + 1;
		}
		else if(value >= 10){
			return 10;
		} else {
			aces[handNum] -= 1;
			return 0;
		}
	}

	private int convertValueNoAdd(int value){
		if(value < 10 && value != 0){
			return value + 1;
		}
		else if(value >= 10){
			return 10;
		} else {
			return 0;
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlackjackGUI window = new BlackjackGUI();
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
	public BlackjackGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame("Blackjack");
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alreadyHit = true;

				int currentCard = -1;
				for(int i = 0; i < 10; i++){
					if(playerHand[handNum][i][0] == -1){
						currentCard = i;
						break;
					}
				}

				dealPlayerCard(playerHand, handNum, currentCard);
				setPlayerCardImage(playerHand);

				playerTotal[handNum] += convertValue(playerHand[handNum][currentCard][0], numPlayerAces, handNum);

				if(playerTotal[handNum] + numPlayerAces[handNum] > 21){
					if(handNum == 0) {
						btnHand1.setText("Busted!");
						btnHand1.setEnabled(false);
					}
					else if(handNum == 1) {
						btnHand2.setText("Busted!");
						btnHand2.setEnabled(false);
					}
					else if(handNum == 2) {
						btnHand3.setText("Busted!");
						btnHand3.setEnabled(false);
					}
					else if(handNum == 3) {
						btnHand4.setText("Busted!");
						btnHand4.setEnabled(false);
					}

					if(playerHand[handNum + 1][0][0] == -1){
						lblError.setText("You bust, you lose!");
						hideButtons();
						textField.setVisible(true);
						btnDealNext.setVisible(true);
					} else {

						ActionListener taskPerformer = new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								handNum += 1;
								showingHandNum += 1;
								setPlayerCardImage(playerHand);
								alreadyHit = false;
								lblCurrentlyPlaying.setText("Currently playing: Hand " + (handNum + 1));
							}
						};
						Timer timer = new Timer(100 ,taskPerformer);
						timer.setRepeats(false);
						timer.start();
					}
				}
			}
		});
		btnHit.setBounds(150, 700, 117, 29);
		frame.getContentPane().add(btnHit);

		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(handNum == 4){
					dealerTurn(playerTotal, numPlayerAces, bet);
				}
				else if(playerHand[handNum + 1][0][0] == -1){
					dealerTurn(playerTotal, numPlayerAces, bet);
				} else {
					handNum += 1;
					showingHandNum += 1;
					setPlayerCardImage(playerHand);
					alreadyHit = false;
					lblCurrentlyPlaying.setText("Currently playing: Hand " + handNum);
				}
			}
		});
		btnStand.setBounds(305, 700, 117, 29);
		frame.getContentPane().add(btnStand);

		btnDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(alreadyHit || playerHand[1][0][0] != -1){
					lblError.setText("Can not double");

					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							lblError.setText("");
						}
					};
					Timer timer = new Timer(2000 ,taskPerformer);
					timer.setRepeats(false);
					timer.start();

				} else {
					bet = bet * 2;
					CasinoData.balance -= bet / 2;
					label_1.setText(Integer.toString(CasinoData.balance));
					dealPlayerCard(playerHand, 0, 2);
					playerTotal[0] += convertValue(playerHand[0][2][0], numPlayerAces, 0);
					setPlayerCardImage(playerHand);

					if(playerTotal[handNum] + numPlayerAces[handNum] > 21){
						btnHand1.setText("Busted");
						ActionListener taskPerformer = new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								lblError.setText("You bust, you lose!");
								hideButtons();
								textField.setVisible(true);
								btnDealNext.setVisible(true);
							}
						};
						Timer timer = new Timer(2000 ,taskPerformer);
						timer.setRepeats(false);
						timer.start();
					} else {
						dealerTurn(playerTotal, numPlayerAces, bet);
					}
				}
			}
		});
		btnDouble.setBounds(460, 700, 117, 29);
		frame.getContentPane().add(btnDouble);

		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(convertValueNoAdd(playerHand[handNum][0][0]) == convertValueNoAdd(playerHand[handNum][1][0]) && playerHand[3][0][0] == -1 && alreadyHit == false){
					int nextOpenHand = -1;
					for(int i = 0; i < 4; i++){
						if(playerHand[i][0][0] == -1){
							nextOpenHand = i;
							break;
						}
					}

					playerTotal[handNum] -= removeValue(playerHand[handNum][1][0], numPlayerAces, handNum);
					playerHand[nextOpenHand][0][0] = playerHand[handNum][1][0];
					playerHand[nextOpenHand][0][1] = playerHand[handNum][1][1];
					playerHand[handNum][1][0] = -1;
					playerHand[handNum][1][1] = -1;

					playerTotal[nextOpenHand] += convertValue(playerHand[nextOpenHand][0][0], numPlayerAces, nextOpenHand);

					dealPlayerCard(playerHand, handNum, 1);
					dealPlayerCard(playerHand, nextOpenHand, 1);

					playerTotal[handNum] += convertValue(playerHand[handNum][1][0], numPlayerAces, handNum);
					playerTotal[nextOpenHand] += convertValue(playerHand[nextOpenHand][1][0], numPlayerAces, nextOpenHand);

					setPlayerCardImage(playerHand);
				} else {
					lblError.setText("Can not split");

					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							lblError.setText("");
						}
					};
					Timer timer = new Timer(2000 ,taskPerformer);
					timer.setRepeats(false);
					timer.start();
				}
			}
		});
		btnSplit.setBounds(615, 700, 117, 29);
		frame.getContentPane().add(btnSplit);

		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(alreadyHit == true || playerHand[1][0][0] != -1){
					lblError.setText("Can not surrender");

					ActionListener taskPerformer = new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							lblError.setText("");
						}
					};
					Timer timer = new Timer(2000 ,taskPerformer);
					timer.setRepeats(false);
					timer.start();
				} else {
					lblError.setText("You surrender!");
					hideButtons();
					textField.setVisible(true);
					btnDealNext.setVisible(true);
					CasinoData.balance += bet/2;
					label_1.setText(Integer.toString(CasinoData.balance));
				}
			}
		});
		btnSurrender.setBounds(770, 700, 117, 29);
		frame.getContentPane().add(btnSurrender);

		btnDealNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(textField.getText().equals("Insufficient Balance")) {
					textField.setText(null);
				}
				lblError.setText("");

				if(Integer.parseInt(textField.getText()) > CasinoData.balance) {
					textField.setText("Insufficient Balance");
				} else {
					startGame(Integer.parseInt(textField.getText()));
					textField.setText(null);
					btnDealNext.setVisible(false);
					textField.setVisible(false);
				}
			}
		});

		btnDealNext.setBounds(700, 100, 200, 80);
		frame.getContentPane().add(btnDealNext);

		//player's card icons
		playerCard10.setBounds(585, 421, 60, 90);
		frame.getContentPane().add(playerCard4);
		playerCard9.setBounds(570, 421, 60, 90);
		frame.getContentPane().add(playerCard9);
		playerCard8.setBounds(555, 421, 60, 90);
		frame.getContentPane().add(playerCard8);
		playerCard7.setBounds(540, 421, 60, 90);
		frame.getContentPane().add(playerCard7);
		playerCard6.setBounds(525, 421, 60, 90);
		frame.getContentPane().add(playerCard6);	
		playerCard5.setBounds(510, 421, 60, 90);
		frame.getContentPane().add(playerCard5);	
		playerCard4.setBounds(495, 421, 60, 90);
		frame.getContentPane().add(playerCard4);	
		playerCard3.setBounds(480, 421, 60, 90);
		frame.getContentPane().add(playerCard3);	
		playerCard2.setBounds(465, 421, 60, 90);
		frame.getContentPane().add(playerCard2);		
		playerCard1.setBounds(450, 421, 60, 90);
		frame.getContentPane().add(playerCard1);

		//dealer's card icons
		dealerCard10.setBounds(585, 240, 60, 90);
		frame.getContentPane().add(dealerCard4);
		dealerCard9.setBounds(570, 240, 60, 90);
		frame.getContentPane().add(dealerCard9);
		dealerCard8.setBounds(555, 240, 60, 90);
		frame.getContentPane().add(dealerCard8);
		dealerCard7.setBounds(540, 240, 60, 90);
		frame.getContentPane().add(dealerCard7);
		dealerCard6.setBounds(525, 240, 60, 90);
		frame.getContentPane().add(dealerCard6);	
		dealerCard5.setBounds(510, 240, 60, 90);
		frame.getContentPane().add(dealerCard5);	
		dealerCard4.setBounds(495, 240, 60, 90);
		frame.getContentPane().add(dealerCard4);	
		dealerCard3.setBounds(480, 240, 60, 90);
		frame.getContentPane().add(dealerCard3);	
		dealerCard2.setBounds(465, 240, 60, 90);
		frame.getContentPane().add(dealerCard2);		
		dealerCard1.setBounds(450, 240, 60, 90);
		frame.getContentPane().add(dealerCard1);

		textField = new JTextField();
		textField.setBounds(730, 180, 145, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnHand1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showingHandNum = 0;
				setPlayerCardImage(playerHand);
			}
		});
		btnHand1.setBounds(305, 418, 117, 29);
		frame.getContentPane().add(btnHand1);

		btnHand2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playerHand[1][0][0] != -1) {
					showingHandNum = 1;
					setPlayerCardImage(playerHand);
				}
			}
		});
		btnHand2.setBounds(305, 442, 117, 29);
		frame.getContentPane().add(btnHand2);

		btnHand3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playerHand[2][0][0] != -1) {
					showingHandNum = 2;
					setPlayerCardImage(playerHand);
				}
			}
		});
		btnHand3.setBounds(305, 466, 117, 29);
		frame.getContentPane().add(btnHand3);

		btnHand4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(playerHand[3][0][0] != -1) {
					showingHandNum = 3;
					setPlayerCardImage(playerHand);
				}
			}
		});
		btnHand4.setBounds(305, 490, 117, 29);
		frame.getContentPane().add(btnHand4);

		lblError.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblError.setForeground(Color.WHITE);
		lblError.setBounds(400, 650, 262, 41);
		frame.getContentPane().add(lblError);

		lblCurrentlyPlaying.setBounds(361, 531, 164, 16);
		lblCurrentlyPlaying.setForeground(Color.WHITE);
		frame.getContentPane().add(lblCurrentlyPlaying);

		label.setBounds(40, 26, 90, 43);
		label.setIcon(buck);
		frame.getContentPane().add(label);

		label_1.setBounds(150, 40, 61, 16);
		label_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		label_1.setForeground(Color.WHITE);
		frame.getContentPane().add(label_1);
		btnBackToCasino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CasinoMain.main(null);
				frame.dispose();
			}
		});
		
		btnBackToCasino.setBounds(827, 16, 117, 29);
		frame.getContentPane().add(btnBackToCasino);

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, 1000, 788);
		lblBackground.setIcon(table);
		frame.getContentPane().add(lblBackground);
		

		hideButtons();
	}

	private void startGame(int originalBet) {

		showButtons();
		CasinoData.balance -= originalBet;
		bet = originalBet;
		label_1.setText(Integer.toString(CasinoData.balance));

		btnHand1.setText("Hand 1");
		btnHand2.setText("Hand 2");
		btnHand3.setText("Hand 3");
		btnHand4.setText("Hand 4");

		btnHand1.setEnabled(true);
		btnHand2.setEnabled(true);
		btnHand3.setEnabled(true);
		btnHand4.setEnabled(true);

		handNum = 0;
		alreadyHit = false;
		showingHandNum = 0;
		
		lblCurrentlyPlaying.setText("Currently playing: Hand 1");

		dealerTotal = 0;
		Arrays.fill(numDealerAces, 0);
		Arrays.fill(numDealerAces, 0);
		Arrays.fill(playerTotal, 0);

		for (boolean[] row : used) {
			Arrays.fill(row, false);
		}

		for(int[] row : dealerHand){
			Arrays.fill(row, -1);
		}

		for(int[][] pog : playerHand){
			for(int[] row : pog){
				Arrays.fill(row, -1);
			}
		}

		dealStartingHands(playerHand, dealerHand);
		setPlayerCardImage(playerHand);
		setDealerInitialCardImage();

		playerTotal[0] += convertValue(playerHand[0][0][0], numPlayerAces, handNum);
		playerTotal[0] += convertValue(playerHand[0][1][0], numPlayerAces, handNum);
	}

	private void hideButtons() {
		btnHit.setVisible(false);
		btnStand.setVisible(false);
		btnDouble.setVisible(false);
		btnSplit.setVisible(false);
		btnSurrender.setVisible(false);
		btnHand1.setVisible(false);
		btnHand2.setVisible(false);
		btnHand3.setVisible(false);
		btnHand4.setVisible(false);
		lblCurrentlyPlaying.setVisible(false);
	}

	private void showButtons() {
		btnHit.setVisible(true);
		btnStand.setVisible(true);
		btnDouble.setVisible(true);
		btnSplit.setVisible(true);
		btnSurrender.setVisible(true);
		btnHand1.setVisible(true);
		btnHand2.setVisible(true);
		btnHand3.setVisible(true);
		btnHand4.setVisible(true);
		lblCurrentlyPlaying.setVisible(true);
	}
}
