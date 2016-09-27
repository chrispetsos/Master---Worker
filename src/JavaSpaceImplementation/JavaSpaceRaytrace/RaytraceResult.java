package JavaSpaceImplementation.JavaSpaceRaytrace;
import JavaSpaceImplementation.ResultEntry;
import java.awt.Point;
import org.jraytrace.util.Color;

public class RaytraceResult extends ResultEntry{
    public Point UL;		// image segment
    public Point LR;
    public Color[] pixels;	// pixel colors

    public RaytraceResult() {
    }

    public RaytraceResult(Point UL, Point LR) {
        this.UL = UL;
        this.LR = LR;
        pixels = new Color[(LR.x-UL.x)*(LR.y-UL.y)];
    }
}
