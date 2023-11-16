package junitTest.junit;

import org.apache.log4j.Logger;
import org.testng.TestNG;

import com.controller.ExcelReaderController;

import java.time.Duration;
import java.time.Instant;

public class JARExecution {
	static final Logger logger = Logger.getLogger(JARExecution.class.getName());

	public static void main(String[] args) {
		TestNG tst = new TestNG();
		Instant t0 = Instant.now();
		tst.setTestClasses(new Class[] { TestExcelDataLoader.class });

		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		while (!erc.endOfFile()) {
			tst.run();
		}
	}
}
