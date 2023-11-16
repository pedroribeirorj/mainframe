package com.keyword;

import com.utils.Utilities;

import io.jsonwebtoken.security.Keys;

import com.jagacy.Key;
import com.jagacy.Location;
import com.jagacy.Session3270;
import com.jagacy.util.JagacyException;
import com.model.EntryField;
import com.model.LabelField;

public class Driver extends Session3270 {

	public final int DEFAULT_TIMEOUT = 700;
	public final int HARD_TIMEOUT    = 1500;

	public LabelField textLabel;

	public Driver(final String str) throws JagacyException {

		super(str);

	}

	/**
	 * Wrapper method on waitForPosition.
	 * 
	 * @param textLabel Label field object
	 * @return true or false
	 * @throws JagacyException JagacyException
	 */

	public final boolean waitForTextLabel(final LabelField textLabel) throws JagacyException {
		return waitForPosition(textLabel.getRow(), textLabel.getColumn(), textLabel.getText(), DEFAULT_TIMEOUT);

	}
	public final boolean waitForTextLabel(final LabelField textLabel, int timeout) throws JagacyException {
		return waitForPosition(textLabel.getRow(), textLabel.getColumn(), textLabel.getText(), timeout);

	}
	/**
	 * Wrapper method on waitForPosition.
	 * 
	 * @param textLabel Label field object
	 * @return true or false
	 * @throws JagacyException JagacyException
	 */

	protected final boolean isLabelPresent(final LabelField textLabel, final int timeout) throws JagacyException {

		return waitForPosition(textLabel.getRow(), textLabel.getColumn(), textLabel.getText(), timeout);

	}

	/**
	 * Pressiona a tecla F2
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */

	public final boolean isKeyboardLocked() throws JagacyException {
		return !isKeyboardUnlocked();
	}
	
	protected final void pressF1() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF1);
		waitForChange(DEFAULT_TIMEOUT);
	}

	protected final void pressF2() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF2);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F3
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressF3() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF3);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F4
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressF4() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF4);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F5
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressF5() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF5);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F6
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressF6() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF6);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla PA2
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressPA2() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PA2);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F8
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */

	protected final void pressF7() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF7);
		waitForChange(DEFAULT_TIMEOUT);
	}

	protected final void pressF8() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF8);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F10
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */

	protected final void pressF9() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF9);
		waitForChange(DEFAULT_TIMEOUT);
	}

	protected final void pressF10() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF10);
		waitForChange(DEFAULT_TIMEOUT);
	}

	protected final void pressF11() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF11);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla F12
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	protected final void pressF12() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.PF12);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla ENTER
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	public final void pressEnter() throws JagacyException, InterruptedException {
		Utilities.getScreenShot();
		writeKey(Key.ENTER);
		waitForChange(DEFAULT_TIMEOUT);
	}

	/**
	 * Pressiona a tecla TAB
	 * 
	 * @throws JagacyException
	 * @throws InterruptedException
	 */
	public final void pressTab() throws JagacyException, InterruptedException {

		writeKey(Key.TAB);
	}

	/**
	 * Wrapper method on writePosition method of Jagacy.
	 * 
	 * @param entryField Entry field object
	 * @param value      value
	 * @throws JagacyException JagacyException
	 */
	// protected
	public final void sendKeys(final EntryField entryField, final String value) throws JagacyException {

		writePosition(entryField.getRow(), entryField.getColumn(), value);

	}

	public final void sendKeysAndEnter(final EntryField entryField, final String value)
			throws JagacyException, InterruptedException {

		writePosition(entryField.getRow(), entryField.getColumn(), value);
		pressEnter();
		waitForChange(1);

	}

	public final void shiftTab() throws JagacyException, InterruptedException {
		writeKey(Key.BACK_TAB);

	}

	protected String getRowText(int row) throws JagacyException {
		return readRow(row);

	}

	protected String getRowText(int row, int beginIndex, int endIndex) throws JagacyException {
		return readRow(row).substring(beginIndex, endIndex);

	}

	protected char[] getAllData() throws JagacyException {

		return readScreenText();

	}

	protected String getData(final EntryField entryField) throws JagacyException {

		return readField(entryField.getRow(), entryField.getColumn(), getWidth());

	}

	public void pressDelete(int i) throws JagacyException {
		for (int j = 0; j < i; j++) {
			writeKey(Key.DELETE);
		}
	}

	public void pressBackspace(int i) throws JagacyException {
		for (int j = 0; j < i; j++) {
			writeKey(Key.BACKSPACE);
		}
	}

	public void pressBackspaceAndDelete(int i) throws JagacyException {
		pressBackspace(i);
		pressDelete(i);
	}

	public final void writeKeyWithoutPosition(String text) throws JagacyException, InterruptedException {

		writeCursor(text);
	}

	public Location getLocation(EntryField ef) throws JagacyException {
		return createLocation(ef.getRow(), ef.getColumn());
	}
	
	public Location getLocation(LabelField ef) throws JagacyException {
		return createLocation(ef.getRow(), ef.getColumn());
	}

	public void writeCursor(EntryField ef) throws JagacyException {
		writeCursor(getLocation(ef));
	}

	public String readPosition(EntryField ef, int length) throws JagacyException {
		return readPosition(getLocation(ef), length).trim();

	}
	
	public String readPosition(LabelField ef, int length) throws JagacyException {
		return readPosition(getLocation(ef), length).trim();

	}
}
