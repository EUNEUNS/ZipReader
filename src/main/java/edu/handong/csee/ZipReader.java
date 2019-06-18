package edu.handong.csee;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ZipReader {

/*	public static void main(String[] args) {
		ZipReader zipReader = new ZipReader();
		zipReader.run(args);
	}

	private void run(String[] args) {
		String path = args[0];
		
		readFileInZip("0001.zip");

			
	} 		*/

	public static HashMap<String,ArrayList<ArrayList<String>>> readFileInZip(String path, String fileName) {
		HashMap<String,ArrayList<ArrayList<String>>> getData = new HashMap<String,ArrayList<ArrayList<String>>>();
		ZipFile zipFile;
		try {
			
			zipFile = new ZipFile(path,"euc-kr");
			 
			Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

		    while(entries.hasMoreElements()){
		    	ZipArchiveEntry entry = entries.nextElement();
		    	
		        if(entry.getName().contains("Summary")|| entry.getName().contains("요약문")) {
		        	
		        	InputStream stream = zipFile.getInputStream(entry);
			        ExcelReader myReader = new ExcelReader();
			      getData=myReader.getData(stream, fileName);
			    
			    
			     /*    }
		    }catch (NullPointerException e) {
		    	
		    }*/}
		        }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return getData;
	
	}

public static HashMap<String,ArrayList<ArrayList<String>>> readFileInZips(String path, String fileName) {
	HashMap<String,ArrayList<ArrayList<String>>> getData = new HashMap<String,ArrayList<ArrayList<String>>>();
	ZipFile zipFile;
	try {
		
		zipFile = new ZipFile(path,"euc-kr");
		 
		Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

	    while(entries.hasMoreElements()){
	    	ZipArchiveEntry entry = entries.nextElement();
	        
	        if(entry.getName().contains("Table")|| entry.getName().contains("표")) {
	        	InputStream stream = zipFile.getInputStream(entry);
		        ExcelReader myReader = new ExcelReader();
		      getData=myReader.getData(stream, fileName);
	        }
	    }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return getData;
}

public static void writeAFile( ArrayList<ArrayList<String>> arr, String targetFileName) {
	
	try {
		BufferedWriter fw = new BufferedWriter(new FileWriter(targetFileName));
		
		for(ArrayList<String> lines: arr) {
		for (String dom : lines) {
			fw.write(dom);
			

		}
		fw.newLine();
		}
		fw.flush();
		fw.close();
	} catch (Exception e) {
		System.out.println(e.getMessage());
		System.out.println("The file path does not exist. Please check your CLI argument!");

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
}

