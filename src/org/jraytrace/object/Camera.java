package org.jraytrace.object;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import org.jraytrace.util.*;
import java.util.Enumeration;
import java.awt.Dimension;
import java.awt.Point;
//import java.awt.image.MemoryImageSource;
import java.io.Serializable;

public class Camera implements Serializable{

	final private static double filmHeight = .035; //'meters'

	private CoordinateSystem ref;

	private Dimension resolution;
//	private MemoryImageSource imageSource;
	private Color[][] image;
	private int[] imageBuffer;
	private double maxColor;
	private double focalLength; //'meters'
	private double filmWidth;

	public Camera() {
		int h = 500;
		int w = 500;
		ref = new CoordinateSystem(
			OrthonormalBase.createFromWV(new Vector3(0, 0, -1), new Vector3(0, 1, 0)),
			new Vector3(0, 0, 0));
		focalLength = .05;
		resolution = new Dimension(h, w);
		image = new Color[h][w];
		imageBuffer = new int[h * w];
/*		for (int i = 0; i < h; i++) {
			int red = (i * 255) / (h - 1);
			for (int j = 0; j < w; j++) {
				int blue = (j * 255) / (w - 1);
				imageBuffer[i * w + j] = (255 << 24) | (red << 16) | (blue);
			}
		}
*/
//		imageSource = new MemoryImageSource(resolution.width,
//			resolution.height, imageBuffer, 0, resolution.width);
		maxColor = 0;
		filmWidth = w * (filmHeight / h);
	}

	public void setResolution(Dimension res) {
		int h = res.height;
		int w = res.width;
		if ((resolution.width != res.width) && (resolution.height != res.height)) {
			image = null;
			imageBuffer = null;
//			imageSource = null;
			System.gc();
			image = new Color[h][w];
			imageBuffer = new int[h * w];
//			imageSource = new MemoryImageSource(w, h, imageBuffer, 0, w);
			maxColor = 0;
			filmWidth = w * (filmHeight / h);
		}
/*		for (int i = 0; i < h; i++) {
			int red = (i * 255) / (h - 1);
			for (int j = 0; j < w; j++) {
				int blue = (j * 255) / (w - 1);
				imageBuffer[i * w + j] = (255 << 24) | (red << 16) | (blue);
			}
		}
*/
//		imageSource.newPixels();
		resolution = res;
	}

	public Dimension getResolution() {
		return resolution;
	}

	public Vector3 getFilmPos() {
		return ref.getOrigin();
	}

	public void setFilmPos(Vector3 position) {
		ref.setOrigin(position);
	}

	public Vector3 getGazeDirection() {
		Vector3 ret = new Vector3(ref.getBase().getW());
		ret.neg();
		return ret;
	}

	public void setGazeDirection(Vector3 direction) {
		CoordinateSystem newRef;
		newRef = new CoordinateSystem(
			OrthonormalBase.createFromWV(direction, ref.getBase().getV()),
			ref.getOrigin());
		ref = newRef;
	}

	public Vector3 getCameraUp() {
		return new Vector3(ref.getBase().getV());
	}

	public void setCameraUp(Vector3 up) {
		CoordinateSystem newRef;
		newRef = new CoordinateSystem(
			OrthonormalBase.createFromWV(ref.getBase().getW(), up),
			ref.getOrigin());
		ref = newRef;
	}

	public CoordinateSystem getCoordinateSystem() {
		return ref;
	}

	public void setCoordinateSystem(CoordinateSystem sys) {
		ref = sys;
	}

//	public MemoryImageSource getImageSource() {
//		return imageSource;
//	}

	public Ray getRayForPoint(Point pt) {
		Vector3 rayPos;
		Vector3 rayDir;
		Vector3 camDir;
		double filmX;
		double filmY;

		filmX = (((double) pt.x / resolution.width) * filmWidth) - (filmWidth / 2);
		filmY = (filmHeight / 2) - (((double) pt.y / resolution.height) * filmHeight);

		camDir = new Vector3(filmX, filmY, focalLength);

		rayDir = ref.getBase().getTransform().transformVector(camDir);
		rayPos = new Vector3(ref.getOrigin());
		return new Ray(rayPos, rayDir);
	}

	public void setPixelColor(Point pt, Color ptColor) {
		image[pt.y][pt.x] = new Color(ptColor);
		imageBuffer[resolution.width * pt.y + pt.x] = ptColor.toInt();
		maxColor = Math.max(maxColor, ptColor.maxComponent());
		if (pt.x == resolution.width - 1) {
//			imageSource.newPixels(0, pt.y, resolution.width, 1);
		}
	}

	public void doAutoExposure() {
		if (maxColor > 0) {
			double scaleFactor = 1 / maxColor;
			int w = resolution.width;
			int h = resolution.height;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					image[i][j].scale(scaleFactor);
					imageBuffer[w * i + j] = image[i][j].toInt();
				}
//				imageSource.newPixels(0, i, w, 1);
			}
		}
//		imageSource.newPixels();
	}

	protected class pointEnum implements Enumeration {
		private int x;
		private int y;
		private Dimension res;

		public pointEnum(Dimension res) {
			this.res = res;
			x = -1;
			y = 0;
		}

		public boolean hasMoreElements() {
			if (x < res.width && y < res.height) {
				if ((x == res.width - 1) && (y == res.height - 1)) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		}

		public Object nextElement() {
			x++;
			if (x >= res.width) {
				x = 0;
				y++;
			}
			if (y > res.height) {
				return null;
			}
			return (Object) new Point(x,y);
		}
	}

	public Enumeration points() {
		return (Enumeration) new pointEnum(this.resolution);
	}

}