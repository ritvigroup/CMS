package com.ritvi.umnag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.ritvi.umnag.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsConditionActivity extends AppCompatActivity {

    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_condition_activityu);
        ButterKnife.bind(this);

//        webView.loadUrl("https://www.sc.com/in/terms-conditions-disclaimer/");
        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermsConditionActivity.this,SliderActivity.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
