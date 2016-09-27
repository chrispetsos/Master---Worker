// The Master class
package MasterWorkerSystem;

import java.util.Vector;
import java.util.Date;
import DataSpaceElements.*;

public class Master {
    public MasterSpace space;		// master's interface
    private TaskGenerator taskGenerator;		// helping objects
    private ResultCollector resultCollector;
    private SpaceInitialiser spaceInitialiser;
    private long countID = 0;					// count of ID for data elements
	public Vector tasks = new Vector();			// the tasks
	public Vector codes = new Vector();			//the codes
	public Vector data = new Vector();			//the data
    public Vector results = new Vector();		// the results
	public static Date t1;						// start of computation
	public static Date t2;						// end of computation
    public static double secsPassed;			// duration of computation
	public int numOfTasks = 0;					// number of subtasks

// constructor
    public Master(Vector tasks, Vector codes, Vector data) {
        this.tasks = tasks;
        this.codes = codes;
        this.data = data;
		taskGenerator = new TaskGenerator(this);
        resultCollector = new ResultCollector(this);
        spaceInitialiser = new SpaceInitialiser(this);
    }

// the space that the master "talks" to
	public void assignSpace(MasterSpace space){
        this.space = space;
    }

// computation procedure
    public void startComputing() {
        Date t1 = new Date();
        generateTasksDataCodes();
        collectResults();
        Date t2 = new Date();
        int secsPassed = t2.getSeconds() - t1.getSeconds();
        System.out.println(secsPassed);
    }

// generate tasks codes and data
    public void generateTasksDataCodes(){
		this.t1 = new Date();
        numOfTasks = tasks.size();
        taskGenerator.start();
    }

// start collecting results
	public void collectResults() {
        resultCollector.start();
	}

// Initialise the DataSpace
    public void deleteCodesData(){
        spaceInitialiser.init();
    }

// simple ID assignment mechanism
    public Long giveUniqueID() {
        return new Long(countID++);
    }
}
