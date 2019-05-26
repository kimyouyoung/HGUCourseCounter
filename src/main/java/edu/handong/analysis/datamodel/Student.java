package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>();
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>() ;
	
	public Student(String studentId) {
		this.studentId =studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void addCourse(Course newRecord) {
		
		coursesTaken.add(newRecord);
		
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		
		int value = 1;
		boolean first = true;
		
		for(Course course : coursesTaken) {
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(first) {
				semestersByYearAndSemester.put(yearAndSemester, value);
				first = false;
			}
			
			if(!semestersByYearAndSemester.containsKey(yearAndSemester)) {
				semestersByYearAndSemester.put(yearAndSemester, ++value);
			}
		}
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		
		int count = 0;
		for(Course course : coursesTaken) {
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(semestersByYearAndSemester.get(yearAndSemester) == semester)
				count++;
		}
		
		return count;
	}
}
