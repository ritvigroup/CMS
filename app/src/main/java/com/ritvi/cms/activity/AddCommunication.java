package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ritvi.cms.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommunication extends AppCompatActivity {

    @BindView(R.id.btn_next)
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_communication);
        ButterKnife.bind(this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCommunication.this,AddCommunicationAddressActivity.class));
            }
        });
    }
}
