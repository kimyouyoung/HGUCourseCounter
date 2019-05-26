package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

	public static ArrayList<String> getLines(String file, boolean removeHeader){
		
		ArrayList<String> lines = new ArrayList<String>();
		String thisLine="";

		try {
			File datafile = new File(file);
			if(!datafile.exists())
				throw new NotEnoughArgumentException("The file path does not exist. Please check your CLI argument!");
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((thisLine = br.readLine()) != null) { 
				lines.add(thisLine);
			}
			br.close();
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
}
