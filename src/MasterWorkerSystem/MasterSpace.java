// the Interface that the master talks to
package MasterWorkerSystem;

import DataSpaceElements.Task;
import DataSpaceElements.Code;
import DataSpaceElements.Data;
import DataSpaceElements.Result;
public interface MasterSpace {
    public boolean writeTask(Task task);
    public boolean writeCode(Code code);
    public boolean writeData(Data data);
    public Result takeResult();
    public void deleteCode(Long codeID);
    public void deleteData(Long dataID);
}
