package lima.jogodaforca.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console {
	
	public static Integer readInteger() throws IOException {
		String str = Console.readString();
		Integer i = Integer.parseInt(str);
		return i;
	}
	
	public static Double readDouble() throws IOException {
		String str = Console.readString();
		Double d = Double.parseDouble(str);
		return d;
	}
	
	public static String readString() throws IOException {
		String line = null;
		InputStreamReader inReader = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(inReader);
		line = buffer.readLine();
		return line;
	}
	
	public static String readString(String regex) throws IOException {
		Pattern pattern = Pattern.compile(regex);
		String str = Console.readString();
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches() == false) {
			return null;
		}
		return str;
	}
}
