package dk.napp.androidscroll;

import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.Ti2DMatrix;

import android.graphics.Matrix;
import android.util.Log;

public class Napp2DMatrix extends Ti2DMatrix {
	
	private static final String LCAT = "Napp2DMatrix";
	
	public static class Operation {
		public static final int TYPE_SCALE = 0;
		public static final int TYPE_TRANSLATE = 1;
		public static final int TYPE_ROTATE = 2;
		public static final int TYPE_MULTIPLY = 3;
		public static final int TYPE_INVERT = 4;

		public float scaleFromX, scaleFromY, scaleToX, scaleToY;
		public float translateX, translateY;
		public float rotateFrom, rotateTo;
		public float anchorX = 0.5f, anchorY = 0.5f;
		public Ti2DMatrix multiplyWith;
		public int type;
		public boolean scaleFromValuesSpecified = false;
		public boolean rotationFromValueSpecified = false;

		public Operation(int type) {
			this.type = type;
		}

		public void apply(float interpolatedTime, Matrix matrix,
				int childWidth, int childHeight, float anchorX, float anchorY) {
			anchorX = anchorX == DEFAULT_ANCHOR_VALUE ? this.anchorX : anchorX;
			anchorY = anchorY == DEFAULT_ANCHOR_VALUE ? this.anchorY : anchorY;
			switch (type) {
			case TYPE_SCALE:
				matrix.preScale((interpolatedTime * (scaleToX - scaleFromX))
						+ scaleFromX,
						(interpolatedTime * (scaleToY - scaleFromY)) + scaleFromY, anchorX * childWidth, anchorY * childHeight);
				break;
			case TYPE_TRANSLATE:
				matrix.preTranslate(interpolatedTime * translateX,
						interpolatedTime * translateY);
				break;
			case TYPE_ROTATE:
				matrix.preRotate((interpolatedTime * (rotateTo - rotateFrom))
						+ rotateFrom, anchorX * childWidth, anchorY
						* childHeight);
				break;
			case TYPE_MULTIPLY:
				matrix.preConcat(multiplyWith.interpolate(interpolatedTime,
						childWidth, childHeight, anchorX, anchorY));
				break;
			case TYPE_INVERT:
				matrix.invert(matrix);
				break;
			}
		}

	}

	public Operation op;
	public Napp2DMatrix next, prev;
	
	public Napp2DMatrix(){
		
	}

	public Napp2DMatrix(Napp2DMatrix prev, int opType){
		if (prev != null) {
			// this.prev represents the previous matrix. This value does not change.
			this.prev = prev;
			// prev.next is not constant. Subsequent calls to Ti2DMatrix() will alter the value of prev.next.
			prev.next = this;
			}
			this.op = new Operation(opType);
	}
	
	@Override
	public Napp2DMatrix scale(Object args[]) {
		Napp2DMatrix newMatrix = new Napp2DMatrix(this, Operation.TYPE_SCALE);
		newMatrix.op.scaleFromX = newMatrix.op.scaleFromY = VALUE_UNSPECIFIED;
		newMatrix.op.scaleToX = newMatrix.op.scaleToY = 1.0f;

		// varargs for API backwards compatibility
		if (args.length == 4) {
			// scale(fromX, fromY, toX, toY)
			newMatrix.op.scaleFromValuesSpecified = true;
			newMatrix.op.scaleFromX = TiConvert.toFloat(args[0]);
			newMatrix.op.scaleFromY = TiConvert.toFloat(args[1]);
			newMatrix.op.scaleToX = TiConvert.toFloat(args[2]);
			newMatrix.op.scaleToY = TiConvert.toFloat(args[3]);
		}
		if (args.length == 3) {
			Log.d(LCAT, "scale - args.length == 3, " + args[0] + ", " + args[1] + ", " + args[2]);
			newMatrix.op.scaleFromValuesSpecified = false;
			newMatrix.op.scaleToX = newMatrix.op.scaleToY = TiConvert.toFloat(args[0]);
			newMatrix.op.anchorX = TiConvert.toFloat(args[1]);
			newMatrix.op.anchorY = TiConvert.toFloat(args[2]);
		}
		if (args.length == 2) {
			// scale(toX, toY)
			newMatrix.op.scaleFromValuesSpecified = false;
			newMatrix.op.scaleToX = TiConvert.toFloat(args[0]);
			newMatrix.op.scaleToY = TiConvert.toFloat(args[1]);
		} else if (args.length == 1) {
			// scale(scaleFactor)
			newMatrix.op.scaleFromValuesSpecified = false;
			newMatrix.op.scaleToX = newMatrix.op.scaleToY = TiConvert.toFloat(args[0]);
		}
		// TODO newMatrix.handleAnchorPoint(newMatrix.getProperties());
		return newMatrix;
	}	
}
