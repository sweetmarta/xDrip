package com.eveningoutpost.dexdrip.stats;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eveningoutpost.dexdrip.R;
import com.eveningoutpost.dexdrip.utils.ActivityWithMenu;

public class StatsActivity extends ActivityWithMenu {

    public static final String MENU_NAME = "Statistics";
    public static final int TODAY = 0;
    public static final int YESTERDAY = 1;
    public static final int D7 = 2;
    public static final int D30 = 3;
    public static final int D90 = 4;
    public static int state = TODAY;
    private static boolean swipeInfoNotNeeded = false; // don't show info if already swiped after startup.
    StatisticsPageAdapter mStatisticsPageAdapter;
    ViewPager mViewPager;
    TextView[] indicationDots;
    private Button buttonTD;
    private Button buttonYTD;
    private Button button7d;
    private Button button30d;
    private Button button90d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        assignButtonNames();
        initPagerAndIndicator();
        setButtonColors();
        registerButtonListeners();
        showStartupInfo();

    }

    private void showStartupInfo() {
        if (swipeInfoNotNeeded) {
            return;
        }

        TextView tv = new TextView(this);
        tv.setText("Swipe left/right to switch between reports!");
        tv.setTextColor(Color.CYAN);
        tv.setTextSize(28);

        for (int i = 0; i < 2 ; i++) {
            Toast toast = new Toast(getApplicationContext());
            toast.setView(tv);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void assignButtonNames() {
        buttonTD = (Button) findViewById(R.id.button_stats_today);
        buttonYTD = (Button) findViewById(R.id.button_stats_yesterday);
        button7d = (Button) findViewById(R.id.button_stats_7d);
        button30d = (Button) findViewById(R.id.button_stats_30d);
        button90d = (Button) findViewById(R.id.button_stats_90d);
    }

    private void initPagerAndIndicator() {
        mStatisticsPageAdapter =
                new StatisticsPageAdapter(
                        getSupportFragmentManager());
        // set dots for indication
        indicationDots = new TextView[mStatisticsPageAdapter.getCount()];
        LinearLayout indicator = (LinearLayout) findViewById(R.id.indicator_layout);
        for (int i = 0; i < indicationDots.length; i++) {
            indicationDots[i] = new TextView(this);
            indicationDots[i].setText("\u25EF");
            indicationDots[i].setTextSize(12);
            indicator.addView(indicationDots[i], new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        indicationDots[0].setText("\u26AB");
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mStatisticsPageAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position !=0){
                    swipeInfoNotNeeded = true;
                }

                for (int i = 0; i < indicationDots.length; i++) {
                    indicationDots[i].setText("\u25EF"); //U+2B24
                }
                indicationDots[position].setText("\u26AB");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(0);
    }

    void setButtonColors() {
        buttonTD.getBackground().setColorFilter(null);
        ;
        buttonYTD.getBackground().setColorFilter(null);
        button7d.getBackground().setColorFilter(null);
        button30d.getBackground().setColorFilter(null);
        button90d.getBackground().setColorFilter(null);
        switch (state) {
            case TODAY:
                buttonTD.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
            case YESTERDAY:
                buttonYTD.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
            case D7:
                button7d.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
            case D30:
                button30d.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
            case D90:
                button90d.getBackground().setColorFilter(new LightingColorFilter(0xFFFFFFFF, 0xFFAA0000));
                break;
        }
    }


    private void registerButtonListeners() {

        View.OnClickListener myListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == buttonTD) {
                    state = TODAY;
                } else if (v == buttonYTD) {
                    state = YESTERDAY;
                } else if (v == button7d) {
                    state = D7;
                } else if (v == button30d) {
                    state = D30;
                } else if (v == button90d) {
                    state = D90;
                }

                Log.d("DrawStats", "button pressed, invalidating");
                mStatisticsPageAdapter.notifyDataSetChanged();
                mViewPager.invalidate();
                setButtonColors();

            }
        };
        buttonTD.setOnClickListener(myListener);
        buttonYTD.setOnClickListener(myListener);
        button7d.setOnClickListener(myListener);
        button30d.setOnClickListener(myListener);
        button90d.setOnClickListener(myListener);


    }

    @Override
    public String getMenuName() {
        return MENU_NAME;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_stats, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public static class MockupFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(
                    R.layout.stats_layout_test, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    "One more chart? " + Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }

    public class StatisticsPageAdapter extends FragmentStatePagerAdapter {
        public StatisticsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0: return new FirstPageFragment();
                case 1: return new ChartFragment();
                case 2: return new PercentileFragment();
            }

            Fragment fragment = new MockupFragment();
            Bundle args = new Bundle();
            // Our object is just an integer
            args.putInt(MockupFragment.ARG_OBJECT, i -2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
            //return 5; // add mockup views
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position == 1) return "Range Pi Chart";
            return "OBJECT " + (position + 1);
        }

        @Override
        public int getItemPosition(Object object) {
            // return POSITION_NONE to update/repaint the views if notifyDataSetChanged()+invalidate() is called
            return POSITION_NONE;
        }

    }

}
