package com.example.user.projectbringback.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.helper.SpaceItemDecoration;
import com.example.user.projectbringback.rcv.SharingTastesAdapter;
import com.example.user.projectbringback.data.Taste;

import java.util.ArrayList;
import java.util.List;

public class SharingTastesFragment extends Fragment {
    private List<Taste> tasteList = new ArrayList<>();

    public SharingTastesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sharing_tastes, container, false);
        RecyclerView tastesView = view.findViewById(R.id.tastesView);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        tastesView.setLayoutManager(gridLayoutManager);
        tastesView.addItemDecoration(new SpaceItemDecoration(30));
        initTastes();
        SharingTastesAdapter adapter = new SharingTastesAdapter(activity, tasteList);
        tastesView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SharingTastesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SharingTastesAdapter.SharingTastesViewHolder holder, View view, int position) {
                String tasteName = holder.tasteName.getText().toString();
                loadFragment(tasteName);
            }
        });

        return view;
    }

    private void loadFragment(String tasteName) {
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, newInstance(tasteName));
        transaction.commit();
    }

    private static Fragment newInstance(String tasteName) {
        TasteInfoFragment fragment = new TasteInfoFragment();
        Bundle args = new Bundle();
        args.putString("tasteName", tasteName);
        fragment.setArguments(args);
        return fragment;
    }

    private void initTastes() {
        tasteList.add(new Taste(R.drawable.ballad, "발라드"));
        tasteList.add(new Taste(R.drawable.dance, "댄스"));
        tasteList.add(new Taste(R.drawable.pop, "팝"));
        tasteList.add(new Taste(R.drawable.fork, "포크/어쿠스틱"));
        tasteList.add(new Taste(R.drawable.idol, "아이돌"));
        tasteList.add(new Taste(R.drawable.hiphop, "랩/힙합"));
        tasteList.add(new Taste(R.drawable.rnbsoul, "알앤비/소울"));
        tasteList.add(new Taste(R.drawable.electronic, "일렉트로닉"));
        tasteList.add(new Taste(R.drawable.rock_metal, "락/메탈"));
        tasteList.add(new Taste(R.drawable.jazz, "재즈"));
        tasteList.add(new Taste(R.drawable.indie, "인디"));
        tasteList.add(new Taste(R.drawable.ost, "OST"));
        tasteList.add(new Taste(R.drawable.classic, "클래식"));
        tasteList.add(new Taste(R.drawable.piano, "뉴에이지"));
        tasteList.add(new Taste(R.drawable.reggae, "레게"));
        tasteList.add(new Taste(R.drawable.blues, "블루스"));
        tasteList.add(new Taste(R.drawable.etc, "기타"));
    }
}
