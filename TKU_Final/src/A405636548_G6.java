
/*
G7分組練習: 指針鐘
a3.(指針時鐘,analog clock)製作一含刻度及時,分,秒三針的時鐘,隨時顯示目前時刻.
   下方並文字顯示目前的年/月/日/星期幾。
提示:
1.利用java.time.LocalDateTime類別，如下取得目前年月日時分秒
     LocalDateTime time = LocalDateTime.now(); // 取得目前時間
     int year = time.getYear();  // 西元年
     int month = time.getMonthValue();  // 1-12月
     int date = time.getDayOfMonth();  // 1-31日
     String weekday = time.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.TAIWAN); // 日~六
     int hour = time.getHour(); // 0-23時
     int min = time.getMinute(); // 0-59分
     in sec = time.getSecond(); // 0-59秒
2.利用緒池執行run方法內的任務
    ExecutorService pool = Executors.newCachedThreadPool();
    pool.execute(new Runnable() { 
        public void run() 
        { 
           // 任務內容
        } 
    });
    pool.shutdown();
3.讓緒休息milliseconds毫秒寫法
        try {
              Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
              System.out.println(ex);
        }
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;

// 定義時鐘物件
class Clock extends JPanel implements Runnable {
	LocalDateTime now; // 本地目前日期時間
	ExecutorService pool; // 緒池

	// 依據目前時鐘寬高，回傳以時鐘圓心為原點
	// 極座標:(半徑r, 角度angle) 對應的 XY座標:(x,y) 點物件
	private Point polarToXY(int angle, int radius) {
		int width = Clock.this.getWidth();
		int height = Clock.this.getHeight();

		int center_x = width / 2;
		int center_y = height / 2;

		double angd = angle;
		double radd = radius;

		Point p = new Point();
		p.x = center_x + (int) (Math.sin(angd * Math.PI / 180.0) * radd);
		p.y = center_y - (int) (Math.cos(angd * Math.PI / 180.0) * radd);

		return p;
	}

	// 利用畫圖環境g，畫出時鐘圓心為原點
	// 介於 極座標:(半徑radiusFrom, 角度angle) 及
	// 極座標:(半徑radiusTo, 角度angle)
	// 兩點之間的半徑方向直線

	private void drawLine(Graphics g, int angleFrom, int angleTo, int radius) {
		g.fillOval(this.getWidth() / 2, this.getHeight() / 2, 10, 10);
		// 呼叫 polarToXY 方法，求出兩極座標對應的XY座標
		Point p1 = polarToXY(angleFrom, radius);
		Point p2 = polarToXY(angleTo, radius);

		// 呼叫 g.drawLine方法，畫出兩XY座標連線
		g.drawLine(p1.x, p1.y, p2.x, p2.y);

	}

	// 時鐘顯示方法
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 套用背景色
		g.drawString("黃色區塊為時鐘面積，待補指針，刻度，日期", 100, 20);

		// 詢問時鐘面積寬高，呼叫getWidth,getHeight方法
		int width = Clock.this.getWidth();
		int height = Clock.this.getHeight();
		// 由全域變數now，詢問年月日星期幾
		int year = now.getYear(); // 西元年
		int month = now.getMonthValue(); // 1-12月
		int date = now.getDayOfMonth(); // 1-31日
		String weekday = now.getDayOfWeek().getDisplayName(TextStyle.NARROW, Locale.TAIWAN); // 日~六

		// 組合年月日星期幾字串，畫於時鐘下方
		g.drawString(String.format("%d年%d月%d日 星期 %s", year, month, date, weekday), 200, 20);
		// 畫出時鐘刻度
		drawLine(g, now.getSecond() * 6, 0, width / 2);
		drawLine(g, now.getSecond() * 6, 0, width / 2);
		// 畫出時鐘指針

	}

	@Override
	// 回傳初始時鐘面積尺寸
	public Dimension getPreferredSize() {
		return new Dimension(500, 500);
	}

	// 建構子
	public Clock() {
		Clock.this.setBackground(Color.yellow);

		// 建立緒池
		pool = Executors.newCachedThreadPool();
		// 交付時鐘更新任務給緒池
		pool.execute(task);
		// 緒池關閉，不再接受新任務
		pool.shutdown();
	}

	// 時鐘更新任務
	Runnable task = new Runnable() // 定義任務
    { 
    	public void run() 
    	{
      // 無窮迴圈，不斷更新時鐘
    		while(true)
    		{	
          // 取得目前時間，存於全域變數now
    			now = LocalDateTime.now();
          // 對自己送出重畫事件，依據now值顯示時鐘
    			repaint();
    	  
    			try {         
    				Thread.sleep(1000);          
    			} catch (InterruptedException ex) {
    				System.out.println(ex);
        	  
    			}
    		}	    	
}

// 定義主程式類別
public class A405636548_G6
{
  public static void main(String args[]) throws InterruptedException
  {
      JFrame jframe = new JFrame("G7 指針鐘");
      Clock c = new Clock();
      jframe.add(c, BorderLayout.CENTER);
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //jframe.setSize(600,400);
      jframe.pack();  // jframe初始大小詢問時鐘物件決定
      jframe.setVisible(true);
  }
}