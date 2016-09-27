package JavaSpaceImplementation.JavaSpaceRaytrace;
import JavaSpaceImplementation.DataEntry;
import java.awt.Point;

public class RaytraceData extends DataEntry{
    public Point UL;	// limits of image segment
    public Point LR;

    public RaytraceData() {
        
    }

    public RaytraceData(Point UL, Point LR) {
        this.UL = UL;
        this.LR = LR;
    }
}
