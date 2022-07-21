package com.visang.studysenior;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class DangerAuthActivity extends AppCompatActivity {
    private final String[] permissions={
      Manifest.permission.CAMERA,
      Manifest.permission.RECORD_AUDIO,
      Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private static final int MULTIPLE_PERMISSIONS = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 숨김
        if(Build.VERSION.SDK_INT >= 23) {
            int result;
            List<String> permissionList = new ArrayList<>();
            for (String pm : permissions) {
                result = ContextCompat.checkSelfPermission(this, pm);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(pm);
                }
            }
            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), MULTIPLE_PERMISSIONS);
                return;
            }
        }

        int permissionChk1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionChk2 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permissionChk3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionChk1 == PackageManager.PERMISSION_GRANTED && permissionChk2 == PackageManager.PERMISSION_GRANTED && permissionChk3 == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            showPermissionToast();
        }
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
    }
    private void showPermissionToast(){
        Toast.makeText(this,"권한 요청에 동의 해주셔야 앱 이용이 가능합니다. 설정에서 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
    }
}
