package com.model;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jagacy.Connection;
import com.utils.Utilities;

public class AuthIO extends JDialog {

	private static final long serialVersionUID = 1L;

	static boolean repetirAutenticacaoAS400ParaIngenium;

	public static void loginIngenium() throws Exception {
		JTextField login = new JTextField();
		JLabel label_login = new JLabel("Usuario:");
		JLabel label_password = new JLabel("Senha:");
		JPasswordField password = new JPasswordField();
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/img/sulamericaLogo.png");
		Object[] array = { icon, label_login, login, label_password, password };
		int res = JOptionPane.showConfirmDialog(null, array, "Login Ingenium", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (res == JOptionPane.OK_OPTION) {
			if (login.getText().trim().isEmpty() || new String(password.getPassword()).isEmpty()) {
				JOptionPane.showMessageDialog(null, "Usuario ou senha não pode estar em branco !", "Erro Login",
						JOptionPane.OK_OPTION);
				loginIngenium();
				System.exit(0);
			}
			Connection.USER_INGENIUM = login.getText().trim();
			Connection.setINGENIUM_ENCODEDPASSWORD(Utilities.encrypt(new String(password.getPassword())));
		}

		if (res == JOptionPane.CANCEL_OPTION || res == -1)
			System.exit(0);
	}

	public static void login() {
		try {
			JTextField login = new JTextField();
			JLabel label_login = new JLabel("Usuario:");
			JLabel label_password = new JLabel("Senha:");
			JPasswordField password = new JPasswordField();
			JCheckBox checkbox = new JCheckBox("Replicar usuário e senha para Ingenium");
			ImageIcon icon = new ImageIcon(
					System.getProperty("user.dir") + "/src/main/resources/img/sulamericaLogo.png");
			Object[] array = { icon, label_login, login, label_password, password, checkbox };
			int res = JOptionPane.showConfirmDialog(null, array, "Login AS400", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			if (res == JOptionPane.OK_OPTION) {
				if (login.getText().trim().isEmpty() || new String(password.getPassword()).isEmpty()) {
					JOptionPane.showMessageDialog(null, "Usuario ou senha não pode estar em branco !", "Erro Login",
							JOptionPane.OK_OPTION);
					login();
					System.exit(0);
				}
				Connection.USER_AS400 = login.getText().trim();
				Connection.setAS400_ENCODEDPASSWORD(Utilities.encrypt(new String(password.getPassword())));
				repetirAutenticacaoAS400ParaIngenium = checkbox.isSelected();
				if (repetirAutenticacaoAS400ParaIngenium) {
					Connection.USER_INGENIUM = Connection.USER_AS400;
					Connection.setINGENIUM_ENCODEDPASSWORD(Connection.getAs400Encodedpassword());
				} else {

					loginIngenium();
				}
			}

			if (res == JOptionPane.CANCEL_OPTION || res == -1)
				System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) throws Exception {
//		login();
//	}
}