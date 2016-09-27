package JavaSpaceImplementation.JavaSpaceSin;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.Label;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import MasterWorkerSystem.MasterSpace;
import MasterWorkerSystem.DataSpaceClient;
import JavaSpaceImplementation.Javaspace;

public class JavaSpaceClientSin extends DataSpaceClient{
    private static Button button3 = new Button();
    private static TextArea textArea1 = new TextArea();
    private static Button button1 = new Button();
    private static Label label1 = new Label();
    private static Button button2 = new Button();

    public JavaSpaceClientSin() {
		initGUI();
    }

	public void assignSpace(MasterSpace space){
        master.space = space;
        enableWorking();
    }

    public static void enableWorking(){
		button1.setEnabled(true);
		button3.setEnabled(true);
        label1.setText("Found DataSpace!");
    }

    private static void createTasksCodesData(){
        // add code
		SinCode codeElement = new SinCode();
        codeElement.ID = master.giveUniqueID();
        codes.add(codeElement);

        // add tasks and data
        for (int i=0;i<=360;i=i+20){
            SinData dataElement = new SinData(new Double(i));
            dataElement.ID = master.giveUniqueID();
            SinTask taskElement = new SinTask();
            taskElement.codeID = codeElement.ID;
			taskElement.dataID = dataElement.ID;

            data.add(dataElement);
            tasks.add(taskElement);
        }
    }

    private static void viewResults(){
        textArea1.setText("");
        for (int i=0;i<master.results.size();i++) {
            textArea1.append(master.results.get(i) + "\n");
        }
    }

    public static void main(String[] args) {
    	JavaSpaceClientSin form = new JavaSpaceClientSin();
        master.assignSpace(new Javaspace());
        enableWorking();
		master.collectResults();
    }

    private void initGUI(){
        JFrame frame = new JFrame("Worker");
        frame.show();
        button3.setLabel("View Results until now");
        button3.setBounds(new java.awt.Rectangle(148, 12, 150, 31));
        button3.addMouseListener(
        new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { button3MouseClicked(e); }
        });
        button1.setLabel("Generate Tasks");
        button1.setBounds(new java.awt.Rectangle(18, 12, 105, 31));
        button1.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){button1MouseClicked(e);}});

        frame.setSize(450, 280);
        frame.setTitle("Master");
        frame.setBounds(new java.awt.Rectangle(0, 0, 463, 300));
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(textArea1);
        frame.getContentPane().add(button3);
        frame.getContentPane().add(button1);
        frame.getContentPane().add(label1);
        frame.getContentPane().add(button2);
        textArea1.setText("");
        textArea1.setBounds(new java.awt.Rectangle(22, 72, 407, 173));
        frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){frameWindowClosing(e);}});

		this.button1.setEnabled(false);
		this.button3.setEnabled(false);

        label1.setText("Resolving DataSpace ...");
        label1.setBounds(new java.awt.Rectangle(24,49,170,20));
        button2.setLabel("Empty DataSpace");
        button2.setBounds(new java.awt.Rectangle(325, 11, 120, 31));
        button2.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){button2MouseClicked(e);}});
    }

    public void button1MouseClicked(MouseEvent e) {
		this.textArea1.append("Generating tasks...\n");
        createTasksCodesData();
		master.generateTasksDataCodes();
		this.textArea1.append("Tasks Generated!\n");
    }

    public void button3MouseClicked(MouseEvent e) {
		viewResults();
    }

    public void button2MouseClicked(MouseEvent e) {
        master.deleteCodesData();
		this.textArea1.append("DataSpace emptied!\n");
    }

    public void frameWindowClosing(WindowEvent e) {
        System.exit(0);
    }
}
