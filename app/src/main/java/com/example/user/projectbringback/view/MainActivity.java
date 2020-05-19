package com.example.user.projectbringback.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.service.MusicService;
import com.example.user.projectbringback.service.PlayerManager;
import com.example.user.projectbringback.service.ScreenService;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import static com.example.user.projectbringback.data.Music.MUSICS;

public class MainActivity extends AppCompatActivity {
    private LinearLayout bottomSheet;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout bottomPlayerBar;
    private BottomNavigationView navigation;
    public PlayerControlView playerView;
    private boolean IS_LIKE = false;
    private PlayerManager playerManager;

    //bottomSheet's widget
    private ImageButton mBtnPlay;
    private ImageButton mBtnPrevious;
    private ImageButton mBtnNext;
    private TextView playerBarTitle;
    private TextView playerBarSinger;
    private TextView playerBarAlbum;
    private ImageView playerBarAlbumArt;

    //player's widget
    private ImageView albumArt;
    private TextView textTitle;
    private TextView textAlbum;
    private TextView textSinger;
    private ImageButton exo_play;
    private ImageButton exo_pause;
    private ImageButton exo_shuffle;
    private ImageButton exo_repeat_toggle;
    private ImageButton mBtnClosePlayer;
    private ImageButton mBtnMore;
    private ImageButton mBtnPlaylist;
    private SeekBar mVolumeBar;

    private String userId;
    private String userTaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = getIntent().getStringExtra("Id");
        userTaste = getIntent().getStringExtra("Taste");

        initViews();

        playerManager = new PlayerManager(this, playerView);

        initBottomSheet();

        initPlayer();

        updatePlayingMusicData();

