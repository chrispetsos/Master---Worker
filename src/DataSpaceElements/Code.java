// Code class
package DataSpaceElements;


public class Code extends SpaceData{
    public Data data;		// contained Data object
    public Result execute() {		// will be overriden by subtypes
        throw new RuntimeException(
            "Code.execute() is not implemented.");
    }
}
