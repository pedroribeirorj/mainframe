package com.jagacy;

import static com.keyword.Keyword.waitForTextLabel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.utils.Data;
import com.utils.Utilities;

import com.jagacy.util.JagacyException;
import com.keyword.Driver;
import com.keyword.Keyword;
import com.model.EntryField;

import static com.keyword.Keyword.*;
import static com.utils.CommonMapping.*;

public class Connection {
	String message = "";
	protected Data data;
	private static final String FILE_PROPERTIES_DIR = "C:/QM_NTX";
	private static Driver driver;

	public static String USER_AS400;// = "PSI009";
	private static String AS400_ENCODEDPASSWORD;// = "cHNpMDA5XzI=";
	public static String USER_INGENIUM;// = "PSI040";
	private static String INGENIUM_ENCODEDPASSWORD;// = "cHNpMTkwODk=";

	private static final String INGENIUM_COMPANY = "pb";
	private static final String WRK_ACT_JOB_SBS = "WRKACTJOB SBS(QINTER)";
	private static final String KILL_JOB_OPTION = "4";
	static final Logger logger = Logger.getLogger(Connection.class.getName());

	public Connection(Data data) {
		this.data = data;
	}

	public static void setAS400_ENCODEDPASSWORD(String aS400_ENCODEDPASSWORD) {
		AS400_ENCODEDPASSWORD = aS400_ENCODEDPASSWORD;
	}

	public static void setINGENIUM_ENCODEDPASSWORD(String iNGENIUM_ENCODEDPASSWORD) {
		INGENIUM_ENCODEDPASSWORD = iNGENIUM_ENCODEDPASSWORD;
	}

	public static Driver getDriver() {
		return driver;
	}

	public static String getIngeniumCompany() {
		return INGENIUM_COMPANY;
	}

	public static String getAs400Encodedpassword() {
		return AS400_ENCODEDPASSWORD;
	}

	public static String getIngeniumEncodedpassword() {
		return INGENIUM_ENCODEDPASSWORD;
	}

	private static void beforeTest() throws Exception {
		System.setProperty("jagacy.properties.dir", FILE_PROPERTIES_DIR);
		System.setProperty("test.window", "true");
	}

	public void connect(boolean connectOnlyAS400) throws IOException {
		try {
			logger.info("Conectando ao Terminal");
			beforeTest();
			driver = new Driver("test");
			driver.open();
			Login login = new Login(driver, data);
			this.message = login.connectAS400();
			logger.info("Script conectado ao terminal");
			if (this.message.isEmpty() && !connectOnlyAS400)
				this.message = login.connectIngenium();

		} catch (Exception e) {
			logger.error("Erro ao conectar com o servidor");
			return;
		}
	}

	public static Driver getKw() {
		return driver;
	}

	public static List<EntryField> readTableUsers(Keyword kw, EntryField fieldUser) throws JagacyException {
		List<EntryField> users = new ArrayList<EntryField>();
		for (int i = 1; i <= 9; i++) {
			String user = kw.read(fieldUser).trim();
			if (user.isEmpty())
				break;
			if (!user.equalsIgnoreCase(USER_AS400)) {
				fieldUser = new EntryField(fieldUser.getRow() + 1, fieldUser.getColumn(), fieldUser.getLength());
			} else {
				users.add(fieldUser);
				fieldUser = new EntryField(fieldUser.getRow() + 1, fieldUser.getColumn(), fieldUser.getLength());
			}
		}
		return users;
	}

	public static boolean isBottomPage() throws JagacyException {
		return read(FIELD_WRJACTJOB_BOTTOM).trim().contains("Bottom");
	}

	public static void closeJagacySession(Data data) throws IOException, JagacyException, InterruptedException {
		restartJagacySession(data);
		System.exit(0);
	}

	public static void restartJagacySession(Data data) throws IOException, JagacyException, InterruptedException {
		logger.info("Iniciando processo de remoção de jobs.");
		Connection c = new Connection(data);
		c.connect(true);
		Keyword kw = new Keyword(c.getDriver());
		kw.inputAndEnter(Login.FIELD_COMMAND, WRK_ACT_JOB_SBS);
		EntryField fieldUser = FIELD_WRJACTJOB_USER_01;
		List<EntryField> users = new ArrayList<EntryField>();

		while (!isBottomPage()) {
			users = readTableUsers(kw, fieldUser);
			if (!users.isEmpty()) {
				endjob(users);
				users = new ArrayList<EntryField>();
			}
			kw.pagedown();
		}

		users = readTableUsers(kw, fieldUser);
		if (!users.isEmpty()) {
			endjob(users);
		}
		logger.info("Processo de remoção de jobs concluído.");
		c.getDriver().close();

	}

	private static void endjob(List<EntryField> users) throws JagacyException, InterruptedException {
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			EntryField entryField = (EntryField) iterator.next();
			entryField.setColumn(FIELD_WRJACTJOB_OPT_01.getColumn());
			inputAndEnter(entryField, KILL_JOB_OPTION);
			String confirmEndJob = read(FIELD_WRJACTJOB_CONFIRM_END_JOB).trim();
			if (confirmEndJob.contains("Confirm") && read(USER,8).equalsIgnoreCase(USER_AS400)) {
				enter();
				if (read(CONFIRMATION.getRow(), CONFIRMATION.getColumn(), CONFIRMATION.getLength())
						.contains("Current job not allowed as job name on this command.")) {
					delete(entryField);
				} else
					logger.info("Job encerrado com sucesso");
			}
		}

	}
}