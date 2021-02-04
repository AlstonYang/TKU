package other;
/*
G8分組練習: 掃地雷
  仿小精靈設計，遊戲畫面隨意位置配置一個主角及周圍若干顆固定式地雷。
  主角可以由鍵盤箭頭作四方位移動，目標是移動到地雷，清除地雷。
  全部地雷掃除完，遊戲即結束。

提示:
1.利用踫撞偵測，清除地雷
2.利用鍵盤監聽器，偵測四方位箭頭事件
3.主角及地雷皆為按鈕物件
4.主角物件內含位置及鍵盤監聽方法

可擴充處:
1.地雷可於某範圍內自主移動
2.累計主角移動距離，愈短完成任務排名愈前
3.顯示主角移動軌跡
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


// 定義面板物件
class Board extends JPanel
{
    JButton pacman;         // 主角按鈕
    List<JButton> mineList; // 地雷按鈕清單
    Random rand;            // 亂數物件，重排位置用
    boolean inGame;         // 遊戲狀態，真表示遊戲中，假表示遊戲結束
    int width = 500;        // 面板預設寬度
    int height = 300;       // 面板預設高度
    int noMine = 5;         // 預設地雷數

    @Override
    // 畫板重畫方法，顯示狀態字串用
    public void paintComponent(Graphics g)
    {
    	System.out.println(3);
      super.paintComponent(g);
      width = this.getWidth();
      height = this.getHeight();
      
      if(inGame)
          g.drawString("請用鍵盤箭頭移動Pacman，清除地雷。ESC跳出。",180,20);
      else
          g.drawString("Game Over。請按空白鍵，重新開始",200,20);
    }
    
    // 偵測踫撞方法
    public void testCollision()
    {
    	System.out.println(5);
        // 利用.getBounds方法取得主角面積矩形
        Rectangle r1 = pacman.getBounds();
        // 設定有效地雷數為0
        int effective =0;
        // 列舉地雷清單mineList每個地雷
        //   如果地雷有效(.isVisible為真)，則
        //      有效地雷數加1
        //      取出地雷面積矩形
        //      如果地雷矩形和主角矩形有重疊，表示踫撞發生
        //      則設定地雷無效
        for(JButton button:mineList) {
        	if(button.isVisible())
        		effective+=1;
        	Rectangle r2 = button.getBounds();
        	if(r1.intersects(r2)) {
        		button.setVisible(false);
        	}        	
        }
        
        // 如果有效地雷數為0，則設定遊戲狀態為假
        if(effective ==0)
        	inGame =false;
 
    }
    
    // 遊戲初始化
    public void initGame()
    {
        // 設定面板排版器為空，因為按鈕位置將自行排版
        this.setLayout(null);
        // 利用面板的.removeAll方法，清除所有面板內部按鈕
        this.removeAll();
        // 利用清單的 .clear方法，清空地雷清單
        mineList.clear();
        // 利用迴圈產生noMine個地雷
        //   產生第i個地雷按鈕，文字為"地雷i"
        //   將地雷加入地雷清單
        //   將地雷加入面板容器
        //   利用地雷的.setBounds方法設定地雷的矩形位置
        //      矩形左上角座標由亂數物件在面板寬高範圍內隨意決定
        //      矩形寬高由詢問按鈕的getPreferredSize方法決定
        for(int i=0;i<noMine;i++) {
        	
        	mineList.add(new JButton(String.format("地雷%d", i+1)));
        	JButton mine =  mineList.get(i);
        	add(mine);
        	
        	mine.setBounds(rand.nextInt(width), rand.nextInt(height), mine.getPreferredSize().width,mine.getPreferredSize().height);
        	
        }
        
        
        // 將主角按鈕加入面板
        add(pacman);
        
        // 利用主角的.setBounds方法設定主角的矩形位置
        //    矩形左上角座標由亂數物件在面板寬高範圍內隨意決定
        //    矩形寬高由詢問主角按鈕的getPreferredSize方法決定
        pacman.setBounds(rand.nextInt(width), rand.nextInt(height), pacman.getPreferredSize().width,pacman.getPreferredSize().height);
        
        // 設定遊戲狀態為真
        inGame =true;
    }
    
    // 面板建構子
    public Board()
    {
        // 建立地雷清單及亂數物件
        mineList = new ArrayList<>();
        rand = new Random();

        // 載入主角圖片，建立主角按鈕     
        ImageIcon gif = new ImageIcon(getClass().getResource("PacMan1.gif"));
        pacman = new JButton("Pacman",gif);

        // 遊戲初始化
        initGame();
    }
}

// 鍵盤事件處理器
class TAdapter extends KeyAdapter 
{
    Board board;  // 面板物件

    @Override
    // 鍵盤壓下事件處理方法
    public void keyPressed(KeyEvent e) 
    {
    	System.out.println(4);
        int reqdx = 0, reqdy = 0;  // 主角2維移動量(reqdx,reqdy)
        int speed = 5;             // 主角1維移動量，單位像素

        // 取得按鍵掃瞄碼
        int key = e.getKeyCode();

        // 如果遊戲狀態為真
        if (board.inGame) {
            // 遇左箭頭，2維移動量為(-speed,0)
            if (key == KeyEvent.VK_LEFT) 
            {
            	reqdx-=speed;
            }
            // 遇右箭頭，2維移動量為(speed,0)
            else if (key == KeyEvent.VK_RIGHT) 
            {
            	reqdx+=speed;
            } 
            // 遇上箭頭，2維移動量為(0,-speed)
            else if (key == KeyEvent.VK_UP) 
            {
            	reqdy-=speed;
            } 
            // 遇下箭頭，2維移動量為(0,speed)
            else if (key == KeyEvent.VK_DOWN) 
            {
            	reqdy+=speed;
            } 
            // 遇ESC，遊戲結束
            else if (key == KeyEvent.VK_ESCAPE) {
            	board.inGame =false;
            }

            // 利用board.pacman的.getBounds方法，取得主角面積矩形
            Rectangle man = board.pacman.getBounds();
            // 依2維移動量修改矩形的左上角座標值
            man.x += reqdx;
            man.y += reqdy;
            // 利用board.pacman的.setBounds方法，修改主角的面積矩形
            board.pacman.setBounds(man);
            
            // 呼叫面板的踫撞偵測方法
            board.testCollision();
        } 
        else // 如果遊戲狀態為假
        {
            // 遇空白格，呼叫面板的遊戲初始化方法
            if (key == KeyEvent.VK_SPACE) 
            {
            	board.initGame();
            }
        }

        // 送出重畫事件給面板，更新狀態顯示
        board.updateUI();
    }

    // 鍵盤事件處理器建構子，傳入面板物件
    public TAdapter(Board b) {
        board = b;
    }
}

// 定義主程式類別
public class m405636548_G8
{
   // 說明說明頁籤的文字內容
    static String msg = 
      "G8分組練習: 掃地雷\n"
            + "  仿小精靈設計，遊戲畫面隨意位置配置一個主角及周圍若干顆固定式地雷。\n"
            + "  主角可以由鍵盤箭頭作四方位移動，目標是移動到地雷，清除地雷。\n"
            + "  全部地雷掃除完，遊戲即結束。\n"
            + "\n"
            + "提示:\n"
            + "1.利用踫撞偵測，清除地雷\n"
            + "2.利用鍵盤監聽器，偵測四方位箭頭事件\n"
            + "3.主角及地雷皆為按鈕物件\n"
            + "4.主角物件內含位置及鍵盤監聽方法\n"
            + "\n"
            + "可擴充處:\n"
            + "1.地雷可於某範圍內自主移動\n"
            + "2.累計主角移動距離，愈短完成任務排名愈前\n"
            + "3.顯示主角移動軌跡";

    public static void main(String args[]) throws InterruptedException
    {
      // 建立遊戲面板
      Board b = new Board();

      // 建立頁籤面板，加入兩個頁籤，遊戲面板及說明文字盒
      JTabbedPane tabPane = new JTabbedPane();      
      tabPane.addTab("Game", null, b, "Board Game");
      tabPane.addTab("說明", null, new JTextArea(msg), "遊戲說明");
     
      // 建立主視窗，加入頁籤面板
      JFrame jframe = new JFrame("G8 清地雷");
      jframe.add(tabPane, BorderLayout.CENTER);
      
      // 主視窗加入鍵盤監聽物件及設定可收鍵盤訊息
      jframe.addKeyListener(new TAdapter(b));
      jframe.setFocusable(true);
      
      // 設定主視窗X按鈕表示結束，初始寬高，為可視狀態
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(600,400);  // jframe初始大小由外界給定，內部配合
      //jframe.pack();  // jframe初始大小由詢問內部物件決定，jframe配合
      jframe.setVisible(true);
    }
}