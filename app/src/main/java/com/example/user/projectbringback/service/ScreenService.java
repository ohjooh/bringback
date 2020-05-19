package com.example.user.projectbringback.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.user.projectbringback.rcv.ScreenReceiver;

public class ScreenService extends Service {
    private ScreenReceiver mReceiver = null;
    String singer;
    String title;

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                Log.d("music info", "Title?"+title+", Singer?"+singer);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("singer", singer);
                Message musicMsg = Message.obtain();
                musicMsg.setData(bundle);
                msg.replyTo.send(musicMsg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mReceiver == null) {
            mReceiver = new ScreenReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mReceiver, filter);
        }

        title = intent.getStringExtra("title");
        singer = intent.getStringExtra("singer");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
    }

}
