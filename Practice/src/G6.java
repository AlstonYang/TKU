

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


abstract class MyShape
{
    Point corner1; // ��ɹ﨤�I�@
    Point corner2; // ��ɹ﨤�I�GPoint corner2,
    Color color; // �C��
    boolean filled; // ��ߧ_
    
    // �̾�corner1,corner2,color,filled�A���e�X�Ϊ�
    abstract public void draw(Graphics g);
}

// �w�q���u�Ϊ�����
class Line extends MyShape
{
    @Override
    public void draw(Graphics g)
    {  // �̾�corner1,corner2,color,filled�e���u
    	g.setColor(color);
    	g.drawLine(corner1.x, corner1.y, corner2.x, corner2.y);
    	
    }
}

// �w�q�x�ΧΪ�����
class Rectangle extends MyShape
{
    @Override
    public void draw(Graphics g)
    {  // �̾�corner1,corner2,color,filled�e�x��
    	g.setColor(color);
    			
    			
    	}

    }


// �w�q���Ϊ�����
class Circle extends MyShape
{
    @Override
    public void draw(Graphics g)
    {   // �̾�corner1,corner2,color,filled�e���
    	g.setColor(color);
    	
    }
}

// �w�q�e�O����
class DrawPanel extends JPanel 
{
    LinkedList<MyShape> shapeArray; // �Ϊ��}�C�A�s��e�O�d�s���Ҧ��Ϊ��ѭ��e����
    MyShape currentShape; // �s�Ϊ��A�b�ƹ����U�ɫإߡA��}�ɲ����A�ѵe�X�s�Ϊ���������Ե��G����
    int shapeType; // �Ϊ������A0,1,2���O�N�����u�B�x�ΡB���C�Ѥu��CcboShape�U�Բ��M�w�A�ѫإ߷s�Ϊ����Ѧ�
    Color currentColor; // �s�C��A�Ѥu��CcboColor�U�Բ��M�w�A�ѫإ߷s�Ϊ����Ѧ�
    boolean filledShape; // �e��ߧΪ��_�A�Ѥu��CchkFilled�Ŀﲰ�M�w�A�ѫإ߷s�Ϊ����Ѧ�
    JLabel statusLabel; // ���A�C,��ܥثe�ƹ��y��

    // �Y���Ϊ��i�e,�v�@�I�s�Ϊ��}�CshapeArray�C�ӧΪ���draw��k
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);  // �M�έI����

        // �e���Ϊ��}�CshapeArray�Ҧ��Ϊ��A�A�e�s�Ϊ�currentShape
        for(MyShape shape : shapeArray) {
        	shape.draw(g);     	
        }
        
        if(currentShape != null) {
        	currentShape.draw(g);
        }
        
        // �s�Ϊ��~�|�X�{�b�Ҧ��§Ϊ����W
        
    }
    
    public void setShapeType(int shape)
    {  // ��cboShape�ΡAshape=0,1,2���O�N���s�Ϊ������u�B�x�ΡB���
    	this.shapeType = shape;
    }
    
    public void setCurrentColor(Color color)
    {  // ��cboColor��
    	currentColor = color;
    }
    
    public void setFilledShape(boolean filled)
    {  // ��chkFilled��
    	this.filledShape = filled;
    	
    }
    
    // �����}�C�̫�@�ӧΪ�,�I�srepaint
    public void clearLastShape()
    {  // ��btnUndo��
    	shapeArray.removeLast();
    	repaint();
    }

    // �M���}�C�Ҧ��Ϊ�,�I�srepaint
    public void clearDrawing()
    {  // ��btnClear��
    	shapeArray.clear();
    	repaint();
    }

    // �غc�l
    public DrawPanel(JLabel lblStatus)
    {
        // �����@���Ҳ��ѼƧ@�����A�C,�غc�Ū��Ϊ��}�C,
    	statusLabel = lblStatus;
    	shapeArray = new LinkedList<MyShape>();
     	// �I����Color.WHITE,���U�A���ƹ��ƥ�B�z��,�]�w��Ȧp�U
    	setBackground(Color.WHITE);
     	// shapeType��0 (���ܷs�اΪ��N�����u),currentShape��null (���ܩ|�L�s�Ϊ�),
    	shapeType =0;
    	currentShape = null;
     	// currentColor��Color.BLACK
    	currentColor = Color.BLACK;

        // �H�U3����U�ƹ���ť����A�ǵe�O����L�h�A��K��ť������o�e�O���e
        MouseHandler mouseHandler = new MouseHandler(DrawPanel.this);
        DrawPanel.this.addMouseListener(mouseHandler);
        DrawPanel.this.addMouseMotionListener(mouseHandler);
    }
}

// �w�q�ƹ���ť����
class MouseHandler extends MouseAdapter implements MouseMotionListener
{
    DrawPanel drawPanel; // �O���e�O����A��K��ť������o�e�O���e


    public MouseHandler(DrawPanel drawPanel)
    {    
        MouseHandler.this.drawPanel = drawPanel;
    }

    @Override
    public void mousePressed(MouseEvent me) 
    {   // ��shapeType�ƭȡA�إ߹������s�Ϊ�����currentShape,
    	
   	// �s�Ϊ�currentShape��l���corner1��corner2��m�Ҭ��ƹ����U�y��
    	drawPanel.currentShape.corner1 = me.getPoint();
    	drawPanel.currentShape.corner2 = me.getPoint();
        // �s�Ϊ�currentShape�C����۵e�OcurrentColor�A��ߧ_���A���۵e�OfilledShape
    	drawPanel.currentShape.color = drawPanel.currentColor;
    	drawPanel.currentShape.filled = drawPanel.filledShape;
    }

