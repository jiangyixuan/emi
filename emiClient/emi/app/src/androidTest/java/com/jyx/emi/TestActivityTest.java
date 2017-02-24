package com.jyx.emi;

import android.content.Intent;
import android.os.SystemClock;
import android.renderscript.Sampler;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TestActivityTest extends InstrumentationTestCase{

/*
    private TestActivity testActivity=null;
    private Button button=null;
    private TextView text=null;

    */
/**
     * 初始化设置
     *//*

    @Override
    protected void setUp(){
        try {
            super.setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent=new Intent();
        intent.setClassName("com.jyx.emi.activity.TestActivity", TestActivity.class.getName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        testActivity=(TestActivity)getInstrumentation().startActivitySync(intent);
        text = (TextView) testActivity.findViewById(R.id.text);
        button = (Button) testActivity.findViewById(R.id.button);
    }

    */
/**
     * 垃圾清理与资源回收
     *//*

    @Override
    protected void tearDown(){
        testActivity.finish();
        try {
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
/**
     * 活动功能测试
     *//*

    public void testActivity()
    {
        Log.i("asdasd","test the activity");
        SystemClock.sleep(1500);
        getInstrumentation().runOnMainSync(new PerformCklick(button));
        SystemClock.sleep(3000);
        assertEquals("Hello Android",text.getText().toString());
    }
    */
/**
     * 模拟按钮点击的接口
     *//*

    private class PerformCklick implements Runnable
    {
        private Button btn;

        public PerformCklick(Button button)
        {
            btn=button;
        }
        @Override
        public void run() {
            btn.performClick();
        }
    }
    */
/**
     * 测试类中的方法
     *//*

    public void testAdd()
    {
        String tag="testAdd";
        Log.i("asdasd","test the method");
        int test=testActivity.add(1,1);
        assertEquals(2,test);
    }
*/

}
