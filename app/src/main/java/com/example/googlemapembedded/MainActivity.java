package com.example.googlemapembedded;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private WebView Map;
    private EditText Src,Dest;
    private Button Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Src = findViewById(R.id.source);
        Dest = findViewById(R.id.destination);
        Search = findViewById(R.id.GetDirection);

        Map = (WebView) findViewById(R.id.map);
        Map.setWebViewClient(new WebViewClient());
        WebSettings webSettings = Map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String src = Src.getText().toString().trim();
                String dest = Dest.getText().toString().trim();
                String mode = "driving";
                showMap(src,dest,mode);
            }
        });

        showMap("Mist","Mist","driving");
    }

    public void showMap(String src, String dest, String mode) {
        Map = findViewById(R.id.map);
        WebSettings webSettings = Map.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        Map.loadUrl("https://directionsdebug.firebaseapp.com/embed.html?origin=" + src + "&destination=" + dest + "&mode=" + mode);

        Map.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        Map.setWebChromeClient(new WebChromeClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

    }
}
