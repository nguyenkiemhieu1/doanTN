package com.kia99.skyrestaurant.View.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.kia99.skyrestaurant.Adapter.AdapterViewPager;
import com.kia99.skyrestaurant.Adapter.AdapterViewPagerAdmin;
import com.kia99.skyrestaurant.R;

public class TrangChuAdminActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    RadioButton radio_OD, radio_AG;
    RadioGroup radioGroup;
    ViewPager viewPager;
    ImageView imageView, imageView_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu_admin);
        init();
    }
    private void init() {
        viewPager =(ViewPager) findViewById(R.id.view_ViewPager);
        AdapterViewPagerAdmin adapterViewPager
                = new AdapterViewPagerAdmin(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        radio_OD =(RadioButton) findViewById(R.id.radio_OD);
        radio_AG =(RadioButton) findViewById(R.id.radio_AG);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        imageView = (ImageView) findViewById(R.id.img_ThemQuanAn);
        imageView_user = (ImageView) findViewById(R.id.img_user);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangChuAdminActivity.this, ThemQuanAnActivity.class);
                startActivity(intent);
            }
        });
        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intentdx = new Intent(TrangChuAdminActivity.this, LoginActivity.class);
                startActivity(intentdx);

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_AG:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radio_OD:
                viewPager.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                radio_OD.setChecked(true);
                break;
            case 1:
                radio_AG.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
