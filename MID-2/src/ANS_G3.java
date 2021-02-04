/*
G3分組練習: 選單盒設字體

畫面設置一寬度25個字的文字盒(JTextField)，用戶可填入任意文字。
另外設置一個選單盒(JComboBox)，內含所有系統字體，一次看得到10個選項。
用戶選擇任一字體後，可以馬上看到文字盒切換為該字體。

提示:
1.取得系統支援所有字體的方法:

	java.awt.GraphicsEnvironment
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	String fa[] = ge.getAvailableFontFamilyNames();
	for(int i=0; i<fa.length; i++)
	  System.out.println(fa[i]);

2.文字盒切換字體方法:

	jtextfield.setFont(new Font(字體字串,Font.PLAIN,大小));
*/

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

class ComboFont extends JFrame
{
    JComboBox<String> cboFont;
    JTextField txtText;
    
    public ComboFont(String text)
    {
    	
        super("選單盒設字體");
        this.setLayout(new FlowLayout());
        txtText = new JTextField(text,25);
        
        java.awt.GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String fa[] = ge.getAvailableFontFamilyNames();        
        cboFont = new JComboBox<>(fa);
        cboFont.setMaximumRowCount(10);

        cboFont.addItemListener(
                new ItemListener() 
                {
                   
                   @Override
                   public void itemStateChanged(ItemEvent event)
                   {                     
                      if (event.getStateChange() == ItemEvent.SELECTED)
                    	  txtText.setFont(new Font(fa[cboFont.getSelectedIndex()],Font.PLAIN,12));
                   } 
                } 
             ); 
        
        
        this.add(txtText);
        this.add(cboFont);
    }
}

public class ANS_G3
{
  public static void main(String args[])
  {
      String text = "初始文字，供套用不同字型之用";
      ComboFont jframe = new ComboFont(text);
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(400,200);
      jframe.setVisible(true);
  }
}