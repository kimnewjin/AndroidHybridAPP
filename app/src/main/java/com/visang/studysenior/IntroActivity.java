package com.visang.studysenior;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_intro);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //상태바 숨김
        try{
            Thread.sleep(1500); //1.5초 스플래시 주고 권한 설정으로 이동
            Intent intent = new Intent(this,DangerAuthActivity.class);
            startActivity(intent);
            finish();
        }catch(InterruptedException e){
            e.printStackTrace();
            finish();
        }
    }


}