package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ritvi.cms.R;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, WebServicesCallBack {


    private static final String CALL_REGISTER_API = "call_register_api";
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_phone_number_cancel)
    ImageView iv_phone_number_cancel;
    @BindView(R.id.et_phone_number)
    EditText et_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        btn_accept.setOnClickListener(this);

        et_phone_number.addTextChangedListener(this);
        iv_phone_number_cancel.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (et_phone_number.getText().toString().length() > 0) {
            iv_phone_number_cancel.setVisibility(View.VISIBLE);
        } else {
            iv_phone_number_cancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_phone_number_cancel:
                et_phone_number.setText("");
                break;
            case R.id.btn_accept:
                onAcceptPhoneNumber();
                break;
        }
    }

    public void onAcceptPhoneNumber() {
        if (et_phone_number.getText().toString().length() >= 10) {
//            callAPI
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("login_request", "REGISTER_MOBILE"));
            nameValuePairs.add(new BasicNameValuePair("device_token", ""));
            nameValuePairs.add(new BasicNameValuePair("mobile", "+91"+et_phone_number.getText().toString()));
            new WebServiceBase(nameValuePairs, this, this, CALL_REGISTER_API, true).execute(WebServicesUrls.REGISTER_URL);

//            startActivity(new Intent(RegistrationActivity.this, SatyapanActivity.class));
        }
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + " :- " + response);
        switch (apicall) {
            case CALL_REGISTER_API:
                parseRegisterResponse(response);
                break;
        }
    }

    public void parseRegisterResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                startActivity(new Intent(RegistrationActivity.this,SatyapanActivity.class).putExtra("mobile_number","+91"+et_phone_number.getText().toString()));
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"No Internet Connection");
        }
    }
}
