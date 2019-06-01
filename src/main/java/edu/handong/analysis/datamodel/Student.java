package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String student;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>();
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>();
	
	public Student(String student) {
		this.student =student;
	}

	public String getStudent() {
		return student;
	}

	public void addCourse(Course newRecord) {
		
		coursesTaken.add(newRecord);
		
	}

	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		
		int value = 1;
		boolean first = true;
		
		for(Course course : coursesTaken) {
			String yearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
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
			String yearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
			if(semestersByYearAndSemester.get(yearAndSemester) == semester)
				count++;
		}
		
		return count;
	}

	public int getTotalStudent(String yearAndSemester) {
		
		int count = 0;
		boolean first = true;
		ArrayList<String> studentId = new ArrayList<String>();
		
		for(Course course:coursesTaken) {
			String getYearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
			if(yearAndSemester.equals(getYearAndSemester)) {
				if(first) {
					count++;
					studentId.add(course.getStudentId());
					first = false;
				}
				else if(studentId.contains(course.getStudentId()))
					continue;
				else {
					count++;
					studentId.add(course.getStudentId());
				}
			
			}
		}
		return count;
	}
	
	public int getYear(String yearAndSemester) {
		int year = 0;
		
		for(Course course:coursesTaken) {
			String getYearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
			if(getYearAndSemester.equals(yearAndSemester)) {
				year = course.getYearTaken();
				return year;
			}
		}
		return year;
	}
	
	public int getSemester(String yearAndSemester) {
		int semester = 0;
		
		for(Course course:coursesTaken) {
			String getYearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
			if(getYearAndSemester.equals(yearAndSemester)) {
				semester = course.getsemesterTaken();
				return semester;
			}
		}
		return semester;
	}
	
	public String getCourseName(String courseCode) {
		String courseName = "";
		
		for(Course course:coursesTaken) {
			if(course.getCourseCode().equals(courseCode)) {
				courseName = course.getCourseName();
				return courseName;
			}
		}
		return courseName;
	}
	
	public int getTakenStudents(String yearAndSemester, String courseCode) {
		int count = 0;
		
		boolean first = true;
		ArrayList<String> studentId = new ArrayList<String>();
		
		for(Course course:coursesTaken) {
			String getYearAndSemester = course.getYearTaken() + "-" + course.getsemesterTaken();
			if(yearAndSemester.equals(getYearAndSemester)) {
				if(course.getCourseCode().equals(courseCode)) {
					if(first) {
						count++;
						studentId.add(course.getStudentId());
						first = false;
					}
					else if(studentId.contains(course.getStudentId()))
						continue;
					else {
						count++;
						studentId.add(course.getStudentId());
					}
				}
			}
		}
		return count;
	}
}

