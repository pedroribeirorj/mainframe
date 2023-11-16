package com.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import com.controller.ExcelReaderController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilities {

	private static Date mv_sceneTime;
	public static final String SHEET_FULL_PATH = "C:/QM_NTX/INGENIUM/testExcelData.xlsx";
	public static String PathSave = new File(".xpto").getAbsolutePath().replaceAll(".xpto", "").replace("\\", "\\\\");
	private static String path = "C:/QM_NTX/INGENIUM/EVIDENCIAS/";
	public static int NUM_FILE = 1;
	public static String CT_FILENAME = "";
	public static boolean enableScreenshot = true;

	/**
	 * Armazenar dados na planilha de massa na linha que está sendo executada
	 * 
	 * @param campo : nome do campo na planilha de dados
	 * @param valor : valor a ser salvo na planilha de dados
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public static String decrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] decoded = Base64.decodeBase64(password.getBytes());
		return new String(decoded);
	}

	public static String encrypt(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		byte[] decoded = Base64.encodeBase64(password.getBytes());
		return new String(decoded);
	}

	public static void save(String msg, long duration) throws Exception {
		int row = ExcelReader.caseRow;
		int rightRow = setRightLine();
		if (rightRow == -1)
			throw new Exception("A coluna do caso de teste não foi encontrada na planilha");
		msg = (msg == null) ? "" : msg;
		String result = msg.trim().isEmpty() ? "Passed" : "Failed";
		Utilities.save(rightRow, result, msg, duration);

		ExcelReader.caseRow = row;
	}

	private static int setRightLine() {
		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		int column = erc.getColumn(ExcelReader.FIELD_TEST_CASE);
		int rightRow = 1;
		String sheetTestCase = ".";
		String testCase = CT_FILENAME.split("//")[1];
		while (!sheetTestCase.isEmpty()) {
			sheetTestCase = erc.read(rightRow, column).trim();
			sheetTestCase = sheetTestCase == null ? "" : sheetTestCase;
			if (sheetTestCase.equalsIgnoreCase(testCase)) {
				erc.close();
				return rightRow;
			} else
				rightRow++;
		}
		erc.close();
		return -1;
	}

	public static Data strtoData(String jsonData) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonData, Data.class);
	}

	public static String dataToJson(Data data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(data);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public static void armazenarDadosPlanilha(String field, String value) throws IOException, InterruptedException {
//		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
//		erc.writeData(field, value);
//		erc.save();
//		erc.close();
//	}

	public static void save(int row, String result, String msg, long duration)
			throws IOException, InterruptedException {
		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		erc.writeData(row, ExcelReader.FIELD_FINAL_RESULT, result);
		erc.writeData(row, ExcelReader.FIELD_DURATION, duration + "");
		erc.writeData(row, ExcelReader.FIELD_ERROR, msg);
		erc.save();
		erc.close();
	}

	public static String cf_getDiffTime(Date cv_date1, Date cv_date2) {
		if (cv_date2.getTime() - cv_date1.getTime() < 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(cv_date2);
			c.add(Calendar.DATE, 1);
			cv_date2 = c.getTime();
		}
		long ms = cv_date2.getTime() - cv_date1.getTime();
		long diffSeconds = ms / 1000 % 60;
		long diffMinutes = ms / (60 * 1000) % 60;
		long diffHours = ms / (60 * 60 * 1000);
		String hh = String.valueOf(diffHours);
		String mm = String.valueOf(diffMinutes);
		String ss = String.valueOf(diffSeconds);
		if (String.valueOf(diffHours).length() == 1)
			hh = "0" + hh;
		if (String.valueOf(diffMinutes).length() == 1)
			mm = "0" + mm;
		if (String.valueOf(diffSeconds).length() == 1)
			ss = "0" + ss;
		return hh + ":" + mm + ":" + ss;
	}

	public static String getOutputDirectory() {
		String outputDirectory = new File("output").getAbsoluteFile() + "/";
		if (!new File(outputDirectory).exists()) {
			new File(outputDirectory).mkdir();
		}
		return outputDirectory;
	}

	/**
	 * Inicia a contagem de tempo da execução do caso de teste
	 */
	public static void mf_startSceneTimer() {
		mv_sceneTime = new Date();
		mv_sceneTime.setTime(System.currentTimeMillis());
	}

	/**
	 * @Description Encerra a contagem de tempo da execução do caso de teste
	 * 
	 * @return: Retorna o tempo decorrido da execução
	 */
	public static Date mf_endSceneTimer() {
		Date sceneTimeCurrent = new Date();
		sceneTimeCurrent.setTime(System.currentTimeMillis());
		return sceneTimeCurrent;
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * @Description Armazena na planilha o tempo de execução do caso de teste
	 */
//	public static void ms_writeTimeOutput() throws IOException, InterruptedException {
//		armazenarDadosPlanilha("Execution Time", cf_getDiffTime(mv_sceneTime, mf_endSceneTimer()));
//	}

	/**
	 * @Description Get system date and time
	 */
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getPathResource() {
		return new File("src/main/resources").getAbsoluteFile() + "/";
	}

	/**
	 * Realiza Print de tela e armazena em formato .PNG o arquivo na pasta
	 * "Evidencias" na pasta raiz do teste
	 * 
	 */
	public final static void getScreenShot() {
		if (!enableScreenshot)
			return;
		try {
			String casoDeTeste = "TestCase";
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String dateTime = getDate();
			String date = dateTime.substring(0, 10);
			// String time = dateTime.substring(10);

			if(getCT_FILENAME().isEmpty())
				return;
			String newPath = path + "//" + getCT_FILENAME() + "//" + date;

			new File(newPath).mkdirs();

			ImageIO.write(image, "png", new File(newPath + "//" + NUM_FILE + ".png"));

			NUM_FILE++;
			// Integer.parseInt((Math.random() * 10 + "").replace(".", "").substring(0, 5))
			// + ".png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int countDaysBetweenTwoDates(String inputDate1, String inputDate2) throws ParseException {
		long diff;
		try {
			SimpleDateFormat myFormat = new SimpleDateFormat("ddMMyy");
			Date date1 = myFormat.parse(inputDate1);
			Date date2 = myFormat.parse(inputDate2);
			diff = date2.getTime() - date1.getTime();
		} catch (Exception e) {
			diff = 0;
		}
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

	}

	public static String countSpace(int val) {

		int length = (int) (Math.log10(val) + 1);

		String space = "   |";

		if (length == 2) {

			space = "  |";
		}
		if (length == 3) {

			space = " |";
		}

		return space;
	}

	public static void renewScreenshotFilename() {
		NUM_FILE = 1;
		CT_FILENAME = "";
	}

	public static String getCT_FILENAME() {
		return CT_FILENAME;
	}

	public static void setCT_FILENAME(String cT_FILENAME) {
		CT_FILENAME = cT_FILENAME;
	}

	public static void disableScreenshot() {
		enableScreenshot = false;

	}

	public static void enableScreenshot() {
		enableScreenshot = true;
	}

}
