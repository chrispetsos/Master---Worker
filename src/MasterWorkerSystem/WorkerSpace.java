// the interface that the Worker talks to
package MasterWorkerSystem;

import DataSpaceElements.Task;
import DataSpaceElements.Code;
import DataSpaceElements.Data;
import DataSpaceElements.Result;
public interface WorkerSpace {
    public Task takeTask();
    public Code readCode(Long codeID);
    public Data readData(Long dataID);
    public boolean writeResult(Result result);
}
