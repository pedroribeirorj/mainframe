
package junitTest.junit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.loader.LoaderType;
import org.easetech.easytest.runner.DataDrivenTestRunner;

import org.junit.Test;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;
import com.utils.Data;
import com.utils.Utilities;
import junit.framework.Assert;
import static com.keyword.Keyword.*;
import com.jagacy.Connection;
import com.jagacy.Login;
import com.jagacy.util.JagacyException;
import com.keyword.Keyword;
import static com.utils.CommonMapping.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static com.utils.CommonMapping.*;
import static com.keyword.Keyword.*;
import org.apache.log4j.Logger;
@Suite
@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = { Utilities.SHEET_FULL_PATH }, loaderType = LoaderType.EXCEL)
//"resources/org/easetech/data/testExcelData.xlsx"
public class TestExcelDataLoader {

	private static Keyword kw;
	private static final Data data = new Data(true);
	private static Connection con;
	static final Logger logger = Logger.getLogger(TestExcelDataLoader.class.getName());

	@BeforeClass
	public static void setUpGone() throws Exception {
		Utilities.disableScreenshot();
//		if(data == null)
//			data = new Data();
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

	@AfterClass
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

	@After
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
	}

	@Before
	public void setUp()
			throws JagacyException, NoSuchAlgorithmException, UnsupportedEncodingException, InterruptedException {
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
		Utilities.save(msg, duration);
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
	public void EmissaoApolicesNaoMedicasUWAuto(@Param(name = "testcase") String testcase,
			@Param(name = "companyIngenium") String companyIngenium, @Param(name = "policy") String policy,
			@Param(name = "name") String name, @Param(name = "surname") String surname) throws Exception {

		logger.info("\n\n ########## Iniciando caso de teste EmissaoApolicesNaoMedicasUWAuto ########## \n\n");
		Utilities.CT_FILENAME = "EmissaoApolicesNaoMedicasUWAuto//" + testcase;
		data.setCompanyIngenium(companyIngenium);
		data.setName(name);
		data.setSurname(surname);
		data.setPolicy(policy);

		Instant t0 = Instant.now();
		if (testcase == null)
			System.exit(0);
		String result = new ingenium.scenarios.EmissaoApolicesNaoMedicasUWAuto(getDriver(), data).run();
		Duration d = Duration.between(t0, Instant.now());
		Assert.assertNull(saveResults(result, d.getSeconds()));
	}

	@Test
	public void taxacaoEmBloco(@Param(name = "testcase") String testcase,
			@Param(name = "companyIngenium") String companyIngenium, @Param(name = "policy") String policy,
			@Param(name = "client") String client, @Param(name = "surname") String surname,
			@Param(name = "uwde_stb2") String uwde_stb2, @Param(name = "uwde_r") String uwde_r) throws Exception {
		logger.info("\n \n ########## Iniciando caso de teste taxacaoEmBloco ########## \n\n");
		Utilities.CT_FILENAME = "taxacaoEmBloco//" + testcase;

		data.setCompanyIngenium(companyIngenium);
		data.setClient(client);
		data.setUwde_r(uwde_r);
		data.setUwde_stb2(uwde_stb2);
		data.setPolicy(policy);

		Instant t0 = Instant.now();
		String result = new ingenium.scenarios.TaxacaoEmBloco(getDriver(), data).run();
		Duration d = Duration.between(t0, Instant.now());
		Assert.assertNull(saveResults(result, d.getSeconds()));
	}
}