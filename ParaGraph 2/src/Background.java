import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Background {
	public static void drawBackground(Graphics2D g2, int width, int height, double scale, int [] ori)
	{
		g2.setStroke(new BasicStroke(2));
        
        g2.setColor(new Color(200, 200, 200));
        
        for(int i = ori[0]; i > 0; i-= scale) //draws vertical gridlines above origin
        {
            g2.drawLine(i, 0, i, height);
        }
        for(int i = ori[0]; i < width; i+= scale) //draws vertical gridlines below origin
        {
            g2.drawLine(i, 0, i, height);
        }
         
        for(int i = ori[1]; i > 0; i-= scale) //draws horizontal gridlines above origin
        {
            g2.drawLine(0, i, width, i);
        }
        for(int i = ori[1]; i < height; i+= scale) //draws horizontal gridlines below origin
        {
            g2.drawLine(0, i, width, i);
        }
        
        g2.setColor(Color.BLACK);
        g2.drawLine(ori[0], 0, ori[0], height); //y axis
        g2.drawLine(0, ori[1], width, ori[1]); //x axis
        
        g2.setStroke(new BasicStroke(2));
        
        g2.setColor(Color.BLACK);
        g2.drawString("Enter the a,b,c,d values for a polynomial, in that form", width-375, 50);
        
      //input info box
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(width-400, 0, width, 200, 25, 25);
        
        g2.setColor(Color.BLACK);
        g2.drawString("Enter the a,b,c,d values for a polynomial, in that form", width-375, 50);
        
        g2.setColor(Color.RED);
        g2.drawString("f(t) in red", width-375, 75);
        
        g2.setColor(Color.BLUE);
        g2.drawString("f'(t) in blue", width-375, 100);
        
        g2.setColor(Color.GREEN);
        g2.drawString("Antiderivative in green", width-375, 125);
	}
}
