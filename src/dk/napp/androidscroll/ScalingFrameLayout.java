/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package dk.napp.androidscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.FrameLayout;

public class ScalingFrameLayout extends FrameLayout {
    private float scale = 1;

    public ScalingFrameLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public void setScale(float factor){
        scale = factor;
        invalidate();
    }
    public float getScale(){
        return scale;
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.scale(scale, scale);
        super.onDraw(canvas);
    }

}