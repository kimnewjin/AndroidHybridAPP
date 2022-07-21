package com.visang.studysenior;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    WebView seniorWebView;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());

        if (status == NetworkStatus.TYPENONE){
            Toast.makeText(this,"인터넷연결상태를 확인하세요", Toast.LENGTH_SHORT).show();
            fnNetworkConnectChkAlert();
        }


        setContentView(R.layout.activity_main);
        seniorWebView = findViewById(R.id.studySenior);
        seniorWebView.addJavascriptInterface(new webAppInterface(this), "studysenior");
        seniorWebView.setWebChromeClient(new myWebChromeClient());
        seniorWebView.setWebViewClient(new WebViewClient());


        WebSettings seniorSetting = seniorWebView.getSettings();

        seniorSetting.setJavaScriptEnabled(true);
        seniorSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        seniorSetting.setUseWideViewPort(true);
        seniorSetting.setLoadWithOverviewMode(true);
        seniorSetting.setDomStorageEnabled(true);
        seniorSetting.setCacheMode(seniorSetting.LOAD_NO_CACHE);


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        cookieManager.getInstance().flush();
        seniorWebView.clearCache(true);
        seniorWebView.clearHistory();
        seniorWebView.loadUrl("https://m.soobakc.com/studyseniorstaff/index.asp?isWebView=1");
    }

    public class myWebChromeClient extends WebChromeClient{

    }

    public class webAppInterface{
        Context mContext;
        webAppInterface(Context c){
            mContext = c;
        }

        @JavascriptInterface
        public void altMsg(String args){
           Toast.makeText(getApplicationContext(),args, Toast.LENGTH_LONG).show();
        }


        @JavascriptInterface
        public void logout(){

            //fnLogout();
        }


    }


    public void fnLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("로그아웃 하시겠습니까?.");
        builder.setTitle("");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                //fnGOLogOut();
            }
        });

        builder.setNegativeButton("취소",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @JavascriptInterface
    public void fnGOLogOut(){

       // seniorWebView.loadUrl("https://m.soobakc.com/studyseniorstaff/logout.asp?isWebView=1");
        //seniorWebView.loadUrl
        //seniorWebView.loadUrl("javascript:alert('hello world')");
        //Toast.makeText(this,"확인버튼클릭", Toast.LENGTH_SHORT).show();
    }




    // 다이얼로그 띄워주기
    public void fnNetworkConnectChkAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("인터넷 연결 상태를 확인하세요.\n\n인터넷 연결 후 이용 가능하며\n\n확인을 누르면 앱이 종료됩니다.");
        builder.setTitle("인터넷 연결 확인");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fnAppProcessFinish();
            }
        });

        builder.setNegativeButton("취소",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //앱종료
    public void fnAppProcessFinish(){
        ActivityCompat.finishAffinity(this);
        System.exit(0);
    }

    /*@Override
    public void onCloseWindow(WebView window){
        window.setVisibility(View.GONE);
        window.destroy();
        super.onCloseWindow(window);
    }*/

    private final long FINISH_INTERNAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed(){
        long thisTime = System.currentTimeMillis();
        long intervalTime = thisTime - backPressedTime;

        if (seniorWebView.canGoBack()){
            seniorWebView.goBack();
        }else{
            if (0 <= intervalTime && FINISH_INTERNAL_TIME > intervalTime){
                finish();
                return;
            }else {
                backPressedTime = thisTime;
                Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}