import java.util.ArrayList;

public class Student {
	
	private int studentID;
	private ArrayList<Integer> gradeList;
	
	public Student(int stuID) {
		this.studentID = stuID;
		this.gradeList = new ArrayList<Integer>();
	}
	
	public int getStudentID() {
		return this.studentID;
	}
	
	public ArrayList<Integer> getGradeList(){
		return this.gradeList;
	}
	
	public void addGrade(int grade) {
		this.gradeList.add(grade);
	}
	
	public String gradeSummary() {
		String r = "";
		for(int i: gradeList) {
			r += Integer.toString(i) + "\n";
		}
		return r;
	}

}
