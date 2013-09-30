/*package model;
//package dk.napp.androidscroll;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.appcelerator.kroll.KrollDict;
//import org.appcelerator.titanium.TiC;
//import org.appcelerator.titanium.TiDimension;
//import org.appcelerator.titanium.proxy.TiViewProxy;
//import org.appcelerator.titanium.util.TiAnimationBuilder;
//import org.appcelerator.titanium.util.TiConvert;
//import org.appcelerator.titanium.view.Ti2DMatrix;
//import org.appcelerator.titanium.view.TiCompositeLayout;
//import org.appcelerator.titanium.view.TiCompositeLayout.LayoutParams;
//import org.appcelerator.titanium.view.TiUIView;
//
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Looper;
//import android.os.MessageQueue;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.view.animation.AlphaAnimation;
//import android.view.animation.Animation;
//import android.view.animation.AnimationSet;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.Transformation;
//import android.view.animation.TranslateAnimation;
//
//public class NappAnimationBuilder extends TiAnimationBuilder {
//
//	private static ArrayList<WeakReference<View>> sRunningViews = new ArrayList<WeakReference<View>>();
//	protected Napp2DMatrix tdm = null;
//	private static final String LCAT = "NappAnimationBuilder";
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void applyOptions(HashMap options) {
//		Log.d(LCAT, "applyOptions has been called!");
//		if (options == null) {
//			return;
//		}
//
//		if (options.containsKey(TiC.PROPERTY_ANCHOR_POINT)) {
//			Object anchorPoint = options.get(TiC.PROPERTY_ANCHOR_POINT);
//			if (anchorPoint instanceof HashMap) {
//				HashMap point = (HashMap) anchorPoint;
//				anchorX = TiConvert.toFloat(point, TiC.PROPERTY_X);
//				anchorY = TiConvert.toFloat(point, TiC.PROPERTY_Y);
//			} else {
//				Log.e(LCAT,"Invalid argument type for anchorPoint property. Ignoring");
//			}
//		}
//
//		if (options.containsKey(TiC.PROPERTY_TRANSFORM)) {
//			tdm = (Napp2DMatrix) options.get(TiC.PROPERTY_TRANSFORM);
//			Log.d(LCAT, "applyOptions - setting TDM: " + tdm.toString());
//		}
//		if (options.containsKey(TiC.PROPERTY_DELAY)) {
//			delay = TiConvert.toDouble(options, TiC.PROPERTY_DELAY);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_DURATION)) {
//			duration = TiConvert.toDouble(options, TiC.PROPERTY_DURATION);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_OPACITY)) {
//			toOpacity = TiConvert.toDouble(options, TiC.PROPERTY_OPACITY);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_REPEAT)) {
//			repeat = TiConvert.toDouble(options, TiC.PROPERTY_REPEAT);
//
//			if (repeat == 0d) {
//				// A repeat of 0 is probably non-sensical. Titanium iOS
//				// treats it as 1 and so should we.
//				repeat = 1d;
//			}
//		} else {
//			repeat = 1d; // Default as indicated in our documentation.
//		}
//
//		if (options.containsKey(TiC.PROPERTY_AUTOREVERSE)) {
//			autoreverse = TiConvert
//					.toBoolean(options, TiC.PROPERTY_AUTOREVERSE);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_TOP)) {
//			top = TiConvert.toString(options, TiC.PROPERTY_TOP);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_BOTTOM)) {
//			bottom = TiConvert.toString(options, TiC.PROPERTY_BOTTOM);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_LEFT)) {
//			left = TiConvert.toString(options, TiC.PROPERTY_LEFT);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_RIGHT)) {
//			right = TiConvert.toString(options, TiC.PROPERTY_RIGHT);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_CENTER)) {
//			Object centerPoint = options.get(TiC.PROPERTY_CENTER);
//			if (centerPoint instanceof HashMap) {
//				HashMap center = (HashMap) centerPoint;
//				centerX = TiConvert.toString(center, TiC.PROPERTY_X);
//				centerY = TiConvert.toString(center, TiC.PROPERTY_Y);
//
//			} else {
//				Log.e(LCAT,"Invalid argument type for center property. Ignoring");
//			}
//		}
//
//		if (options.containsKey(TiC.PROPERTY_WIDTH)) {
//			width = TiConvert.toString(options, TiC.PROPERTY_WIDTH);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_HEIGHT)) {
//			height = TiConvert.toString(options, TiC.PROPERTY_HEIGHT);
//		}
//
//		if (options.containsKey(TiC.PROPERTY_BACKGROUND_COLOR)) {
//			backgroundColor = TiConvert.toColor(options,
//					TiC.PROPERTY_BACKGROUND_COLOR);
//		}
//
//		this.options = options;
//	}
//
//	public void start(TiViewProxy viewProxy, View view) {
//		Log.d(LCAT, "start method has been called!");
//		if (isAnimationRunningFor(view)) {
//			// This brings in parity with Titanium iOS, which appears to
//			// ignore requests to animate when an animation is in in progress.
//			return;
//		}
//		// Indicate that an animation is running on this view.
//		setAnimationRunningFor(view);
//
//		this.view = view;
//		this.viewProxy = viewProxy;
//
////		if (tdm == null || tdm.canUsePropertyAnimators()) {
//			// We can use Honeycomb+ property Animators via the
//			// NineOldAndroids library.
////			buildPropertyAnimators().start();
////		} else {
//			// We cannot use Honeycomb+ property Animators
//			// because a matrix transform is too complicated
//			// (see top of this file for explanation.)
//			Log.d(LCAT, "tdm: " + tdm.toString());
//			view.startAnimation(buildViewAnimations());
////		}
//	}
//
//	private AnimationSet buildViewAnimations() {
//		// if (Log.isDebugModeEnabled()) {
//		// Log.w(TAG, "Using legacy animations");
//		// }
//
//		ViewParent parent = view.getParent();
//		int parentWidth = 0;
//		int parentHeight = 0;
//		if (parent instanceof ViewGroup) {
//			ViewGroup group = (ViewGroup) parent;
//			parentHeight = group.getMeasuredHeight();
//			parentWidth = group.getMeasuredWidth();
//		}
//
//		return buildViewAnimations(view.getLeft(), view.getTop(),
//				view.getMeasuredWidth(), view.getMeasuredHeight(), parentWidth,
//				parentHeight);
//	}
//
//	private static boolean isAnimationRunningFor(View v) {
//		if (sRunningViews.size() == 0) {
//			return false;
//		}
//		// Not synchronized because we know it can only run on UI thread.
//		for (WeakReference<View> viewRef : sRunningViews) {
//			View refd = viewRef.get();
//			if (v.equals(refd)) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	private static void setAnimationRunningFor(View v) {
//		setAnimationRunningFor(v, true);
//	}
//
//	private static void setAnimationRunningFor(View v, boolean running) {
//		if (running) {
//			if (!isAnimationRunningFor(v)) {
//				sRunningViews.add(new WeakReference<View>(v));
//			}
//		} else {
//			WeakReference<View> toRemove = null;
//
//			for (WeakReference<View> viewRef : sRunningViews) {
//				View refd = viewRef.get();
//				if (v.equals(refd)) {
//					toRemove = viewRef;
//					break;
//				}
//			}
//			if (toRemove != null) {
//				sRunningViews.remove(toRemove);
//			}
//
//		}
//	}
//
//	protected class SizeAnimation extends Animation {
//
//		protected View view;
//		protected float fromWidth, fromHeight, toWidth, toHeight;
//		protected static final String TAG = "TiSizeAnimation";
//
//		public SizeAnimation(View view, float fromWidth, float fromHeight,
//				float toWidth, float toHeight) {
//			this.view = view;
//			this.fromWidth = fromWidth;
//			this.fromHeight = fromHeight;
//			this.toWidth = toWidth;
//			this.toHeight = toHeight;
//
//			// if (Log.isDebugModeEnabled()) {
//			// Log.d(TAG, "animate view from (" + fromWidth + "x" + fromHeight
//			// + ") to (" + toWidth + "x" + toHeight + ")",
//			// Log.DEBUG_MODE);
//			// }
//		}
//
//		protected void applyTransformation(float interpolatedTime,
//				Transformation transformation) {
//			this.applyTransformation(interpolatedTime, transformation);
//
//			int width = 0;
//			if (fromWidth == toWidth) {
//				width = (int) fromWidth;
//
//			} else {
//				width = (int) Math.floor(fromWidth
//						+ ((toWidth - fromWidth) * interpolatedTime));
//			}
//
//			int height = 0;
//			if (fromHeight == toHeight) {
//				height = (int) fromHeight;
//
//			} else {
//				height = (int) Math.floor(fromHeight
//						+ ((toHeight - fromHeight) * interpolatedTime));
//			}
//
//			ViewGroup.LayoutParams params = view.getLayoutParams();
//			params.width = width;
//			params.height = height;
//
//			if (params instanceof TiCompositeLayout.LayoutParams) {
//				TiCompositeLayout.LayoutParams tiParams = (TiCompositeLayout.LayoutParams) params;
//				tiParams.optionHeight = new TiDimension(height,
//						TiDimension.TYPE_HEIGHT);
//				tiParams.optionHeight.setUnits(TypedValue.COMPLEX_UNIT_PX);
//				tiParams.optionWidth = new TiDimension(width,
//						TiDimension.TYPE_WIDTH);
//				tiParams.optionWidth.setUnits(TypedValue.COMPLEX_UNIT_PX);
//			}
//
//			view.setLayoutParams(params);
//		}
//	}
//
//	private AnimationSet buildViewAnimations(int x, int y, int w, int h,
//			int parentWidth, int parentHeight) {
//		boolean includesRotation = false;
//		AnimationSet as = new AnimationSet(false);
//		AnimationListener animationListener = new AnimationListener();
//		as.setAnimationListener(animationListener);
//		TiUIView tiView = viewProxy.peekView();
//
//		if (toOpacity != null) {
//			// Determine which value to use for "from" value, in this order:
//			// 1.) If we previously performed an alpha animation on the view,
//			// use that as the from value.
//			// 2.) Else, if we have set an opacity property on the view, use
//			// that as the from value.
//			// 3.) Else, use 1.0f as the from value.
//
//			float fromOpacity;
//			float currentAnimatedAlpha = tiView == null ? Float.MIN_VALUE
//					: tiView.getAnimatedAlpha();
//
//			if (currentAnimatedAlpha != Float.MIN_VALUE) {
//				// MIN_VALUE is used as a signal that no value has been set.
//				fromOpacity = currentAnimatedAlpha;
//
//			} else if (viewProxy.hasProperty(TiC.PROPERTY_OPACITY)) {
//				fromOpacity = TiConvert.toFloat(viewProxy
//						.getProperty(TiC.PROPERTY_OPACITY));
//
//			} else {
//				fromOpacity = 1.0f;
//			}
//
//			Animation animation = new AlphaAnimation(fromOpacity,
//					toOpacity.floatValue());
//
//			// Remember the toOpacity value for next time, since we no way of
//			// looking
//			// up animated alpha values on the Android native view itself.
//			if (tiView != null) {
//				tiView.setAnimatedAlpha(toOpacity.floatValue());
//			}
//
//			applyOpacity = true; // Used in the animation listener
//			addAnimation(as, animation);
//			animation.setAnimationListener(animationListener);
//
//			if (viewProxy.hasProperty(TiC.PROPERTY_OPACITY)
//					&& toOpacity != null) {
//				prepareOpacityForAnimation();
//			}
//		}
//
//		if (backgroundColor != null) {
//			int fromBackgroundColor = 0;
//
//			if (viewProxy.hasProperty(TiC.PROPERTY_BACKGROUND_COLOR)) {
//				fromBackgroundColor = TiConvert.toColor(TiConvert
//						.toString(viewProxy
//								.getProperty(TiC.PROPERTY_BACKGROUND_COLOR)));
//			} else {
//				// Log.w(TAG,
//				// "Cannot animate view without a backgroundColor. View doesn't have that property. Using #00000000");
//				fromBackgroundColor = Color.argb(0, 0, 0, 0);
//			}
//
//			Animation a = new TiColorAnimation(view, fromBackgroundColor,
//					backgroundColor);
//			addAnimation(as, a);
//		}
//
//		if (tdm != null) {
//
//			Animation anim;
//			if (tdm.hasScaleOperation() && tiView != null) {
//				tiView.setAnimatedScaleValues(tdm.verifyScaleValues(tiView,
//						(autoreverse != null && autoreverse.booleanValue())));
//			}
//
//			if (tdm.hasRotateOperation() && tiView != null) {
//				includesRotation = true;
//				tiView.setAnimatedRotationDegrees(tdm.verifyRotationValues(
//						tiView,
//						(autoreverse != null && autoreverse.booleanValue())));
//			}
//
//			anim = new TiMatrixAnimation(tdm, anchorX, anchorY);
//
//			addAnimation(as, anim);
//
//		}
//
//		if (top != null || bottom != null || left != null || right != null
//				|| centerX != null || centerY != null) {
//			TiDimension optionTop = null, optionBottom = null;
//			TiDimension optionLeft = null, optionRight = null;
//			TiDimension optionCenterX = null, optionCenterY = null;
//
//			// Note that we're stringifying the values to make sure we
//			// use the correct TiDimension constructor, except when
//			// we know the values are expressed for certain in pixels.
//			if (top != null) {
//				optionTop = new TiDimension(top, TiDimension.TYPE_TOP);
//			} else if (bottom == null && centerY == null) {
//				// Fix a top value since no other y-axis value is being set.
//				optionTop = new TiDimension(view.getTop(), TiDimension.TYPE_TOP);
//				optionTop.setUnits(TypedValue.COMPLEX_UNIT_PX);
//			}
//
//			if (bottom != null) {
//				optionBottom = new TiDimension(bottom, TiDimension.TYPE_BOTTOM);
//			}
//
//			if (left != null) {
//				optionLeft = new TiDimension(left, TiDimension.TYPE_LEFT);
//			} else if (right == null && centerX == null) {
//				// Fix a left value since no other x-axis value is being set.
//				optionLeft = new TiDimension(view.getLeft(),
//						TiDimension.TYPE_LEFT);
//				optionLeft.setUnits(TypedValue.COMPLEX_UNIT_PX);
//			}
//
//			if (right != null) {
//				optionRight = new TiDimension(right, TiDimension.TYPE_RIGHT);
//			}
//
//			if (centerX != null) {
//				optionCenterX = new TiDimension(centerX,
//						TiDimension.TYPE_CENTER_X);
//			}
//
//			if (centerY != null) {
//				optionCenterY = new TiDimension(centerY,
//						TiDimension.TYPE_CENTER_Y);
//			}
//
//			int horizontal[] = new int[2];
//			int vertical[] = new int[2];
//			ViewParent parent = view.getParent();
//			View parentView = null;
//
//			if (parent instanceof View) {
//				parentView = (View) parent;
//			}
//
//			TiCompositeLayout.computePosition(parentView, optionLeft,
//					optionCenterX, optionRight, w, 0, parentWidth, horizontal);
//			TiCompositeLayout.computePosition(parentView, optionTop,
//					optionCenterY, optionBottom, h, 0, parentHeight, vertical);
//
//			Animation animation = new TranslateAnimation(Animation.ABSOLUTE, 0,
//					Animation.ABSOLUTE, horizontal[0] - x, Animation.ABSOLUTE,
//					0, Animation.ABSOLUTE, vertical[0] - y);
//
//			animation.setAnimationListener(animationListener);
//			addAnimation(as, animation);
//
//			// Will need to update layout params at end of animation
//			// so that touch events will be recognized at new location,
//			// and so that view will stay at new location after changes in
//			// orientation. But if autoreversing to original layout, no
//			// need to re-layout. But don't do it if a rotation is included
//			// because re-layout will lose the rotation.
//			relayoutChild = !includesRotation
//					&& (autoreverse == null || !autoreverse.booleanValue());
//
//			// if (Log.isDebugModeEnabled()) {
//			// Log.d(TAG, "animate " + viewProxy + " relative to self: "
//			// + (horizontal[0] - x) + ", " + (vertical[0] - y),
//			// Log.DEBUG_MODE);
//			// }
//
//		}
//
//		if (tdm == null && (width != null || height != null)) {
//			TiDimension optionWidth, optionHeight;
//
//			if (width != null) {
//				optionWidth = new TiDimension(width, TiDimension.TYPE_WIDTH);
//			} else {
//				optionWidth = new TiDimension(w, TiDimension.TYPE_WIDTH);
//				optionWidth.setUnits(TypedValue.COMPLEX_UNIT_PX);
//			}
//
//			if (height != null) {
//				optionHeight = new TiDimension(height, TiDimension.TYPE_HEIGHT);
//			} else {
//				optionHeight = new TiDimension(h, TiDimension.TYPE_HEIGHT);
//				optionHeight.setUnits(TypedValue.COMPLEX_UNIT_PX);
//			}
//
//			ViewParent parent = view.getParent();
//			View parentView = null;
//
//			if (parent instanceof View) {
//				parentView = (View) parent;
//			}
//
//			int toWidth = optionWidth
//					.getAsPixels((parentView != null) ? parentView : view);
//			int toHeight = optionHeight
//					.getAsPixels((parentView != null) ? parentView : view);
//
//			SizeAnimation sizeAnimation = new SizeAnimation(view, w, h,
//					toWidth, toHeight);
//
//			if (duration != null) {
//				sizeAnimation.setDuration(duration.longValue());
//			}
//
//			sizeAnimation.setInterpolator(new LinearInterpolator());
//			sizeAnimation.setAnimationListener(animationListener);
//			addAnimation(as, sizeAnimation);
//
//			// Will need to update layout params at end of animation
//			// so that touch events will be recognized within new
//			// size rectangle, and so that new size will survive
//			// any changes in orientation. But if autoreversing
//			// to original layout, no need to re-layout. But don't do it if a
//			// rotation is included
//			// because re-layout will lose the rotation.
//			relayoutChild = !includesRotation
//					&& (autoreverse == null || !autoreverse.booleanValue());
//		}
//
//		// Set duration, repeatMode and fillAfter only after adding children.
//		// The values are pushed down to the child animations.
//		as.setFillAfter(true);
//
//		if (duration != null) {
//			as.setDuration(duration.longValue());
//		}
//
//		if (autoreverse != null && autoreverse.booleanValue()) {
//			as.setRepeatMode(Animation.REVERSE);
//		} else {
//			as.setRepeatMode(Animation.RESTART);
//		}
//
//		// startOffset is relevant to the animation set and thus
//		// not also set on the child animations.
//		if (delay != null) {
//			as.setStartOffset(delay.longValue());
//		}
//
//		return as;
//	}
//
//	private void addAnimation(AnimationSet animationSet, Animation animation) {
//		// repeatCount is ignored at the AnimationSet level, so it needs to
//		// be set for each child animation manually.
//
//		// We need to reduce the repeat count by 1, since for native Android
//		// 1 would mean repeating it once.
//		int repeatCount = (repeat == null ? 0 : repeat.intValue() - 1);
//
//		// In Android (native), the repeat count includes reverses. So we
//		// need to double-up and add one to the repeat count if we're reversing.
//		if (autoreverse != null && autoreverse.booleanValue()) {
//			repeatCount = repeatCount * 2 + 1;
//		}
//
//		animation.setRepeatCount(repeatCount);
//
//		animationSet.addAnimation(animation);
//	}
//
//	private void prepareOpacityForAnimation() {
//		TiUIView tiView = viewProxy.peekView();
//		if (tiView == null) {
//			return;
//		}
//		tiView.setOpacity(1.0f);
//	}
//
//	// private AnimatorSet buildPropertyAnimators() {
//	// // if (Log.isDebugModeEnabled()) {
//	// // Log.d(TAG, "Property Animations will be used.");
//	// // }
//	// ViewParent parent = view.getParent();
//	// int parentWidth = 0;
//	// int parentHeight = 0;
//	//
//	// if (parent instanceof ViewGroup) {
//	// ViewGroup group = (ViewGroup) parent;
//	// parentHeight = group.getHeight();
//	// parentWidth = group.getWidth();
//	// }
//	//
//	// return buildPropertyAnimators(view.getLeft(), view.getTop(),
//	// view.getWidth(), view.getHeight(), parentWidth, parentHeight);
//	// }
//	//
//	// private AnimatorSet buildPropertyAnimators(int x, int y, int w, int h,
//	// int parentWidth, int parentHeight) {
//	// List<Animator> animators = new ArrayList<Animator>();
//	// boolean includesRotation = false;
//	//
//	// if (toOpacity != null) {
//	// addAnimator(
//	// animators,
//	// ObjectAnimator.ofFloat(view, "alpha",
//	// toOpacity.floatValue()));
//	// if (PRE_HONEYCOMB && viewProxy.hasProperty(TiC.PROPERTY_OPACITY)) {
//	// prepareOpacityForAnimation();
//	// }
//	// }
//	//
//	// if (backgroundColor != null) {
//	// TiBackgroundColorWrapper bgWrap = TiBackgroundColorWrapper
//	// .wrap(view);
//	// int currentBackgroundColor = bgWrap.getBackgroundColor();
//	// ObjectAnimator bgAnimator = ObjectAnimator.ofInt(view,
//	// "backgroundColor", currentBackgroundColor, backgroundColor);
//	// bgAnimator.setEvaluator(new ArgbEvaluator());
//	// addAnimator(animators, bgAnimator);
//	// }
//	//
//	// if (tdm != null) {
//	// // Derive a set of property Animators from the
//	// // operations in the matrix so we can go ahead
//	// // and use Honeycomb+ animations rather than
//	// // our custom TiMatrixAnimation.
//	// List<Operation> operations = tdm.getAllOperations();
//	// for (Operation operation : operations) {
//	// if (operation.anchorX != Ti2DMatrix.DEFAULT_ANCHOR_VALUE
//	// || operation.anchorY != Ti2DMatrix.DEFAULT_ANCHOR_VALUE) {
//	// setAnchor(w, h, operation.anchorX, operation.anchorY);
//	// }
//	// switch (operation.type) {
//	// case Operation.TYPE_ROTATE:
//	// includesRotation = true;
//	// if (operation.rotationFromValueSpecified) {
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "rotation", operation.rotateFrom,
//	// operation.rotateTo));
//	// } else {
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "rotation", operation.rotateTo));
//	// }
//	// break;
//	// case Operation.TYPE_SCALE:
//	// if (operation.scaleFromValuesSpecified) {
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "scaleX", operation.scaleFromX,
//	// operation.scaleToX));
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "scaleY", operation.scaleFromY,
//	// operation.scaleToY));
//	//
//	// } else {
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "scaleX", operation.scaleToX));
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "scaleY", operation.scaleToY));
//	// }
//	// break;
//	// case Operation.TYPE_TRANSLATE:
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "translationX", operation.translateX));
//	// addAnimator(animators, ObjectAnimator.ofFloat(view,
//	// "translationY", operation.translateY));
//	// }
//	// }
//	// }
//	//
//	// if (top != null || bottom != null || left != null || right != null
//	// || centerX != null || centerY != null) {
//	// TiDimension optionTop = null, optionBottom = null;
//	// TiDimension optionLeft = null, optionRight = null;
//	// TiDimension optionCenterX = null, optionCenterY = null;
//	//
//	// // Note that we're stringifying the values to make sure we
//	// // use the correct TiDimension constructor, except when
//	// // we know the values are expressed for certain in pixels.
//	// if (top != null) {
//	// optionTop = new TiDimension(top, TiDimension.TYPE_TOP);
//	// } else if (bottom == null && centerY == null) {
//	// // Fix a top value since no other y-axis value is being set.
//	// optionTop = new TiDimension(view.getTop(), TiDimension.TYPE_TOP);
//	// optionTop.setUnits(TypedValue.COMPLEX_UNIT_PX);
//	// }
//	//
//	// if (bottom != null) {
//	// optionBottom = new TiDimension(bottom, TiDimension.TYPE_BOTTOM);
//	// }
//	//
//	// if (left != null) {
//	// optionLeft = new TiDimension(left, TiDimension.TYPE_LEFT);
//	// } else if (right == null && centerX == null) {
//	// // Fix a left value since no other x-axis value is being set.
//	// optionLeft = new TiDimension(view.getLeft(),
//	// TiDimension.TYPE_LEFT);
//	// optionLeft.setUnits(TypedValue.COMPLEX_UNIT_PX);
//	// }
//	//
//	// if (right != null) {
//	// optionRight = new TiDimension(right, TiDimension.TYPE_RIGHT);
//	// }
//	//
//	// if (centerX != null) {
//	// optionCenterX = new TiDimension(centerX,
//	// TiDimension.TYPE_CENTER_X);
//	// }
//	//
//	// if (centerY != null) {
//	// optionCenterY = new TiDimension(centerY,
//	// TiDimension.TYPE_CENTER_Y);
//	// }
//	//
//	// int horizontal[] = new int[2];
//	// int vertical[] = new int[2];
//	// ViewParent parent = view.getParent();
//	// View parentView = null;
//	//
//	// if (parent instanceof View) {
//	// parentView = (View) parent;
//	// }
//	//
//	// TiCompositeLayout.computePosition(parentView, optionLeft,
//	// optionCenterX, optionRight, w, 0, parentWidth, horizontal);
//	// TiCompositeLayout.computePosition(parentView, optionTop,
//	// optionCenterY, optionBottom, h, 0, parentHeight, vertical);
//	//
//	// int translationX = horizontal[0] - x;
//	// int translationY = vertical[0] - y;
//	//
//	// addAnimator(animators,
//	// ObjectAnimator.ofFloat(view, "translationX", translationX));
//	// addAnimator(animators,
//	// ObjectAnimator.ofFloat(view, "translationY", translationY));
//	//
//	// // Pre-Honeycomb, we will need to update layout params at end of
//	// // animation
//	// // so that touch events will be recognized at new location,
//	// // and so that view will stay at new location after changes in
//	// // orientation. But if autoreversing to original layout, no
//	// // need to re-layout. Also, don't do it if a rotation is included,
//	// // since the re-layout will lose the rotation.
//	// relayoutChild = PRE_HONEYCOMB && !includesRotation
//	// && (autoreverse == null || !autoreverse.booleanValue());
//	//
//	// }
//	//
//	// if (tdm == null && (width != null || height != null)) {
//	// // A Scale animation *not* done via the 2DMatrix.
//	// TiDimension optionWidth, optionHeight;
//	//
//	// if (width != null) {
//	// optionWidth = new TiDimension(width, TiDimension.TYPE_WIDTH);
//	// } else {
//	// optionWidth = new TiDimension(w, TiDimension.TYPE_WIDTH);
//	// optionWidth.setUnits(TypedValue.COMPLEX_UNIT_PX);
//	// }
//	//
//	// if (height != null) {
//	// optionHeight = new TiDimension(height, TiDimension.TYPE_HEIGHT);
//	// } else {
//	// optionHeight = new TiDimension(w, TiDimension.TYPE_HEIGHT);
//	// optionHeight.setUnits(TypedValue.COMPLEX_UNIT_PX);
//	// }
//	//
//	// int toWidth = optionWidth.getAsPixels(view);
//	// int toHeight = optionHeight.getAsPixels(view);
//	//
//	// float scaleX = (float) toWidth / w;
//	// float scaleY = (float) toHeight / h;
//	//
//	// addAnimator(animators,
//	// ObjectAnimator.ofFloat(view, "scaleX", scaleX));
//	// addAnimator(animators,
//	// ObjectAnimator.ofFloat(view, "scaleY", scaleY));
//	//
//	// setAnchor(w, h);
//	//
//	// // Pre-Honeycomb, will need to update layout params at end of
//	// // animation
//	// // so that touch events will be recognized within new
//	// // size rectangle, and so that new size will survive
//	// // any changes in orientation. But if autoreversing
//	// // to original layout, no need to re-layout.
//	// // Also, don't do it if a rotation is included,
//	// // since the re-layout will lose the rotation.
//	// relayoutChild = PRE_HONEYCOMB && !includesRotation
//	// && (autoreverse == null || !autoreverse.booleanValue());
//	// }
//	//
//	// AnimatorSet as = new AnimatorSet();
//	// as.playTogether(animators);
//	//
//	// as.addListener(new AnimatorListener());
//	//
//	// if (duration != null) {
//	// as.setDuration(duration.longValue());
//	// }
//	//
//	// if (delay != null) {
//	// as.setStartDelay(delay.longValue());
//	// }
//	//
//	// return as;
//	// }
//	//
//	// private void setAnchor(int width, int height) {
//	// setAnchor(width, height, anchorX, anchorY);
//	// }
//	//
//	// private void setViewPivot(float pivotX, float pivotY) {
//	// AnimatorProxy proxy = AnimatorProxy.wrap(view);
//	// proxy.setPivotX(pivotX);
//	// proxy.setPivotY(pivotY);
//	// }
//	//
//	// private void setAnchor(int width, int height, float thisAnchorX,
//	// float thisAnchorY) {
//	// float pivotX = 0, pivotY = 0;
//	//
//	// if (thisAnchorX != Ti2DMatrix.DEFAULT_ANCHOR_VALUE) {
//	// pivotX = width * thisAnchorX;
//	// }
//	//
//	// if (thisAnchorY != Ti2DMatrix.DEFAULT_ANCHOR_VALUE) {
//	// pivotY = height * thisAnchorY;
//	// }
//	//
//	// if (PRE_HONEYCOMB) {
//	// setViewPivot(pivotX, pivotY);
//	// } else {
//	// setViewPivotHC(pivotX, pivotY);
//	// }
//	// }
//	//
//	// protected class AnimatorListener implements Animator.AnimatorListener {
//	//
//	// public void onAnimationCancel(Animator animator) {
//	// if (animator instanceof AnimatorSet) {
//	// setAnimationRunningFor(view, false);
//	// }
//	// }
//	//
//	// @SuppressWarnings("unchecked")
//	// public void onAnimationEnd(Animator animator) {
//	// if (relayoutChild && PRE_HONEYCOMB) {
//	// LayoutParams params = (LayoutParams) view.getLayoutParams();
//	// TiConvert.fillLayout(options, params);
//	// view.setLayoutParams(params);
//	// view.clearAnimation();
//	// relayoutChild = false;
//	// // TIMOB-11298 Propagate layout property changes to proxy
//	// for (Object key : options.keySet()) {
//	// if (TiC.PROPERTY_TOP.equals(key)
//	// || TiC.PROPERTY_BOTTOM.equals(key)
//	// || TiC.PROPERTY_LEFT.equals(key)
//	// || TiC.PROPERTY_RIGHT.equals(key)
//	// || TiC.PROPERTY_CENTER.equals(key)
//	// || TiC.PROPERTY_WIDTH.equals(key)
//	// || TiC.PROPERTY_HEIGHT.equals(key)
//	// || TiC.PROPERTY_BACKGROUND_COLOR.equals(key)) {
//	// viewProxy.setProperty((String) key, options.get(key));
//	// }
//	// }
//	// }
//	//
//	// if (animator instanceof AnimatorSet) {
//	// setAnimationRunningFor(view, false);
//	// if (callback != null) {
//	// callback.callAsync(viewProxy.getKrollObject(),
//	// new Object[] { new KrollDict() });
//	// }
//	//
//	// if (animationProxy != null) {
//	// // In versions prior to Honeycomb, don't fire the event
//	// // until the message queue is empty. There appears to be
//	// // a bug in versions before Honeycomb where this
//	// // onAnimationEnd listener can be called even before the
//	// // animation is really complete.
//	// if (Build.VERSION.SDK_INT >= TiC.API_LEVEL_HONEYCOMB) {
//	// animationProxy.fireEvent(TiC.EVENT_COMPLETE, null);
//	// } else {
//	// Looper.myQueue().addIdleHandler(
//	// new MessageQueue.IdleHandler() {
//	// public boolean queueIdle() {
//	// animationProxy.fireEvent(
//	// TiC.EVENT_COMPLETE, null);
//	// return false;
//	// }
//	// });
//	// }
//	// }
//	// }
//	//
//	// }
//	//
//	// public void onAnimationRepeat(Animator animator) {
//	// }
//	//
//	// public void onAnimationStart(Animator animator) {
//	// if (animationProxy != null) {
//	// animationProxy.fireEvent(TiC.EVENT_START, null);
//	// }
//	// }
//
//	// }
//
//	protected class AnimationListener implements Animation.AnimationListener {
//		@SuppressWarnings("unchecked")
//		public void onAnimationEnd(Animation a) {
//			if (relayoutChild) {
//				// Do it only for TiCompositeLayout.LayoutParams, for border
//				// views
//				// height and width are defined as 'MATCH_PARENT' and no change
//				// is
//				// needed
//				if (view.getLayoutParams() instanceof TiCompositeLayout.LayoutParams) {
//					LayoutParams params = (LayoutParams) view.getLayoutParams();
//					TiConvert.fillLayout(options, params);
//					view.setLayoutParams(params);
//				}
//				view.clearAnimation();
//				relayoutChild = false;
//				// TIMOB-11298 Propagate layout property changes to proxy
//				for (Object key : options.keySet()) {
//					if (TiC.PROPERTY_TOP.equals(key)
//							|| TiC.PROPERTY_BOTTOM.equals(key)
//							|| TiC.PROPERTY_LEFT.equals(key)
//							|| TiC.PROPERTY_RIGHT.equals(key)
//							|| TiC.PROPERTY_CENTER.equals(key)
//							|| TiC.PROPERTY_WIDTH.equals(key)
//							|| TiC.PROPERTY_HEIGHT.equals(key)
//							|| TiC.PROPERTY_BACKGROUND_COLOR.equals(key)) {
//						viewProxy.setProperty((String) key, options.get(key));
//					}
//				}
//			}
//
//			if (applyOpacity
//					&& (autoreverse == null || !autoreverse.booleanValue())) {
//				// There is an android bug where animations still occur after
//				// this method. We clear it from the view to
//				// correct this.
//				view.clearAnimation();
//				if (toOpacity.floatValue() == 0) {
//					view.setVisibility(View.INVISIBLE);
//
//				} else {
//					if (view.getVisibility() == View.INVISIBLE) {
//						view.setVisibility(View.VISIBLE);
//					}
//					// this is apparently the only way to apply an opacity to
//					// the entire view and have it stick
//					AlphaAnimation aa = new AlphaAnimation(
//							toOpacity.floatValue(), toOpacity.floatValue());
//					aa.setDuration(1);
//					aa.setFillAfter(true);
//					view.setLayoutParams(view.getLayoutParams());
//					view.startAnimation(aa);
//				}
//
//				applyOpacity = false;
//			}
//
//			if (a instanceof AnimationSet) {
//				setAnimationRunningFor(view, false);
//				if (callback != null) {
//					callback.callAsync(viewProxy.getKrollObject(),
//							new Object[] { new KrollDict() });
//				}
//
//				if (animationProxy != null) {
//					// In versions prior to Honeycomb, don't fire the event
//					// until the message queue is empty. There appears to be
//					// a bug in versions before Honeycomb where this
//					// onAnimationEnd listener can be called even before the
//					// animation is really complete.
//					if (Build.VERSION.SDK_INT >= TiC.API_LEVEL_HONEYCOMB) {
//						animationProxy.fireEvent(TiC.EVENT_COMPLETE, null);
//					} else {
//						Looper.myQueue().addIdleHandler(
//								new MessageQueue.IdleHandler() {
//									public boolean queueIdle() {
//										animationProxy.fireEvent(
//												TiC.EVENT_COMPLETE, null);
//										return false;
//									}
//								});
//					}
//				}
//			}
//		}
//
//		public void onAnimationRepeat(Animation a) {
//		}
//
//		public void onAnimationStart(Animation a) {
//			if (animationProxy != null) {
//				animationProxy.fireEvent(TiC.EVENT_START, null);
//			}
//		}
//	}
//}
*/