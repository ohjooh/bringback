package com.example.user.projectbringback.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.annotation.Nullable;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;
import com.example.user.projectbringback.helper.PlaybackStatus;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerNotificationManager;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.user.projectbringback.data.Music.MUSICS;
import static com.example.user.projectbringback.data.Music.getBitmap;


public class MusicService extends Service implements EventListener, ControlDispatcher {
    public SimpleExoPlayer player;
    private PlayerNotificationManager playerNotificationManager;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaSessionConnector;
    private String status;
    final Context context = this;
    private final IBinder musicBind = new MusicBinder();

    @Override
    public boolean dispatchSetPlayWhenReady(Player player, boolean playWhenReady) {
        if (playWhenReady && status.equals(PlaybackStatus.PLAYING)) {
            player.setPlayWhenReady(true);
            return true;
        } else if (playWhenReady && status.equals(PlaybackStatus.PAUSED)) {
            player.setPlayWhenReady(false);
            return true;
        } else{
            player.setPlayWhenReady(false);
            return true;
        }
    }

    @Override
    public boolean dispatchSeekTo(Player player, int windowIndex, long positionMs) {
        player.seekTo(windowIndex, positionMs);
        return true;
    }

    @Override
    public boolean dispatchSetRepeatMode(Player player, int repeatMode) {
        if (repeatMode == Player.REPEAT_MODE_OFF) {
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
        } else if (repeatMode == Player.REPEAT_MODE_ONE) {
            player.setRepeatMode(Player.REPEAT_MODE_ALL);
        } else {
            player.setRepeatMode(Player.REPEAT_MODE_OFF);
        }
        return true;
    }

    @Override
    public boolean dispatchSetShuffleModeEnabled(Player player, boolean shuffleModeEnabled) {
        if (shuffleModeEnabled) {
            player.setShuffleModeEnabled(false);
        } else {
            player.setShuffleModeEnabled(true);
        }
        return true;
    }

    @Override
    public boolean dispatchStop(Player player, boolean reset) {
        return true;
    }

