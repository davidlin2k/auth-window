import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Label;

import java.sql.*;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class login {

	protected Shell shlAuthWindow;
	private Text username_box;
	private Text password_box;
	private Button loginButton;
	private Button registerButton;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			login window = new login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlAuthWindow.open();
		shlAuthWindow.layout();
		while (!shlAuthWindow.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlAuthWindow = new Shell();
		shlAuthWindow.setSize(338, 149);
		shlAuthWindow.setText("Auth Window");
		shlAuthWindow.setLayout(null);
		
		Label lblNewLabel = new Label(shlAuthWindow, SWT.NONE);
		lblNewLabel.setBounds(5, 27, 54, 16);
		lblNewLabel.setText("Username");
		
		username_box = new Text(shlAuthWindow, SWT.BORDER);
		username_box.setBounds(64, 26, 252, 19);
		
		Label lblNewLabel_1 = new Label(shlAuthWindow, SWT.NONE);
		lblNewLabel_1.setBounds(5, 51, 49, 16);
		lblNewLabel_1.setText("Password");
		
		password_box = new Text(shlAuthWindow, SWT.BORDER | SWT.PASSWORD);
		password_box.setBounds(64, 50, 251, 19);
		
		loginButton = new Button(shlAuthWindow, SWT.NONE);
		loginButton.setBounds(244, 75, 71, 21);
		loginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (username_box.getText().isBlank() || password_box.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid Info", "Info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				login(username_box.getText(), password_box.getText());
			}
		});
		loginButton.setText("Login");
		
		registerButton = new Button(shlAuthWindow, SWT.NONE);
		registerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (username_box.getText().isBlank() || password_box.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "Invalid Info", "Info", JOptionPane.ERROR_MESSAGE);
					return;
				}
				register(username_box.getText(), password_box.getText());
			}
		});
		registerButton.setText("Register");
		registerButton.setBounds(160, 75, 71, 21);
	}
	
	public static void login(String username, String password) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			//here localhost is your hose ip, database_name is database name
			"jdbc:mysql://localhost/database_name","username","password"
					);  
			Statement stmt=con.createStatement();
			//get the rows with the same username as the user input		
			ResultSet rs=stmt.executeQuery("SELECT * FROM `users` WHERE `username` LIKE '" + username + "'");
			
			if(rs.next() && password.equals(rs.getString(2))) //check if user exists and password is correct
				JOptionPane.showMessageDialog(null, "Access granted!", "Info", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Invalid info", "Info", JOptionPane.ERROR_MESSAGE);
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void register(String username, String password) {
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			//here localhost is your hose ip, database_name is database name
			"jdbc:mysql://localhost/database_name","username","password"
					);  
			Statement stmt=con.createStatement();
			//get the rows with the same username as the user input		
			ResultSet rs=stmt.executeQuery("SELECT * FROM `users` WHERE `username` LIKE '" + username + "'");
			
			if(!rs.next()) { //check if user already exist
				stmt.executeUpdate("INSERT INTO `users` (`username`, `password`) VALUES ('" + username + "', '" + password + "')");
				JOptionPane.showMessageDialog(null, "Success!", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "User already exist", "Info", JOptionPane.ERROR_MESSAGE);
			}
			con.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
