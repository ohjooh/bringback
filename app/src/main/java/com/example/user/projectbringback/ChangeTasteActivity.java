package com.example.user.projectbringback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChangeTasteActivity extends AppCompatActivity {
    private List<String> tasteList = new ArrayList<>();
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_taste);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_black_24dp);
        RecyclerView tasteView = findViewById(R.id.tastesView);
        initTastes();

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL);
        tasteView.setLayoutManager(gridLayoutManager);
        final TasteAdapter adapter = new TasteAdapter(this, tasteList);
        tasteView.setAdapter(adapter);

        final Animation aniZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);

        adapter.setOnItemClickListener(new TasteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TasteAdapter.TasteViewHolder holder, View view, int position) {
                if (mSelectedItems.get(position, false)) {
                    mSelectedItems.delete(position);
                    holder.tasteName.setTextColor(Color.WHITE);
                    holder.tasteName.setBackgroundColor(Color.BLACK);
                    int textHeight = holder.tasteName.getHeight();
                    int textWidth = holder.tasteName.getWidth();
                    holder.tasteName.setHeight(textHeight - 50);
                    holder.tasteName.setWidth(textWidth - 50);
                } else {
                    if (mSelectedItems.size() >= 2) {
                        Toast.makeText(ChangeTasteActivity.this, "최대 두 가지 취향만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }else{
                        mSelectedItems.put(position, true);
                        holder.tasteName.setTextColor(getColor(R.color.colorPrimary));
                        holder.tasteName.setBackgroundColor(getColor(R.color.colorSecondary));
                        int textHeight = holder.tasteName.getHeight();
                        int textWidth = holder.tasteName.getWidth();
                        holder.tasteName.setHeight(textHeight + 50);
                        holder.tasteName.setWidth(textWidth + 50);
                        holder.tasteName.startAnimation(aniZoomIn);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.submit) {
            if(mSelectedItems.size() == 0){
                Toast.makeText(this, "좋아하는 음악 취향을 한 가지 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                Log.d("SELECTED ITEM","selected item ? "+mSelectedItems);
                for(int i=0;i<tasteList.size();i++){
                    Log.d("mSelectedItems","index of key "+mSelectedItems.get(i));
                    if(mSelectedItems.get(i)){
                    String tasteName = tasteList.get(i);
                    Log.d("tasteName", tasteName);
                    //TODO 여기에서 i값의 tasteName를 User에 저장하기
                }
            }
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTastes() {
        tasteList.add("발라드");
        tasteList.add("댄스");
        tasteList.add("팝");
        tasteList.add("포크/어쿠스틱");
        tasteList.add("아이돌");
        tasteList.add("랩/힙합");
        tasteList.add("알앤비/소울");
        tasteList.add("일렉트로닉");
        tasteList.add("락/메탈");
        tasteList.add("재즈");
        tasteList.add("인디");
        tasteList.add("OST");
        tasteList.add("클래식");
        tasteList.add("뉴에이지");
        tasteList.add("레게");
        tasteList.add("블루스");
        tasteList.add("기타");
    }
}
