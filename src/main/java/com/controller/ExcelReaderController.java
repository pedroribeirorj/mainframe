package com.controller;

import java.io.File;
import java.io.IOException;

import com.sheet.ExcelReader;
import com.utils.Data;
import com.utils.Utilities;

public class ExcelReaderController {
	private ExcelReader er;

	public static final int TAB_RULES = 0;
	public static final int TAB_RESULTS = 1;
	public static final String TEXT_TAB_RESULTS = "test cases";
	public static final String TEXT_TAB_RULES = "rules";
	public static final String SHEET_FULL_PATH = "C:/QM_NTX/INGENIUM/testExcelData.xlsx";

	public ExcelReaderController(int tab) {
		er = new ExcelReader();
		switch (tab) {
		case TAB_RESULTS:
			er.ds_startExcel(SHEET_FULL_PATH, TEXT_TAB_RESULTS);
			break;
		case TAB_RULES:
			er.ds_startExcel(SHEET_FULL_PATH, TEXT_TAB_RULES);
		}
	}

	public int getColumn(String column) {
		return er.df_getColumnName(column);
	}

	public String read(int line, String columnName) {
		return er.df_getData(line, er.df_getColumnName(columnName));
	}

	public String read(int line, int columnName) {
		return er.df_getData(line, columnName);
	}

	public String readPremioAplicado(int line) {
		return er.df_getData(line, er.df_getColumnName(ExcelReader.AJUSTE_PREMIO_APLICADO));
	}

	public String read(String columnName) {
		return er.df_getData(ExcelReader.caseRow, er.df_getColumnName(columnName));
	}

	public String readPremioAplicado() {
		return er.df_getData(ExcelReader.caseRow, er.df_getColumnName(ExcelReader.AJUSTE_PREMIO_APLICADO));
	}

	public String readAjustePremioAplicadoMQC_AF() {
		return er.df_getData(ExcelReader.caseRow, er.df_getColumnName(ExcelReader.AJUSTE_PREMIO_APLICADO_MQC_AF));
	}

	public String readPAPCoberturaAdicional(int line) {
		return er.df_getData(line, er.df_getColumnName(ExcelReader.PAP_ESPERADA_COB_ADC));
	}

	public String readAjustePremioAplicadoMQC_AF(int line) {
		return er.df_getData(line, er.df_getColumnName(ExcelReader.AJUSTE_PREMIO_APLICADO_MQC_AF));
	}

	public String readPAPCoberturaAdicional() {
		return er.df_getData(ExcelReader.caseRow, er.df_getColumnName(ExcelReader.PAP_ESPERADA_COB_ADC));
	}

	public void writeData(String field, String value) {
		er.ds_writeData(value, TAB_RESULTS, field);
	}

	public void writeData(int row, String field, String value) {
		er.ds_writeData(value, row, field);
	}

	public static void addRow() {
		ExcelReader.caseRow++;
	}

	public static int getRow() {
		return ExcelReader.caseRow;
	}

	public void save() throws IOException {
		er.ds_saveData();
	}

	public void close() {
		er.ds_closeData();
	}
	// pesquisar na linha

	public int readRule() {
		// TODO Auto-generated method stub
		try {
			double value = Double
					.parseDouble(er.df_getData(ExcelReader.caseRow, er.df_getColumnName(ExcelReader.RULE)));
			return (int) Math.round(value);
		} catch (Exception e) {
			return -1;
		}
	}

	public static void save(String msg, long duration) throws Exception {
		int row = ExcelReader.caseRow;
		int rightRow = setRightLine();
		if (rightRow == -1)
			throw new Exception("A coluna do caso de teste não foi encontrada na planilha");
		msg = (msg == null) ? "" : msg;
		String result = msg.trim().isEmpty() ? "Passed" : "Failed";
		save(rightRow, result, msg, duration);

		ExcelReader.caseRow = row;
	}

	private static int setRightLine() {
		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		int column = erc.getColumn(ExcelReader.FIELD_TEST_CASE);
		int rightRow = 1;
		String sheetTestCase = ".";
		String testCase = Utilities.CT_FILENAME.split("//")[1];
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

	public static void save(int row, String result, String msg, long duration)
			throws IOException, InterruptedException {
		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		erc.writeData(row, ExcelReader.FIELD_FINAL_RESULT, result);
		erc.writeData(row, ExcelReader.FIELD_DURATION, duration + "");
		erc.writeData(row, ExcelReader.FIELD_ERROR, msg);
		erc.save();
		erc.close();
	}

	public static Data getCurrentData() {
		ExcelReaderController erc = new ExcelReaderController(ExcelReaderController.TAB_RESULTS);
		Data data = new Data(false);
		data.setCompanyIngenium(erc.read("companyIngenium"));
		data.setTestcase(erc.read("testcase"));
		data.setClient(erc.read("client"));
		data.setUwde_r(erc.read("uwde_r"));
		data.setUwde_stb2(erc.read("uwde_stb2"));
		data.setPolicy(erc.read("policy"));
		data.setName(erc.read("name"));
		data.setSurname(erc.read("surname"));
		return data;
	}
	
	public static Data getNextTestCase() {
		Data data = null;
		do {
			data = getCurrentData();
			if(data.getRun().equalsIgnoreCase("Yes"))
				return data;
			addRow();
		}while(!endOfFile());
		return null;
	}
	
	public static boolean endOfFile() {
		return getCurrentData().getTestcase().isEmpty();
	}

}
