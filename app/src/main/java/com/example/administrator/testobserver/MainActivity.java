package com.example.administrator.testobserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    static final String TAG = "MyObserver";
    Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        SampleRX sampleRX = new SampleRX();
//        sampleRX.testSampleRX();
        Log.d(TAG, "main thread id: "+Thread.currentThread().getId());
        //SchedulerRx.testSchedulerRx();
        SchedulerRx.test1();
        //SchedulerRx.test2();
    }
}
