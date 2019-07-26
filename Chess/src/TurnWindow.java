//Ben Zhang
//ICS 4U
//Summitive Assignment
//Turn Window

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.util.*;
public class TurnWindow {

	private JFrame frame;
	static JLabel message = new JLabel();
	static JButton recordButton = new JButton("Record this game's result!");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TurnWindow window = new TurnWindow();
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
	public TurnWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(900, 100, 200, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		message.setBounds(6, 30, 200, 30);
		message.setText("White (" + chessData.whiteName + ") to move");
		frame.getContentPane().add(message);
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					record();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		recordButton.setBounds(6, 65, 190, 30);
		recordButton.setVisible(false);
		frame.getContentPane().add(recordButton);
	}

	private void record() throws IOException {
		
		//reads through accounts file, copies data to list, then to array
		File file = new File("src/res/accounts.txt");
		Scanner sc = new Scanner(file);

		ArrayList<String> list = new ArrayList<>();

		while(sc.hasNextLine()) {
			list.add(sc.nextLine());
		}

		String[][] listArray = new String[list.size()][3];

		for(int i = 0; i < list.size(); i++) {
			String[] row = new String[3];
			row = list.get(i).split(" ");
			listArray[i] = row;
		}

		//writes to file
		for(int i = 0; i < list.size(); i++) {
			if(listArray[i][0].equals(chessData.winnerName)) {
				
				String[] recordString = listArray[i][2].split("-");
				double[] record = new double[2];

				if(chessData.draw) {
					record[0] = Double.parseDouble(recordString[0]) + 0.5;
					record[1] = Double.parseDouble(recordString[1]) + 0.5;
				} else {
					record[0] = Double.parseDouble(recordString[0]) + 1;
					record[1] = Double.parseDouble(recordString[1]);
				}
				
				listArray[i][2] = record[0] + "-" + record[1];
				list.set(i, listArray[i][0] + " " + listArray[i][1] + " " + listArray[i][2]);
				
			}
			else if(listArray[i][0].equals(chessData.loserName)) {
				
				String[] recordString = listArray[i][2].split("-");
				double[] record = new double[2];

				if(chessData.draw) {
					record[0] = Double.parseDouble(recordString[0]) + 0.5;
					record[1] = Double.parseDouble(recordString[1]) + 0.5;
				} else {
					record[0] = Double.parseDouble(recordString[0]);
					record[1] = Double.parseDouble(recordString[1]) + 1;
				}
				
				listArray[i][2] = record[0] + "-" + record[1];	
				list.set(i, listArray[i][0] + " " + listArray[i][1] + " " + listArray[i][2]);
				
			}
		}
		file.delete();
		file.createNewFile();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		
		bw.write("");
		
		for(int k = 0; k < listArray.length; k++) {
			bw.append(list.get(k));
			
			if(k != listArray.length - 1) {
				bw.newLine();
			}
		}
		bw.close();
		RecordingSuccessful.main(null);
		frame.dispose();
		MainBoard.frame.dispose();
	}

}
