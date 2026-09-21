package com.earnly.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;
    private boolean homeShown = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Android status bar (time, signal, battery, etc.)
        hideStatusBar();

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();

        // Enable JavaScript
        settings.setJavaScriptEnabled(true);

        // Enable local storage
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        // Allow local files and content
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        // Better WebView experience
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        setContentView(webView);

        // Load your existing HTML exactly as it is
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void hideStatusBar() {
        Window window = getWindow();

        // Hide the Android status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Keep the app content stable/fullscreen
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Make sure the status bar stays hidden
        if (hasFocus) {
            hideStatusBar();
        }
    }

    @Override
    public void onBackPressed() {

        // First Back = go to Home inside the HTML app
        if (!homeShown) {
            webView.evaluateJavascript(
                    "if(typeof go === 'function'){go('home');}",
                    null
            );

            homeShown = true;
            return;
        }

        // Second Back = exit application
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }

        super.onDestroy();
    }
}
