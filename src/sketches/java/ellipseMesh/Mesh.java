package ellipseMesh;


import java.util.ArrayList;
import java.util.List;

import numericalLibrary.types.Vector3;
import processing.core.PApplet;
import processing.core.PConstants;



public class Mesh
{
    
    ////////////////////////////////////////////////////////////////
    // PRIVATE VARIABLES
    ////////////////////////////////////////////////////////////////
    
    List<Vector3> pointList;
    
    List<MeshTriangle> triangleList;
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC CONSTRUCTORS
    ////////////////////////////////////////////////////////////////
    
    public Mesh( List<Vector3> listOfPoints , List<MeshTriangle> listOfTriangles )
    {
        this.pointList = listOfPoints;
        this.triangleList = listOfTriangles;
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////////
    
    public List<MeshTriangle> getTriangleList()
    {
        return this.triangleList;
    }
    
    
    public List<Vector3> getPointList()
    {
        return this.pointList;
    }
    
    
    public void subdivide( int numberOfSubdivisions )
    {
        for( int n=0; n<numberOfSubdivisions; n++ ) {
            List<MeshTriangle> trianglesToAdd = new ArrayList<MeshTriangle>();
            List<MeshTriangle> trianglesToRemove = new ArrayList<MeshTriangle>();
            for( MeshTriangle triangle : this.triangleList ) {
                Vector3 a = triangle.a();
                Vector3 b = triangle.b();
                Vector3 c = triangle.c();
                Vector3 ab = a.add( b ).scaleInplace( 0.5 );
                Vector3 ac = a.add( c ).scaleInplace( 0.5 );
                Vector3 bc = b.add( c ).scaleInplace( 0.5 );
                //Mesh.projectToSurfaceInplace( ab );
                //Mesh.projectToSurfaceInplace( ac );
                //Mesh.projectToSurfaceInplace( bc );
                
                this.pointList.add( ab );
                this.pointList.add( ac );
                this.pointList.add( bc );
                trianglesToAdd.add( new MeshTriangle( a , ab , ac ) );
                trianglesToAdd.add( new MeshTriangle( b , ab , bc ) );
                trianglesToAdd.add( new MeshTriangle( c , ac , bc ) );
                trianglesToAdd.add( new MeshTriangle( ab , bc , ac ) );
                trianglesToRemove.add( triangle );
            }
            this.triangleList.removeAll( trianglesToRemove );
            this.triangleList.addAll( trianglesToAdd );
        }
    }
    
    
    public void plot( PApplet pd )
    {
        pd.stroke( pd.color( 0 ) );
        pd.strokeWeight( 5.0e-3f );
        pd.beginShape( PConstants.TRIANGLES );
        for( MeshTriangle triangle : this.triangleList ) {
            triangle.plot( pd );
        }
        pd.endShape();
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC STATIC METHODS
    ////////////////////////////////////////////////////////////////
    
    public static Vector3 projectToSurfaceInplace( Vector3 input )
    {
        Vector3 output = input.normalizeInplace();
        output.setX( output.x() * 3.0 );
        output.setY( output.y() * 2.0 );
        output.setZ( output.z() * 1.0 );
        return output;
    }
    
    
    public static Mesh tetrahedron()
    {
        // Create list of points.
        List<Vector3> listOfPoints = new ArrayList<Vector3>();
        listOfPoints.add( new Vector3( 1.0 , 1.0 , 1.0 ) );
        listOfPoints.add( new Vector3( -1.0 , -1.0 , 1.0 ) );
        listOfPoints.add( new Vector3( 1.0 , -1.0 , -1.0 ) );
        listOfPoints.add( new Vector3( -1.0 , 1.0 , -1.0 ) );
        
        // Create list of triangles.
        List<MeshTriangle> listOfTriangles = new ArrayList<MeshTriangle>();
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 0 ) , listOfPoints.get( 1 ) , listOfPoints.get( 2 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 0 ) , listOfPoints.get( 1 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 0 ) , listOfPoints.get( 2 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 1 ) , listOfPoints.get( 2 ) , listOfPoints.get( 3 ) ) );
        
        return new Mesh( listOfPoints , listOfTriangles );
    }
    
    
    public static Mesh icosahedron()
    {
        double phi = ( 1.0 + Math.sqrt( 5.0 ) ) * 0.5;  // golden ratio
        double a = 1.0;
        double b = 1.0 / phi;
        
        // Create list of points.
        List<Vector3> listOfPoints = new ArrayList<Vector3>();
        listOfPoints.add( new Vector3( 0 , b , -a ) );
        listOfPoints.add( new Vector3( b , a , 0 ) );
        listOfPoints.add( new Vector3( -b , a , 0 ) );
        listOfPoints.add( new Vector3( 0 , b , a ) );
        listOfPoints.add( new Vector3( 0 , -b , a ) );
        listOfPoints.add( new Vector3( -a , 0 , b ) );
        listOfPoints.add( new Vector3( 0 , -b , -a ) );
        listOfPoints.add( new Vector3( a , 0 , -b ) );
        listOfPoints.add( new Vector3( a , 0 , b ) );
        listOfPoints.add( new Vector3( -a , 0 , -b ) );
        listOfPoints.add( new Vector3( b , -a , 0 ) );
        listOfPoints.add( new Vector3( -b , -a , 0 ) );
        
        // Create list of triangles.
        List<MeshTriangle> listOfTriangles = new ArrayList<MeshTriangle>();
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 2 ) , listOfPoints.get( 1 ) , listOfPoints.get( 0 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 1 ) , listOfPoints.get( 2 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 5 ) , listOfPoints.get( 4 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 4 ) , listOfPoints.get( 8 ) , listOfPoints.get( 3 ) ) );
        
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 7 ) , listOfPoints.get( 6 ) , listOfPoints.get( 0 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 6 ) , listOfPoints.get( 9 ) , listOfPoints.get( 0 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 11 ) , listOfPoints.get( 10 ) , listOfPoints.get( 4 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 10 ) , listOfPoints.get( 11 ) , listOfPoints.get( 6 ) ) );
        
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 9 ) , listOfPoints.get( 5 ) , listOfPoints.get( 2 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 5 ) , listOfPoints.get( 9 ) , listOfPoints.get( 11 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 8 ) , listOfPoints.get( 7 ) , listOfPoints.get( 1 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 7 ) , listOfPoints.get( 8 ) , listOfPoints.get( 10 ) ) );
        
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 2 ) , listOfPoints.get( 5 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 8 ) , listOfPoints.get( 1 ) , listOfPoints.get( 3 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 9 ) , listOfPoints.get( 2 ) , listOfPoints.get( 0 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 1 ) , listOfPoints.get( 7 ) , listOfPoints.get( 0 ) ) );
        
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 11 ) , listOfPoints.get( 9 ) , listOfPoints.get( 6 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 7 ) , listOfPoints.get( 10 ) , listOfPoints.get( 6 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 5 ) , listOfPoints.get( 11 ) , listOfPoints.get( 4 ) ) );
        listOfTriangles.add( new MeshTriangle( listOfPoints.get( 10 ) , listOfPoints.get( 8 ) , listOfPoints.get( 4 ) ) );
        
        return new Mesh( listOfPoints , listOfTriangles );
    }
    
    
}
