package junitTest.junit;


import org.apache.log4j.Logger;
import java.time.Duration;
import java.time.Instant;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runners.AllTests;

public class JARExecution {
	static final Logger logger = Logger.getLogger(JARExecution.class.getName());

	public static void main(String[] args) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		Instant t0 = Instant.now();
		Result result = junit.runClasses(TestExcelDataLoader.class);
		Duration time =  Duration.between(t0, Instant.now());

		int runCount = result.getRunCount();
		int failed = result.getFailureCount();
		int success = runCount - failed; 
		double percSuccess = Math.round(success/runCount)*100;
		double percFailed = Math.abs(100 - percSuccess);
		int min = (int)(time.getSeconds()/60);
		int sec = (int) (time.getSeconds() - (60*min));
		String report = 
					"Execuções: "+ runCount	+
					"\n Sucessos: "+ success + " (" + percSuccess+"%)"+
					"\n Falhas: " +failed + " (" + percFailed+"%)"+
					"n  Duração Total: " +  min +"min"+sec+"s" ; 
		logger.info(report);
	}
}
