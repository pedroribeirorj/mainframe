package com.model;

public class LabelField {

	private int row;
	private int column;
	private String text;

	public LabelField(EntryField ef, final String labelText) {
		this.row = ef.getRow();
		this.column = ef.getColumn();
		this.text = labelText;
	}

	public LabelField(String rowColumn, final String labelText) {
		String[] lc = rowColumn.trim().split(":");
		this.row = Integer.parseInt(lc[0]);
		this.column = Integer.parseInt(lc[1]);
		this.text = labelText;
	}
	public LabelField(final int rowNum, final int columnNum) {
		this.row = rowNum;
		this.column = columnNum;
		this.text = null;
	}
	
	public LabelField(final int rowNum, final int columnNum, final String labelText) {
		this.row = rowNum;
		this.column = columnNum;
		this.text = labelText;
	}

	


	/**
	 * Get row number of the label.
	 * 
	 * @return row number
	 */
	public final int getRow() {
		return row;
	}

	/**
	 * Get column number of the label.
	 * 
	 * @return column number
	 */
	public final int getColumn() {
		return column;
	}

	/**
	 * Get label text.
	 * 
	 * @return label text
	 */
	public final String getText() {
		return text;
	}
	
}
