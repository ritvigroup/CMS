package com.ritvi.cms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ritvi.cms.R;
import com.ritvi.cms.activity.AadharQrScannerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 29-01-2018.
 */

public class LoginAadharFragment extends Fragment{

    @BindView(R.id.tv_scan_aadhar)
    TextView tv_scan_aadhar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_login_aadhar,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_scan_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                IntentIntegrator.forSupportFragment(LoginAadharFragment.this).initiateScan();
                startActivity(new Intent(getActivity(), AadharQrScannerActivity.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if(result != null) {
//            if(result.getContents() == null) {
//                ToastClass.showShortToast(getActivity(), "Cancelled");
//            } else {
//                Log.d(TagUtils.getTag(),"scanned result:-"+result.getContents());
//                ToastClass.showShortToast(getActivity(), "Scanned: " + result.getContents());
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
}
