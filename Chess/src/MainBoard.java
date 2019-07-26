//Ben Zhang
//ICS 4U
//Summitive Assignment
//Main Chess Board Screen

import java.awt.EventQueue;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.awt.event.ActionEvent;

public class MainBoard {

	//why can the rook jump over pieces when its supposed to be mate???

	//EDIT
	//for(int i = row + 1; i < 8; i++) {

	//String piece = piecesCopy[i][col];
	//piecesCopy[i][col] = "wr";
	//piecesCopy[row][col] = "ee";

	//boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

	//piecesCopy[i][col] = piece;
	//piecesCopy[row][col] = "wr";

	//if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
	//	if(chessData.pieces[i][col].charAt(0) == 'e') {
	//		showLabel(i,col,true);
	//	}
	//	else if(chessData.pieces[i][col].charAt(0) == 'b') {
	//		showLabel(i,col,false);
	//		break;
	//	} else {
	//		break;
	//	}
	//}
	//}
	//THIS WAS THE PREVIOUS CODE. IF THE KING IS IN CHECK, THEN THE PIECE NEVER REACHES THE THIRD BREAK STATEMENT, THEREFORE ALLOWING THE PIECE TO "JUMP OVER" OTHER PIECES
	//FIX: ADD AN ELSE STATEMENT AFTER CHECKING IF THE KING WOULD BE IN CHECK, AND IT CHECKS IF THERE IS A PIECE THERE, AND IF THERE IS, BREAK THE LOOP

	//Throughout this code, arrays that hold piece placement data will have string values depending on what the piece is.
	//If a square is empty, the string will be "ee". If it is not empty, the character at index 0 will signify the piece's colour, and the character at index 1 will indicate the piece type
	//w = white, b = black, k = king, q = queen, r = rook, n = knight, b = bishop, p = pawn
	//i.e. a black king will have value "bk" and a white knight will have value "wn"
	static JFrame frame;

	//creating images and arrays
	private JButton[][] squares = new JButton[8][8];
	private JLabel[][] showMoves = new JLabel[8][8];

	//private URL emptyURL = getClass().getClassLoader().getResource("/res/EmptyHighlighter.png");
	//private ImageIcon emptyHighlight = new ImageIcon(emptyURL);
	//private URL occupiedURL = getClass().getClassLoader().getResource("/res/OccupiedHighlighter.png");
	//private ImageIcon occupiedHighlight = new ImageIcon(occupiedURL);
	private ImageIcon emptyHighlight = new ImageIcon(getClass().getResource("/res/EmptyHighlighter.png"));
	private ImageIcon occupiedHighlight = new ImageIcon(getClass().getResource("/res/OccupiedHighlighter.png"));

	//regular soundpack
	private URL moveURL = getClass().getResource("/res/move.wav");
	private URL captureURL = getClass().getResource("/res/capture.wav");
	private URL castleURL = getClass().getResource("/res/castle.wav");
	private URL gameEndedURL = getClass().getResource("/res/gameEnded.wav");

