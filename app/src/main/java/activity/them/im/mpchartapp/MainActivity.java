package activity.them.im.mpchartapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private Context mContext;

    //我的认识系学校需  郭故宫

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_money);
//        Entry 为每个点的类
//        DataSet 一组Y轴上面的数据
//        Linedata 整个Y轴的数据
//        initLineChart();

        mContext=this;
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

//        获取当前开发包版本号
        PayTask mPayVersion=new PayTask(this);
        Log.d("tag",mPayVersion.getVersion());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                Log.d("tag","支付宝");
                payInterrface();
                break;

            case R.id.button2:
                Log.d("tag","微信支付");
                break;
        }
    }

//    支付接口调用
    void payInterrface(){
        //订单信息
        final String orderInfo="[{\"goods_id\":\"STANDARD1026181538\",\"goods_name\":\"雪碧\",\"discount_amount\":\"100.00\",\"voucher_id\":\"2015102600073002039000002D5O\"}]";
        Runnable payRunnable=new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = 0xff;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread=new Thread(payRunnable);
        payThread.start();
    }
    /*9000 订单支付成功
    8000 正在处理中
    4000 订单支付失败
    6001 用户中途取消
    6002 网络连接出错*/
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, String> result = (Map<String, String>) msg.obj;
            Log.d("resultStatus",result.get("resultStatus"));
            Log.d("result",result.get("result"));
            Log.d("memo",result.get("memo"));
            Log.d("result", result.toString());
//                Toast.makeText(DemoActivity.this, result.getResult(),
//                        Toast.LENGTH_LONG).show();
        };
    };

//    private void initLineChart() {
//        LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
//        XAxis xAxis = mLineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTextSize(12f);
//        YAxis mLeftY= mLineChart.getAxisLeft();
//        mLeftY.setTextSize(12f);
//        YAxis mRight=mLineChart.getAxisRight();
//        mRight.setTextSize(12f);
//        ArrayList<String> xValues = new ArrayList<>();
//        Random random=new Random();
//        for (int i = 1; i < 8; i++) {
//            xValues.add("12/" + i);
//        }
//        ArrayList<Entry> yValue = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            yValue.add(new Entry(random.nextInt(200), i));
//        }
//        //构建一个LineDataSet 代表一组Y轴数据 （比如不同的彩票： 七星彩  双色球）
//        LineDataSet dataSet=new LineDataSet(yValue,"双色球");
//        dataSet.setColor(Color.RED);
//        dataSet.setValueTextSize(13f);
//        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
//        //设置包括的范围区域填充颜色
//        dataSet.setDrawFilled(true);
//        dataSet.setCircleSize(10f);
//        dataSet.setLineWidth(2f);
//        dataSet.setLabel("周期");
//
//        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
////        ArrayList<LineDataSet> dataSets = new ArrayList<>();
//        Description mDescScription=new Description();
//        mDescScription.setText("7天时间");
//        mLineChart.setDescription(mDescScription);
//        mDescScription.setTextSize(13f);
//
//
//        //构建一个LineData  将dataSets放入
//        LineData lineData=new LineData(yValue,dataSet);
//
//        //将数据插入
//        mLineChart.setData(lineData);
//        //设置是否可以触摸，如为false，则不能拖动，缩放等
//        mLineChart.setDragEnabled(true);
//    }


    private void initLineChart() {
        LineChart mLineChart = (LineChart) findViewById(R.id.lineChart);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        YAxis mLeftY = mLineChart.getAxisLeft();
        mLeftY.setTextSize(12f);
        YAxis mRight = mLineChart.getAxisRight();
        mRight.setTextSize(12f);
        ArrayList<String> xValues = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i < 8; i++) {
            xValues.add("12/" + i);
        }
        ArrayList<Entry> yValue = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            yValue.add(new Entry(random.nextInt(200), i));
        }
        //构建一个LineDataSet 代表一组Y轴数据 （比如不同的彩票： 七星彩  双色球）
        LineDataSet dataSet = new LineDataSet(yValue, "双色球");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextSize(13f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        //设置包括的范围区域填充颜色
        dataSet.setDrawFilled(true);
        dataSet.setCircleSize(10f);
        dataSet.setLineWidth(2f);
        //设置曲线为圆滑的线
        dataSet.setDrawCubic(true);
        dataSet.setLabel("周期");

        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
//        ArrayList<LineDataSet> dataSets = new ArrayList<>();
//        Description mDescScription=new Description();
//        mDescScription.setText("7天时间");
        mLineChart.setDescription("7天时间");
        mLineChart.setDescriptionTextSize(13f);

        //构建一个LineData  将dataSets放入
        LineData lineData = new LineData(xValues, dataSet);
        //将数据插入
        mLineChart.setData(lineData);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setDragEnabled(true);
    }


}

