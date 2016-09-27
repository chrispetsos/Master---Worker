package org.jraytrace.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import org.jraytrace.object.*;
import org.jraytrace.util.*;
import java.util.Vector;
//import Acme.JPM.Encoders.*;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class TestClient extends JFrame {

	private Image image;
	private TraceThread traceThread;
	private MemoryImageSource src;

	public TestClient() {
		super("TestClient");
		JPanel pane = new JPanel();
		JLabel lbl;
		ImageIcon icon;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		traceThread = new TraceThread("tracer");
		//RayTracer tracer = new RayTracer();
		//tracer.setScene(new Sphere(new Vector3(0, 0, -50), 25));

//		src = traceThread.tracer.getCamera().getImageSource();
		src.setAnimated(true);

		image = pane.createImage(src);
		icon = new ImageIcon(image);
		lbl = new JLabel(icon);
		pane.add(lbl);

		//traceThread.myImg = image;

		/*
		int h = 500;
		int w = 500;
		int pix[] = new int[h * w];
		MemoryImageSource src = new MemoryImageSource(w, h, pix, 0, w);
		src.setAnimated(true);
		image = pane.createImage(src);
		icon = new ImageIcon(image);
		lbl = new JLabel(icon);
		pane.add(lbl);
		*/
		pane.setSize(500,500);
		getContentPane().add(pane);
		pack();
		show();

		//tracer.trace();
		traceThread.start();

		/*src.newPixels();*/
	}


	protected class TraceThread extends Thread {
		public RayTracer tracer;

		public TraceThread(String name) {
			super(name);

			tracer = new RayTracer();
			//tracer.getCamera().setGazeDirection(new Vector3(0, 1, -10));
			createDefaultScene();
		}

		private void createDefaultScene() {
			UnionSurface scene;
			UnionSurface scene2;
			UnionSurface scene3;
			Sphere sph;
			Sphere sph2;
			Sphere sph3;
			PointSource lightSource;
			PointSource lightSource2;

			sph = new Sphere(new Vector3(0, 0, -10), 1, new org.jraytrace.util.Color(1,1,1));
			lightSource = new PointSource(new Vector3(-5, 3, 4), new org.jraytrace.util.Color(1,1,1));

			lightSource2 = new PointSource(new Vector3(5, 3, 4), new org.jraytrace.util.Color(1,0,0));
			sph2 = new Sphere(new Vector3(1, 1, -8), 1, new org.jraytrace.util.Color(1, 0, 0));
			sph3 = new Sphere(new Vector3(8, 8, 0), 1, new org.jraytrace.util.Color(1, 0, 0));

//			scene = new UnionSurface(sph, sph2);
			scene2 = new UnionSurface(lightSource2, lightSource);
			scene3 = new UnionSurface(sph, sph3);

			scene = new UnionSurface(scene3, scene2);

			tracer.setScene(scene);

		}

/*		public void run() {

			if (tracer == null) {
				return;
			}
			tracer.trace();
		}
*/	}

	public static void main(String[] args) {
		JFrame frame = new TestClient();
		//frame.show();
	}
}