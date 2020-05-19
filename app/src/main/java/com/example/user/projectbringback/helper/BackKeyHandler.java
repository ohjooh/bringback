package com.example.user.projectbringback.helper;

import android.app.Activity;
import android.widget.Toast;

public class BackKeyHandler {
    private long backKeyTime = 0;
    private Activity activity;

    public BackKeyHandler(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyTime + 2000) {
            backKeyTime = System.currentTimeMillis();
            showToast();
            return;
        }
        if (System.currentTimeMillis() <= backKeyTime + 2000) {
            activity.finish();
        }
    }

    private void showToast() {
        Toast.makeText(activity, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
