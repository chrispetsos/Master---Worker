// Generic client class for feeding the master with tasks,codes and data
package MasterWorkerSystem;

import java.util.Vector;

public class DataSpaceClient {
    protected static Vector tasks = new Vector();
    protected static Vector codes = new Vector();
    protected static Vector data = new Vector();
    protected static Master master = new Master(tasks,codes,data);
}
