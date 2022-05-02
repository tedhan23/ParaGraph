import java.awt.*;
import java.util.*;

public class Functions {
	String parent = "";
	static double a=0.0, b=0.0, c=0.0, d = 0.0;
	static ArrayList<Double> tPoints = new ArrayList<Double>(){};
	
	public static String read(Graphics2D g2, int width, int height, double scale, int [] ori, String input) //VERY INFLEXIBLE. REWRITE
	{
		/*PSEUDOCODE FOR REWORKED READ SYSTEM:
		 * string array list "tags" that contains key characters/words. ie: +, ^, sin(), e, etc
		 * decompose input into tags. use conditionals starting from position 0 of input. painful,
		 * but idk how else to do it. use a for loop with temporary marker variables (RECURSION MAYBE? TO CHECK AHEAD OF PLACE). for example:
		 * say the input is "12x+3"
		 * The for loop begins at "1," which is not tag-worthy since it is a number and not an "independent" (operator or x) (may make it part of tag for a polynomial? ie x^3 is all tagged)
		 * I AM TAKING THE TYPE DETECTION FOR GRANTED. MAYBE TRY/CATCH to_int. IF IT WORKS, IT'S A # and APPEND. IF NOT, GO INTO MORE SPECIFICS
		 * then iterates to input[2], which is "x." store x as tag. place marker at x. 
		 * input[3] is a plus sign. Always store operators as their own tags.
		 * Finally, input[4] is 3. not stored as a tag because it is not preceded by ^
		 * 
		 * Tags needs to be parsed to define parent function
		 * 
		 */
		
		String parent = "polynomial";
       
        ArrayList<Integer> commas = new ArrayList<Integer>();//{};
        
         for(int i = 0; i < input.length(); i++)
         {
             if(input.substring(i,i+1).equals(","))
                 commas.add(i);
         }
         String aExcept = "";
         if(input.length() > 1 && !input.substring(0,1).equals("C"))
         {
             a = Double.parseDouble(input.substring(0,commas.get(0)));
             
             aExcept = input.substring(0,1);
         }
      
         b = Double.parseDouble(input.substring(commas.get(0)+1,commas.get(1)));
         c = Double.parseDouble(input.substring(commas.get(1)+1,commas.get(2)));
         d = Double.parseDouble(input.substring(commas.get(2)+1,input.length()));
     
         if(input.substring(0,1).equals("C")) //circle
         {
             g2.drawOval(ori[0]+(int)((scale*b)-(d/2*scale)),ori[1]-(int)((scale*c)+(d/2*scale)),(int)(d*scale),(int)(d*scale));
             //System.out.println("circle code");
             parent = "circle";
         }
         
         if(a == 0 && b == 0 && c == 0 && !parent.equals("circle")) //constant function
         {
             g2.setColor(Color.RED);
             g2.drawLine(0, ori[1]-(int)(d * scale), width, ori[1]-(int)(d * scale));
             g2.fillOval(width/2-5, ori[1]-(int)(d * scale)-5, 10, 10);
             parent = "constant";
         }
         return parent;
	}
	
