/*
G4分組練習: 滑鼠拖畫長方形
畫面上有一自訂面板(JPanel)，可偵測滑鼠如下事件，達成拖畫長方形目的。
	mousePressed: 遇滑鼠壓下事件,記錄起點
	mouseDragged: 遇滑鼠拖拉事件,記錄終點,並於起終點之間畫長方形
提示:
1.畫長方形方法如下:
		graphics.drawRect(x,y,width,height);
  其中,畫圖環境graphics,長方形左上起點座標(x,y),寬width,高height
2.自訂面板殼寫法如下:
	class MyPanel extends JPanel
	{
	   int x,y,width,height; // 長方形左上起點座標及寬高
           // 面板註冊滑鼠事件監聽器
	   public MyPanel()
	   {
	     this.addMouseListener(
	       new MouseAdapter()
	       {
	         public void mousePressed(MouseEvent me)
	         {
	           //記錄x,y
	         }
	       }
	     );
	     this.addMouseMotionListener(
	       new MouseMotionAdapter()
	       {
	         public void mouseDragged(MouseEvent me)
	         {
	           //記錄width,height
	           MyPanel.this.repaint(); // 呼叫面板重畫方法
	         }
	       }
	     );
	   }
           // 面板重畫方法
	   public void paintComponent(Graphics g)
	   {
	     super.paintComponent(g);
	     // 依據x,y,width,height畫長方形
	   }
	}
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MyPanel extends JPanel {

    int x1, y1,x2,y2,width, height; // 長方形左上起點座標及寬高

    // 面板註冊滑鼠事件監聽器
    public MyPanel() {
        this.addMouseListener(
                new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                //記錄x,y
            	x1 = me.getX();
            	y1 = me.getY();
            }
        }
        );

        this.addMouseMotionListener(
                new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                //記錄width,height
            	x2 = me.getX();
            	y2 = me.getY();

                MyPanel.this.repaint(); // 呼叫面板重畫方法
            }
        }
        );
    }

    // 面板重畫方法
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 依據x,y,width,height畫長方形
        g.drawRect(Math.min(x1,x2),Math.min(y1, y2),Math.abs(x1-x2),Math.abs(y1-y2));
    }
}

class DrawRectangle extends JFrame
{
    JPanel pnlCanvas;
    
    public DrawRectangle()
    {
        super("滑鼠拖畫長方形");
        //this.setLayout(new FlowLayout());
        
        pnlCanvas = new MyPanel();
        pnlCanvas.setBackground(Color.yellow);
        
        this.add(pnlCanvas, BorderLayout.CENTER);
    }
}

public class ANS_G4
{
  public static void main(String args[])
  {
      DrawRectangle jframe = new DrawRectangle();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(400,200);
      jframe.setVisible(true);
  }
}
