/*
G5分組練習: 形狀塗鴨
t4.設計形狀MyShape抽象類別,內含狀態家規如下
	邊界對角點一Point corner1,
	邊界對角點二Point corner2,
	顏色Color color,
	實心否boolean filled,
    對外提供抽象方法家規draw,希望可據corner1,corner2,color,filled適當畫出形狀.
    並設計如下線,方,圓三形狀類別,繼承自MyShape類別,補實各自形狀的draw方法
  設計畫板DrawPanel類別,繼承自JPanel類別,內含如下狀態
	形狀陣列ArrayList<MyShape> shapeArray,
	形狀類型int shapeType,
	新形狀MyShape currentShape,
	新顏色Color currentColor,
	畫實心形狀否boolean filledShape,
	狀態列JLabel statusLabel,顯示目前滑鼠座標
     對外提供如下方法,
        paintComponent,只要有形狀可畫,逐一呼叫形狀陣列每個形狀的draw方法,
        setShapeType,setCurrentColor,setFilledShape,三狀態設定方法,
        clearLastShape,移除陣列最後一個形狀,呼叫repaint
        clearDrawing,清除陣列所有形狀,呼叫repaint
     	DrawPanel建構子,接受一標籤盒參數作為狀態列,建構空的形狀陣列,
     		背景色Color.WHITE,註冊適當滑鼠事件處理器,設定初值如下
     		shapeType為直線,currentShape為null,
     		currentColor為Color.BLACK
   設計滑鼠處理器MouseHandler類別,繼承自MouseAdpater,實作MouseMotionListener
     處理所有畫板類別所需滑鼠事件,如下
   	mousePressed,依shapeType建構新形狀物件,暫存於currentShape變數,
   		設定新形狀初始邊界對角點1,2位置皆為滑鼠按下座標.
   	mouseReleased,設定新形狀邊界對角點2位置為滑鼠放開座標,
   		將新形狀放入形狀陣列最後一格,
   		設定新形狀為null,呼叫repaint重畫畫板所有形狀.
   	mouseMoved,將滑鼠於畫板內移動座標顯示於狀態列上
   	mouseDragged,設定新形狀邊界對角點2位置為滑鼠拖動座標,
   		呼叫repaint重畫畫板所有形狀,讓用戶看出形狀最新拖動效果
   		將滑鼠於畫板內拖動座標顯示於狀態列上
   設計畫框DrawFrame類別,繼承自JFrame類別,使用邊界排版器佈置
   	中央畫板,北方選項列,南方標籤盒狀態列,其中,選項列包含
   		復原按鈕,刪除形狀陣列最後一個形狀,若存在的話
   		清空按鈕,刪除形狀陣列所有形狀
   		顏色組合盒,提供13種預設形狀顏色,例如紅黑綠白等
   		形狀組合盒,提供線,方,圓等形狀
   		實心勾選盒,提供畫實心或空心形狀參考
	其建構子在建構畫板時,記得先建構標籤盒,再傳給畫板供狀態列用.
   測試時,以能畫出課本習題高爾夫球洞為目標.
   提示:線的空實心效果相同,
        設定顏色指令為g.setColor(color),
        空實心的線,方,圓形狀畫圖指令如下
   		g.drawLine(x1,y1,x2,y2)
   		g.drawRect(x1,y1,width,height)
   		g.fillRect(x1,y1,width,height)
   		g.drawOval(x1,y1,width,height)
   		g.fillOval(x1,y1,width,height)
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// 定義形狀家規
abstract class MyShape
{
    Point corner1; // 邊界對角點一
    Point corner2; // 邊界對角點二Point corner2,
    Color color; // 顏色
    boolean filled; // 實心否
    
    // 依據corner1,corner2,color,filled適當畫出形狀
    abstract public void draw(Graphics g);
}

// 定義直線形狀物件
class Line extends MyShape
{
    @Override
    public void draw(Graphics g)
    {  // 依據corner1,corner2,color,filled畫直線
    	g.setColor(color);
    	g.drawLine(corner1.x, corner1.y, corner2.x, corner2.y);
    	
    }
}

// 定義矩形形狀物件
class Rectangle extends MyShape
{
    @Override
    public void draw(Graphics g)
    {  // 依據corner1,corner2,color,filled畫矩形
    	g.setColor(color);
    	if(filled==true)    		
    			g.fillRect(Math.min(corner1.x, corner2.x),Math.min(corner1.y, corner2.y),Math.abs(corner2.x-corner1.x), Math.abs(corner1.y-corner2.y));  			

    	else {
    			g.drawRect(Math.min(corner1.x, corner2.x),Math.min(corner1.y, corner2.y),Math.abs(corner2.x-corner1.x), Math.abs(corner1.y-corner2.y));
    	}

    }
}

// 定義橢圓形狀物件
class Circle extends MyShape
{
    @Override
    public void draw(Graphics g)
    {   // 依據corner1,corner2,color,filled畫橢圓
    	g.setColor(color);
    	if(filled==true) 
    	{  		
    			g.fillOval(Math.min(corner1.x, corner2.x),Math.min(corner1.y, corner2.y),Math.abs(corner2.x-corner1.x), Math.abs(corner1.y-corner2.y));	
    	}
    	else 		
    			g.drawOval(Math.min(corner1.x, corner2.x),Math.min(corner1.y, corner2.y),Math.abs(corner2.x-corner1.x), Math.abs(corner1.y-corner2.y));
    }
}

// 定義畫板物件
class DrawPanel extends JPanel 
{
    LinkedList<MyShape> shapeArray; // 形狀陣列，存放畫板留存的所有形狀供重畫之用
    MyShape currentShape; // 新形狀，在滑鼠壓下時建立，放開時移除，供畫出新形狀的中間拖拉結果之用
    int shapeType; // 形狀類型，0,1,2分別代表直線、矩形、橢圓。由工具列cboShape下拉盒決定，供建立新形狀之參考
    Color currentColor; // 新顏色，由工具列cboColor下拉盒決定，供建立新形狀之參考
    boolean filledShape; // 畫實心形狀否，由工具列chkFilled勾選盒決定，供建立新形狀之參考
    JLabel statusLabel; // 狀態列,顯示目前滑鼠座標

    // 若有形狀可畫,逐一呼叫形狀陣列shapeArray每個形狀的draw方法
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);  // 套用背景色

        // 畫完形狀陣列shapeArray所有形狀，再畫新形狀currentShape
        for(MyShape shape : shapeArray) {
        	shape.draw(g);     	
        }
        
        if(currentShape != null) {
        	currentShape.draw(g);
        }
        
        // 新形狀才會出現在所有舊形狀之上
        
    }
    
    public void setShapeType(int shape)
    {  // 供cboShape用，shape=0,1,2分別代表新形狀為直線、矩形、橢圓
    	this.shapeType = shape;
    }
    
    public void setCurrentColor(Color color)
    {  // 供cboColor用
    	currentColor = color;
    }
    
    public void setFilledShape(boolean filled)
    {  // 供chkFilled用
    	this.filledShape = filled;
    	
    }
    
    // 移除陣列最後一個形狀,呼叫repaint
    public void clearLastShape()
    {  // 供btnUndo用
    	shapeArray.removeLast();
    	repaint();
    }

    // 清除陣列所有形狀,呼叫repaint
    public void clearDrawing()
    {  // 供btnClear用
    	shapeArray.clear();
    	repaint();
    }

    // 建構子
    public DrawPanel(JLabel lblStatus)
    {
        // 接受一標籤盒參數作為狀態列,建構空的形狀陣列,
    	statusLabel = lblStatus;
    	shapeArray = new LinkedList<MyShape>();
     	// 背景色Color.WHITE,註冊適當滑鼠事件處理器,設定初值如下
    	setBackground(Color.WHITE);
     	// shapeType為0 (表示新建形狀將為直線),currentShape為null (表示尚無新形狀),
    	shapeType =0;
    	currentShape = null;
     	// currentColor為Color.BLACK
    	currentColor = Color.BLACK;

        // 以下3行註冊滑鼠監聽物件，傳畫板物件過去，方便監聽物件取得畫板內容
        MouseHandler mouseHandler = new MouseHandler(DrawPanel.this);
        DrawPanel.this.addMouseListener(mouseHandler);
        DrawPanel.this.addMouseMotionListener(mouseHandler);
    }
}

// 定義滑鼠監聽物件
class MouseHandler extends MouseAdapter implements MouseMotionListener
{
    DrawPanel drawPanel; // 記錄畫板物件，方便監聽物件取得畫板內容


    public MouseHandler(DrawPanel drawPanel)
    {    
        MouseHandler.this.drawPanel = drawPanel;
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {   // 依shapeType數值，建立對應的新形狀物件currentShape,
    	if(drawPanel.shapeType==0)
    		drawPanel.currentShape = new Line();
    	else if(drawPanel.shapeType==1)
    		drawPanel.currentShape = new Rectangle();
    	else
    		drawPanel.currentShape = new Circle();
   	// 新形狀currentShape初始邊界corner1及corner2位置皆為滑鼠按下座標
    	drawPanel.currentShape.corner1 = me.getPoint();
    	drawPanel.currentShape.corner2 = me.getPoint();
        // 新形狀currentShape顏色取自畫板currentColor，實心否狀態取自畫板filledShape
    	drawPanel.currentShape.color = drawPanel.currentColor;
    	drawPanel.currentShape.filled = drawPanel.filledShape;
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {   // 設定新形狀currentShape邊界corner2位置為滑鼠放開座標,
    	drawPanel.currentShape.corner2=me.getPoint();
   	// 新形狀currentShape顏色取自畫板currentColor，實心否狀態取自畫板filledShape
    	drawPanel.currentShape.color =drawPanel.currentColor;
    	drawPanel.currentShape.filled =drawPanel.filledShape;
        // 將新形狀currentShape放入形狀陣列shapeArray最後一格,
    	drawPanel.shapeArray.addLast(drawPanel.currentShape);
   	// 設定新形狀currentShape為null,呼叫repaint重畫畫板所有形狀.
    	drawPanel.currentShape=null;
    	drawPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {   // 將滑鼠於畫板內移動座標顯示於畫板statusLabel狀態列上
    	drawPanel.statusLabel.setText(String.format("滑鼠移動位置：(x=%d,y=%d),形狀陣列長度：%d", me.getX(),me.getY(),drawPanel.shapeArray.size()));
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {   // 設定新形狀currentShape邊界corner2位置為滑鼠拖動座標,
    	drawPanel.currentShape.corner2 = me.getPoint();
   	// 呼叫repaint重畫畫板所有形狀,讓用戶看出形狀最新拖動效果
    	drawPanel.repaint();
   	// 將滑鼠於畫板內拖動座標顯示於畫板statusLabel狀態列上
    	drawPanel.statusLabel.setText(String.format("滑鼠拖拉位置：(x=%d,y=%d),形狀陣列長度：%d", me.getX(),me.getY(),drawPanel.shapeArray.size()));
    }
}

// 定義畫形狀的視窗物件
class DrawShapes extends JFrame
{
	DrawPanel pnlCanvas;
    JPanel pnlToolBar;
    JLabel lblStatus;
    JButton btnUndo;
    JButton btnClear;
    JComboBox<String> cboColor;
    JComboBox<String> cboShape;
    JCheckBox chkFilled;
    
    
 
    String colorStringArray[] = {"黑", "紅", "藍", "綠"};
    Color colorArray[] = {Color.black, Color.red, Color.blue, Color.green};
    String shapeStringArray[] = {"直線", "矩形", "橢圓"};

    public DrawShapes()
    {
        super("形狀塗鴨");
        
        
        lblStatus = new JLabel("狀態列");
        pnlCanvas = new DrawPanel(lblStatus);
        pnlCanvas.setBackground(Color.lightGray);

        btnUndo = new JButton("復原");
        btnClear = new JButton("清空");
        cboColor = new JComboBox<>(colorStringArray);
        cboShape = new JComboBox<>(shapeStringArray);
        chkFilled = new JCheckBox("實心");
        pnlToolBar = new JPanel(); // 工具列容器
        pnlToolBar.add(btnUndo);
        pnlToolBar.add(btnClear);
        pnlToolBar.add(cboColor);
        pnlToolBar.add(cboShape);
        pnlToolBar.add(chkFilled);

        this.add(pnlToolBar, BorderLayout.NORTH); // 工具列放北方
        this.add(pnlCanvas, BorderLayout.CENTER); // 畫板放中間
        this.add(lblStatus, BorderLayout.SOUTH); // 狀態列放南方

        // 工具列[復原]按鈕，刪除最後一個形狀
        btnUndo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {  // 呼叫畫板clearLastShape方法
            	pnlCanvas.clearLastShape();
            	
            }
        });

        // 工具列[清空]按鈕，刪除所有形狀
        btnClear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {  // 呼叫畫板clearDrawing方法
            	pnlCanvas.clearDrawing();
            }
        });

        // 工具列[顏色]下拉盒，選擇顏色
        cboColor.addItemListener(new ItemListener()
        {   // 依據cboColor下拉盒選擇，挑選顏色，利用setCurrentColor方法，傳送給pnlCanvas
            public void itemStateChanged(ItemEvent ie)
            {
            	if (ie.getStateChange() == ItemEvent.SELECTED) {
            		pnlCanvas.setCurrentColor(colorArray[cboColor.getSelectedIndex()]); 
              } 
            }
        });

        // 工具列[形狀]下拉盒，選擇形狀
        cboShape.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent ie)
            {   // 將cboShape下拉盒選擇的下標，利用setShapeType方法，傳送給pnlCanvas  
            	if (ie.getStateChange() == ItemEvent.SELECTED) {
            		pnlCanvas.setShapeType(cboShape.getSelectedIndex());
              } 
            }
        });

        // 工具列[實心]勾選盒，選擇實心否
        chkFilled.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent ie)
            {  // 將chkFilled勾選盒狀態，利用setFilledShape方法，傳送給pnlCanvas
            	pnlCanvas.setFilledShape(chkFilled.isSelected());
            	
            }
        });
    }
}

// 定義主程式類別
public class A405636548_G5
{
  public static void main(String args[])
  {
      DrawShapes jframe = new DrawShapes();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(600,400);
      jframe.setVisible(true);
  }
}