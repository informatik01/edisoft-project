package ee.edisoft.security;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

/**
 * SecurePasswordEncoder is a class that represents Swing GUI application.
 * This application is used to securely encode passwords.
 * It outputs the salted password hash
 * along with the respective salt that was used for hashing.<br />
 * This application uses {@link SecureEncoder} class for the password encoding and salt generation.
 *
 */
public class SecurePasswordEncoder extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int passwordLength = 5;
	private static final String HELP_FILE = "README.txt";
	private JTextField passwordTextField;
	private JTextField hashTextField;
	private JTextField saltTextField;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SecurePasswordEncoder frame = new SecurePasswordEncoder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 */
	public SecurePasswordEncoder() {

		initComponents();
	}
	
	/**
	 * Initializes GUI application components.
	 */
	private void initComponents() {
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(500, 0));
		getContentPane().setMinimumSize(new Dimension(500, 0));
		getContentPane().setMaximumSize(new Dimension(500, 2147483647));
		setMinimumSize(new Dimension(500, 0));
		setMaximumSize(new Dimension(500, 2147483647));
		setPreferredSize(new Dimension(500, 0));
		setTitle("Secure Password Encoder");
		setBounds(100, 100, 617, 490);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblHeader = new JLabel("Secure Password Encoder");
		lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblHeader.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblHeader.setOpaque(true);
		lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeader.setBackground(new Color(255, 215, 0));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(500, 10));
		inputPanel.setMaximumSize(new Dimension(500, 32767));
		
		JPanel resultPanel = new JPanel();
		resultPanel.setName("");
		resultPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton btnClearFields = new JButton("Clear Fields");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				passwordTextField.setText(null);
				hashTextField.setText(null);
				saltTextField.setText(null);
			}
		});
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		btnExit.setMinimumSize(new Dimension(95, 28));
		btnExit.setMaximumSize(new Dimension(95, 28));
		btnExit.setPreferredSize(new Dimension(95, 28));
		
		JButton btnEncode = new JButton("Encode!");
		btnEncode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				passwordTextFieldHandler(event);
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(inputPanel, 500, 599, Short.MAX_VALUE)
						.addComponent(lblHeader, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
						.addComponent(resultPanel, 500, 599, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnClearFields, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
							.addGap(368)
							.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnEncode, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHeader, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnEncode)
					.addGap(18)
					.addComponent(resultPanel, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClearFields)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
		JLabel lblPasswordHash = new JLabel("Salted Password Hash");
		lblPasswordHash.setPreferredSize(new Dimension(150, 16));
		lblPasswordHash.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		hashTextField = new JTextField();
		hashTextField.setBackground(new Color(240, 255, 240));
		hashTextField.setEditable(false);
		hashTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
		hashTextField.setMaximumSize(new Dimension(500, 2147483647));
		hashTextField.setPreferredSize(new Dimension(500, 28));
		hashTextField.setMinimumSize(new Dimension(500, 28));
		hashTextField.setColumns(64);
		
		JLabel lblSalt = new JLabel("Salt used for the Password");
		lblSalt.setPreferredSize(new Dimension(150, 16));
		lblSalt.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		saltTextField = new JTextField();
		saltTextField.setBackground(new Color(240, 255, 240));
		saltTextField.setEditable(false);
		saltTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
		saltTextField.setMaximumSize(new Dimension(500, 2147483647));
		saltTextField.setPreferredSize(new Dimension(500, 28));
		saltTextField.setMinimumSize(new Dimension(500, 28));
		saltTextField.setColumns(64);
		GroupLayout gl_resultPanel = new GroupLayout(resultPanel);
		gl_resultPanel.setHorizontalGroup(
			gl_resultPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_resultPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_resultPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(hashTextField, GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
						.addComponent(saltTextField, GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
						.addComponent(lblSalt, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPasswordHash, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_resultPanel.setVerticalGroup(
			gl_resultPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_resultPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPasswordHash)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(hashTextField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSalt, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(saltTextField, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(24, Short.MAX_VALUE))
		);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(hashTextField, popupMenu);
		addPopup(saltTextField, popupMenu);
		
		JMenuItem copyMenuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
		copyMenuItem.setText("Copy");
		popupMenu.add(copyMenuItem);
		
		resultPanel.setLayout(gl_resultPanel);
		
		JLabel lblPassword = new JLabel("Enter Password");
		lblPassword.setFont(new Font("SansSerif", Font.PLAIN, 14));
		
		passwordTextField = new JTextField();
		passwordTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				passwordTextFieldHandler(event);
			}
		});
		passwordTextField.setPreferredSize(new Dimension(500, 28));
		passwordTextField.setAutoscrolls(false);
		passwordTextField.setMaximumSize(new Dimension(500, 2147483647));
		passwordTextField.setMinimumSize(new Dimension(500, 28));
		passwordTextField.setFont(new Font("Monospaced", Font.PLAIN, 14));
		passwordTextField.setColumns(64);
		
		GroupLayout gl_inputPanel = new GroupLayout(inputPanel);
		gl_inputPanel.setHorizontalGroup(
			gl_inputPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_inputPanel.createSequentialGroup()
					.addGroup(gl_inputPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPassword)
						.addComponent(passwordTextField, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_inputPanel.setVerticalGroup(
			gl_inputPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_inputPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPassword)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		inputPanel.setLayout(gl_inputPanel);
		getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		fileMenu.add(exitMenuItem);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		final JMenuItem aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutInfo();
			}
		});
		
		JMenuItem helpMenuItem = new JMenuItem("Help");
		helpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File helpFile = getResourceAsFile(HELP_FILE);
				try {
					Desktop.getDesktop().open(helpFile);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"No help file with name \"" + HELP_FILE + "\" found.",
							"Exception", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		helpMenu.add(helpMenuItem);
		helpMenu.add(aboutMenuItem);
	}
	
	/**
	 * Helper method to add popup menu.
	 * 
	 * @param component The component for which to add mouse listener
	 * @param popup The JPopupMenu to use within mouse listener
	 */
	private static void addPopup(final Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (((JTextComponent) component).getText().isEmpty()) {
					return;
				}
				((JTextComponent) component).selectAll();
	            component.requestFocusInWindow();
				showMenu(e);
			}
			
			public void mouseReleased(MouseEvent e) {
				mousePressed(e);
			}
			
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * Helper method to show dialog box with the "About" info.
	 */
	private void showAboutInfo() {
		JOptionPane.showMessageDialog(this,
				"Secure Password Encoder version 1.0\n\n" +
				"Author: Levan Kekelidze\n" +
				"email: informatik0101@gmail.com", "About Secure Password Encoder", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Helper method to get a resource as a {@link File}.
	 * This application will be distributed as a runnable JAR file and this method
	 * makes makes it possible to open a resource contained inside this runnable JAR.
	 * 
	 * @param name The name of the resourse to represent as a File object
	 * @return The File object that represents a resource
	 */
	private File getResourceAsFile(String name) {
		File tempFile = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
			tempFile = File.createTempFile("README", ".txt");
			tempFile.deleteOnExit();
			out = new FileOutputStream(tempFile);
			
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Could not load resource from JAR.", "Exception", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				in.close();
			} catch (IOException ignore) {}
			
			try {
				out.close();
			} catch (IOException ignore) {}
		}
		
		return tempFile;
	}

	/**
	 * Custom event handler for the password text field.
	 * 
	 * @param event The event object representing the generated event
	 */
	private void passwordTextFieldHandler(ActionEvent event) {
		String password = passwordTextField.getText().trim();
		if (password.length() < passwordLength) {
			Toolkit.getDefaultToolkit().beep();
		    JOptionPane.showMessageDialog(this,
		    		"Password must be at least " + passwordLength + " characters.",
		    									"Reminder", JOptionPane.INFORMATION_MESSAGE);
		    passwordTextField.setText(password);
		    return;
		}
		
		String salt = SecureEncoder.generateSalt();
		String saltedPasswordHash = null;
		try {
			saltedPasswordHash = SecureEncoder.computeHash(password, salt);
		} catch (NoSuchAlgorithmException e) {
			Toolkit.getDefaultToolkit().beep();
		    JOptionPane.showMessageDialog(null, "Specify valid algorithm for MessageDigest.",
		    									"Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		hashTextField.setText(saltedPasswordHash);
		saltTextField.setText(salt);
	}
}
