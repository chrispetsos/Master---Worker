package MasterWorkerSystem;
import DataSpaceElements.Result;
import java.util.Date;

public class ResultCollector extends Thread{
    Master master;

    public ResultCollector(Master master) {
        this.master = master;
    }

    // start collecting results
    public void run() {
		Result result;
        int numOfResults=0;
		while (master.numOfTasks!=numOfResults || master.numOfTasks == 0){
			result = null;
			while (result == null) {
				result = master.space.takeResult();
			}
            master.results.addElement(result);
            numOfResults++;
		}
		master.t2 = new Date();
        master.secsPassed = (master.t2.getTime() - master.t1.getTime())/1000.0;
    }
}
