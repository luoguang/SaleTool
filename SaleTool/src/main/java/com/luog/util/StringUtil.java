package com.luog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtil {
	private final static String ENCODE = "UTF-8";

	public static String encode(String val) {
		String encodeVal;
		try {
			encodeVal = URLEncoder.encode(val, ENCODE);
		} catch (UnsupportedEncodingException e) {
			encodeVal = "";
			e.printStackTrace();
		}
		return encodeVal;
	}

	public static String decode(String val) {
		String decodeVal;
		try {
			decodeVal = URLDecoder.decode(val, ENCODE);
		} catch (UnsupportedEncodingException e) {
			decodeVal = "";
			e.printStackTrace();
		}
		return decodeVal;
	}

}
