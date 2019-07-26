//Ben Zhang
//ICS 4U
//Summitive Assignment
//Chess Data

import java.util.Arrays;

public class chessData {
	//false is lightsquare, true is darksquare
	final static boolean[][] darkOrLight = {{false, true, false, true, false, true, false, true}, {true, false, true, false, true, false, true, false}, {false, true, false, true, false, true, false, true}, {true, false, true, false, true, false, true, false}, {false, true, false, true, false, true, false, true}, {true, false, true, false, true, false, true, false}, {false, true, false, true, false, true, false, true}, {true, false, true, false, true, false, true, false}};
	static String[][] pieces = new String[8][8];
	static boolean[][] enPassant = new boolean[8][8];
	static int[] enPassantTake = new int[2];
	static boolean[][] blackThreats = new boolean[8][8];
	static boolean[][] whiteThreats = new boolean[8][8];
	static boolean whiteTurn = true;

	static boolean whiteCanCastleKingSide = true;
	static boolean whiteCanCastleQueenSide = true;
	static boolean blackCanCastleKingSide = true;
	static boolean blackCanCastleQueenSide = true;

	static boolean pieceClicked = false;
	static String whatPiece = "";
	static int originalRow = -1;
	static int originalCol = -1;

	static int blackKingRow = 0;
	static int blackKingCol = 4;

	static int whiteKingRow = 7;
	static int whiteKingCol = 4;

	static String whiteName = "";
	static String blackName = "";
	static String winnerName = "";
	static String loserName = "";
	static boolean draw = false;
	
	static String mode = "classic";
	
	//enabling of agadmator soundpack
	static boolean agadMode = false;

