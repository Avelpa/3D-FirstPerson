
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The place where all the points are created and modified, and where the view is manipulated.
 * @author Dmitry
 */
public class Engine {
    
    // the engine has its own instance of the view class
    private View view;
    private GridPoint clickPoint;
    // all of the grid points
    private ArrayList<GridPoint> points = new ArrayList();
    
    /**
     * Creates an Engine object.
     * @param view the view the engine should be able to access.
     */
    public Engine(View view)
    {
        this.view = view;
    }
    
    /**
     * Checks whether or not the mouse has been clicked.
     * @return true if the mouse has been clicked; otherwise, return false.
     */
    public boolean mouseClicked()
    {
        // if the mouse coordinates are not valid (ie. null), then return false; otherwise, return true
        //clickPoint = view.getClickPoint();
        return clickPoint != null;
    }
    
    /**
     * Adds a GridPoint to the points list.
     */
    public void addPoint()
    {
        // Create a new GridPoint object using the click coordinates
        points.add(clickPoint); // GONNA HAVE TO COMPLETELY REVAMP THE ADDING SYSTEM (WITH CAMERA VIEW POINTS INTEGRATED...)
        // Clears the click point so that only one instance of the point is created (and the user has to click again to create another one)
        view.clearClickPoint();
        
        // Draws all the points each time a new point is created
        drawPoints();
    }
    
    /**
     * Draws all of the points.
     */
    private void drawPoints()
    {
        view.drawPoints(points);
    }
    
}
