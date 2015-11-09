/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A point of view for the 3D environment
 * @author Dmitry
 */
public class Camera {
    
    // The x coordinate of the camera -- affects the horizontal scope of the view
    private double originX;
    // The y coordinate of the camera -- affects the vertical scope of the view
    private double originY;
    // The z coordinate of the camera -- affects the size of objects on the screen
    private double originZ;
    
    private double x, y, z;
    // For each rotation, counter clockwise is a positive value, and clockwise is a negative value
    // The rotation of the camera on the x axis
    private double xyRot;
    // The rotation of the camera on the y axis
    private double yzRot;
    // The rotation of the camera on the z axis
    private double zxRot;
    
    private final double radius = 100;

    public Camera(double originX, double originY, double originZ, double xyRot, double yzRot, double zxRot)
    {

        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;
        this.xyRot = xyRot;
        this.yzRot = yzRot;
        this.zxRot = zxRot;
        x = radius*Math.abs(Math.cos(xyRot))*Math.sin(zxRot);
        y = radius*Math.abs(Math.cos(yzRot))*Math.sin(xyRot);
        z = radius*Math.abs(Math.cos(zxRot))*Math.sin(yzRot);

    }
    
    
    public double getRadius()
    {
        return radius;
    }
    
    // ADDS ROTATION
    /**
     * Adds rotation to the existing x rotation.
     * @param rotation the extra rotation in radians.
     */
    
    public void rotateX(double rotation)
    {
        yzRot += rotation;
    }
    
    public void rotateY(double rotation) 
    {
        zxRot += rotation;
    }
    public void rotateZ(double rotation)
    {
        xyRot += rotation;
    }

    
    public GridPoint toPoint()
    {
        // get the zx plane radius, then get the x component, Math.abs() for the projection cause all i'm doing is creating a new radius on only one plane, and all radiuses are pos
        double xPoint = radius*Math.abs(Math.cos(xyRot))*Math.sin(zxRot);
        double yPoint = radius*Math.abs(Math.cos(yzRot))*Math.sin(xyRot);
        double zPoint = radius*Math.abs(Math.cos(zxRot))*Math.sin(yzRot);
        
        return new GridPoint(xPoint, yPoint, zPoint);
    }
}
