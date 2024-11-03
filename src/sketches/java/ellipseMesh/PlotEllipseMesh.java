package ellipseMesh;

import java.util.ArrayList;
import java.util.List;

import numericalLibrary.types.Vector3;
import processing.core.PApplet;
import processing.event.MouseEvent;

/**
 * Example on how to process and plot images and point clouds of multiple MaixSense-A010 at the same time.
 * <p>
 * In this example we can observe the effect of multi-machine interference.
 * <p>
 * Controls:
 * <ul>
 *  <li> Mouse position: camera viewpoint.
 *  <li> Mouse wheel: zoom.
 * </ul>
 */
public class PlotEllipseMesh
    extends PApplet
{
    ////////////////////////////////////////////////////////////////
    // VARIABLES
    ////////////////////////////////////////////////////////////////
    
    Mesh mesh;
    
    /**
     * Zoom set with the mouse wheel.
     */
    int zoom;
    
    
    
    ////////////////////////////////////////////////////////////////
    // MAIN: ENTRY POINT
    ////////////////////////////////////////////////////////////////

    /**
     * Entry point.
     * <p>
     * The argument passed to "main" must match the class name.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        PApplet.main("ellipseMesh.PlotEllipseMesh");
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////////
    
    /**
     * Initial settings.
     * <p>
     * Needed in addition to "setup" to set the size of the window.
     */
    public void settings()
    {
        System.setProperty( "jogl.disable.opengles" , "false" );
        System.setProperty( "jogamp.gluegen.UseTempJarCache" , "false" );
        size( 1000 , 1000 , P3D );
        smooth(8);
    }
    
    
    /**
     * Identical to "setup" in Processing IDE except for "size" that must be set in "settings".
     */
    public void setup()
    {
        this.generateMesh();
        // Initialize zoom variable.
        this.zoom = ( 1 << 8 );
    }
    
    
    /**
     * Identical to "draw" in Processing IDE.
     */
    public void draw()
    {
        // Background initialization.
        background(0);
        
        // Here start the scope of 3d transformations on the scene.
        pushMatrix();
        // Set center to origin.
        translate( width/2 , height/2 , 0 );
        // Make reference frame right-handed.
        scale( 1 , -1 , 1 );
        // Scale using the zoom variable (controlled with mouse wheel).
        scale( this.zoom );
        // Rotate scene using mouse position.
        rotateX( (float)Math.PI );
        double theta = -( mouseY - height/2 ) * Math.PI / height;
        rotateX( (float)theta );
        double phi = ( mouseX - width/2 ) * Math.PI / width;
        rotateY( (float)phi );
        // Draw reference frame lines.
        strokeWeight( 1.0e-4f );
        stroke( color(255,0,0) );
        line( -100 , 0 , 0 , 100 , 0 , 0 );
        stroke( color(0,255,0) );
        line( 0 , -100 , 0 , 0 , 100 , 0 );
        stroke( color(0,0,255) );
        line( 0 , 0 , -100 , 0 , 0 , 100 );
        
        // Draw triangles
        mesh.plot( this );
        
        popMatrix();
    }
    
    
    /**
     * Behavior when a mouse wheel event is captured.
     */
    public void mouseWheel( MouseEvent event )
    {
        float e = event.getCount();
        if( e > 0 ) {
            this.zoom *= 2;
        } else {
            this.zoom /= 2;
        }
        if( this.zoom < 1 ) {
            this.zoom = 1;
        }
    }
    
    
    private void generateMesh()
    {
        mesh = Mesh.icosahedron();
        
        mesh.subdivide( 3 );
        for( Vector3 point : mesh.getPointList() ) {
            Mesh.projectToSurfaceInplace( point );
        }
        
    }
    
    
}
