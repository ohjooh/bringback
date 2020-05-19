package com.example.user.projectbringback.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.service.ScreenService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class LockScreenActivity extends AppCompatActivity {
    private Messenger mService = null;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    TextView textCurrentTime;
    TextView textCurrentDate;
    TextView textMusicName;
    TextView textMusicSinger;
    TextView textUnlock;
    ImageView albumCover;
    GestureDetector gestureDetector;
    long nowTime = System.currentTimeMillis();
    public final int swipeMinDistance = 100;
    public final int swipeMaxDistance = 350;
    public final int swipeMinVelocity = 100;


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            String title = msg.getData().getString("title");
            String singer = msg.getData().getString("singer");
            textMusicName.setText(title);
            textMusicSinger.setText(singer);
            super.handleMessage(msg);
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            try {
                Message msg = Message.obtain();
                msg.replyTo = mMessenger;
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return gestureDetector.onTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        initViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
        bindService(new Intent(this, ScreenService.class), connection,
                Context.BIND_AUTO_CREATE);

        setCurrentTime();

        setCurrentDate();

        setRecordImage();

        setUnlockTextAnimation();

        gestureDetector = new GestureDetector(this, mOnGesture);

    }

    private void initViews() {
        textCurrentTime = findViewById(R.id.textCurrentTime);
        textCurrentDate = findViewById(R.id.textCurrentDate);
        textMusicName = findViewById(R.id.textMusicName);
        textMusicSinger = findViewById(R.id.textMusicSinger);
        textUnlock = findViewById(R.id.textUnlock);
        albumCover = findViewById(R.id.imageAlbum);
    }

    private void setUnlockTextAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        animation.setRepeatCount(3);
        animation.setRepeatMode(Animation.REVERSE);
        textUnlock.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textUnlock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void setRecordImage() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.lp)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(albumCover);
    }

    private void setCurrentTime() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> textCurrentTime.setText(getCurrentTime()));
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    private String getCurrentTime() {
        long nowTime = System.currentTimeMillis();
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.KOREA);
        return formatTime.format(new Date(nowTime));
    }

    private void setCurrentDate() {
        Date date = new Date(nowTime);
        SimpleDateFormat nowDate = new SimpleDateFormat("MM월 dd일 EE요일", Locale.KOREA);
        String formatDate = nowDate.format(date);
        textCurrentDate.setText(formatDate);
    }

    private GestureDetector.OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > swipeMaxDistance)
                return false;

            if (((e1.getX() - e2.getX()) > swipeMinDistance) && (Math.abs(velocityX) > swipeMinVelocity)) {
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
            } else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeMinVelocity) {
                finish();
                overridePendingTransition(0, android.R.anim.fade_out);
            }

            return false;
        }
    };

}
