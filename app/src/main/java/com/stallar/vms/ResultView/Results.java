package com.stallar.vms.ResultView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.microblink.activity.ScanActivity;
import com.microblink.recognizers.RecognitionResults;
import com.stallar.vms.R;
import com.stallar.vms.ResultView.ResultFragment;

public class Results extends FragmentActivity {

    FragmentPagerAdapter adapterViewPager;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.results_menu);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new ResultFragmentAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

    class ResultFragmentAdapter extends FragmentPagerAdapter {

        RecognitionResults mResults = getIntent().getExtras().getParcelable(
                ScanActivity.EXTRAS_RECOGNITION_RESULTS);

        public ResultFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ResultFragment.newInstance(mResults.getRecognitionResults()[position]);
        }

        @Override
        public int getCount() {
            if (mResults.getRecognitionResults() == null) {

                return 0;
            } else {
                return mResults.getRecognitionResults().length;
            }
        }

//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mResults.getRecognitionResults()[position].getTitle();
//        }
    }

}
