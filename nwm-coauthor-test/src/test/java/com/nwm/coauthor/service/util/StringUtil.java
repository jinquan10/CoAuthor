package com.nwm.coauthor.service.util;

public class StringUtil {
	public static String repeat(char character, int numTimes){
		String repeated = "";
		
		for(int i = 0; i < numTimes; i++){
			repeated += character;
		}
		
		return repeated;
	}
}
