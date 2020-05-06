package com.luog.util;

public class ExpressQueryAPI {
	private static final String URL = "https://www.kuaidi100.com/query?type=jd&postid=JD0014989330095&temp=0.01482414324758996&phone=";

	public static void main(String[] args) {
		System.out.println(HttpInvoke.doGet(URL));
	}
}
