package ellipseMesh;


import numericalLibrary.types.Vector3;
import processing.core.PApplet;



public class MeshTriangle
{
    
    ////////////////////////////////////////////////////////////////
    // PRIVATE VARIABLES
    ////////////////////////////////////////////////////////////////
    private Vector3 a;
    private Vector3 b;
    private Vector3 c;
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC CONSTRUCTORS
    ////////////////////////////////////////////////////////////////
    
    public MeshTriangle( Vector3 first , Vector3 second , Vector3 third )
    {
        this.a = first;
        this.b = second;
        this.c = third;
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////////
    
    public Vector3 a()
    {
        return this.a;
    }
    
    
    public Vector3 b()
    {
        return this.b;
    }
    
    
    public Vector3 c()
    {
        return this.c;
    }
    
    
    
    ////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ////////////////////////////////////////////////////////////////
    
    public void plot( PApplet pd )
    {
        pd.vertex( (float)this.a.x() , (float)this.a.y() , (float)this.a.z() );
        pd.vertex( (float)this.b.x() , (float)this.b.y() , (float)this.b.z() );
        pd.vertex( (float)this.c.x() , (float)this.c.y() , (float)this.c.z() );
    }
    
}
