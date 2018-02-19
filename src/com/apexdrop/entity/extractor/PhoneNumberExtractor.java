package com.apexdrop.entity.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class PhoneNumberExtractor {
	
	public static void phoneNumberFinder(String htmlbody,String urlReference) throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/output.txt",true));
		
		final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
		
		Iterator<PhoneNumberMatch> iterator = util.findNumbers(htmlbody, null).iterator();
		 
		while (iterator.hasNext()) {
			
		        String  s = iterator.next().rawString();
		        System.out.println(" Phone number found here are ------> " + s);
		        System.out.println(" Website URL :::  " + urlReference);	
		        writer.write(" Phone number found here are ------> " + s);
		  }
        writer.close();
	}

}
