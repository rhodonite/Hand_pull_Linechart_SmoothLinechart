package com.rhodonite.linechart_smoothlinechart;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends Activity {
    private SimpleLineChart mSimpleLineChart;
    private SmoothLineChart chart;
    private SmoothLineChartEquallySpaced chartES;
    Button bt;
    String[] xItem = {"0", "1", "2", "3", "4", "5", "6"};
    String[] yItem = {"", "", "12", "", "", "", "", "", "6", "", "", "", "", "", "0", "", "", "", "", "", "-6", "", "", "", "", "", "-12", "", ""};
    HashMap<Integer, Integer> pointMap = new HashMap();
    private int _xDelta;
    private int _yDelta;
    boolean is_true_1 = false;
    boolean is_true_2 = false;
    boolean is_true_3 = false;
    boolean is_true_4 = false;
    boolean is_true_5 = false;

    boolean is_ES_true_1 = false, is_ES_true_2 = false, is_ES_true_3 = false, is_ES_true_4 = false, is_ES_true_5 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /****/
        chart = (SmoothLineChart) findViewById(R.id.smoothChart);
        chart.setData(new PointF[]{
                new PointF(0, 20),
                new PointF(1, 20), // {x, y}
                new PointF(2, 20),
                new PointF(3, 20),
                new PointF(4, 20),
                new PointF(5, 20),
                new PointF(6, 20),
        });

        chart.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                final int X = (int) event.getRawX();
                final int Y = (int) event.getY();
                int[] Xpoint;
                int[] Ypoint;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("手點(x,y)", "(" + event.getX() + " , " + event.getY() + ")");
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }

                return true;
            }
        });

        /****/

        chartES = (SmoothLineChartEquallySpaced) findViewById(R.id.smoothChartES);
        chartES.setXItem(xItem);
        chartES.setData(new float[]{
                17,
                24,
                12,
                24,
                27,
                18,
                17
        });
        chartES.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                float X = (int) event.getX();
                float Y = (int) event.getY();
                int[] Xpoint;
                int[] Ypoint;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("手點(x,y)", "(" + event.getX() + " , " + event.getY() + ")");
                        Log.e("實際(x,y)", "(" + chartES.points.get(5).x + " , " + chartES.points.get(5).y + ")");
                        Log.e("實際(x,y)", "(" + chartES.points.get(6).x + " , " + chartES.points.get(6).y + ")");

                        if ((chartES.points.get(1).y >= event.getY() - 40 && chartES.points.get(1).x >= event.getX() - 50) &&
                                (chartES.points.get(1).y <= event.getY() + 40 && chartES.points.get(1).x <= event.getX() + 50)) {
                            is_ES_true_1 = true;
                        } else if ((chartES.points.get(2).y >= event.getY() - 40 && chartES.points.get(2).x >= event.getX() - 50) &&
                                (chartES.points.get(2).y <= event.getY() + 40 && chartES.points.get(2).x <= event.getX() + 50)) {
                            is_ES_true_2 = true;
                        } else if ((chartES.points.get(3).y >= event.getY() - 40 && chartES.points.get(3).x >= event.getX() - 50) &&
                                (chartES.points.get(3).y <= event.getY() + 40 && chartES.points.get(3).x <= event.getX() + 50)) {
                            is_ES_true_3 = true;
                        } else if ((chartES.points.get(4).y >= event.getY() - 40 && chartES.points.get(4).x >= event.getX() - 50) &&
                                (chartES.points.get(4).y <= event.getY() + 40 && chartES.points.get(4).x <= event.getX() + 50)) {
                            is_ES_true_4 = true;
                        } else if ((chartES.points.get(5).y >= event.getY() - 40 && chartES.points.get(5).x >= event.getX() - 50) &&
                                (chartES.points.get(5).y <= event.getY() + 40 && chartES.points.get(5).x <= event.getX() + 50)) {
                            is_ES_true_5 = true;
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        is_ES_true_1 = false;
                        is_ES_true_2 = false;
                        is_ES_true_3 = false;
                        is_ES_true_4 = false;
                        is_ES_true_5 = false;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int temp_Y = (int) ((chartES.mBorder_height - Y) / chartES.height_dY);
                        int temp_X = (int) ((X - chartES.get_mBorder) / chartES.width_dX);

                        if (is_ES_true_1) {
                            if (temp_Y < 30 && temp_Y >= 5)
                                chartES.setData(new float[]{
                                        chartES.mValues_temp[0],
                                        temp_Y,
                                        chartES.mValues_temp[2],
                                        chartES.mValues_temp[3],
                                        chartES.mValues_temp[4],
                                        chartES.mValues_temp[5],
                                        chartES.mValues_temp[6]
                                });

                        } else if (is_ES_true_2) {
                            if (temp_Y < 30 && temp_Y >= 5)
                                chartES.setData(new float[]{
                                        chartES.mValues_temp[0],
                                        chartES.mValues_temp[1],
                                        temp_Y,
                                        chartES.mValues_temp[3],
                                        chartES.mValues_temp[4],
                                        chartES.mValues_temp[5],
                                        chartES.mValues_temp[6]
                                });
                        } else if (is_ES_true_3) {
                            if (temp_Y < 30 && temp_Y >= 5)
                                chartES.setData(new float[]{
                                        chartES.mValues_temp[0],
                                        chartES.mValues_temp[1],
                                        chartES.mValues_temp[2],
                                        temp_Y,
                                        chartES.mValues_temp[4],
                                        chartES.mValues_temp[5],
                                        chartES.mValues_temp[6]
                                });
                        } else if (is_ES_true_4) {
                            if (temp_Y < 30 && temp_Y >= 5)
                                chartES.setData(new float[]{
                                        chartES.mValues_temp[0],
                                        chartES.mValues_temp[1],
                                        chartES.mValues_temp[2],
                                        chartES.mValues_temp[3],
                                        temp_Y,
                                        chartES.mValues_temp[5],
                                        chartES.mValues_temp[6]
                                });
                        } else if (is_ES_true_5) {
                            if (temp_Y < 30 && temp_Y >= 5)
                                chartES.setData(new float[]{
                                        chartES.mValues_temp[0],
                                        chartES.mValues_temp[1],
                                        chartES.mValues_temp[2],
                                        chartES.mValues_temp[3],
                                        chartES.mValues_temp[4],
                                        temp_Y,
                                        chartES.mValues_temp[6]
                                });
                        }

                        break;
                }

                return true;
            }
        });
        /****/

        mSimpleLineChart = (SimpleLineChart) findViewById(R.id.simpleLineChart);
        if (mSimpleLineChart == null)
            Log.e("wing", "null!!!!");
        mSimpleLineChart.setXItem(xItem);
        mSimpleLineChart.setYItem(yItem);
        for (int i = 0; i < xItem.length; i++) {
            pointMap.put(i, 12);
        }

        mSimpleLineChart.setData(pointMap);


        bt = (Button) findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartES.setData(new float[]{
                        12+5,
                        (int) (Math.random() * 24+5),
                        (int) (Math.random() * 24+5),
                        (int) (Math.random() * 24+5),
                        (int) (Math.random() * 24+5),
                        (int) (Math.random() * 24+5),
                        12+5
                });
            }
        });

        mSimpleLineChart.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                final int X = (int) event.getRawX();
                final int Y = (int) event.getY();
                int[] Xpoint;
                int[] Ypoint;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:

                        Ypoint = mSimpleLineChart.yPoints;
                        Xpoint = mSimpleLineChart.xPoints;
                        if ((Ypoint[pointMap.get(1)] >= event.getY() - 40 && Xpoint[1] >= event.getX() - 50) &&
                                (Ypoint[pointMap.get(1)] <= event.getY() + 40 && Xpoint[1] <= event.getX() + 50)) {
                            is_true_1 = true;
                        } else if ((Ypoint[pointMap.get(2)] >= event.getY() - 40 && Xpoint[2] >= event.getX() - 50) &&
                                (Ypoint[pointMap.get(2)] <= event.getY() + 40 && Xpoint[2] <= event.getX() + 50)) {
                            is_true_2 = true;
                        } else if ((Ypoint[pointMap.get(3)] >= event.getY() - 40 && Xpoint[3] >= event.getX() - 50) &&
                                (Ypoint[pointMap.get(3)] <= event.getY() + 40 && Xpoint[3] <= event.getX() + 50)) {
                            is_true_3 = true;
                        } else if ((Ypoint[pointMap.get(4)] >= event.getY() - 40 && Xpoint[4] >= event.getX() - 50) &&
                                (Ypoint[pointMap.get(4)] <= event.getY() + 40 && Xpoint[4] <= event.getX() + 50)) {
                            is_true_4 = true;
                        } else if ((Ypoint[pointMap.get(5)] >= event.getY() - 40 && Xpoint[5] >= event.getX() - 50) &&
                                (Ypoint[pointMap.get(5)] <= event.getY() + 40 && Xpoint[5] <= event.getX() + 50)) {
                            is_true_5 = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        is_true_1 = false;
                        is_true_2 = false;
                        is_true_3 = false;
                        is_true_4 = false;
                        is_true_5 = false;
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (is_true_1) {
                            int temp = (int) (event.getY() - 24) / mSimpleLineChart.Yadjust;
                            if (temp < 25 && temp >= 0) {
                                pointMap.put(0, 12);
                                pointMap.put(1, temp);
                                pointMap.put(2, pointMap.get(2));
                                pointMap.put(3, pointMap.get(3));
                                pointMap.put(4, pointMap.get(4));
                                pointMap.put(5, pointMap.get(5));
                                pointMap.put(6, 12);
                                mSimpleLineChart.setData(pointMap);
                            }

                        } else if (is_true_2) {
                            int temp = (int) (event.getY() - 24) / mSimpleLineChart.Yadjust;
                            if (temp < 25 && temp >= 0) {
                                pointMap.put(0, 12);
                                pointMap.put(1, pointMap.get(1));
                                pointMap.put(2, temp);
                                pointMap.put(3, pointMap.get(3));
                                pointMap.put(4, pointMap.get(4));
                                pointMap.put(5, pointMap.get(5));
                                pointMap.put(6, 12);
                                mSimpleLineChart.setData(pointMap);
                            }
                        } else if (is_true_3) {
                            int temp = (int) (event.getY() - 24) / mSimpleLineChart.Yadjust;
                            if (temp < 25 && temp >= 0) {
                                pointMap.put(0, 12);
                                pointMap.put(1, pointMap.get(1));
                                pointMap.put(2, pointMap.get(2));
                                pointMap.put(3, temp);
                                pointMap.put(4, pointMap.get(4));
                                pointMap.put(5, pointMap.get(5));
                                pointMap.put(6, 12);
                                mSimpleLineChart.setData(pointMap);
                            }
                        } else if (is_true_4) {
                            int temp = (int) (event.getY() - 24) / mSimpleLineChart.Yadjust;
                            if (temp < 25 && temp >= 0) {
                                pointMap.put(0, 12);
                                pointMap.put(1, pointMap.get(1));
                                pointMap.put(2, pointMap.get(2));
                                pointMap.put(3, pointMap.get(3));
                                pointMap.put(4, temp);
                                pointMap.put(5, pointMap.get(5));
                                pointMap.put(6, 12);
                                mSimpleLineChart.setData(pointMap);
                            }
                        } else if (is_true_5) {
                            int temp = (int) (event.getY() - 24) / mSimpleLineChart.Yadjust;
                            if (temp < 25 && temp >= 0) {
                                pointMap.put(0, 12);
                                pointMap.put(1, pointMap.get(1));
                                pointMap.put(2, pointMap.get(2));
                                pointMap.put(3, pointMap.get(3));
                                pointMap.put(4, pointMap.get(4));
                                pointMap.put(5, temp);
                                pointMap.put(6, 12);
                                mSimpleLineChart.setData(pointMap);
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

}
