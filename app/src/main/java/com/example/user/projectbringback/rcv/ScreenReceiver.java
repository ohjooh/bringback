package com.example.user.projectbringback.rcv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.user.projectbringback.view.LockScreenActivity;

import java.util.Objects;

public class ScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String singer = intent.getStringExtra("singer");
        Log.d("ScreenReceiver Info","Title ? "+title+", Singer ? "+singer);
        if(Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_SCREEN_OFF)){
            Intent intentLockScreen = new Intent(context, LockScreenActivity.class);
            intentLockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentLockScreen);
        }
    }
}
