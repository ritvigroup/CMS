package com.ritvi.cms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.StringUtils;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.Util.ToastClass;
import com.ritvi.cms.activity.HomeActivity;
import com.ritvi.cms.activity.ProfileInfoActivity;
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

/**
 * Created by sunil on 29-01-2018.
 */

public class LoginMobileFragment extends Fragment implements WebServicesCallBack{

    private static final String CALL_LOGIN_API = "call_login_api";

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.et_mobile_number)
    EditText et_mobile_number;
    @BindView(R.id.et_mpin)
    EditText et_mpin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_login_mobile,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ProfileInfoActivity.class));
                callLoginAPI();
            }
        });
    }

    public void callLoginAPI(){
        if(et_mobile_number.getText().toString().length()>0&&et_mpin.getText().toString().length()>0) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("login_request", "LOGIN_WITH_MPIN"));
            nameValuePairs.add(new BasicNameValuePair("mobile", et_mobile_number.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("mpin", et_mpin.getText().toString()));
            new WebServiceBase(nameValuePairs, getActivity(), this, CALL_LOGIN_API, true).execute(WebServicesUrls.LOGIN_URL);
        }else{
            ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter required fields properly");
        }
    }
    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(),apicall+":-"+response);
        switch (apicall){
            case CALL_LOGIN_API:
                parseLoginResponse(response);
                break;
        }
    }

    public void parseLoginResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("status").equals("success")){
                String user_profile=jsonObject.optJSONObject("user_detail").optJSONObject("user_profile").toString();
                Gson gson=new Gson();
                UserProfilePOJO userProfilePOJO=gson.fromJson(user_profile,UserProfilePOJO.class);
                Pref.SaveUserProfile(getActivity(),userProfilePOJO);
                Pref.SetBooleanPref(getActivity(), StringUtils.IS_LOGIN,true);
                if(userProfilePOJO.getUserFullName().equals("")||userProfilePOJO.getUserGender().equals("0")||
                        userProfilePOJO.getUserDateOfBirth().equals("0000-00-00")||userProfilePOJO.getUserState().equals("")){
                    Pref.SetBooleanPref(getActivity(), StringUtils.IS_PROFILE_COMPLETED,false);
                    startActivity(new Intent(getActivity(), ProfileInfoActivity.class));
                    getActivity().finishAffinity();
                }else{
                    Pref.SetBooleanPref(getActivity(), StringUtils.IS_PROFILE_COMPLETED,true);
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    getActivity().finishAffinity();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
