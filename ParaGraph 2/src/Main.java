import javax.swing.*;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
 
public class Main extends JFrame
{
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = (int)screenSize.getWidth();
    int height = (int)screenSize.getHeight();
    double scale = 50; //how many pixels are 1 unit
    int [] ori = new int[]{width/2, height/2}; //origin, center of screen
    private JTextField funcInput;
   
    private String input;
    
    public Main()
   {
       super("Silly Graphing Calculator");
       
       setSize(width, height);
       
       input = "";
        funcInput = new JTextField(20);
        add(funcInput, BorderLayout.SOUTH);
        textListener handler = new textListener();
        funcInput.addActionListener(handler);
   }
 
   void render(Graphics g)
   {
       Graphics2D g2 = (Graphics2D) g;
       
       double dt = 0.25; //tstep
            
     //background drawing: gridlines
       Background.drawBackground(g2, width, height, scale, ori);
     
       String parent = Functions.read(g2, width, height, scale, ori, input);

       if(parent.equals("polynomial"))
       {
    	   Functions.graphPolynomial(g2, width, height, scale, ori, dt);
       }
       if(parent.equals("sinusoid"))
       {
    	   Functions.graphSinusoid(g2, width, height, scale, ori, dt);
       }
   }
 
   public void paint (Graphics g)
   {
       super.paint(g);
       render(g);
    
   }
 
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Runnable ()
       {
           public void run()
           {
               new Main().setVisible(true);
           }
       });
   }
   private class textListener implements ActionListener
   {
        public void actionPerformed(ActionEvent event)
        {
            if(event.getSource()==funcInput)
            {
                input = event.getActionCommand();
                //System.out.println(input);
                repaint();
            }
        }
    }
}