	//agadmator soundpack
	private URL agadBlackURL = getClass().getResource("/res/Black.wav");
	private URL agadWhiteURL = getClass().getResource("/res/White.wav");
	private URL agadHelloURL = getClass().getResource("/res/Hello.wav");
	private URL agadBlackMatedURL = getClass().getResource("/res/BlackMated.wav");
	private URL agadBlackMated2URL = getClass().getResource("/res/BlackMated2.wav");
	private URL agadBlackMated3URL = getClass().getResource("/res/BlackMated3.wav");
	private URL agadWhiteMatedURL = getClass().getResource("/res/WhiteMated.wav");
	private URL agadWhiteMated2URL = getClass().getResource("/res/WhiteMated2.wav");
	private URL agadWhiteMated3URL = getClass().getResource("/res/WhiteMated3.wav");
	private URL agadStalemateURL = getClass().getResource("/res/Stalemate.wav");
	private URL agadCaptures1URL = getClass().getResource("/res/Captures1.wav");
	private URL agadCaptures2URL = getClass().getResource("/res/Captures2.wav");
	private URL agadCaptures3URL = getClass().getResource("/res/Captures3.wav");
	private URL agadCastlesURL = getClass().getResource("/res/Castles.wav");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainBoard window = new MainBoard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public MainBoard() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	private void initialize() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		frame = new JFrame();
		frame.setBounds(0, 0, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//opens turnwindow and sets the chessboard
		TurnWindow.main(null);
		setBoard();
		
		//resetting the game
		chessData.whiteTurn = true;
		chessData.blackCanCastleKingSide = true;
		chessData.blackCanCastleQueenSide = true;
		chessData.whiteCanCastleKingSide = true;
		chessData.whiteCanCastleQueenSide = true;
		chessData.blackKingRow = 0;
		chessData.blackKingCol = 4;
		chessData.whiteKingRow = 7;
		chessData.whiteKingCol = 4;
		chessData.draw = false;

		//hello everyone!
		if(chessData.agadMode) {
			Clip agadHello = AudioSystem.getClip();
			agadHello.open(AudioSystem.getAudioInputStream(agadHelloURL));
			agadHello.start();
		}
		
		//listener for when user clicks a square on the chess board
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(TurnWindow.recordButton.isVisible() == false) {
					//determining which button in the array was clicked
					JButton button = (JButton) e.getSource();
					int row = (int) button.getClientProperty("row");
					int col = (int) button.getClientProperty("col");

					//checks if a piece has already been clicked
					if(!chessData.pieceClicked) {
						if(chessData.pieces[row][col].charAt(0) == 'w' && chessData.whiteTurn) {
							chessData.whatPiece = chessData.pieces[row][col];
							chessData.originalRow = row;
							chessData.originalCol = col;
							showLegalMoves(row, col, 'w');
						}
						else if(chessData.pieces[row][col].charAt(0) == 'b' && !chessData.whiteTurn) {
							chessData.whatPiece = chessData.pieces[row][col];
							chessData.originalRow = row;
							chessData.originalCol = col;
							showLegalMoves(row, col, 'b');
						}
					} else {
						try {
							try {
								movePiece(row, col, chessData.whatPiece);
							} catch (UnsupportedAudioFileException e1) {
								e1.printStackTrace();
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (LineUnavailableException e1) {
								e1.printStackTrace();
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		};

		//constructs each button in the 2d array of buttons
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {

				showMoves[i][k] = new JLabel();
				showMoves[i][k].setIcon(occupiedHighlight);
				showMoves[i][k].setBounds(k * 100, i * 100, 100, 100);
				showMoves[i][k].setVisible(false);
				frame.getContentPane().add(showMoves[i][k]);

				squares[i][k] = new JButton();
				squares[i][k].setBounds(k * 100, i * 100, 100, 100);
				squares[i][k].addActionListener(listener);
				squares[i][k].putClientProperty("row", i);
				squares[i][k].putClientProperty("col", k);
				frame.getContentPane().add(squares[i][k]);
				updateSquare(i, k);
			}
		}
	}

	//fills pieces string array based on what mode was chosen
	//also calls update square to apply the changes in pieces array to gui
	private void setBoard() {

		for(String row[]:chessData.pieces) {
			Arrays.fill(row, "ee");
		}

		if(chessData.mode.equals("classic")) {
			chessData.pieces[0][0] = "br";
			chessData.pieces[0][1] = "bn";
			chessData.pieces[0][2] = "bb";
			chessData.pieces[0][3] = "bq";
			chessData.pieces[0][4] = "bk";
			chessData.pieces[0][5] = "bb";
			chessData.pieces[0][6] = "bn";
			chessData.pieces[0][7] = "br";
			for(int i = 0; i < 8; i++) {
				chessData.pieces[1][i] = "bp";
			}

			chessData.pieces[7][0] = "wr";
			chessData.pieces[7][1] = "wn";
			chessData.pieces[7][2] = "wb";
			chessData.pieces[7][3] = "wq";
			chessData.pieces[7][4] = "wk";
			chessData.pieces[7][5] = "wb";
			chessData.pieces[7][6] = "wn";
			chessData.pieces[7][7] = "wr";
			for(int i = 0; i < 8; i++) {
				chessData.pieces[6][i] = "wp";
			}
		}
		else if(chessData.mode.equals("random")) {
			Random rand = new Random();

			for(int i = 0; i < 8; i++) {
				chessData.pieces[1][i] = "bp";
			}
			for(int i = 0; i < 8; i++) {
				chessData.pieces[6][i] = "wp";
			}

			int[] startCols = new int[8];

			for(int i = 0; i < 8; i++) {
				startCols[i] = rand.nextInt(8);

				for(int k = 0; k < i; k++) {
					if(startCols[k] == startCols[i]) {
						i -= 1;
					}
				}
			}
			chessData.pieces[7][startCols[0]] = "wr";
			chessData.pieces[7][startCols[1]] = "wr";
			chessData.pieces[7][startCols[2]] = "wn";
			chessData.pieces[7][startCols[3]] = "wn";
			chessData.pieces[7][startCols[4]] = "wb";
			chessData.pieces[7][startCols[5]] = "wb";
			chessData.pieces[7][startCols[6]] = "wq";
			chessData.pieces[7][startCols[7]] = "wk";

			chessData.pieces[0][startCols[0]] = "br";
			chessData.pieces[0][startCols[1]] = "br";
			chessData.pieces[0][startCols[2]] = "bn";
			chessData.pieces[0][startCols[3]] = "bn";
			chessData.pieces[0][startCols[4]] = "bb";
			chessData.pieces[0][startCols[5]] = "bb";
			chessData.pieces[0][startCols[6]] = "bq";
			chessData.pieces[0][startCols[7]] = "bk";

		}
		else if(chessData.mode.equals("bad")) {
			Random rand = new Random();

			int[] pieceNums = new int[8];
			String[] piecePlace = new String[8];

			for(int i = 0; i < 8; i++) {
				pieceNums[i] = rand.nextInt(4);
			}

			int kingSquare = rand.nextInt(8);

			pieceNums[kingSquare] = 4;

			for(int i = 0; i < 8; i++) {
				switch(pieceNums[i]) {
				case 0:
					piecePlace[i] = "r";
					break;
				case 1:
					piecePlace[i] = "n";
					break;
				case 2:
					piecePlace[i] = "b";
					break;
				case 3:
					piecePlace[i] = "q";
					break;
				case 4:
					piecePlace[i] = "k";
					break;
				}
			}

			for(int i = 0; i < 8; i++) {
				chessData.pieces[1][i] = "bp";
				chessData.pieces[6][i] = "wp";
				chessData.pieces[0][i] = "b" + piecePlace[i];
				chessData.pieces[7][i] = "w" + piecePlace[i];
			}
		}
	}

	//changes the icon of a button based on the value of that index in the pieces array
	private void updateSquare(int row, int col) {
		if(chessData.pieces[row][col].charAt(0) == 'e') {
			if(chessData.darkOrLight[row][col]) {
				squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/EmptyDarkSquare.jpg")));
			} else {
				squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/EmptyLightSquare.jpg")));
			}
		}
		else if(chessData.pieces[row][col].charAt(0) == 'b') {
			if(chessData.pieces[row][col].charAt(1) == 'r') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackRookDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackRookLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackKnightDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackKnightLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackBishopDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackBishopLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackQueenDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackQueenLightSquare.png")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'k') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackKingDarkSquare.png")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackKingLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackPawnDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(getClass().getResource("/res/BlackPawnLightSquare.jpg")));
				}
			}
		}
		else if(chessData.pieces[row][col].charAt(0) == 'w') {
			if(chessData.pieces[row][col].charAt(1) == 'r') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteRookDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteRookLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteKnightDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteKnightLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteBishopDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteBishopLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteQueenDarkSquare.png")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteQueenLightSquare.jpg")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'k') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteKingDarkSquare.png")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhiteKingLightSquare.png")));
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {
				if(chessData.darkOrLight[row][col]) {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhitePawnDarkSquare.jpg")));
				} else {
					squares[row][col].setIcon(new ImageIcon(MainBoard.class.getResource("/res/WhitePawnLightSquare.jpg")));
				}
			}
		}
	}

	//displays the legal moves of a piece to the user when clicked
	//each piece has relatively similar code for this section so I will only comment the part for the king, however, the general layout is: create a copy of "pieces", check if the king will be threatened if the tested move is made, displays the appropriate label if the move will be legal
	private void showLegalMoves(int row, int col, char player) {

		chessData.pieceClicked = true;

		if(player == 'w') {

			//king moves to a single adjacent square
			if(chessData.pieces[row][col].charAt(1) == 'k') {

				int moveRow;
				int moveCol;
				int rowCap;
				int colCap;

				//checks if king is on the edge of the board, avoids IndexOutOfBounds exception
				if(row - 1 < 0) {
					moveRow = 0;
				} else {
					moveRow = row - 1;
				}
				if(col - 1 < 0) {
					moveCol = 0;
				} else {
					moveCol = col - 1;
				}

				if(row + 1 > 7) {
					rowCap = 7;
				} else {
					rowCap = row + 1;
				}
				if(col + 1 > 7) {
					colCap = 7;
				} else {
					colCap = col + 1;
				}

				//creates a copy of the array pieces
				String[][] piecesCopy = new String[8][8];
				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				//checks each possible square the king can move to if it will be legal
				for(int i = moveRow; i <= rowCap; i++) {
					for(int k = moveCol; k <= colCap; k++) {
						if(moveRow != row && moveCol != col) {
							String piece = piecesCopy[i][k];

							//assumes that the king has moved to the tested square
							piecesCopy[i][k] = "wk";
							piecesCopy[row][col] = "ee";

							//creates a array of which squares are threatened by the black pieces
							boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

							//moves the king back to the original square
							piecesCopy[i][k] = piece;
							piecesCopy[row][col] = "wk";

							//determines if the move is legal
							if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'e') {
								showLabel(i,k,true);
							}
							else if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'b') {
								showLabel(i,k,false);
							}
						}
					}
				}

				//castling for the king
				if(chessData.whiteCanCastleKingSide && chessData.pieces[7][5].charAt(0) == 'e' && chessData.pieces[7][6].charAt(0) == 'e' && !chessData.blackThreats[7][5] && !chessData.blackThreats[7][6] && !chessData.blackThreats[chessData.whiteKingRow][chessData.whiteKingCol]) {
					showLabel(7,6,true);
				}
				if(chessData.whiteCanCastleQueenSide && chessData.pieces[7][2].charAt(0) == 'e' && chessData.pieces[7][3].charAt(0) == 'e' && !chessData.blackThreats[7][2] && !chessData.blackThreats[7][3] && !chessData.blackThreats[7][4] && !chessData.blackThreats[chessData.whiteKingRow][chessData.whiteKingCol]) {
					showLabel(7,2,true);
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				if(piecesCopy[row - 1][col].charAt(0) == 'e') {

					piecesCopy[row][col] = "ee";
					piecesCopy[row - 1][col] = "wp";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					if(row == 6) {
						piecesCopy[row][col] = "ee";
						piecesCopy[row-2][col] = "wp";
					}
					boolean[][] threatCopy2 = chessData.potentialBlackThreats(piecesCopy);

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol] && chessData.pieces[row-1][col].charAt(0) == 'e') {
						showLabel(row-1,col,true);
					}
					if(row == 6 && chessData.pieces[4][col].charAt(0) == 'e' && chessData.pieces[row-1][col].charAt(0) == 'e' && !threatCopy2[chessData.whiteKingRow][chessData.whiteKingCol]) {
						showLabel(row-2,col,true);
					}


					piecesCopy[row][col] = "wp";
					piecesCopy[row - 1][col] = "ee";
				}
				if(col - 1 >= 0) {
					if(chessData.pieces[row-1][col-1].charAt(0) == 'b') {
						String pieceAt = chessData.pieces[row-1][col-1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row-1][col-1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							showLabel(row-1,col-1,false);
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row - 1][col-1] = pieceAt;
					}
					if(chessData.enPassant[row][col-1]) {

						String piece = piecesCopy[row][col-1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col-1] = "ee";
						piecesCopy[row-1][col-1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							showLabel(row-1,col-1,false);
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row][col-1] = piece;
						piecesCopy[row-1][col-1] = "ee";

					}
				}
				if(col + 1 < 8) {
					if(chessData.pieces[row-1][col+1].charAt(0) == 'b') {
						String pieceAt = chessData.pieces[row-1][col+1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row-1][col+1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							showLabel(row-1,col+1,false);
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row - 1][col+1] = pieceAt;
					}

					//checking if en passant is possible
					if(chessData.enPassant[row][col+1]) {

						String piece = piecesCopy[row][col+1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col+1] = "ee";
						piecesCopy[row-1][col+1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							showLabel(row-1,col+1,false);
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row][col+1] = piece;
						piecesCopy[row-1][col+1] = "ee";
					}
				}

			}
			else if(chessData.pieces[row][col].charAt(1) == 'r') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
						//THE FIX TO THE WORST BUG EVER
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
					//THE FIX TO THE WORST BUG EVER
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				piecesCopy[row][col] = "ee";

				if(row - 2 >= 0) {
					if(col - 1 >= 0) {

						String piece = chessData.pieces[row-2][col-1];
						piecesCopy[row-2][col-1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-2][col-1].charAt(0) == 'e') {
								showLabel(row-2,col-1,true);
							}
							else if(chessData.pieces[row-2][col-1].charAt(0) == 'b') {
								showLabel(row-2,col-1,false);
							}
						}

						piecesCopy[row-2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row-2][col+1];
						piecesCopy[row-2][col+1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-2][col+1].charAt(0) == 'e') {
								showLabel(row-2,col+1,true);
							}
							else if(chessData.pieces[row-2][col+1].charAt(0) == 'b') {
								showLabel(row-2,col+1,false);
							}
						}

						piecesCopy[row-2][col+1] = piece;
					}
				}
				if(row + 2 < 8) {
					if(col - 1 >= 0) {
						String piece = chessData.pieces[row+2][col-1];
						piecesCopy[row+2][col-1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+2][col-1].charAt(0) == 'e') {
								showLabel(row+2,col-1,true);
							}
							else if(chessData.pieces[row+2][col-1].charAt(0) == 'b') {
								showLabel(row+2,col-1,false);
							}
						}

						piecesCopy[row+2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row+2][col+1];
						piecesCopy[row+2][col+1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+2][col+1].charAt(0) == 'e') {
								showLabel(row+2,col+1,true);
							}
							else if(chessData.pieces[row+2][col+1].charAt(0) == 'b') {
								showLabel(row+2,col+1,false);
							}
						}

						piecesCopy[row+2][col+1] = piece;
					}
				}
				if(row - 1 >= 0) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row-1][col-2];
						piecesCopy[row-1][col-2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col-2].charAt(0) == 'e') {
								showLabel(row-1,col-2,true);
							}
							else if(chessData.pieces[row-1][col-2].charAt(0) == 'b') {
								showLabel(row-1,col-2,false);
							}
						}

						piecesCopy[row-1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row-1][col+2];
						piecesCopy[row-1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col+2].charAt(0) == 'e') {
								showLabel(row-1,col+2,true);
							}
							else if(chessData.pieces[row-1][col+2].charAt(0) == 'b') {
								showLabel(row-1,col+2,false);
							}
						}

						piecesCopy[row-1][col+2] = piece;
					}
				}
				if(row + 1 < 8) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row+1][col-2];
						piecesCopy[row+1][col-2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+1][col-2].charAt(0) == 'e') {
								showLabel(row+1,col-2,true);
							}
							else if(chessData.pieces[row+1][col-2].charAt(0) == 'b') {
								showLabel(row+1,col-2,false);
							}
						}

						piecesCopy[row+1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row+1][col+2];
						piecesCopy[row+1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+1][col+2].charAt(0) == 'e') {
								showLabel(row+1,col+2,true);
							}
							else if(chessData.pieces[row+1][col+2].charAt(0) == 'b') {
								showLabel(row+1,col+2,false);
							}
						}

						piecesCopy[row+1][col+2] = piece;
					}
				}

			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				String[][] piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
		}

		//if a black piece was clicked
		else if(player == 'b') {

			if(chessData.pieces[row][col].charAt(1) == 'k') {
				int moveRow;
				int moveCol;
				int rowCap;
				int colCap;

				if(row - 1 < 0) {
					moveRow = 0;
				} else {
					moveRow = row - 1;
				}
				if(col - 1 < 0) {
					moveCol = 0;
				} else {
					moveCol = col - 1;
				}

				if(row + 1 > 7) {
					rowCap = 7;
				} else {
					rowCap = row + 1;
				}
				if(col + 1 > 7) {
					colCap = 7;
				} else {
					colCap = col + 1;
				}

				String[][] piecesCopy = new String[8][8];
				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = moveRow; i <= rowCap; i++) {
					for(int k = moveCol; k <= colCap; k++) {
						if(!(moveRow == row && moveCol == col)) {
							String piece = piecesCopy[i][k];

							piecesCopy[i][k] = "bk";
							piecesCopy[row][col] = "ee";

							boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

							piecesCopy[i][k] = piece;
							piecesCopy[row][col] = "bk";

							if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'e') {
								showLabel(i,k,true);
							}
							else if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'w') {
								showLabel(i,k,false);
							}
						}
					}
				}

				if(chessData.blackCanCastleKingSide && chessData.pieces[0][5].charAt(0) == 'e' && chessData.pieces[0][6].charAt(0) == 'e' && !chessData.whiteThreats[0][5] && !chessData.whiteThreats[0][6] && !chessData.whiteThreats[chessData.blackKingRow][chessData.blackKingCol]) {
					showLabel(0,6,true);
				}
				if(chessData.blackCanCastleQueenSide && chessData.pieces[0][2].charAt(0) == 'e' && chessData.pieces[0][3].charAt(0) == 'e' && !chessData.whiteThreats[0][2] && !chessData.whiteThreats[0][3] && !chessData.whiteThreats[0][4] && !chessData.whiteThreats[chessData.blackKingRow][chessData.blackKingCol]) {
					showLabel(0,2,true);
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {

				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				if(piecesCopy[row + 1][col].charAt(0) == 'e') {

					piecesCopy[row][col] = "ee";
					piecesCopy[row + 1][col] = "bp";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					if(row == 1) {
						piecesCopy[row][col] = "ee";
						piecesCopy[row+2][col] = "bp";
					}

					boolean[][] threatCopy2 = chessData.potentialWhiteThreats(piecesCopy);

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol] && chessData.pieces[row+1][col].charAt(0) == 'e') {
						showLabel(row+1,col,true);
					}
					if(row == 1 && chessData.pieces[3][col].charAt(0) == 'e' && chessData.pieces[row+1][col].charAt(0) == 'e' && !threatCopy2[chessData.blackKingRow][chessData.blackKingCol]) {
						showLabel(row+2,col,true);
					}

					piecesCopy[row][col] = "bp";
					piecesCopy[row + 1][col] = "ee";
				}
				if(col - 1 >= 0) {
					if(chessData.pieces[row+1][col-1].charAt(0) == 'w') {
						String pieceAt = chessData.pieces[row+1][col-1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row+1][col-1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							showLabel(row+1,col-1,false);
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row + 1][col-1] = pieceAt;
					}
					if(chessData.enPassant[row][col-1]) {

						String piece = piecesCopy[row][col-1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col-1] = "ee";
						piecesCopy[row+1][col-1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							showLabel(row+1,col-1,false);
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row][col-1] = piece;
						piecesCopy[row+1][col-1] = "ee";
					}
				}
				if(col + 1 < 8) {
					if(chessData.pieces[row+1][col+1].charAt(0) == 'w') {
						String pieceAt = chessData.pieces[row+1][col+1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row+1][col+1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							showLabel(row+1,col+1,false);
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row + 1][col+1] = pieceAt;
					}
					if(chessData.enPassant[row][col+1]) {

						String piece = piecesCopy[row][col+1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col+1] = "ee";
						piecesCopy[row+1][col+1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							showLabel(row+1,col+1,false);
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row][col+1] = piece;
						piecesCopy[row+1][col+1] = "ee";
					}
				}

			}
			else if(chessData.pieces[row][col].charAt(1) == 'r') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				piecesCopy[row][col] = "ee";

				if(row - 2 >= 0) {
					if(col - 1 >= 0) {

						String piece = chessData.pieces[row-2][col-1];
						piecesCopy[row-2][col-1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-2][col-1].charAt(0) == 'e') {
								showLabel(row-2,col-1,true);
							}
							else if(chessData.pieces[row-2][col-1].charAt(0) == 'w') {
								showLabel(row-2,col-1,false);
							}
						}

						piecesCopy[row-2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row-2][col+1];
						piecesCopy[row-2][col+1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-2][col+1].charAt(0) == 'e') {
								showLabel(row-2,col+1,true);
							}
							else if(chessData.pieces[row-2][col+1].charAt(0) == 'w') {
								showLabel(row-2,col+1,false);
							}
						}

						piecesCopy[row-2][col+1] = piece;
					}
				}
				if(row + 2 < 8) {
					if(col - 1 >= 0) {
						String piece = chessData.pieces[row+2][col-1];
						piecesCopy[row+2][col-1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+2][col-1].charAt(0) == 'e') {
								showLabel(row+2,col-1,true);
							}
							else if(chessData.pieces[row+2][col-1].charAt(0) == 'w') {
								showLabel(row+2,col-1,false);
							}
						}

						piecesCopy[row+2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row+2][col+1];
						piecesCopy[row+2][col+1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+2][col+1].charAt(0) == 'e') {
								showLabel(row+2,col+1,true);
							}
							else if(chessData.pieces[row+2][col+1].charAt(0) == 'w') {
								showLabel(row+2,col+1,false);
							}
						}

						piecesCopy[row+2][col+1] = piece;
					}
				}
				if(row - 1 >= 0) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row-1][col-2];
						piecesCopy[row-1][col-2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-1][col-2].charAt(0) == 'e') {
								showLabel(row-1,col-2,true);
							}
							else if(chessData.pieces[row-1][col-2].charAt(0) == 'w') {
								showLabel(row-1,col-2,false);
							}
						}

						piecesCopy[row-1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row-1][col+2];
						piecesCopy[row-1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col+2].charAt(0) == 'e') {
								showLabel(row-1,col+2,true);
							}
							else if(chessData.pieces[row-1][col+2].charAt(0) == 'w') {
								showLabel(row-1,col+2,false);
							}
						}

						piecesCopy[row-1][col+2] = piece;
					}
				}
				if(row + 1 < 8) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row+1][col-2];
						piecesCopy[row+1][col-2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+1][col-2].charAt(0) == 'e') {
								showLabel(row+1,col-2,true);
							}
							else if(chessData.pieces[row+1][col-2].charAt(0) == 'w') {
								showLabel(row+1,col-2,false);
							}
						}

						piecesCopy[row+1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row+1][col+2];
						piecesCopy[row+1][col+2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+1][col+2].charAt(0) == 'e') {
								showLabel(row+1,col+2,true);
							}
							else if(chessData.pieces[row+1][col+2].charAt(0) == 'w') {
								showLabel(row+1,col+2,false);
							}
						}

						piecesCopy[row+1][col+2] = piece;
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				String[][] piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							showLabel(i,col,true);
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							showLabel(i,col,false);
							break;
						} else {
							break;
						}
					}
					else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							showLabel(row,i,true);
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							showLabel(row,i,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							showLabel(moveRow,moveCol,true);
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							showLabel(moveRow,moveCol,false);
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
		}
	}

	//moves the piece to the clicked square
	private void movePiece(int row, int col, String whatPiece) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {

		chessData.pieceClicked = false;

		//if the move is legal
		if(showMoves[row][col].isVisible()) {

			//resets array for en passant legality check
			for(boolean[] rows:chessData.enPassant) {
				Arrays.fill(rows, false);
			}

			//handles castling
			if(whatPiece.equals("wk") && col == chessData.originalCol - 2) {
				chessData.pieces[7][0] = "ee";
				chessData.pieces[7][3] = "wr";
				chessData.pieces[7][2] = "wk";
				chessData.pieces[7][4] = "ee";

				chessData.whiteKingRow = 7;
				chessData.whiteKingCol = 2;
				chessData.whiteCanCastleKingSide = false;
				chessData.whiteCanCastleQueenSide = false;

				updateSquare(7,0);
				updateSquare(7,2);
				updateSquare(7,3);
				updateSquare(7,4);

				//plays castle sound
				if(!chessData.agadMode) {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(castleURL));
					castle.start();
				} else {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(agadCastlesURL));
					castle.start();
				}

			}
			else if(whatPiece.equals("wk") && col == chessData.originalCol + 2) {
				chessData.pieces[7][7] = "ee";
				chessData.pieces[7][5] = "wr";
				chessData.pieces[7][6] = "wk";
				chessData.pieces[7][4] = "ee";

				chessData.whiteKingRow = 7;
				chessData.whiteKingCol = 6;		
				chessData.whiteCanCastleKingSide = false;
				chessData.whiteCanCastleQueenSide = false;

				updateSquare(7,4);
				updateSquare(7,5);
				updateSquare(7,6);
				updateSquare(7,7);

				if(!chessData.agadMode) {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(castleURL));
					castle.start();
				} else {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(agadCastlesURL));
					castle.start();
				}
			}
			else if(whatPiece.equals("bk") && col == chessData.originalCol - 2) {
				chessData.pieces[0][0] = "ee";
				chessData.pieces[0][3] = "br";
				chessData.pieces[0][2] = "bk";
				chessData.pieces[0][4] = "ee";

				chessData.blackKingRow = 0;
				chessData.blackKingCol = 2;
				chessData.blackCanCastleKingSide = false;
				chessData.blackCanCastleQueenSide = false;

				updateSquare(0,0);
				updateSquare(0,2);
				updateSquare(0,3);
				updateSquare(0,4);

				if(!chessData.agadMode) {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(castleURL));
					castle.start();
				} else {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(agadCastlesURL));
					castle.start();
				}
			}
			else if(whatPiece.equals("bk") && col == chessData.originalCol + 2) {
				chessData.pieces[0][7] = "ee";
				chessData.pieces[0][5] = "br";
				chessData.pieces[0][6] = "bk";
				chessData.pieces[0][4] = "ee";

				chessData.blackKingRow = 0;
				chessData.blackKingCol = 6;
				chessData.blackCanCastleKingSide = false;
				chessData.blackCanCastleQueenSide = false;

				updateSquare(0,4);
				updateSquare(0,5);
				updateSquare(0,6);
				updateSquare(0,7);

				if(!chessData.agadMode) {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(castleURL));
					castle.start();
				} else {
					Clip castle = AudioSystem.getClip();
					castle.open(AudioSystem.getAudioInputStream(agadCastlesURL));
					castle.start();
				}
			}
			//en passant
			else if(whatPiece.equals("wp") && chessData.pieces[row][col].equals("ee") && showMoves[row][col].getIcon().equals(occupiedHighlight)) {
				chessData.pieces[row][col] = whatPiece;
				chessData.pieces[chessData.originalRow][chessData.originalCol] = "ee";
				chessData.pieces[row + 1][col] = "ee";
				updateSquare(row,col);
				updateSquare(chessData.originalRow,chessData.originalCol);
				updateSquare(row+1,col);

				if(!chessData.agadMode) {
					Clip capture = AudioSystem.getClip();
					capture.open(AudioSystem.getAudioInputStream(captureURL));
					capture.start();
				} else {
					Random rand = new Random();
					int quoteNum = rand.nextInt(3);
					
					if(quoteNum == 0) {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures1URL));
						captures.start();
					}
					else if(quoteNum == 1) {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures2URL));
						captures.start();
					} else {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures3URL));
						captures.start();
					}
				}
			}
			else if(whatPiece.equals("bp") && chessData.pieces[row][col].equals("ee") && showMoves[row][col].getIcon().equals(occupiedHighlight)) {
				chessData.pieces[row][col] = whatPiece;
				chessData.pieces[chessData.originalRow][chessData.originalCol] = "ee";
				chessData.pieces[row-1][col] = "ee";
				updateSquare(row,col);
				updateSquare(chessData.originalRow,chessData.originalCol);
				updateSquare(row-1,col);

				if(!chessData.agadMode) {
					Clip capture = AudioSystem.getClip();
					capture.open(AudioSystem.getAudioInputStream(captureURL));
					capture.start();
				} else {
					Random rand = new Random();
					int quoteNum = rand.nextInt(3);
					
					if(quoteNum == 0) {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures1URL));
						captures.start();
					}
					else if(quoteNum == 1) {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures2URL));
						captures.start();
					} else {
						Clip captures = AudioSystem.getClip();
						captures.open(AudioSystem.getAudioInputStream(agadCaptures3URL));
						captures.start();
					}
				}
			}
			//handles other pieces moving
			else {
				if(chessData.pieces[row][col].equals("ee")) {
					if(!chessData.agadMode) {
						Clip move = AudioSystem.getClip();
						move.open(AudioSystem.getAudioInputStream(moveURL));
						move.start();
					} else {
						if(whatPiece.charAt(0) == 'w') {
							Clip move = AudioSystem.getClip();
							move.open(AudioSystem.getAudioInputStream(agadWhiteURL));
							move.start();
						} else {
							Clip move = AudioSystem.getClip();
							move.open(AudioSystem.getAudioInputStream(agadBlackURL));
							move.start();
						}
					}
				} else {
					if(!chessData.agadMode) {
						Clip capture = AudioSystem.getClip();
						capture.open(AudioSystem.getAudioInputStream(captureURL));
						capture.start();
					} else {
						Random rand = new Random();
						int quoteNum = rand.nextInt(3);
						
						if(quoteNum == 0) {
							Clip captures = AudioSystem.getClip();
							captures.open(AudioSystem.getAudioInputStream(agadCaptures1URL));
							captures.start();
						}
						else if(quoteNum == 1) {
							Clip captures = AudioSystem.getClip();
							captures.open(AudioSystem.getAudioInputStream(agadCaptures2URL));
							captures.start();
						} else {
							Clip captures = AudioSystem.getClip();
							captures.open(AudioSystem.getAudioInputStream(agadCaptures3URL));
							captures.start();
						}
					}
				}

				chessData.pieces[row][col] = whatPiece;
				chessData.pieces[chessData.originalRow][chessData.originalCol] = "ee";
				updateSquare(row,col);
				updateSquare(chessData.originalRow,chessData.originalCol);

				if(whatPiece.equals("wk")) {
					chessData.whiteKingRow = row;
					chessData.whiteKingCol = col;
					chessData.whiteCanCastleKingSide = false;
					chessData.whiteCanCastleQueenSide = false;
				}
				else if(whatPiece.equals("bk")) {
					chessData.blackKingRow = row;
					chessData.blackKingCol = col;
					chessData.blackCanCastleKingSide = false;
					chessData.blackCanCastleQueenSide = false;
				}
				else if(whatPiece.equals("wp")) {
					if(chessData.originalRow - row == 2) {
						chessData.enPassant[row][col] = true;
					}
				}
				else if(whatPiece.equals("bp")) {
					if(row - chessData.originalRow == 2) {
						chessData.enPassant[row][col] = true;
					}
				}

				//if the piece moved was a rook, king loses castling rights on that side
			}
			if(whatPiece.equals("wr") && chessData.originalRow == 7 && chessData.originalCol == 0) {
				chessData.whiteCanCastleQueenSide = false;
			}
			else if(whatPiece.equals("wr") && chessData.originalRow == 7 && chessData.originalCol == 7) {
				chessData.whiteCanCastleKingSide = false;
			}
			else if(whatPiece.equals("br") && chessData.originalRow == 0 && chessData.originalCol == 0) {
				chessData.blackCanCastleQueenSide = false;
			}
			else if(whatPiece.equals("br") && chessData.originalRow == 0 && chessData.originalCol == 7) {
				chessData.blackCanCastleKingSide = false;
			}

			//handles promotion for pawns
			if(whatPiece.equals("wp") && row == 0) {

				String promoted = null;

				while(promoted == null) {
					promoted = createDialog();
				}

				if(promoted.equals("Queen")) {
					chessData.pieces[row][col] = "wq";
				}
				else if(promoted.equals("Knight")) {
					chessData.pieces[row][col] = "wn";
				}
				else if(promoted.equals("Bishop")) {
					chessData.pieces[row][col] = "wb";
				}
				else if(promoted.equals("Rook")) {
					chessData.pieces[row][col] = "wr";
				}
			}
			else if(whatPiece.equals("bp") && row == 7) {

				String promoted = null;

				while(promoted == null) {
					promoted = createDialog();
				}
				if(promoted.equals("Queen")) {
					chessData.pieces[row][col] = "bq";
				}
				else if(promoted.equals("Knight")) {
					chessData.pieces[row][col] = "bn";
				}
				else if(promoted.equals("Bishop")) {
					chessData.pieces[row][col] = "bb";
				}
				else if(promoted.equals("Rook")) {
					chessData.pieces[row][col] = "br";
				}
			}

			//updates squares and possible threats
			updateSquare(row, col);

			chessData.updateThreats();

			boolean checkmate = true;

			hideLabels();

			//checks for checkmate/stalemate
			breakLoop: for(int i = 0; i < 8; i++) {
				for(int k = 0; k < 8; k++) {
					if(chessData.whiteTurn) {
						if(chessData.pieces[i][k].charAt(0) == 'b') {
							if(mateTest(i,k,'b')) {
								checkmate = false;
								break breakLoop;
							}
						}
					} else {
						if(chessData.pieces[i][k].charAt(0) == 'w') {
							if(mateTest(i,k,'w')) {
								checkmate = false;
								break breakLoop;
							}
						}
					}
				}
			}

			//displays if checkmate or stalemate
			if(checkmate && chessData.whiteTurn) {
				if(chessData.whiteThreats[chessData.blackKingRow][chessData.blackKingCol]) {
					TurnWindow.message.setText("Checkmate! White wins!");
					chessData.winnerName = chessData.whiteName;
					chessData.loserName = chessData.blackName;
					TurnWindow.recordButton.setVisible(true);

					if(!chessData.agadMode) {
						Clip gameEnded = AudioSystem.getClip();
						gameEnded.open(AudioSystem.getAudioInputStream(gameEndedURL));
						gameEnded.start();
					} else {
						Random rand = new Random();
						int quoteNum = rand.nextInt(3);
						ActionListener playSound = new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									if(quoteNum == 0) {
										Clip blackMated = AudioSystem.getClip();
										blackMated.open(AudioSystem.getAudioInputStream(agadBlackMatedURL));
										blackMated.start();
									}
									else if(quoteNum == 1) {
										Clip blackMated = AudioSystem.getClip();
										blackMated.open(AudioSystem.getAudioInputStream(agadBlackMated2URL));
										blackMated.start();
									} else {
										Clip blackMated = AudioSystem.getClip();
										blackMated.open(AudioSystem.getAudioInputStream(agadBlackMated3URL));
										blackMated.start();
									}
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
									e.printStackTrace();
								}
				            }
				        };
						
						Timer timer = new Timer(800, playSound);
						timer.setRepeats(false);
						timer.start();
					}
					return;
				} else {
					TurnWindow.message.setText("Stalemate!");
					chessData.winnerName = chessData.whiteName;
					chessData.loserName = chessData.blackName;
					chessData.draw = true;
					TurnWindow.recordButton.setVisible(true);

					if(!chessData.agadMode) {
						Clip gameEnded = AudioSystem.getClip();
						gameEnded.open(AudioSystem.getAudioInputStream(gameEndedURL));
						gameEnded.start();
					} else {
						ActionListener playSound = new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									Clip stalemate = AudioSystem.getClip();
									stalemate.open(AudioSystem.getAudioInputStream(agadStalemateURL));
									stalemate.start();
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
									e.printStackTrace();
								}
				            }
				        };
				        
				        Timer timer = new Timer(800, playSound);
				        timer.setRepeats(false);
				        timer.start();
					}
					return;
				}
			}
			else if(checkmate && !chessData.whiteTurn) {
				if(chessData.blackThreats[chessData.whiteKingRow][chessData.whiteKingCol]) {
					TurnWindow.message.setText("Checkmate! Black wins!");
					chessData.winnerName = chessData.blackName;
					chessData.loserName = chessData.whiteName;
					TurnWindow.recordButton.setVisible(true);

					if(!chessData.agadMode) {
						Clip gameEnded = AudioSystem.getClip();
						gameEnded.open(AudioSystem.getAudioInputStream(gameEndedURL));
						gameEnded.start();
					} else {
						Random rand = new Random();
						int quoteNum = rand.nextInt(3);
						
						ActionListener playSound = new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									if(quoteNum == 0) {
										Clip whiteMated = AudioSystem.getClip();
										whiteMated.open(AudioSystem.getAudioInputStream(agadWhiteMatedURL));
										whiteMated.start();
									}
									else if(quoteNum == 1) {
										Clip whiteMated = AudioSystem.getClip();
										whiteMated.open(AudioSystem.getAudioInputStream(agadWhiteMated2URL));
										whiteMated.start();
									} else {
										Clip whiteMated = AudioSystem.getClip();
										whiteMated.open(AudioSystem.getAudioInputStream(agadWhiteMated3URL));
										whiteMated.start();
									}
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
									e.printStackTrace();
								}
				            }
				        };
						Timer timer = new Timer(800, playSound);
						timer.setRepeats(false);
						timer.start();
					}
					return;
				} else {
					TurnWindow.message.setText("Stalemate!");
					chessData.winnerName = chessData.whiteName;
					chessData.loserName = chessData.blackName;
					chessData.draw = true;
					TurnWindow.recordButton.setVisible(true);

					if(!chessData.agadMode) {
						Clip gameEnded = AudioSystem.getClip();
						gameEnded.open(AudioSystem.getAudioInputStream(gameEndedURL));
						gameEnded.start();
					} else {
						ActionListener playSound = new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									Clip stalemate = AudioSystem.getClip();
									stalemate.open(AudioSystem.getAudioInputStream(agadStalemateURL));
									stalemate.start();
								} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
									e.printStackTrace();
								}
				            }
				        };
				        
				        Timer timer = new Timer(800, playSound);
				        timer.setRepeats(false);
				        timer.start();
					}

					return;
				}
			}

			//displays whose turn to move
			if(chessData.whiteTurn) {
				chessData.whiteTurn = false;
				TurnWindow.message.setText("Black (" + chessData.blackName + ") to move");
			} else {
				chessData.whiteTurn = true;
				TurnWindow.message.setText("White (" + chessData.whiteName + ") to move");
			}

		} else {
			hideLabels();
		}

	}

	//checks each piece of a certain colour if it can move, if none can move, it means the player is in checkmate or stalemate
	//uses the same algorithm as when a piece is clicked to display all legal moves
	private boolean mateTest(int row, int col, char player) {

		boolean possibleMoves = false;

		if(player == 'w') {
			if(chessData.pieces[row][col].charAt(1) == 'k') {

				int moveRow;
				int moveCol;
				int rowCap;
				int colCap;

				if(row - 1 < 0) {
					moveRow = 0;
				} else {
					moveRow = row - 1;
				}
				if(col - 1 < 0) {
					moveCol = 0;
				} else {
					moveCol = col - 1;
				}

				if(row + 1 > 7) {
					rowCap = 7;
				} else {
					rowCap = row + 1;
				}
				if(col + 1 > 7) {
					colCap = 7;
				} else {
					colCap = col + 1;
				}

				String[][] piecesCopy = new String[8][8];
				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = moveRow; i <= rowCap; i++) {
					for(int k = moveCol; k <= colCap; k++) {
						if(moveRow != row && moveCol != col) {
							String piece = piecesCopy[i][k];

							piecesCopy[i][k] = "wk";
							piecesCopy[row][col] = "ee";

							boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

							piecesCopy[i][k] = piece;
							piecesCopy[row][col] = "wk";

							if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}
					}
				}

				if(chessData.whiteCanCastleKingSide && chessData.pieces[7][5].charAt(0) == 'e' && chessData.pieces[7][6].charAt(0) == 'e' && !chessData.blackThreats[7][5] && !chessData.blackThreats[7][6] && !chessData.blackThreats[chessData.whiteKingRow][chessData.whiteKingCol]) {
					possibleMoves = true;
				}
				if(chessData.whiteCanCastleQueenSide && chessData.pieces[7][2].charAt(0) == 'e' && chessData.pieces[7][3].charAt(0) == 'e' && chessData.pieces[7][4].charAt(0) == 'e' && !chessData.blackThreats[7][2] && !chessData.blackThreats[7][3] && !chessData.blackThreats[7][4]) {
					possibleMoves = true;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {

				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				if(piecesCopy[row - 1][col].charAt(0) == 'e') {

					piecesCopy[row][col] = "ee";
					piecesCopy[row - 1][col] = "wp";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					if(row-2 >= 0) {
						piecesCopy[row][col] = "ee";
						piecesCopy[row-2][col] = "wp";
					}
					boolean[][] threatCopy2 = chessData.potentialBlackThreats(piecesCopy);

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol] && chessData.pieces[row-1][col].charAt(0) == 'e') {
						possibleMoves = true;
					}
					if(row == 6 && chessData.pieces[4][col].charAt(0) == 'e' && chessData.pieces[row-1][col].charAt(0) == 'e' && !threatCopy2[chessData.whiteKingRow][chessData.whiteKingCol]) {
						possibleMoves = true;
					}


					piecesCopy[row][col] = "wp";
					piecesCopy[row - 1][col] = "ee";
				}
				if(col - 1 >= 0) {
					if(chessData.pieces[row-1][col-1].charAt(0) == 'b') {
						String pieceAt = chessData.pieces[row-1][col-1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row-1][col-1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row - 1][col-1] = pieceAt;
					}
					if(chessData.enPassant[row][col-1]) {

						String piece = piecesCopy[row][col-1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col-1] = "ee";
						piecesCopy[row-1][col-1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row][col-1] = piece;
						piecesCopy[row-1][col-1] = "ee";
					}
				}
				if(col + 1 < 8) {
					if(chessData.pieces[row-1][col+1].charAt(0) == 'b') {
						String pieceAt = chessData.pieces[row-1][col+1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row-1][col+1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row - 1][col+1] = pieceAt;
					}
					if(chessData.enPassant[row][col+1]) {

						String piece = piecesCopy[row][col+1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col+1] = "ee";
						piecesCopy[row-1][col+1] = "wp";

						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "wp";
						piecesCopy[row][col+1] = piece;
						piecesCopy[row+1][col+1] = "ee";
					}
				}

			}
			else if(chessData.pieces[row][col].charAt(1) == 'r') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wr";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wr";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				piecesCopy[row][col] = "ee";

				if(row - 2 >= 0) {
					if(col - 1 >= 0) {

						String piece = chessData.pieces[row-2][col-1];
						piecesCopy[row-2][col-1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-2][col-1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-2][col-1].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row-2][col+1];
						piecesCopy[row-2][col+1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-2][col+1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-2][col+1].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-2][col+1] = piece;
					}
				}
				if(row + 2 < 8) {
					if(col - 1 >= 0) {
						String piece = chessData.pieces[row+2][col-1];
						piecesCopy[row+2][col-1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+2][col-1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+2][col-1].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row+2][col+1];
						piecesCopy[row+2][col+1] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+2][col+1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+2][col+1].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+2][col+1] = piece;
					}
				}
				if(row - 1 >= 0) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row-1][col-2];
						piecesCopy[row-1][col-2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col-2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-1][col-2].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row-1][col+2];
						piecesCopy[row-1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col+2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-1][col+2].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-1][col+2] = piece;
					}
				}
				if(row + 1 < 8) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row+1][col-2];
						piecesCopy[row+1][col-2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+1][col-2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+1][col-2].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row+1][col+2];
						piecesCopy[row+1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row+1][col+2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+1][col+2].charAt(0) == 'b') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+1][col+2] = piece;
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				String[][] piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wb";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "wq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "wq";

					if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'b') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
		}
		else if(player == 'b') {

			if(chessData.pieces[row][col].charAt(1) == 'k') {
				int moveRow;
				int moveCol;
				int rowCap;
				int colCap;

				if(row - 1 < 0) {
					moveRow = 0;
				} else {
					moveRow = row - 1;
				}
				if(col - 1 < 0) {
					moveCol = 0;
				} else {
					moveCol = col - 1;
				}

				if(row + 1 > 7) {
					rowCap = 7;
				} else {
					rowCap = row + 1;
				}
				if(col + 1 > 7) {
					colCap = 7;
				} else {
					colCap = col + 1;
				}

				String[][] piecesCopy = new String[8][8];
				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = moveRow; i <= rowCap; i++) {
					for(int k = moveCol; k <= colCap; k++) {
						if(!(moveRow == row && moveCol == col)) {
							String piece = piecesCopy[i][k];

							piecesCopy[i][k] = "bk";
							piecesCopy[row][col] = "ee";

							boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

							piecesCopy[i][k] = piece;
							piecesCopy[row][col] = "bk";

							if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(!threatCopy[i][k] && chessData.pieces[i][k].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}
					}
				}

				if(chessData.blackCanCastleKingSide && chessData.pieces[0][5].charAt(0) == 'e' && chessData.pieces[0][6].charAt(0) == 'e' && !chessData.whiteThreats[0][5] && !chessData.whiteThreats[0][6] && !chessData.whiteThreats[chessData.blackKingRow][chessData.blackKingCol]) {
					possibleMoves = true;
				}
				if(chessData.blackCanCastleQueenSide && chessData.pieces[0][2].charAt(0) == 'e' && chessData.pieces[0][3].charAt(0) == 'e' && chessData.pieces[0][4].charAt(0) == 'e' && !chessData.whiteThreats[0][2] && !chessData.whiteThreats[0][3] && !chessData.whiteThreats[0][4] && !chessData.whiteThreats[chessData.blackKingRow][chessData.blackKingCol]) {
					possibleMoves = true;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'p') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				if(piecesCopy[row + 1][col].charAt(0) == 'e') {

					piecesCopy[row][col] = "ee";
					piecesCopy[row + 1][col] = "bp";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					if(row + 2 < 8) {
						piecesCopy[row][col] = "ee";
						piecesCopy[row+2][col] = "bp";
					}
					boolean[][] threatCopy2 = chessData.potentialWhiteThreats(piecesCopy);

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol] && chessData.pieces[row+1][col].charAt(0) == 'e') {
						possibleMoves = true;
					}
					if(row == 1 && chessData.pieces[3][col].charAt(0) == 'e' && chessData.pieces[row+1][col].charAt(0) == 'e' && !threatCopy2[chessData.blackKingRow][chessData.blackKingCol]) {
						possibleMoves = true;
					}

					piecesCopy[row][col] = "bp";
					piecesCopy[row + 1][col] = "ee";
				}
				if(col - 1 >= 0) {
					if(chessData.pieces[row+1][col-1].charAt(0) == 'w') {
						String pieceAt = chessData.pieces[row+1][col-1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row+1][col-1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row + 1][col-1] = pieceAt;
					}
					if(chessData.enPassant[row][col-1]) {

						String piece = piecesCopy[row][col-1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col-1] = "ee";
						piecesCopy[row+1][col-1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row][col-1] = piece;
						piecesCopy[row+1][col-1] = "ee";
					}
				}
				if(col + 1 < 8) {
					if(chessData.pieces[row+1][col+1].charAt(0) == 'w') {
						String pieceAt = chessData.pieces[row+1][col+1];

						piecesCopy[row][col] = "ee";
						piecesCopy[row+1][col+1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row + 1][col+1] = pieceAt;
					}
					if(chessData.enPassant[row][col+1]) {

						String piece = piecesCopy[row][col+1];
						piecesCopy[row][col] = "ee";
						piecesCopy[row][col+1] = "ee";
						piecesCopy[row+1][col+1] = "bp";

						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							possibleMoves = true;
						}

						piecesCopy[row][col] = "bp";
						piecesCopy[row][col+1] = piece;
						piecesCopy[row+1][col+1] = "ee";
					}
				}

			}
			else if(chessData.pieces[row][col].charAt(1) == 'r') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "br";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "br";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'n') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				piecesCopy[row][col] = "ee";

				if(row - 2 >= 0) {
					if(col - 1 >= 0) {
						String piece = chessData.pieces[row-2][col-1];
						piecesCopy[row-2][col-1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-2][col-1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-2][col-1].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row-2][col+1];
						piecesCopy[row-2][col+1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-2][col+1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-2][col+1].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-2][col+1] = piece;
					}
				}
				if(row + 2 < 8) {
					if(col - 1 >= 0) {
						String piece = chessData.pieces[row+2][col-1];
						piecesCopy[row+2][col-1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+2][col-1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+2][col-1].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+2][col-1] = piece;
					}
					if(col + 1 < 8) {
						String piece = chessData.pieces[row+2][col+1];
						piecesCopy[row+2][col+1] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+2][col+1].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+2][col+1].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+2][col+1] = piece;
					}
				}
				if(row - 1 >= 0) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row-1][col-2];
						piecesCopy[row-1][col-2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row-1][col-2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-1][col-2].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row-1][col+2];
						piecesCopy[row-1][col+2] = "wn";
						boolean[][] threatCopy = chessData.potentialBlackThreats(piecesCopy);

						if(!threatCopy[chessData.whiteKingRow][chessData.whiteKingCol]) {
							if(chessData.pieces[row-1][col+2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row-1][col+2].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row-1][col+2] = piece;
					}
				}
				if(row + 1 < 8) {
					if(col - 2 >= 0) {
						String piece = chessData.pieces[row+1][col-2];
						piecesCopy[row+1][col-2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+1][col-2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+1][col-2].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+1][col-2] = piece;
					}
					if(col + 2 < 8) {
						String piece = chessData.pieces[row+1][col+2];
						piecesCopy[row+1][col+2] = "bn";
						boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

						if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
							if(chessData.pieces[row+1][col+2].charAt(0) == 'e') {
								possibleMoves = true;
							}
							else if(chessData.pieces[row+1][col+2].charAt(0) == 'w') {
								possibleMoves = true;
							}
						}

						piecesCopy[row+1][col+2] = piece;
					}
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'b') {
				String[][] piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bb";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bb";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
			else if(chessData.pieces[row][col].charAt(1) == 'q') {
				String[][] piecesCopy = new String[8][8];

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				for(int i = row + 1; i < 8; i++) {

					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = row - 1; i >= 0; i--) {
					String piece = piecesCopy[i][col];
					piecesCopy[i][col] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[i][col] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[i][col].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[i][col].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[i][col].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col + 1; i < 8; i++) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				for(int i = col - 1; i >= 0; i--) {
					String piece = piecesCopy[row][i];
					piecesCopy[row][i] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[row][i] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[row][i].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[row][i].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[row][i].charAt(0) != 'e') {
							break;
						}
					}
				}
				piecesCopy = new String[8][8];

				int moveRow = row - 1;
				int moveCol = col - 1;

				for(int i = 0; i < 8; i++) {
					for(int k = 0; k < 8; k++) {
						piecesCopy[i][k] = chessData.pieces[i][k];
					}
				}

				while(moveRow >= 0 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol--;
				}

				moveRow = row + 1;
				moveCol = col - 1;

				while(moveRow < 8 && moveCol >= 0) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol--;
				}

				moveRow = row - 1;
				moveCol = col + 1;

				while(moveRow >= 0 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow--;
					moveCol++;
				}

				moveRow = row + 1;
				moveCol = col + 1;

				while(moveRow < 8 && moveCol < 8) {
					String piece = piecesCopy[moveRow][moveCol];

					piecesCopy[moveRow][moveCol] = "bq";
					piecesCopy[row][col] = "ee";

					boolean[][] threatCopy = chessData.potentialWhiteThreats(piecesCopy);

					piecesCopy[moveRow][moveCol] = piece;
					piecesCopy[row][col] = "bq";

					if(!threatCopy[chessData.blackKingRow][chessData.blackKingCol]) {
						if(chessData.pieces[moveRow][moveCol].charAt(0) == 'e') {
							possibleMoves = true;
						}
						else if(chessData.pieces[moveRow][moveCol].charAt(0) == 'w') {
							possibleMoves = true;
							break;
						} else {
							break;
						}
					} else {
						if(chessData.pieces[moveRow][moveCol].charAt(0) != 'e') {
							break;
						}
					}
					moveRow++;
					moveCol++;
				}
			}
		}
		return possibleMoves;
	}

	//displays labels
	private void showLabel(int row, int col, boolean empty) {
		if(empty) {
			showMoves[row][col].setIcon(emptyHighlight);
			showMoves[row][col].setVisible(true);
		} else {
			showMoves[row][col].setIcon(occupiedHighlight);
			showMoves[row][col].setVisible(true);
		}
	}
	//hides labels
	private void hideLabels() {
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
				showMoves[i][k].setVisible(false);
			}
		}
	}

	//creates a dialog box for promotion of a pawn
	private String createDialog() {
		hideLabels();
		Object[] options = {"Queen", "Knight", "Bishop", "Rook"};
		String selection = (String)JOptionPane.showInputDialog(frame, "What would you like to promote to?, ", "Promote", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		return selection;
	}
}
