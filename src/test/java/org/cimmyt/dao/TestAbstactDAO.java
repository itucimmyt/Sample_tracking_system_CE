package org.cimmyt.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class TestAbstactDAO {

	
	@Test
	public void evaluateString(){
		String str = "XXXXX67XXX000010";
		//System.out.println("value : "+parseSampleId(str));
		//System.out.println("value : "+parseSamplePatern(str));
		String str2 = "XXXXX67XXX10";
		System.out.println("value : "+parseSampleId(str2));
		System.out.println("value : "+parseSamplePatern(str2));
		//System.out.println("value : "+parseSampleId("XX2XX67XXX1000"));
		//System.out.println("value : "+parseSamplePatern("XX2XX67XXX1000"));
		//System.out.println("value : "+parseSampleId("XX3XX67XXX001000"));
		//System.out.println("value : "+parseSamplePatern("XX3XX67X123321X001000"));
		
	}

	private int parseSampleId(String input){
		Pattern splitter = Pattern.compile("(\\d+)$");
		Matcher m = splitter.matcher(input);
		if (m.find()){
			return Integer.parseInt(m.group());
		}
		
		return 0;
	}
	private String parseSamplePatern(String input){
		Pattern splitter = Pattern.compile("(\\d+)$");
		Matcher m = splitter.matcher(input);
		if (m.find()){
			return input.substring(0, input.indexOf(m.group()));
		}
		
		return "";
	}

}
