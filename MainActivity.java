package com.earnly.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    private WebView webView;
    private ValueCallback<Uri[]> filePathCallback;

    private static final int FILE_CHOOSER_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // Dark system-bar background
        window.setStatusBarColor(Color.rgb(7, 17, 31));
        window.setNavigationBarColor(Color.rgb(7, 17, 31));

        // White system-bar icons
        window.getDecorView().setSystemUiVisibility(0);

        // Root container
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(7, 17, 31));

        // WebView
        webView = new WebView(this);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);

        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setBackgroundColor(Color.rgb(7, 17, 31));

        webView.setWebViewClient(new WebViewClient());

        // File upload support
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(
                    WebView webView,
                    ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams) {

                if (MainActivity.this.filePathCallback != null) {
                    MainActivity.this.filePathCallback.onReceiveValue(null);
                }

                MainActivity.this.filePathCallback = filePathCallback;

                try {
                    Intent intent = fileChooserParams.createIntent();
                    startActivityForResult(intent, FILE_CHOOSER_REQUEST);
                    return true;
                } catch (Exception e) {
                    MainActivity.this.filePathCallback = null;
                    return false;
                }
            }
        });

        // Add WebView to root
        FrameLayout.LayoutParams webParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );

        root.addView(webView, webParams);

        setContentView(root);

        /*
         * Android 15 uses edge-to-edge for apps targeting SDK 35.
         *
         * We manually keep the WebView below the status bar
         * and above the navigation bar.
         */
        root.setOnApplyWindowInsetsListener((view, insets) -> {

            WindowInsets systemInsets = insets;

            int top = systemInsets.getInsets(
                    WindowInsets.Type.statusBars()
            ).top;

            int bottom = systemInsets.getInsets(
                    WindowInsets.Type.navigationBars()
            ).bottom;

            int left = systemInsets.getInsets(
                    WindowInsets.Type.systemBars()
            ).left;

            int right = systemInsets.getInsets(
                    WindowInsets.Type.systemBars()
            ).right;

            FrameLayout.LayoutParams params =
                    (FrameLayout.LayoutParams) webView.getLayoutParams();

            params.leftMargin = left;
            params.topMargin = top;
            params.rightMargin = right;
            params.bottomMargin = bottom;

            webView.setLayoutParams(params);

            return insets;
        });

        // Apply the insets immediately
        root.requestApplyInsets();

        // Load your existing HTML.
        // HTML code does
