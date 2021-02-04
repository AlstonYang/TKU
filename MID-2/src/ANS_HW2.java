/*
HW2個人作業：BMI計算     4/9 (二)
畫面有[體重]及[身高]兩文字盒，內含初始說明字串"公斤體重","公分身高"。
  用戶於其上覆蓋說明字串，輸入兩數字後，
  按[BMI]按鈕，將於[結果]標籤盒中看到BMI值。
  並依BMI值在正常或異常區間，設定畫面背景色為綠色或紅色。
  提示:
  1.字串轉換數字方法: double number = Double.parseDouble(string);
  2.設畫面背景色方法: jframe.getContentPane().setBackground(color);
  3.顏色常數: 綠色為Color.GREEN; 紅色為Color.RED
  4.BMI = 公斤體重 / 公尺身高平方
  5.正常區間: 18.5<=BMI<24，其餘為異常區間
*/

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class BMI extends JFrame
{
    JTextField txtHeight;
    JTextField txtWeight;
    JButton btnBMI;
    JLabel lblResult;
    
    public BMI()
    {
        super("加法運算");
        this.setLayout(new FlowLayout());
        
        txtHeight = new JTextField("公分身高");
        txtWeight = new JTextField("公斤體重");
        btnBMI = new JButton("BMI");
        lblResult = new JLabel("結果");
 
        this.add(txtHeight);
        this.add(txtWeight);
        this.add(btnBMI);
        this.add(lblResult);
        
        btnBMI.addActionListener(
        		new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						double h = Double.parseDouble(txtHeight.getText());
						double w = Double.parseDouble(txtWeight.getText());
						double BMI = w/((h*0.01)*(h*0.01));
					
						lblResult.setText(Double.toString(BMI));

	        			if(18.5<=BMI && BMI<24)
	        				getContentPane().setBackground(Color.GREEN);
	        			else
	        				getContentPane().setBackground(Color.RED);
					}
        			
        		});
     }
}

public class ANS_HW2
{
  public static void main(String args[])
  {
      BMI jframe = new BMI();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(400,200);
      jframe.setVisible(true);
  }
}
