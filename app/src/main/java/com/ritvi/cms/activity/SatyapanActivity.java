package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.pojo.user.UserProfilePOJO;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SatyapanActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final String CALL_OTP_VERIFIED = "call_otp_verified";
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.progress_timer)
    ProgressBar progress_timer;
    @BindView(R.id.et_otp)
    EditText et_otp;

    String mobile_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satyapan);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobile_number = bundle.getString("mobile_number");
        }


        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateMobileOTP();
//                startActivity(new Intent(SatyapanActivity.this,MpinActivity.class));
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void validateMobileOTP() {
        if (et_otp.getText().length() == 6) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("login_request", "VALIDATE_MOBILE_OTP"));
            nameValuePairs.add(new BasicNameValuePair("device_token", ""));
            nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
            nameValuePairs.add(new BasicNameValuePair("otp", et_otp.getText().toString()));
            new WebServiceBase(nameValuePairs, this, this, CALL_OTP_VERIFIED, true).execute(WebServicesUrls.REGISTER_URL);
        } else {
            ToastClass.showShortToast(getApplicationContext(),"Please Enter Correct OTP");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long l) {
//                Log.d(TagUtils.getTag(), "time elapsed:-" + l);
                progress_timer.setProgress((int) l);
            }

            @Override
            public void onFinish() {
                progress_timer.setProgress(0);
            }
        }.start();
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        TagUtils.printResponse(apicall, response);
        switch (apicall) {
            case CALL_OTP_VERIFIED:
                parseOTPVerifiedResponse(response);
                break;
        }
    }

    public void parseOTPVerifiedResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                String userprofile=jsonObject.optJSONObject("user_detail").optJSONObject("user_profile").toString();
                Gson gson=new Gson();
                UserProfilePOJO userProfilePOJO=gson.fromJson(userprofile,UserProfilePOJO.class);
                Pref.SaveUserProfile(getApplicationContext(),userProfilePOJO);
                startActivity(new Intent(SatyapanActivity.this,MpinActivity.class).putExtra("mobile_number",mobile_number));
            }else{
                ToastClass.showShortToast(getApplicationContext(),"wrong otp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
