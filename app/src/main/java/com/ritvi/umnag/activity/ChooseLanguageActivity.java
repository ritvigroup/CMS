package com.ritvi.umnag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.ritvi.umnag.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseLanguageActivity extends AppCompatActivity {

    @BindView(R.id.iv_next)
    ImageView iv_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        ButterKnife.bind(this);

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseLanguageActivity.this,TermsConditionActivity.class));
            }
        });
    }
}
