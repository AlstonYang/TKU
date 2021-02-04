  /*
HW5個人作業： 九宮格  W13~W15. 5/28 (二)
  仿拼圖設計，遊戲畫面配置九宮格，隨機填滿1~8數字，及1個空格。
  玩家可用鍵盤操作，以四方位箭頭，將空格附近數字移進空格。
  最後排成如下形狀，遊戲即結束:
  	1 2 3
  	4 5 6
  	7 8

提示:
1.利用鍵盤監聽器，偵測四方位箭頭事件
2.每格數字及空格皆為按鈕物件，利用格子排版器置於3x3方格內

可擴充處:
1.自動偵測遊戲結束
2.提供各種結束形狀
3.提供重玩按鈕
4.顯示累計移動多少步才結束,步數愈少愈好
5.紀錄移動過程，可退回前幾步
*/

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


// 定義九宮格面板
class Puzzle extends JPanel 
{
    List<JButton> listButton; // 存放九宮格9個按鈕的清單
    Font font;  // 按鈕文字採用的字型
    GridLayout gridLayout;
    
    public Puzzle() 
    {
        // 建立空按鈕清單listButton
    	listButton = new ArrayList<>();
        // 建立按鈕文字採用的字型font
         font = new Font("標楷體", Font.PLAIN, 20);
        // 利用迴圈建立8個按鈕，加入按鈕清單，按鈕文字介於1~8，採用font字型
         for(int i=0;i<8;i++) {
        	 listButton.add(new JButton(Integer.toString(i+1))); 
        	 listButton.get(i).setFont(font);
         }
        // 建立空白按鈕，加入按鈕清單listButton
         listButton.add(new JButton(" "));
        // 利用Collections.shuffle方法，將按鈕清單listButton順序打亂
         Collections.shuffle(listButton);
        // 設定九宮格面板的排版器為GridLayout，畫分3x3，共9格
         gridLayout = new GridLayout(3,3);
         setLayout(gridLayout);
        // 逐一列舉將按鈕清單listButton的按鈕加入九宮格面板
         for(JButton button:listButton) {
        	 
        	 add(button);
        	 
         }
         
        // 註: 依上述按鈕加入面板順序，排版之後，九宮格及按鈕清單下標的對應關係如下:
        //     0   1   2
        //     3   4   5
        //     6   7   8
        // 例如，listButton[4]按鈕必位於中心處，listButton[8]按鈕必位於右下角
    }
}

// 定義鍵盤事件處理器
class TAdapter extends KeyAdapter 
{
    Puzzle puzzle;  // 記錄九宮格面板物件
    
    // 鍵盤下壓事件處理方法
    public void keyPressed(KeyEvent e) 
    {
        int idxBlank=-1;  // 空白格在按鈕清單的下標位置，-1表無效格
        int idxFrom=-1;   // 方向鍵希望移動的按鈕在按鈕清單的下標位置，-1表無效格
        
        // 利用迴圈列舉按鈕清單 puzzle.listButton，
        // 找出空白格在按鈕清單的下標位置idxBlank
        
        // 空白格特徵為.getText()取得文字為空字串""

        // 從鍵盤事件e，取得按鍵掃瞄碼key
        for(JButton button:puzzle.listButton) {
        	
        	if(button.getText()==" ") {
        		
        		idxBlank = puzzle.listButton.indexOf(button);
        	}
     
        }
        
        int key = e.getKeyCode();

        // 如果按鍵掃瞄碼key為左箭頭，則欲移動格為空白格的右方格
        // 由 idxBlank 下標找出對應的 idxFrom下標
        // 若無右方格，idxFrom維持-1
        if (key == KeyEvent.VK_LEFT) {
        	
        	idxFrom = idxBlank +1;
        	
        	if(idxBlank ==2 || idxBlank==5 ||idxBlank==8) {
        		
        		idxFrom = -1;
        	
        	}
        }

        // 如果按鍵掃瞄碼key為右箭頭，則欲移動格為空白格的左方格
        // 由 idxBlank 下標找出對應的 idxFrom下標
        // 若無左方格，idxFrom維持-1
        if (key == KeyEvent.VK_RIGHT) {
        	
        	idxFrom = idxBlank -1;
        	
        	if(idxBlank ==0 || idxBlank==3 ||idxBlank==6) {
        		
        		idxFrom = -1;
        	
        	}
        }

       // 如果按鍵掃瞄碼key為上箭頭，則欲移動格為空白格的下方格
       // 由 idxBlank 下標找出對應的 idxFrom下標
       // 若無下方格，idxFrom維持-1
       if (key == KeyEvent.VK_UP) {
    	   idxFrom = idxBlank +3;
    	   
    	   if(idxBlank ==6 || idxBlank==7 ||idxBlank==8) {
       		
       			idxFrom = -1;
       	
       		}
       }

        // 如果按鍵掃瞄碼key為下箭頭，則欲移動格為空白格的上方格
        // 由 idxBlank 下標找出對應的 idxFrom下標
        // 若無上方格，idxFrom維持-1
        if (key == KeyEvent.VK_DOWN) {
        	 idxFrom = idxBlank -3;
        	 
        	 if(idxBlank ==0 || idxBlank==1 ||idxBlank==2) {
            		
        			idxFrom = -1;
        	
        		}
        }
        
        // 如果欲移動格的下標>=0，表示為有效移動格
        if (idxFrom >= 0) 
        {
            // 將按鈕清單中，idxBlank及idxFrom兩個下標所存放的按鈕對調位罝
            // 可利用Collections.swap方法
        	Collections.swap(puzzle.listButton, idxBlank, idxFrom);           
            // 呼叫九宮格面板的.removeAll() 方法清除容器內的所有按鈕
        	puzzle.removeAll();
            // 重新逐一列舉將按鈕清單的按鈕加入九宮格面板
        	for(JButton button:puzzle.listButton) {
           	 
        		puzzle.add(button);
           	 
            }
            // 呼叫九宮格面板的.updateUI() 方法，重新呈現排版結果
        	puzzle.updateUI();
        }
     }
    
    // 鍵盤事件處理器建構子，傳入九宮格面板
    public TAdapter(Puzzle p)
    {
        puzzle = p;
    }
}


public class A405636548_HW5
{

    // 說明頁籤的文字內容
    static String msg = 
       "HW5個人作業： 九宮格  W13~W15. 5/28 (二)\n"
      +"仿拼圖設計，遊戲畫面配置九宮格，隨機填滿1~8數字，及1個空格。\n"
      +"玩家可用鍵盤操作，以四方位箭頭，將空格附近數字移進空格。\n"
      +"最後排成如下形狀，遊戲即結束:\n"
      +"1 2 3\n"
      +"4 5 6\n"
      +"7 8\n";

    public static void main(String args[]) 
    {
        JTabbedPane tabPane = new JTabbedPane();
        
        Puzzle p = new Puzzle();
        tabPane.addTab("Puzzle", null, p, "Puzzle Game");
        tabPane.addTab("說明", null, new JTextArea(msg), "遊戲說明");
        
        JFrame jframe = new JFrame();
        jframe.setTitle("HW5 九宮格");
        jframe.add(tabPane, BorderLayout.CENTER);
        jframe.setFocusable(true);
        jframe.addKeyListener(new TAdapter(p));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(400, 400);
        jframe.setVisible(true);
    }
}