    class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    private MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            super.onPlay();
            player.setPlayWhenReady(true);
            Intent screenServiceIntent = new Intent(getApplicationContext(), ScreenService.class);
            startService(screenServiceIntent);
            if (player.getPlaybackState() == Player.STATE_ENDED)
                dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }

        @Override
        public void onPause() {
            super.onPause();
            player.setPlayWhenReady(false);
            dispatchSeekTo(player, player.getCurrentWindowIndex(), player.getCurrentPosition());
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            player.next();
            dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            player.previous();
            dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }

        @Override
        public void onStop() {
            super.onStop();
            player.stop();
            dispatchSeekTo(player, player.getCurrentWindowIndex(), C.TIME_UNSET);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        final Context context = this;
        player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        player.addListener(this);
        MediaSource mediaSource = createMediaSourceFromUri();
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build();
        player.setAudioAttributes(audioAttributes, true);
        createNotificationManager();
        playerNotificationManager.setPlayer(player);

        mediaSession = new MediaSessionCompat(context, "audio");
        mediaSession.setActive(true);
        mediaSession.setCallback(mediaSessionCallback);
        playerNotificationManager.setMediaSessionToken(mediaSession.getSessionToken());

        mediaSessionConnector = new MediaSessionConnector(mediaSession);
        mediaSessionConnector.setQueueNavigator(new TimelineQueueNavigator(mediaSession) {
            @Override
            public MediaDescriptionCompat getMediaDescription(Player player, int windowIndex) {
                try {
                    return Music.getMediaDescription(context, MUSICS[windowIndex]);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                return null;
            }
        });
        mediaSessionConnector.setPlayer(player);
        status = PlaybackStatus.PLAYING;
    }

    public MediaSource createMediaSourceFromUri() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)));
        ProgressiveMediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(dataSourceFactory);
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        for (Music music : MUSICS) {
            MediaSource mediaSource = mediaSourceFactory.createMediaSource(music.uri);
            concatenatingMediaSource.addMediaSource(mediaSource);
        }

        return concatenatingMediaSource;
    }

    private void createNotificationManager() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                    context,
                    "audio channel",
                    NotificationManager.IMPORTANCE_DEFAULT,
                    R.string.app_name,
                    1,
                    new PlayerNotificationManager.MediaDescriptionAdapter() {
                        @Override
                        public String getCurrentContentTitle(Player player) {
                            return MUSICS[player.getCurrentWindowIndex()].name;
                        }

                        @Nullable
                        @Override
                        public PendingIntent createCurrentContentIntent(Player player) {
                            return null;
                        }

                        @Nullable
                        @Override
                        public String getCurrentContentText(Player player) {
                            return MUSICS[player.getCurrentWindowIndex()].album;
                        }

                        @Nullable
                        @Override
                        public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                            callback.onBitmap(getBitmap(context, MUSICS[player.getCurrentWindowIndex()].bitmapResource));
                            return getBitmap(context, MUSICS[player.getCurrentWindowIndex()].bitmapResource);
                        }
                    },
                    new PlayerNotificationManager.NotificationListener() {
                        @Override
                        public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                            stopSelf();
                        }

                        @Override
                        public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                            startForeground(notificationId, notification);
                        }
                    }
            );
        }else{
            playerNotificationManager = new PlayerNotificationManager(
                    context,
                    "audio channel",
                    1,
                    new PlayerNotificationManager.MediaDescriptionAdapter() {
                        @Override
                        public String getCurrentContentTitle(Player player) {
                            return MUSICS[player.getCurrentWindowIndex()].name;
                        }

                        @Nullable
                        @Override
                        public PendingIntent createCurrentContentIntent(Player player) {
                            return null;
                        }

                        @Nullable
                        @Override
                        public String getCurrentContentText(Player player) {
                            return MUSICS[player.getCurrentWindowIndex()].album;
                        }

                        @Nullable
                        @Override
                        public Bitmap getCurrentLargeIcon(Player player, PlayerNotificationManager.BitmapCallback callback) {
                            callback.onBitmap(getBitmap(context, MUSICS[player.getCurrentWindowIndex()].bitmapResource));
                            return getBitmap(context, MUSICS[player.getCurrentWindowIndex()].bitmapResource);
                        }
                    },
                    new PlayerNotificationManager.NotificationListener() {
                        @Override
                        public void onNotificationCancelled(int notificationId, boolean dismissedByUser) {
                            stopSelf();
                        }

                        @Override
                        public void onNotificationPosted(int notificationId, Notification notification, boolean ongoing) {
                            startForeground(notificationId, notification);
                        }
                    });
        }
    }


    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                status = PlaybackStatus.LOADING;
                break;
            case Player.STATE_READY:
                status = playWhenReady ? PlaybackStatus.PLAYING : PlaybackStatus.PAUSED;
                break;
            case Player.STATE_ENDED:
                status = PlaybackStatus.STOPPED;
                break;
            case Player.STATE_IDLE:
            default:
                status = PlaybackStatus.IDLE;
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {
    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent screenServiceIntent = new Intent(getApplicationContext(), ScreenService.class);
        stopService(screenServiceIntent);
        player.setPlayWhenReady(false);
        mediaSession.release();
        mediaSessionConnector.setPlayer(null);
        playerNotificationManager.setPlayer(null);
        playerNotificationManager = null;
        player.release();
        player.removeListener(this);
        player = null;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent screenServiceIntent = new Intent(getApplicationContext(), ScreenService.class);

        String message = intent.getStringExtra("playMode");
        if (message == null)
            message = "prepare";
        switch (message) {
            case "play": {
                player.setPlayWhenReady(true);
                String title =  MUSICS[player.getCurrentWindowIndex()].name;
                String singer = MUSICS[player.getCurrentWindowIndex()].singer;
                screenServiceIntent.putExtra("title", title);
                screenServiceIntent.putExtra("singer", singer);
                startService(screenServiceIntent);
                break;
            }
            case "pause":
                player.setPlayWhenReady(false);
                stopService(screenServiceIntent);
                break;
            case "previous": {
                player.previous();
                String title =  MUSICS[player.getCurrentWindowIndex()].name;
                String singer = MUSICS[player.getCurrentWindowIndex()].singer;
                screenServiceIntent.putExtra("title", title);
                screenServiceIntent.putExtra("singer", singer);
                startService(screenServiceIntent);
                break;
            }
            case "next": {
                player.next();
                String title =  MUSICS[player.getCurrentWindowIndex()].name;
                String singer = MUSICS[player.getCurrentWindowIndex()].singer;
                screenServiceIntent.putExtra("title", title);
                screenServiceIntent.putExtra("singer", singer);
                startService(screenServiceIntent);
                break;
            }
            case "shuffle": {
                boolean isShuffling = player.getShuffleModeEnabled();
                player.setShuffleModeEnabled(!isShuffling);
                break;
            }
            case "repeat": {
                int repeatMode = player.getRepeatMode();
                if (repeatMode == Player.REPEAT_MODE_ALL)
                    player.setRepeatMode(Player.REPEAT_MODE_OFF);
                else
                    player.setRepeatMode(repeatMode + 1);
                break;
            }
            default: {
                player.setPlayWhenReady(false);
                stopService(screenServiceIntent);
                break;
            }
        }

        return START_STICKY;
    }


    public boolean isPlaying() {
        return this.status.equals(PlaybackStatus.PLAYING);
    }

}
