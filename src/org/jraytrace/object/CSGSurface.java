package org.jraytrace.object;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import java.util.Vector;
import org.jraytrace.util.*;
import java.io.Serializable;

public interface CSGSurface extends Surface, Serializable{
	public Surface getRight();
	public void setRight(Surface right);
	public Surface getLeft();
	public void setLeft(Surface left);
	public Vector getLightSources();
	public Ray getRayToLight(LightSource light, Vector3 point);
}