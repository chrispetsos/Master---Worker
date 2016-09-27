package JavaSpaceImplementation.JavaSpaceRaytrace;

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
import org.jraytrace.object.UnionSurface;
import org.jraytrace.object.Sphere;
import org.jraytrace.object.PointSource;
import org.jraytrace.util.Vector3;
import org.jraytrace.engine.RayTracer;
import java.awt.Point;
import java.awt.Canvas;
import java.awt.Panel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import org.jraytrace.util.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.util.Date;

public class JavaSpaceClientRaytrace extends DataSpaceClient{
    private static int chunkSize = 250;
    private static JFrame frame;
	private static Button button3 = new Button();
    private static TextArea textArea1 = new TextArea();
    private static Button button1 = new Button();
    private static Label label1 = new Label();
    private static Button button2 = new Button();
    private static RayTracer tracer = new RayTracer();
	private static JLabel lbl;
	private static ImageIcon icon;
	private static Image image;
	private static MemoryImageSource src;
	private static int[] imageBuffer = new int[500*500];
	private static Color[][] imageMap = new Color[500][500];
	private static double maxColor = 0;
    private static TextField textField1 = new TextField();

    public JavaSpaceClientRaytrace() {
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

    public static void disableWorking(){
		button1.setEnabled(false);
		button3.setEnabled(false);
        textArea1.append("Computation ended!\n");
    }

    // creates 27 spheres positioned as a cube
	private static void createDefaultScene() {
		UnionSurface scene;
		UnionSurface scene2;
		UnionSurface scene3;
		UnionSurface tempScene;
		Sphere sph;
		Sphere sph2;
		Sphere sph3;
		PointSource lightSource;
		PointSource lightSource2;

		lightSource = new PointSource(new Vector3(-5, 3, 4), new org.jraytrace.util.Color(1,1,1));
		lightSource2 = new PointSource(new Vector3(5, 3, 4), new org.jraytrace.util.Color(1,0,0));
		tempScene = new UnionSurface(lightSource2, lightSource);
        for (int i=-2;i<5;i=i+3){
			for (int j=-2;j<5;j=j+3){
                for (int k=0;k<9;k=k+3){
					sph = new Sphere(new Vector3(i, j, -10-k), 1, new org.jraytrace.util.Color(1,1,1));
                    tempScene = new UnionSurface(sph, tempScene);
                }
            }
        }

		tracer.setScene(tempScene);
	}

    private static void createTasksCodesData(){

			createDefaultScene();
			RaytraceCode codeElement = new RaytraceCode();
			codeElement.tracer = tracer;
			codeElement.ID = master.giveUniqueID();
			codes.add(codeElement);
	
			for (int i = 0; i<500; i = i+chunkSize){
				for (int j = 0; j<500; j = j+chunkSize){
					RaytraceData dataElement = new RaytraceData(new Point(i,j), new Point(i+chunkSize,j+chunkSize));
					dataElement.ID = master.giveUniqueID();
					RaytraceTask taskElement = new RaytraceTask();
					taskElement.codeID = codeElement.ID;
					taskElement.dataID = dataElement.ID;
	
					data.add(dataElement);
					tasks.add(taskElement);
				}
			}
    }

    // view Results from result vector
    private static void viewResults(){
        for (int l=0;l<master.results.size();l++) {
			RaytraceResult theResult = (RaytraceResult)master.results.get(l);
			int k = -1;
			for (int i=theResult.UL.x;i<theResult.LR.x;i++){
				for (int j=theResult.UL.y;j<theResult.LR.y;j++){
					k++;
					Point temp = new Point(j,i);
					imageMap[temp.x][temp.y] = new Color(theResult.pixels[k]);
					imageBuffer[500 * temp.x + temp.y] = theResult.pixels[k].toInt();
					maxColor = Math.max(maxColor, theResult.pixels[k].maxComponent());
				}
			}
        }
	doAutoExposure();
    if (master.numOfTasks==master.results.size()){	// computation ended
		disableWorking();
        textArea1.append("Seconds passed: " + master.secsPassed + "\n");
    }
    }

	// scale pixel colors
	public static void doAutoExposure() {
		if (maxColor > 0) {
			double scaleFactor = 1 / maxColor;
			int w = 500;
			int h = 500;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
                    if (imageMap[i][j]!=null){
						imageMap[i][j].scale(scaleFactor);
						imageBuffer[w * i + j] = imageMap[i][j].toInt();
                    }
				}
				src.newPixels(0, i, w, 1);
			}
		}
		src.newPixels();
	}

    public static void main(String[] args) {
    	JavaSpaceClientRaytrace form = new JavaSpaceClientRaytrace();
        master.assignSpace(new Javaspace());
        enableWorking();
		master.collectResults();
    }

    private void initGUI(){
        JFrame frame = new JFrame("Worker");
		src = new MemoryImageSource(500, 500, imageBuffer, 0, 500);
		src.setAnimated(true);
		image = frame.getContentPane().createImage(src);
		icon = new ImageIcon(image);
		lbl = new JLabel(icon);
		frame.getContentPane().add(lbl);
		frame.pack();
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
        frame.setTitle("Raytrace Master");
        frame.setBounds(new java.awt.Rectangle(0, 0, 816, 735));
        frame.setResizable(false);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(textArea1);
        frame.getContentPane().add(button3);
        frame.getContentPane().add(button1);
        frame.getContentPane().add(label1);
        frame.getContentPane().add(button2);
        frame.getContentPane().add(textField1);
        textArea1.setText("");
        textArea1.setBounds(new java.awt.Rectangle(551, 14, 243, 160));
        frame.addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){frameWindowClosing(e);}});

		this.button1.setEnabled(false);
		this.button3.setEnabled(false);

        label1.setText("Resolving DataSpace ...");
        label1.setBounds(new java.awt.Rectangle(192, 56, 170, 20));
        button2.setLabel("Empty DataSpace");
        button2.setBounds(new java.awt.Rectangle(325, 11, 120, 31));
        button2.addMouseListener(new MouseAdapter(){public void mouseClicked(MouseEvent e){button2MouseClicked(e);}});
        lbl.setBounds(new java.awt.Rectangle(15, 97, 500, 500));
        textField1.setText("500");
        textField1.setBounds(new java.awt.Rectangle(21,56,103,22));
    }

	// Generate tasks button
    public void button1MouseClicked(MouseEvent e) {
        chunkSize = Integer.parseInt(textField1.getText());
        if (500%chunkSize==0 && chunkSize!=0){
			this.textArea1.append("Generating tasks...\n");
			createTasksCodesData();
			master.generateTasksDataCodes();
			this.textArea1.append("Tasks Generated!\n");
        }
        else{
			this.textArea1.append("Invalid chunk size!\n");
        }
    }

    // View Result Button
    public void button3MouseClicked(MouseEvent e) {
		viewResults();
    }

    // Empty DataSpace button
    public void button2MouseClicked(MouseEvent e) {
        master.deleteCodesData();
		this.textArea1.append("DataSpace emptied!\n");
    }

    public void frameWindowClosing(WindowEvent e) {
        System.exit(0);
    }
}