        loadFragment(new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.browser:
                    fragment = new BrowserFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.playlist:
                    fragment = new PlaylistFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.sharingTastes:
                    fragment = new SharingTastesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        });
    }

    private void initViews() {
        bottomSheet = findViewById(R.id.bottomsheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomPlayerBar = findViewById(R.id.playerBar);

        playerView = findViewById(R.id.playerView);
        navigation = findViewById(R.id.navigation);

        //bottomSheet's view
        mBtnPrevious = findViewById(R.id.playerBarBtnPrevious);
        mBtnPlay = findViewById(R.id.playerBarBtnPlay);
        mBtnNext = findViewById(R.id.playerBarBtnNext);
        playerBarTitle = findViewById(R.id.playerBarTitle);
        playerBarSinger = findViewById(R.id.playerBarSinger);
        playerBarAlbum = findViewById(R.id.playerBarAlbum);
        playerBarAlbumArt = findViewById(R.id.playerBarAlbumArt);

        //player's view
        albumArt = findViewById(R.id.albumArt);
        textTitle = findViewById(R.id.textTitle);
        textAlbum = findViewById(R.id.textAlbum);
        textSinger = findViewById(R.id.textSinger);

        final ClickListener clickListener = new ClickListener();
        findViewById(R.id.exo_prev).setOnClickListener(clickListener);
        findViewById(R.id.exo_next).setOnClickListener(clickListener);
        exo_shuffle = findViewById(R.id.exo_shuffle);
        exo_shuffle.setOnClickListener(clickListener);
        exo_repeat_toggle = findViewById(R.id.exo_repeat_toggle);
        exo_repeat_toggle.setOnClickListener(clickListener);
        exo_play = findViewById(R.id.exo_play);
        exo_play.setOnClickListener(clickListener);
        exo_pause = findViewById(R.id.exo_pause);
        exo_pause.setOnClickListener(clickListener);
        mBtnClosePlayer = findViewById(R.id.btnClosePlayer);
        mBtnMore = findViewById(R.id.btnMore);
        mBtnPlaylist = findViewById(R.id.btnPlaylist);
        mVolumeBar = findViewById(R.id.volumeBar);
    }

    public void updatePlayingMusicData() {
        int musicIndex = playerManager.getPlayer().getCurrentWindowIndex();
        playerBarTitle.setText(MUSICS[musicIndex].name);
        playerBarSinger.setText(MUSICS[musicIndex].singer);
        playerBarAlbum.setText(MUSICS[musicIndex].album);
        playerBarAlbumArt.setImageResource(MUSICS[musicIndex].bitmapResource);
        albumArt.setImageResource(MUSICS[musicIndex].bitmapResource);
        textTitle.setText(MUSICS[musicIndex].name);
        textAlbum.setText(MUSICS[musicIndex].album);
        textSinger.setText(MUSICS[musicIndex].singer);
    }

    public void updatePlayingMusicData(int index) {
        playerBarTitle.setText(MUSICS[index].name);
        playerBarSinger.setText(MUSICS[index].singer);
        playerBarAlbum.setText(MUSICS[index].album);
        playerBarAlbumArt.setImageResource(MUSICS[index].bitmapResource);
        albumArt.setImageResource(MUSICS[index].bitmapResource);
        textTitle.setText(MUSICS[index].name);
        textAlbum.setText(MUSICS[index].album);
        textSinger.setText(MUSICS[index].singer);
    }

    private void initBottomSheet() {
        playerView.setShowTimeoutMs(0);

        mBtnPrevious.setOnClickListener(view -> {
            if (playerManager.getPlayer().getPreviousWindowIndex() == -1) {
                playerManager.getPlayer().seekTo(MUSICS.length - 1, C.TIME_UNSET);
                updatePlayingMusicData();
            } else {
                Intent intent = new Intent(this, MusicService.class);
                intent.putExtra("playMode", "previous");
                playerManager.bind(intent);
                if (playerManager.getPlayer().getShuffleModeEnabled()) {
                    updatePlayingMusicData();
                } else {
                    updatePlayingMusicData(playerManager.getPlayer().getPreviousWindowIndex());
                }
            }
        });

        //exoplayer에서 play 버튼 눌렀을 때 bottomsheet의 이 버튼도 이미지가 변경돼야 함.
        mBtnPlay.setOnClickListener(view -> {
            if (playerManager.isPlaying()) {
                Intent intent = new Intent(this, MusicService.class);
                intent.putExtra("playMode", "pause");
                playerManager.bind(intent);
                mBtnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                exo_play.setVisibility(View.VISIBLE);
                exo_pause.setVisibility(View.GONE);
            } else {
                Intent intent = new Intent(this, MusicService.class);
                intent.putExtra("playMode", "play");
                playerManager.bind(intent);
                mBtnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                exo_play.setVisibility(View.GONE);
                exo_pause.setVisibility(View.VISIBLE);
                updatePlayingMusicData();
            }
        });

        mBtnNext.setOnClickListener(view -> {
            if (playerManager.getPlayer().getNextWindowIndex() == -1) {
                playerManager.getPlayer().seekTo(0, C.TIME_UNSET);
                updatePlayingMusicData(0);
            } else {
                Intent intent = new Intent(this, MusicService.class);
                intent.putExtra("playMode", "next");
                playerManager.bind(intent);
                if (playerManager.getPlayer().getShuffleModeEnabled()) {
                    updatePlayingMusicData();
                } else {
                    updatePlayingMusicData(playerManager.getPlayer().getNextWindowIndex());
                }
            }
        });

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int state) {
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomPlayerBar.setVisibility(View.VISIBLE);
                    showBottomNavigationView(navigation);
                }

                if (state == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomPlayerBar.setVisibility(View.GONE);
                    hideBottomNavigationView(navigation);
                }

                if (state == BottomSheetBehavior.STATE_SETTLING) {
                    bottomPlayerBar.setVisibility(View.GONE);
                    hideBottomNavigationView(navigation);
                }

                if (state == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomPlayerBar.setVisibility(View.GONE);
                    hideBottomNavigationView(navigation);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });

        bottomPlayerBar.setOnClickListener(view -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
    }

    private void initPlayer() {
        final ImageButton mBtnLike = findViewById(R.id.btnLike);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert audioManager != null;
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nowVolumeValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mVolumeBar.setMax(max);
        mVolumeBar.setProgress(nowVolumeValue);

        mVolumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBtnLike.setOnClickListener(view -> {
            if (!IS_LIKE) {
                mBtnLike.setImageResource(R.drawable.ic_star_press_black_24dp);
                IS_LIKE = true;
            } else {
                mBtnLike.setImageResource(R.drawable.ic_star_black_24dp);
                IS_LIKE = false;
            }
        });

        mBtnMore.setOnClickListener(view -> setPlayerEtcDialog());

        mBtnPlaylist.setOnClickListener(view -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            navigation.setSelectedItemId(R.id.playlist);
            loadFragment(new PlaylistFragment());
        });

        mBtnClosePlayer.setOnClickListener(view -> mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

        Bundle args = new Bundle();
        args.putString("Id", userId);
        args.putString("Taste", userTaste);
        fragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setPlayerEtcDialog(){
        final CharSequence[] moreItems = {"플레이리스트에 추가하기", "보관함에 추가하기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        builder.setItems(moreItems, (dialogInterface, i) -> {
            switch(i){
                case 0:
                    //add playlist this search_song
                    Toast.makeText(MainActivity.this, "add playlist this search_song", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    //add home's saved search_song list
                    Toast.makeText(MainActivity.this, "add home's saved search_song list", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(0);
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.animate().translationY(view.getHeight());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent screenServiceIntent = new Intent(getApplicationContext(), ScreenService.class);
        stopService(screenServiceIntent);
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), MusicService.class);
            switch (view.getId()) {
                case R.id.exo_play:
                    if (playerManager.isPlaying()) {
                        intent.putExtra("playMode", "pause");
                        playerManager.bind(intent);
                        mBtnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        exo_play.setVisibility(View.VISIBLE);
                        exo_pause.setVisibility(View.GONE);
                        updatePlayingMusicData();
                    } else {
                        intent.putExtra("playMode", "play");
                        playerManager.bind(intent);
                        mBtnPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                        exo_play.setVisibility(View.GONE);
                        exo_pause.setVisibility(View.VISIBLE);
                        updatePlayingMusicData();
                    }
                    break;
                case R.id.exo_pause:
                    intent.putExtra("playMode", "pause");
                    playerManager.bind(intent);
                    mBtnPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    exo_play.setVisibility(View.VISIBLE);
                    exo_pause.setVisibility(View.GONE);
                    updatePlayingMusicData();
                    break;
                case R.id.exo_prev:
                    if (playerManager.getPlayer().getPreviousWindowIndex() == -1) {
                        playerManager.getPlayer().seekTo(MUSICS.length - 1, C.TIME_UNSET);
                        updatePlayingMusicData();
                    } else {
                        intent.putExtra("playMode", "previous");
                        playerManager.bind(intent);
                        if (playerManager.getPlayer().getShuffleModeEnabled()) {
                            updatePlayingMusicData();
                        } else {
                            updatePlayingMusicData(playerManager.getPlayer().getPreviousWindowIndex());
                        }
                    }
                    break;
                case R.id.exo_next:
                    if (playerManager.getPlayer().getNextWindowIndex() == -1) {
                        playerManager.getPlayer().seekTo(0, C.TIME_UNSET);
                        updatePlayingMusicData(0);
                    } else {
                        intent.putExtra("playMode", "next");
                        playerManager.bind(intent);
                        if (playerManager.getPlayer().getShuffleModeEnabled()) {
                            updatePlayingMusicData();
                        } else {
                            updatePlayingMusicData(playerManager.getPlayer().getNextWindowIndex());
                        }
                    }
                    break;
                case R.id.exo_shuffle:
                    intent.putExtra("playMode", "shuffle");
                    playerManager.bind(intent);
                    if (playerManager.getPlayer().getShuffleModeEnabled()) {
                        exo_shuffle.setImageResource(R.drawable.ic_shuffle_24px);
                    } else {
                        exo_shuffle.setImageResource(R.drawable.ic_shuffle_press_24px);
                    }
                    break;
                case R.id.exo_repeat_toggle:
                    intent.putExtra("playMode", "repeat");
                    playerManager.bind(intent);
                    if (playerManager.getPlayer().getRepeatMode() == Player.REPEAT_MODE_OFF) {
                        exo_repeat_toggle.setImageResource(R.drawable.ic_repeat_24px);
                    } else if (playerManager.getPlayer().getRepeatMode() == Player.REPEAT_MODE_ONE) {
                        exo_repeat_toggle.setImageResource(R.drawable.ic_repeat_one_24dp);
                    } else if (playerManager.getPlayer().getRepeatMode() == Player.REPEAT_MODE_ALL) {
                        exo_repeat_toggle.setImageResource(R.drawable.ic_repeat_press_24px);
                    }
                    break;
            }
        }
    }
}