	public static void graphPolynomial(Graphics2D g2, int width, int height, double scale, int [] ori, double dt)
	{
		int start = -1000;
		
		double yOld = (ori[1] - (((a/(scale * scale) * (start * start * start)) + ((b/scale) * (start * start))) + (c * start) + (d * scale)));
        
		for(double t = start; t < 1000; t+=dt)
		{
			g2.setColor(Color.RED);
            double scaledX = t/scale;
            double scaledY = Math.round(((a/(scale * scale) * (t * t * t)) + ((b/scale) * (t * t))) + (c * t) + (d * scale)); //calculates f(x) but just rounded down? i forgor
              
            double x = (ori[0] + t);
           
            double y = (ori[1] - (((a/(scale * scale) * (t * t * t)) + ((b/scale) * (t * t))) + (c * t) + (d * scale))); //calculates f(x) scaled to be rendered
          
            if(y == ori[1]*1.0) //roots
            {
            	g2.fillOval((int)(x)-5, ori[1]-5, 10, 10);
            	g2.setColor(Color.black);
            	g2.drawString("(" + scaledX + ", 0.0)", (int)(x)-5, ori[1]-5);
            }
            if(t == 0.0) //y-ints
            {
            	g2.fillOval(ori[0]-5, (int)(y)-5, 10, 10);
            	g2.setColor(Color.black);
            	g2.drawString("(0.0, " + d/1.0 + ")", (int)x-5, (int)(y)-5);
            }
           
            g2.drawLine((int)x, (int)yOld, (int)x, (int)y);
           
            yOld = y;
		}
		 
		//graphing derivative
		g2.setColor(Color.BLUE);
		
		yOld = (int)(ori[1] - (((a/(scale * scale) * (start * start * start)) + ((b/scale) * (start * start))) + (c * start) + (d * scale)));
        
        for(double t = start; t < 1000; t+=dt)
        {
        	int x = (int)(ori[0] + t);
        	double y = (ori[1] - (((3*a/(scale) * (t * t)) + ((2*b) * (t))) + (c * scale)));
         
        	if(y == (double)ori[1])
        	{
        		tPoints.add(t);
        	}
         
        	g2.drawLine(x, (int)yOld, x, (int)y);
         
        	yOld = y;
        }
      //turning points
        g2.setColor(Color.RED);
        for(int i = 0; i < tPoints.size(); i++)
        {
            double tPX = (ori[0]+tPoints.get(i));
            double tPY = (ori[1] - (((a/(scale * scale) * (tPoints.get(i) * tPoints.get(i) * tPoints.get(i)) + ((b/scale) * (tPoints.get(i) * tPoints.get(i)))) + (c * tPoints.get(i)) + (d * scale))))-5;

            g2.fillOval((int)tPX-5, (int)tPY, 10, 10);
            
            g2.setColor(Color.black);
            g2.drawString("(" + Math.round(tPoints.get(i)/scale)/1.0 + ", " + (Math.round(((a/(scale * scale) * (tPoints.get(i) * tPoints.get(i) * tPoints.get(i))) + ((b/scale) * (tPoints.get(i) * tPoints.get(i)))) + (c * tPoints.get(i)) + (d * scale)))/scale + ")", (int)(tPX)-5, (int)tPY);
       }
        
      //drawing antiderivative
        /*
        g2.setColor(Color.green);
        if(parent.equals("polynomial") || parent.equals("constant"))
           {
        int yOld = (int)(ori[1] - (((a/(4 * scale * scale * scale) * (start * start * start * start)) + (b/(3 * scale * scale) * (start * start * start)) + (c/(2 * scale) * (start * start)) + (d/(scale) * start))));
           
           for(double t = start; t < 1000; t+=dt)
         {
            int x = (int)(ori[0] + t);
            //System.out.println("length" + inputData.length);
            //System.out.println("b" + b);
            int y = (int)(ori[1] - ((a/(4 * scale * scale * scale) * (t * t * t * t)) + (b/(3 * scale * scale) * (t * t * t)) + (c/(2 * scale) * (t * t)) + (d * t)));
            
            g2.drawLine(x, yOld, x, y);
            
            yOld = y;
        }
        }
        */
	}
	
	public static void graphSinusoid(Graphics2D g2, int width, int height, double scale, int [] ori, double dt) //this is example code- doesn't take input
	{
		g2.setColor(Color.RED);
        
        for(double t = -1000; t < 1000; t+=dt)
        {
           int x = (int)(ori[0] + t);

           int y = ori[1] - (int)(scale * Math.sin((1/scale * t)));
           g2.drawLine(x, y, x, y+1);
       }
	}
	
	public static void graphSqrt(Graphics2D g2, int width, int height, double scale, int [] ori, double dt) //also example code
	{
		g2.setColor(Color.GREEN);
        
        for(double t = -1000; t < 1000; t+=dt)
        {
            int x = ori[0] + (int)(1.0/scale * (t * t));

            int y = (int)(ori[1] + t);
            g2.drawLine(x, y, x+1, y+2);
        }
	}
	
	public static void printInfo(Graphics2D g2, int width, int height, double scale, int [] ori)
	{
		 //end beh
        boolean even;
        if(a == 0.0)
        {
         even = true;
         } else
         {
             even = false;
         }
        
        g2.setColor(Color.BLACK);
        if(even && (b > 0))
        {
            g2.drawString("End behavior of f(t): Up/Up", width-375, 150);
        }
            
         if(!even && (a > 0))
         {
             g2.drawString("End behavior of f(t): Down/Up", width-375, 150);
         }
            
         if(even && (b < 0))
         {
            g2.drawString("End behavior of f(t): Down/Down", width-375, 150);
         }
            
         if(!even && (a < 0))
         {
            g2.drawString("End behavior of f(t): Up/Down", width-375, 150);
         }
	}
}