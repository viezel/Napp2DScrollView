/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package dk.napp.androidscroll;

import model.Napp2DMatrix;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;

public class ScalingFrameLayout extends FrameLayout {
	private float scale = 1;
	private float focusX, focusY;

	public ScalingFrameLayout(Context context) {
		super(context);
		setWillNotDraw(false);
	}

	public void setScale(float factor) {
		scale = factor;
		invalidate();
	}

	public float getScale() {
		return scale;
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
		canvas.scale(scale, scale, focusX, focusY);
		super.onDraw(canvas);
		Log.d("ScalingFrameLayout", "canvas - width: " + canvas.getWidth() + ", height: " + canvas.getHeight() + ", focusX: " + focusX + ", focusY: " + focusY);
	}
}