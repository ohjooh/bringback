package com.example.user.projectbringback.view;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.user.projectbringback.rcv.PlaylistInfoAdapter;
import com.example.user.projectbringback.rcv.PlaylistItemTouchHelper;
import com.example.user.projectbringback.R;
import com.example.user.projectbringback.data.Music;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PlaylistInfoFragment extends Fragment{
    private List<Music> musicLists = new ArrayList<>();
    private static final int GET_GALLERY_IMAGE = 1;
    private ItemTouchHelper mItemTouchHelper;
    private EditText mEditSearchMusic;
    private String mPlaylistName;
    private int mNumberOfSong;

    public PlaylistInfoFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_info, container, false);
        mEditSearchMusic = view.findViewById(R.id.editSearchMusic);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && getArguments() != null) {
            mPlaylistName = getArguments().getString("name");
            mNumberOfSong = getArguments().getInt("numberOfSong");
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setTitle(mPlaylistName);
            activity.getSupportActionBar().setHomeButtonEnabled(false);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_settings_black);
            final RecyclerView musicView = view.findViewById(R.id.playlist);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
            musicView.setLayoutManager(linearLayoutManager);

            for (int i = 0; i < mNumberOfSong; i++)
                musicLists.add(new Music("노래제목"+(i+1), "가수", "앨범명", "장르", "2019-09-23", i + 1));

            final PlaylistInfoAdapter adapter = new PlaylistInfoAdapter(musicLists, activity);
            PlaylistItemTouchHelper mCallback = new PlaylistItemTouchHelper(adapter);
            mItemTouchHelper = new ItemTouchHelper(mCallback);
            mItemTouchHelper.attachToRecyclerView(musicView);
            musicView.setAdapter(adapter);

            mEditSearchMusic.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    //TODO editText 입력 후 플레이리스트 띄우기
                    String searchName = editable.toString();
                    adapter.filter(searchName);
                }
            });
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_playlist_info, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent modifyPlaylistIntent = new Intent(getActivity(), ModifyPlaylistInfoActivity.class);
                modifyPlaylistIntent.putExtra("name", mPlaylistName);
                modifyPlaylistIntent.putExtra("numberOfSong",mNumberOfSong);
                startActivity(modifyPlaylistIntent);
                //TODO playlist edit 화면 가져오기 (Activity)
                return true;
            case R.id.insert_photo:
                showImageSelectDialog();
                return true;
            case R.id.finish:
                loadFragment(new PlaylistFragment());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showImageSelectDialog() {
        final CharSequence[] addItems = {"갤러리에서 사진 가져오기", "기본 이미지로 설정하기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.AlertDialogTheme);
        builder.setTitle("플레이리스트 앨범커버 설정")
                .setItems(addItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                showPermissionDialog();
                                dialogInterface.dismiss();
                                break;
                            case 1:
//                        mProfile.setImageResource(android.R.color.white);
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

        TedPermission.with(getActivity())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 업로드를 하기 위해서는 갤러리 접근 권한이 필요합니다.")
                .setDeniedMessage("갤러리 접근 권한을 거부했습니다.\n[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void pickPhotoAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                Log.e(String.valueOf(getActivity()), "Crop Error: " + cropError);
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
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity != null;
        Uri destinationUri = Uri.fromFile(new File(activity.getCacheDir(), queryName(activity.getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(80);
        options.setToolbarTitle("이미지 편집");
        options.setToolbarColor(ContextCompat.getColor(activity, R.color.colorSecondary));
        options.setToolbarWidgetColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        options.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        options.setActiveControlsWidgetColor(ContextCompat.getColor(activity, R.color.colorSecondary));
        options.withAspectRatio(1, 1);
        options.withMaxResultSize(1000, 1000);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(activity);
    }

    private void setResultOk(Uri imagePath) {
        Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        Objects.requireNonNull(getActivity()).setResult(RESULT_OK, intent);

        /*Glide.with(this)
                .asBitmap()
                .load(imagePath)
                .into(mProfile);*/
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        Objects.requireNonNull(getActivity()).setResult(Activity.RESULT_CANCELED, intent);
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
