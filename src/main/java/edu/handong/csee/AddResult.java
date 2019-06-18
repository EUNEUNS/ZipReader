package edu.handong.csee;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;


public class AddResult {
	 String inputPath;
	 String outputPath1;
	 String outputPath2;
	String StuNum;
	HashMap<String ,ArrayList<ArrayList<String>>> Summary = new HashMap<String ,ArrayList<ArrayList<String>>>();
	HashMap<String ,ArrayList<ArrayList<String>>>TableAndPic = new HashMap<String ,ArrayList<ArrayList<String>>>();
	 ArrayList<ArrayList<String>> ResultData1 = new ArrayList<ArrayList<String>>();
	 ArrayList<ArrayList<String>> ResultData2= new ArrayList<ArrayList<String>>();

	public void run(String[] args) {
		try {
			 
			 
			Options options = createOptions();
			
			if (parseOption(options, args)) {

		String input = inputPath;
		String output1 =outputPath1;
		String output2 =outputPath2;

		File dir = new File(input);
		File []fileList = dir.listFiles();
		for(File file : fileList) {
			if(file.isFile()) {
				HashMap<String ,ArrayList<ArrayList<String>>> temp = new HashMap<String ,ArrayList<ArrayList<String>>>();
				String FileName = file.getName();
				String StuNum = FileName.split("\\.")[0].trim();
				temp = ZipReader.readFileInZip(input+"\\"+FileName,StuNum);
				Summary.putAll(temp);

				temp = ZipReader.readFileInZips(input+"\\"+FileName,StuNum);
				TableAndPic.putAll(temp);

				
			}

		}
		Map<String, ArrayList<ArrayList<String>>> Sum1 = new TreeMap<String, ArrayList<ArrayList<String>>>(Summary);
		Map<String, ArrayList<ArrayList<String>>> Sum2 = new TreeMap<String, ArrayList<ArrayList<String>>>(TableAndPic);

		ResultData1=Sumoutput(Sum1);
		ResultData2=Sumoutput2(Sum2);
		
		ZipReader.writeAFile(ResultData1, output1);
		ZipReader.writeAFile(ResultData2, output2);

			}
			throw new HelloJC();
		} catch (HelloJC e) 
        { 
            System.out.println("Hello,My favorite Prof.JC"); 
            System.out.println(e.getMessage()); 
        } 
		catch (Exception e) {
			System.out.println(e.getMessage());

		}
	}
	
	
	
	
	private boolean parseOption(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			inputPath = cmd.getOptionValue("i");
			outputPath1 = cmd.getOptionValue("o1");
			outputPath2 = cmd.getOptionValue("o2");
		
			
		} catch (Exception e) {
			printHelp(options);
			System.exit(1);
		}

		return true;
	}
	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "Add Results";
		String footer = "";
		formatter.printHelp("Add Results", header, options, footer, true);
	}
	private Options createOptions() {
		Options options = new Options();
		options.addOption(Option.builder("i").longOpt("input").desc("Set an input file path").hasArg()
				.argName("Input path").required().build());
		options.addOption(Option.builder("o1").longOpt("output1").desc("Set an output file path").hasArg()
				.argName("Output path1").required().build());
		options.addOption(Option.builder("o2").longOpt("output2").desc("Set an output file path").hasArg()
				.argName("Output path2").required().build());
		options.addOption(Option.builder("h").longOpt("help").desc("Show a Help page").argName("Help").build());

		return options;
	}



	
	
	
	
	
	
	
	
	

	public ArrayList<ArrayList<String>> Sumoutput (Map<String, ArrayList<ArrayList<String>>> Hash) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>();
			header = Hash.get("0001").get(0);
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : Hash.entrySet()) {
			
				
				String key = entry.getKey();	
				ArrayList<ArrayList<String>> Summ = entry.getValue();
				Summ.remove(0);
				for(ArrayList<String> values : Summ) { 
					values.add(0,key);	
					result.add(values);
				} 
			
		
		}
		
		header.add(0,"id");
		result.add(0,header);
		return result;
	}

	public ArrayList<ArrayList<String>> Sumoutput2 (Map<String, ArrayList<ArrayList<String>>> Hash) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> header = new ArrayList<String>();
			header = Hash.get("0001").get(1);
		for (Map.Entry<String, ArrayList<ArrayList<String>>> entry : Hash.entrySet()) {
			
				
				String key = entry.getKey();	
				ArrayList<ArrayList<String>> Summ = entry.getValue();
				Summ.remove(0);
				Summ.remove(0);
				for(ArrayList<String> values : Summ) { 
					values.add(0,key);	
					result.add(values);
				} 
			
		
		}
		
		header.add(0,"id");
		result.add(0,header);
		
		return result;
	}
	
	public static <T> List<T> getInstance(List<T> arrayList)
	{
		return arrayList.stream()
						.collect(Collectors.toCollection(LinkedList::new));
	}

}
class HelloJC extends Exception 
{ 
}
   





