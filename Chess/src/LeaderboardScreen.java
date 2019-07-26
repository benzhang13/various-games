//Ben Zhang
//ICS 4U
//Summitive Assignment
//Leaderboard Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.util.*;
import java.io.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class LeaderboardScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeaderboardScreen window = new LeaderboardScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public LeaderboardScreen() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLeaderboard = new JLabel("Leaderboard");
		lblLeaderboard.setBounds(119, 20, 77, 16);
		frame.getContentPane().add(lblLeaderboard);

		JLabel lblName1 = new JLabel("New label");
		lblName1.setBounds(119, 73, 120, 16);
		lblName1.setVisible(false);
		frame.getContentPane().add(lblName1);

		JLabel lblName2 = new JLabel("New label");
		lblName2.setBounds(119, 118, 120, 16);
		lblName2.setVisible(false);
		frame.getContentPane().add(lblName2);

		JLabel lblName3 = new JLabel("Name3");
		lblName3.setBounds(119, 163, 120, 16);
		lblName3.setVisible(false);
		frame.getContentPane().add(lblName3);

		JLabel lblName4 = new JLabel("New label");
		lblName4.setBounds(119, 211, 120, 16);
		lblName4.setVisible(false);
		frame.getContentPane().add(lblName4);

		JLabel lblRecord1 = new JLabel("New label");
		lblRecord1.setBounds(295, 73, 135, 16);
		lblRecord1.setVisible(false);
		frame.getContentPane().add(lblRecord1);

		JLabel lblRecord2 = new JLabel("New label");
		lblRecord2.setBounds(295, 118, 135, 16);
		lblRecord2.setVisible(false);
		frame.getContentPane().add(lblRecord2);

		JLabel lblRecord3 = new JLabel("New label");
		lblRecord3.setBounds(295, 163, 135, 16);
		lblRecord3.setVisible(false);
		frame.getContentPane().add(lblRecord3);

		JLabel lblRecord4 = new JLabel("New label");
		lblRecord4.setBounds(295, 211, 135, 16);
		lblRecord4.setVisible(false);
		frame.getContentPane().add(lblRecord4);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(179, 243, 117, 29);
		frame.getContentPane().add(btnMainMenu);

		//reads through accounts file, copies account information to a list, then to an array
		File file = new File("src/res/accounts.txt");
		Scanner sc = new Scanner(file);

		List<String> list = new ArrayList<>();

		while(sc.hasNextLine()) {
			String string = sc.nextLine();
			list.add(string);
		}

		String[][] listArray = new String[list.size()][3];

		for(int i = 0; i < list.size(); i++) {
			String[] row = new String[3];
			row = list.get(i).split(" ");
			listArray[i] = row;
		}

		//array of records, showing a players wins minus losses
		double[] records = new double[list.size()];

		for(int i = 0; i < records.length; i++) {
			String[] recordString = listArray[i][2].split("-");
			double[] wl = new double[2];

			wl[0] = Double.parseDouble(recordString[0]);
			wl[1] = Double.parseDouble(recordString[1]);

			records[i] = wl[0] - wl[1];
		}

		//sorts records, makes same changes to listarray so that accounts match records
		sort(listArray, records);
		
		//shows who has the best record, if statements prevent indexoutofbounds exception
		if(listArray.length >= 1) {
			String name1 = listArray[listArray.length-1][0];
			String record1 = listArray[listArray.length-1][2];
			
			lblName1.setText(name1);
			lblName1.setVisible(true);
			lblRecord1.setText(record1);
			lblRecord1.setVisible(true);
		}
		
		if(listArray.length >= 2) {
			String name2 = listArray[listArray.length-2][0];
			String record2 = listArray[listArray.length-2][2];
			
			lblName2.setText(name2);
			lblName2.setVisible(true);
			lblRecord2.setText(record2);
			lblRecord2.setVisible(true);
		}

		if(listArray.length >= 3) {
			String name3 = listArray[listArray.length-3][0];
			String record3 = listArray[listArray.length-3][2];
			
			lblName3.setText(name3);
			lblName3.setVisible(true);
			lblRecord3.setText(record3);
			lblRecord3.setVisible(true);
		}

		if(listArray.length >= 4) {
			String name4 = listArray[listArray.length-4][0];
			String record4 = listArray[listArray.length-4][2];
			
			lblName4.setText(name4);
			lblName4.setVisible(true);
			lblRecord4.setText(record4);
			lblRecord4.setVisible(true);
		}
	}

	//sorts an array, and makes the same change to a mirror array
	//uses selection sort
	private void sort(String[][] listArray, double[] records) {
		for (int i = 0; i < records.length-1; i++) {
			for (int j = 0; j < records.length-i-1; j++) {
				if (records[j] > records[j+1]) 
				{ 
					// swap temp and arr[i] 
					double temp = records[j]; 
					String[] tempRow = listArray[j];
					
					listArray[j] = listArray[j+1];
					listArray[j+1] = tempRow;
					records[j] = records[j+1]; 
					records[j+1] = temp; 
				} 
			}
		}
	}
}
