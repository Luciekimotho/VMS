package com.stallar.vms.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.stallar.vms.R;

public class Home extends AppCompatActivity implements View.OnClickListener {
Button id, passport, no_document;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        id = (Button) findViewById(R.id.btn_id);
        passport = (Button) findViewById(R.id.btn_passport);
        //no_document = (Button) findViewById(R.id.btn_nodocument);

        id.setOnClickListener(this);
        passport.setOnClickListener(this);
        //no_document.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_id:
                Intent i = new Intent(Home.this, NationalIDScan.class);
                i.putExtra("param", "param");
                startActivity(i);
                break;

            case R.id.btn_passport:
                startActivity(new Intent(Home.this, PassportScan.class));
                break;

//            case R.id.btn_nodocument:
//                startActivity(new Intent(Home.this, AddVisitor.class));
//                break;
        }

    }
}
