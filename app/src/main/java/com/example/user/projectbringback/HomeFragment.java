package com.example.user.projectbringback;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        this.setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageView mProfile = view.findViewById(R.id.profile);
        ImageView btnSetting = view.findViewById(R.id.btnSetting);
        TextView mNickName = view.findViewById(R.id.nickname);
        TextView mTastes = view.findViewById(R.id.tastes);
        mProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mProfile.setClipToOutline(true);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeSettingIntent = new Intent(getActivity(), HomeSettingActivity.class);
                startActivity(homeSettingIntent);
            }
        });

        return view;
    }

}
