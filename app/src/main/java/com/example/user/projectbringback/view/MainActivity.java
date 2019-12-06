package com.example.user.projectbringback.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.user.projectbringback.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout bottomPlayerBar;
    private BottomNavigationView navigation;
    boolean IS_LIKE = false;
    boolean IS_SHUFFLE = false;
    int IS_REPEAT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        LinearLayout bottomSheet = findViewById(R.id.bottomsheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomPlayerBar = findViewById(R.id.playerBar);

        initBottomSheet();

        initPlayer();

        loadFragment(new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
            }
        });
    }

    private void initBottomSheet() {
        ImageButton mBtnPrevious = findViewById(R.id.playerBarBtnPrevious);
        ImageButton mBtnPlay = findViewById(R.id.playerBarBtnPlay);
        ImageButton mBtnNext = findViewById(R.id.playerBarBtnNext);

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

        bottomPlayerBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    private void initPlayer() {
        ImageButton mBtnPlayerNext = findViewById(R.id.btnNext);
        ImageButton mBtnPlayerPlay = findViewById(R.id.btnPlay);
        ImageButton mBtnPlayerPrevious = findViewById(R.id.btnPrevious);
        final ImageButton mBtnPlayerShuffle = findViewById(R.id.btnShuffle);
        final ImageButton mBtnPlayerRepeat = findViewById(R.id.btnRepeat);
        final ImageButton mBtnLike = findViewById(R.id.btnLike);
        ImageButton mBtnClosePlayer = findViewById(R.id.btnClosePlayer);
        ImageButton mBtnMore = findViewById(R.id.btnMore);
        ImageButton mBtnPlaylist = findViewById(R.id.btnPlaylist);
        SeekBar mVolumeBar = findViewById(R.id.volumeBar);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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



        mBtnPlayerShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IS_SHUFFLE){
                    mBtnPlayerShuffle.setImageResource(R.drawable.ic_shuffle_press_24px);
                    IS_SHUFFLE = true;
                }else{
                    mBtnPlayerShuffle.setImageResource(R.drawable.ic_shuffle_24px);
                    IS_SHUFFLE = false;
                }
            }
        });

        //0 -> 설정x 1 -> 전체반복 2 -> 한곡반복
        mBtnPlayerRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IS_REPEAT == 0){
                    mBtnPlayerRepeat.setImageResource(R.drawable.ic_repeat_press_24px);
                    IS_REPEAT = 1;
                }else if(IS_REPEAT == 1){
                    mBtnPlayerRepeat.setImageResource(R.drawable.ic_repeat_one_24dp);
                    IS_REPEAT = 2;
                }else{
                    mBtnPlayerRepeat.setImageResource(R.drawable.ic_repeat_24px);
                    IS_REPEAT = 0;
                }

            }
        });

        mBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!IS_LIKE){
                    mBtnLike.setImageResource(R.drawable.ic_star_press_black_24dp);
                    IS_LIKE = true;
                }else{
                    mBtnLike.setImageResource(R.drawable.ic_star_black_24dp);
                    IS_LIKE = false;
                }
            }
        });

        mBtnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPlayerEtcDialog();
            }
        });

        mBtnPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                navigation.setSelectedItemId(R.id.playlist);
                loadFragment(new PlaylistFragment());
            }
        });

        mBtnClosePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void setPlayerEtcDialog(){
        final CharSequence[] moreItems = {"플레이리스트에 추가하기", "보관함에 추가하기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        builder.setItems(moreItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        //add playlist this song
                        Toast.makeText(MainActivity.this, "add playlist this song", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        //add home's saved song list
                        Toast.makeText(MainActivity.this, "add home's saved song list", Toast.LENGTH_SHORT).show();
                        break;
                }
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
}
