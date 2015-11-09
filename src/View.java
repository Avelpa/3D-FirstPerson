
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * This is basically the GameBoard class we made for the Dalek game
 *
 * @author Dmitry
 */
public class View extends JComponent implements MouseListener, MouseMotionListener, KeyListener {

    private final int SCREEN_WIDTH = 640, SCREEN_HEIGHT = 480;
    
    // Location of mouse click in screen coordinates
    private Point clickPoint = null;
    // All of the points
    private ArrayList<GridPoint> points = null;
    // The camera (point of view)
    private Camera camera;
    
    private Robot robot;
    
    private Cursor invisibleCursor;
    
    private boolean isCursorInvisible = false;
    private int oldMouseX, oldMouseY;
    private int newMouseX, newMouseY;
    private int mouseX, mouseY;

    private JFrame window;
    
    public View() throws AWTException {
        
        // create a new camera at the origin, with 0 rotation for all angles <<< the coordinates are in the Cartesian system
        camera = new Camera(0, 0, 0, Math.toRadians(0), Math.toRadians(0), Math.toRadians(90));
        //camera.rotateZ(90);
        
        robot = new Robot();
        invisibleCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            Toolkit.getDefaultToolkit().getImage(""),
            new Point(0, 0),
            "invisible");
        
        // create the frame to display the board
        window = new JFrame("3D THING");
        // add the board to the frame
        window.add(this);
        // make the frame visible
        window.setVisible(true);
        //set the size of our board
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // resize the window
        window.pack();
        //set the X
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add mouselistener
        this.addMouseListener(this);
        window.addKeyListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * Converts a pair of coordinates from screen format to Cartesian format.
     * @param x the X coordinate to be converted.
     * @param y the Y coordinate to be converted.
     * @param z the Y coordinate to be converted.
     * @return a Point object containing the Cartesian system coordinates.
     */
    public GridPoint convertToCartesianCoordinates(double x, double y, double z) {
        // Screen coordinates start at top left, while Cartesian coordinates start at middle
        // The x value is offset since Cartesian system halves the screen width
        double gridX = x - SCREEN_WIDTH / 2d;
        // The y value is offset since Cartesian system halves the screen height AND is reversed since Cartesian y points up while Screen y points down
        double gridY = -(y - SCREEN_HEIGHT / 2d);
        
        return new GridPoint(gridX, gridY, z);
    }

    /**
     * Converts a pair of coordinates from screen format to Cartesian format.
     * @param x the X coordinate to be converted.
     * @param y the Y coordinate to be converted.
     * @param z the Z coordinate to be converted.
     * @return a Point object containing the Screen system coordinates.
     */
    public GridPoint convertToScreenCoordinates(double x, double y, double z) {
        // Screen coordinates start at top left, while Cartesian coordinates start at middle
        // The x value is offset since Cartesian system halves the screen width
        double screenX = x + SCREEN_WIDTH/2d;
        // The y value is offset since Cartesian system halves the screen height AND is reversed since Cartesian y points up while Screen y points down
        double screenY = -(y - SCREEN_HEIGHT / 2d);
        
        return new GridPoint(screenX, screenY, z);
    }

//    /**
//     * Returns the click coordinates in Cartesian form.
//     * @return the click point object in Cartesian form.
//     */
//    public GridPoint getClickPoint() {
//        // don't want to convert null coordinates
//        if (clickPoint != null) {
//            
//            GridPoint newPoint = convertToCartesianCoordinates(clickPoint.getX(), clickPoint.getY(), 0);
//            newPoint = transformPointToCamera(newPoint);
//            return newPoint;
//        }
//        return null;
//    }

    /**
     * Clears the click point.
     */
    public void clearClickPoint() {
        clickPoint = null;
    }

    /**
     * Draws all of the points.
     * @param points all of the points to be drawn.
     */
    public void drawPoints(ArrayList<GridPoint> points) {
        // Updates the points
        this.points = points;
        
        repaint();
    }

