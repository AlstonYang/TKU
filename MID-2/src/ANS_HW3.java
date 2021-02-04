/*
HW3個人作業：可清除之塗鴨     4/16  (二)
畫面北方有[清空Clear]、[取消Undo]、[復原Redo]按鈕，中央有塗鴨面板。
  利用滑鼠可以壓下畫圓點圖，並利用按鈕清空、取消、復原最近所畫圓點。
  提示:
  1.參考課本塗鴨Painter範例，點清單可以改設計成
  	java.util.LinkedList points; // 有效點清單
  	java.util.LinkedList undoPoints;  // 取消點清單
  2.鏈結清單資料可以頭尾進出或清空
        linkedlist.addFirst/addLast/removeFirst/removeLast/clear
  3.遇清空按鈕,清空兩清單
  4.遇取消按鈕,刪除有效點清單之尾,將其加入取消點清單之頭
  5.遇復原按鈕,刪除取消點清單之頭,將其加入有效點清單之尾
  6.自訂面板在paintComponent畫圖時,只畫有效點清單內容
  7.滑鼠壓下新增畫點時,將之加入有效點清單尾,清空取消點清單
  8.自訂面板殼寫法如下:
	class MyPanel extends JPanel
	{
  	   LinkedList points; // 有效點清單
  	   LinkedList undoPoints;  // 取消點清單
  	   JButton btnClear;
  	   JButton btnUndo;
  	   JButton btnRedo;
  	   JPanel canvas;
	   public MyPanel()
	   {
	     points = new LinkedList();
	     undoPoints = new LinkedList();
	     btnClear = new JButton("清空");
	     btnUndo = new JButton("取消");
	     btnRedo = new JButton("復原");
             btnClear.addActionListener(
               new ActionListener()
               {
               	  public void actionPerformed(ActionEvent ae)
               	  {
               	    //遇清空按鈕,清空兩清單
             	    canvas.repaint(); // 重畫面板
               	  }
               });
             btnUndo.addActionListener(
               new ActionListener()
               {
               	  public void actionPerformed(ActionEvent ae)
               	  {
               	    //遇取消按鈕,刪除有效點清單之尾,將其加入取消點清單之頭
             	    canvas.repaint(); // 重畫面板
               	  }
               });
             btnRedo.addActionListener(
               new ActionListener()
               {
               	  public void actionPerformed(ActionEvent ae)
               	  {
               	    //遇復原按鈕,刪除取消點清單之頭,將其加入有效點清單之尾
             	    canvas.repaint(); // 重畫面板
               	  }
               });
             canvas = new JPanel()
               {
                 // 面板重畫方法
	         public void paintComponent(Graphics g)
	         {
	           super.paintComponent(g);
	           // 依據有效點清單畫圓點g.fillOval
	         }
               };
             // 面板註冊滑鼠移動監聽器
	     canvas.addMouseMotionListener(
	       new MouseMotionAdapter()
	       {
	         public void mouseDragged(MouseEvent me)
	         {
	           //將畫點x,y加入有效點清單尾,清空取消點清單
	           MyPanel.this.repaint(); // 呼叫面板重畫方法
	         }
	       }
	     );
	     // 元件排版
	     this.setLayout(new BorderLayout());  // 面板採方位排版
	     Box box = Box.createHorizontalBox(); // 建立水平盒容器
	     box.add( Box.createHorizontalGlue() ); // 置中元件前加膠水
	     box.add(btnClear);
	     box.add(btnUndo);
	     box.add(btnRedo);
	     box.add( Box.createHorizontalGlue() ); // 置中元件後加膠水
	     this.add(box, BorderLayout.NORTH);  // 水平盒置於北方位
	     this.add(canvas, BorderLayout.CENTER); // 畫布置於中方位
	   }
	}
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MyPanel extends JPanel {

    LinkedList<Point> points; // 有效點清單
    LinkedList<Point> undoPoints;  // 取消點清單

    JButton btnClear;
    JButton btnUndo;
    JButton btnRedo;
    JPanel canvas;

    public MyPanel() {
        points = new LinkedList<>();
        undoPoints = new LinkedList<>();

        btnClear = new JButton("清空");
        btnUndo = new JButton("取消");
        btnRedo = new JButton("復原");

        btnClear.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //遇清空按鈕,清空有效點及取消點兩清單
            	points.clear();
            	undoPoints.clear();
                canvas.repaint(); // 重畫面板
            }
        });

        btnUndo.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //遇取消按鈕,刪除有效點清單之尾,將其加入取消點清單之頭
            	if(points.isEmpty() == false) {
            	Point rem = points.getLast();
            	points.removeLast();
            	undoPoints.add(rem);
                canvas.repaint(); // 重畫面板
            	}
            }
        });

        btnRedo.addActionListener(
                new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //遇復原按鈕,刪除取消點清單之頭,將其加入有效點清單之尾
            	if(undoPoints.isEmpty() == false) {
            	Point add = undoPoints.getLast();
            	points.add(add);
            	undoPoints.removeLast();
                canvas.repaint(); // 重畫面板
            	}
            }
        });

        canvas = new JPanel() {
            // 面板重畫方法
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                for(Point point: points) {
                g.fillOval(point.x, point.y, 10, 10);}
                // 依據有效點清單畫圓點g.fillOval
            }
        };

        // 面板註冊滑鼠移動監聽器
        canvas.addMouseMotionListener(
                new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                //將畫點x,y加入有效點清單尾,清空取消點清單
            	points.add(me.getPoint());
            	undoPoints.clear();
                MyPanel.this.repaint(); // 呼叫面板重畫方法
            }
        }
        );
        
        canvas.setBackground(Color.yellow);

        // 元件排版
        this.setLayout(new BorderLayout());  // 面板採方位排版
        Box box = Box.createHorizontalBox(); // 建立水平盒容器
        box.add(Box.createHorizontalGlue()); // 置中元件前加膠水
        box.add(btnClear);
        box.add(btnUndo);
        box.add(btnRedo);
        box.add(Box.createHorizontalGlue()); // 置中元件後加膠水
        this.add(box, BorderLayout.NORTH);  // 水平盒置於北方位
        this.add(canvas, BorderLayout.CENTER); // 畫布置於中方位
    }
}

class MyPainter extends JFrame {

    JPanel pnlCanvas;

    public MyPainter() {
        super("可清除之塗鴨");
     
        pnlCanvas = new MyPanel();
        this.add(pnlCanvas, BorderLayout.CENTER);
    }
}

public class ANS_HW3 {

    public static void main(String args[]) {
        MyPainter jframe = new MyPainter();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(600, 400);
        jframe.setVisible(true);
    }
}
