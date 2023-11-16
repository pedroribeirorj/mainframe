package com.keyword;

import com.utils.Utilities;
import com.jagacy.Key;
import com.jagacy.util.JagacyException;
import com.model.EntryField;
import com.model.LabelField;
import static com.utils.CommonMapping.*;

public class Keyword {
	private static Driver driver;
	public String error;

	public void setError(String error) {
		this.error = error;
	}

	public Keyword(Driver driver) {
		this.driver = driver;
		this.error = "";
	}

	public static Driver getDriver() {
		return driver;
	}

	public static void write(EntryField ef) throws JagacyException {
		driver.writeCursor(ef);
	}

	public static void delete(int n) throws JagacyException {
		driver.pressDelete(n);
	}

	public static boolean isKeyboarLocked() throws JagacyException {
		return driver.isKeyboardLocked();
	}

	public static void inputAndEnter(final EntryField entryField, final String value)
			throws JagacyException, InterruptedException {
		driver.sendKeysAndEnter(entryField, value);
		waitForChange(getDriver().DEFAULT_TIMEOUT);
	}

	public static void input(final EntryField entryField, final String value)
			throws JagacyException, InterruptedException {
		driver.sendKeys(entryField, value);
	}

	public static void cleanAndInput(final EntryField entryField, final String value)
			throws JagacyException, InterruptedException {
		driver.writeCursor(entryField);
		driver.pressDelete(entryField.getLength());
		driver.sendKeys(entryField, value);
	}
	
	public static void delete(final EntryField entryField)
			throws JagacyException, InterruptedException {
		driver.writeCursor(entryField);
		driver.pressDelete(entryField.getLength());
	}
	

	public static String read(EntryField ef, int length) throws JagacyException {
		return driver.readPosition(ef, length).trim();
	}

	public static String read(EntryField ef) throws JagacyException {
		return driver.readPosition(ef, ef.getLength()).trim();
	}

	public static String read(int row, int column, int length) throws JagacyException {
		return driver.readPosition(row, column, length).trim();
	}

	public static String read(LabelField lf, int length) throws JagacyException {
		return driver.readPosition(lf, length).trim();
	}

	public static void returnToIngeniumHome() throws JagacyException, InterruptedException {
		try {
			driver.sendKeysAndEnter(FIELD_COMMAND_AUX, "apls");
		} catch (Exception e) {
			driver.sendKeysAndEnter(FIELD_COMMAND, "apls");
		}
	}

	public static boolean waitForTextLabel(String label) throws JagacyException {
		return driver.waitForTextLabel(stringToEntryLabel(label));
	}

	public static boolean waitForChange(int miliseconds) throws JagacyException {
		return driver.waitForChange(miliseconds);
	}

	public static final boolean waitForTextLabel(final LabelField textLabel) throws JagacyException {
		return driver.waitForTextLabel(textLabel);
	}

	public static boolean isLabelPresent(String label, final int timeout) throws JagacyException {
		LabelField textLabel = stringToEntryLabel(label);
		return driver.waitForPosition(textLabel.getRow(), textLabel.getColumn(), textLabel.getText(), timeout);
	}

	public static void F1() throws JagacyException, InterruptedException {
		driver.pressF1();
	}

	public static void F2() throws JagacyException, InterruptedException {
		driver.pressF2();
	}

	public static void F3() throws JagacyException, InterruptedException {
		driver.pressF3();
	}

	void F4() throws JagacyException, InterruptedException {
		driver.pressF4();
	}

	void F5() throws JagacyException, InterruptedException {
		driver.pressF5();
	}

	void F6() throws JagacyException, InterruptedException {
		driver.pressF6();
	}

	void F7() throws JagacyException, InterruptedException {
		driver.pressF7();
	}

	void F8() throws JagacyException, InterruptedException {
		driver.pressF8();
	}

	void F9() throws JagacyException, InterruptedException {
		driver.pressF9();
	}

	void F10() throws JagacyException, InterruptedException {
		driver.pressF10();
	}

	void F11() throws JagacyException, InterruptedException {
		driver.pressF11();
	}

	public static void F12() throws JagacyException, InterruptedException {
		driver.pressF12();
	}

	public static void enter() throws JagacyException, InterruptedException {
		driver.pressEnter();
		waitForChange(getDriver().DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla TAB
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	void tab() throws JagacyException, InterruptedException {
		driver.pressTab();
	}

	/**
	 * Wrapper method on writePosition method of Jagacy.
	 * 
	 * @param entryField Entry field object
	 * @param value      value
	 * @throws JagacyException JagacyException
	 */
	void input(String entryField, final String value) throws JagacyException {
		driver.sendKeys(strToEntryField(entryField), value);

	}

	String getRowText(int row) throws JagacyException {
		return driver.getRowText(row);
	}

	String getRowText(int row, int beginIndex, int endIndex) throws JagacyException {
		return driver.getRowText(row, beginIndex, endIndex);
	}

	char[] getAllData() throws JagacyException {
		return driver.readScreenText();
	}

	String getData(String entryField) throws JagacyException {
		return driver.getData(strToEntryField(entryField));
	}

	private EntryField strToEntryField(String entryField) {
		// TODO Auto-generated method stub
		return null;
	}

	private static LabelField stringToEntryLabel(String label) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void pagedown() throws JagacyException, InterruptedException {
		driver.pressF8();
	}
	public static void pageup() throws JagacyException, InterruptedException {
		driver.pressF7();
	}
}
