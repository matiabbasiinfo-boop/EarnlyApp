package com.earnly.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Insets;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private WebView webView;
    private FrameLayout root;
    private FrameLayout.LayoutParams webViewParams;

    private boolean homeShown = true;

    private static final int APP_BACKGROUND = Color.rgb(7, 19, 42);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        // Android 15 / target SDK 35
        // Keep system bars visible.
        window.setStatusBarColor(APP_BACKGROUND);
        window.setNavigationBarColor(APP_BACKGROUND);

        if (Build.VERSION.SDK_INT >= 29) {
            window.setStatusBarContrastEnforced(false);
            window.setNavigationBarContrastEnforced(false);
        }

        // Use light-colored status/navigation icons.
        window.getDecorView().setSystemUiVisibility(0);

        // Root screen
        root = new FrameLayout(this);
        root.setBackgroundColor(APP_BACKGROUND);

        // WebView
        webView = new WebView(this);
        webView.setBackgroundColor(APP_BACKGROUND);

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
         * IMPORTANT:
         *
         * The WebView starts with zero margins.
         * WindowInsets will move the ENTIRE WebView
         * below the Android status bar.
         */
        webViewParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        root.addView(webView, webViewParams);

        /*
         * Android 15 sends the system-bar sizes here.
         */
        root.setOnApplyWindowInsetsListener(
                new View.OnApplyWindowInsetsListener() {

                    @Override
                    public WindowInsets onApplyWindowInsets(
                            View view,
                            WindowInsets insets) {

                        int top = 0;
                        int bottom = 0;

                        if (Build.VERSION.SDK_INT >= 30) {

                            Insets bars = insets.getInsets(
                                    WindowInsets.Type.systemBars()
                            );

                            top = bars.top;
                            bottom = bars.bottom;

                        } else {

                            top = insets.getSystemWindowInsetTop();
                            bottom = insets.getSystemWindowInsetBottom();
                        }

                        /*
                         * THIS is the important part.
                         *
                         * Move WebView itself below the status bar.
                         */
                        FrameLayout.LayoutParams params =
                                (FrameLayout.LayoutParams)
                                        webView.getLayoutParams();

                        params.topMargin = top;
                        params.bottomMargin = bottom;
                        params.leftMargin = 0;
                        params.rightMargin = 0;

                        webView.setLayoutParams(params);

                        return insets;
                    }
                }
        );

        setContentView(root);

        // Existing HTML remains completely unchanged.
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
}                         *
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
