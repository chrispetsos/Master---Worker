package JavaSpaceImplementation.JavaSpaceMult;

import DataSpaceElements.Result;
import JavaSpaceImplementation.CodeEntry;

public class MultCode extends CodeEntry{
    public MultCode() {
    }

    public Result execute() {
        Integer answer = new Integer(((MultData)data).a.intValue() * (((MultData)data)).b.intValue());
        MultResult result = new MultResult(((MultData)data).a,((MultData)data).b, answer);
        return result;
    }
}
