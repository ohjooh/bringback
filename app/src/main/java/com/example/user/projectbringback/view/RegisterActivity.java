package com.example.user.projectbringback.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.projectbringback.R;
import com.example.user.projectbringback.RetrofitInterface;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

public class RegisterActivity extends AppCompatActivity {
    private static final int GET_USER_TASTE = 2;
    private EditText editUserId;
    private EditText editUserPW;
    private EditText editUserPW2;
    private EditText editUserEmail;
    private EditText editUserPhoneNumber;
    private TextView textUserBirth;
    private TextView textUserSex;
    private TextView textUserTaste;
    private Calendar time = Calendar.getInstance();
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
//    private String BASE_URL = "http://10.0.2.2:3000"; //에뮬레이터 주소
    private String BASE_URL = "http://fc0fff68.ngrok.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory((GsonConverterFactory.create()))
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        textUserBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseDate();
            }
        });

        textUserSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseSex();
            }
        });

        textUserTaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTaste();
            }
        });
    }

    public void initViews() {
        editUserId = findViewById(R.id.editUserId);
        editUserPW = findViewById(R.id.editUserPW);
        editUserPW2 = findViewById(R.id.editUserPW2);
        editUserEmail = findViewById(R.id.editUserEmail);
        editUserPhoneNumber = findViewById(R.id.editUserPhoneNumber);
        textUserBirth = findViewById(R.id.textUserBirth);
        textUserSex = findViewById(R.id.textUserSex);
        textUserTaste = findViewById(R.id.textUserTaste);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    public void register() {
        String id = editUserId.getText().toString().trim();
        String pw = editUserPW.getText().toString().trim();
        String pw2 = editUserPW2.getText().toString().trim();
        String email = editUserEmail.getText().toString().trim();
        String phoneNumber = editUserPhoneNumber.getText().toString().trim();
        String birth = textUserBirth.getText().toString().trim();
        String taste = textUserTaste.getText().toString().trim();
        String sex = textUserSex.getText().toString().trim();

        if (id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editUserId.requestFocus();
        }else if(pw.isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editUserPW.requestFocus();
        }else if(pw2.isEmpty()){
            Toast.makeText(this, "비밀번호 확인을 입력해주세요.", Toast.LENGTH_SHORT).show();
            editUserPW2.requestFocus();
        }else if(email.isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            editUserEmail.requestFocus();
        }else if(phoneNumber.isEmpty()){
            Toast.makeText(this, "핸드폰 번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            editUserPhoneNumber.requestFocus();
        }else if(birth.equals("생년월일") || birth.isEmpty()){
            Toast.makeText(this, "생년월일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(taste.equals("음악 취향") || taste.isEmpty()){
            Toast.makeText(this, "음악 취향을 선택해주세요.", Toast.LENGTH_SHORT).show();
        }else if(sex.equals("성별") || sex.isEmpty()){
            Toast.makeText(this, "성별을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }else if(!pw.equals(pw2)){
            Toast.makeText(this, "비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else{

            Toast.makeText(RegisterActivity.this, taste, Toast.LENGTH_SHORT).show();

            HashMap<String, String> map = new HashMap<>();
            map.put("userId", id);
            map.put("password", pw);
            map.put("email", email);
            map.put("phone", phoneNumber);
            map.put("birth", birth);
            map.put("gender", sex);
            map.put("taste", taste);

            Call<Void> call = retrofitInterface.ExsiguUp(map);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 200){
                        Toast.makeText(RegisterActivity.this, "회원 가입 완료", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);

                    }
                    else if (response.code() == 400) {
                        Toast.makeText(RegisterActivity.this,
                                "이미 가입한 회원의 아이디 입니다.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "네트워크 연결 오류",
                            Toast.LENGTH_LONG).show();

                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseDate() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_birth);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        final NumberPicker yearPicker = dialog.findViewById(R.id.year);
        final NumberPicker monthPicker = dialog.findViewById(R.id.month);
        final NumberPicker dayPicker = dialog.findViewById(R.id.day);
        int thisYear = time.get(Calendar.YEAR);

        yearPicker.setMinValue(1900);
        yearPicker.setMaxValue(thisYear);
        yearPicker.setValue(2000);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(1);
        monthPicker.setWrapSelectorWheel(true);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);
        dayPicker.setValue(1);
        dayPicker.setWrapSelectorWheel(true);
        dayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = yearPicker.getValue();
                int month = monthPicker.getValue();
                int day = dayPicker.getValue();
                String birth = year + "년 " + month + "월 " + day + "일";

                textUserBirth.setText(birth);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void chooseSex() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sex);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        final RadioGroup group = dialog.findViewById(R.id.radioGroupSex);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnId = group.getCheckedRadioButtonId();
                if (btnId == -1) {
                    Toast.makeText(RegisterActivity.this, "성별을 선택해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    RadioButton btnUserSex = dialog.findViewById(btnId);
                    String userSex = btnUserSex.getText().toString();
                    textUserSex.setText(userSex);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }

    private void chooseTaste() {
        Intent intent = new Intent(RegisterActivity.this, ChangeTasteActivity.class);
        startActivityForResult(intent, GET_USER_TASTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_USER_TASTE && resultCode == RESULT_OK) {
            textUserTaste.setText("");
            assert data != null;
            String[] selectedTaste = data.getStringArrayExtra("tastes");
            for (String s : selectedTaste) {
                if (s != null)
                    textUserTaste.append(s + " ");
            }
        }
    }
}
