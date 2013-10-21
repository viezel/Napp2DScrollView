/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package dk.napp.androidscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

public class ScalingFrameLayout extends FrameLayout {
	
	public float curZoomDifference;
	private float curZoom = 1;
	public float focusX, focusY;
	public float translateX, translateY;
	
	public ScalingFrameLayout(Context context) {
		super(context);
		setWillNotDraw(false);
	}

	public void setScale(float factor) {
		curZoom = factor;
		invalidate();
	}

	public float getScale() {
		return curZoom;
	}

	public float getFocusX() {
		return focusX;
	}

	public void setFocusX(float focusX) {
		this.focusX = focusX;
	}

	public float getFocusY() {
		return focusY;
	}

	public void setFocusY(float focusY) {
		this.focusY = focusY;
	}

	@Override
	public void onDraw(Canvas canvas) {
		//canvas.translate(translateX, translateY);
		
		//TODO
		//The 4 parameter canvas.scale method call would enable pinch zoom to 
		//function on the entire image, however our boundaries at their current state, doesnt support that.
		//canvas.scale(curZoom, curZoom, focusX, focusY);¨
		
		canvas.scale(curZoom, curZoom);
		super.onDraw(canvas);
	}
}