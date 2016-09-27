package JavaSpaceImplementation.JavaSpaceRaytrace;
import DataSpaceElements.Result;
import JavaSpaceImplementation.CodeEntry;
import org.jraytrace.engine.RayTracer;
import java.awt.Point;
import org.jraytrace.util.Color;


public class RaytraceCode extends CodeEntry{
	public RayTracer tracer;	// the RayTracer object from JRaytrace

    public RaytraceCode() {
    }

    public Result execute(){
        // get the segment limits
        RaytraceData theData = (RaytraceData)this.data;
        Point UL = theData.UL;
        Point LR = theData.LR;
        RaytraceResult theResult = new RaytraceResult(UL,LR);
		int k = -1;
        // for this segment
		for (int i=UL.x;i<LR.x;i++){
            for (int j=UL.y;j<LR.y;j++){
				k++;
                Point temp = new Point(i,j);
                // raytrace pixels
				Color ptColor = tracer.recursiveTrace(tracer.getCamera().getRayForPoint(temp),10);
                theResult.pixels[k] = ptColor;
        	}
        }
        return theResult;
    }
}
