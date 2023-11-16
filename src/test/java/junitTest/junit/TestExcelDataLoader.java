
package junitTest.junit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

import org.testng.annotations.*;

import com.utils.Data;
import com.utils.Utilities;
import junit.framework.Assert;
import static com.keyword.Keyword.*;

import com.controller.ExcelReaderController;
import com.jagacy.Connection;
import com.jagacy.Login;
import com.jagacy.util.JagacyException;
import com.keyword.Keyword;
import static com.utils.CommonMapping.*;
import static com.utils.CommonMapping.*;
import static com.keyword.Keyword.*;
import org.apache.log4j.Logger;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

public class TestExcelDataLoader {

	private static Keyword kw;
	static Data data = new Data(true);
	private static Connection con;
	static final Logger logger = Logger.getLogger(TestExcelDataLoader.class.getName());

	@BeforeSuite
	public static void setUpGone() throws Exception {
		Utilities.disableScreenshot();
		con = new Connection(data);
		con.connect(false);
		kw = new Keyword(con.getDriver());
		Utilities.enableScreenshot();
	}

	public static void resetClass() throws JagacyException {
		con.getDriver().close();
		kw = null;
		// data = null;
		con = null;
	}

	@AfterSuite
	public static void closeConnections() throws IOException, JagacyException, InterruptedException {
		resetClass();
		try {
			Connection.closeJagacySession(new Data(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void restartConnections() throws IOException, JagacyException, InterruptedException {
		resetClass();
		try {
			Connection.restartJagacySession(new Data(false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() throws Exception {
		// returnToIngeniumHome();
		if (!kw.isKeyboarLocked()) {
			Keyword.F12();
		} else {
			logger.error("Aplicação se encontra em locked");
			logger.error("O script será reiniciado.");
			restartConnections();
			setUpGone();
		}
		Utilities.renewScreenshotFilename();
		ExcelReaderController.addRow();
	}

	@BeforeTest
	public void setUp()
			throws JagacyException, NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException {
		data = ExcelReaderController.getCurrentData();
		Login.validateLogin(data, getDriver());
		String statusAdmin = read(FIELD_ADMIN_SYSTEM_STATUS, 8);
		String statusNewBusiness = read(FIELD_NEW_BUSINESS_STATUS, 8);

		if (!statusAdmin.equalsIgnoreCase("Connect") && !statusNewBusiness.equalsIgnoreCase("Connect")) {
			String errorMsg = "O ambiente não está disponível para uso. Tente mais tarde.";
			logger.error(errorMsg);
			throw new JagacyException(errorMsg);
		}
	}

	private String saveResults(String msg, long duration) throws Exception {
		ExcelReaderController.save(msg, duration);
		String result = msg == null ? "" : msg;
		String formattedMsg = "";
		if (result.isEmpty())
			formattedMsg = "\n Result: Passed \n Time: " + duration + "s";
		else {
			formattedMsg = "\n Result: Failed \n Time: " + duration + "s";
			formattedMsg += "\n Cause:" + msg;
		}

		logger.info(formattedMsg);

		return msg;
	}

//	@Test
	public void EmissaoApolicesNaoMedicasUWAuto() throws Exception {

		logger.info("\n\n ########## Iniciando caso de teste EmissaoApolicesNaoMedicasUWAuto ########## \n\n");
		Utilities.CT_FILENAME = "EmissaoApolicesNaoMedicasUWAuto//" + data.getTestcase();
		Instant t0 = Instant.now();
		String result = new ingenium.scenarios.EmissaoApolicesNaoMedicasUWAuto(getDriver(), data).run();
		Duration d = Duration.between(t0, Instant.now());
		Assert.assertNull(saveResults(result, d.getSeconds()));
	}

	@Test
	public void taxacaoEmBloco() throws Exception {
		logger.info("\n \n ########## Iniciando caso de teste taxacaoEmBloco ########## \n\n");
		Instant t0 = Instant.now();
		String result = new ingenium.scenarios.TaxacaoEmBloco(getDriver(), data).run();
		Duration d = Duration.between(t0, Instant.now());
		Assert.assertNull(saveResults(result, d.getSeconds()));
		logger.info("");
	}
}