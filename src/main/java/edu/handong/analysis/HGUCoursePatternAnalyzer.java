package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import java.util.TreeMap;

import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
//import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	//private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		Runner.runner(args);
		
		//String dataPath = args[0]; // csv file to be analyzed
		//String resultPath = args[1]; // the file path where the results are saved.
		//ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
		//students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		//Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		//ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		//Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	public static HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines, int startyear, int endyear) {
		
		HashMap<String,Student> records = new HashMap<String,Student>();
		String studentId;
		boolean first = true;
		
		for(CSVRecord line:lines) {
			Course course = new Course(line);
			if(course.getYearTaken() >= startyear && course.getYearTaken() <= endyear) {
				studentId = course.getStudentId();
				if(first) {
					Student student = new Student(studentId);
					student.addCourse(course);
					records.put(studentId, student);
					first = false;
				}
				
				else if(records.containsKey(studentId))
					records.get(studentId).addCourse(course);
				else {
					Student student = new Student(studentId);
					student.addCourse(course);
					records.put(studentId, student);
				}
			}
		}
		
		return records; // do not forget to return a proper variable.
	}
	
	public static HashMap<String,Student> loadStudentYearAndSemester(ArrayList<CSVRecord> lines, int startyear, int endyear) {
		
		HashMap<String,Student> records = new HashMap<String,Student>();
		String yearAndSemester;
		boolean first = true;
		
		for(CSVRecord line:lines) {
			Course course = new Course(line);
			if(course.getYearTaken() >= startyear && course.getYearTaken() <= endyear) {
				yearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
				if(first) {
					Student student = new Student(yearAndSemester);
					student.addCourse(course);
					records.put(yearAndSemester, student);
					first = false;
				}
				
				else if(records.containsKey(yearAndSemester))
					records.get(yearAndSemester).addCourse(course);
				else {
					Student student = new Student(yearAndSemester);
					student.addCourse(course);
					records.put(yearAndSemester, student);
				}
			}
		}
		
		return records; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semesters in total. In the first semester (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	public static ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		ArrayList <String> takenCourses = new ArrayList<String>();
		
		for (String studentId:sortedStudents.keySet()){
            Student student = sortedStudents.get(studentId);
            HashMap<String, Integer> semesterByYearAndSemester = student.getSemestersByYearAndSemester();

            int maxSemester = semesterByYearAndSemester.size();

            for (int i = 1; i <= maxSemester; i++){
                takenCourses.add(studentId + "," + maxSemester + "," + i + "," + student.getNumCourseInNthSemester(i));
            } 
        }
		return takenCourses; // do not forget to return a proper variable.
	}
	
	public static ArrayList<String> countStudentsOfCoursesTaken(Map<String, Student> sortedYearsAndSemester, String courseCode) {
		
		ArrayList <String> studentTaken = new ArrayList<String>();
		String name = "";
		
		for (String yearAndSemester:sortedYearsAndSemester.keySet()){
            Student student = sortedYearsAndSemester.get(yearAndSemester);
            String courseName = student.getCourseName(courseCode);
            if(!courseName.equals("")) {
            	name = courseName;
            	break;
            }
		}
		
		for (String yearAndSemester:sortedYearsAndSemester.keySet()){
            Student student = sortedYearsAndSemester.get(yearAndSemester);
            
            int totalStudents = student.getTotalStudent(yearAndSemester);
            int takenStudents = student.getTakenStudents(yearAndSemester, courseCode);
            float rate = (takenStudents/(float)totalStudents) * 100;
            int year = student.getYear(yearAndSemester);
            int semester = student.getSemester(yearAndSemester);
         
            studentTaken.add(year+ "," + semester + "," + courseCode + "," + name + "," + totalStudents + "," + takenStudents + "," + String.format("%.1f", rate) + "%");
        }
		return studentTaken; // do not forget to return a proper variable.
	}
}
