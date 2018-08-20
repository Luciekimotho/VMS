package com.stallar.vms.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stallar.vms.R;
import com.stallar.vms.Scan;
import com.stallar.vms.Views.AddVisitor;
import com.stallar.vms.Views.Home;
import com.stallar.vms.Views.NationalIDScan;
import com.stallar.vms.Views.PassportScan;


public class ScanFragment extends Fragment implements View.OnClickListener {
    Button id, passport, no_document;


    public static ScanFragment newInstance(){
        ScanFragment fragement = new ScanFragment();
        return fragement;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home, container, false);
        id = (Button) v.findViewById(R.id.btn_id);
        passport = (Button) v.findViewById(R.id.btn_passport);
        //no_document = (Button) v.findViewById(R.id.btn_nodocument);

        id.setOnClickListener(this);
        passport.setOnClickListener(this);
        //no_document.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_id:
                Intent i = new Intent(getActivity(), NationalIDScan.class);
                i.putExtra("param", "param");
                startActivity(i);
                break;

            case R.id.btn_passport:
                startActivity(new Intent(getActivity(), PassportScan.class));
                break;

//            case R.id.btn_nodocument:
//                startActivity(new Intent(getActivity(), AddVisitor.class));
//                break;
        }
    }
}
