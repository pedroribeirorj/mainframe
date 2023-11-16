package com.sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import com.lowagie.text.Cell;

//import scenario.peopleSoft.Execution;

//===================================================================================

//===================================================================================

//------------------ DatabaseManager => Database Manager ----------------

//===================================================================================

//===================================================================================

public class ExcelReader {
	public static int caseRow = 1;
	public String dv_fileName = "";
	public XSSFWorkbook dv_workbook;
	public XSSFSheet dv_sheet;
	public FileInputStream dv_file;
	public String dv_nameWorkSheet = "";
	public String dvTablename = "";
	public static String AJUSTE_PREMIO_APLICADO = "AJUSTE DE PREMIO APLICADO";
	public static String AJUSTE_PREMIO_APLICADO_MQC_AF = "AJUSTE DE PREMIO PARA COBERTURA ADICIONAL MQC E ASSIST FUNERAL";
	public static String PAP_ESPERADA_COB_ADC = "PAP ESPERADA PARA COBERTURA ADICIONAL";
	public static String RULE = "RNG";
	public static String FIELD_FINAL_RESULT = "Final Result";
	public static String FIELD_TEST_CASE = "testcase";
	public static String FIELD_DURATION = "Duration(s)";
	public static String FIELD_ERROR = "Error";

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- startExcel() => Starts the Excel File -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_startExcel(String dv_f, String dv_sheetName) {
		dvTablename = dv_sheetName;
		dv_fileName = dv_f;
		ds_getName();

		try {
			dv_file = new FileInputStream(new File(dv_fileName));
			dv_workbook = new XSSFWorkbook(dv_file);
			dv_sheet = dv_workbook.getSheet(dv_sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_changeTable() => Changes the excel datasheet

	// -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_changeTable(String dv_tableName) {
		dvTablename = dv_tableName;
		dv_sheet = dv_workbook.getSheet(dv_tableName);
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- df_getData() => Gets datasheet Cell data -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public String df_getData(int dv_row, int dv_col) {
		try {
			return dv_sheet.getRow(dv_row).getCell(dv_col).toString();
		} catch (Exception e) {
			return "";
		}
	}

	public String df_getData(int row, String columnName) {
		try {
			int col = df_getColumnName(columnName);
//		DataFormatter fmt = new DataFormatter();
			return dv_sheet.getRow(row).getCell(col).toString();

//		return fmt.formatCellValue(dv_sheet.getRow(row).getCell(col));
		} catch (Exception e) {
			return "";
		}
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_writeData() => Writes datasheet Cell data

	// -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_writeData(String dv_data, int dv_row, int dv_col) {
		// XSSFCellStyle dv_style =
		// dv_sheet.getRow(1).getCell(1).getCellStyle();
		// XSSFCell dv_cell = dv_sheet.getRow(dv_row).createCell(dv_col);
		try {
			Row row = dv_sheet.getRow(dv_row);
			Cell cell = row.createCell(dv_col);
			cell.setCellValue(dv_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// dv_cell.setCellStyle(dv_style);
	}

	public void ds_writeData(String dv_data, int dv_row, String dv_name) {
		try {
			int dv_col = df_getColumnName(dv_name);
			Row row = dv_sheet.getRow(dv_row);
			Cell cell = row.createCell(dv_col);
			cell.setCellValue(dv_data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- closeData() => Closes File -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_closeData() {
		try {
			dv_file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_saveData() => Saves File -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_saveData() throws IOException {
		FileOutputStream dv_out;
		try {
			dv_out = new FileOutputStream(new File(dv_fileName));
			dv_workbook.write(dv_out);
			dv_out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- df_getColumnName() => Search Header Name -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public int df_getColumnName(String dv_name) {
		int dv_col = 0;
		while (df_getData(0, dv_col) != "") {
			if (df_getData(0, dv_col).equals(dv_name))
				return dv_col;
			dv_col++;
		}
		return dv_col;

	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_updateExcel() => Updates File -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_updateExcel(String dv_sheetName) {
		try {
			ds_saveData();
			dv_file = new FileInputStream(new File(dv_fileName));
			// Create Workbook instance holding reference to .xlsx file
			dv_workbook = new XSSFWorkbook(dv_file);
			dv_sheet = dv_workbook.getSheet(dv_sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_addRow() => Adds a new row in the specified position

	// -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_addRow(int dv_currentRow) {
		// XSSFCellStyle style = dv_sheet.getRow(1).getCell(1).getCellStyle();
		try {
			dv_sheet.shiftRows(dv_currentRow, dv_sheet.getLastRowNum(), 1);
		} catch (Exception e) {
		}
		try {
			ds_saveData();
		} catch (IOException e) {
		}
		// ds_updateExcel("Resultado");
		ds_updateExcel(dvTablename);
		dv_sheet.createRow(dv_currentRow);
		// dv_sheet.createRow(dv_currentRow).setRowStyle(style);
	}

	// ===================================================================================

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ---------------- ds_getName() => get the name of scenario -------------

	// +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

	// ===================================================================================

	public void ds_getName() {
		String[] dv_path;
		dv_path = dv_fileName.split("/");
		dv_nameWorkSheet = dv_path[dv_path.length - 1];
		dv_nameWorkSheet = dv_nameWorkSheet.replace(".xlsx", "");
		dv_nameWorkSheet = dv_nameWorkSheet.replace(".xls", "");
	}
}