/*
HW4個人作業： 文字跑馬燈 5/21 (二)
a1.(文字跑馬燈,text marquee)
   製作一跑馬燈,可由左而右捲動一串文字,周而復始.
   並提供一個滑軸可以調控跑馬燈速度
提示:
 1.文字物件記錄文字內容，位置，字型，所在畫布
   並提供畫字方法draw
 2.利用緒模擬計時器，每休息少量時間，則依滑軸值調整文字(x,y)，重新畫字
   當位置超出畫面邊界時，適當調整位置，產生文字從另一邊逐漸出來的跑馬燈感覺
 3.利用緒執行任務寫法:
    Runnable task = new Runnable() 
    { 
        public void run() 
        { 
           // 任務內容
        } 
    };
    Thread thread  =  new Thread(task);
    thread.start();
 4.讓緒休息milliseconds毫秒寫法
        try {
              Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
              System.out.println(ex);
        }
可擴充處
1. 詢問文字寬度，適當調整邊界條件，讓文字面積全移出邊界後，才從另一邊逐漸冒出來
2. 文字除水平，也可以垂直，像球任意方向移動
3. 文字加顏色屬性
*/




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// 定義文字物件
class Text
{
    int pos_x;  // 文字目前位置X軸座標 
    int pos_y;  // 文字目前位置Y軸座標
    Font font;  // 文字字型
    String text;  // 文字內容
    JPanel canvas;  // 文字所在畫布
    
    // 更新文字位置水平移動speed像素
    public void updatePos(int speed)
    {
        // 取得目前畫布寬度
    	int width = canvas.getWidth();
        // 將目前水平位置pos_x加上speed移動量
        pos_x += speed;
        // 若水平位置pos_x超過畫布寬度，位置歸零
        if(pos_x > width)
        	pos_x=0;
        if(pos_x < 0)
        	pos_x = width;
       // 若水平位置pos_x小於0，位置設為畫布寬度
    }
    
    // 利用畫圖環境g，將形狀畫於畫布上
    public void draw(Graphics g)
    {
        // 設定畫圖環境g的字型為全域變數font
        g.setFont(font);
        // 於文字位置pos_x,pos_y畫出文字內容text
        g.drawString(text, pos_x, pos_y);
    }
    
    // 文字物件建構子,傳入
    //   文字內容s,字型f,初始位置x,y,所在畫布c
    public Text(String s, Font f, int x, int y, JPanel c)
    {
        text = s;
        font = f;
        pos_x = x;
        pos_y = y;
        canvas = c;
    }
}

// 定義文字跑馬燈物件
class TextMarquee extends JPanel 
{
    JPanel canvas;   // 畫布
    JSlider slider;  // 滑軸 
    Thread timer;  // 緒，當計時器用
    int speed; // 文字單位時間水平移動量
    Text text; // 文字物件

    public TextMarquee() 
    {
        // 建立滑軸物件，左右邊界介於-50到50之間
        slider = new JSlider(SwingConstants.HORIZONTAL, -50, 50, 10);
        
        // 定義滑軸監聽物件
        ChangeListener change = new ChangeListener()
        {
            // 滑軸狀態變化處理方法
            public void stateChanged(ChangeEvent ce)
            {
                // 取得滑軸值，填入speed全域變數
            	speed = slider.getValue();
            	
             }
        };
        // 註冊滑軸監聽物件
        slider.addChangeListener(change);
        // 取得滑軸值，初始化speed全域變數
        speed = slider.getValue();
        
        // 定義畫布重畫方法
        canvas = new JPanel() 
        {
            // 畫布重畫方法
            @Override
            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                g.drawString("黃色區塊為跑馬燈面積", 200, 20);
                text.draw(g);
                // 呼叫 text文字物件的draw方法，將文字畫在畫布上
                
            }
        };
        canvas.setBackground(Color.yellow);

        // 定義文字物件
        Font f = new Font("標楷體", Font.PLAIN, 80);
        text = new Text("跑馬燈文字",f, 0,200,TextMarquee.this);

        // 定義緒任務
        Runnable task = new Runnable() 
        {
            @Override
            // 緒任務內容
            public void run()
            {
                // 無窮迴圈
                while(true)
                {
                    // 呼叫updatePos方法，傳入speed移動量，更新text文字物件位置
                    text.updatePos(speed);
                    // 送重畫事件給跑馬燈物件
                   repaint();
                    // 讓緒休息0.1秒
                    try {
                        Thread.sleep(100);
                  } catch (InterruptedException ex) {
                        System.out.println(ex);
                  }
                }
            }
        };
        timer = new Thread(task);
        
        // 啟動緒執行任務
        timer.start();        
        // 元件排版
        this.setLayout(new BorderLayout());  // 面板採方位排版
        this.add(slider, BorderLayout.SOUTH);  // 滑軸置於南方位
        this.add(canvas, BorderLayout.CENTER); // 畫布置於中方位
    }
}

public class A405636548_HW4 {

    public static void main(String args[]) {
        JFrame jframe = new JFrame();
        TextMarquee marquee = new TextMarquee();
        jframe.add(marquee, BorderLayout.CENTER);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(600, 400);
        jframe.setVisible(true);
    }
}