	//updates the threatarrays to show which squares are threatened
	static void updateThreats() {

		//clears both threat arrays
		for(boolean[] row:blackThreats) {
			Arrays.fill(row, false);
		}
		for(boolean[] row:whiteThreats) {
			Arrays.fill(row, false);
		}

		//checks all legal moves for every piece, adds a threat to all squares they can capture
		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {

				if(pieces[i][k].charAt(0) == 'b') {
					if(pieces[i][k].charAt(1) == 'r') {
						for(int j = i - 1; j >= 0; j--) {
							if(pieces[j][k].charAt(0) == 'e') {
								blackThreats[j][k] = true;
							} else {
								blackThreats[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(pieces[j][k].charAt(0) == 'e') {
								blackThreats[j][k] = true;
							} else {
								blackThreats[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(pieces[i][j].charAt(0) == 'e') {
								blackThreats[i][j] = true;
							} else {
								blackThreats[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(pieces[i][j].charAt(0) == 'e') {
								blackThreats[i][j] = true;
							} else {
								blackThreats[i][j] = true;
								break;
							}
						}
					}
					else if(pieces[i][k].charAt(1) == 'n') {
						if(i - 2 >= 0 && k - 1 >= 0) {
							blackThreats[i - 2][k - 1] = true;
						}
						if(i - 2 >= 0 && k + 1 < 8) {
							blackThreats[i - 2][k + 1] = true;
						}
						if(i + 2 < 8 && k - 1 >= 0) {
							blackThreats[i + 2][k - 1] = true;
						}
						if(i + 2 < 8 && k + 1 < 8) {
							blackThreats[i + 2][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 2 >= 0) {
							blackThreats[i - 1][k - 2] = true;
						}
						if(i - 1 >= 0 && k + 2 < 8) {
							blackThreats[i - 1][k + 2] = true;
						}
						if(i + 1 < 8 && k - 2 >= 0) {
							blackThreats[i + 1][k - 2] = true;
						}
						if(i + 1 < 8 && k + 2 < 8) {
							blackThreats[i + 1][k + 2] = true;
						}
					}
					else if(pieces[i][k].charAt(1) == 'b') {
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(pieces[i][k].charAt(1) == 'p') {
						if(i + 1 < 8) {
							if(k + 1 < 8){
								blackThreats[i + 1][k + 1] = true;
							}
							if(k - 1 >= 0) {
								blackThreats[i + 1][k - 1] = true;
							}
						}
					}
					else if(pieces[i][k].charAt(1) == 'q') {
						for(int j = i - 1; j >= 0; j--) {
							if(pieces[j][k].charAt(0) == 'e') {
								blackThreats[j][k] = true;
							} else {
								blackThreats[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(pieces[j][k].charAt(0) == 'e') {
								blackThreats[j][k] = true;
							} else {
								blackThreats[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(pieces[i][j].charAt(0) == 'e') {
								blackThreats[i][j] = true;
							} else {
								blackThreats[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(pieces[i][j].charAt(0) == 'e') {
								blackThreats[i][j] = true;
							} else {
								blackThreats[i][j] = true;
								break;
							}
						}
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								blackThreats[row][col] = true;
							} else {
								blackThreats[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(pieces[i][k].charAt(1) == 'k') {
						if(i + 1 < 8 && k + 1 < 8) {
							blackThreats[i + 1][k + 1] = true;
						}
						if(i + 1 < 8 && k - 1 >= 0) {
							blackThreats[i + 1][k - 1] = true;
						}
						if(i - 1 >= 0 && k + 1 < 8) {
							blackThreats[i - 1][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 1 >= 0) {
							blackThreats[i - 1][k - 1] = true;
						}
						if(i + 1 < 8) {
							blackThreats[i + 1][k] = true;
						}
						if(i - 1 >= 0) {
							blackThreats[i - 1][k] = true;
						}
						if(k + 1 < 8) {
							blackThreats[i][k + 1] = true;
						}
						if(k - 1 >= 0) {
							blackThreats[i][k - 1] = true;
						}
					}
				}
				else if(pieces[i][k].charAt(0) == 'w') {
					if(pieces[i][k].charAt(1) == 'r') {
						for(int j = i - 1; j >= 0; j--) {
							if(pieces[j][k].charAt(0) == 'e') {
								whiteThreats[j][k] = true;
							} else {
								whiteThreats[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(pieces[j][k].charAt(0) == 'e') {
								whiteThreats[j][k] = true;
							} else {
								whiteThreats[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(pieces[i][j].charAt(0) == 'e') {
								whiteThreats[i][j] = true;
							} else {
								whiteThreats[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(pieces[i][j].charAt(0) == 'e') {
								whiteThreats[i][j] = true;
							} else {
								whiteThreats[i][j] = true;
								break;
							}
						}
					}
					else if(pieces[i][k].charAt(1) == 'n') {
						if(i - 2 >= 0 && k - 1 >= 0) {
							whiteThreats[i - 2][k - 1] = true;
						}
						if(i - 2 >= 0 && k + 1 < 8) {
							whiteThreats[i - 2][k + 1] = true;
						}
						if(i + 2 < 8 && k - 1 >= 0) {
							whiteThreats[i + 2][k - 1] = true;
						}
						if(i + 2 < 8 && k + 1 < 8) {
							whiteThreats[i + 2][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 2 >= 0) {
							whiteThreats[i - 1][k - 2] = true;
						}
						if(i - 1 >= 0 && k + 2 < 8) {
							whiteThreats[i - 1][k + 2] = true;
						}
						if(i + 1 < 8 && k - 2 >= 0) {
							whiteThreats[i + 1][k - 2] = true;
						}
						if(i + 1 < 8 && k + 2 < 8) {
							whiteThreats[i + 1][k + 2] = true;
						}
					}
					else if(pieces[i][k].charAt(1) == 'b') {
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(pieces[i][k].charAt(1) == 'p') {
						if(i-1>=0) {
							if(k + 1 < 8){
								whiteThreats[i - 1][k + 1] = true;
							}
							if(k - 1 >= 0) {
								whiteThreats[i - 1][k - 1] = true;
							}
						}
					}
					else if(pieces[i][k].charAt(1) == 'q') {
						for(int j = i - 1; j >= 0; j--) {
							if(pieces[j][k].charAt(0) == 'e') {
								whiteThreats[j][k] = true;
							} else {
								whiteThreats[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(pieces[j][k].charAt(0) == 'e') {
								whiteThreats[j][k] = true;
							} else {
								whiteThreats[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(pieces[i][j].charAt(0) == 'e') {
								whiteThreats[i][j] = true;
							} else {
								whiteThreats[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(pieces[i][j].charAt(0) == 'e') {
								whiteThreats[i][j] = true;
							} else {
								whiteThreats[i][j] = true;
								break;
							}
						}
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(pieces[row][col].charAt(0) == 'e') {
								whiteThreats[row][col] = true;
							} else {
								whiteThreats[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(pieces[i][k].charAt(1) == 'k') {
						if(i + 1 < 8 && k + 1 < 8) {
							whiteThreats[i + 1][k + 1] = true;
						}
						if(i + 1 < 8 && k - 1 >= 0) {
							whiteThreats[i + 1][k - 1] = true;
						}
						if(i - 1 >= 0 && k + 1 < 8) {
							whiteThreats[i - 1][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 1 >= 0) {
							whiteThreats[i - 1][k - 1] = true;
						}
						if(i + 1 < 8) {
							whiteThreats[i + 1][k] = true;
						}
						if(i - 1 >= 0) {
							whiteThreats[i - 1][k] = true;
						}
						if(k + 1 < 8) {
							whiteThreats[i][k + 1] = true;
						}
						if(k - 1 >= 0) {
							whiteThreats[i][k - 1] = true;
						}
					}
				}
			}
		}
	}
	
	//creates a threats array that shows black's potential threats if a piece were moved
	static boolean[][] potentialBlackThreats(String[][] piecesCopy){

		boolean[][] blackThreatsCopy = new boolean[8][8];

		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
				if(piecesCopy[i][k].charAt(0) == 'b') {
					if(piecesCopy[i][k].charAt(1) == 'r') {
						for(int j = i - 1; j >= 0; j--) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								blackThreatsCopy[j][k] = true;
							} else {
								blackThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								blackThreatsCopy[j][k] = true;
							} else {
								blackThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								blackThreatsCopy[i][j] = true;
							} else {
								blackThreatsCopy[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								blackThreatsCopy[i][j] = true;
							} else {
								blackThreatsCopy[i][j] = true;
								break;
							}
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'n') {
						if(i - 2 >= 0 && k - 1 >= 0) {
							blackThreatsCopy[i - 2][k - 1] = true;
						}
						if(i - 2 >= 0 && k + 1 < 8) {
							blackThreatsCopy[i - 2][k + 1] = true;
						}
						if(i + 2 < 8 && k - 1 >= 0) {
							blackThreatsCopy[i + 2][k - 1] = true;
						}
						if(i + 2 < 8 && k + 1 < 8) {
							blackThreatsCopy[i + 2][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 2 >= 0) {
							blackThreatsCopy[i - 1][k - 2] = true;
						}
						if(i - 1 >= 0 && k + 2 < 8) {
							blackThreatsCopy[i - 1][k + 2] = true;
						}
						if(i + 1 < 8 && k - 2 >= 0) {
							blackThreatsCopy[i + 1][k - 2] = true;
						}
						if(i + 1 < 8 && k + 2 < 8) {
							blackThreatsCopy[i + 1][k + 2] = true;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'b') {
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'p') {
						if(k + 1 < 8){
							blackThreatsCopy[i + 1][k + 1] = true;
						}
						if(k - 1 >= 0) {
							blackThreatsCopy[i + 1][k - 1] = true;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'q') {
						for(int j = i - 1; j >= 0; j--) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								blackThreatsCopy[j][k] = true;
							} else {
								blackThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								blackThreatsCopy[j][k] = true;
							} else {
								blackThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								blackThreatsCopy[i][j] = true;
							} else {
								blackThreatsCopy[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								blackThreatsCopy[i][j] = true;
							} else {
								blackThreatsCopy[i][j] = true;
								break;
							}
						}
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								blackThreatsCopy[row][col] = true;
							} else {
								blackThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'k') {
						if(i + 1 < 8 && k + 1 < 8) {
							blackThreatsCopy[i + 1][k + 1] = true;
						}
						if(i + 1 < 8 && k - 1 >= 0) {
							blackThreatsCopy[i + 1][k - 1] = true;
						}
						if(i - 1 >= 0 && k + 1 < 8) {
							blackThreatsCopy[i - 1][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 1 >= 0) {
							blackThreatsCopy[i - 1][k - 1] = true;
						}
						if(i + 1 < 8) {
							blackThreatsCopy[i + 1][k] = true;
						}
						if(i - 1 >= 0) {
							blackThreatsCopy[i - 1][k] = true;
						}
						if(k + 1 < 8) {
							blackThreatsCopy[i][k + 1] = true;
						}
						if(k - 1 >= 0) {
							blackThreatsCopy[i][k - 1] = true;
						}
					}
				}
			}
		}
		return blackThreatsCopy;
	}

	//same as previous method but for white
	static boolean[][] potentialWhiteThreats(String[][] piecesCopy) {

		boolean[][] whiteThreatsCopy = new boolean[8][8];

		for(int i = 0; i < 8; i++) {
			for(int k = 0; k < 8; k++) {
				if(piecesCopy[i][k].charAt(0) == 'w') {
					if(piecesCopy[i][k].charAt(1) == 'r') {
						for(int j = i - 1; j >= 0; j--) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								whiteThreatsCopy[j][k] = true;
							} else {
								whiteThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								whiteThreatsCopy[j][k] = true;
							} else {
								whiteThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								whiteThreatsCopy[i][j] = true;
							} else {
								whiteThreatsCopy[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								whiteThreatsCopy[i][j] = true;
							} else {
								whiteThreatsCopy[i][j] = true;
								break;
							}
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'n') {
						if(i - 2 >= 0 && k - 1 >= 0) {
							whiteThreatsCopy[i - 2][k - 1] = true;
						}
						if(i - 2 >= 0 && k + 1 < 8) {
							whiteThreatsCopy[i - 2][k + 1] = true;
						}
						if(i + 2 < 8 && k - 1 >= 0) {
							whiteThreatsCopy[i + 2][k - 1] = true;
						}
						if(i + 2 < 8 && k + 1 < 8) {
							whiteThreatsCopy[i + 2][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 2 >= 0) {
							whiteThreatsCopy[i - 1][k - 2] = true;
						}
						if(i - 1 >= 0 && k + 2 < 8) {
							whiteThreatsCopy[i - 1][k + 2] = true;
						}
						if(i + 1 < 8 && k - 2 >= 0) {
							whiteThreatsCopy[i + 1][k - 2] = true;
						}
						if(i + 1 < 8 && k + 2 < 8) {
							whiteThreatsCopy[i + 1][k + 2] = true;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'b') {
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'p') {
						if(i - 1 >= 0) {
							if(k + 1 < 8){
								whiteThreatsCopy[i - 1][k + 1] = true;
							}
							if(k - 1 >= 0) {
								whiteThreatsCopy[i - 1][k - 1] = true;
							}
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'q') {
						for(int j = i - 1; j >= 0; j--) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								whiteThreatsCopy[j][k] = true;
							} else {
								whiteThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = i + 1; j < 8; j++) {
							if(piecesCopy[j][k].charAt(0) == 'e') {
								whiteThreatsCopy[j][k] = true;
							} else {
								whiteThreatsCopy[j][k] = true;
								break;
							}
						}
						for(int j = k - 1; j >= 0; j--) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								whiteThreatsCopy[i][j] = true;
							} else {
								whiteThreatsCopy[i][j] = true;
								break;
							}
						}
						for(int j = k + 1; j < 8; j++) {
							if(piecesCopy[i][j].charAt(0) == 'e') {
								whiteThreatsCopy[i][j] = true;
							} else {
								whiteThreatsCopy[i][j] = true;
								break;
							}
						}
						int row = i - 1;
						int col = k - 1;

						while(row >= 0 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col -= 1;
						}
						row = i + 1;
						col = k + 1;

						while(row < 8 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col += 1;
						}
						row = i + 1;
						col = k - 1;

						while(row < 8 && col >= 0) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row += 1;
							col -= 1;
						}
						row = i - 1;
						col = k + 1;

						while(row >= 0 && col < 8) {
							if(piecesCopy[row][col].charAt(0) == 'e') {
								whiteThreatsCopy[row][col] = true;
							} else {
								whiteThreatsCopy[row][col] = true;
								break;
							}
							row -= 1;
							col += 1;
						}
					}
					else if(piecesCopy[i][k].charAt(1) == 'k') {
						if(i + 1 < 8 && k + 1 < 8) {
							whiteThreatsCopy[i + 1][k + 1] = true;
						}
						if(i + 1 < 8 && k - 1 >= 0) {
							whiteThreatsCopy[i + 1][k - 1] = true;
						}
						if(i - 1 >= 0 && k + 1 < 8) {
							whiteThreatsCopy[i - 1][k + 1] = true;
						}
						if(i - 1 >= 0 && k - 1 >= 0) {
							whiteThreatsCopy[i - 1][k - 1] = true;
						}
						if(i + 1 < 8) {
							whiteThreatsCopy[i + 1][k] = true;
						}
						if(i - 1 >= 0) {
							whiteThreatsCopy[i - 1][k] = true;
						}
						if(k + 1 < 8) {
							whiteThreatsCopy[i][k + 1] = true;
						}
						if(k - 1 >= 0) {
							whiteThreatsCopy[i][k - 1] = true;
						}
					}
				}
			}
		}
		return whiteThreatsCopy;
	}
}