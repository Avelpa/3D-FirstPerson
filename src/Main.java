
import java.awt.AWTException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dmitry
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException {
        // Create view and engine objects
        View view = new View();
        Engine engine = new Engine(view);
        // GAME LOOP
        boolean running = true;
        while (running)
        {
//            // When the mouse is clicked
//            if (engine.mouseClicked())
//            {
//                // Add a point to the mouse click coordinates
//                engine.addPoint();
//            }
            view.analyzeMouseMovement();
            view.drawCursor();
            // Since this is a while loop, pause it so the mouselistener thread can run
            try
            {
                Thread.sleep(60);
            }catch(Exception e){}
        }
    }
    
}
