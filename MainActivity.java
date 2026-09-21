package com.earnly.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
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

        // Keep Android status bar and navigation bar visible
        window.setStatusBarColor(Color.rgb(7, 19, 42));
        window.setNavigationBarColor(Color.rgb(7, 19, 42));

        // Normal system-bar icon mode
        window.getDecorView().setSystemUiVisibility(0);

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

        webView.setWebViewClient(new                            top = insets.getSystemWindowInsetTop();
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
