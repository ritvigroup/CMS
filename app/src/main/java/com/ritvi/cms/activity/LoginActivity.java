package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ritvi.cms.R;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.adapter.ViewPagerWithTitleAdapter;
import com.ritvi.cms.fragment.LoginAadharFragment;
import com.ritvi.cms.fragment.LoginMobileFragment;
import com.ritvi.cms.webservice.WebServiceBase;
import com.ritvi.cms.webservice.WebServicesCallBack;
import com.ritvi.cms.webservice.WebServicesUrls;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,WebServicesCallBack{

    private static final String CALL_LOGIN_API = "call_login_api";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_facebook_login)
    ImageView iv_facebook_login;
    @BindView(R.id.iv_google_login)
    ImageView iv_google_login;
    @BindView(R.id.twitterLogin)
    TwitterLoginButton twitterLoginButton;
    @BindView(R.id.tv_new_user)
    TextView tv_new_user;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;
    CallbackManager callbackManager;

    private int RC_SIGN_IN = 100;


    private static final String TWITTER_KEY = "odTUxR2y7jhDIb1ImhiGE4VDY";
    private static final String TWITTER_SECRET = "FFKtAo7BeyDoEoUeRXZUq1FwHAjCHutOXZc4gcimEmG4cOMWKV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        RequestData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUpTabswithViewPager();

        initializeGoogleSignIn();

        iv_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FbIntegration();
            }
        });

        iv_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleIntegration();
            }
        });

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        tv_new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });
    }

    public void GoogleIntegration() {
        signIn();
    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void initializeGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void setUpTabswithViewPager() {
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(5);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getSupportFragmentManager());
        adapter.addFrag(new LoginMobileFragment(), "MOBILE");
        adapter.addFrag(new LoginAadharFragment(), "AADHAR");
        viewPager.setAdapter(adapter);

    }


    public void FbIntegration() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d(TagUtils.getTag(), "facebook response:-" + response.toString());
                JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
//                        String text = "<b>Name :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Profile link :</b> "+json.getString("link");
//                        Log.d("sunil",text);
                        Log.d(TagUtils.getTag(), "name:-" + json.getString("name"));
                        Log.d(TagUtils.getTag(), "link:-" + json.getString("link"));
                        Log.d(TagUtils.getTag(), "id:-" + json.getString("id"));
                        Log.d(TagUtils.getTag(), "email:-" + json.getString("email"));
//                        String path = FileUtils.BASE_FILE_PATH;
//                        File file = new File(path + File.separator + "fb_profile.png");
                        String profile_url = "https://graph.facebook.com/" + json.getString("id") + "/picture?type=large";
                        Log.d(TagUtils.getTag(), "facebook profile url:-" + profile_url);
//                        getBitmapCallApi(profile_url,json.getString("name"),
//                                json.getString("email"),"");
//                        String request_data = json.getString("name") + "," + "" + "," +
//                                "" + "," + "1234";
//
//                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//                        FacebookLoginAPI(json.getString("email"));
                    }

                } catch (JSONException e) {
                    Log.d("profile", e.toString());

                    try {
                        Log.d(TagUtils.getTag(), e.toString());
                        Log.d(TagUtils.getTag(), "id:-" + json.getString("id"));
                        Log.d(TagUtils.getTag(), "name:-" + json.getString("name"));
//                        String email="temp_"+json.getString("id")+"@bjain.com";
//                        FacebookLoginAPI(email);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            try {
                Log.d(TagUtils.getTag(), "name:-" + acct.getDisplayName());
                Log.d(TagUtils.getTag(), "email:-" + acct.getEmail());
                Log.d(TagUtils.getTag(), "image:-" + acct.getPhotoUrl().toString());
                Log.d(TagUtils.getTag(), "id:-" + acct.getId());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Your Google account is not configured with google plus account", Toast.LENGTH_SHORT).show();
            }

        } else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

//        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;
        TwitterAuthClient authClient = new TwitterAuthClient();
        Call<User> userResult = Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);
        userResult.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                User user = result.data;
                String user_name = user.email;
                String description = user.description;
                int followersCount = user.followersCount;
                Log.d(TagUtils.getTag(), "name:-" + user.name);
                Log.d(TagUtils.getTag(), "description:-" + user.description);
                String profileImage = user.profileImageUrl.replace("_normal", "");
                Log.d(TagUtils.getTag(), "profile image:-" + profileImage);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("sunil", "failed");
            }
        });
//        TwitterAuthClient authClient = new TwitterAuthClient();
        authClient.requestEmail(session, new Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
                String email = result.data;
                Log.d(TagUtils.getTag(), "email:-" + email);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

    }

    public void callLoginAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("",""));
        new WebServiceBase(nameValuePairs,this,this,CALL_LOGIN_API,true).execute(WebServicesUrls.LOGIN_URL);
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

    }
}
