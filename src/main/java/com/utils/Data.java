package com.utils;

import java.util.Set;

import com.jagacy.Connection;
import com.model.AuthIO;

public class Data {
	private String username;
	private String password;
	private String usernameIngenium;
	private String passwordIngenium;
	private String companyIngenium;
	private String policy;
	private String name;
	private String surname;
	private String client;
	private String uwde_stb2;
	private String uwde_r;
	private String testcase;
	private String run;

	public Data(boolean auth) {
		if (auth)
			AuthIO.login();
		username = Connection.USER_AS400;
		usernameIngenium = Connection.USER_INGENIUM;
		passwordIngenium = Connection.getIngeniumEncodedpassword();
		password = Connection.getAs400Encodedpassword();
		companyIngenium = Connection.getIngeniumCompany();
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public void setTestcase(String testcase) {
		this.testcase = testcase;
	}

	public String getTestcase() {
		return testcase;
	}

	public String getUwde_stb2() {
		return uwde_stb2;
	}

	public String getUwde_r() {
		return uwde_r;
	}

	public void setUwde_r(String uwde_r) {
		if (uwde_r == null)
			this.uwde_r = "";
		else
			this.uwde_r = uwde_r;
	}

	public void setUwde_stb2(String uwde_stb2) {
		if (uwde_stb2 == null)
			this.uwde_stb2 = "";
		else
			this.uwde_stb2 = uwde_stb2;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getClient() {
		return client;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPolicy() {
		return policy;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	Data(final String username, final String password, final String usernameIngenium, final String passwordIngenium,
			final String policy) {
		this.username = username;
		this.password = password;
		this.passwordIngenium = passwordIngenium;
		this.usernameIngenium = usernameIngenium;
		this.policy = policy;
	}

	public String getPasswordIngenium() {
		return passwordIngenium;
	}

	public String getFirstSurnameLetters() {
		return surname.substring(0, 4);
	}

	public String getUsernameIngenium() {
		return usernameIngenium;
	}

	public void setUsernameIngenium(String usernameIngenium) {
		this.usernameIngenium = usernameIngenium;
	}

	public void setPasswordIngenium(String passwordIngenium) {
		this.passwordIngenium = passwordIngenium;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyIngenium() {
		// TODO Auto-generated method stub
		return companyIngenium;
	}

	public void setCompanyIngenium(String companyIngenium) {
		this.companyIngenium = companyIngenium;
	}
}
