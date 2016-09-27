package org.jraytrace.engine;

import java.awt.*;
import org.jraytrace.util.*;
import java.io.Serializable;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class RayTraceCanvas extends Canvas implements Serializable{

	private org.jraytrace.util.Color[][] image;
	private int[] imageBuffer;
	private Dimension size;

	public RayTraceCanvas(org.jraytrace.util.Color[][] image) {
		super();
		this.image = image;
	}

	public void paint(Graphics g) {
		int i;
		int j;
		int w;
		int h;

		if (image != null) {
			w = image[0].length;
			h = image.length;
			for (i = 0; i < h; i++) {
				for (j = 0; j < w; j++) {
					g.setColor(new java.awt.Color(image[i][j].toInt()));
					g.drawRect(j,i,1,1);
				}
			}
		}
	}


}