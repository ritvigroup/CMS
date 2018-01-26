package com.ritvi.umnag.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ritvi.umnag.R;
import com.ritvi.umnag.adapter.CustomswipeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_dot1)
    ImageView iv_dot1;
    @BindView(R.id.iv_dot2)
    ImageView iv_dot2;
    @BindView(R.id.iv_dot3)
    ImageView iv_dot3;
    @BindView(R.id.iv_dot4)
    ImageView iv_dot4;
    @BindView(R.id.iv_dot5)
    ImageView iv_dot5;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_panjikaran)
    Button btn_panjikaran;

    List<Integer> imagesList=new ArrayList<>();

    List<ImageView> listImageViews=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        ButterKnife.bind(this);

        imagesList.add(R.drawable.ic_slider1);
        imagesList.add(R.drawable.ic_slider2);
        imagesList.add(R.drawable.ic_slider3);
        imagesList.add(R.drawable.ic_slider4);
        imagesList.add(R.drawable.ic_slider5);

        listImageViews.add(iv_dot1);
        listImageViews.add(iv_dot2);
        listImageViews.add(iv_dot3);
        listImageViews.add(iv_dot4);
        listImageViews.add(iv_dot5);

        iv_dot1.setImageResource(R.drawable.ic_filled_dot);

        setUpViewPager(imagesList);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btn_panjikaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SliderActivity.this,RegistrationActivity.class));
            }
        });
    }


    public void setUpViewPager(final List<Integer> imageIdList) {
        viewPager.setAdapter(new CustomswipeAdapter(this, imageIdList));
        viewPager.setOffscreenPageLimit(imageIdList.size());



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(ImageView imageView:listImageViews){
                    imageView.setImageResource(R.drawable.ic_dot);
                }

                listImageViews.get(position).setImageResource(R.drawable.ic_filled_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
