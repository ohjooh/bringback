package com.example.user.projectbringback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class HomeSettingActivity extends AppCompatActivity {
    private static final int GET_GALLERY_IMAGE = 1;
    private ImageView mProfile;
    private TextView textEditPassword;
    private TextView textEditTaste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_setting);
        mProfile = findViewById(R.id.profile);
        ImageButton mBtnEditProfile = findViewById(R.id.btnEditProfile);
        Button mBtnFinish = findViewById(R.id.btnFinish);
        textEditPassword = findViewById(R.id.textEditPassword);
        textEditTaste = findViewById(R.id.textEditTaste);
        mProfile.setBackground(new ShapeDrawable(new OvalShape()));
        mProfile.setClipToOutline(true);

        mBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageSelectDialog();
            }
        });

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent passwordIntent = new Intent(HomeSettingActivity.this, ChangePasswordActivity.class);
                startActivity(passwordIntent);
            }
        });

        textEditTaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tasteIntent = new Intent(HomeSettingActivity.this, ChangeTasteActivity.class);
                startActivity(tasteIntent);
            }
        });
    }

    private void showImageSelectDialog() {
        final CharSequence[] addItems = {"갤러리에서 사진 가져오기", "기본 이미지로 설정하기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeSettingActivity.this, R.style.AlertDialogTheme);
        builder.setItems(addItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        showPermissionDialog();
                        dialogInterface.dismiss();
                        break;
                    case 1:
                        mProfile.setImageResource(android.R.color.white);
                        dialogInterface.dismiss();
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPermissionDialog() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                pickPhotoAction();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };

        TedPermission.with(HomeSettingActivity.this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 업로드를 하기 위해서는 갤러리 접근 권한이 필요합니다.")
                .setDeniedMessage("갤러리 접근 권한을 거부했습니다.\n[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(READ_EXTERNAL_STORAGE)
                .check();
    }

    private void pickPhotoAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    assert data != null;
                    Uri imageUri = data.getData();
                    cropImage(imageUri);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleCropResult(data);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:
                assert data != null;
                final Throwable cropError = UCrop.getError(data);
                Log.e(String.valueOf(getApplicationContext()), "Crop Error: " + cropError);
                setResultCancelled();
                break;
            default:
                setResultCancelled();
        }
    }

    private void handleCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(80);
        options.setToolbarTitle("이미지 편집");
        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorSecondary));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.colorSecondary));
        options.withAspectRatio(1, 1);
        options.withMaxResultSize(1000, 1000);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    private void setResultOk(Uri imagePath) {
        Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        setResult(Activity.RESULT_OK, intent);

        Glide.with(this)
                .asBitmap()
                .load(imagePath)
                .into(mProfile);
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
    }

    private String queryName(ContentResolver contentResolver, Uri sourceUri) {
        Cursor returnCursor = contentResolver.query(sourceUri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


}
