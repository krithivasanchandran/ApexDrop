package com.apexdrop.entity.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailExtractor {
	
 public static void EmailFinder(String htmlbody,String urlReference) throws IOException{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/output.txt",true));
		
		final String RE_MAIL = "([\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Za-z]{2,4})";
	    Pattern p = Pattern.compile(RE_MAIL);
	    Matcher m = p.matcher(htmlbody);

	    while(m.find()) {
	    	 System.out.println(" Email Address :::  " + m.group(1));
		     System.out.println(" Website URL :::  " + urlReference);	
		     writer.write(" Email Address found here are ------> " + m.group(1));

	    }
        writer.close();
	}

}
