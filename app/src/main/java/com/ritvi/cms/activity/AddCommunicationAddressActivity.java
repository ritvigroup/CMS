package com.ritvi.cms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.ritvi.cms.R;
import com.ritvi.cms.adapter.ViewPagerAdapter;
import com.ritvi.cms.fragment.RuralAddressFragment;
import com.ritvi.cms.fragment.UrbanAddressFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCommunicationAddressActivity extends AppCompatActivity {

    @BindView(R.id.addressViewPager)
    ViewPager addressViewPager;
    @BindView(R.id.rg_address)
    RadioGroup rg_address;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_communication_address);
        ButterKnife.bind(this);


        setupViewPager(addressViewPager);
        rg_address.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_urban) {
                    addressViewPager.setCurrentItem(1);
                } else {
                    addressViewPager.setCurrentItem(0);
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCommunicationAddressActivity.this, ApplicationSubmittedActivity.class));
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RuralAddressFragment(), "MOBILE");
        adapter.addFrag(new UrbanAddressFragment(), "AADHAR");
        viewPager.setAdapter(adapter);
    }

}
