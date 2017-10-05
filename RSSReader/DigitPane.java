package project;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 * @author Rasha Mahdi
 * the class extends a JPanel for digital clock feature
 */
public class DigitPane extends JPanel {

		private static final long serialVersionUID = 1L;
		private int value;

      
       public Dimension getPreferredSize() {
           FontMetrics fm = getFontMetrics(getFont());
           return new Dimension(fm.stringWidth("00"), fm.getHeight());
       }

       public void setValue(int aValue) {
           if (value != aValue) {
               int old = value;
               value = aValue;
               firePropertyChange("value", old, value);
               repaint();
           }
       }

       public int getValue() {
           return value;
       }

       protected String pad(int value) {
           StringBuilder sb = new StringBuilder(String.valueOf(value));
           while (sb.length() < 2) {
               sb.insert(0, "0");
           }
           return sb.toString();
       }

       @Override
       protected void paintComponent(Graphics g) {
           super.paintComponent(g); 
           String text = pad(getValue());
           FontMetrics fm = getFontMetrics(g.getFont());
           int x = (getWidth() - fm.stringWidth(text)) / 2;
           int y = ((getHeight()- fm.getHeight()) / 2) + fm.getAscent();
           g.drawString(text, x, y);
       }        
   }    