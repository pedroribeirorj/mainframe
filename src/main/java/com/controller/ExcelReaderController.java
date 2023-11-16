package com.controller;

import java.io.File;
import java.io.IOException;

import com.utils.ExcelReader;
import com.utils.Utilities;

public class ExcelReaderController {
	private ExcelReader er;
	
	public static final int TAB_RULES = 0;
	public static final int TAB_RESULTS = 1;
	public static final String TEXT_TAB_RESULTS = "test cases";
	public static final String TEXT_TAB_RULES = "rules";
	
	public ExcelReaderController(int tab) {
		er = new ExcelReader();
		switch(tab) {
		case TAB_RESULTS:
			er.ds_startExcel(Utilities.SHEET_FULL_PATH, TEXT_TAB_RESULTS);
//			er.ds_startExcel(new File("").getAbsolutePath() + "\\resources\\org\\easetech\\data\\testExcelData.xlsx", TEXT_TAB_RESULTS);
			break;
		case TAB_RULES:
			er.ds_startExcel(Utilities.SHEET_FULL_PATH, TEXT_TAB_RULES);
//			er.ds_startExcel(new File("").getAbsolutePath() + "\\resources\\org\\easetech\\data\\testExcelData.xlsx", TEXT_TAB_RULES);
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
	
	public void writeData( int row,String field, String value) {
		er.ds_writeData(value, row, field);
	}

	public void addRow() {
		ExcelReader.caseRow++;
	}

	public int getRow() {
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
}
