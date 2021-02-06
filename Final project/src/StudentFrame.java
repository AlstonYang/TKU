
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class StudentFrame extends JFrame {
	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 350;
	private static final int FIELD_WIDTH = 10;
	private static final int AREA_WIDTH = 40;
	private static final int AREA_HEIGHT = 10;
	private JComboBox<Integer> stuComb;
	private JLabel selStuID, addGradeL;
	private JTextField addGradeT;
	private JButton addGradeBtn, summaryBtn;
	private JTextArea resultArea;
	private JScrollPane scrollPane;
	private ArrayList<Student> studentList;
	private Student student;

	public StudentFrame() {
		Student stu1 = new Undegraduate(101);
		Student stu2 = new Graduate(111);
		studentList = new ArrayList<Student>();
		studentList.add(stu1);
		studentList.add(stu2);
		
		createPanel();
		disableAllBtn();
		setTitle("Student's Grade");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
//Please follow instructions in question 14 and 15 to finish the remaining methods.


	public void createPanel() {
		JPanel northPanel, centerPanel, southPanel, mainPanel, resultPanel;
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();

		selStuID = new JLabel("Select a Student: ");
		stuComb = new JComboBox<Integer>();
		for (Student s : studentList) {
			stuComb.addItem(s.getStudentID());
		}
		createStuCombListener();
		northPanel.add(selStuID);
		northPanel.add(stuComb);

		addGradeL = new JLabel("New grade");
		addGradeT = new JTextField(FIELD_WIDTH);
		addGradeBtn = new JButton("Add");
		createAddGradeListener();

		centerPanel.add(addGradeL);
		centerPanel.add(addGradeT);
		centerPanel.add(addGradeBtn);

		summaryBtn = new JButton("Summary");
		createSummaryListener();

		southPanel.add(summaryBtn);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		resultArea = new JTextArea(AREA_HEIGHT, AREA_WIDTH);
		resultArea.setEditable(false);
		scrollPane = new JScrollPane(resultArea);

		resultPanel = new JPanel();

		resultPanel.add(scrollPane);

		add(mainPanel, BorderLayout.NORTH);
		add(resultPanel, BorderLayout.SOUTH);

	}

	public void createStuCombListener() {
		class listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				int stuID = (int) stuComb.getSelectedItem();
				for (Student stu : studentList) {
					if (stu.getStudentID() == stuID) {
						student = stu;
						enableAllBtn();
					}
				}

			}
		}

		stuComb.addActionListener(new listener());
	}

	public void createAddGradeListener() {
		class listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				int grade = Integer.parseInt(addGradeT.getText());
				student.addGrade(grade);
				resultArea.append("new grade: " + grade + "\n");
			}
		}
		addGradeBtn.addActionListener(new listener());

	}

	public void createSummaryListener() {
		class listener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = student.gradeSummary();
				resultArea.append(s);
			}
		}
		summaryBtn.addActionListener(new listener());
	}

	public void disableAllBtn() {
		addGradeBtn.setEnabled(false);
		summaryBtn.setEnabled(false);
	}
	
	public void enableAllBtn() {
		addGradeBtn.setEnabled(true);
		summaryBtn.setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			int grade = Integer.parseInt(addGradeT.getText());
			student.addGrade(grade);
			resultArea.append("new grade: " + grade + "\n");
		} catch (Exception exception) {
			resultArea.append("Please input integer");
		}

	}


}

