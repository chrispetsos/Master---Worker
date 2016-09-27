// the Worker class

package MasterWorkerSystem;

import javax.swing.JFrame;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Button;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Label;
import DataSpaceElements.*;

public class Worker implements Runnable{
    public WorkerSpace space;					// the space that the worker talks to
    private Task taskToExecute = new Task();	// locally stored task
    Thread workerThread = new Thread(this);		// the worker Thread
    private Button button3 = new Button();		// GUI
    private Button button4 = new Button();
    private TextArea textArea1 = new TextArea();
    private Button button1 = new Button();
    private Label label1 = new Label();
    private Button button2 = new Button();

    public Worker() {
        initGUI();
    }

    public void run(){
        startWork();
    }

// start taking and executing tasks infinitely
    public void startWork() {
		for (;;) {
            boolean takeCode = true;
            boolean takeData = true;
            // take a task
			Task task = space.takeTask();

			// Worker will NOT take the code again if and only if :
            // 1) the local task code ID is equal to the incoming task code ID AND
            // 2) the local task is of the same type with the incoming task
            if (task.codeID.equals(taskToExecute.codeID) && taskToExecute.getClass() == task.getClass()){
				takeCode = false;
            }

			if (takeCode){
            	// take the code if needed
				taskToExecute.code = space.readCode(task.codeID);
			}
			task.code = taskToExecute.code;

            // Same with data
            if (task.dataID.equals(taskToExecute.dataID) && taskToExecute.getClass() == task.getClass()){
				takeData = false;
            }

			if (takeData){
                // take the data if needed
				taskToExecute.code.data = space.readData(task.dataID);
			}
			task.code.data = taskToExecute.code.data;

			// Make Task local
            taskToExecute = task;
			// perform the task
			Result result = task.code.execute();
			this.textArea1.append("I executed a \"" + getTaskClass(taskToExecute.getClass().toString()) + "\" task with codeID " + task.codeID + " and dataID " + task.dataID + "\n");
			// write the result into the space
			if (result != null) {
				boolean writtenResult = false;
				while (!writtenResult) {
                	writtenResult = space.writeResult(result);
                }
			}
        }
    }

// returns the final class name of a packaged class
    private String getTaskClass(String className){
		String temp;
        int dotIndex;
        dotIndex = className.lastIndexOf(".");
        temp = className.substring(dotIndex+1,className.length());
		return temp;
    }

// space the worker will communicate with
	public void assignSpace(WorkerSpace space){
        this.space = space;
        enableWorking();
    }

    public void enableWorking(){
		this.button1.setEnabled(true);
        this.label1.setText("Found DataSpace!");
    }

// dereference the local buffer IDs
    public void initWorker(){
		taskToExecute.codeID = new Long(-1);
		taskToExecute.dataID = new Long(-1);
        this.textArea1.append("Worker Initialised!\n");
    }

    // GUI creation
    private void initGUI(){
        JFrame frame = new JFrame("Worker");
        frame.show();
        button4.setLabel("Resume");
        button4.setBounds(new java.awt.Rectangle(228, 11, 70, 31));
        button4.addMouseListener(
        new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { button4MouseClicked(e); }
        });
        button3.setLabel("Pause");
        button3.setBounds(new java.awt.Rectangle(128, 11, 70, 31));
        button3.addMouseListener(
        new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { button3MouseClicked(e); }
        });
        button1.setLabel("Start Work");
        button1.setBounds(new java.awt.Rectangle(26, 12, 72, 31));
        button1.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){button1MouseClicked(e);}});

        frame.setSize(450, 280);
        frame.setTitle("Worker");
        frame.setBounds(new java.awt.Rectangle(0, 0, 557, 300));
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(textArea1);
        frame.getContentPane().add(button3);
        frame.getContentPane().add(button4);
        frame.getContentPane().add(button1);
        frame.getContentPane().add(label1);
        frame.getContentPane().add(button2);
        textArea1.setText("");
        textArea1.setBounds(new java.awt.Rectangle(22, 72, 518, 173));
        frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){frameWindowClosing(e);}});

		this.button1.setEnabled(false);
		this.button3.setEnabled(false);
		this.button4.setEnabled(false);

        label1.setText("Resolving DataSpace ...");
        label1.setBounds(new java.awt.Rectangle(24,49,170,20));
        button2.setLabel("Init");
        button2.setBounds(new java.awt.Rectangle(334,12,86,33));
        button2.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){button2MouseClicked(e);}});
    }

    // Pause button
    public void button3MouseClicked(MouseEvent e) {
		this.button3.setEnabled(false);
		this.button4.setEnabled(true);
		this.textArea1.append("Paused...\n");
		workerThread.suspend();
    }

	// Resume button
    public void button4MouseClicked(MouseEvent e) {
		this.button4.setEnabled(false);
		this.button3.setEnabled(true);
		this.textArea1.append("Resuming...\n");
		workerThread.resume();
    }

    public void frameWindowClosing(WindowEvent e) {
        System.exit(0);
    }

    // Start Work Button
    public void button1MouseClicked(MouseEvent e) {
        workerThread.start();
		this.button1.setEnabled(false);
		this.button3.setEnabled(true);
		this.textArea1.append("Starting Work...\n");
    }

	// Init button
    public void button2MouseClicked(MouseEvent e) {
        initWorker();
    }
}
