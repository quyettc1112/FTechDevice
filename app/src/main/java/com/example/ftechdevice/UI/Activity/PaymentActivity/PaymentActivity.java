package com.example.ftechdevice.UI.Activity.PaymentActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ftechdevice.API_Repository.VNPay_Repository;
import com.example.ftechdevice.Model.ModelRespone.UrlResponseDTO;
import com.example.ftechdevice.R;

import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@AndroidEntryPoint
public class PaymentActivity extends AppCompatActivity {

    private static final org.apache.commons.logging.Log log = LogFactory.getLog(PaymentActivity.class);
    @Inject
    VNPay_Repository vnPayRepository;

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        webView = findViewById(R.id.webView);

        vnPayRepository.submitOrder(29000, "test payment").enqueue(new Callback<UrlResponseDTO>() {

            @Override
            public void onResponse(Call<UrlResponseDTO> call, Response<UrlResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String url = response.body().getUrl();
                    Log.d("url",url);
                    loadUrlIntoWebView(url);
                } else {
                    Toast.makeText(PaymentActivity.this, "Không thể lấy URL từ server", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UrlResponseDTO> call, Throwable t) {
                Log.d("CheckT", t.getMessage());
                Toast.makeText(PaymentActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void loadUrlIntoWebView(String url) {

        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Permissions-Policy", "geolocation=(self), microphone=()");
                    headers.put("Content-Security-Policy", "default-src 'self'; style-src 'self' 'unsafe-inline'; img-src 'self' data:");

                    WebResourceRequest finalRequest = request;
                    request = new WebResourceRequest() {
                        @Override
                        public Uri getUrl() {
                            return finalRequest.getUrl();
                        }

                        @Override
                        public boolean isForMainFrame() {
                            return finalRequest.isForMainFrame();
                        }

                        @Override
                        public boolean isRedirect() {
                            return finalRequest.isRedirect();
                        }

                        @Override
                        public boolean hasGesture() {
                            return finalRequest.hasGesture();
                        }

                        @Override
                        public String getMethod() {
                            return finalRequest.getMethod();
                        }

                        @Override
                        public Map<String, String> getRequestHeaders() {
                            return headers;
                        }
                    };
                }
                return super.shouldInterceptRequest(view, request);
            }
        });

        webView.loadUrl(url);

    }

    private void handleOrderResponse() {
        Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
    }
}