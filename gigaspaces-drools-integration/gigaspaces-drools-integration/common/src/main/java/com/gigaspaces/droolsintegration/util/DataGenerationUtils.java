package com.gigaspaces.droolsintegration.util;

import java.util.Random;

public class DataGenerationUtils {
	
	private DataGenerationUtils() {}
	 
	private static final String NAMES[] = {"Mike", "Ralph", "Don", "Leo"};
	
	private static Random random = new Random();
	
	public static String pickRandomName() {
		return NAMES[random.nextInt(NAMES.length)];
	}
	
	public static int pickRandomAge() {
		return random.nextInt(35);
	}
	
}