/*
HW1個人作業： 加法運算    3/26 (二)
  畫面有[數1]文字盒，[+]標籤，[數2]文字盒，[=]標籤，[總和]文字盒，[結果]標籤。
  用戶按[出題]按鈕後，將於[數1]及[數2]文字盒產生隨機2位數，
  用戶填完[總和]文字盒，按[核對]按鈕後，將於結果標籤盒中看到"對"或"錯"訊息。
  提示:
  1.產生隨機2位數方法: int n = random.nextInt(90) + 10;
    其中,random = new Random();
  2.核對方法有兩種寫法:
      A.出題時先存總和答案在全域變數，核對時與之比對
      		此作法較無彈性，只能練習亂數產生題目
      B.出題時不存答案，核對時才臨時計算數1及數2總和，進行比對
      		此作法較有彈性，可練習自己修改的數1及數2題目
*/

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Addition extends JFrame
{
    JTextField txtNumber1;
    JLabel lblAdd;
    JTextField txtNumber2;
    JLabel lblEqual;
    JTextField txtSum;
    JLabel lblResult;
    JButton btnNew;
    JButton btnCheck;
    Random random;
    
    public Addition()
    {
        super("加法運算");
        this.setLayout(new FlowLayout());
        
        txtNumber1 = new JTextField("文字盒: 產生數1");
        txtNumber2 = new JTextField("文字盒: 產生數2");
        txtSum = new JTextField("文字盒: 填入總和");
        
        lblAdd = new JLabel("標籤: +");
        lblEqual = new JLabel("標籤: =");
        lblResult = new JLabel("標籤: 結果");
        
        btnNew = new JButton("按鈕: 出題");
        btnCheck = new JButton("按鈕: 核對");
        
        random = new Random();
        
        btnNew.addActionListener(
        		new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int n = random.nextInt(90) + 10;
						int m = random.nextInt(90) + 10;
						txtNumber1.setText(Integer.toString(n));
						txtNumber2.setText(Integer.toString(m));
					}						
        	});
        
        btnCheck.addActionListener(
        		new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method st

				int ans = Integer.parseInt(txtNumber1.getText())+Integer.parseInt(txtNumber2.getText());
				int in = Integer.parseInt(txtSum.getText());
				if(in == ans) 
					lblResult.setText("對");
				else
					lblResult.setText("錯");
				
			}});
                 
        this.add(txtNumber1);
        this.add(lblAdd);
        this.add(txtNumber2);
        this.add(lblEqual);
        this.add(txtSum);
        this.add(btnNew);
        this.add(btnCheck);
        this.add(lblResult);
    }
}

public class ANS_HW1
{
  public static void main(String args[])
  {
      Addition jframe = new Addition();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(400,200);
      jframe.setVisible(true);
  }
}
