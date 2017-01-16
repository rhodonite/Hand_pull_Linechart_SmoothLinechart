package com.rhodonite.linechart_smoothlinechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SmoothLineChartEquallySpaced extends View {
	
	private static final int CHART_COLOR = 0xFF0099CC;
	private static final int CIRCLE_SIZE = 8;
	private static final int STROKE_SIZE = 2;	
	private static final float SMOOTHNESS = 0.35f; // the higher the smoother, but don't go over 0.5
	
	private final Paint mPaint;
	private final Path mPath;
	private final float mCircleSize;
	private final float mStrokeSize;
	private final float mBorder;
	private String[] mXAxis = {"0", "1", "2", "3", "4", "5", "6"};
	private float[] mValues;
	private float mMinY;
	private float mMaxY;
	public List<PointF> points;
	public float[] mValues_temp;
    public float mBorder_height=0,height_dY=0,width_dX=0,get_mBorder;
	public SmoothLineChartEquallySpaced(Context context) {
		this(context, null, 0);
	}

	public SmoothLineChartEquallySpaced(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SmoothLineChartEquallySpaced(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		float scale = context.getResources().getDisplayMetrics().density;
		
		mCircleSize = scale * CIRCLE_SIZE;
		mStrokeSize = scale * STROKE_SIZE;
		mBorder = mCircleSize;
		
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(mStrokeSize);
		
		mPath = new Path();
	}
	
	public void setData(float[] values) {
		mValues = values;
		mValues_temp = values;
		if (values != null && values.length > 0) {
			mMaxY = 34;
			//mMinY = values[0].y;
			for (float y : values) {
				if (y > mMaxY) 
					mMaxY = y;
				/*if (y < mMinY)
					mMinY = y;*/
			}
		}
				
		invalidate();
	}
	
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(mXAxis.length==0){
			throw new IllegalArgumentException("X or Y items is null");
		}
		if (mValues == null || mValues.length == 0)
			return;

		int size = mValues.length;
		
		final float height = getMeasuredHeight() - 2*mBorder;	
		final float width = getMeasuredWidth() - 2*mBorder;

		final float dX = mValues.length > 1 ? mValues.length-1  : (2);	
		final float dY = (mMaxY-mMinY) > 0 ? (mMaxY-mMinY) : (2);
				
		mPath.reset();

		mBorder_height= mBorder + height;
		height_dY=height/dY;
		width_dX=width/dX;
		get_mBorder=mBorder;

		// calculate point coordinates
		points = new ArrayList<PointF>(size);
		for (int i=0; i<size; i++) {
			float x = mBorder + i*width/dX;
			float y = mBorder + height - (mValues[i]-mMinY)*(height/dY);
			points.add(new PointF(x, y));
		}



		// calculate smooth path
		float lX = 0, lY = 0;
		mPath.moveTo(points.get(0).x, points.get(0).y);
		for (int i=1; i<size; i++) {	
			PointF p = points.get(i);	// current point
			
			// first control point
			PointF p0 = points.get(i-1);	// previous point
			float x1 = p0.x + lX; 	
			float y1 = p0.y + lY;
	

			PointF p1 = points.get(i+1 < size ? i+1 : i);
			lX = (p1.x-p0.x)/2*SMOOTHNESS;
			lY = (p1.y-p0.y)/2*SMOOTHNESS;
			float x2 = p.x - lX;	
			float y2 = p.y - lY;

			// add line
			mPath.cubicTo(x1,y1,x2, y2, p.x, p.y);		
		}
		

		// draw path
		mPaint.setColor(CHART_COLOR);
		mPaint.setStyle(Style.STROKE);
		canvas.drawPath(mPath, mPaint);
		drawLabels(canvas);
		// draw area
		if (size > 0) {
			mPaint.setStyle(Style.FILL);
			mPaint.setColor((CHART_COLOR & 0xFFFFFF) | 0x10000000);
			mPath.lineTo(points.get(size-1).x, height+mBorder);	
			mPath.lineTo(points.get(0).x, height+mBorder);	
			mPath.close();
			canvas.drawPath(mPath, mPaint);
		}

		// draw circles
		mPaint.setColor(CHART_COLOR);
		mPaint.setStyle(Style.FILL_AND_STROKE);
		for (PointF point : points) {
			canvas.drawCircle(point.x, point.y, mCircleSize/2, mPaint);
		}
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(Color.WHITE);
		for (PointF point : points) {
			canvas.drawCircle(point.x, point.y, (mCircleSize-mStrokeSize)/2, mPaint);
		}

	}
	public void setXItem(String[] xItem){
		mXAxis = xItem;
	}

	public float getTextWidth(Paint textPaint, String text) {
		return textPaint.measureText(text);
	}
	private void drawLabels(Canvas canvas) {
		Paint labelPaint = new Paint();
		labelPaint.setColor(Color.rgb(0x71, 0x71, 0x71));
		labelPaint.setTextSize(21f);
		labelPaint.setAntiAlias(true);
		int width = (int) (getMeasuredWidth() - 2*mBorder);

		float labelY =30 + getMeasuredHeight() - 2*mBorder;
		float part = (float) width / (mXAxis.length - 1);

		for (int i = 0; i < mXAxis.length; i++) {
			String s = mXAxis[i];
			float centerX = width + part * i;
			float labelWidth = getTextWidth(labelPaint, s);
			float labelX;
			if (i == 0) {
				labelX = 0;
			} else if (i == mXAxis.length - 1) {
				labelX = width - labelWidth;
			} else {
				labelX = width_dX*i;
			}
			canvas.drawText(s, labelX, labelY, labelPaint);
		}

	}

}