    @Override
    public void mouseReleased(MouseEvent me) 
    {   // �]�w�s�Ϊ�currentShape���corner2��m���ƹ���}�y��,
    	drawPanel.currentShape.corner2=me.getPoint();
   	// �s�Ϊ�currentShape�C����۵e�OcurrentColor�A��ߧ_���A���۵e�OfilledShape
    	drawPanel.currentShape.color =drawPanel.currentColor;
    	drawPanel.currentShape.filled =drawPanel.filledShape;
        // �N�s�Ϊ�currentShape��J�Ϊ��}�CshapeArray�̫�@��,
    	drawPanel.shapeArray.addLast(drawPanel.currentShape);
   	// �]�w�s�Ϊ�currentShape��null,�I�srepaint���e�e�O�Ҧ��Ϊ�.
    	drawPanel.currentShape=null;
    	drawPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) 
    {   // �N�ƹ���e�O�����ʮy����ܩ�e�OstatusLabel���A�C�W
    	drawPanel.statusLabel.setText(String.format("�ƹ����ʦ�m�G(x=%d,y=%d),�Ϊ��}�C���סG%d", me.getX(),me.getY(),drawPanel.shapeArray.size()));
    }

    @Override
    public void mouseDragged(MouseEvent me) 
    {   // �]�w�s�Ϊ�currentShape���corner2��m���ƹ���ʮy��,
    	drawPanel.currentShape.corner2 = me.getPoint();
   	// �I�srepaint���e�e�O�Ҧ��Ϊ�,���Τ�ݥX�Ϊ��̷s��ʮĪG
    	drawPanel.repaint();
   	// �N�ƹ���e�O����ʮy����ܩ�e�OstatusLabel���A�C�W
    	drawPanel.statusLabel.setText(String.format("�ƹ���Ԧ�m�G(x=%d,y=%d),�Ϊ��}�C���סG%d", me.getX(),me.getY(),drawPanel.shapeArray.size()));
    }
}

// �w�q�e�Ϊ�����������
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
    
    
 
    String colorStringArray[] = {"��", "��", "��", "��"};
    Color colorArray[] = {Color.black, Color.red, Color.blue, Color.green};
    String shapeStringArray[] = {"���u", "�x��", "���"};

    public DrawShapes()
    {
        super("�Ϊ����n");
        
        
        lblStatus = new JLabel("���A�C");
        pnlCanvas = new DrawPanel(lblStatus);
        pnlCanvas.setBackground(Color.lightGray);

        btnUndo = new JButton("�_��");
        btnClear = new JButton("�M��");
        cboColor = new JComboBox<>(colorStringArray);
        cboShape = new JComboBox<>(shapeStringArray);
        chkFilled = new JCheckBox("���");
        pnlToolBar = new JPanel(); // �u��C�e��
        pnlToolBar.add(btnUndo);
        pnlToolBar.add(btnClear);
        pnlToolBar.add(cboColor);
        pnlToolBar.add(cboShape);
        pnlToolBar.add(chkFilled);

        this.add(pnlToolBar, BorderLayout.NORTH); // �u��C��_��
        this.add(pnlCanvas, BorderLayout.CENTER); // �e�O�񤤶�
        this.add(lblStatus, BorderLayout.SOUTH); // ���A�C��n��

        // �u��C[�_��]���s�A�R���̫�@�ӧΪ�
        btnUndo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {  // �I�s�e�OclearLastShape��k
            	pnlCanvas.clearLastShape();
            	
            }
        });

        // �u��C[�M��]���s�A�R���Ҧ��Ϊ�
        btnClear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {  // �I�s�e�OclearDrawing��k
            	pnlCanvas.clearDrawing();
            }
        });

        // �u��C[�C��]�U�Բ��A����C��
        cboColor.addItemListener(new ItemListener()
        {   // �̾�cboColor�U�Բ���ܡA�D���C��A�Q��setCurrentColor��k�A�ǰe��pnlCanvas
            public void itemStateChanged(ItemEvent ie)
            {
            	if (ie.getStateChange() == ItemEvent.SELECTED) {
            		pnlCanvas.setCurrentColor(colorArray[cboColor.getSelectedIndex()]); 
              } 
            }
        });

        // �u��C[�Ϊ�]�U�Բ��A��ܧΪ�
        cboShape.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent ie)
            {   // �NcboShape�U�Բ���ܪ��U�СA�Q��setShapeType��k�A�ǰe��pnlCanvas  
            	if (ie.getStateChange() == ItemEvent.SELECTED) {
            		pnlCanvas.setShapeType(cboShape.getSelectedIndex());
              } 
            }
        });

        // �u��C[���]�Ŀﲰ�A��ܹ�ߧ_
        chkFilled.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent ie)
            {  // �NchkFilled�Ŀﲰ���A�A�Q��setFilledShape��k�A�ǰe��pnlCanvas
            	pnlCanvas.setFilledShape(chkFilled.isSelected());
            	
            }
        });
    }
}

// �w�q�D�{�����O
public class G6
{
  public static void main(String args[])
  {
      DrawShapes jframe = new DrawShapes();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.setSize(600,400);
      jframe.setVisible(true);
  }
}
