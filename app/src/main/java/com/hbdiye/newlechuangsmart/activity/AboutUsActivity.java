package com.hbdiye.newlechuangsmart.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hbdiye.newlechuangsmart.R;

import java.util.HashMap;
import java.util.Map;

public class AboutUsActivity extends BaseActivity {
    private WebView webview;
    private ProgressBar pb_yitoiuzi;
    @Override
    protected void initData() {

    }

    @Override
    protected String getTitleName() {
        return "关于我们";
    }

    @Override
    protected void initView() {
        pb_yitoiuzi = (ProgressBar) findViewById(R.id.pb_yitoiuzi);
        pb_yitoiuzi.setMax(100);
        webview = (WebView) findViewById(R.id.webview2);

        WebSettings settings = webview.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadsImagesAutomatically(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(true);// 隐藏缩放按钮
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 排版适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);// 可任意比例缩放
        settings.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。

        webview.loadUrl("http://www.hbdiye.com/");
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb_yitoiuzi.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_yitoiuzi.setVisibility(View.GONE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
//                    Map<String, String> header = new HashMap<String, String>();
//                    header.put("X-Powered-Token-By", SafePreference.getStr(context, "SID"));
                    view.loadUrl(url);
                }

//                try {
//                    view.loadUrl(url);
////					}
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                return true;
            }

        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
//				defaultInit(AboutMeWebViewActivity.this, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    // 网页加载完成
                    pb_yitoiuzi.setVisibility(View.GONE);
                } else {
                    // 加载
                    pb_yitoiuzi.setVisibility(View.VISIBLE);
                    pb_yitoiuzi.setProgress(newProgress);
                }

            }

        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_about_us;
    }
}
