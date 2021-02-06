public class Undegraduate extends Student {

	public Undegraduate(int stuID) {
		super(stuID);
	}

	public String gradeSummary() {
		String r = super.gradeSummary();
		int pass = 0;
		int failed = 0;
		for (int i : super.getGradeList()) {
			if (i >= 60) {
				pass++;
			} else {
				failed++;
			}
		}
		r += "Pass: " + pass + "\nFailed: " + failed + "\n";
		return r;
	}

}
