public class Tester {
	public static void main(String[] args) {
		Student stu1 = new Undegraduate(101);
		stu1.addGrade(55);
		stu1.addGrade(80);
		System.out.println(stu1.gradeSummary());
	}
}
