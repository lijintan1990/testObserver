package com.example.administrator.testobserver;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SampleRX {
    static final String TAG = "MyObserver";
    Disposable mDisposable;

    void testSampleRX() {
        Observable novel = createObserable();
        Observer<String> reader = createObserver();
        novel.subscribe(reader);
    }

    //被观察者
    Observable createObserable() {
        //被观察者
        Observable novel= Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });

        return novel;
    }

    //观察者
    Observer<String> createObserver() {
        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String value) {
                if ("2".equals(value)) {
                    mDisposable.dispose();
                    return;
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError="+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };

        return  reader;
    }
}
