package com.example.number_ded.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.number_ded.R;
import com.example.number_ded.databinding.FragmentWebviewBinding;

public class WebviewFragment extends Fragment {

    WebView mywebview;
    String url = "https://huaylike.net";
    public WebviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentWebviewBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding= FragmentWebviewBinding.inflate(inflater, container, false);
        getActivity().setTitle("Web View");
       mywebview=binding.webView;
        WebSettings settings = mywebview.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCacheMaxSize(1010241024);
        settings.setAppCachePath("");
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
// Flash settings
        settings.setPluginState(WebSettings.PluginState.ON);
        mywebview.setWebChromeClient(new WebChromeClient());
        // workaround so that the default browser doesn't take over
        mywebview.setWebViewClient(new MyWebViewClient());
        mywebview.loadUrl(url);
       return binding.getRoot();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
    }

}