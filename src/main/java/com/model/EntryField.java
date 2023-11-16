package com.model;

/**
 * Função para realizar input de dados no sistema
 * 
 *
 */

public class EntryField {

	private int row;
	private int column;
	private int length;
	
	public EntryField(String rowColumn) {
		//34:43 - Linha 34, coluna 43
		String[] lc = rowColumn.trim().split(":");
		this.row = Integer.parseInt(lc[0]);
		this.column = Integer.parseInt(lc[1]);
		this.length = 0;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	public EntryField(final int rowNum, final int columnNum) {
		this.row = rowNum;
		this.column = columnNum;
		this.length = 0;
	}
	
	public EntryField(String rowColumn, int length) {
		//34:43 - Linha 34, coluna 43
		String[] lc = rowColumn.trim().split(":");
		this.row = Integer.parseInt(lc[0]);
		this.column = Integer.parseInt(lc[1]);
		this.length = 0;
	}

	public EntryField(final int rowNum, final int columnNum, int length) {
		this.row = rowNum;
		this.column = columnNum;
		this.length = length;
	}

	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * Ger row number of the field.
	 * 
	 * @return row number
	 */
	public final int getRow() {
		return row;
	}

	/**
	 * Get column number of the field.
	 * 
	 * @return column number
	 */
	public final int getColumn() {
		return column;
	}
}