    /**
     * Paints the screen.
     * @param g the Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        
        // Draws the Cartesian grid (2D for now)
//        g.drawLine((int)(SCREEN_WIDTH/2d - camera.getX()), 0, (int)(SCREEN_WIDTH/2d - (int)camera.getX()), SCREEN_HEIGHT);
//        // lol the cartesian grid is reversed since i'm drawing it directly to screen form (that's why it's + camera)
//        g.drawLine(0, (int)(SCREEN_HEIGHT/2d + camera.getY()), SCREEN_WIDTH, (int)(SCREEN_HEIGHT/2d + camera.getY()));
        if (isCursorInvisible)
        {
            // draw crosshair
            g.fillRect(SCREEN_WIDTH/2-7, SCREEN_HEIGHT/2-1, 15, 3);
            g.fillRect(SCREEN_WIDTH/2-1, SCREEN_HEIGHT/2-7, 3, 15);
        }
        // Draws all of the points
//        if (points != null) {
//            
//            for (GridPoint point : points) {
//                // Applies camera transformations to each point
//                GridPoint drawnPoint = transformPointToGrid(point);
//                // Converts the points to screen form
//                drawnPoint = convertToScreenCoordinates(drawnPoint.getX(), drawnPoint.getY(), drawnPoint.getZ());
//                // each point is an oval
//                g.fillOval((int)drawnPoint.getX(), (int)drawnPoint.getY(), 10, 10);
//            }
//            
////            for (int i = 0; i < points.size(); i ++)
////            {
////                // Applies camera transformations to each point
////                GridPoint drawnPoint = transformPointToGrid(points.get(i));
////                // Converts the points to screen form
////                drawnPoint = convertToScreenCoordinates(drawnPoint.getX(), drawnPoint.getY(), drawnPoint.getZ());
////                // each point is an oval
////                //g.fillOval((int)drawnPoint.getX(), (int)drawnPoint.getY(), 10, 10);
////                
////                if (i >= 1)
////                {
////                    GridPoint prevPoint = transformPointToGrid(points.get(i-1));
////                    prevPoint = convertToScreenCoordinates(prevPoint.getX(), prevPoint.getY(), prevPoint.getZ());
////                    g.drawLine((int)drawnPoint.getX(), (int)drawnPoint.getY(), (int)prevPoint.getX(), (int)prevPoint.getY());
////                }
////            }
//        }
    }
    
    public void drawCursor()
    {
        if (isCursorInvisible)
        {
            centerMouse();
            if (window.getCursor() == Cursor.getDefaultCursor())
            {
                window.setCursor(invisibleCursor);
            }
        }
        else
        {
            if (window.getCursor() == invisibleCursor)
            {
                window.setCursor(Cursor.getDefaultCursor());
            }
        }
        
        repaint();
        
    }
    
    public void centerMouse()
    {
        if (robot != null)
        {
            robot.mouseMove((int)this.getLocationOnScreen().getX() + SCREEN_WIDTH/2, (int)this.getLocationOnScreen().getY() + SCREEN_HEIGHT/2);
        }
    }
    
    public void analyzeMouseMovement()
    {
        GridPoint mousePoint = convertToCartesianCoordinates(mouseX, mouseY, 0);
        if (isCursorInvisible)
        {
//            double thetaY = -(mousePoint.getX())*unitAngle;
//            camera.rotateY(thetaY);
//            
//            double thetaX = mousePoint.getY()*unitAngle;
//            camera.rotateX(thetaX);
            
        }
    }

    public double getMagnitude(Double... components)
    {
        double magnitude = 0;
        for (Double d: components)
        {
            magnitude += d*d;
        }
        return Math.sqrt(magnitude);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Reads a mouse event
     * @param e the mouse event object
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE)
        {
            isCursorInvisible = (isCursorInvisible == true ? false: true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
