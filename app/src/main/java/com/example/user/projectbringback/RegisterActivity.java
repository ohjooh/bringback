package com.example.user.projectbringback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;
import java.util.Calendar;

import static java.lang.String.format;

public class RegisterActivity extends AppCompatActivity {
    private TextView textUserBirth;
    private TextView textUserSex;
    private Calendar time = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textUserBirth = findViewById(R.id.textUserBirth);
        textUserSex = findViewById(R.id.textUserSex);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
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
        int thisYear =time.get(Calendar.YEAR);

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
                String birth = year+"년 "+month+"월 "+day+"일";

                textUserBirth.setText(birth);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void chooseSex(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sex);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        final RadioGroup group = dialog.findViewById(R.id.radioGroupSex);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int btnId = group.getCheckedRadioButtonId();
                if(btnId == -1){
                    Toast.makeText(RegisterActivity.this, "성별을 선택해주세요.", Toast.LENGTH_LONG).show();
                }else{
                    RadioButton btnUserSex = dialog.findViewById(btnId);
                    String userSex = btnUserSex.getText().toString();
                    textUserSex.setText(userSex);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
}
