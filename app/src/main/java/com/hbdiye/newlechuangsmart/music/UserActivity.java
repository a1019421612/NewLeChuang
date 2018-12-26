package com.hbdiye.newlechuangsmart.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hbdiye.newlechuangsmart.R;
import com.hbdiye.newlechuangsmart.music.factory.HttpRequestFactory;
import com.lib.smartlib.HopeLoginBusiness;
import com.lib.smartlib.HopeSDK;
import com.lib.smartlib.callback.HttpCallback;

import java.io.File;
import java.io.FileOutputStream;


/**
 * 作者：kelingqiu on 18/3/15 09:36
 * 邮箱：42747487@qq.com
 */

public class UserActivity extends AppCompatActivity {
    private static final String TAG = UserActivity.class.getSimpleName();
    private static final int REQUEST_PHONE_STATUS = 101;
    private Bitmap head;
    private String fileName = "headimg.jpg";
    private TextView content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_user);
        content = findViewById(R.id.content);
    }

    public void uploadImg(View view){
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            }else{
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
            }
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, 1);
        }
    }

    public void profileLoad(View view){
        HopeSDK.getInstance().httpSend("/hopeApi/profile/load", HttpRequestFactory.profileLoad(HopeLoginBusiness.getInstance().getToken()), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }

    public void profileAlias(View view){
        HopeSDK.getInstance().httpSend("/hopeApi/profile/alias", HttpRequestFactory.profileAlias(HopeLoginBusiness.getInstance().getToken(),"昵称"), new HttpCallback() {
            @Override
            public void onSuccess(String success) {
                Log.d("HopeSDK","success:" + success);
                showContent(success);
            }

            @Override
            public void onFailure(String error) {
                Log.d("HopeSDK", "error:" + error);
                showContent(error);
            }
        });
    }



    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHONE_STATUS);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PHONE_STATUS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATUS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "应用需要权限", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "权限没有打开", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode){
                case 1:{
                    Log.d(TAG, "picture uri:" + data.getData());
                    cropPhoto(data.getData());
                    break;
                }
                case 2:{
                    File temp = new File(Environment.getExternalStorageDirectory().toString() + "/head.jpg");
                    Log.d(TAG, " capture uri:" + temp.getPath());
                    cropPhoto(Uri.fromFile(temp));
                    break;
                }
                case 3:{
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        head = extras.getParcelable("data");
                        if (head != null) {
                            savePic(head);
                            File file = new File(Environment.getExternalStorageDirectory(), fileName);
                            if (file.exists()) {
                                HopeSDK.getInstance().httpSendWithImage("/hopeApi/profile/header", HttpRequestFactory.profileHeader(HopeLoginBusiness.getInstance().getToken()), file, "image/jpeg", "header",
                                new HttpCallback() {
                                    @Override
                                    public void onSuccess(String success) {
                                        Log.d(TAG, "success:"+success);
                                        showContent(success);
                                    }

                                    @Override
                                    public void onFailure(String error) {
                                        Log.d(TAG, "error:"+error);
                                        showContent(error);
                                    }
                                });
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void savePic(Bitmap bitmap) {
        String status = Environment.getExternalStorageState();
        FileOutputStream fileOut = null;
        if (!Environment.MEDIA_MOUNTED.equals(status)) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        Log.d(TAG, "create file info :" + file.getAbsolutePath());
        try {
            fileOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showContent(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                content.setText(text);
            }
        });
    }
}
