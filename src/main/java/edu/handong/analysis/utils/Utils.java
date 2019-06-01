package edu.handong.analysis.utils;

//import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {

	public static ArrayList<CSVRecord> getLines(String file, boolean removeHeader){
		
		ArrayList<CSVRecord> lines = new ArrayList<CSVRecord>();
		//String thisLine;

		try {
			File csvData = new File(file);
			if(!csvData.exists())
				throw new NotEnoughArgumentException("The file path does not exist. Please check your CLI argument!");
			
			FileReader data = new FileReader(csvData);
			CSVParser parser = CSVParser.parse(data, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withTrim());
			
			//Iterator<CSVRecord> iterator = parser.iterator();
			//BufferedReader br = new BufferedReader(new FileReader(file));
			/*(while ((thisLine = br.readLine()) != null) { 
				lines.add(thisLine);
			}
			br.close();
			*/
			for(CSVRecord csvRecord:parser)
				lines.add(csvRecord);
		}catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}catch (IOException e) {
			System.err.println("Error: " + e);
			System.exit(0);
		}
		
		if(removeHeader)
			lines.remove(0);
		
		return lines;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
		
		try {
			File file= new File(targetFileName);
			if(!file.exists())
				file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos=new DataOutputStream(fos);
			
			dos.write("StudentID,TotalNumberOfSemestersRegistered,Semester,NumCoursesTakenInTheSemester\n".getBytes());
			for(String line:lines){
				dos.write((line+"\n").getBytes());
			}
			//dos.writeBytes();
			dos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void writeASecondFile(ArrayList<String> lines, String targetFileName) {
		
		try {
			File file= new File(targetFileName);
			if(!file.exists())
				file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			DataOutputStream dos=new DataOutputStream(fos);
			
			dos.write("Year,Semester,CourseCode,CourseName,TotalStudent\n".getBytes());
			System.out.println("Year,Semester,CourseCode,CourseName,TotalStudent\n");
			for(String line:lines){
				dos.write((line+"\n").getBytes());
				System.out.println(line+"\n");
			}
			//dos.writeBytes();
			dos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
