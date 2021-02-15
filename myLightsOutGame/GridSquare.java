import java.awt.Color;
import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;

/*
 *  A GUI component
 *
 *  A simple extension of JComponent which records its
 *  coordinates in xcoord and ycoord, NOT in 'x' and 'y'.
 *  Why not? Because 'x' and 'y' are already attributes of
 *  the panel (super) class which say where to draw it in the window.
 *
 *  The game grid and allows the background colour to be set with ease.
 *  
 *
 *  @author mhatcher & Daniel Brown
 *  Septmber 24, 2020
 */
public class GridSquare extends JButton
{
    private int xcoord, ycoord;  // location in the game grid
    public Random rand;
    private String name;
    
    // constructor takes the x and y coordinates of this square
    public GridSquare(int xcoord, int ycoord)
    {
        super();
        this.setSize(50,50);
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        rand = new Random();
    }
    
    // If the randomizer is 1 the square is set to yellow, otherwise it is set to black
    public void setColor(int decider)
    {
        int n = rand.nextInt(2);
        if(n == 1)
        {
            Color colour = Color.yellow;
            this.setBackground( colour);
        }
        
        else
        {
            Color colour = Color.black;
            this.setBackground( colour);
        }
    }
    
    // Getter method for the background color of a square
    public Color getBackground(Color colour)
    {
        return colour;
    }
 
    
    // If the square is black it becomes yellow, and vice-versa
    public void switchColor()
    {
        Color colour = (getBackground() == Color.black) ? Color.yellow: Color.black;
        this.setBackground( colour);
    }
    
    // simple getters
    public int getXcoord()   { return xcoord; }
    public int getYcoord()   { return ycoord; }
    
    // Getters for the coordinates of squares surrounding the selected square
    public int getNxCoord()   { return xcoord-1; }
    
    public int getSxCoord()   { return xcoord+1; }
    
    public int getEyCoord()   { return ycoord+1; }
    
    public int getWyCoord()   { return ycoord-1; }
}