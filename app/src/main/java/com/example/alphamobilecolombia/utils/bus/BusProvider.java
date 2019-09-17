package com.example.alphamobilecolombia.utils.bus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class BusProvider {
    private BusProvider(){}

    private static Bus bus;

    public synchronized static Bus getBus(){

        if (bus == null){
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }


    public static class MainThreadBus extends Bus {

        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        post(event);
                    }
                });
            }
        }
    }
}

