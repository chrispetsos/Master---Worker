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
import java.io.Serializable;

public interface Surface extends Serializable{
	public static final double EPSILON = 1E-4;
	public static final double DELTA = EPSILON * 10;

	public double getT(Ray r);
	public double getT(Ray r, Interval i);
	public boolean intersects(Ray r);

	public Intersection hit(Ray r);
	public Intersection hit(Ray r, Interval i);
}