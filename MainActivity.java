package com.earnly.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
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

        Window window = getWindow();

        // Keep system bars visible
        window.setStatusBarColor(Color.rgb(8, 20, 45));
        window.setNavigationBarColor(Color.rgb(8, 20, 45));

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        /*
         * Prevent the HTML/WebView from going underneath
         * the phone's status bar and navigation bar.
         */
        webView.setOnApplyWindowInsetsListener(
                new View.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsets onApplyWindowInsets(
                            View view,
                            WindowInsets insets) {

                        int top = insets.getSystemWindowInsetTop();
                        int bottom = insets.getSystemWindowInsetBottom();

                        view.setPadding(
                                0,
                                top,
                                0,
                                bottom
                        );

                        return insets;
                    }
                }
        );

        webView.loadUrl("file:///android_asset/index.html");

        setContentView(webView);
    }

    @Override
    public void onBackPressed() {

        if (!homeShown) {
            webView.evaluateJavascript(
                    "if(typeof go === 'function'){go('home');}",
                    null
            );

            homeShown = true;
            return;
        }

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
