package com.example.ftechdevice.UI.Activity.PaymentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
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
import com.example.ftechdevice.Model.ModelRespone.VNPayResponse;
import com.example.ftechdevice.Model.PaymentResponse;
import com.example.ftechdevice.R;
import com.example.ftechdevice.UI.Activity.MainActivity.MainActivity;

import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.Request;
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

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (url.contains("http://ftech-env.eba-3ng3tyiq.ap-northeast-1.elasticbeanstalk.com/vnpay/vnpay-payment")) {
                    view.loadUrl("about:blank");
                    handlePaymentResult(url);

                    return true;
                } else {
                    String url = request.getUrl().toString();
                   // view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Kiểm tra URL để xử lý kết quả thanh toán nếu cần
                if (url.contains("http://ftech-env.eba-3ng3tyiq.ap-northeast-1.elasticbeanstalk.com/vnpay/vnpay-payment")) {
                    view.loadUrl("about:blank");
                    handlePaymentResult(url);
                }
            }


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


    private void handlePaymentResult(String url) {
        vnPayRepository.getPaymentResult(url).enqueue(new Callback<VNPayResponse>() {
            @Override
            public void onResponse(Call<VNPayResponse> call, Response<VNPayResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VNPayResponse vnPayResponse = response.body();
                    String status = vnPayResponse.getCode();
                    String transactionId = vnPayResponse.getTransactionId();
                    String orderId = vnPayResponse.getOrderId();
                    String totalPrice = vnPayResponse.getTotalPrice();
                    String paymentTime = vnPayResponse.getPaymentTime();


                    if (status != null && status.equals("1")) { // Giả sử "00" là mã thành công
                        showToast("Thanh toán thành công! Mã giao dịch: " + transactionId + "\nMã đơn hàng: " + orderId + "\nSố tiền: " + totalPrice + "\nThời gian: " + paymentTime);
                        finish();
                        startActivity(new Intent(PaymentActivity.this, MainActivity.class));

                    } else {
                        showToast("Thanh toán thất bại hoặc bị hủy");
                    }
                } else {
                    showToast("Có lỗi xảy ra khi nhận kết quả thanh toán");
                }
            }

            @Override
            public void onFailure(Call<VNPayResponse> call, Throwable t) {
                showToast("Có lỗi xảy ra: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void handleOrderResponse() {
        Toast.makeText(PaymentActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
    }
}