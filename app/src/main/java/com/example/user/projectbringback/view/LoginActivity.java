package com.example.user.projectbringback.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.projectbringback.LoginResult;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.helper.BackKeyHandler;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.user.projectbringback.Network.onNetwork;
import static com.example.user.projectbringback.Network.retrofitInterface;

public class LoginActivity extends AppCompatActivity {
    private BackKeyHandler backKeyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backKeyHandler = new BackKeyHandler(this);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> login());
    }

    public void login(){

        final EditText userId = findViewById(R.id.editId);
        final EditText userPassword = findViewById(R.id.editPW);

        final String id =  userId.getText().toString().trim();
        final String password = userPassword.getText().toString().trim();


        if (id.isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            userId.requestFocus();
        }else if(password.isEmpty()) {
            Toast.makeText(this, " 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            userPassword.requestFocus();
        }else {
            HashMap<String, String> map = new HashMap<>();
            map.put("userId", id);
            map.put("password", password);

            onNetwork();

            Call<LoginResult> call =retrofitInterface.ExLogin(map);

            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(@NotNull Call<LoginResult> call, @NotNull Response<LoginResult> response) {
                    LoginResult result = response.body();

                    if (response.code() == 200){

                        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);

                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        assert result != null;
                        homeIntent.putExtra("Id",result.getUserId_result());
                        homeIntent.putExtra("Taste",result.getTaste_result());
                        startActivity(homeIntent);

                    }

                    else if( response.code() == 404){
                        Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인하세요.",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<LoginResult> call, @NotNull Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        backKeyHandler.onBackPressed();
    }

    public void onRegister(View view) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}