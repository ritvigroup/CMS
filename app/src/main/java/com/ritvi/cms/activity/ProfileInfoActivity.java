package com.ritvi.cms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.Constants;
import com.ritvi.cms.Util.Pref;
import com.ritvi.cms.Util.StringUtils;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.pojo.user.UserProfilePOJO;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileInfoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, WebServicesCallBack {


    private static final String CALL_PROFILE_SAVE_API = "call_profile_save_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_calendar)
    ImageView iv_calendar;
    @BindView(R.id.et_birth_date)
    EditText et_birth_date;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rb_male)
    RadioButton rb_male;
    @BindView(R.id.rb_female)
    RadioButton rb_female;
    @BindView(R.id.rb_other)
    RadioButton rb_other;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_alternate_mobile)
    EditText et_alternate_mobile;

    @BindView(R.id.tv_state_select)
    TextView tv_state_select;
    @BindView(R.id.tv_skip)
    TextView tv_skip;
    @BindView(R.id.btn_accept)
    Button btn_accept;

    private final int STATE_SELECT_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileInfoActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select DOB");
            }
        });

        tv_state_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileInfoActivity.this, StateSelectActivity.class);
                startActivityForResult(i, STATE_SELECT_INTENT);
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileInfoActivity.this, HomeActivity.class));
                finish();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callProfileSaveAPI();
            }
        });

    }

    public void callProfileSaveAPI() {

        String gender = "";
        if (rg_gender.getCheckedRadioButtonId() != -1) {
            switch (((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString().toLowerCase()) {
                case "male":
                    gender = String.valueOf(Constants.GENDER_MALE);
                    break;
                case "female":
                    gender = String.valueOf(Constants.GENDER_FEMALE);
                    break;
                case "other":
                    gender = String.valueOf(Constants.GENDER_OTHER);
                    break;
                default:
                    gender = String.valueOf(Constants.GENDER_DEFAULT);
            }
        }

        String date="";


        if(et_birth_date.getText().toString().length()>0) {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d1 = simpleDateFormat.parse(et_birth_date.getText().toString());
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                date=sdf.format(d1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        UserProfilePOJO userProfilePOJO=Pref.GetUserProfile(this);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "UPDATE_PROFILE"));
        nameValuePairs.add(new BasicNameValuePair("user_id", userProfilePOJO.getUserId()));
        nameValuePairs.add(new BasicNameValuePair("fullname", et_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("gender", gender));
        nameValuePairs.add(new BasicNameValuePair("date_of_birth", date));
        nameValuePairs.add(new BasicNameValuePair("state", tv_state_select.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("alt_mobile", et_alternate_mobile.getText().toString()));
        new WebServiceBase(nameValuePairs, this, this, CALL_PROFILE_SAVE_API, true).execute(WebServicesUrls.EDIT_PROFILE);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        et_birth_date.setText(date);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATE_SELECT_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                String state = data.getStringExtra("state");
                tv_state_select.setText(state);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetMsg(String apicall, String response) {
        Log.d(TagUtils.getTag(), apicall + ":-" + response);
        switch (apicall) {
            case CALL_PROFILE_SAVE_API:
                parseProfileResponse(response);
                break;
        }
    }

    public void parseProfileResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("status").equals("success")) {
                String user_profile = jsonObject.optJSONObject("user_detail").optJSONObject("user_profile").toString();
                Gson gson = new Gson();
                UserProfilePOJO userProfilePOJO = gson.fromJson(user_profile, UserProfilePOJO.class);
                Pref.SaveUserProfile(getApplicationContext(), userProfilePOJO);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, true);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_COMPLETED, true);
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PROFILE_SKIPPED, true);
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finishAffinity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
