//Ben Zhang
//ICS 4U
//Summitive Assignment
//Login Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.util.*;
import javax.swing.SwingConstants;
public class LoginWindow {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JTextField textField_1;
	private JPasswordField passwordField_1;
	private JLabel lblErrors = new JLabel("errors");
	private JLabel lblErrors_1 = new JLabel("errors");
	private JButton btnLogIn = new JButton("Log In");
	private JButton btnLogIn_1 = new JButton("Log In");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
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
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		chessData.blackName = "";
		chessData.whiteName = "";
		chessData.winnerName = "";
		chessData.loserName = "";

		frame = new JFrame();
		frame.setBounds(100, 100, 550, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(97, 89, 130, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(97, 168, 130, 26);
		frame.getContentPane().add(passwordField);

		JLabel lblEnterWhitesLogin = new JLabel("Enter White's login information:");
		lblEnterWhitesLogin.setBounds(21, 26, 221, 16);
		frame.getContentPane().add(lblEnterWhitesLogin);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(97, 61, 78, 16);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(97, 140, 78, 16);
		frame.getContentPane().add(lblPassword);

		JLabel lblEnterBlacksLogin = new JLabel("Enter Black's login information:");
		lblEnterBlacksLogin.setBounds(284, 26, 221, 16);
		frame.getContentPane().add(lblEnterBlacksLogin);

		textField_1 = new JTextField();
		textField_1.setBounds(375, 89, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblUsername_1 = new JLabel("Username: ");
		lblUsername_1.setBounds(375, 61, 78, 16);
		frame.getContentPane().add(lblUsername_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(375, 168, 130, 26);
		frame.getContentPane().add(passwordField_1);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setBounds(375, 140, 78, 16);
		frame.getContentPane().add(lblPassword_1);

		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//reads all account names and passwords from accounts file, adds them to list
				File file = new File("src/res/accounts.txt");
				String nameP1 = textField.getText();
				String passP1 = new String(passwordField.getPassword());

				List<String> list = new ArrayList<>();

				try {
					Scanner sc = new Scanner(file);

					while(sc.hasNextLine()) {
						String ln = sc.nextLine();
						list.add(ln);
					}

					//creates array from list
					String[][] listArray = new String[list.size()][3];

					for(int i = 0; i < list.size(); i++) {
						String[] row = new String[3];
						row = list.get(i).split(" ");
						listArray[i] = row;
					}

					//calls method to check p1 login
					checkLoginP1(nameP1,passP1,listArray);

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogIn.setBounds(97, 206, 117, 29);
		frame.getContentPane().add(btnLogIn);

		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chessData.blackName = "";
				chessData.whiteName = "";
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(223, 127, 117, 29);
		frame.getContentPane().add(btnMainMenu);

		lblErrors.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrors.setBounds(21, 241, 250, 16);
		lblErrors.setVisible(false);
		frame.getContentPane().add(lblErrors);
		btnLogIn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//reads all accounts info
				File file = new File("src/res/accounts.txt");
				String nameP1 = textField_1.getText();
				String passP1 = new String(passwordField_1.getPassword());

				List<String> list = new ArrayList<>();

				try {
					Scanner sc = new Scanner(file);

					while(sc.hasNextLine()) {
						String ln = sc.nextLine();
						list.add(ln);
					}

					String[][] listArray = new String[list.size()][3];

					for(int i = 0; i < list.size(); i++) {
						String[] row = new String[3];
						row = list.get(i).split(" ");
						listArray[i] = row;
					}

					//runs method that checks p2 login info
					checkLoginP2(nameP1,passP1,listArray);

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnLogIn_1.setBounds(375, 206, 117, 29);
		frame.getContentPane().add(btnLogIn_1);

		lblErrors_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrors_1.setBounds(310, 241, 231, 16);
		lblErrors_1.setVisible(false);
		frame.getContentPane().add(lblErrors_1);
	}

	private void checkLoginP1(String username, String password, String[][] list) {
		boolean accExists = false;

		//if the account is in use already
		if(username.equals(chessData.blackName)) {
			if(!username.equals("")) {
				lblErrors.setText("Account already in use");
				lblErrors.setVisible(true);
			}
		} else {
			for(int i = 0; i < list.length; i++) {
				if(list[i][0].equals(username)) {
					accExists = true;
					//if passwords are the same
					if(list[i][1].equals(password)) {
						//logs in
						chessData.whiteName = username;
						lblErrors.setText("Login Successful!");
						lblErrors.setVisible(true);
						btnLogIn.setVisible(false);
						//if both players are logged in
						if(!chessData.blackName.equals("")) {
							MainBoard.main(null);
							frame.dispose();
						}
					} else {
						lblErrors.setText("Incorrect login");
						lblErrors.setVisible(true);
					}
				}
			}
			if(!accExists && chessData.whiteName.equals("")) {
				lblErrors.setText("Account does not exist");
				lblErrors.setVisible(true);
			}
		}
	}
	private void checkLoginP2(String username, String password, String[][] list) {
		boolean accExists = false;

		if(username.equals(chessData.whiteName)) {
			if(!username.equals("")) {
				lblErrors_1.setText("Account already in use");
				lblErrors_1.setVisible(true);
			}
		} else {
			for(int i = 0; i < list.length; i++) {
				if(list[i][0].equals(username)) {
					accExists = true;
					if(list[i][1].equals(password)) {
						chessData.blackName = username;
						lblErrors_1.setText("Login Successful!");
						lblErrors_1.setVisible(true);
						btnLogIn_1.setVisible(false);
						if(!chessData.whiteName.equals("")) {
							MainBoard.main(null);
							frame.dispose();
						}
					} else {
						lblErrors_1.setText("Incorrect login");
						lblErrors_1.setVisible(true);
					}
				}
			}
			if(!accExists && chessData.blackName.equals("")) {
				lblErrors_1.setText("Account does not exist.");
				lblErrors_1.setVisible(true);
			}
		}
	}

}
