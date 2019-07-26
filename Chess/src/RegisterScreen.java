//Ben Zhang
//ICS 4U
//Summitive Assignment
//Registration Screen

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.awt.Font;

public class RegisterScreen {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JButton btnRegister;
	private JLabel lblError;
	private JButton btnMainMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterScreen window = new RegisterScreen();
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
	public RegisterScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblRegister = new JLabel("Register for an Account");
		lblRegister.setBounds(157, 38, 197, 29);
		frame.getContentPane().add(lblRegister);

		textField = new JTextField();
		textField.setBounds(167, 92, 267, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(79, 98, 87, 16);
		frame.getContentPane().add(lblUsername);

		passwordField = new JPasswordField();
		passwordField.setBounds(167, 132, 267, 26);
		frame.getContentPane().add(passwordField);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(79, 137, 61, 16);
		frame.getContentPane().add(lblPassword);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(167, 172, 267, 26);
		frame.getContentPane().add(passwordField_1);

		JLabel lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setBounds(32, 172, 134, 16);
		frame.getContentPane().add(lblConfirmPassword);

		btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					register();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRegister.setBounds(177, 221, 117, 29);
		frame.getContentPane().add(btnRegister);

		lblError = new JLabel("");
		lblError.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblError.setBounds(79, 203, 325, 16);
		lblError.setVisible(false);
		frame.getContentPane().add(lblError);
		
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenuScreen.main(null);
				frame.dispose();
			}
		});
		btnMainMenu.setBounds(23, 221, 117, 29);
		frame.getContentPane().add(btnMainMenu);
	}

	private void register() throws IOException {
		
		//reads through accounts file, copies data to a list
		File file = new File("src/res/accounts.txt");
		Scanner sc = new Scanner(file);
		Scanner scanner = new Scanner(file);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		List<String> accList = new ArrayList<String>();

		//assumes the username is not in use
		boolean unique = true;

		int numAccounts = 0;
		while(scanner.hasNextLine()) {
			numAccounts += 1;
			scanner.nextLine();
		}
		String[] acc = new String[numAccounts];
		String username = textField.getText();
		String pass = String.valueOf(passwordField.getPassword());
		String passConfirm = String.valueOf(passwordField_1.getPassword());

		//input sanitation
		if(textField.getText().contains(" ") || pass.contains(" ")) {
			lblError.setText("username and password cannot contain spaces.");
			lblError.setVisible(true);
		} else {
			for(int i = 0; i < acc.length; i++) {
				acc[i] = sc.next();
				if(acc[i].equalsIgnoreCase(username)) {
					unique = false;
				}
				sc.nextLine();
			}
			if(unique) {
				//writes to accounts file
				if(pass.equals(passConfirm)) {
					if(!username.equals("") && !pass.equals("")) {

						writer.newLine();
						writer.append(username + " " + pass + " 0-0");

						RegistrationSuccessful.main(null);
						frame.dispose();
					} else {
						lblError.setText("username and password fields cannot be empty");
						lblError.setVisible(true);
					}
				} else {
					lblError.setText("Passwords do not match.");
					lblError.setVisible(true);
				}
			} else {
				lblError.setText("This username has been taken.");
				lblError.setVisible(true);
			}
		}
		writer.close();
	}
}
