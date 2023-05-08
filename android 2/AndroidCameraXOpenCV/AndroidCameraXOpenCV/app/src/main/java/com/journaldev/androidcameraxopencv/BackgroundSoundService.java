package com.journaldev.androidcameraxopencv;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jorgesys on 02/02/2015.
 */

/* Add declaration of this service into the AndroidManifest.xml inside application tag*/

public class BackgroundSoundService extends Service {

    private static final String TAG = "BackgroundSoundService";
    MediaPlayer player;

    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "onBind()" );
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mediaurl= sh.getString("murl","https://samplelib.com/lib/preview/mp3/sample-3s.mp3");


        player = MediaPlayer.create(this, Uri.parse(mediaurl));
        player.setLooping(false); // Set looping
        player.setVolume(100,100);
        Toast.makeText(this, "Service started...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service started...");

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        Toast.makeText(this, "Service stopped...", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }
}
