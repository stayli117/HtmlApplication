package net.people.html;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import net.people.html.utils.PeopleHtml;

public class HtmlActivity extends AppCompatActivity {
    WebView webView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        webView = (WebView) findViewById(R.id.web);
        btn = (Button) findViewById(R.id.java2Js);
        initWebViewSettings();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:native2JsData(来自Native的数据)");
            }
        });
    }

    private void initWebViewSettings() {

        webView.setVerticalScrollBarEnabled(true);

        webView.setHorizontalScrollBarEnabled(true);

        WebSettings webSettings = webView.getSettings();

        //设置编码

        webSettings.setJavaScriptEnabled(true);
        //支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

       //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.e("People", "onJsAlert: " + message);

                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.e("People", "onJsAlert: " + message);

                return super.onJsConfirm(view, url, message, result);
            }
        });//作界面渲梁效果，js弹框或者进度加载
        webView.setWebViewClient(new WebClient());
        webView.loadUrl("file:///android_asset/index.html");

    }

    private class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                PeopleHtml.getInstance(HtmlActivity.this).execute(url, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //网页加载开始
            super.onPageStarted(view, url, favicon);
        }


        @Override

        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            //网页加载失败
            super.onReceivedError(view, request, error);
        }


        @Override

        public void onPageFinished(WebView view, String url) {
            //网页加载完成
            view.loadUrl("javascript:setWebViewFlag()");
        }

    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    //销毁Webview
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
