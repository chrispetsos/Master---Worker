package TestBed;

import junit.framework.*;
//import DataSpace;
import JavaSpaceImplementation.Javaspace;
import MasterWorkerSystem.Master;
import MasterWorkerSystem.Worker;
import java.util.Vector;
import JavaSpaceImplementation.TransactionManagerAccessor;
import JavaSpaceImplementation.JavaSpaceMult.MultTask;
import JavaSpaceImplementation.JavaSpaceMult.MultCode;
import JavaSpaceImplementation.JavaSpaceMult.MultData;
import JavaSpaceImplementation.TaskEntry;
import JavaSpaceImplementation.JavaSpaceMult.MultResult;
import net.jini.core.entry.Entry;

public class TestJavaspace extends TestCase {
    public Javaspace space = new Javaspace();
    private Vector tasks = new Vector();
    private Vector codes = new Vector();
    private Vector data = new Vector();
    private Master master = new Master(tasks,codes,data);
    private Worker worker = new Worker();

    public static void main(String[] args) {
//        junit.swingui.TestRunner.run(TestJavaspace.class);
        junit.textui.TestRunner.run(TestJavaspace.class);
//        junit.awtui.TestRunner.run(TestJavaspace.class);
    }

    /** Constructs a test case with the given name. */
    public TestJavaspace(String name) {
        super(name);
    }

    /** Sets up the fixture, for example, open a network connection. This method is called before a test is executed. */
    protected void setUp() {
        // Write your code here
        worker.space = space;
        master.space = space;
    }

    /** Tears down the fixture, for example, close a network connection. This method is called after a test is executed. */
    protected void tearDown() {
        // Write your code here
        space = null;
        master = null;
        worker = null;
        tasks = null;
        codes = null;
        data = null;
    }

    public void testResolveTransactionManagerJavaSpace(){
        Assert.assertTrue(space!=null);
    }

    public void testWriteAndTakeTask(){
        TaskEntry task = new TaskEntry();
		task.codeID = new Long(1);
		task.dataID = new Long(3);
		space.write(task);
        TaskEntry template = new TaskEntry();
        template.codeID = new Long(1);
        TaskEntry returned = (TaskEntry)space.take(template);
        Assert.assertTrue(returned.dataID.equals(new Long(3)));
    }

    public void testMult(){
        MultTask thetask = new MultTask();
        MultCode thecode = new MultCode();
        MultData thedata = new MultData();
        TaskEntry tasktoexecute = new TaskEntry();

        // create and write the data
        thedata.ID = new Long(1);
        thedata.a = new Integer(5);
        thedata.b = new Integer(8);

		master.space.writeData(thedata);

        // create and write the code
        thecode.ID = new Long(2);
		thecode.data = thedata;

        master.space.writeCode(thecode);

        // create and write the task
        thetask.codeID = thecode.ID;
        thetask.dataID = thedata.ID;

        master.space.writeTask(thetask);

        tasktoexecute = (TaskEntry)worker.space.takeTask();
        tasktoexecute.code = worker.space.readCode(tasktoexecute.codeID);
        tasktoexecute.code.data = worker.space.readData(tasktoexecute.dataID);
        MultResult result = (MultResult)tasktoexecute.code.execute();
        Assert.assertTrue(result.answer.equals(new Integer(40)));
    }

    public void testEmptySpace(){
        Entry c;
        // take two entries left from the previous test
        master.space.deleteCode(new Long(2));
        master.space.deleteData(new Long(1));
//        Entry a = space.Space.take(null,null,Long.MAX_VALUE);
//        Entry b = space.Space.take(null,null,Long.MAX_VALUE);
            try {
                c = space.Space.takeIfExists(null, null, Long.MAX_VALUE);
            } catch (Exception e) {
                c = null;
            }
        Assert.assertTrue(c==null);
    }

    public void testCreateTasksCodesDataVectors(){
        MultCode codeElement = new MultCode();
        codeElement.ID = master.giveUniqueID();
        codes.add(codeElement);

        for (int i = 1; i <= 3; i++) {
            // Create data for this mult task
            MultData dataElement = new MultData(new Integer(i), new Integer(i));
            // Assign unique ID to data partition
            dataElement.ID = master.giveUniqueID();
            // Create task and assign this ID to this task
            MultTask taskElement = new MultTask();
            taskElement.codeID = codeElement.ID;
			taskElement.dataID = dataElement.ID;

            data.addElement(dataElement);
            tasks.addElement(taskElement);
        }
        Assert.assertTrue(codes.size() == 1);
        Assert.assertTrue(tasks.size() == 3);
        Assert.assertTrue(data.size() == 3);
    }
}
//test bed koui koui xoux