package com.earnly.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;
    private boolean homeShown = true;

    private final int BG_COLOR = Color.rgb(7, 19, 42);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // Android 15 uses edge-to-edge.
        // Keep the system bars visible and use the app background
        // behind the transparent system-bar areas.
        window.setStatusBarColor(BG_COLOR);
        window.setNavigationBarColor(BG_COLOR);

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            window.setStatusBarContrastEnforced(false);
            window.setNavigationBarContrastEnforced(false);
        }

        // Dark background with LIGHT status-bar icons.
        window.getDecorView().setSystemUiVisibility(0);

        // Root container fills the screen.
        // We will inset the WebView using WindowInsets.
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(BG_COLOR);

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

        webView.setBackgroundColor(BG_COLOR);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        FrameLayout.LayoutParams webParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

        root.addView(webView, webParams);

        /*
         * IMPORTANT:
         *
         * Android 15 draws the application edge-to-edge.
         * We therefore move the WebView itself below the
         * status bar and above the navigation bar.
         */
        root.setOnApplyWindowInsetsListener(
                new View.OnApplyWindowInsetsListener() {

                    @Override
                    public WindowInsets onApplyWindowInsets(
                            View view,
                            WindowInsets insets) {

                        int topInset = 0;
                        int bottomInset = 0;

                        if (android.os.Build.VERSION.SDK_INT >= 30) {

                            android.graphics.Insets systemBars =
                                    insets.getInsets(
                                            WindowInsets.Type.systemBars()
                                    );

                            topInset = systemBars.top;
                            bottomInset = systemBars.bottom;

                        } else {

                            topInset = insets.getSystemWindowInsetTop();
                            bottomInset = insets.getSystemWindowInsetBottom();
                        }

                        /*
                         * This is the key fix.
                         *
                         * The WebView does NOT occupy the status-bar
                         * area anymore.
                         */
                        view.setPadding(
                                0,
                                topInset,
                                0,
                                bottomInset
                        );

                        return insets;
                    }
                }
        );

        setContentView(root);

        // Load the existing HTML. No HTML modification required.
        webView.loadUrl("file:///android_asset/index.html");
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
            webView = null;
        }

        super.onDestroy();
    }
}
