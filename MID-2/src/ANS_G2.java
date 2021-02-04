/*
G2分組練習: 勾選盒設背景色

畫面設置紅、藍、綠三勾選盒，及1個[設成背景色]按鈕.
可以複選勾選盒後,點選按鈕看到背景色的變化.
提示: 設背景色作法: jframe.getContentPane().setBackground(new Color(r,g,b));
      r,g,b=0或255，表示紅綠藍成份值。
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class ColorMix extends JFrame
{
    JCheckBox ckbRed;
    JCheckBox ckbGreen;
    JCheckBox ckbBlue;
    JButton change;
    
    public ColorMix()
    {
        super("勾選盒設背景色");
        this.setLayout(new FlowLayout());
        
        ckbRed = new JCheckBox("紅");
        ckbGreen = new JCheckBox("綠");
        ckbBlue = new JCheckBox("藍");
        change = new JButton("設成背景色");
        change.addActionListener(
        		new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int r=0,g=0,b=0;
						if(ckbRed.isSelected())
							r=255;
						if(ckbGreen.isSelected())
							g=255;
						if(ckbBlue.isSelected())
							b=255;
						
						getContentPane().setBackground(new Color(r,g,b));
					}
        			
        		});
        this.add(ckbRed);
        this.add(ckbGreen);
        this.add(ckbBlue);
        this.add(change);
        
       
    }
}


public class ANS_G2
{
  public static void main(String args[])
  {
      ColorMix jframe = new ColorMix();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(400,200);
      jframe.setVisible(true);
  }
}