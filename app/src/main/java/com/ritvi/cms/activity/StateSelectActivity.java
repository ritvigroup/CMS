package com.ritvi.cms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ritvi.cms.R;
import com.ritvi.cms.adapter.StateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateSelectActivity extends AppCompatActivity {
    List<String> stateList = new ArrayList<>();

    @BindView(R.id.rv_places)
    RecyclerView rv_places;
    @BindView(R.id.et_place_search)
    EditText et_place_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_select);
        ButterKnife.bind(this);
        setStateList();
        attachAdapter();

        et_place_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(stateAdapter!=null){
                    if(et_place_search.getText().toString().length()>0) {
                        stateAdapter.getFilter().filter(et_place_search.getText().toString());
                    }else{
                        stateAdapter.getFilter().filter(" ");
                    }
                }
            }
        });

    }

    public void showSelectedState(String state_name) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("state", state_name);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
    StateAdapter stateAdapter;
    public void attachAdapter() {

        stateAdapter = new StateAdapter(this, null, stateList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_places.setHasFixedSize(true);
        rv_places.setAdapter(stateAdapter);
        rv_places.setLayoutManager(layoutManager);
        rv_places.setNestedScrollingEnabled(false);
        rv_places.setItemAnimator(new DefaultItemAnimator());
    }

    public void setStateList() {
        stateList.add("Andaman and Nicobar Islands");
        stateList.add("Andhra Pradesh");
        stateList.add("Arunachal Pradesh");
        stateList.add("Assam");
        stateList.add("Bihar");
        stateList.add("Chandigarh");
        stateList.add("Chhattisgarh");
        stateList.add("Dadra and Nagar Haveli");
        stateList.add("Daman and Diu");
        stateList.add("National Capital Territory of Delhi");
        stateList.add("Goa");
        stateList.add("Gujarat");
        stateList.add("Haryana");
        stateList.add("Himachal Pradesh");
        stateList.add("Jammu and Kashmir");
        stateList.add("Jharkhand");
        stateList.add("Karnataka");
        stateList.add("Kerala");
        stateList.add("Lakshadweep");
        stateList.add("Madhya Pradesh");
        stateList.add("Maharashtra");
        stateList.add("Manipur");
        stateList.add("Meghalaya");
        stateList.add("Mizoram");
        stateList.add("Nagaland");
        stateList.add("Odisha");
        stateList.add("Puducherry");
        stateList.add("Punjab");
        stateList.add("Rajasthan");
        stateList.add("Sikkim");
        stateList.add("Tamil Nadu");
        stateList.add("Telangana");
        stateList.add("Tripura");
        stateList.add("Uttar Pradesh");
        stateList.add("Uttarakhand");
        stateList.add("West Bengal");
    }

}
