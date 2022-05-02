import java.awt.*;
public class Vectors {
	public static void drawVectors(Graphics2D g2, int width, int height, double scale, int [] ori) //this is example code- doesn't take input
	{
		int[] vect1 = new int []{2, 4};
        int[] vect2 = new int []{-3, 2};
        
        int tip1x = ori[0]+(int)(scale*vect1[0]);
        int tip1y = ori[1]-(int)(scale * vect1[1]);
        int tip2x = ori[0]+(int)(scale*vect2[0]);
        int tip2y = ori[1]-(int)(scale * vect2[1]);
        int sumx = ori[0]+(int)(scale*(vect1[0]+vect2[0]));
        int sumy = ori[1]-(int)(scale*(vect1[1]+vect2[1]));
        System.out.println("Sumx: " + sumx + "Sumy: " + sumy);
        
        g2.setColor(Color.RED);
        g2.drawLine(ori[0], ori[1], tip1x, tip1y);
        
        g2.setColor(Color.BLUE);
        g2.drawLine(ori[0], ori[1], tip2x, tip2y);
        
        //difference
        g2.setColor(Color.GREEN);
        g2.drawLine(tip1x, tip1y, tip2x, tip2y);
        
        //sum
        g2.setColor(new Color(255, 0, 255));
        g2.drawLine(ori[0], ori[1], sumx, sumy);
	}
}
