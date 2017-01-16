package com.rhodonite.linechart_smoothlinechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;


public class SimpleLineChart extends View {

    private int mWidth, mHeight;

    private float mYAxisFontSize = 24;

    private int mLineColor = Color.parseColor("#00BCD4");

    private float mStrokeWidth = 8.0f;

    private HashMap<Integer, Integer> mPointMap;

    private float mPointRadius = 10;

    private String mNoDataMsg = "no data";

    private String[] mXAxis = {};

    private String[] mYAxis = {};

    public SimpleLineChart(Context context) {
        this(context, null);
    }

    public SimpleLineChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLineChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Path fillPath = new Path(); ;
    public int Yadjust=0;
    int[] yPoints;
    int[] xPoints;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else if(widthMode == MeasureSpec.AT_MOST){
            throw new IllegalArgumentException("width must be EXACTLY,you should set like android:width=\"200dp\"");
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else if(widthMeasureSpec == MeasureSpec.AT_MOST){

            throw new IllegalArgumentException("height must be EXACTLY,you should set like android:height=\"200dp\"");
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //画坐标线的轴
        Paint axisPaint = new Paint();
        axisPaint.setTextSize(mYAxisFontSize);
        axisPaint.setColor(Color.parseColor("#3F51B5"));

        if (mPointMap == null || mPointMap.size() == 0) {
            int textLength = (int) axisPaint.measureText(mNoDataMsg);
            canvas.drawText(mNoDataMsg, mWidth/2 - textLength/2, mHeight/2, axisPaint);
        } else {
            yPoints = new int[mYAxis.length];
            int yInterval = (int) ((mHeight - mYAxisFontSize - 2) / (mYAxis.length));
            Yadjust = yInterval;
            Paint.FontMetrics fm = axisPaint.getFontMetrics();
            int yItemHeight = (int) Math.ceil(fm.descent - fm.ascent);


            for (int i = 0; i < mYAxis.length; i++) {
                canvas.drawText(mYAxis[i], 0, mYAxisFontSize + i * yInterval, axisPaint);
                yPoints[i] = (int) (mYAxisFontSize + i * yInterval);
            }
            xPoints = new int[mXAxis.length];
            int xItemX = (int) axisPaint.measureText(mYAxis[1]);
            int xOffset = 50;
            int xInterval = (int) ((mWidth - xOffset) / (mXAxis.length));
            int xItemY = (int) (mYAxisFontSize + mYAxis.length * yInterval);

            for (int i = 0; i < mXAxis.length; i++) {
                canvas.drawText(mXAxis[i], i * xInterval + xItemX + xOffset, xItemY, axisPaint);
                xPoints[i] = (int) (i * xInterval + xItemX + axisPaint.measureText(mXAxis[i]) / 2 + xOffset + 10);
            }
            Paint pointPaint = new Paint();

            pointPaint.setColor(mLineColor);

            Paint linePaint = new Paint();

            linePaint.setColor(mLineColor);
            linePaint.setAntiAlias(true);
            linePaint.setStrokeWidth(mStrokeWidth);
            pointPaint.setStyle(Paint.Style.FILL);


            for (int i = 0; i < mXAxis.length; i++) {
                if (mPointMap.get(i) == null) {
                    throw new IllegalArgumentException("PointMap has incomplete data!");
                }
                canvas.drawCircle(xPoints[i], yPoints[mPointMap.get(i)], mPointRadius, pointPaint);
                if (i > 0) {
                    canvas.drawLine(xPoints[i - 1], yPoints[mPointMap.get(i - 1)], xPoints[i], yPoints[mPointMap.get(i)], linePaint);

                }
            }


            /****/

            fillPath.reset();
            buildPath(fillPath);
            Paint p = new Paint();
            fillPath.lineTo(xPoints[xPoints.length-1],yPoints[yPoints.length-1]);
            fillPath.lineTo(xPoints[0], yPoints[yPoints.length-1]);
            fillPath.lineTo(xPoints[0], yPoints[mPointMap.get(mPointMap.size()-1)]);
            fillPath.close();
            p.setShader(new LinearGradient(0, 0, 0, getHeight(), 0xFFFFFF00, 0x00000000, Shader.TileMode.CLAMP));

            canvas.drawPath(fillPath, p); // 把波浪填滿

        }
    }
    private void buildPath(Path path) {
        //Important!
        path.reset();

        path.moveTo(xPoints[0], yPoints[mPointMap.get(0)]);
        int pointSize = mPointMap.size();

        for (int i = 0; i < xPoints.length ; i++) {

            float controlX = xPoints[i];
            float controlY =yPoints[mPointMap.get(i)];

            path.lineTo(controlX, controlY);
        }

        path.lineTo(xPoints[pointSize - 1], yPoints[mPointMap.get(pointSize - 1)]);
    }

    public void setData(HashMap<Integer,Integer> data){
        mPointMap = data;
        invalidate();
        //mSimpleLineChart.setOnTouchListener(this);
    }
    public void setYItem(String[] yItem){
        mYAxis = yItem;
    }

    public void setXItem(String[] xItem){
        mXAxis = xItem;
    }

    public void setLineColor(int color){
        mLineColor = color;
        invalidate();
    }


}
