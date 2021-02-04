package test_pro2;
/*
G6分組練習: 彈跳球

b1.畫面上滑鼠按下後,會出現一球在畫面上直線移動,遇到邊界則彈往相反方向移動.

提示:
 1.球物件記錄球的中心位置(x,y)及單位時間移動向量(dx,dy)
   並提供畫球方法draw

 2.利用計時器事件，每隔單位時間，依(dx,dy)調整球中心位置(x,y)，重新畫球
   當(x,y)超出畫面邊界時，適當調整dx或dy的正負方向，產生彈跳感覺
*/

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// 定義移動形狀類別
abstract class MoveShape
{
    // 宣告protected表示允許後代存取
    protected int pos_x;  // 形狀目前位置X軸座標 
    protected int pos_y;  // 形狀目前位置Y軸座標
    protected int speed_x;  // 單位時間移動X軸像素
    protected int speed_y;  // 單位時間移動Y軸像素
    protected JComponent canvas;  // 形狀所處畫布

    public void updatePos()
    {
        // 詢問所處畫布的寬及高，提示用.getWidth()及.getHeight()方法
         int width = canvas.getWidth();
         int Height  =canvas.getHeight();
        // 依據speed_x,speed_y修正形狀目前位置pos_x,pos_y
         pos_x += speed_x;
         pos_y += speed_y;
        // 若目前位置pos_x或pos_y超出畫布邊界，
       
        if(pos_x>width) {
        	pos_x=width;
        	speed_x *= -1;
        	}
        if(pos_y>Height) {
        	pos_x=Height;
        	speed_y *= -1;
        	}       
        if(pos_x < 0) {
        	pos_x =0;
        	speed_x *= -1;
        	}
        if(pos_y < 0) {
        	pos_y =0;
        	speed_y *= -1;
         	}
        // 則修正位置在邊界上，且speed_x或speed_y方向相反
    }
    
    // 移動形狀建構子，設定基本資料
    public MoveShape(int pos_x, int pos_y, int speed_x, int speed_y, JComponent canvas)
    {
        // 設定移動形狀的基本資料，X,Y軸初始位置(pos_x,pos_y)，
        // 單位時間移動像素(speed_x,speed_y)，所處畫布 canvas
    }
    public void MoveShape(int pos_x, int pos_y, int speed_x, int speed_y, JComponent canvas)
    {
        // 設定移動形狀的基本資料，X,Y軸初始位置(pos_x,pos_y)，
        // 單位時間移動像素(speed_x,speed_y)，所處畫布 canvas
    	this.pos_x =pos_x;
    	this.pos_y =pos_y;
    	this.speed_x = speed_x;
    	this.speed_y = speed_y;
    	this.canvas = canvas;
    
    
    }
    
    // 利用畫圖環境g，將形狀畫於畫布上
    abstract public void draw(Graphics g);
}

// 定義球物件
class Ball extends MoveShape
{
    @Override
    // 利用畫圖環境g，將球形狀畫於畫布上
    public void draw(Graphics g)
    {  
        // 依球目前位置pos_x,pos_y，塗滿某大小的圓
    	g.fillOval(pos_x, pos_y, 20, 20);
    }
    
    // 球建構子，設定球的基本資料
    public Ball(int pos_x, int pos_y, int speed_x, int speed_y, JComponent canvas)
    {
        // 委託上一代建構子，設定球的基本資料，X,Y軸初始位置(pos_x,pos_y)，
        // 單位時間移動像素(speed_x,speed_y)，所處畫布 canvas
        super(pos_x, pos_y, speed_x, speed_y, canvas);
    }
}

// 定義畫布物件
class Canvas extends JPanel 
{
    ArrayList<MoveShape> shapeList; // 移動形狀清單
    Timer timer;  // 計時器
    
    // 畫布重畫方法
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);  // 套用背景色
        g.drawString("請於任意位置按左鍵，可產生任意多顆彈跳球，隨變動的邊界彈跳", 100, 20);
        for(MoveShape ball:shapeList) {
        	ball.draw(g);
        }
        // 逐一呼叫形狀清單每個形狀的draw方法
    }
 
    // 形狀開始移動
    public void start()
    {
    	timer.start();
        // 啟動計時器
    }
    
    // 形狀停止移動
    public void stop()
    {
    	timer.stop();
        // 停止計時器
    }
    
    // 建構子
    public Canvas()
    {
        // 建立空形狀清單
    	shapeList = new ArrayList<MoveShape>(); 
        // 建立每次計時器觸發要監聽的物件
        ActionListener actionListener = new ActionListener()
        {
            // 計時器每次觸發動作事件要作的事
            public void actionPerformed(ActionEvent ae)
            {
                // 逐一呼叫形狀清單每個形狀的updatePos方法，更新位置
            	 for(MoveShape ball:shapeList) {
                 	ball.updatePos();
                 }
                // 呼叫畫布repaint方法，送出重畫事件
            	 repaint();
            }
        };
        
        // 建立計時器，填入多久觸發一次，及每次觸發要監聽的物件
        timer = new Timer(100,actionListener);
        // 以下2行註冊滑鼠監聽物件，傳畫布物件過去，方便監聽物件取得畫布內容
        MouseHandler mouseHandler = new MouseHandler(Canvas.this);
        Canvas.this.addMouseListener(mouseHandler);
    }
}

// 定義滑鼠監聽物件
class MouseHandler extends MouseAdapter
{
    Canvas canvas;  // 畫布物件
    
    public MouseHandler(Canvas c)
    {    
        MouseHandler.this.canvas = c;
    }

    @Override
    public void mouseClicked(MouseEvent me) 
    {  
        // 依滑鼠事件me觸發位置，建立球物件，填入
        // X,Y軸初始位置，單位時間移動像素，及球所處畫布
    	canvas.shapeList.add(new Ball(me.getX(),me.getY(),20,20,canvas));        // 將球加入畫布的形狀清單
                    
        // 畫布重畫
    	canvas.repaint();
    }
}

// 定義主程式類別
public class m405636548_G6
{
  public static void main(String args[]) throws InterruptedException
  {
      JFrame jframe = new JFrame("G6 彈跳球");
      Canvas canvas = new Canvas();
      jframe.add(canvas, BorderLayout.CENTER);
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(600,400);
      jframe.setVisible(true);
      
      canvas.start();
      Thread.sleep(60000);
      canvas.stop();
  }
}