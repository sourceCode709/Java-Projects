import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;

/*
 *  The main window of the game Lights out.
 *  @author Daniel Brown
 *  September 24, 2020
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
    // gui components that are contained in this frame:
    private JPanel topPanel, bottomPanel;   // top and bottom panels in the main window
    private JLabel instructionLabel;        // a text label to tell the user what to do
    //public JLabel infoLabel;            // a text label to show the coordinate of the selected square
    private JButton topButton;              // a 'reset' button to appear in the top panel
    private GridSquare [][] gridSquares;    // squares to appear in grid formation in the bottom panel
    private int rows,columns;               // the size of the grid
    public String name;
    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it makes sure that action listeners are added to selectable items
     *  it makes sure that the gui will be visible
     */
    public WindowDemo(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.setSize(600,600);
        this.name = name;
        
        // first create the panels
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(rows, columns));
        bottomPanel.setSize(500,500);
        
        // then create the components for each panel and add them to it
        
        // for the top panel:
        instructionLabel = new JLabel("Click on the lights to put them out!");
        //infoLabel = new JLabel("No square clicked yet.");
        topButton = new JButton("New Game");
        topButton.addActionListener(this);          // IMPORTANT! Without this, clicking the button does nothing.
        
        topPanel.add(instructionLabel);
        topPanel.add (topButton);
        //topPanel.add(infoLabel);
        
    
        // for the bottom panel:    
        // create the buttons and add them to the grid
        gridSquares = new GridSquare[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                gridSquares[x][y] = new GridSquare(x, y);
                gridSquares[x][y].setSize(20, 20);
                gridSquares[x][y].setColor(x + y);
                
                gridSquares[x][y].addMouseListener(this);       // AGAIN, don't forget this line!
                
                bottomPanel.add(gridSquares[x][y]);
            }
        }
        
        // now add the top and bottom panels to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);     // needs to be center or will draw too small
        
        // housekeeping : behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    
    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed(ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();
                
        // if resetting the squares' colours is requested then do so
        if ( selected.equals(topButton) )
        {
            for ( int x = 0; x < columns; x ++)
            {
                for ( int y = 0; y < rows; y ++)
                {
                    gridSquares [x][y].setColor(x + y);
                }
            }
        }
    }
    
    public String getName()
    {
        return this.name;
    }
    
    // Mouse Listener events
    public void mouseClicked(MouseEvent mevt)
    {
        // get the object that was selected in the gui
        Object selected = mevt.getSource();
        
        /*
         * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
         * with just one piece of code.
         * In a real system you'll probably have one piece of action code per selectable item.
         * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
         */
        
        // if a gridsquare is selected then switch its color
        if (selected instanceof GridSquare)
        {
            GridSquare square = (GridSquare) selected;
            square.switchColor();
            
            int x = square.getXcoord();
            int y = square.getYcoord();
            
            // If the square that was slected has a square to its north, then it will switch its color
            try
            {
                int north = square.getNxCoord();
                gridSquares [north][y].switchColor();
            }
            catch(ArrayIndexOutOfBoundsException i){}
            
            // If the square that was slected has a square to its south, then it will switch its color
            try
            {
                int south = square.getSxCoord();
                gridSquares [south][y].switchColor();
            }
            catch(ArrayIndexOutOfBoundsException i){}
            
            // If the square that was slected has a square to its east, then it will switch its color
            try
            {
                int east = square.getEyCoord();
                gridSquares [x][east].switchColor();
            }
            catch(ArrayIndexOutOfBoundsException i){}
            
            // If the square that was slected has a square to its west, then it will switch its color
            try
            {
                int west = square.getWyCoord();
                gridSquares [x][west].switchColor();
            }
            catch(ArrayIndexOutOfBoundsException i){}
            
            // Checks to see if the user has won after every move
            gameWon();        }
    }
    
    //Method to determine if all the lights are out, meaning the user has won
    public void gameWon()
    {
        int counter = 0;
        for(GridSquare x[]: gridSquares)
        {
            for(GridSquare y: x)
            {
                if(y.getBackground() == Color.black)
                {
                    counter++;
                }
            }
        }
        
        //Displays message indicating that the user has won, if all of the lights are out
        if(counter == 25)
        {
            JOptionPane.showMessageDialog(this, "You Win!!!");
        }
    }
    
    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}
