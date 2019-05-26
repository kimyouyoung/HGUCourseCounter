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
		/*
		 * addCourse method는 line을 읽으면서 생성된 Course instance를  Student instance에 있는 coursesTaken ArrayList에 추가하는 method입니다.
		*/
		coursesTaken.add(newRecord);
		
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		/*
		 * getSemestersByYearAndSemester method는 수강한 년도와 학기 정보를 이용 해당 학생의 순차적인 학기 정보를 저장하는 hashmap을 만듭니다. 
		*/
		int value = 1;
		boolean first = true;
		
		for(Course course : coursesTaken) {
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(first) {
				semestersByYearAndSemester.put(yearAndSemester, value++);
				first = false;
			}
			else if(!semestersByYearAndSemester.containsKey(yearAndSemester)) {
				semestersByYearAndSemester.put(yearAndSemester, value++);
			}
		}
		
		return semestersByYearAndSemester;
	}
	
	public int getNumCourseInNthSemester(int semester) {
		/*
		 * getNumCourseInNthSementer method는 순차적 학기 번호를 넣으면 해당 학기의 들은 과목의 개수를 돌려줍니다. 
		 * 앞의 hashmap에서 3을 입력하면 해당 학생이 2002-1학기에 들은 과목의 개수를 돌려줍니다. 
		 * coursesTaken에 과목들을 쭉 돌면서 년차 및 학기 정보를 조합해서 key (2002-1)를 만든 후 
		 * semestersByYearAndSemester에 해당 key값에 해당하는 순차적 학기 번호를 받아와 3이랑 같으면 count를 하나 늘리식으로 논리를 짜면 됩니다.
		 */
		int count = 0;
		for(Course course : coursesTaken) {
			String yearAndSemester = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(semestersByYearAndSemester.get(yearAndSemester) == semester)
				count++;
		}
		
		return count;
	}
}
