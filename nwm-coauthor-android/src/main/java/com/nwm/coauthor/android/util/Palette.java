package com.nwm.coauthor.android.util;

import android.graphics.Color;
import android.graphics.Paint;

public class Palette {
	public static Paint getFingerNailPaint() {
		Paint paint = new Paint();

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.rgb(0, 0, 0));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(1);
//		paint.setMaskFilter(new BlurMaskFilter(1, BlurMaskFilter.Blur.OUTER));

		return paint;
	}
}