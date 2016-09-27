package JavaSpaceImplementation.JavaSpaceSin;

import net.jini.core.entry.Entry;
import DataSpaceElements.Result;
import JavaSpaceImplementation.CodeEntry;

public class SinCode extends CodeEntry{
    public SinCode() {
    }

	DegToRad degtool = new DegToRad();

    public Result execute(){
        SinResult result = new SinResult();
		Double angleDouble = (((SinData)this.data).angle);
        double angledouble = angleDouble.doubleValue();
        result.angle = angleDouble;
        double rads = degtool.degtorad(angledouble);
		result.result = new Double(Math.sin(rads));
        return result;
    }
}
