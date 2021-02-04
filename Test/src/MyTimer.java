import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;

public class MyTimer {
	private static final int FRAME_WIDTH = 100;
	private static final int FRAME_HEIGHT = 60;
	private static int timeCount=0;
	private static Timer timer;
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		JButton button = new JButton("Start Timer");
		frame.add(button);
	    button.setBackground(Color.RED);
		
		class TimeListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				
	            timeCount=timeCount+5;
				System.out.println(timeCount);
			}	
		}
        class ClickListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event) {
				System.out.println("Timer started");
				timer.start();
			}
		}
		
		ActionListener listener1 = new TimeListener();
		timer=new Timer(5000, listener1);
		
		ActionListener listener2 = new ClickListener();
		button.addActionListener(listener2);
		
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	
	}

}
