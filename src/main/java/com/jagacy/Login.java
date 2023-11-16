package com.jagacy;

import com.utils.Data;
import com.utils.Utilities;

import static com.keyword.Keyword.getDriver;
import static com.keyword.Keyword.waitForTextLabel;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.jagacy.util.JagacyException;
import com.keyword.Driver;
import com.model.EntryField;
import com.model.LabelField;

public class Login {
	private Driver driver;
	private String screenCrc = "0xcba90787";

	public static boolean login = true;
	private LabelField SIGN_ON = new LabelField(0, 27, "SIGN ON (POWER8 TESTE INTEGRADO)");
	private LabelField SIGN_ON_INFORMATION = new LabelField(0, 30, "Sign-on Information");
	public static LabelField LOGIN_OK = new LabelField(1, 61, "System:");
	private LabelField SIGN_ON_INFORMATION_INGENIUM = new LabelField(0, 26, "APPLICATION SIGN-ON");
	public static LabelField LABEL_SELECTION_OR_COMMAND = new LabelField(19, 2, "Selection or command");

	private EntryField FIELD_USER = new EntryField(14, 15);
	private EntryField FIELD_PASSWORD = new EntryField(15, 15);
	private EntryField FIELD_ERROR = new EntryField(23, 0);

	public static EntryField FIELD_COMMAND = new EntryField(19, 6);

	private EntryField FIELD_USERINGENIUM = new EntryField(14, 17);
	private EntryField FIELD_PASSWORDINGENIUM = new EntryField(14, 35);
	private EntryField FIELD_COMPANYINGENIUM = new EntryField(14, 53);

	private Data data;
	static final Logger logger = Logger.getLogger(Login.class.getName());


	public Login() {

	}

	public Login(final Driver session, Data data) throws JagacyException {
		this.data = data;
		this.driver = session;
		if (!driver.waitForTextLabel(SIGN_ON, driver.DEFAULT_TIMEOUT)) {
			throw new IllegalStateException("Not Home screen!");
		}

		if (driver.getCrc32() != Long.decode(screenCrc)) {
			throw new IllegalStateException("Home Screen has been changed!");
		}
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public static void relogin(Data data, Driver driver) throws NoSuchAlgorithmException, UnsupportedEncodingException, JagacyException, InterruptedException {
		Login l = new Login();
		l.setData(data);
		l.setDriver(driver);
		l.connectIngenium();
	}
	
	public static void validateLogin(Data data, Driver driver) throws NoSuchAlgorithmException, UnsupportedEncodingException, JagacyException, InterruptedException {
		if (waitForTextLabel(Login.LOGIN_OK)) {
			logger.info("Realizando nova tentativa de login.");
			Login.relogin(data,driver);
		}
	}

	public String connectIngenium()
			throws JagacyException, InterruptedException, NoSuchAlgorithmException, UnsupportedEncodingException {
		driver.sendKeys(FIELD_COMMAND, "STRCICSUSR P552 APLS");
		driver.pressEnter();

		driver.waitForChange(driver.HARD_TIMEOUT);
		if (isAllocatedAnotherJob()) {
			driver.pressEnter();
			driver.waitForChange(driver.DEFAULT_TIMEOUT);
		}
		driver.sendKeys(FIELD_USERINGENIUM, data.getUsernameIngenium());
		driver.sendKeys(FIELD_PASSWORDINGENIUM, Utilities.decrypt(data.getPasswordIngenium()));
		driver.sendKeys(FIELD_COMPANYINGENIUM, data.getCompanyIngenium());
		driver.pressEnter();
		driver.waitForChange(driver.DEFAULT_TIMEOUT);

		if (!driver.waitForTextLabel(SIGN_ON_INFORMATION_INGENIUM))
			return driver.readRow(FIELD_ERROR.getRow());
		logger.info("Conectado ao Ingenium");
		return "";
	}

	public boolean isAllocatedAnotherJob() throws JagacyException {
		LabelField DISPLAY_PROGRAM_MESSAGE_INFO_JOB = new LabelField(3, 1,
				"Message queue " + data.getUsername().toUpperCase() + " is allocated to another job.");
		return driver.waitForTextLabel(DISPLAY_PROGRAM_MESSAGE_INFO_JOB);
	}

	public String connectAS400()
			throws JagacyException, InterruptedException, NoSuchAlgorithmException, UnsupportedEncodingException {
		driver.sendKeys(FIELD_USER, getData().getUsername());
		driver.sendKeys(FIELD_PASSWORD, Utilities.decrypt(getData().getPassword()));
		driver.pressEnter();
		driver.waitForTextLabel(SIGN_ON_INFORMATION);
		driver.pressEnter();
		if (isAllocatedAnotherJob()) {
			driver.pressEnter();
			driver.waitForChange(driver.DEFAULT_TIMEOUT);
		}
		if (!driver.waitForTextLabel(LOGIN_OK))
			return driver.readRow(FIELD_ERROR.getRow());
		return "";
	}

	public Data getData() {
		return data;
	}

}
