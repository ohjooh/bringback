package com.example.user.projectbringback.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class PlayerManager {
    private MusicService service;
    private SimpleExoPlayer player;
    private Context context;

    public PlayerManager(Context context, PlayerControlView playerView) {
        this.context = context;
        player = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        MediaSource mediaSource = createMediaSourceFromUri(context);
        player.addListener(getService());
        player.setPlayWhenReady(false);
        player.prepare(mediaSource);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .build();
        player.setAudioAttributes(audioAttributes, true);
        playerView.setPlayer(player);
        playerView.setControlDispatcher(getService());
        playerView.setRepeatToggleModes(Player.REPEAT_MODE_ONE | Player.REPEAT_MODE_ALL);
        playerView.setShowShuffleButton(true);
        playerView.setShowTimeoutMs(0);
    }

    public SimpleExoPlayer getPlayer(){
        if(service == null){
            service = getService();
            service.player = player;
            service.onPlayerStateChanged(false, Player.STATE_BUFFERING);
        }
        return service.player;
    }

    private MusicService getService() {
        return new MusicService();
    }

    public boolean isPlaying() {
        if (service == null) {
            getService();
            return false;
        }
        return service.isPlaying();
    }

    public void bind(Intent intent) {
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Util.startForegroundService(context, intent);
    }

    private MediaSource createMediaSourceFromUri(Context context) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, context.getString(R.string.app_name)));
        ProgressiveMediaSource.Factory mediaSourceFactory = new ProgressiveMediaSource.Factory(dataSourceFactory);
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();

        for (Music music : MUSICS) {
            MediaSource mediaSource = mediaSourceFactory.createMediaSource(music.uri);
            concatenatingMediaSource.addMediaSource(mediaSource);
        }

        return concatenatingMediaSource;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            service = ((MusicService.MusicBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

}
