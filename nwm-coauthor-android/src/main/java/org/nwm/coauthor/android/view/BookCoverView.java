package org.nwm.coauthor.android.view;

import com.nwm.coauthor.android.util.Palette;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BookCoverView extends View{
	private Paint paint;
	private Path path;
	private float prevX;
	private float prevY;
	
	public BookCoverView(Context context) {
		super(context);
		init();
	}	
	
	public BookCoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public BookCoverView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init(){
		paint = Palette.getFingerNailPaint();
		path = new Path();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		canvas.drawPath(this.path, this.paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			touchDown(event.getX(), event.getY());
		case MotionEvent.ACTION_MOVE:
			touchMove(event.getX(), event.getY());
		}
		
		return true;
	}

	private void touchDown(float x, float y) {
		this.path.moveTo(x, y);
		this.prevX = x;
		this.prevY = y;
	}

	private void touchMove(float newX, float newY) {
		this.path.quadTo(this.prevX, this.prevY, (this.prevX + newX)/2, (this.prevY + newY)/2);
		
		this.prevX = newX;
		this.prevY = newY;
		
		invalidate();
	}
}
