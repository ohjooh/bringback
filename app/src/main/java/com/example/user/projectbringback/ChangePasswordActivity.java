package com.example.user.projectbringback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText mCurrentPassword;
    private EditText mNewPassword;
    private EditText mNewPassword2;
    private Button mBtnSubmit;
    private TextView mIsEqualNewPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mCurrentPassword = findViewById(R.id.editCurrentPW);
        mNewPassword = findViewById(R.id.editNewPW);
        mNewPassword2 = findViewById(R.id.editNewPW2);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mIsEqualNewPW = findViewById(R.id.textIsEqualNewPW);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        mNewPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = mNewPassword.getText().toString();
                String password2 = mNewPassword2.getText().toString();

                if(password.equals(password2)){
                    mIsEqualNewPW.setText("비밀번호가 일치합니다.");
                    mIsEqualNewPW.setTextColor(getColor(R.color.colorSecondary));
                }else{
                    mIsEqualNewPW.setText("비밀번호가 일치하지 않습니다.");
                    mIsEqualNewPW.setTextColor(Color.RED);
                }
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO DB에 입력돼있는 비밀번호와 동일 여부 판별하는 조건문 작성

                String currentPW = mCurrentPassword.getText().toString().trim();
                String newPW = mNewPassword.getText().toString().trim();
                String newPW2 = mNewPassword2.getText().toString().trim();
                if(currentPW.isEmpty() || newPW.isEmpty() || newPW2.isEmpty()){
                    Toast.makeText(ChangePasswordActivity.this, "빈칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    if(currentPW.equals(newPW) && newPW.equals(newPW2)){
                        Toast.makeText(ChangePasswordActivity.this, "현재 비밀번호와 새 비밀번호가 동일합니다.", Toast.LENGTH_SHORT).show();
                        mNewPassword.requestFocus();
                    }else if(!newPW.equals(newPW2)){
                        Toast.makeText(ChangePasswordActivity.this, "새로운 비밀번호와 2차 비밀번호 확인이 동일하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "비밀번호가 정상적으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
