package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MpinActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String CALL_MPIN_SET = "call_mpin_set";
    @BindView(R.id.btn_accept)
    Button btn_accept;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_mpin)
    EditText et_mpin;
    @BindView(R.id.et_confirm_mpin)
    EditText et_confirm_mpin;

    String mobile_number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mobile_number=bundle.getString("mobile_number");
        }

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_mpin.getText().toString().length() == 0 || et_confirm_mpin.getText().toString().length() == 0) {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Mpin");
                } else {
                    if (et_confirm_mpin.getText().toString().equals(et_mpin.getText().toString())) {
//                        startActivity(new Intent(MpinActivity.this, HomeActivity.class));
                        callSetMpin();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "Please Enter same MPIN");
                    }
                }
            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void callSetMpin() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("login_request", "SET_MPIN"));
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_number));
        nameValuePairs.add(new BasicNameValuePair("mpin", et_confirm_mpin.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("mpin_confirm", et_confirm_mpin.getText().toString()));
        new WebServiceBase(nameValuePairs, this, this, CALL_MPIN_SET, true).execute(WebServicesUrls.REGISTER_URL);
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        TagUtils.printResponse(apicall, response);
        switch (apicall) {
            case CALL_MPIN_SET:
                parseMpinResponse(response);
                break;
        }
    }

    public void parseMpinResponse(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                startActivity(new Intent(MpinActivity.this,HomeActivity.class));
            }else{
                ToastClass.showShortToast(getApplicationContext(),"Failed to set Mpin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
