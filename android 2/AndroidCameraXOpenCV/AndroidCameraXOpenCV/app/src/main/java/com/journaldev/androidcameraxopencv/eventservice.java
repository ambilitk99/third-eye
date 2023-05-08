package com.journaldev.androidcameraxopencv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class eventservice extends Service {
    public eventservice() {
    }


    Runnable rn= new Runnable() {
        @Override
        public void run() {



        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}