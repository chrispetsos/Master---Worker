package org.jraytrace.engine;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

//import java.awt.image.MemoryImageSource;
import java.awt.Point;
import java.util.*;
import java.io.*;
import org.jraytrace.object.*;
import org.jraytrace.util.*;

public class RayTracer implements Serializable{

	private CSGSurface objects;
	private Vector lightSources;
	private Camera viewer;

	private static final int maxRecursion = 10;

	public RayTracer() {
		objects = null;
		Camera cam = new Camera();
		setCamera(cam);
	}

	public void setScene(CSGSurface object) {
		objects = object;
	}

	public CSGSurface getScene() {
		return objects;
	}

	public void setCamera(Camera cam) {
		viewer = cam;
	}

	public Camera getCamera() {
		return viewer;
	}

/*	public void trace() {
		Enumeration points = viewer.points();
		int w;
		int h;
		double maxBrightness = 0;

		w = (int) viewer.getResolution().getWidth();
		h = (int) viewer.getResolution().getHeight();

//        System.out.println(points.);
		while (points.hasMoreElements()) {
			Point pt = (Point) points.nextElement();
			Color ptColor = recursiveTrace(viewer.getRayForPoint(pt),maxRecursion);
			viewer.setPixelColor(pt, ptColor);
		}

		viewer.doAutoExposure();
	}
*/
	public Color recursiveTrace(Ray ray, int depth) {
		Color ret;

		if (objects.intersects(ray)) { //hit
			Intersection hitObj = objects.hit(ray,Interval.positiveReals);
			if (null != hitObj) {
				ret = illuminate(hitObj);
				ret.mult(hitObj.getMatteColor());
			} else {
				ret = new Color(0,1,0);
			}
		} else {
			ret = new Color(0,0,0);
		}
		return ret;
	}

	protected Color illuminate(Intersection intersect) {
		Enumeration sources = objects.getLightSources().elements();
		LightSource source;
		Ray rayToLight;
		Intersection lightData = null;
		Color lightColor = new Color(0,0,0);

		while (sources.hasMoreElements()) {
			Color tempColor;
			Surface tempSurf;
			source = (LightSource) sources.nextElement();
			rayToLight = objects.getRayToLight(source, intersect.getIntersection());
			lightData = objects.hit(rayToLight, new Interval(Surface.EPSILON, Double.POSITIVE_INFINITY));
			if (lightData != null) {
				tempSurf = lightData.getSurface();
				if (tempSurf instanceof LightSource) {
					if (((LightSource) tempSurf).equals(source)) {
						tempColor = lightData.getMatteColor();
						double thetaScale =
							intersect.getNormal().dot(rayToLight.direction);
						if (thetaScale > 0) {
							tempColor.scale(thetaScale);
							lightColor.add(tempColor);
						} else {
							tempColor = null;
						}
					} else {
						tempColor = null;
					}
				} else {
					tempColor = null;
				}
			} else {
				tempColor = null;
			}
		}
		return lightColor;
	}
}