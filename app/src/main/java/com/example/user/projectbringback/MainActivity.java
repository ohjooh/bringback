package com.example.user.projectbringback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {
    private BottomSheetBehavior mBottomSheetBehavior;
    private ConstraintLayout bottomPlayerBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout bottomSheet = findViewById(R.id.bottomsheet);
        final BottomNavigationView navigation = findViewById(R.id.navigation);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomPlayerBar = findViewById(R.id.playerBar);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int state) {
                if(state == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomPlayerBar.setVisibility(View.VISIBLE);
                    showBottomNavigationView(navigation);
                }

                if(state == BottomSheetBehavior.STATE_EXPANDED){
                    bottomPlayerBar.setVisibility(View.GONE);
                    hideBottomNavigationView(navigation);
                }

                if(state == BottomSheetBehavior.STATE_SETTLING){
                    bottomPlayerBar.setVisibility(View.GONE);
                    hideBottomNavigationView(navigation);
                }

                if(state == BottomSheetBehavior.STATE_DRAGGING){
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
                if(mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        loadFragment(new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()){
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

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showBottomNavigationView(BottomNavigationView view){
        view.animate().translationY(0);
    }

    private void hideBottomNavigationView(BottomNavigationView view){
        view.animate().translationY(view.getHeight());
    }
}
