/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A point in the 3D environment.
 * @author Dmitry
 */
public class GridPoint {
    
    // coordinates
    private double x, y, z;
    // The inital x, y and z angles (Degrees)
    private double xyTheta, yzTheta, zxTheta;
    
    /**
     * Creates a GridPoint object.
     * @param x the initial x coordinate.
     * @param y the initial y coordinate.
     */
    public GridPoint(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        // Calculate the angles
        calculate();
        
    }
    
    private void calculate()
    {
        calculateAngles();
    }
    
    public void setZ(double z)
    {
        this.z = z;
        calculate();
    }
    public void setX(double x)
    {
        this.x = x;
        calculate();
    }
    public void setY(double y)
    {
        this.y = y;
        calculate();
    }
    
    private void calculateAngles()
    {
        xyTheta = Math.toDegrees(Math.atan2(y, x));
        yzTheta = Math.toDegrees(Math.atan2(z, y));
        zxTheta = Math.toDegrees(Math.atan2(x, z));
    }
    
    /**
     * Gets the x coordinate.
     * @return the x coordinate.
     */
    public double getX()
    {
        return x;
    }
    /**
     * Gets the y coordinate.
     * @return the y coordinate.
     */
    public double getY()
    {
        return y;
    }
    /**
     * Gets the z coordinate.
     * @return the z coordinate.
     */
    public double getZ()
    {
        return z;
    }

    public double getxyTheta() {
        return xyTheta;
    }

    public double getyzTheta() {
        return yzTheta;
    }
    
    public double getzxTheta()
    {
        return zxTheta;
    }

    @Override
    public String toString()
    {
        return "(" + x + "," + y + "," + z + ")" + "    " + "xy " + xyTheta + " yz " + yzTheta + " zx " + zxTheta;
    }
}
