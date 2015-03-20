package com.yun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.androidanimation.R;

/**
 * 可以360度滑动的relativelayout
 * 
 * @author Administrator
 * 
 */
public class AnimationRelativelayout extends RelativeLayout implements
		OnTouchListener {
	private RelativeLayout topLayout, middleLayout, bottomLayout;
	private ImageView topImage, middleImage, bottomImage;
	private ViewCoordinate topViewCoordinate, middleViewCoordinate,
			bottomViewCoordinate;
	private static final String TOP = "top";
	private static final String MIDDLE = "middle";
	private static final String BOTTOM = "bottom";
	private static String NOW_VISABLE = TOP;
	private float downX, downY;
	private AnimationListener listener;

	public AnimationRelativelayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		listener = new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				switch (NOW_VISABLE) {
				case TOP:
					topImage.setImageResource(R.drawable.b);
					topLayout.layout(topViewCoordinate.left,
							topViewCoordinate.top, topViewCoordinate.right,
							topViewCoordinate.bottom);
					middleImage.setImageResource(R.drawable.c);
					bottomImage.setImageResource(R.drawable.a);
					NOW_VISABLE = MIDDLE;
					break;
				case MIDDLE:
					topImage.setImageResource(R.drawable.c);
					topLayout.layout(topViewCoordinate.left,
							topViewCoordinate.top, topViewCoordinate.right,
							topViewCoordinate.bottom);
					middleImage.setImageResource(R.drawable.a);
					bottomImage.setImageResource(R.drawable.b);
					NOW_VISABLE = BOTTOM;
					break;
				case BOTTOM:
					topImage.setImageResource(R.drawable.a);
					topLayout.layout(topViewCoordinate.left,
							topViewCoordinate.top, topViewCoordinate.right,
							topViewCoordinate.bottom);
					middleImage.setImageResource(R.drawable.b);
					bottomImage.setImageResource(R.drawable.c);
					NOW_VISABLE = TOP;
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (topViewCoordinate == null) {
				topViewCoordinate = new ViewCoordinate(topLayout.getLeft(),
						topLayout.getTop(), topLayout.getRight(),
						topLayout.getBottom());
				middleViewCoordinate = new ViewCoordinate(
						middleLayout.getLeft(), middleLayout.getTop(),
						middleLayout.getRight(), middleLayout.getBottom());
				bottomViewCoordinate = new ViewCoordinate(
						bottomLayout.getLeft(), bottomLayout.getTop(),
						bottomLayout.getRight(), bottomLayout.getBottom());
			}
			downX = event.getX();
			downY = event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			v.layout(v.getLeft() + (int) (event.getX() - downX), v.getTop()
					+ (int) (event.getY() - downY),
					v.getRight() + (int) (event.getX() - downX), v.getBottom()
							+ (int) (event.getY() - downY));
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			if (v.getLeft() < topViewCoordinate.left
					- (topViewCoordinate.right - topViewCoordinate.left)/2) {
				TranslateAnimation animation = new TranslateAnimation(0, -1000,
						0, 0);
				animation.setDuration(200);
				v.startAnimation(animation);
				animation.setAnimationListener(listener);
			} else if (v.getRight() > topViewCoordinate.right
					+ (topViewCoordinate.right - topViewCoordinate.left)/2) {
				TranslateAnimation animation = new TranslateAnimation(0, 1000,
						0, 0);
				animation.setDuration(200);
				v.startAnimation(animation);
				animation.setAnimationListener(listener);
			} else if (v.getTop() < topViewCoordinate.top
					- (topViewCoordinate.bottom - topViewCoordinate.top)/2) {
				TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
						-1000);
				animation.setDuration(200);
				v.startAnimation(animation);
				animation.setAnimationListener(listener);
			} else if (v.getBottom() > topViewCoordinate.bottom
					+ (topViewCoordinate.bottom - topViewCoordinate.top)/2) {
				TranslateAnimation animation = new TranslateAnimation(0, 0, 0,
						1000);
				animation.setDuration(200);
				v.startAnimation(animation);
				animation.setAnimationListener(listener);
			} else {
				v.layout(topViewCoordinate.left, topViewCoordinate.top,
						topViewCoordinate.right, topViewCoordinate.bottom);
			}

		}
		return true;
	}

	/**
	 * view坐标 用来记录三个layout的初始坐标
	 * 
	 * @author Administrator
	 * 
	 */
	private class ViewCoordinate {
		public int left;
		public int top;
		public int right;
		public int bottom;

		public ViewCoordinate(int left, int top, int right, int bottom) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}

	}

	@Override
	protected void onFinishInflate() {
		topLayout = (RelativeLayout) findViewById(R.id.top);
		middleLayout = (RelativeLayout) findViewById(R.id.middle);
		bottomLayout = (RelativeLayout) findViewById(R.id.bottom);
		topImage = (ImageView) findViewById(R.id.top_image);
		middleImage = (ImageView) findViewById(R.id.middle_image);
		bottomImage = (ImageView) findViewById(R.id.bottom_image);
		topLayout.setOnTouchListener(this);
		super.onFinishInflate();
	}

}
