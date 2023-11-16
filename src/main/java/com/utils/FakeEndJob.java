package com.utils;

import java.io.IOException;

import com.jagacy.Connection;
import com.jagacy.util.JagacyException;

public class FakeEndJob {
	
	public static void main(String[] args) throws Exception {
		Connection.closeJagacySession(new Data(false));
	}

}
