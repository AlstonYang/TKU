/*
G1分組練習: 溫度轉換
t1.用文字盒接收華氏溫度,轉換成攝氏溫度,顯示到標籤上.
   公式為: 攝氏 = 5 / 9 x (華氏 - 32)
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

class ConvertFtoC extends JFrame
{
    JTextField txtF;
    JLabel lblC;
    
    public ConvertFtoC()
    {
        super("華氏轉換攝氏溫度");
        this.setLayout(new FlowLayout());
        
        txtF = new JTextField("文字盒: 填入華氏溫度",20);
        lblC = new JLabel("標籤: 顯示攝氏溫度");
        
        txtF.addActionListener
        (
        		new ActionListener()
        		{
       
		@Override
			public void actionPerformed(ActionEvent e) 
			{
			// TODO Auto-generated method stub
			 double f = Double.parseDouble(txtF.getText());
		     double c = 5.0/9.0 * (f - 32);
		     lblC.setText(Double.toString(c));
		     
			} 
        });
        
        this.add(txtF);
        this.add(lblC);
    }
}

public class ANS_G1
{

  public static void main(String args[])
  {
      ConvertFtoC cfc = new ConvertFtoC();
      cfc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      cfc.setSize(400,200);
      cfc.setVisible(true);
  }
}