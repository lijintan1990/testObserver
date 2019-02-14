package com.example.administrator.testobserver;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.graphics.Bitmap;
import android.util.Log;
import static com.example.administrator.testobserver.MainActivity.TAG;

public class SchedulerRx {
    public static void test1() {
        Observable.just("aaaaaaaa", "bbbbbb").map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) throws Exception {
                Log.d(TAG, "applay threadID "+Thread.currentThread().getId() + " " + s);
                return null;
            }
        }).subscribe(new Observer<Bitmap>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe threadID "+Thread.currentThread().getId());
            }

            @Override
            public void onNext(Bitmap value) {
                Log.d(TAG, "onNext threadID "+Thread.currentThread().getId());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError threadID "+Thread.currentThread().getId());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete threadID "+Thread.currentThread().getId());
            }
        });
    }

    public static void test2() {
        String [] strings = new String[2];
        strings[0] = new String("aaaaaa");
        strings[1] = new String("bbbbb");

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext "+value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        //Observable.just(strings[0], strings[1]).subscribe(observer);
        Observable.fromArray(strings).subscribe(observer);
    }

    public static void testFlatMap() {
        String [] strings = new String[2];
        strings[0] = new String("aaaaaa");
        strings[1] = new String("bbbbb");


    }


    public static void testSchedulerRx() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "subsribe "+Thread.currentThread().getId());//这里会在io线程池上面运行
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//指定 Subscriber 所运行在的线程, 即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程
                .subscribeOn(Schedulers.io())//指定 subscribe() 所发生的线程,指定 Subscriber 所运行在的线程。或者叫做事件消费的线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG,"onSubscribe "+Thread.currentThread().getId());//这些将会在主线程运行
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG,"onNext:"+value + " "+Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,"onError="+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,"onComplete()");
                    }
                });


//        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(123);
//                sleep(3000);
//                emitter.onNext(456);
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, integer + "");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//
//                    }
//                });
    }
}
